package com.seongho.spring.factory;

import com.seongho.spring.contract.dto.ContractRequestDto;

public class ContractFactory {

    public static ContractRequestDto createIssueContractSuccessCase() {
        return new ContractRequestDto("계약 A", "2021-01-01", "2025-12-31", 24);
    }

    public static ContractRequestDto createIssueContractFailureCaseA() {
        return new ContractRequestDto("계약 B", "2024-01-01", "2025-12-31", 24);
    }
}
