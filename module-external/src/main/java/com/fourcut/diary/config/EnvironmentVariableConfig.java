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
}
