package com.fourcut.diary.exception.model;

import com.fourcut.diary.constant.ErrorMessage;

public class UnauthorizedException extends FourCutDiaryException {

    public UnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
