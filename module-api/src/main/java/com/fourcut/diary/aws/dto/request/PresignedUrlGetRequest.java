package com.fourcut.diary.aws.dto.request;

import java.util.List;

public record PresignedUrlGetRequest(

        List<String> imageUrlList
) {
}
