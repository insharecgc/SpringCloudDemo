package com.inshare.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    // 使用断路器，调用失败时，调用后备的 fallbackGetSome 方法
    @HystrixCommand(fallbackMethod = "fallbackGetSome")
    public String getSome() {
        return restTemplate.getForObject("http://some/getsome", String.class);
    }
    public String fallbackGetSome() {
        return "Some Service 模块故障";
    }

    // 使用断路器，调用失败时，调用后备的 fallbackSome 方法
    @HystrixCommand(fallbackMethod = "fallbackSetSome")
    public String setSome(String msg) {
        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> httpEntity = new HttpEntity<>(msg, headers);
        return restTemplate.postForObject("http://some/setsome", msg, String.class);
    }
    public String fallbackSetSome(String msg) {
        return "设置：'"+msg+"' 失败，Some Service 模块故障";
    }
}
