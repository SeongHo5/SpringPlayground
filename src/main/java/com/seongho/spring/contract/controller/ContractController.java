package com.seongho.spring.contract.controller;

import com.seongho.spring.contract.dto.ContractRequestDto;
import com.seongho.spring.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/contract")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.CREATED)
    public void issueContract(
            final @RequestBody ContractRequestDto contractRequestDto
    ) {
        log.info("contractName: {}, startDate: {}, endDate: {}, validPeriod: {}",
                contractRequestDto.name(),
                contractRequestDto.startDate(),
                contractRequestDto.endDate(),
                contractRequestDto.validPeriod());
        contractService.issueContract(contractRequestDto);
    }


}
