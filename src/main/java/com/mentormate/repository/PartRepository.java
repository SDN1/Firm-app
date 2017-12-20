package com.mentormate.repository;

import com.mentormate.entity.Firm;
import com.mentormate.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {
	//@Query(value = "SELECT p FROM Part p WHERE p.person.personId = ?1")
	public List<Part> findAllByPerson_PersonId(Integer id);
	public List<Part> findAllByFirm_FirmId(Integer id);
}
