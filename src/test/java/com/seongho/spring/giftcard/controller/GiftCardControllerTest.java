package com.seongho.spring.giftcard.controller;

import com.seongho.spring.helper.TestHelper;
import com.seongho.spring.account.dto.AccountDetails;
import com.seongho.spring.common.jwt.JwtProvider;
import com.seongho.spring.common.jwt.dto.JwtResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "admin@admin.com", userDetailsServiceBeanName = "accountDetailsServiceImpl")
class GiftCardControllerTest {

    @Autowired
    private TestHelper testHelper;

    @Autowired
    WebApplicationContext context;

    @Autowired
    JwtProvider jwtProvider;

    private JwtResponseDto jwtResponseDto;

    @BeforeEach
    void initJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AccountDetails accountDetails) {
            jwtResponseDto = jwtProvider.createJwtToken(accountDetails.getUsername());
        }
    }

    private DynamicTest testApiEndpoint(String testName, RequestBuilder requestBuilder, ResultMatcher expectedStatus) {
        return testHelper.testApiEndpoint(testName, requestBuilder, expectedStatus);
    }

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
        return post("/api/gift-card/use")
                .header(AUTHORIZATION, "Bearer " + jwtResponseDto.accessToken())
                .param("code", code);

    }

    @TestFactory
    @DisplayName("상품권 발행 테스트")
    @Transactional
    Stream<DynamicTest> dynamicTestForIssueGiftCard() {
        return Stream.of(
                testApiEndpoint("상품권 발행 성공", issueGiftCard(10000), status().isOk()),
                testApiEndpoint("상품권 발행 실패 - 최소 금액 미만", issueGiftCard(9999), status().isBadRequest()),
                testApiEndpoint("상품권 발행 실패 - 최대 금액 초과", issueGiftCard(10000001), status().isBadRequest())
        );
    }

    private RequestBuilder issueGiftCard(int value) {
        return post("/api/gift-card/issue")
                .header(AUTHORIZATION, "Bearer " + jwtResponseDto.accessToken())
                .param("value", String.valueOf(value));
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
        return delete("/api/gift-card/dispose")
                .header(AUTHORIZATION, "Bearer " + jwtResponseDto.accessToken())
                .param("code", code);
    }

}
