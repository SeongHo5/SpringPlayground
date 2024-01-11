package com.seongho.spring.contract.service;

import com.seongho.spring.common.exception.ValidationException;
import com.seongho.spring.contract.dto.ContractRequestDto;
import com.seongho.spring.contract.entity.Contract;
import com.seongho.spring.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.VALID_PERIOD_EXCEED_CONTRACT_DURATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public void issueContract(ContractRequestDto contractRequestDto) {
        validateContractDate(contractRequestDto);

        Contract contract = contractRequestDto.toEntity();

        contractRepository.save(contract);
    }

    private void validateContractDate(ContractRequestDto contractRequestDto) {
        LocalDate startDate = LocalDate.parse(contractRequestDto.startDate());
        LocalDate endDate = LocalDate.parse(contractRequestDto.endDate());
        int validPeriod = contractRequestDto.validPeriod();

        long contractDurationInMonths = ChronoUnit.MONTHS.between(startDate, endDate);


        if (validPeriod > contractDurationInMonths) {
            throw new ValidationException(VALID_PERIOD_EXCEED_CONTRACT_DURATION);
        }
    }

}
