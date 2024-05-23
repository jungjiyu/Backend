package com.likelionkit.board.domain.user.controller;

import com.likelionkit.board.domain.user.dto.LoginRequest;
import com.likelionkit.board.domain.user.dto.LoginResponse;
import com.likelionkit.board.domain.user.dto.SignUpRequest;
import com.likelionkit.board.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid  SignUpRequest request){ // @Valid >>     @NotBlank(message = "사용자 이름은 빈값일 수 없습니다.") 관련

        // 실제 회원 가입 f로직 > 서비스
        log.info("signUp> "+request.toString());

        userService.signUp(request);
        return ResponseEntity.ok().build();


    }

    // dto 를 같이 사용하며 안된다. 나중에 필드ㅏ 추가될 수 있기 떄문
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){ // @Valid >>     @NotBlank(message = "사용자 이름은 빈값일 수 없습니다.") 관련

       LoginResponse response= userService.login(request);

        // 실제 회원 가입 f로직 > 서비스
        log.info("login> "+request.toString());

        return ResponseEntity.ok().body(response);


    }


}
