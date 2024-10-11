package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.LambdaException;

@Service
@RequiredArgsConstructor
public class LambdaService {

    private final AwsConfig awsConfig;

    public void enrollFcmTokenInSNS(String fcmToken) {

        try (LambdaClient lambdaClient = createLambdaClient()) {
        } catch (LambdaException exception) {
        }
    }

    private LambdaClient createLambdaClient() {
        return LambdaClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.systemPropertyCredentialsProviderForLambda())
                .build();
    }
}
