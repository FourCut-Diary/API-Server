package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AwsConfig awsConfig;

    public String createPutPresignedUrl(String keyName) {

        try (S3Presigner s3Presigner = createPresigner()) {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(awsConfig.getS3BucketName())
                    .key(keyName)
                    .build();

            PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(2))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);

            return presignedPutObjectRequest.url().toExternalForm();
        } catch (S3Exception exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    public List<String> createGetPresignedUrl(List<String> imageUrlList) {

        try (S3Presigner s3Presigner = createPresigner()) {
            return imageUrlList.stream()
                    .map(imageUrl -> {
                        String objectKey = extractObjectKey(imageUrl);
                        GetObjectRequest objectRequest = GetObjectRequest.builder()
                                .bucket(awsConfig.getS3BucketName())
                                .key(objectKey)
                                .build();
                        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                                .signatureDuration(Duration.ofMinutes(2))
                                .getObjectRequest(objectRequest)
                                .build();
                        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

                        return presignedGetObjectRequest.url().toExternalForm();
                    }).toList();
        } catch (S3Exception exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    /**
     * CDN URL 생성 (CloudFlare Worker 사용)
     * 클라이언트는 JWT 토큰으로 인증하여 이미지에 접근
     *
     * @param imageUrlList S3 원본 URL 리스트
     * @return CDN URL 리스트
     */
    public List<String> createCdnUrls(List<String> imageUrlList) {
        String cdnBaseUrl = awsConfig.getCdnBaseUrl();

        // CDN URL이 설정되지 않은 경우 기존 presigned URL 방식 사용 (하위 호환성)
        if (cdnBaseUrl == null || cdnBaseUrl.isBlank()) {
            return createGetPresignedUrl(imageUrlList);
        }

        return imageUrlList.stream()
                .map(imageUrl -> {
                    String objectKey = extractObjectKey(imageUrl);
                    return cdnBaseUrl + "/" + objectKey;
                })
                .toList();
    }

    public void checkImageUrlExists(String imageUrl) {

        try (S3Client s3Client = createS3Client()) {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(awsConfig.getS3BucketName())
                    .key(extractObjectKey(imageUrl))
                    .build();

            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            if (response != null) {
                return;
            }
        } catch (S3Exception exception) {
            if (exception.statusCode() == 404) {
                throw new BadRequestException(ErrorMessage.NOT_EXIST_IMAGE_URL);
            }
            log.error("S3 Exception: {}", exception.awsErrorDetails().errorMessage(), exception);
            throw new RuntimeException("S3 service error");
        }
    }

    private String extractObjectKey(String s3Url) {
        try {
            URI uri = new URI(s3Url);
            String path = uri.getPath();
            return path.startsWith("/") ? path.substring(1) : path;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("잘못된 S3 URL 형식입니다: " + s3Url, e);
        }
    }

    private S3Presigner createPresigner() {
        return S3Presigner.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.credentialsProviderForS3())
                .build();
    }

    private S3Client createS3Client() {
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.credentialsProviderForS3())
                .build();
    }
}
