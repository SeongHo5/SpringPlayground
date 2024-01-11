package com.seongho.spring.contract.controller;

import com.seongho.spring.helper.TestHelper;
import com.seongho.spring.account.dto.AccountDetails;
import com.seongho.spring.common.jwt.JwtProvider;
import com.seongho.spring.common.jwt.dto.JwtResponseDto;
import com.seongho.spring.contract.dto.ContractRequestDto;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static com.seongho.spring.factory.ContractFactory.createIssueContractFailureCaseA;
import static com.seongho.spring.factory.ContractFactory.createIssueContractSuccessCase;
import static net.minidev.json.JSONValue.toJSONString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "admin@admin.com", userDetailsServiceBeanName = "accountDetailsServiceImpl")
class ContractControllerTest {

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
    @DisplayName("계약 체결 테스트")
    Stream<DynamicTest> dynamicTestForIssueContract() {

        return Stream.of(
                testApiEndpoint("계약 체결 성공", issueContract(createIssueContractSuccessCase()), status().isCreated()),
                testApiEndpoint("계약 체결 실패 - 상품권 유효기간 총 계약 기간보다 큼", issueContract(createIssueContractFailureCaseA()), status().isBadRequest())
        );
    }

    private RequestBuilder issueContract(ContractRequestDto contractRequestDto) {
        return post("/api/contract/issue")
                .header(AUTHORIZATION, "Bearer " + jwtResponseDto.accessToken())
                .contentType(APPLICATION_JSON)
                .content(toJSONString(contractRequestDto));
    }
}
