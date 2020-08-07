package com.cdac.iafralley.controllers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cdac.iafralley.entity.RalleyCandidateDetails;
import com.cdac.iafralley.entity.RalleyCities;
import com.cdac.iafralley.entity.RalleyStates;
import com.cdac.iafralley.exception.CandidateSelectedStateCitiesException;
import com.cdac.iafralley.mailConfig.MailingService;
import com.cdac.iafralley.services.RalleyCandidateDetailsService;
import com.cdac.iafralley.services.UserService;
import com.cdac.iafralley.user.RalleyCandidateDTO;
import com.cdac.iafralley.util.RalleyIdGenrator;
import com.cdac.iafralley.util.RegisterdCandidatePDFReport;



/**
 * 
 * @author Ketan Tank
 *
 */
@Controller
@PropertySource({"classpath:mailserver.properties"})
@PropertySource({"classpath:applicantfilepath.properties"})
public class RalleyRegistrationFormController {
	
	@Autowired
	RalleyCandidateDetailsService candidateService;
	
	@Value("${config.mailserver}")
    private String mailserver;
 
    @Value("${config.from}")
    private String from;
 
    @Value("${config.password}")
    private String password;
	
    @Value("${applicant.filepath}")
	private String FILE_PATH;

	private static final Logger logger = LoggerFactory.getLogger(RalleyRegistrationFormController.class);
	
	
	/**
	 * Dob binder in yyyy-MM-dd format for ralleycandidatedetails modal
	 * @param binder
	 */
	/*
	 * @InitBinder("ralleycandidatedetails") public void customizeBinding
	 * (WebDataBinder binder) { SimpleDateFormat dateFormatter = new
	 * SimpleDateFormat("yyyy-MM-dd"); dateFormatter.setLenient(false);
	 * binder.registerCustomEditor(Date.class, "dateOfBirth", new
	 * CustomDateEditor(dateFormatter, true)); }
	 */
	
	/**
	 * String trimmer for all fields in ralleycandidatedetails modal
	 * 
	 * @param dataBinder
	 */
	
	  @InitBinder public void initBinder(WebDataBinder dataBinder) {
	  
	  StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
	  
	  dataBinder.registerCustomEditor(String.class, stringTrimmerEditor); }
	 

	@GetMapping(value = "/showRegistrationForm")
	public ModelAndView createUserView(Model model) {
		
		
		
		
		ModelAndView modelAndView = new ModelAndView("CandidateRegistration");
		 if (!model.containsAttribute("ralleyCandidateDetails")) {
	        	modelAndView.addObject("ralleyCandidateDetails", new RalleyCandidateDetails());
	        	modelAndView.addObject("allStates", candidateService.getallState());
	        }
		
		 
		return modelAndView;
	}
	
	
	@GetMapping(value = "/RegistrationSuccess")
	public String showRegisterdCandidateDetails(Model model) {
		
		if(!model.containsAttribute("candidateDetails"))
		{
			return "redirect:/showRegistrationForm";
		}
		
		
		
		return "RegistrationSuccess";

	}
	 
	@PostMapping("/registerCandidate")
	public ModelAndView createUser(@ModelAttribute("ralleyCandidateDetails") @Valid RalleyCandidateDetails ralleyCandidateDetails, BindingResult result,RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		
		logger.info("error:"+result.hasErrors());
		if (result.hasErrors()) {
			
			  logger.info("Validation errors while submitting form."); result
			  .getFieldErrors() .stream() .forEach(f -> System.out.println(f.getField() +
			  ": " + f.getDefaultMessage()));
			
			  modelAndView.setViewName("redirect:/showRegistrationForm");
			  redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ralleyCandidateDetails", result);
			  redirectAttributes.addFlashAttribute("ralleyCandidateDetails", ralleyCandidateDetails);
			 // modelAndView.addObject("ralleyCandidateDetails", ralleyCandidateDetails);
			 // modelAndView.addObject("allStates", candidateService.getallState());
			  redirectAttributes.addFlashAttribute("allStates", candidateService.getallState());
			 
			 
			return modelAndView;
		}
		
		
		  try { RalleyCandidateDetails
		  candidateDetails=candidateService.save(ralleyCandidateDetails);
		  //modelAndView.addObject("allUsers", userService.getAllUsers());
		  //modelAndView.addObject("candidateDetails",candidateDetails );
		  RegisterdCandidatePDFReport.createPDF(candidateDetails,FILE_PATH);
		  String to=candidateDetails.getEmailid();
		String subject="Ralley Registration Conformation";
		String message="Thank u for registring and Here is your register detail application form";
		
		
		try {
		
		MailingService.sendMail(mailserver, from, password, candidateDetails, subject, message,FILE_PATH);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		 redirectAttributes.addFlashAttribute("candidateDetails", candidateDetails);
		  modelAndView.setViewName("redirect:/RegistrationSuccess");
		  logger.info("Form submitted successfully.");
		  
		  } catch (Exception e) { // TODO Auto-generated catch block
		  
		  modelAndView.setViewName("CandidateRegistration");
		  modelAndView.addObject("candidateDetails", ralleyCandidateDetails);
		  modelAndView.addObject("allStates", candidateService.getallState());
		  modelAndView.addObject("error",e.getMessage() );
		  
		  
		  }
		 
		return modelAndView;
		
	}


	@RequestMapping(value="/getCitiesonbasisofStateSeclected", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  ResponseEntity<List<RalleyCities>> getcitiesAll(@RequestBody Map<String, Long>  stateid) {
		
		System.out.println("in getcities"+stateid.get("stateid"));
	   List<RalleyCities> entityList = candidateService.getallCitesByState(stateid.get("stateid"));
		//List<RalleyCities> entityList=Collections.EMPTY_LIST;
	    
	   
	    return new ResponseEntity<List<RalleyCities>>(entityList, HttpStatus.OK);
	}

	

}
