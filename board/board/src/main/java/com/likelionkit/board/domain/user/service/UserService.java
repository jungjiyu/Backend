package com.likelionkit.board.domain.user.service;


import com.likelionkit.board.domain.user.dto.LoginRequest;
import com.likelionkit.board.domain.user.dto.LoginResponse;
import com.likelionkit.board.domain.user.dto.SignUpRequest;
import com.likelionkit.board.domain.user.entity.User;
import com.likelionkit.board.domain.user.repository.UserRepository;

import com.likelionkit.board.global.base.exception.CustomException;
import com.likelionkit.board.global.base.exception.ErrorCode;
import com.likelionkit.board.global.jwt.TokenProvider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor // 알아서 final 붙은 거 autowired 해준다
@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

/*


EntityManager >> CRUD와 관련한 모든일 처리. JParepository 내부에서 일하고 있따
EntityManagerFactory 로 생성된다. 트랜잭션 실행할 떄 커넥션ㅇ르 획득한다

영속성 컨텍스트 : 엔티티 영구 저장환경
논리적인 개념이다.
id를 식별자로 사용한다.
em.persist(member);

만들기만함>>비영속
em.persist( ) 로 저장 >> 영속
detach( ) >>준영속
remove( ) >>삭제

flush( ) >> db 에 실제 저장
    : 보통 트랜잭션 커밋하는 순간

왜 굳이 이렇게 관리하는가
    1. 1차 캐시 > 영속성 컨텍스트에만 저장하고 db엔 반영안함 혹은 db에서 불러왔었던 것
        : find 를 하면 db에서 찾는게 아니라 1차캐시 부분에서 찾는다
    2.
    3. 쓰기 지연 > insert sql 문 저장한다.
        : .commit( ) 하면 db에 날라간다.
    4.

 트랜잭션 > 완료되어야 반영
 ACID
    원자성 > 하나ㅇ의 단위취급
    일관성 >
    격리성 >
    지속성 >

변경감지 >
엔티티의 수정 방법 > jpa 에서 수정할 때는 트랜잭션 시작하고 멤버 찾고 수정할거 수정하고 하는데 em.update( ) 는 별도로 필요하지 않다. 그러니까 조회에서 변경만 하면 된다 . dirtychecking
엔티티와 스냅샷을 보관한다 . 최초상태가 스냆샷이다. 스냅샷과 비교하여 변경사항이 있으면 그걸 update 해주고 flush 해준다.

기본전략은 특정 필드만 변경하는게 아니라 모든 필드를 업데이트해버린다.


flush
    1. 직접 em.flush
     2. commit
     3. jpql 쿼리

 */
    @Transactional
    public void signUp(SignUpRequest request) {
        // username 중복확인하기
       userRepository.findByUsername(request.getUsername())
               .ifPresent(it -> {
                   throw new CustomException(ErrorCode.DUPLUCATED_USERNAME);
               }); //.ifPresent(() -> throw new RuntimeException("username 이 중복"))

      User newUser= SignUpRequest.toEntity(request.getUsername(), passwordEncoder.encode(request.getPassword()));
      User savedUser = userRepository.save(newUser);// 실제 db 저장


    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        //db 에 로그인 요청이 들어오면 해당 회원이 존재하는지 검ㅁ사
        User savedUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new CustomException(ErrorCode.NO_USERNAME)); // 존재하지 않는 회원

        // 비밀번호검사
        if(!passwordEncoder.matches(request.getPassword(), savedUser.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        // 토큰 발급
        String token = tokenProvider.createToken(savedUser.getUsername(),savedUser.getRole());
        return new LoginResponse(token);
    }
}

