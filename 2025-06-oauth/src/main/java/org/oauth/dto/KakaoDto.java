package org.oauth.dto;

import lombok.Getter;

@Getter
public class KakaoDto {

    private long id;
    private String name;

    public KakaoDto(long id, String nickname) {
        this.id = id;
        this.name = nickname;
    }

    public static KakaoDto of(long id, String name){
        return new KakaoDto(id, name);
    }

}
