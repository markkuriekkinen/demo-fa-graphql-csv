package com.fademo.facsvreport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class FetchTokenService {
    private final RestClient restClient;
    public static final String FA_OPENID_URL = "https://tryme.fasolutions.com/auth/realms/fa/protocol/openid-connect/token";

    @Value("#{environment.FA_USERNAME}")
    private String faUsername;
    @Value("#{environment.FA_PASSWORD}")
    private String faPassword;

    public FetchTokenService() {
        this.restClient = RestClient.create(FA_OPENID_URL);
    }

    public FaAccessToken getToken() {
        LinkedMultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
        postData.add("client_id", "external-api");
        postData.add("username", faUsername);
        postData.add("password", faPassword);
        postData.add("grant_type", "password");
        return restClient
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(postData)
                .retrieve()
                .body(FaAccessToken.class);
    }
}
