package com.cdac.iafralley.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cdac.iafralley.entity.RalleyDaywiseSlotDetails;
import com.cdac.iafralley.entity.RalleyDetails;
import com.cdac.iafralley.services.RalleyDetailsService;

@Controller
public class AdminController {
	
	@Autowired
	RalleyDetailsService rdservice;
	
	@GetMapping("/Dashboard")
	public String showDashboard() {
		System.out.println("in dashboard");
		return "Dashboard";
		
	}
	
	@GetMapping("/ShowRalleyDetails")
	public String showRalleyDetails(Model model) {
		System.out.println("in dashboard");
		model.addAttribute("ralleyDetails", rdservice.getAllRalleyDetails());
		return "ShowRalleyDetails";
		
	}
	
	@GetMapping("/ShowCreateRalley")
	public String showCreateRalleyForm(RalleyDetails rd,Model model) {
		System.out.println("in dashboard");
		model.addAttribute("ralleyDetails", rd);
		return "CreateRalley";
		
	}
	
	@PostMapping("/CreateRalley")
	public String showCreateRalley(@Valid  RalleyDetails rd,BindingResult result,Model model ) {
		System.out.println("in dashboard");
		/*
		 * if(result.hasErrors()) { model.addAttribute("ralleyDetails", rd); return
		 * "CreateRalley"; }
		 */
		System.out.println(rd.toString());
		RalleyDaywiseSlotDetails rds=new RalleyDaywiseSlotDetails(Long.parseLong("50"),rd.getStart_date(),"08:00AM");
		RalleyDaywiseSlotDetails rds2=new RalleyDaywiseSlotDetails(Long.parseLong("50"),rd.getStart_date(),"08:00AM");
		//rdservice.saveRalleyDetails(rd);
		List<RalleyDaywiseSlotDetails> lirds=new ArrayList<>();
		
		rd.setRalleydaywiseSlot(lirds);
		rd.add(rds);
		rd.add(rds2);
		rd.setCity_id((long) 1);
		rd.setState_id((long) 1);
		rdservice.saveRalleyDetails(rd);
		
		return "redirect:/ShowRalleyDetails";
		
	}
	
	@GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RalleyDetails rs = rdservice.findById(id);
            if(rs == null)
            {
            	model.addAttribute("error", "id does exist any more");
            	return "ShowRalleyDetails";
            }
        model.addAttribute("ralleyDetails", rs);
        return "CreateRalley";
    }

	
	@PostMapping("/update/{id}")
    public String updateRalley(@PathVariable("id") Long id, @Valid RalleyDetails ralleydetails, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            ralleydetails.setRalley_id(id);
            return "update-ralley";
        }

        rdservice.saveRalleyDetails(ralleydetails);
        model.addAttribute("ralleyDetailsshow", rdservice.getAllRalleyDetails());
        return "redirect:/ShowRalleyDetails";
    }
	
	 @GetMapping("/delete/{id}")
	    public String deleteRalleyDetail(@PathVariable("id") Long id, Model model) {
	        
	          
	        rdservice.deleteRalleyDetails(id);
	        model.addAttribute("ralleyDetails", rdservice.getAllRalleyDetails());
	        return "redirect:/ShowRalleyDetails";
	    }

}
