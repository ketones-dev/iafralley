package com.cdac.iafralley.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.iafralley.entity.RalleyDetails;

@Repository
public interface RalleyDetailsDAO extends JpaRepository<RalleyDetails, Long> {
	
	

}
