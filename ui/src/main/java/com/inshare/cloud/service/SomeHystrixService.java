package com.inshare.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * description:
 * author: Inshare
 * date: 2018-8-14
 */
@Service
public class SomeHystrixService {

    // 使用 Ribbon 模块调用通信
    @Autowired
    RestTemplate restTemplate;

    // 使用断路器，调用失败时，调用后备的 fallbackSome 方法
    @HystrixCommand(fallbackMethod = "fallbackSome")
    public String getSome() {
        return restTemplate.getForObject("http://some/getsome", String.class);
    }

    public String fallbackSome() {
        return "Some Service 模块故障";
    }
}
