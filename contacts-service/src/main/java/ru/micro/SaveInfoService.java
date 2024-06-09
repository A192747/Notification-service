package ru.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SaveInfoService {
    public static void main(String[] args) {
        SpringApplication.run(SaveInfoService.class, args);
    }
}
