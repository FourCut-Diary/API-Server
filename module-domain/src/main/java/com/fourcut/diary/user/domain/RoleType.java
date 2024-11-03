package com.fourcut.diary.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

    ADMIN("관리자"),
    USER("사용자")
    ;

    private final String title;
}
