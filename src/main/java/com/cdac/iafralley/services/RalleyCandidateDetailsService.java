package com.cdac.iafralley.services;

import java.util.List;
import java.util.Map;

import com.cdac.iafralley.entity.RalleyCandidateDetails;
import com.cdac.iafralley.entity.RalleyCities;
import com.cdac.iafralley.entity.RalleyStates;
import com.cdac.iafralley.exception.CandidateDuplicateEntry;
import com.cdac.iafralley.exception.CandidateSelectedStateCitiesException;

public interface RalleyCandidateDetailsService {
	
	public List<RalleyCandidateDetails> findAll();
	
	public RalleyCandidateDetails save(RalleyCandidateDetails candidate) throws CandidateSelectedStateCitiesException, CandidateDuplicateEntry;
	
	public RalleyCandidateDetails findById(Long id);
	
	public List<RalleyCities> getallCites(Long stateid);
	
	public List<RalleyStates> getallState();

	public List<RalleyCities> getallCitesByState(Long stateid);
	
	
	

}
