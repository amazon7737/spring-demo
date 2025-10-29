package org.oauth.naver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.oauth.client.NaverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {NaverClient.class})
public class NaverClientPropertiesTest {

    @Autowired NaverClient client;

    @Test
    @DisplayName("네이버 설정 연동 확인 테스트")
    void 네이버_설정_연동_확인() {
        assertThat(client.getNAVER_CLIENT_ID()).isNotNull();
        assertThat(client.getNAVER_CLIENT_ID().getClass()).isEqualTo(String.class);
        assertThat(client.getNAVER_CLIENT_SECRET()).isNotNull();
        assertThat(client.getNAVER_CLIENT_SECRET().getClass()).isEqualTo(String.class);
        assertThat(client.getNAVER_REDIRECT_URL()).isNotNull();
        assertThat(client.getNAVER_REDIRECT_URL().getClass()).isEqualTo(String.class);
    }

    @Test
    @DisplayName("네이버 로그인 요청 URL 확인 테스트")
    void 네이버_로그인_요청_URL_확인() {
        var uri = client.getURI();

        assertThat(uri).isNotNull();
        assertThat(uri.getClass()).isEqualTo(String.class);
        assertThat(uri.contains("/oauth")).isEqualTo(true);
    }

}
