package com.cdac.iafralley.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.cdac.iafralley.user.validation.ValidEmail;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="candidate_details")
public class RalleyCandidateDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message="is required")
	@Column(name="name")
	private String name;
	
	@NotBlank(message="is required")
	@Column(name="father_name")
	private String fathername;
	
	@ValidEmail
	@Column(name="emailid")
	private String emailid;
	
	//Mapping Date and Timestamp using java.util.Date
	@NotNull
	@Column(name = "DOB")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private java.util.Date dateOfBirth;
	
	@NotEmpty
	@Column(name="mobile")
	private String contactno;
	
	@NotEmpty
	@Column(name="passed_exam_detail")
	private String passed_exam_detail;
	
	@NotNull(message="is required")
	@Column(name="degree_detail")
	private String otherDetailPassedDetail;
	
	
	@NotNull
	@Column(name="passed_exam_percentage")
	private Integer passed_exam_percentage;
	
	@NotNull
	@Column(name="english_percentage")
	private Integer english_percentage;
	
	
	@Column(name="maritial_status")
	private boolean maritial_status;
	
	@NotEmpty
	@Column(name="height")
	private String height;
	
	@NotEmpty
	@Column(name="city")
	private String city;
	
	@NotEmpty
	@Column(name="state")
	private String state;
	
	
	@Column(name = "Datetime_reporting")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private java.util.Date dateTimeOfReporting;
	
	@Column(name = "subscribed_on")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private java.util.Date subscirbed_on;
	

//	 @Id
//	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registrationid_seq")
//	    @GenericGenerator(
//	        name = "registrationid_seq", 
//	        strategy = "com.cdac.iafralley.entity.StringPrefixedSequenceIdGenerator", 
//	        parameters = {
//	            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
//	            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "B_"),
//	            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	 @Column(name="ralley_regid")
	private String ralleyregistrationNo;
	 
	 public RalleyCandidateDetails() {
		// TODO Auto-generated constructor stub
	}	

	public RalleyCandidateDetails(String name, String fathername, String emailid, Date dateOfBirth, String contactno,
			String passed_exam_detail, Integer passed_exam_percentage, Integer english_percentage,
			boolean maritial_status, String height, String city, String state, Date dateTimeOfReporting) {
		super();
		this.name = name;
		this.fathername = fathername;
		this.emailid = emailid;
		this.dateOfBirth = dateOfBirth;
		this.contactno = contactno;
		this.passed_exam_detail = passed_exam_detail;
		this.passed_exam_percentage = passed_exam_percentage;
		this.english_percentage = english_percentage;
		this.maritial_status = maritial_status;
		this.height = height;
		this.city = city;
		this.state = state;
		this.dateTimeOfReporting = dateTimeOfReporting;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public java.util.Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.util.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getPassed_exam_detail() {
		return passed_exam_detail;
	}

	public void setPassed_exam_detail(String passed_exam_detail) {
		this.passed_exam_detail = passed_exam_detail;
	}

	public Integer getPassed_exam_percentage() {
		return passed_exam_percentage;
	}

	public void setPassed_exam_percentage(Integer passed_exam_percentage) {
		this.passed_exam_percentage = passed_exam_percentage;
	}

	public Integer getEnglish_percentage() {
		return english_percentage;
	}

	public void setEnglish_percentage(Integer english_percentage) {
		this.english_percentage = english_percentage;
	}

	public boolean isMaritial_status() {
		return maritial_status;
	}

	public void setMaritial_status(boolean maritial_status) {
		this.maritial_status = maritial_status;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public java.util.Date getDateTimeOfReporting() {
		return dateTimeOfReporting;
	}

	public void setDateTimeOfReporting(java.util.Date dateTimeOfReporting) {
		this.dateTimeOfReporting = dateTimeOfReporting;
	}


	
	
	public String getRalleyregistrationNo() {
		return ralleyregistrationNo;
	}

	public void setRalleyregistrationNo(String ralleyregistrationNo) {
		this.ralleyregistrationNo = ralleyregistrationNo;
	}

	
	
	

	public java.util.Date getSubscirbed_on() {
		return subscirbed_on;
	}

	public void setSubscirbed_on(java.util.Date subscirbed_on) {
		this.subscirbed_on = subscirbed_on;
	}
	
	

	public String getOtherDetailPassedDetail() {
		return otherDetailPassedDetail;
	}

	public void setOtherDetailPassedDetail(String otherDetailPassedDetail) {
		this.otherDetailPassedDetail = otherDetailPassedDetail;
	}

	@Override
	public String toString() {
		return "RalleyCandidateDetails [id=" + id + ", name=" + name + ", fathername=" + fathername + ", emailid="
				+ emailid + ", dateOfBirth=" + dateOfBirth + ", contactno=" + contactno + ", passed_exam_detail="
				+ passed_exam_detail + ", otherDetailPassedDetail=" + otherDetailPassedDetail
				+ ", passed_exam_percentage=" + passed_exam_percentage + ", english_percentage=" + english_percentage
				+ ", maritial_status=" + maritial_status + ", height=" + height + ", city=" + city + ", state=" + state
				+ ", dateTimeOfReporting=" + dateTimeOfReporting + ", subscirbed_on=" + subscirbed_on
				+ ", ralleyregistrationNo=" + ralleyregistrationNo + "]";
	}

	

	

	
	
	
	
	

}