package org.oauth.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.oauth.dto.KakaoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoClient {

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private static final String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private static final String KAKAO_API_URI = "https://kapi.kakao.com";


    public String getKAKAO_CLIENT_ID() {
        return KAKAO_CLIENT_ID;
    }

    public String getKAKAO_CLIENT_SECRET() {
        return KAKAO_CLIENT_SECRET;
    }

    public String getKAKAO_REDIRECT_URL() {
        return KAKAO_REDIRECT_URL;
    }

    public String getURI() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    }

    public HashMap<String, Object> execute(String code) throws Exception {
        if (code == null) throw new Exception("Authorization code is missing");

        String accessToken;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("redirect_uri", KAKAO_REDIRECT_URL);
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );
//            System.out.println("!!!! " + response.getBody());

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
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.GET,
                request,
                Map.class
        );
        Map<String,Object> user = response.getBody();
        Long id = Long.valueOf(user.get("id").toString());
        Map<String, Object> account = (Map<String, Object>) user.get("kakao_account");

        System.out.println("!!!" + " " + account);

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

//        String email = account.get("email").toString();
        String name = profile.get("nickname").toString();
        String profileImage = profile.get("profile_image_url").toString();

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("type", "카카오");

        return result;
    }
}
