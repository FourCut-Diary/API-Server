package com.fourcut.diary.aws;

import com.fourcut.diary.aws.dto.request.PresignedUrlGetRequest;
import com.fourcut.diary.aws.dto.request.PresignedUrlPutRequest;
import com.fourcut.diary.aws.dto.response.PresignedUrlGetResponse;
import com.fourcut.diary.aws.dto.response.PresignedUrlPutResponse;
import com.fourcut.diary.config.resolver.UserAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aws")
@RequiredArgsConstructor
public class AwsController implements AwsControllerSwagger {

    private final S3Service s3Service;

    @PostMapping("/presigned-url/put")
    public ResponseEntity<PresignedUrlPutResponse> getPutPresignedUrl(
            @UserAuthentication String socialId,
            @RequestBody @Valid PresignedUrlPutRequest request
    ) {

        PresignedUrlPutResponse response = new PresignedUrlPutResponse(s3Service.createPutPresignedUrl(request.fileName()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/presigned-url/get")
    public ResponseEntity<PresignedUrlGetResponse> getGetPresignedUrl(
            @UserAuthentication String socialId,
            @RequestBody @Valid PresignedUrlGetRequest request
    ) {

        PresignedUrlGetResponse response = new PresignedUrlGetResponse(s3Service.createGetPresignedUrl(request.imageUrlList()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
