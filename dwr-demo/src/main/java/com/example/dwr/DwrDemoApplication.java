package com.example.dwr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class DwrDemoApplication {


    public static void main(String[] args) {

        SpringApplication.run(DwrDemoApplication.class, args);
    }

    @RequestMapping
    public String index() {

        return "index";
    }
    @RequestMapping("index2")
    public String index2() {

        return "index2";
    }
}
