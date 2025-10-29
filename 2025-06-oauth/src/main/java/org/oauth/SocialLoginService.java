package org.oauth;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SocialLoginService {
    private final UserCrudService userCrudService;
    private final CustomUserDetailsService userDetailsService;
    private final Logger log = Logger.getLogger("UserService");

    protected static String socialId;
    protected static String accessToken;
    protected static LoginType loginType;
    protected static String username;

    public UserResponse prepare(HashMap<String, Object> callbacked) {
//        String socialId1 = String.valueOf(callbacked.get("id"));
//        String trimmed = socialId1.length() > 18 // 소셜아이디가 18자리를 초과하면 끝자리에서 앞으로 자르도록
//                ? socialId1.substring(socialId1.length() - 18)
//                : socialId1;

        socialId = String.valueOf(callbacked.get("id"));
        username = String.valueOf(callbacked.get("name"));
        loginType = LoginType.valueOf(String.valueOf(callbacked.get("type")));

        try{
            User user = userCrudService.getSocialLoginId(socialId).orElseThrow(() -> new NeedSignUpException("회원가입 필요"));
            return login(user);
        } catch (NeedSignUpException e){
            return signUp();
        }

    }

    public UserResponse login(User user) {
        log.info("로그인 처리");
//        user.tokenRecently(accessToken);
        try{
        String token = userDetailsService.createToken(user.getPrimaryId(), socialId);
        accessToken = token;
        user.tokenRecently(token);
        }catch (Exception e){
            System.out.println(e);
        }
        return UserResponse.from(user, accessToken);
    }


    public UserResponse signUp() {
        log.info("회원가입 처리");

        User user = new User(socialId, loginType, username);
        userCrudService.save(user);

        User registered = userCrudService.getSocialLoginId(socialId).get();
        try{
            String token = userDetailsService.createToken(registered.getPrimaryId(), socialId);
            System.out.println("!#!@#@!#!@ " + token);
            accessToken = token;
            registered.tokenRecently(token);
        }catch (Exception e){
            System.out.println(e);
        }


        // 탈퇴되지않았다는거 검증
        // 생성날짜

        return UserResponse.from(socialId, accessToken);
    }

}