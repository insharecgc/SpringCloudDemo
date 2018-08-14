package com.inshare.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 * author: Inshare
 * date: 2018-8-14
 */
@RestController
public class SomeController {

    @Value("${my.message}")
    private String message;

    @GetMapping("/getsome")
    public String getSome() {
        return message;
    }
}
