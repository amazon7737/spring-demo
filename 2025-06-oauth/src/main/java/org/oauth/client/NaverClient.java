package org.oauth.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.oauth.dto.NaverDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.UUID;


@Component
public class NaverClient {

    @Value("${naver.client.id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client.secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${naver.redirect.url}")
    private String NAVER_REDIRECT_URL;

    private static final String NAVER_AUTH_URI = "https://nid.naver.com";
    private static final String NAVER_API_URI = "https://openapi.naver.com";

    public String getNAVER_CLIENT_ID() {
        return NAVER_CLIENT_ID;
    }

    public String getNAVER_CLIENT_SECRET() {
        return NAVER_CLIENT_SECRET;
    }

    public String getNAVER_REDIRECT_URL() {
        return NAVER_REDIRECT_URL;
    }

    public String getURI() {
        return NAVER_AUTH_URI + "/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + NAVER_REDIRECT_URL
                + "&state=" + UUID.randomUUID();
    }

    public HashMap<String, Object> execute(String code) throws Exception {
        if (code == null) throw new Exception("Authorization code is missing");

        String accessToken;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", NAVER_CLIENT_ID);
            params.add("client_secret", NAVER_CLIENT_SECRET);
            params.add("code", code);
            params.add("state", "naver");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(
                    NAVER_AUTH_URI + "/oauth2.0/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.getBody());

            accessToken = (String) json.get("access_token");
        } catch (Exception e) {
            throw new Exception("Failed to get access token", e);
        }

        return getUserProfile(accessToken);
    }

    private HashMap<String, Object> getUserProfile(String accessToken) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                NAVER_API_URI + "/v1/nid/me",
                HttpMethod.GET,
                request,
                String.class
        );

        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(response.getBody());
        JSONObject responseObj = (JSONObject) body.get("response");

        String id = String.valueOf(responseObj.get("id"));
        String email = String.valueOf(responseObj.get("email"));
        String name = String.valueOf(responseObj.get("name"));

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("type", "네이버");
        System.out.println(id + " " + email + " " + name);

        return result;
    }
}