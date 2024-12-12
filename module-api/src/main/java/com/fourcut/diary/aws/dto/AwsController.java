package com.fourcut.diary.aws.dto;

import com.fourcut.diary.aws.AwsControllerSwagger;
import com.fourcut.diary.aws.S3Service;
import com.fourcut.diary.aws.dto.request.PresignedUrlRequest;
import com.fourcut.diary.aws.dto.response.PresingedUrlResponse;
import com.fourcut.diary.config.resolver.UserAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
@RequiredArgsConstructor
public class AwsController implements AwsControllerSwagger {

    private final S3Service s3Service;

    @GetMapping("/presigned-url")
    public ResponseEntity<PresingedUrlResponse> getPresignedUrl(
            @UserAuthentication String socialId,
            @RequestBody @Valid PresignedUrlRequest request
    ) {

        PresingedUrlResponse response = new PresingedUrlResponse(s3Service.createPresignedUrl(request.fileName()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
