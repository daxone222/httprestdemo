package com.httprestdemo.restservicesdemo.restutils;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dgliga on 15.11.2016.
 */
class RestTemplateManager {

    /**
     * Returns restTemplate object to be used by restTemplateUtils for calling REST services
     *
     * @param readTimeout       milliseconds to wait to read data
     * @param connectionTimeout milliseconds to wait while making the initial connection
     * @return
     */
    static RestTemplate getRestTemplateWithTimeouts(int readTimeout, int connectionTimeout) {
        return new RestTemplate(buildClientHttpRequestFactory(readTimeout, connectionTimeout));
    }

    /**
     * Returns request factory with timeouts to be applied on rest template object
     *
     * @param readTimeout
     * @param connectionTimeout
     * @return
     */
    private static ClientHttpRequestFactory buildClientHttpRequestFactory(int readTimeout, int connectionTimeout) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectionTimeout);
        return factory;
    }

    /**
     * Returns rest template object with clientHttpRequestFactory, to be used by restTemplateUtils
     *
     * @param clientHttpRequestFactory factory with custom settings for making rest calls
     * @return rest template object
     */
    static RestTemplate getRestTemplateWithClientRequestFactory(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

}
