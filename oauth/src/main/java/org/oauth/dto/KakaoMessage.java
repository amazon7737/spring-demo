package org.oauth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoMessage {
    private String id;
    private Object result;

    public KakaoMessage(String id, Object result) {
        this.id = id;
        this.result = result;
    }
}
