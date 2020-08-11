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

let ralley_stateSelectedValue = document.getElementById("ralleystateSelect");
let ralley_citySelectedValue = document.getElementById("ralleycitySelect");

let stateSelectedValue = document.getElementById("stateSelect");
let citySelectedValue = document.getElementById("citySelect");

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
			    
			        for ( var i = 0; i < ralley_citySelectedValue.options.length; i++ ) {
			            if ( ralley_citySelectedValue.options[i].value === optcity.value ) {
			            	ralley_citySelectedValue.options[i].selected = true;
			                return;
			            }
			        }
			    
			  }
			 });
    	stateSelectedValue.selectedIndex = 0;
    	
    	}
    
})


ralley_citySelectedValue.addEventListener("change", function(e) {
	
	 let data = ralley_citySelectedValue.options[ralley_citySelectedValue.selectedIndex].value;
	 console.log(typeof data);
	 if(data !== "0")
		 {
		// Create Post
		 
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
		    	
		    	toogleshowtable()
		    	
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
	    if(data !== "0")
	    {
	        addActivityItem(data,e.target.id);
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
