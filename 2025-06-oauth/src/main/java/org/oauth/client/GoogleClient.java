package org.oauth.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.oauth.dto.GoogleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class GoogleClient {

    @Value("${google.client.id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client.secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${google.client.redirect.url}")
    private String GOOGLE_REDIRECT_URL;

    private static final String GOOGLE_AUTH_URI = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_API_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

    public String getGOOGLE_CLIENT_ID() {
        return GOOGLE_CLIENT_ID;
    }

    public String getGOOGLE_CLIENT_SECRET() {
        return GOOGLE_CLIENT_SECRET;
    }

    public String getGOOGLE_REDIRECT_URL() {
        return GOOGLE_REDIRECT_URL;
    }

    public String getURI() {
        return GOOGLE_AUTH_URI +
                "?client_id=" + GOOGLE_CLIENT_ID +
                "&redirect_uri=" + GOOGLE_REDIRECT_URL +
                "&response_type=code" +
                "&scope=openid%20email%20profile";
    }

    public HashMap<String, Object> execute(String code) throws Exception {
        if (code == null) throw new Exception("Authorization code is missing");

        String accessToken;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", GOOGLE_CLIENT_ID);
            params.add("client_secret", GOOGLE_CLIENT_SECRET);
            params.add("redirect_uri", GOOGLE_REDIRECT_URL);
            params.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(
                    GOOGLE_TOKEN_URI,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.getBody());

            accessToken = (String) json.get("access_token");
        } catch (Exception e) {
            throw new Exception("Failed to get access token from Google", e);
        }

        return getUserProfile(accessToken);
    }

    private HashMap<String, Object> getUserProfile(String accessToken) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_API_URI,
                HttpMethod.GET,
                request,
                String.class
        );

        JSONParser parser = new JSONParser();
        JSONObject user = (JSONObject) parser.parse(response.getBody());

        String id = String.valueOf(user.get("id"));
        String email = String.valueOf(user.get("email"));
        String name = String.valueOf(user.get("name"));

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("type", "구글");
        System.out.println(id + " " + email + " " + name);

        return result;
    }
}
