package com.inshare.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/setsome")
    public String setSome(@RequestBody String msg) {
        message = msg;
        return "msg修改成功";
    }
}
