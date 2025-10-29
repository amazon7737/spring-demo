package org.oauth.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oauth.client.GoogleClient;
import org.oauth.client.KakaoClient;
import org.oauth.client.NaverClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SocialLoginTestController {
    private final KakaoClient kakaoService;
    private final GoogleClient googleService;
    private final NaverClient naverService;

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public String login(Model model) {
        log.info("getKaKaoLogin: {}", kakaoService.getURI());
        log.info("getGoogleLogin: {}", googleService.getURI());
        log.info("getNaverLogin: {}", naverService.getURI());


        model.addAttribute("kakaoUrl", kakaoService.getURI());
        model.addAttribute("googleUrl", googleService.getURI());
        model.addAttribute("naverUrl", naverService.getURI());

        return "index";
    }
}
