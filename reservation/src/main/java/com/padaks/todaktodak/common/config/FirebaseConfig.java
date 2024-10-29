package com.padaks.todaktodak.common.config;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${fcm.secret-file}")
    private String secretFileName;
//    https://todak-1f8d0-default-rtdb.asia-southeast1.firebasedatabase.app
    @PostConstruct
    public void init() {
        if (FirebaseApp.getApps().isEmpty()) { // FirebaseApp이 초기화되지 않은 경우에만 초기화
            try {
                //firebase sdk 업데이트로 GoogleCredentials 생성하는 방법 변경 => createScoped 추가
                GoogleCredentials credentials = GoogleCredentials
                        .fromStream(new ClassPathResource(secretFileName).getInputStream())
                        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .setDatabaseUrl("https://todak-1f8d0-default-rtdb.asia-southeast1.firebasedatabase.app") // DatabaseURL 추가
                        .build();
                // FirebaseApp 초기화 한 번만 이루어지도록 수정
                if (FirebaseApp.getApps().isEmpty()){
                    FirebaseApp.initializeApp(options);
                }
                System.out.println("FCM SETTING SUCCESS");
            }catch (IOException e){
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize FCM: " + e.getMessage());
            }
        }
    }

    private String getSecretFromAWS(String secretFileName){
        log.info(secretFileName);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        log.info(client.toString());
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretFileName);
        log.info(getSecretValueRequest.getSecretId());
        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        log.info(getSecretValueResult.getSecretString());

        return getSecretValueResult.getSecretString();
    }
}
