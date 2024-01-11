package com.seongho.spring.contract.dto;

import com.seongho.spring.contract.entity.Contract;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

public record ContractRequestDto(
        String name,
        String startDate,
        String endDate,
        @Max(120) @Positive int validPeriod) {

    public Contract toEntity() {
        return Contract.builder()
                .name(name)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .validPeriod(validPeriod)
                .build();
    }

}

