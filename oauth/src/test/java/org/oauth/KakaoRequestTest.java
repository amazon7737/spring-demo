package org.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class KakaoRequestTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;


    @Test
    @DisplayName("카카오 로그인 요청 테스트")
    void 카카오_로그인_요청_테스트() {


    }

}
