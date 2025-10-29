package org.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaverMessage {
    private String status;
    private NaverDto data;
}
