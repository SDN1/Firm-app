package com.mentormate.repository;

import com.mentormate.entity.Firm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirmRepository extends JpaRepository<Firm, Integer> {
	 public List<Firm> findAllByIsActiveTrue();
	 public List<Firm> findAllByIsActiveFalse();
	 public List<Firm> findAllByBalanceGreaterThan(Float money);
	 public List<Firm> findAllByBalanceLessThan(Float money);
	 public List<Firm> findByNameContainsAllIgnoreCase(String namePart);
	 public Firm findByBulstat(String bulstat);
}
