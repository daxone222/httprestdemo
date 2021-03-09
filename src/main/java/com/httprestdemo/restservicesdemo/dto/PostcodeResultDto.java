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
public class PostcodeResultDto {
    @JsonProperty("postcode")
    String postcode;
    @JsonProperty("quality")
    Integer quality;
    @JsonProperty("eastings")
    Integer eastings;
    @JsonProperty("northings")
    Integer northings;
    @JsonProperty("country")
    String country;
    @JsonProperty("nhs_ha")
    String nhsHa;
    @JsonProperty("longitude")
    Double longitude;
    @JsonProperty("latitude")
    Double latitude;
    @JsonProperty("european_electoral_region")
    String europeanElectoralRegion;
    @JsonProperty("primary_care_trust")
    String primaryCareTrust;
    @JsonProperty("region")
    String region;
    @JsonProperty("lsoa")
    String lsoa;
    @JsonProperty("msoa")
    String msoa;
    @JsonProperty("incode")
    String incode;
    @JsonProperty("outcode")
    String outcode;
    @JsonProperty("parliamentary_constituency")
    String parliamentaryConstituency;
    @JsonProperty("admin_district")
    String adminDistrict;
    @JsonProperty("parish")
    String parish;
    @JsonProperty("admin_county")
    Object adminCounty;
    @JsonProperty("admin_ward")
    String adminWard;
    @JsonProperty("ccg")
    String ccg;
    @JsonProperty("nuts")
    String nuts;
    @JsonProperty("codes")
    CodesUkDto codes;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
