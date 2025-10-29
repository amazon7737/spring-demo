package org.oauth.dto;

import lombok.Getter;

@Getter
public class NaverDto {
    private String id;
    private String email;
    private String name;

    public NaverDto(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static NaverDto of(String id, String email, String name){
        return new NaverDto(id,email,name);
    }


}
