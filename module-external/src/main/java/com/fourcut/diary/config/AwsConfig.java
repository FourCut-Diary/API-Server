package com.fourcut.diary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final static String AWS_ACCESS_KEY_ID = "aws.accessKeyId";
    private final static String AWS_SECRET_ACCESS_KEY = "aws.secretAccessKey";

    private final EnvironmentVariableConfig environmentVariableConfig;

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return DefaultCredentialsProvider.create();
    }

    @Bean
    public StaticCredentialsProvider credentialsProviderForSNS() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                environmentVariableConfig.getSnsAccessKey(),
                environmentVariableConfig.getSnsSecretKey()
        ));
    }

    @Bean
    public StaticCredentialsProvider credentialsProviderForS3() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                environmentVariableConfig.getS3AccessKey(),
                environmentVariableConfig.getS3SecretKey()
        ));
    }

    @Bean
    public String getSnsPlatformApplicationArn() {
        return environmentVariableConfig.getSnsPlatformApplicationArn();
    }

    @Bean
    public String getS3BucketName() {
        return environmentVariableConfig.getS3Bucket();
    }
}
