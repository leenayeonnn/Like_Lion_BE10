package org.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@SpringBootApplication
public class TodoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

//    @Value("${DB_USERNAME}")
//    private String dbUsername;
//    @Value("${DB_PASSWORD}")
//    private String dbPassword;
//    @Bean
//    public CommandLineRunner run(){
//        return args -> {
//            System.out.println("DB_USERNAME"+dbUsername);
//            System.out.println("DB_PASSWORD"+dbPassword);
//        };
//    }

    @Autowired
    private S3Client s3Client;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            SsmClient ssmClient = SsmClient.builder()
                    .region(Region.of(String.valueOf(Region.AP_NORTHEAST_2)))
                    .build();

            System.out.println("todo_DB_USERNAME ::: " + getParameterValue(ssmClient, "/todo/config/DB_USERNAME"));
            System.out.println("todo_DB_PASSWORD ::: " + getParameterValue(ssmClient, "/todo/config/DB_PASSWORD"));

        };
    }

    private String getParameterValue(SsmClient ssmClient, String parameterName) {
        GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(parameterName)
                .withDecryption(true)
                .build();

        GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
        return parameterResponse.parameter().value();
    }
}
