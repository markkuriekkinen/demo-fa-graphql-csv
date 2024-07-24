package com.fademo.facsvreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
record FaAccessToken(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType) {
}
