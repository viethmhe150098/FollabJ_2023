package com.follabj_be.follabj_be.service;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface AmazonS3Interface {
    PutObjectResult upload(
            String path,
            String fileName,
            Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream);

    S3Object download(String path, String fileName);
}