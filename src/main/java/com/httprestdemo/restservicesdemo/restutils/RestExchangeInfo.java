package com.httprestdemo.restservicesdemo.restutils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

/**
 * Created by dgliga on 21.10.2016.
 */
public class RestExchangeInfo {

    private String requestDescription;
    private String url;
    private HttpMethod httpMethod;
    private HttpEntity httpEntity;
    private Class response;
    private int sleepTimeAfterRequestInSec;
    private Object[] uriVariables;

    RestExchangeInfo(String requestDescription, String url, HttpMethod httpMethod, HttpEntity httpEntity,
                     Class response, int sleepTimeAfterRequestInSec, Object[] uriVariables) {
        this.requestDescription = requestDescription;
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpEntity = httpEntity;
        this.response = response;
        this.sleepTimeAfterRequestInSec = sleepTimeAfterRequestInSec;
        this.uriVariables = uriVariables;
    }

    public static RestExchangeInfo.RestExchangeDataBuilder builder() {
        return new RestExchangeInfo.RestExchangeDataBuilder();
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public String getUrl() {
        return this.url;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public HttpEntity getHttpEntity() {
        return this.httpEntity;
    }

    public Class getResponse() {
        return this.response;
    }

    public int getSleepTimeAfterRequestInSec() {
        return this.sleepTimeAfterRequestInSec;
    }

    public Object[] getUriVariables() {
        return this.uriVariables;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    public void setResponse(Class response) {
        this.response = response;
    }

    public void setSleepTimeAfterRequestInSec(int sleepTimeAfterRequestInSec) {
        this.sleepTimeAfterRequestInSec = sleepTimeAfterRequestInSec;
    }

    public void setUriVariables(Object[] uriVariables) {
        this.uriVariables = uriVariables;
    }

    public static class RestExchangeDataBuilder {
        private String requestDescription;
        private String url;
        private HttpMethod httpMethod;
        private HttpEntity requestBody;
        private Class response;
        private int sleepTimeAfterRequestInSec;
        private Object[] uriVariables;

        RestExchangeDataBuilder() {
        }

        public RestExchangeInfo.RestExchangeDataBuilder requestDescription(String requestDescription) {
            this.requestDescription = requestDescription;
            return this;
        }

        public RestExchangeInfo.RestExchangeDataBuilder url(String url) {
            this.url = url;
            return this;
        }

        public RestExchangeInfo.RestExchangeDataBuilder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public RestExchangeInfo.RestExchangeDataBuilder requestBody(HttpEntity requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public RestExchangeInfo.RestExchangeDataBuilder response(Class response) {
            this.response = response;
            return this;
        }

        public RestExchangeInfo.RestExchangeDataBuilder sleepTimeAfterRequestInSec(int sleepTimeAfterRequestInSec) {
            this.sleepTimeAfterRequestInSec = sleepTimeAfterRequestInSec;
            return this;
        }

        public RestExchangeInfo.RestExchangeDataBuilder uriVariables(Object... uriVariables) {
            this.uriVariables = uriVariables;
            return this;
        }

        public RestExchangeInfo build() {
            return new RestExchangeInfo(this.requestDescription, this.url, this.httpMethod, this.requestBody,
                    this.response, this.sleepTimeAfterRequestInSec, this.uriVariables);
        }

        public String toString() {
            return "RestExchangeInfo.RestExchangeDataBuilder(requestDescription=" + this.requestDescription + ", url="
                    + this.url + ", httpMethod=" + this.httpMethod + ", httpEntity="
                    + this.requestBody + ", response=" + this.response + ", sleepTimeAfterRequestInSec=" + this.sleepTimeAfterRequestInSec
                    + ", uriVariables=" + Arrays.deepToString(this.uriVariables) + ")";
        }
    }

}
