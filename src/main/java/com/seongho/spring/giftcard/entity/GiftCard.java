package com.seongho.spring.giftcard.entity;

import com.seongho.spring.common.entity.BaseEntity;
import com.seongho.spring.contract.entity.Contract;
import com.seongho.spring.giftcard.entity.listener.GiftCardChangeListener;
import com.seongho.spring.giftcard.enums.GiftCardStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gift_card", uniqueConstraints = {
        @UniqueConstraint(name = "uk_gift_card_code", columnNames = {"code"})
})
@EntityListeners(GiftCardChangeListener.class)
@SQLDelete(sql = "UPDATE gift_card SET status = 'DISPOSED' WHERE id = ?")
public class GiftCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 21, nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(precision = 10, nullable = false)
    private BigDecimal value;

    @Column(precision = 10)
    private BigDecimal usedValue;

    @Enumerated(EnumType.STRING)
    private GiftCardStatus status;

    private LocalDateTime usedAt;

    private LocalDate validUntil;


    public void modifyStatus(GiftCardStatus status) {
        this.status = status;
        this.usedAt = LocalDateTime.now();
    }


}
