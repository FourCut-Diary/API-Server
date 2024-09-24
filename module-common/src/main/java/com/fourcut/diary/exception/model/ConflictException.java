package com.fourcut.diary.exception.model;

import com.fourcut.diary.constant.ErrorMessage;

public class ConflictException extends FourCutDiaryException {

    public ConflictException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
