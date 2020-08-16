/**
 * 
 *@Author ketan tank
 * candidateRegistration 
 */

const http = new easyHTTP;

//getting fields
let form=document.getElementById("form-table");
let message=document.getElementById("message");
let error=document.getElementById("error");
let optcity=document.getElementById("optcity");

let passed_exam =document.getElementById('passed_exam');
let eng_passed_exam =document.getElementById('eng_passed_exam');
let dateOfBirth=document.getElementById('dateOfBirth');
let height=document.getElementById('height');


let ralley_stateSelectedValue = document.getElementById("ralleystateSelect");
let ralley_citySelectedValue = document.getElementById("ralleycitySelect");

let stateSelectedValue = document.getElementById("stateSelect");
let citySelectedValue = document.getElementById("citySelect");

let duplicateerror= document.getElementById("error2");

window.addEventListener('load', function() {
    console.log('All assets are loaded');
    if(error.value === "error")
    	{
    	let data = ralley_stateSelectedValue.options[ralley_stateSelectedValue.selectedIndex].value;
    	toogleshowtable();
    	 http.post('getralleyallotCitiesonbasisofStateSeclected', {stateid : data} , function(err, post) {
			  if(err) {
			    console.log(err);
			  } else {
			    console.log(post);
			    let cities=JSON.parse(post);
			    createCitydropdownData(cities,"ralleystateSelect");
			    //optcity.value
			       // for ( var i = 0; i < ralley_citySelectedValue.options.length; i++ ) {
			         //   if ( ralley_citySelectedValue.options[i].value === "0" ) {
			            	ralley_citySelectedValue.options[0].selected = true;
			           //     return;
			           // }
			       // }
			    
			  }
			 });
    	stateSelectedValue.selectedIndex = 0;
    	alert("please check input values...")
    	}
    
    if(duplicateerror.value !== "")
    	{
    	ralley_stateSelectedValue.selectedIndex=0;
    	//stateSelectedValue.selectedIndex = 0;
    	alert("Either emaild id or aahdar is already registered");
    	}
    
})


ralley_citySelectedValue.addEventListener("change", function(e) {
	
	 let data = ralley_citySelectedValue.options[ralley_citySelectedValue.selectedIndex].value;
	 let citytext=ralley_citySelectedValue.options[ralley_citySelectedValue.selectedIndex].text;
	 console.log(typeof data);
	 if(data !== "")
		 {
		// Create Post
		 document.getElementById("optedcityname").value = citytext;
		 
		 http.post('getralleyFormOnBasisOfAdminCities', {cityid : data} , function(err, post) {
		  if(err) {
		    console.log(err);
		  } else {
		    console.log(post);
		    let cities=JSON.parse(post);
		    console.log(cities);
		    if(cities === false)
		    	{
		    	toogleshowmsg();
		    	  let messagetag="Seems like registration for selected city is full";
		    	 
		    	  document.getElementById("message").innerHTML = messagetag;
		    	}
		    else
		    	{
		    	
		    	http.post('getralleyDetailsOnBasisOfAdminCities', {cityid : data} , function(err, post) {
			   		  if(err) {
			   		    console.log(err);
			   		  } else {
			   		    console.log(post);
			   		    let ra=post;
			   		 document.getElementById("ralleyoptdetails").innerHTML= ra;
			   		 
			   		if(cities === true){
				    	http.post('getralleyValidationDetailsOnBasisOfAdminCities', {cityid : data} , function(err, post) {
					   		  if(err) {
					   		    console.log(err);
					   		  } else {
					   		    console.log(post);
					   		    let data=JSON.parse(post);
					   		 passed_exam.setAttribute("min",data.minpassing);
					   		eng_passed_exam.setAttribute("min",data.engpassing);
					   	 passed_exam.setAttribute("max","100");
					   		eng_passed_exam.setAttribute("max","100");
					   		dateOfBirth.setAttribute("min",data.mindob);
					   		dateOfBirth.setAttribute("max",data.maxdob);
					   		height.setAttribute("min",data.height);
					   		 
					   		  }
					    	 });
				    	}
			   		 
			   		  }
			    	 });
		    	
		    	
		    	
		    	toogleshowtable();
		    	
		    	
		    	}
		  }
		 });
		 }
	 else
		 {
		 toogleshowmsg();
   	  let messagetag="Please select city";
   	 
   	  document.getElementById("message").innerHTML = messagetag;
		 }
	
	
});

