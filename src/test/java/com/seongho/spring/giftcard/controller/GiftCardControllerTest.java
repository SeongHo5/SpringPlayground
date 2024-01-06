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
    @DisplayName("상품권 발급 실패 - 최소 금액 미만")
    @Transactional
    void shouldFailToIssueGiftCardUnderMinimumValue() throws Exception {
        // Given
        String value = "9999";

        // When & Then
        mockMvc.perform(post("/api/gift-card/issue")
                .param("value", value))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품권 발급 실패 - 최대 금액 초과")
    @Transactional
    void shouldFailToIssueGiftCardOverMaximumValue() throws Exception {
        // Given
        String value = "10000001";

        // When & Then
        mockMvc.perform(post("/api/gift-card/issue")
                .param("value", value))
                .andExpect(status().isBadRequest());
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
    @DisplayName("상품권 사용 실패 - 존재하지 않는 코드")
    @Transactional
    void shouldFailToUseGiftCardWithNonExistentCode() throws Exception {
        // Given
        String code = "1111-2222-3333-444444";

        // When & Then
        mockMvc.perform(post("/api/gift-card/use")
                .param("code", code))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("상품권 사용 실패 - 이미 사용된 상품권")
    @Transactional
    void shouldFailToUseAlreadyUsedGiftCard() throws Exception {
        // Given
        String code = "A9KM-0USD-FW5V-2VPXO5";

        // When & Then
        mockMvc.perform(post("/api/gift-card/use")
                        .param("code", code))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("상품권 사용 실패 - 만료된 상품권")
    @Transactional
    void shouldFailToUseExpiredGiftCard() throws Exception {
        // Given
        String code = "G4UX-AKWG-LEUV-5NZ278";

        // When & Then
        mockMvc.perform(post("/api/gift-card/use")
                .param("code", code))
                .andExpect(status().is4xxClientError());
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
