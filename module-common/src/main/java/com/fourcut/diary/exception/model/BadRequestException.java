package com.fourcut.diary.exception.model;

import com.fourcut.diary.constant.ErrorMessage;

public class BadRequestException extends FourCutDiaryException {

    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
