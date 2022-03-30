package com.zyaud.idata.iam.application;

import com.zyaud.fzhx.iam.client.EnableFzhxIamClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableFzhxIamClient
@EnableFeignClients(value = "com.zyaud.*.**")
@SpringBootApplication
@EnableAsync
@ComponentScan(value = {"com.zyaud.*.**","com.bjsasc.drap.*.**"})
public class IamMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(IamMainApplication.class, args);
    }


}
