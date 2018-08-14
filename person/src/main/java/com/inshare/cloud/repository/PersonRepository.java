package com.inshare.cloud.repository;

import com.inshare.cloud.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description:
 * author: Inshare
 * date: 2018-8-14
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
