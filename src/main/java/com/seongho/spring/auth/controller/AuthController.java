package com.seongho.spring.auth.controller;


import com.seongho.spring.auth.dto.SignInRequestDto;
import com.seongho.spring.auth.dto.SignInResponseDto;
import com.seongho.spring.auth.service.impl.AuthServiceImpl;
import com.seongho.spring.common.jwt.dto.JwtReissueResponseDto;
import com.seongho.spring.common.jwt.dto.JwtRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signIn(
            final @Valid @RequestBody SignInRequestDto signInRequestDto
    ) {
        return authService.signIn(signInRequestDto);
    }

    /**
     * 로그아웃
     */
    @DeleteMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public void signOut(
            final @RequestBody JwtRequestDto jwtRequestDto
    ) {
        authService.signOut(jwtRequestDto);
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/re-issue")
    public ResponseEntity<JwtReissueResponseDto> reissue(
            final @RequestBody JwtRequestDto jwtRequestDto
            ) {
        return authService.reissue(jwtRequestDto);
    }


}
