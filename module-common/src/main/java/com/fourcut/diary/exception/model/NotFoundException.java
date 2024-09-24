package com.fourcut.diary.exception.model;

import com.fourcut.diary.constant.ErrorMessage;

public class NotFoundException extends FourCutDiaryException {

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
