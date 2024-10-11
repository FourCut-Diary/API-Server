package com.fourcut.diary.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EnvironmentVariableConfig {

    @Value("${aws.lambda.access-key}")
    private String lambdaAccessKey;

    @Value("${aws.lambda.secret-key}")
    private String lambdaSecretKey;

    @Value("${aws.lambda.region}")
    private String lambdaRegion;

    @Value("${aws.sns.access-key}")
    private String snsAccessKey;

    @Value("${aws.sns.secret-key}")
    private String snsSecretKey;

    @Value("${aws.sns.platform-application-arn}")
    private String snsPlatformApplicationArn;

    @Value("${aws.sns.token-arn}")
    private String snsTokenArn;
}
