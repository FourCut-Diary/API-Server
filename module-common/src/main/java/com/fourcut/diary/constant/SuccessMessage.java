package com.fourcut.diary.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {

    ;

    final int status;
    final String message;
}
