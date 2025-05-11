package org.oauth.dto;


public class KakaoDto {

    private long id;
    private String email;
    private String nickname;

    public KakaoDto(long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static KakaoDto of(long id, String email, String nickname){
        return new KakaoDto(id, email, nickname);
    }

}
