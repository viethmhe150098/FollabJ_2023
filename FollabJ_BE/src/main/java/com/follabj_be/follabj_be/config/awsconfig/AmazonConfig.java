package com.follabj_be.follabj_be.config.awsconfig;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AmazonConfig {
    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 s3config(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(System.getenv("S3_ACCESS_KEY"), System.getenv("S3_SECRET_KEY"));
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
