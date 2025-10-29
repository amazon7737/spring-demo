package org.oauth.endpoint;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.oauth.client.GoogleClient;
import org.oauth.dto.GoogleDto;
import org.oauth.dto.GoogleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

public class CallbackController {
    @GetMapping( "/google")
    public ResponseEntity<UserResponse> callbackWithGoogle(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        System.out.println("!!!!!!" + " " + code);

        HashMap<String, Object> response = googleClient.execute(code);
        UserResponse result = userService.prepare(response);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/kakao")
    public ResponseEntity<UserResponse> callbackWithKakao(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");

        HashMap<String, Object> response = kakaoClient.execute(code);
        UserResponse result = userService.prepare(response);
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/naver")
    public ResponseEntity<UserResponse> callbackWithNaver(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");

        HashMap<String, Object> response = naverClient.execute(code);
        UserResponse result = userService.prepare(response);
        return ResponseEntity.ok(result);
    }
}
