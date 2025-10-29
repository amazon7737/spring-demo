package org.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoMessage {
    private String status;
    private KakaoDto data;

}
