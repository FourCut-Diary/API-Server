package com.fourcut.diary.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EnvironmentVariableConfig {

    @Value("${aws.sns.access-key}")
    private String snsAccessKey;

    @Value("${aws.sns.secret-key}")
    private String snsSecretKey;

    @Value("${aws.sns.platform-application-arn}")
    private String snsPlatformApplicationArn;

    @Value("${aws.s3.credentials.access-key}")
    private String s3AccessKey;

    @Value("${aws.s3.credentials.secret-key}")
    private String s3SecretKey;

    @Value("${aws.event-bridge.credentials.access-key}")
    private String eventBridgeAccessKey;

    @Value("${aws.event-bridge.credentials.secret-key}")
    private String eventBridgeSecretKey;

    @Value("${aws.event-bridge.role-arn}")
    private String eventBridgeRoleArn;

    @Value("${aws.s3.bucket}")
    private String s3Bucket;

    @Value("${aws.lambda.push-lambda-arn}")
    private String pushLambdaArn;
}
