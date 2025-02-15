package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
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
                .credentialsProvider(awsConfig.systemPropertyCredentialsProviderForS3())
                .build();
    }
}
