package com.fourcut.diary.aws;

import com.fourcut.diary.aws.dto.request.PresignedUrlRequest;
import com.fourcut.diary.aws.dto.response.PresingedUrlResponse;
import com.fourcut.diary.config.resolver.UserAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AWS", description = "AWS 관련 API 명세서")
public interface AwsControllerSwagger {

    @Operation(summary = "S3 미리 서명된 URL 발급")
    ResponseEntity<PresingedUrlResponse> getPresignedUrl(
            @UserAuthentication String socialId,
            @RequestBody @Valid final PresignedUrlRequest request
    );
}
