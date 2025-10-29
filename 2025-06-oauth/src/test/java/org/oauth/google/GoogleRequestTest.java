package org.oauth.google;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.oauth.client.GoogleClient;
import org.oauth.dto.GoogleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GoogleEndpoint.class)
@AutoConfigureMockMvc
public class GoogleRequestTest {

    @MockBean GoogleClient googleClient;
    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("사용자 정보 요청 목테스트")
    void 구글_콜백_요청_성공_응답() throws Exception {
        GoogleDto dto = GoogleDto.of("1234", "amazon7737@gmail.com", "amazon7737");

        when(googleClient.execute("test-code")).thenReturn(dto);

        mockMvc.perform(get("/google/callback").param("code", "test-code"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1234"))
                .andExpect(jsonPath("$.data.email").value("amazon7737@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("amazon7737"));

    }

}
