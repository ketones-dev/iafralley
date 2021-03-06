package com.cdac.iafralley.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cdac.iafralley.entity.RalleyCandidateDetails;
import com.cdac.iafralley.entity.RalleyCities;
import com.cdac.iafralley.entity.RalleyDaywiseSlotDetails;
import com.cdac.iafralley.entity.RalleyDetails;
import com.cdac.iafralley.services.RalleyCandidateDetailsService;
import com.cdac.iafralley.services.RalleyDetailsService;
import com.cdac.iafralley.user.RalleyDetailsDTO;
import com.cdac.iafralley.util.RalleyIdGenrator;

@Controller
@RequestMapping("/Dashboard")
public class AdminController {
	
	@Autowired
	RalleyDetailsService rdservice;
	@Autowired
	RalleyCandidateDetailsService candidateService;
	@Autowired
	private RalleyIdGenrator ralleyIdGenrator;
	
	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
	
	
	
	
	private java.util.Date parseTimestamp(String timestamp) {
	    try {
	        return DATE_TIME_FORMAT.parse(timestamp);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	
	
	@GetMapping("/home")
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
		mv.addObject("allStates", rdservice.getallState());
		mv.addObject("allgroups",rdservice.getAllgroups());
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

		//rd.setCity_id((long) 1);
		//rd.setState_id((long) 1);
		System.out.println(rd.toString());
		LocalDate std=LocalDate.parse(convertDateToString(rd.getStart_date()));
		String prefixvalue=String.valueOf(std.getMonth()).substring(0, 3).toUpperCase()+String.valueOf(std.getDayOfMonth())+String.valueOf(std.getYear()).substring(2)+rd.getCity_name().substring(0, 2).toUpperCase();
		String custid=ralleyIdGenrator.RalleyCustomId(prefixvalue);
		
		RalleyDetails ralleyDetails = new RalleyDetails(rd.getRalley_id(),rd.getState_id(), rd.getCity_id(), rd.getRalley_details(),
				rd.getVenue_details(), rd.getStart_date(), rd.getEnd_date(), rd.getNo_OfDays(), rd.getMin_dob(),
				rd.getMax_dob(), rd.getMin_passing_percentage(), rd.getMin_eng_percentage(), rd.getMin_height(),rd.getCity_name(),rd.getState_name(),custid);

		ralleyDetails.setRalleyForGroup(rd.getRalleyForGroup());
		
		List<RalleyDaywiseSlotDetails> slotlist = rd.getRalleydaywiseSlot();

		List<RalleyDaywiseSlotDetails> rds = rd.getRalleydaywiseSlot();
		rds.forEach(System.out::println);

		slotlist.forEach(p -> {
			RalleyDaywiseSlotDetails newobj = new RalleyDaywiseSlotDetails(p.getSlot_id(),p.getNo_of_intake(), p.getDay_date(),
					p.getTime_of_reporting());
			ralleyDetails.add(newobj);
		});

		rdservice.saveRalleyDetails(ralleyDetails);

		return "redirect:/Dashboard/ShowRalleyDetails";
		
	}
	
	@GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RalleyDetails rs = rdservice.findById(id);
       // List<RalleyDaywiseSlotDetails> sdetails=rd
        List<RalleyDaywiseSlotDetails> rds=rdservice.getAllSlot(rs) ;
        System.out.println(rs.toString());
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
        model.addAttribute("allStates", rdservice.getallState());
        model.addAttribute("allgroups",rdservice.getAllgroups());
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
        return "redirect:/Dashboard/ShowRalleyDetails";
    }
	
	 @GetMapping("/delete/{id}")
	    public String deleteRalleyDetail(@PathVariable("id") Long id, Model model) {
	        
	          
	        rdservice.deleteRalleyDetails(id);
	        model.addAttribute("ralleyDetails", rdservice.getAllRalleyDetails());
	        return "redirect:/Dashboard/ShowRalleyDetails";
	    }
	 
	 @GetMapping("/ShowRegisteredStudentData")
	 public ModelAndView ShowRegisteredStudentData()
	 {
		 ModelAndView m= new ModelAndView("RegisteredStudentData");
		 List<RalleyCandidateDetails> rd=candidateService.findAll();
		 m.addObject("studentdata", rd);
		 return m;
	 }
	 
	 @RequestMapping(value="/edit/getCitiesonbasisofStateSeclected", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public  ResponseEntity<List<RalleyCities>> getcitiesAll(@RequestBody Map<String, Long>  stateid) {
			
			System.out.println("in getcities"+stateid.get("stateid"));
		   List<RalleyCities> entityList = candidateService.getallCitesByState(stateid.get("stateid"));
			//List<RalleyCities> entityList=Collections.EMPTY_LIST;
		    
		   
		    return new ResponseEntity<List<RalleyCities>>(entityList, HttpStatus.OK);
		}
	 
	 @RequestMapping(value="/getCitiesonbasisofStateSeclected", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public  ResponseEntity<List<RalleyCities>> getCitiesonbasisofStateSeclected(@RequestBody Map<String, Long>  stateid) {
			
			System.out.println("in getcities"+stateid.get("stateid"));
		   List<RalleyCities> entityList = candidateService.getallCitesByState(stateid.get("stateid"));
			//List<RalleyCities> entityList=Collections.EMPTY_LIST;
		    
		   
		    return new ResponseEntity<List<RalleyCities>>(entityList, HttpStatus.OK);
		}
	 
	 
	 @RequestMapping(value="/getralleyallotCities", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public  ResponseEntity<List<RalleyDetails>> getralleyallotCities() {
			List<RalleyDetails> entityList= rdservice.findDistinctCity_id();
			
			
			//System.out.println("in getcities"+stateid.get("stateid"));
		 //  List<RalleyCities> entityList = candidateService.getallCitesByState(stateid.get("stateid"));
			//List<RalleyCities> entityList=Collections.EMPTY_LIST;
		    
		   
		    return new ResponseEntity<List<RalleyDetails>>(entityList, HttpStatus.OK);
		}
	 
	 public static String convertDateToString(Date strDate) {
		    try {
		      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		       // you can change format of date
		      String date = formatter.format(strDate);
		      //Timestamp timeStampDate = new Timestamp(date.getTime());
		    //  logger.info("in string"+date);
		      return date;
		    } catch (Exception e) {
		      System.out.println("Exception :" + e);
		      return null;
		    }
		  }

}
