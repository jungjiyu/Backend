package com.likelionkit.board.global.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLUCATED_USERNAME(HttpStatus.CONFLICT, "username이 중복되었습니다"),
    NO_USERNAME(HttpStatus.NOT_FOUND, "username이 존재하지 않습니다"),
    NOT_VALID_USERNAME(HttpStatus.BAD_REQUEST, "username이 틀렸습니다.");
    private HttpStatus status;
    private String message;
}
