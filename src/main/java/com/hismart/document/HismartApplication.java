package com.hismart.document;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author echo_
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class HismartApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HismartApplication.class)
                .run(args);
    }

}
