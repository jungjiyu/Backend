package com.likelionkit.board.domain.user.repository;

import com.likelionkit.board.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // ?
public interface UserRepository extends JpaRepository<User,Long> { // 인터페이스 형식으로 추상화된거라 직접 엔티티메니져같은걸 우리가 사용안해도된다.

    public abstract Optional<User> findByUsername(String username);
}
