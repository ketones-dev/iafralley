package com.cdac.iafralley.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.iafralley.entity.RalleyDaywiseSlotDetails;
import com.cdac.iafralley.entity.RalleyDetails;

@Repository
public interface RalleyDaywiseSlotDetailsDAO extends JpaRepository<RalleyDaywiseSlotDetails, Long> {
	
	public List<RalleyDaywiseSlotDetails> findByRalleydetails(RalleyDetails rd);

}
