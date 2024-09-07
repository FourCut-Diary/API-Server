package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.LambdaException;

@Service
@RequiredArgsConstructor
public class LambdaService {

    private final AwsConfig awsConfig;

    public void enrollFcmTokenInSNS(String fcmToken) {

        try (LambdaClient lambdaClient = createLambdaClient()) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("fcmToken", fcmToken);
            String json = jsonObj.toString();
            SdkBytes payload = SdkBytes.fromUtf8String(json);

            InvokeRequest request = InvokeRequest.builder()
                    .functionName("enrollUserDeviceTokenToSNS")
                    .payload(payload)
                    .build();

            lambdaClient.invoke(request);
        } catch (LambdaException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private LambdaClient createLambdaClient() {
        return LambdaClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.systemPropertyCredentialsProvider())
                .build();
    }
}
