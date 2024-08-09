package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.UserDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.entity.UserRole;
import com.sparta.msa_exam.auth.exception.LoginFailedException;
import com.sparta.msa_exam.auth.exception.SignUpFailedException;
import com.sparta.msa_exam.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long expiration;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       @Value("${service.jwt.secreat-key}") String secretKey) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // 토큰 생성
    public String createToken(UserDto userDto) {
        User user = userRepository.findByLoginId(userDto.getLogin_id()).orElseThrow(() -> new LoginFailedException("User not found"));

        return Jwts.builder()
                .claim("user_id", userDto.getLogin_id())
                .claim("role", user.getUserRole().name())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    // 회원 가입
    @Transactional
    public UserDto signUp(UserDto userDto) {
        Optional<User> findUser = userRepository.findByLoginId(userDto.getLogin_id());
        if (findUser.isPresent()) {
            throw new SignUpFailedException("User already exists");
        }

        User user;
        if (userDto.getUserRole().equals("USER")) {
            user = new User(userDto.getLogin_id(), passwordEncoder.encode(userDto.getPassword()), UserRole.USER);
        } else if(userDto.getUserRole().equals("ADMIN")){
            user = new User(userDto.getLogin_id(), passwordEncoder.encode(userDto.getPassword()), UserRole.ADMIN);
        } else{
            throw new SignUpFailedException("회원 가입이 정상적으로 처리되지 않았습니다.");
        }

        return UserDto.toDto(userRepository.save(user));
    }

    // 로그인
    public UserDto signIn(UserDto userDto) {
        User user = userRepository.findByLoginId(userDto.getLogin_id()).orElseThrow(() ->
                new LoginFailedException("User not found"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new LoginFailedException("ID or Password incorrect");
        }

        String token = createToken(userDto);
        return new UserDto(user.getId(), user.getLoginId(), userDto.getPassword(), user.getUserRole().name(), token);
    }
}
