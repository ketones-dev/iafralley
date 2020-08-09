package com.cdac.iafralley.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.iafralley.entity.RalleyDetails;

@Repository
public interface RalleyDetailsDAO extends JpaRepository<RalleyDetails, Long> {
	
	
	
	
	@Query("select distinct state_id from RalleyDetails")
	public List<Long> findDistinctAllotStates();
	
	@Query("select distinct r.city_id from RalleyDetails r where r.state_id = :stateid")
	public List<Long> findDistinctAllotCities(@Param("stateid") Long stateid);
	
	
	
	

}
