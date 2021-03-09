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
public class CodesUkDto {

    @JsonProperty("admin_district")
    String adminDistrict;
    @JsonProperty("admin_county")
    String adminCounty;
    @JsonProperty("admin_ward")
    String adminWard;
    @JsonProperty("parish")
    String parish;
    @JsonProperty("parliamentary_constituency")
    String parliamentaryConstituency;
    @JsonProperty("ccg")
    String ccg;
    @JsonProperty("nuts")
    String nuts;
}
