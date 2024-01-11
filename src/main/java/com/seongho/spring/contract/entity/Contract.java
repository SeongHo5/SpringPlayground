package com.seongho.spring.contract.entity;

import com.seongho.spring.common.entity.SecureBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contract")
public class Contract extends SecureBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Comment("계약 시작 기간")
    private LocalDate startDate;

    @Column(nullable = false)
    @Comment("계약 종료 기간")
    private LocalDate endDate;

    @Column
    @Positive @Max(120)
    @Comment("계약별 상품권 유효 기간(월)")
    private int validPeriod;

}
