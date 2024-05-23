package com.likelionkit.board.domain.user.dto;


import com.likelionkit.board.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
public class SignUpRequest {
// user 를 받는게 아니라 dto 받는 걸 받아줘서 원하는 값만 와리가리 가능

    @NotBlank(message = "사용자 이름은 빈값일 수 없습니다.")
    private String username;

    @NotBlank(message = "사용자 이름은 빈값일 수 없습니다.") // 빈 값 넘어올 경우
    @Size(min=4, max=12, message="비밀번호는 4자리 이상 12자리 이하여야 합니다") // 사이즈도 결정 가능
    private String password;

    // role 과 id 는 따로 받지 않는다.


    public static User toEntity(String username , String password){
// new User(request.getUsername() , request.getPassword());
        //빌 더 패턴을 활용하여 생성자를 대체. 더 읽기 쉬움
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
