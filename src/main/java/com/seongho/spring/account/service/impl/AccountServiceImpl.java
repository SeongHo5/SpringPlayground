package com.seongho.spring.account.service.impl;

import com.seongho.spring.account.dto.AccountDetails;
import com.seongho.spring.account.dto.AccountInfoDto;
import com.seongho.spring.account.dto.ModifyAccountInfoRequestDto;
import com.seongho.spring.account.dto.SignUpRequestDto;
import com.seongho.spring.account.entity.Account;
import com.seongho.spring.account.enums.ForbiddenUserName;
import com.seongho.spring.account.repository.AccountRepository;
import com.seongho.spring.account.service.AccountService;
import com.seongho.spring.common.exception.AuthException;
import com.seongho.spring.common.exception.DuplicatedException;
import com.seongho.spring.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createAccount(SignUpRequestDto signUpRequestDto) {
        String requestedEmail = signUpRequestDto.getEmail();
        String requestedUsername = signUpRequestDto.getName();

        checkUsernameIsProhibited(requestedUsername);
        checkEmailIsDuplicated(requestedEmail);

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        Account account = signUpRequestDto.toEntity(encodedPassword);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public ResponseEntity<AccountInfoDto> modifyAccount(
            AccountDetails accountDetails,
            ModifyAccountInfoRequestDto requestDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAccount(AccountDetails accountDetails) {
        accountRepository.delete(accountDetails.getAccount());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AccountInfoDto> getAccountInfo(AccountDetails accountDetails) {
        return null;
    }

    /**
     * 이메일로 사용자를 조회한다.
     *
     * @param email 사용자 이메일
     * @return 조회된 사용자
     */
    @Override
    public Account findAccountByEmail(final String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ACCOUNT));
    }

    @Override
    public void checkEmailDuplicated(String email) {
        checkEmailIsDuplicated(email);
    }

    // =============== PRIVATE METHODS =============== //

    /**
     * 회원 가입 시 이메일이 중복되는지 확인한다.
     */
    private void checkEmailIsDuplicated(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new DuplicatedException(CONFLICT_ACCOUNT);
        }
    }

    /**
     * 회원 가입 시 이름에 금지어가 포함되어 있는지 확인한다.
     *
     * @see ForbiddenUserName 금지어 목록
     */
    private void checkUsernameIsProhibited(String username) {
        boolean isProhibited = Arrays.stream(ForbiddenUserName.values())
                .anyMatch(forbiddenUserName -> username.contains(forbiddenUserName.getName()));

        if (isProhibited) {
            throw new AuthException(PROHIBITED_USERNAME);
        }
    }
}
