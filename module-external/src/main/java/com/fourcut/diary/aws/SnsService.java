package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SnsService {

    private final AwsConfig awsConfig;

    public void topicPublish(String endpoint) {

        log.info("푸시알림 발송: {}", awsConfig.getSnsTokenArn() + endpoint);
        try (SnsClient snsClient = createSnsClient()) {
            PublishRequest request = PublishRequest.builder()
                    .targetArn(awsConfig.getSnsTokenArn() + endpoint)
                    .message("send push notification")
                    .build();

            PublishResponse response = snsClient.publish(request);
            log.info(response.toString());
        } catch (SnsException exception) {
            log.error(exception.getMessage());
        }
    }

    private SnsClient createSnsClient() {
        return SnsClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.systemPropertyCredentialsProviderForSNS())
                .build();
    }

    private String createTakePictureMessage(Long userId, String fcmToken, int index) {
        return """
                {
                    "userId": "%s",
                    "fcmToken": "%s",
                    "title": "네 컷 일기",
                    "body": "지금, 오늘의 %d번째 사진을 찍어주세요!"
                }
                """.formatted(userId, fcmToken, index);
    }
}
