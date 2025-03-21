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

    @Value("${aws.s3.bucket}")
    private String s3Bucket;
}
