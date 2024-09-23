package com.fourcut.diary.dto;

import com.fourcut.diary.constant.SuccessMessage;

public record SuccessResponse<T>(
        int status,
        String message,
        T data
) {
    public static <T> SuccessResponse<T> of(final SuccessMessage successMessage, final T data) {
        return new SuccessResponse<>(successMessage.getStatus(), successMessage.getMessage(), data);
    }

    public static <T> SuccessResponse<T> of(final SuccessMessage successMessage) {
        return new SuccessResponse<>(successMessage.getStatus(), successMessage.getMessage(), null);
    }
}
