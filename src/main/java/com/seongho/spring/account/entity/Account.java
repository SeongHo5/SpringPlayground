package com.seongho.spring.account.entity;

import com.seongho.spring.account.enums.UserRole;
import com.seongho.spring.account.enums.UserStatus;
import com.seongho.spring.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(name = "uk_account_email", columnNames = {"email"})
})
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() WHERE id = ?")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    private String email;

    private String password;

    private String contact;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private LocalDateTime deletedAt;

    public Account updatePassword(String encodePassword) {
        this.password = encodePassword;
        return this;
    }

    public void updateContact(String contact) {
        this.contact = contact;
    }

    public void updateAccountRole(UserRole userRole) {
        this.userRole = userRole;
    }


}
