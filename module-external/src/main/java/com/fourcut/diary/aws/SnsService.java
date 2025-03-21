package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreatePlatformEndpointRequest;
import software.amazon.awssdk.services.sns.model.CreatePlatformEndpointResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class SnsService {

    private final AwsConfig awsConfig;

    public String createEndpoint(String fcmToken) {
        try (SnsClient snsClient = createSnsClient()) {
            CreatePlatformEndpointRequest request = CreatePlatformEndpointRequest.builder()
                    .platformApplicationArn(awsConfig.getSnsPlatformApplicationArn())
                    .token(fcmToken)
                    .build();

            CreatePlatformEndpointResponse response = snsClient.createPlatformEndpoint(request);
            return response.endpointArn();
        } catch (Exception e) {
            log.error("Failed to create SNS endpoint", e);
            throw e;
        }
    }

    private SnsClient createSnsClient() {
        return SnsClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.credentialsProviderForSNS())
                .build();
    }
}
