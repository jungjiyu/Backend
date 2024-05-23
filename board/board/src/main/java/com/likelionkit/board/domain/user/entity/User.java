package com.likelionkit.board.domain.user.entity;

import com.likelionkit.board.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 대부분 identity 쓴다
    private long id;

    //nullable = false : 비면 오류난다
    @Column(unique = true, nullable = false) // 겹치지 않게 해준다
    private String username;

    
    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.ROLE_USER;
    }
}
