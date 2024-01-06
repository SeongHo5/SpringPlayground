package com.seongho.spring.giftcard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class GiftCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품권 발급 성공")
    @Transactional
    void shouldIssueGiftCard() throws Exception {
        // Given
        String value = "10000";

        // When & Then
        mockMvc.perform(post("/api/gift-card/issue")
                .param("value", value))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품권 사용 성공")
    @Transactional
    void shouldUseGiftCard() throws Exception {
        // Given
        String code = "CLLW-2QQ4-QY4A-PWZ6W9";

        // When & Then
        mockMvc.perform(post("/api/gift-card/use")
                .param("code", code))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품권 폐기 성공")
    @Transactional
    void shouldDisposeGiftCard() throws Exception {
        // Given
        String code = "CLLW-2QQ4-QY4A-PWZ6W9";

        // When & Then
        mockMvc.perform(delete("/api/gift-card/dispose")
                .param("code", code))
                .andExpect(status().isOk());
    }
}
