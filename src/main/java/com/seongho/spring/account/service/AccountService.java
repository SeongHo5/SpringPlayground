package com.seongho.spring.account.service;

import com.seongho.spring.account.dto.AccountDetails;
import com.seongho.spring.account.dto.AccountInfoDto;
import com.seongho.spring.account.dto.ModifyAccountInfoRequestDto;
import com.seongho.spring.account.dto.SignUpRequestDto;
import com.seongho.spring.account.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AccountService {

    void createAccount(final SignUpRequestDto signUpRequestDto);

    ResponseEntity<AccountInfoDto> modifyAccount(
            final @AuthenticationPrincipal AccountDetails accountDetails,
            final ModifyAccountInfoRequestDto requestDto
    );

    void deleteAccount(final @AuthenticationPrincipal AccountDetails accountDetails);

    ResponseEntity<AccountInfoDto> getAccountInfo(final @AuthenticationPrincipal AccountDetails accountDetails);

    Account findAccountByEmail(final String email);

    void checkEmailDuplicated(final String email);

}
