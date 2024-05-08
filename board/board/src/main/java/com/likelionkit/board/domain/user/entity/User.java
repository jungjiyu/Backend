package com.likelionkit.board.domain.user.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 대부분 identity 쓴다
    private long id;

    @Column(unique = true) // 겹치지 않게 해준다
    private String username;

    @Column
    private String password;


}
