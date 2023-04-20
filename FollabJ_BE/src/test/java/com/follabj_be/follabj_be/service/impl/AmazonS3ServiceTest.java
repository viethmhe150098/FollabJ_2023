package com.follabj_be.follabj_be.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AmazonS3Service")
class AmazonS3ServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private AmazonS3Service amazonS3Service;


    @Test
    @DisplayName("Should throw an exception when the path is invalid")
    void downloadWhenPathIsInvalidThenThrowException() {
        String invalidPath = "";
        String fileName = "test.txt";

        when(amazonS3.getObject(invalidPath, fileName)).thenThrow(IllegalArgumentException.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> amazonS3Service.download(invalidPath, fileName));

        verify(amazonS3, times(1)).getObject(invalidPath, fileName);
    }

    @Test
    @DisplayName("Should download the file successfully when the path and fileName are valid")
    void downloadWhenPathAndFileNameAreValid() {
        String path = "test-path";
        String fileName = "test-file.txt";
        S3Object expectedS3Object = new S3Object();
        expectedS3Object.setBucketName(path);
        expectedS3Object.setKey(fileName);

        when(amazonS3.getObject(path, fileName)).thenReturn(expectedS3Object);

        S3Object actualS3Object = amazonS3Service.download(path, fileName);

        assertEquals(expectedS3Object, actualS3Object);
        verify(amazonS3, times(1)).getObject(path, fileName);
    }



    @Test
    @DisplayName("Should upload the file with metadata when metadata is provided")
    void uploadFileWithMetadataWhenMetadataIsProvided() {
        String path = "test-path";
        String fileName = "test-file.txt";
        Map<String, String> metaData = new HashMap<>();
        metaData.put("key1", "value1");
        metaData.put("key2", "value2");
        Optional<Map<String, String>> optionalMetaData = Optional.of(metaData);
        InputStream inputStream = new ByteArrayInputStream("test-content".getBytes());
        PutObjectResult putObjectResult = new PutObjectResult();

        when(amazonS3.putObject(eq(path), eq(fileName), eq(inputStream), any()))
                .thenReturn(putObjectResult);

        PutObjectResult result =
                amazonS3Service.upload(path, fileName, optionalMetaData, inputStream);

        assertEquals(putObjectResult, result);
        verify(amazonS3, times(1)).putObject(eq(path), eq(fileName), eq(inputStream), any());
    }
}