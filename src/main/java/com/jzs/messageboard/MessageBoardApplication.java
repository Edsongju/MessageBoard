package com.jzs.messageboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jzs.messageboard.mapper")
public class MessageBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageBoardApplication.class, args);
    }
}
