package com.seongho.spring.auth.service;

import com.seongho.spring.auth.dto.SignInRequestDto;
import com.seongho.spring.auth.dto.SignInResponseDto;
import com.seongho.spring.common.jwt.dto.JwtReissueResponseDto;
import com.seongho.spring.common.jwt.dto.JwtRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<SignInResponseDto> signIn(final SignInRequestDto signInRequestDto);

    void signOut(final JwtRequestDto jwtRequestDto);

    ResponseEntity<JwtReissueResponseDto> reissue(final JwtRequestDto jwtRequestDto);


}
