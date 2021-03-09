package com.httprestdemo.restservicesdemo.restutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.httprestdemo.restservicesdemo.testnglisteners.TestDescriptor;

import java.io.IOException;
import java.util.Optional;


/**
 * Created by dgliga on 12.10.2016.
 */
@SuppressWarnings("unchecked")
@Component
public class RestTemplateUtils {

    private Logger LOGGER = LogManager.getLogger(RestTemplateUtils.class);

    private RestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public RestTemplateUtils() {
        this.restTemplate = new RestTemplate();
    }

    public RestTemplateUtils(int readTimeout, int connectionTimeout) {
        this.restTemplate = RestTemplateManager.getRestTemplateWithTimeouts(readTimeout, connectionTimeout);
    }

    public RestTemplateUtils(ClientHttpRequestFactory clientHttpRequestFactory) {
        this.restTemplate = RestTemplateManager.getRestTemplateWithClientRequestFactory(clientHttpRequestFactory);
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Generic method that handles REST requests
     *
     * @param restExchangeInfo       object containing request information
     * @param expectedExceptionClass class that will map the potential HttpStatusCodeException that rest template throws
     *                               in case of problems. It will be defined based on specific application implementation.
     *                               Must extend Exception.
     * @param <E>                    type by which the response body of the response entity is mapped. Can be array.
     * @return response entity that contains response body, headers and status code.
     */
    public <E> ResponseEntity<E> makeExchange(RestExchangeInfo restExchangeInfo, Class expectedExceptionClass) {
        ResponseEntity<E> responseEntity;

        logRequestInfo(restExchangeInfo);

        try {
            Object[] uriVariables = restExchangeInfo.getUriVariables();

            if (uriVariables == null) {
                responseEntity = restTemplate.exchange(restExchangeInfo.getUrl(), restExchangeInfo.getHttpMethod(),
                        restExchangeInfo.getHttpEntity(), restExchangeInfo.getResponse());
            } else {
                responseEntity = restTemplate.exchange(restExchangeInfo.getUrl(), restExchangeInfo.getHttpMethod(),
                        restExchangeInfo.getHttpEntity(), restExchangeInfo.getResponse(), restExchangeInfo.getUriVariables());
            }

            logResponseInfo(responseEntity);

            sleep(restExchangeInfo.getSleepTimeAfterRequestInSec());

            return responseEntity;

        } catch (HttpStatusCodeException e) {
            handleException(e, expectedExceptionClass);

            throw e;
        }
    }

    /**
     * HttpStatusCodeException handler that transforms HttpStatusCodeException into application specific exception
     *
     * @param e                      status code exception thrown by rest template
     * @param expectedExceptionClass Exception class by which to map e.
     * @param <T>                    Must extend Exception
     */
    private <T extends Exception> void handleException(HttpStatusCodeException e, Class<T> expectedExceptionClass) {
        LOGGER.debug("Status code was: " + e.getStatusCode());
        if (expectedExceptionClass == null) {
            e.printStackTrace();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Exception exception = mapper.readValue(e.getResponseBodyAsByteArray(), expectedExceptionClass);

                LOGGER.debug("Exception occurred: " + exception);
            } catch (IOException e1) {
                LOGGER.debug("Cannot map " + e + " into " + expectedExceptionClass);
            }
        }
    }

    /**
     * Method used to wait after a request, if necessary. After some requests some kind of wait is needed before
     * performing next steps (wait until db writes / reads complete...etc)
     *
     * @param sec time to wait in seconds
     */
    private void sleep(int sec) {
        try {
            if (sec != 0)
                LOGGER.debug("Waiting after request for " + sec + " seconds ...");
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            //do nothing
        }
    }

    /**
     * Logs request information for increased verbosity in reports
     *
     * @param restExchangeInfo object containing request information
     */
    private void logRequestInfo(RestExchangeInfo restExchangeInfo) {
        UriComponents uriComponents;
        if (restExchangeInfo.getUriVariables() != null) {
            uriComponents = UriComponentsBuilder.fromHttpUrl(restExchangeInfo.getUrl())
                    .buildAndExpand(restExchangeInfo.getUriVariables());
        } else {
            uriComponents = UriComponentsBuilder.fromHttpUrl(restExchangeInfo.getUrl()).build();
        }

        TestDescriptor.describeStep(Optional.ofNullable(restExchangeInfo.getRequestDescription())
                .orElse("No description provided for this step."));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Making ").append(restExchangeInfo.getHttpMethod())
                .append(" request").append(" on URL: ").append(uriComponents);

        HttpEntity entity = restExchangeInfo.getHttpEntity();
        if (entity != null) {
            if (entity.getBody() != null) {
                String s = null;
                try {
                    s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(restExchangeInfo.getHttpEntity().getBody());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("\nwith request body: ").append(Optional.ofNullable(s).orElse("JSON was not properly processed."));
                stringBuilder.append("\nmapped as DTO: ").append(restExchangeInfo.getHttpEntity().getBody().toString());
            }
        }
        LOGGER.debug(stringBuilder);
    }

    /**
     * Logs response information for increased verbosity in reports
     *
     * @param responseEntity response entity that contains body, headers and status code.
     * @param <E>            type by which response entity body will be mapped
     */
    private <E> void logResponseInfo(ResponseEntity<E> responseEntity) {
        if (responseEntity.getBody() != null) {
            StringBuilder respBuilderJson = new StringBuilder();
            StringBuilder respBuilderDto = new StringBuilder();
            respBuilderJson.append("Response body was: ");
            respBuilderDto.append("Response body mapped by DTO: ");

            try {
                respBuilderJson.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseEntity.getBody()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (responseEntity.getBody().getClass().isArray()) {
                for (Object o : (E[]) responseEntity.getBody()) {
                    respBuilderDto.append(o).append("\n\t\t");
                }
            } else {
                respBuilderDto.append(responseEntity.getBody());
            }
            respBuilderDto.append("\nStatus code was: ").append(responseEntity.getStatusCode());

            LOGGER.debug(respBuilderJson);
            LOGGER.debug(respBuilderDto);
        }
    }
}
