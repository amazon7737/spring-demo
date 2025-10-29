package org.oauth.google;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.oauth.client.GoogleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {GoogleClient.class})
public class GoogleClientPropertiesTest {

    @Autowired GoogleClient client;

    @Test
    @DisplayName("구글 설정 연동 확인 테스트")
    void 구글_설정_연동_확인() {
        assertThat(client.getGOOGLE_CLIENT_ID()).isNotNull();
        assertThat(client.getGOOGLE_CLIENT_ID().getClass()).isEqualTo(String.class);
        assertThat(client.getGOOGLE_CLIENT_SECRET()).isNotNull();
        assertThat(client.getGOOGLE_CLIENT_SECRET().getClass()).isEqualTo(String.class);
        assertThat(client.getGOOGLE_REDIRECT_URL()).isNotNull();
        assertThat(client.getGOOGLE_REDIRECT_URL().getClass()).isEqualTo(String.class);
    }

    @Test
    @DisplayName("구글 로그인 요청 URL 확인 테스트")
    void 구글_로그인_요청_URL_확인() {
        var uri = client.getURI();

        assertThat(uri).isNotNull();
        assertThat(uri.getClass()).isEqualTo(String.class);
        assertThat(uri.contains("/oauth")).isEqualTo(true);
    }


}
