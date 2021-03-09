package com.httprestdemo.restservicesdemo.service;


import com.httprestdemo.restservicesdemo.dto.PostcodeUkDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.httprestdemo.restservicesdemo.restutils.RestExchangeInfo;
import com.httprestdemo.restservicesdemo.restutils.RestTemplateUtils;

/**
 * Created by dgliga on 26.02.2018.
 */
@Component
public class ServiceImpl {

    @Value("${base.url}")
    private String getRandomPostcode;

    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);

    /**
     * This method represents the implementation of the call to retrieve random uk post code from postcodesio API
     *
     * @return PostcodeUkDto object containing all information retrieved from service
     */
    public ResponseEntity<PostcodeUkDto> getRandomUkPostCode() {
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get Random UK Post Code")
                .url(getRandomPostcode)
                .httpMethod(HttpMethod.GET)
                .response(PostcodeUkDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, Exception.class);
    }

}
