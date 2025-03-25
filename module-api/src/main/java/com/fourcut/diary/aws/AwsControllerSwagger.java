package com.fourcut.diary.aws;

import com.fourcut.diary.aws.dto.request.PresignedUrlGetRequest;
import com.fourcut.diary.aws.dto.request.PresignedUrlPutRequest;
import com.fourcut.diary.aws.dto.response.PresignedUrlGetResponse;
import com.fourcut.diary.aws.dto.response.PresignedUrlPutResponse;
import com.fourcut.diary.config.resolver.UserAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AWS", description = "AWS 관련 API 명세서")
public interface AwsControllerSwagger {

    @Operation(summary = "S3 미리 서명된 URL 발급 - 이미지 업로드")
    ResponseEntity<PresignedUrlPutResponse> getPutPresignedUrl(
            @UserAuthentication String socialId,
            @RequestBody @Valid final PresignedUrlPutRequest request
    );

    @Operation(summary = "S3 미리 서명된 URL 발급 - 이미지 조회")
    ResponseEntity<PresignedUrlGetResponse> getGetPresignedUrl(
            @UserAuthentication String socialId,
            @RequestBody @Valid final PresignedUrlGetRequest request
    );
}
