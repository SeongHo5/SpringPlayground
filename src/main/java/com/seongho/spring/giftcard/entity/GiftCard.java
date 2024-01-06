package com.seongho.spring.giftcard.entity;

import com.seongho.spring.common.entity.BaseEntity;
import com.seongho.spring.giftcard.enums.GiftCardStatus;
import com.seongho.spring.giftcard.event.GiftCardChangeEventListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

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
@EntityListeners(GiftCardChangeEventListener.class)
@SQLDelete(sql = "UPDATE gift_card SET status = 'DELETED' WHERE id = ?")
public class GiftCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 21, nullable = false)
    private String code;

    @Column(precision = 10, nullable = false)
    private BigDecimal value;

    @Column(precision = 10)
    private BigDecimal usedValue;

    @Enumerated(EnumType.STRING)
    private GiftCardStatus status;

    private LocalDateTime usedAt;

    private LocalDateTime expiredAt;

    public void modifyStatus(GiftCardStatus status) {
        this.status = status;
        this.usedAt = LocalDateTime.now();
    }


}
