package com.cdac.iafralley.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.iafralley.entity.RalleyCandidateDetails;


public interface RalleyCandidateDetailsDAO extends JpaRepository<RalleyCandidateDetails, Long> {

	public RalleyCandidateDetails findByEmailid(String email);
	
	@Query("select max(SUBSTRING(a.ralleyregistrationNo,10,5)) from RalleyCandidateDetails a")
	public String maxCount();
	
	

}
