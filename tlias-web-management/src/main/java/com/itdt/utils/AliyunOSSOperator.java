package com.itdt.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;


@Component
public class AliyunOSSOperator {

    //通过value注解，一个一个属性注入
    //@Value("${aliyun.oss.endpoint}")
    //private String endpoint;
    //@Value("${aliyun.oss.bucketName}")
    //private String bucketName;
    //@Value("${aliyun.oss.region}")
    //private String region;
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;


    public String upload(byte[] bytes, @Nullable String originalFilename) throws com.aliyuncs.exceptions.ClientException {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();
        // 1. 判空
        if (bytes == null || bytes.length == 0 || originalFilename == null || originalFilename.isEmpty()) {
            return null;
        }

        // 2. 截取文件后缀 如 .png .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 3. UUID生成唯一文件名，防止同名覆盖
        String objectName = UUID.randomUUID() + suffix;

        // 4. 创建凭证
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        try {
            // 字节数组转输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            ossClient.putObject(putObjectRequest);
            // 拼接访问地址
            return "https://" + bucketName + "." + endpoint.replace("https://", "") + "/" + objectName;
        } catch (OSSException oe) {
            oe.printStackTrace();
            return null;
        } catch (ClientException ce) {
            ce.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}