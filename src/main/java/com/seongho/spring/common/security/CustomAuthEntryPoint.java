package com.seongho.spring.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.seongho.spring.common.log.PlaygroundLogger.logUnAuthorizedRequest;


@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        logUnAuthorizedRequest(request);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();

        responseJson.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        responseJson.put("message", "인증이 필요한 서비스입니다.");

        response.getWriter().print(responseJson);
    }
}
