package com.fourcut.diary.aws.dto.response;

import java.util.List;

public record PresignedUrlGetResponse(

        List<String> imageUrlList
) {
}
