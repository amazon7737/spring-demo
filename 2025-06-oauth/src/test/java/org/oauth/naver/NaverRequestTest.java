package org.oauth.naver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.oauth.client.NaverClient;
import org.oauth.dto.NaverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NaverEndpoint.class)
@AutoConfigureMockMvc
public class NaverRequestTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private NaverClient naverClient;

    @Test
    @DisplayName("네이버 콜백 요청 성공 응답 테스트")
    void 네이버_콜백_요청_성공_응답() throws Exception {
        // given
        NaverDto dto = NaverDto.of("1234", "amazon08@naver.com", "김강민");

        // when
        when(naverClient.execute("test-code")).thenReturn(dto);

        // then
        mockMvc.perform(get("/naver/callback").param("code", "test-code"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.data.email").value("amazon08@naver.com"))
                .andExpect(jsonPath("$.data.name").value("김강민"));

    }
}
