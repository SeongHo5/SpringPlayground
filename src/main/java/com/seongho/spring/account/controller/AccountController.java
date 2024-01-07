package com.seongho.spring.account.controller;

import com.seongho.spring.account.dto.AccountDetails;
import com.seongho.spring.account.dto.AccountInfoDto;
import com.seongho.spring.account.dto.ModifyAccountInfoRequestDto;
import com.seongho.spring.account.dto.SignUpRequestDto;
import com.seongho.spring.account.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(final @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        accountService.createAccount(signUpRequestDto);
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/my-info")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<AccountInfoDto> getAccountInfo(
            final @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        return accountService.getAccountInfo(accountDetails);
    }

    /**
     * 내 정보 수정
     */
    @PatchMapping("/my-info/modify")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<AccountInfoDto> modifyAccount(
            final @AuthenticationPrincipal AccountDetails accountDetails,
            final @RequestBody ModifyAccountInfoRequestDto requestDto
    ) {
        return accountService.modifyAccount(accountDetails, requestDto);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/drop-out")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void dropOut(
            final @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        accountService.deleteAccount(accountDetails);
    }

    /**
     * 이메일 중복 체크
     */
    @GetMapping("/check-email")
    @ResponseStatus(HttpStatus.OK)
    public void checkEmailDuplicated(final @Email @RequestParam String email) {
        accountService.checkEmailDuplicated(email);
    }
}
