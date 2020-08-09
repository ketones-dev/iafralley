package com.cdac.iafralley.services;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.iafralley.Dao.RalleyCandidateDetailsDAO;
import com.cdac.iafralley.Dao.RalleyCitiesDAO;
import com.cdac.iafralley.Dao.RalleyDetailsDAO;
import com.cdac.iafralley.Dao.RalleyStateDAO;
import com.cdac.iafralley.controllers.RalleyRegistrationFormController;
import com.cdac.iafralley.entity.RalleyCandidateDetails;
import com.cdac.iafralley.entity.RalleyCities;
import com.cdac.iafralley.entity.RalleyStates;
import com.cdac.iafralley.exception.CandidateDuplicateEntry;
import com.cdac.iafralley.exception.CandidateSelectedStateCitiesException;
import com.cdac.iafralley.util.RalleyIdGenrator;


@Service
public class RalleyCandidateDetailsServiceImpl implements RalleyCandidateDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(RalleyCandidateDetailsServiceImpl.class);
	
	@Autowired
	private RalleyCandidateDetailsDAO ralleyCandidateDetailsRepo;
	
	@Autowired
	private RalleyCitiesDAO conductingCities; 
	
	@Autowired
	private RalleyStateDAO conductingStates; 
	
	@Autowired
	private RalleyIdGenrator ralleyIdGenrator;
	
	@Autowired
	private RalleyDetailsDAO ralleyDetailsRepo;

	@Override
	public List<RalleyCandidateDetails> findAll() {
		// TODO Auto-generated method stub
		return ralleyCandidateDetailsRepo.findAll();
	}

	@Override
	public RalleyCandidateDetails save(RalleyCandidateDetails candidate) throws CandidateSelectedStateCitiesException, CandidateDuplicateEntry {
		// TODO Auto-generated method stub
		//before saving genrating and setting candidate ralley id
		logger.info("converting and getting candidate selected state and city name");
		Map<String, String> values=getCandidateSelectedStateCityName(candidate.getState(),candidate.getCity());
		if(values.get("statename") != null && values.get("cityname") != null)
		{
			candidate.setState(values.get("statename"));
			candidate.setCity(values.get("cityname"));
			logger.info("setting values");
		}
		logger.info("before genrating id checking registring emailid is already present in DB or not...");
		RalleyCandidateDetails result=ralleyCandidateDetailsRepo.findByEmailid(candidate.getEmailid());
		if(result != null)
		{
			throw new CandidateDuplicateEntry("emailid:"+candidate.getEmailid()+" is already registered on this portal");
		}
		logger.info("Registering emailid is not present in DB so proceeding further...");
		logger.info("Genrating registration id and alloting daywise time to candidate");
		String regisrationid=ralleyIdGenrator.RalleyRegistrationNumGenrator();
		logger.info("for candidate with emailid:"+candidate.getEmailid()+" Genrated Candidate registration ID:"+regisrationid);
		candidate.setRalleyregistrationNo(regisrationid);
		
		logger.info("Before saving Candidate filled values are :"+candidate.toString());
		return ralleyCandidateDetailsRepo.save(candidate);

	}

	@Override
	public RalleyCandidateDetails findById(Long id) {
		// TODO Auto-generated method stub
		Optional<RalleyCandidateDetails> result= ralleyCandidateDetailsRepo.findById(id);
		RalleyCandidateDetails theCandidate=null;
		if(result.isPresent())
		{
			theCandidate=result.get();
		}
		return theCandidate;
	}
	
	@Override
	public List<RalleyStates> getallState() {
		// TODO Auto-generated method stub
		List<RalleyStates> ralleystates=conductingStates.findAll();
		if(ralleystates == null)
		{
			ralleystates=Collections.emptyList();
			logger.warn("No states entries in database....");
		}
		logger.info("showing all conducting ralley states:"+ralleystates.toString());
		return ralleystates;
	}

	@Override
	public List<RalleyCities> getallCitesByState(Long stateid) {
		// TODO Auto-generated method stub
		List<RalleyCities> ralleycities=conductingCities.getallCities(stateid);
		if(ralleycities == null)
		{
			
			logger.warn("No cities entries in database....");
		}
		logger.info("showing all conducting ralley cities:"+ralleycities.toString());
		return ralleycities;
		
		
		
	}

	

	@Override
	public List<RalleyCities> getallCites(Long stateid) {
		// TODO Auto-generated method stub
				List<RalleyCities> ralleycities=conductingCities.findAll();
				if(ralleycities == null)
				{
					ralleycities=Collections.emptyList();
					logger.warn("No cities entries in database....");
				}
				logger.info("showing all conducting ralley cities:"+ralleycities.toString());
				return ralleycities;
				
	}

	public Map<String, String> getCandidateSelectedStateCityName(String state, String city) throws CandidateSelectedStateCitiesException {
		// TODO Auto-generated method stub
		Optional<RalleyStates> selectedState = conductingStates.findById(Long.valueOf(state));
		Optional<RalleyCities> selectedCities = conductingCities.findById(Long.valueOf(city));
		if(!selectedState.isPresent() || !selectedCities.isPresent())
		{
			
			logger.warn("Either Selected State or City is not present in database");
			throw new CandidateSelectedStateCitiesException("Either Selected State or City is not present in database");
		}
		RalleyStates statename = selectedState.get();
		RalleyCities cityname = selectedCities.get();
		logger.info("state:"+statename.getState()+" city:"+cityname.getCity());
		Map<String,String> names=new HashMap<String, String>();
		names.put("statename",statename.getState());
		names.put("cityname",cityname.getCity());
		
		return names;
	}

	@Override
	public List<RalleyCities> getallCitesByStateAdminAlloted(Long stateid) {
		// TODO Auto-generated method stub
		List<Long> allocatecities= ralleyDetailsRepo.findDistinctAllotCities(stateid);
		List<RalleyCities> c=conductingCities.getAllotCities(allocatecities);
		
		return c;
	}

	@Override
	public List<RalleyStates> getralleyAllState() {
		// TODO Auto-generated method stub
		List<Long> allocatestates= ralleyDetailsRepo.findDistinctAllotStates();
		List<RalleyStates> s=conductingStates.getallStateonBasisOfList(allocatestates);
		return s;
	}

	
	

	

}
