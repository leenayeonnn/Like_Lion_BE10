package org.example.filterexam.filterExam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class FilterExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilterExamApplication.class, args);
    }

}
