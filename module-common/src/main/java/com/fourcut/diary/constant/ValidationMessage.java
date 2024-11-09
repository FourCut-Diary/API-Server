package com.fourcut.diary.constant;

public class ValidationMessage {

    public final static String NULL_SOCIAL_TYPE = "소셜 플랫폼 유형이 전달되지 않았습니다.";
    public final static String INVALID_AUTHORIZATION_CODE = "처리할 수 없는 인가 코드입니다.";
    public final static String INVALID_FCM_TOKEN = "처리할 수 없는 디바이스 토큰입니다.";

    // auth
    public final static String INVALID_REFRESH_TOKEN = "검증할 수 없는 토큰입니다.";

    // user
    public final static String INVALID_NICKNAME = "허용되지 않은 형식의 닉네임입니다.";
    public final static String NULL_BIRTHDAY = "생일이 입력되지 않았습니다.";
    public final static String NULL_GENDER = "성별이 입력되지 않았습니다.";
    public final static String NULL_DAILY_START_TIME = "활동 시작 시간이 입력되지 않았습니다.";
    public final static String NULL_DAILY_END_TIME = "활동 종료 시간이 입력되지 않았습니다.";
}
