package com.seongho.spring.giftcard.entity;

import com.seongho.spring.common.entity.BaseEntity;
import com.seongho.spring.giftcard.enums.GiftCardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gift_card", uniqueConstraints = {
        @UniqueConstraint(name = "uk_gift_card_code", columnNames = {"code"})
})
public class GiftCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 21, nullable = false)
    private String code;

    @Column(precision = 10, nullable = false)
    private BigDecimal value;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id")
//    private Account account;

    @Enumerated(EnumType.STRING)
    private GiftCardStatus status;

    private LocalDateTime usedAt;

    private LocalDateTime expiredAt;

    public void modifyStatus(GiftCardStatus status) {
        this.status = status;
        this.usedAt = LocalDateTime.now();
    }

}
