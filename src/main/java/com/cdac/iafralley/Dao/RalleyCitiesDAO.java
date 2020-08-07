package com.cdac.iafralley.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.iafralley.entity.RalleyCities;
import com.cdac.iafralley.entity.RalleyStates;

@Repository
public interface RalleyCitiesDAO extends JpaRepository<RalleyCities, Long>{

	@Query("from RalleyCities r where r.state.state_id= :id")
	public List<RalleyCities> getallCities(@Param("id") Long id);

	
}
