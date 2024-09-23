package com.fourcut.diary.exception.model;

import com.fourcut.diary.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class FourCutDiaryException extends RuntimeException {

    private ErrorMessage errorMessage;

    public FourCutDiaryException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
