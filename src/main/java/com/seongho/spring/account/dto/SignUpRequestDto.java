package com.seongho.spring.account.dto;

import com.seongho.spring.account.entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import static com.seongho.spring.account.enums.UserRole.ROLE_USER;
import static com.seongho.spring.account.enums.UserStatus.ACTIVE;

@Value
@NoArgsConstructor(force = true)
public class SignUpRequestDto {

    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글만 가능합니다.")
    String name;

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$",
            message = "비밀번호는 8자에서 20자 사이이며, 특수문자를 포함해야 합니다.")
    String password;

    String contact;



    public Account toEntity(final String encodedPassword) {
        return Account.builder()
                .name(this.getName())
                .email(this.getEmail())
                .password(encodedPassword)
                .contact(this.getContact())
                .userStatus(ACTIVE)
                .userRole(ROLE_USER)
                .build();
    }

}
