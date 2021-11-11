package com.example.demo.modal;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface PaymentScheduleInterface extends JpaRepository<PaymentSchedule, Long> {
	
	List<PaymentSchedule> findByLoanId(Long id);

}
   