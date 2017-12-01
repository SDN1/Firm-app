package com.mentormate.repository;

import com.mentormate.entity.Firm;
import com.mentormate.entity.Part;
import com.mentormate.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
