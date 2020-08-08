package com.cdac.iafralley.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cdac.iafralley.entity.RalleyDaywiseSlotDetails;
import com.cdac.iafralley.entity.RalleyDetails;
import com.cdac.iafralley.services.RalleyDetailsService;
import com.cdac.iafralley.user.RalleyDetailsDTO;

@Controller
public class AdminController {
	
	@Autowired
	RalleyDetailsService rdservice;
	
	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
	
	
	
	
	private java.util.Date parseTimestamp(String timestamp) {
	    try {
	        return DATE_TIME_FORMAT.parse(timestamp);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	
	
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
	public ModelAndView showCreateRalleyForm(RalleyDetails rd) {
		ModelAndView mv=new ModelAndView("CreateRalley");
		System.out.println("in dashboard");
		
		mv.addObject("ralleyDetails", rd);
		mv.addObject("slotlist", rd.getRalleydaywiseSlot());
		return mv;
		
	}
	
	@PostMapping("/CreateRalley")
	public String showCreateRalley(@Valid  RalleyDetailsDTO rd,BindingResult result,Model model ) {
		System.out.println("in create ralley");

		if (result.hasErrors()) {
			result
			  .getFieldErrors() .stream() .forEach(f -> System.out.println(f.getField() +
			  ": " + f.getDefaultMessage()));
			model.addAttribute("ralleyDetails", rd);
			return "CreateRalley";
		}

		rd.setCity_id((long) 1);
		rd.setState_id((long) 1);
		System.out.println(rd.toString());
		RalleyDetails ralleyDetails = new RalleyDetails(rd.getState_id(), rd.getCity_id(), rd.getRalley_details(),
				rd.getVenue_details(), rd.getStart_date(), rd.getEnd_date(), rd.getNo_OfDays(), rd.getMin_dob(),
				rd.getMax_dob(), rd.getMin_passing_percentage(), rd.getMin_eng_percentage(), rd.getMin_height());

		List<RalleyDaywiseSlotDetails> slotlist = rd.getRalleydaywiseSlot();

		List<RalleyDaywiseSlotDetails> rds = rd.getRalleydaywiseSlot();
		rds.forEach(System.out::println);

		slotlist.forEach(p -> {
			RalleyDaywiseSlotDetails newobj = new RalleyDaywiseSlotDetails(p.getNo_of_intake(), p.getDay_date(),
					p.getTime_of_reporting());
			ralleyDetails.add(newobj);
		});

		rdservice.saveRalleyDetails(ralleyDetails);

		return "redirect:/ShowRalleyDetails";
		
	}
	
	@GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RalleyDetails rs = rdservice.findById(id);
       // List<RalleyDaywiseSlotDetails> sdetails=rd
        List<RalleyDaywiseSlotDetails> rds=rdservice.getAllSlot(rs) ;
        
            if(rs == null)
            {
            	model.addAttribute("error", "id does exist any more");
            	return "ShowRalleyDetails";
            }
           if( rds.isEmpty())
           {
        	   System.out.println("true");
           }
            rds.forEach(System.out::println);
           // model.addAttribute("slotlist", rds);    
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
