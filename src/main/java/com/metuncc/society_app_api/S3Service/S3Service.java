package com.metuncc.society_app_api.S3Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    public S3Service(@Value("${aws.accessKeyId}") String accessKey,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.s3.region}") String region,
                     @Value("${aws.s3.bucket-name}") String bucketName) {
        this.bucketName = bucketName;
        this.region = region;

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String uploadFile(MultipartFile file) {
        // Convert the file to a temporary area
        File convertedFile = convertMultipartFileToFile(file);

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            // Upload the file to S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(convertedFile));

            // Return the URL of the uploaded file
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + uniqueFileName;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while uploading the file: " + e.getMessage());
        } finally {
            // Delete the temporary file
            if (convertedFile.exists()) {
                try {
                    Files.delete(convertedFile.toPath());
                } catch (IOException e) {
                    System.err.println("An error occurred while deleting the temporary file: " + e.getMessage());
                }
            }
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("File conversion error: " + e.getMessage());
        }
        return convertedFile;
    }
}
