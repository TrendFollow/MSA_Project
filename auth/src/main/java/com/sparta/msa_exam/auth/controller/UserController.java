package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.UserDto;
import com.sparta.msa_exam.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Value("${server.port}")
    private String port;

    private final UserService userService;

    // 회원 가입
    @PostMapping("/auth/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto) {
        log.info("회원 가입 시도 중 : {}", userDto.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", port);

        UserDto resultDto = userService.signUp(userDto);
        log.info("회원 가입 성공 : {}", resultDto.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resultDto);
    }

    // 로그인
    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserDto userDto) {
        log.info("로그인 시도 중 : {}", userDto.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", port);

        UserDto resultDto = userService.signIn(userDto);
        log.info("로그인 성공 : {}", resultDto.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resultDto);
    }

}
