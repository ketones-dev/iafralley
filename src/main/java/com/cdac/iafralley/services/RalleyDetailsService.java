package com.cdac.iafralley.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.iafralley.Dao.RalleyDetailsDAO;
import com.cdac.iafralley.entity.RalleyDetails;

@Service
public class RalleyDetailsService {
	
	@Autowired
	RalleyDetailsDAO ralleydetaildao;
	
	public RalleyDetails findById(Long id)
	{
		Optional<RalleyDetails> ralleyDetail=ralleydetaildao.findById(id); 
		RalleyDetails rs=null;
		if(ralleyDetail.isPresent())
		{
			rs=ralleyDetail.get();
		}
		return rs;
		
	}
	
	
	public List<RalleyDetails> getAllRalleyDetails()
	{
		List<RalleyDetails> rs=ralleydetaildao.findAll();
		return rs;
		
	}
	
	public void saveRalleyDetails(RalleyDetails r)
	{
		
		ralleydetaildao.save(r);
	}
	
	public void deleteRalleyDetails(Long id)
	{
		RalleyDetails ralleyDetails=ralleydetaildao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
	ralleydetaildao.delete(ralleyDetails);
	}

}
