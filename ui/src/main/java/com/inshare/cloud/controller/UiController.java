package com.inshare.cloud.controller;

import com.inshare.cloud.domain.Person;
import com.inshare.cloud.service.PersonHystrixService;
import com.inshare.cloud.service.SomeHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description:
 * author: Inshare
 * date: 2018-8-14
 */
@RestController
public class UiController {

    @Autowired
    PersonHystrixService personHystrixService;

    @Autowired
    SomeHystrixService someHystrixService;

    @PostMapping(value = "/save")
    public List<Person> savePerson(@RequestBody String name) {
        return personHystrixService.save(name);
    }

    @GetMapping(value = "/getsome", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String getSome() {
        return someHystrixService.getSome();
    }

    @PostMapping(value = "/setsome")
    public String setSome(@RequestBody String msg) {
        return someHystrixService.setSome(msg);
    }
}