function toogleshowmsg()
{
	 message.classList.remove("message");
  	  message.classList.add("show");
  	  form.classList.remove("show");
  	  form.classList.add("form-table");
}

function toogleshowtable()
{
	 message.classList.remove("show");
	  message.classList.add("message");
	  form.classList.remove("form-table");
	  form.classList.add("show");
}


ralley_stateSelectedValue.addEventListener("change", function(e) {
	 console.log(this);
	 let data = ralley_stateSelectedValue.options[ralley_stateSelectedValue.selectedIndex].value;
	 let statetext=ralley_stateSelectedValue.options[ralley_stateSelectedValue.selectedIndex].text;
	    if(data !== "")
	    {
	    	
	    	toogleshowmsg();
	    	document.getElementById("message").innerHTML = "";
	    	
	    	document.getElementById("optedstatename").value=statetext;
	        addActivityItem(data,e.target.id);
	    }
	    else
	    	{
	    	 toogleshowmsg();
	      	  let messagetag="Please select state";
	      	$('#ralleycitySelect option:not(:first)').remove();
	      	 
	      	  document.getElementById("message").innerHTML = messagetag;
	    	}
	    //console.log(activities.value);
	});
 
 
stateSelectedValue.addEventListener("change", function(e) {
	 console.log(this);
	 let data = stateSelectedValue.options[stateSelectedValue.selectedIndex].value;
	    if(data !== "0")
	    {
	        addActivityItem(data,e.target.id);
	    }
	    //console.log(activities.value);
	});
 
 function addActivityItem(data,id){
	 if(id === "stateSelect"){
		 
		 console.log(data);
		 
			// Create Post
			 
			 http.post('getCitiesonbasisofStateSeclected', {stateid : data} , function(err, post) {
			  if(err) {
			    console.log(err);
			  } else {
			    console.log(post);
			    let cities=JSON.parse(post);
			    createCitydropdownData(cities,id);
			  }
			 });

			 
		 
	 }
	 else{
		 
		 console.log(data);
		 
			// Create Post
			 
			 http.post('getralleyallotCitiesonbasisofStateSeclected', {stateid : data} , function(err, post) {
			  if(err) {
			    console.log(err);
			  } else {
			    console.log(post);
			    let cities=JSON.parse(post);
			    createCitydropdownData(cities,id);
			  }
			 });
		 
	 }
	
 }
 
 
 
function createCitydropdownData(citiesArrays,id)  {
	//console.log(JSON.parse(citiesArrays).length);
	removecitiesExcetFirstOption(citiesArrays,id);
	//Add the Options to the DropDownList.
    for (var i = 0; i < citiesArrays.length; i++) {
        var option = document.createElement("option");
        console.log(citiesArrays[i].city_id);
        //Set Customer Name in Text part.
        option.text = citiesArrays[i].city;

        //Set CustomerId in Value part.
        option.value = citiesArrays[i].city_id;
        if(id === "stateSelect")
        	{
        //Add the Option element to DropDownList.
        citySelectedValue.appendChild(option);
        	}
        else{
        	ralley_citySelectedValue.appendChild(option);
        }
    }
}
	
function removecitiesExcetFirstOption(citiesArrays,id){
	
	var i;var L;
	if (id === "stateSelect") {
		L = citySelectedValue.length;
	} else {
		L = ralley_citySelectedValue.length;
	}
	   for(i = L; i > 0; i--) {
		   if(id === "stateSelect")
			   {citySelectedValue.remove(i);}
		   else
			   {ralley_citySelectedValue.remove(i)}
		   
	   }
	   
	  // citySelectedValue.append("<option value='0'>---Select City----</option>");
	
}
