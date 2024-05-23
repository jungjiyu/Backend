package com.likelionkit.board.global.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ErrorResponse {//정해놓은 틀 같은거
    private HttpStatus status;
    private String message;

    public ErrorResponse(ErrorCode errorCode ){
        this.status = errorCode.getStatus();
        this.message= errorCode.getMessage();
    }

}
