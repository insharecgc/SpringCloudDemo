package com.inshare.cloud.controller;

import com.inshare.cloud.domain.Person;
import com.inshare.cloud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:
 * author: Inshare
 * date: 2018-8-14
 */
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/save")
    public List<Person> savePerson(@RequestBody String name) {
        Person p = new Person(name);
        personRepository.save(p);
        return personRepository.findAll(PageRequest.of(0, 10)).getContent();
    }
}
