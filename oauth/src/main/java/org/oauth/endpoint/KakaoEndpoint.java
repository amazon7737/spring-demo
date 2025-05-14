package org.oauth.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import org.oauth.KakaoClient;
import org.oauth.dto.KakaoDto;
import org.oauth.dto.KakaoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoEndpoint {

    private final KakaoClient kakaoClient;

    private final Logger log = LoggerFactory.getLogger("KakaoEndPoint");

    public KakaoEndpoint(KakaoClient kakaoClient) {
        this.kakaoClient = kakaoClient;
    }

    @GetMapping("/callback")
    public ResponseEntity<KakaoMessage> callback(HttpServletRequest request) throws Exception {
        KakaoDto response = kakaoClient.execute(request.getParameter("accessToken"));
        log.info("accessToken: {}", request.getParameter("accessToken"));

        return ResponseEntity.ok().body(new KakaoMessage("Success", response));
    }


}
