package com.seongho.spring.account.dto;

import com.seongho.spring.account.entity.Account;
import lombok.Value;

@Value
public class AccountInfoDto {

    String name;
    String email;
    String contact;

    public AccountInfoDto(Account account) {
        this.name = account.getName();
        this.email = account.getEmail();
        this.contact = account.getContact();
    }

}
