package com.seongho.spring.giftcard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class GiftCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestFactory
    @DisplayName("상품권 사용 테스트")
    @Transactional
    Stream<DynamicTest> dynamicTestForUseGiftCard() {
        return Stream.of(
                testApiEndpoint("상품권 사용 성공", useGiftCard("CLLW-2QQ4-QY4A-PWZ6W9"), status().isOk()),
                testApiEndpoint("상품권 사용 실패 - 존재하지 않는 코드", useGiftCard("1111-2222-3333-444444"), status().isNotFound()),
                testApiEndpoint("상품권 사용 실패 - 이미 사용된 상품권", useGiftCard("A9KM-0USD-FW5V-2VPXO5"), status().is4xxClientError()),
                testApiEndpoint("상품권 사용 실패 - 만료된 상품권", useGiftCard("G4UX-AKWG-LEUV-5NZ278"), status().is4xxClientError())
        );
    }

    private RequestBuilder useGiftCard(String code) {
        return post("/api/gift-card/use").param("code", code);
    }

    @TestFactory
    @DisplayName("상품권 발급 테스트")
    @Transactional
    Stream<DynamicTest> dynamicTestForIssueGiftCard() {
        return Stream.of(
                testApiEndpoint("상품권 발급 성공", issueGiftCard("10000"), status().isOk()),
                testApiEndpoint("상품권 발급 실패 - 최소 금액 미만", issueGiftCard("9999"), status().isBadRequest()),
                testApiEndpoint("상품권 발급 실패 - 최대 금액 초과", issueGiftCard("10000001"), status().isBadRequest())
        );
    }

    private RequestBuilder issueGiftCard(String value) {
        return post("/api/gift-card/issue").param("value", value);
    }

    @TestFactory
    @DisplayName("상품권 폐기 테스트")
    @Transactional
    Stream<DynamicTest> dynamicTestForDisposeGiftCard() {
        return Stream.of(
                testApiEndpoint("상품권 폐기 성공", disposeGiftCard("CLLW-2QQ4-QY4A-PWZ6W9"), status().isOk()),
                testApiEndpoint("상품권 폐기 실패 - 존재하지 않는 코드", disposeGiftCard("1111-2222-3333-444444"), status().isNotFound())
        );
    }

    private RequestBuilder disposeGiftCard(String code) {
        return delete("/api/gift-card/dispose").param("code", code);
    }

    /**
     * 지정된 API 엔드포인트에 대해 테스트를 수행하는 DynamicTest를 생성합니다.
     *
     * @param testName 테스트의 Display Name으로 사용될 이름
     * @param requestBuilder API 엔드포인트에 보낼 요청, HTTP Method, URL, 매개변수 또는 본문이 포함되어야 합니다.
     * @param expectedStatus API 엔드포인트의 응답으로 예상되는 HTTP 상태
     * @return DynamicTest
     */
    private DynamicTest testApiEndpoint(String testName, RequestBuilder requestBuilder, ResultMatcher expectedStatus) {
        return DynamicTest.dynamicTest(testName,
                () -> mockMvc.perform(requestBuilder)
                        .andExpect(expectedStatus));
    }

}
