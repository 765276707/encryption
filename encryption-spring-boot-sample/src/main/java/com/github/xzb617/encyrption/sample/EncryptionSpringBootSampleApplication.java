package com.github.xzb617.encyrption.sample;

import com.github.xzb617.encryption.autoconfigure.annotation.EnableEncryption;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryption
@SpringBootApplication
public class EncryptionSpringBootSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptionSpringBootSampleApplication.class, args);
    }

}

