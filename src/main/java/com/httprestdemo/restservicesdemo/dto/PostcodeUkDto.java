package com.httprestdemo.restservicesdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Created by dgliga on 26.02.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostcodeUkDto {
    @JsonProperty("status")
    Integer status;
    @JsonProperty("result")
    PostcodeResultDto result;

    public PostcodeResultDto getResult() {
        return result;
    }

    public void setResult(PostcodeResultDto result) {
        this.result = result;
    }
}

