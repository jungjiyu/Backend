package com.likelionkit.board.global.base;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@MappedSuperclass// 이걸 붙이면 baseentity 가 테이블을 생성하지 않고 상속 가능하게 한다. 상속을 공유하게 한다. 근데 자동으로 업데이트가 되진 않는다
@EntityListeners(AuditingEntityListener.class) // 얘가 업뎃 해줌. JpaCOnfig 에서 그리고  @EnableJpaAuditing 추가적으로 해줘야됨
@Getter
@NoArgsConstructor
public abstract class BaseEntity { // 중복되는 얘들을 가지는 엔티티
    // 별도로 @Entity 를 붙이지 않는다.

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
