/**
 * 
 *@Author ketan tank
 * candidateRegistration 
 */

const http = new easyHTTP;

//getting fields

let stateSelectedValue = document.getElementById("stateSelect");
let citySelectedValue = document.getElementById("citySelect");


 
 
stateSelectedValue.addEventListener("change", function() {
	 console.log(this);
	 let data = stateSelectedValue.options[stateSelectedValue.selectedIndex].value;
	    if(data.text !== "0")
	    {
	        addActivityItem(data);
	    }
	    //console.log(activities.value);
	});
 
 function addActivityItem(data){
	 console.log(data);
	 
	// Create Post
	 
	 http.post('getCitiesonbasisofStateSeclected', {stateid : data} , function(err, post) {
	  if(err) {
	    console.log(err);
	  } else {
	    console.log(post);
	    let cities=JSON.parse(post);
	    createCitydropdownData(cities);
	  }
	 });

	 
 }
 
function createCitydropdownData(citiesArrays)  {
	//console.log(JSON.parse(citiesArrays).length);
	removecitiesExcetFirstOption(citiesArrays);
	//Add the Options to the DropDownList.
    for (var i = 0; i < citiesArrays.length; i++) {
        var option = document.createElement("option");
        console.log(citiesArrays[i].city_id);
        //Set Customer Name in Text part.
        option.text = citiesArrays[i].city;

        //Set CustomerId in Value part.
        option.value = citiesArrays[i].city_id;

        //Add the Option element to DropDownList.
        citySelectedValue.appendChild(option);
    }
}
	
function removecitiesExcetFirstOption(citiesArrays){
	
	var i, L = citySelectedValue.length;
	   for(i = L; i > 0; i--) {
		   citySelectedValue.remove(i);
	   }
	   
	  // citySelectedValue.append("<option value='0'>---Select City----</option>");
	
}
