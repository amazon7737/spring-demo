package org.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleMessage {
    private String status;
    private GoogleDto data;
}
