package com.seongho.spring.giftcard.entity;

import com.seongho.spring.common.entity.SecureBaseEntity;
import com.seongho.spring.giftcard.enums.GiftCardStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gift_card_history")
public class GiftCardHistory extends SecureBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_card_id")
    private GiftCard giftCard;

    private String description;
}
