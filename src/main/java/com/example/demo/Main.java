package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // Este comando inicializa o servidor web embutido na porta 8080
        SpringApplication.run(Main.class, args);
    }
}