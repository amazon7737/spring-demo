package org.oauth.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.oauth.KakaoClient;
import org.oauth.dto.KakaoDto;
import org.oauth.endpoint.KakaoEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(KakaoEndpoint.class)
@AutoConfigureMockMvc
public class KakaoRequestTest {

    @MockBean
    KakaoClient kakaoClient;

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    private static final String accessToken = "cLDCR4xs1TYyrJAIzQ9bzvXuzML37QiimPVuEwo9dVoAAAF7dvoLNw";

    @Test
    @DisplayName("사용자 정보 요청 목테스트")
    void 카카오_콜백_요청_성공_응답() throws Exception {
        // given
        KakaoDto dto = KakaoDto.of(1234, "amazon7737@gmail.com", "amazon7737");

        // when
        when(kakaoClient.execute("message")).thenReturn(dto);

        // then
        mockMvc.perform(get("/callback").param("message", "message"))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
