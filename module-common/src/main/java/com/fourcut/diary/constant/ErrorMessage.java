package com.fourcut.diary.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    /*
    logic message
     */
    // common
    INVALID_METHOD_ARGUMENT(HttpStatus.BAD_REQUEST.value(), "API 요청에 필요한 인자가 누락되었습니다."),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST.value(), "request body 가 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "허용되지 않은 http method 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 서버 에러입니다."),

    // user
    ALREADY_ENROLL_USER(HttpStatus.CONFLICT.value(), "이미 등록된 사용자입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다."),

    // notification_time
    NOT_FOUND_NOTIFICATION_TIME(HttpStatus.NOT_FOUND.value(), "해당 유저의 알림 시간이 존재하지 않습니다."),

    // external
    INVALID_EXTERNAL_API_DATA(HttpStatus.BAD_REQUEST.value(), "외부 API 통신에 잘못된 값이 전달됐습니다.")
    ;

    final int status;
    final String message;
}
