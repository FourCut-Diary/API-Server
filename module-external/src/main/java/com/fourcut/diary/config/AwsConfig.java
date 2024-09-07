package com.fourcut.diary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final static String AWS_ACCESS_KEY_ID = "aws.accessKeyId";
    private final static String AWS_SECRET_ACCESS_KEY = "aws.secretAccessKey";

    private final EnvironmentVariableConfig environmentVariableConfig;

    @Bean
    public SystemPropertyCredentialsProvider systemPropertyCredentialsProvider() {
        System.setProperty(AWS_ACCESS_KEY_ID, environmentVariableConfig.getLambdaAccessKey());
        System.setProperty(AWS_SECRET_ACCESS_KEY, environmentVariableConfig.getLambdaSecretKey());
        return SystemPropertyCredentialsProvider.create();
    }
}
