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
    public SystemPropertyCredentialsProvider systemPropertyCredentialsProviderForLambda() {
        System.setProperty(AWS_ACCESS_KEY_ID, environmentVariableConfig.getLambdaAccessKey());
        System.setProperty(AWS_SECRET_ACCESS_KEY, environmentVariableConfig.getLambdaSecretKey());
        return SystemPropertyCredentialsProvider.create();
    }

    @Bean
    public SystemPropertyCredentialsProvider systemPropertyCredentialsProviderForSNS() {
        System.setProperty(AWS_ACCESS_KEY_ID, environmentVariableConfig.getSnsAccessKey());
        System.setProperty(AWS_SECRET_ACCESS_KEY, environmentVariableConfig.getSnsSecretKey());
        return SystemPropertyCredentialsProvider.create();
    }

    @Bean
    public String getSnsPlatformApplicationArn() {
        return environmentVariableConfig.getSnsPlatformApplicationArn();
    }

    @Bean
    public String getSnsTokenArn() {
        return environmentVariableConfig.getSnsTokenArn();
    }
}
