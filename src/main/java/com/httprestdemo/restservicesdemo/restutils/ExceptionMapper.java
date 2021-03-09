package com.httprestdemo.restservicesdemo.restutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

/**
 * Created by dgliga on 23.02.2017.
 */
public class ExceptionMapper {

    private static Logger LOGGER = LogManager.getLogger(ExceptionMapper.class);


    public static <E> E mapHttpStatusCodeException(HttpStatusCodeException exception, Class<E> expectedExceptionClass) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(exception.getResponseBodyAsByteArray(), expectedExceptionClass);
        } catch (IOException e) {
            LOGGER.debug("Cannot map: " + exception + " into class: " + expectedExceptionClass);
            throw e;
        }
    }

}
