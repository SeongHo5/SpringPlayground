package com.seongho.spring.account.service.impl;


import com.seongho.spring.account.dto.AccountDetails;
import com.seongho.spring.account.entity.Account;
import com.seongho.spring.account.repository.AccountRepository;
import com.seongho.spring.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.NOT_FOUND_ACCOUNT;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ACCOUNT));
        return new AccountDetails(account);
    }
}
