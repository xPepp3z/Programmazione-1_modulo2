package com.example.operazione_typerequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"Controller", "Services"})
public class OperazioneTypeRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperazioneTypeRequestApplication.class, args);
    }

}
