package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.model.LoanList;

@RepositoryRestResource
public interface LoanListInterface extends JpaRepository<LoanList, Long>{

	List<LoanList> findByCustomerId(Long customerId);
	
}
