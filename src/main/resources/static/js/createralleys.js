/**
 * Create ralley slotwise logic
 */

let start_date=document.getElementById("start_date");
let end_date=document.getElementById("end_date");

end_date.addEventListener("input",function(){
	if(end_date.value === "")
		{
		alert("Please select Start Date");
		return;
		}
	
	var start_jsdate = new Date(start_date.value);
	var end_jsdate = new Date(end_date.value);

	if ( !!start_jsdate.valueOf() ) { // Valid date
	    year = start_jsdate.getFullYear();
	    month = start_jsdate.getMonth();
	    day = start_jsdate.getDate();
	}
	console.log(year+''+month+''+day);



	const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
	const firstDate = new Date(start_jsdate.getFullYear(),start_jsdate.getMonth() ,start_jsdate.getDate());
	const secondDate =new Date(end_jsdate.getFullYear(),end_jsdate.getMonth() ,end_jsdate.getDate());

	const diffDays = Math.round(Math.abs((firstDate - secondDate) / oneDay));
	console.log(diffDays);
	addRow(diffDays)
	
	
	
	//callCreateSlotForm(start_jsdate,end_jsdate,diffDays);
});

 function addRow(diffdays) {
    let listName = 'ralleydaywiseSlot'; //list name in Ralleydetails.class
    let fieldsNames = ['day_date', 'time_of_reporting', 'no_of_intake']; //field names from ralleydaywiseSlot.class
   
for(var i=0;i<=diffdays;i++){
    let row = document.createElement('tr');
    row.classList.add('row', 'item');

    fieldsNames.forEach((fieldName) => {
        let col = document.createElement('td');
        col.classList.add('col', 'form-group');
      
let input = document.createElement('input');
       
    if(fieldName === 'day_date')
    { input.type = 'date';}
     else if(fieldName === 'time_of_reporting')
	{ input.type = 'time';}
	else if(fieldName === 'no_of_intake')
	{ input.type = 'number';}
        
        input.classList.add('form-control');
        input.id = listName + i + '.' + fieldName;
        input.setAttribute('name', listName + '[' + i+ '].' + fieldName);

        col.appendChild(input);
        row.appendChild(col);
    });


    document.getElementById('table-slot1').appendChild(row);
}
}

function callCreateSlotForm(start_date,end_date,diffDays)
{
	let clear=document.getElementById("table-slot1");
	clear.innerHTML = "";
	//create element no basis of days
	let table=document.createElement("table");
	let d=["Days","Time-of-reporting","Slots","intake count"];
	generateTableHead(table,d);
	generateTable(table,diffDays,start_date);
	let a=document.getElementById("table-slot1");
	table.setAttribute("class","table table-striped table-responsive-md")
	a.append(table);
	
	
	}

function generateTableHead(table, data) {
	  let thead = table.createTHead();
	  let row = thead.insertRow();
	  for (let key of data) {
	    let th = document.createElement("th");
	    let text = document.createTextNode(key);
	    th.appendChild(text);
	    row.appendChild(th);
	  }
	}

function generateTable(table, data,start_date) {
	let newstartdate=new Date();
	//let genrateblock=document.createElement("th:block");
	//genrateblock.setAttribute("th:each","ralley : ${ralleyDetails.ralleydaywiseSlot}");
	  for (var i=0;i<=data;i++) {
	    let row = table.insertRow();
	    
	      let cell1 = row.insertCell();
	      let text1 = document.createElement("input");
	      text1.setAttribute("type","date");
	      text1.setAttribute("th:value","${ralley.day_date}");
	      text1.setAttribute("th:name","day_date");
	      text1.setAttribute("class","form-control");
	     
	      let id= "day_date"+i;
	      console.log(id);
	      text1.setAttribute("id",id);
	      let date=new Date(newstartdate.setDate(start_date.getDate()+i));
	      
	      
	     
	      text1.value = formatDateToString(date) ;
	     // console.log(start_date.setDate(start_date.getDate()+1));
	      cell1.appendChild(text1);
	      
	      let cell2 = row.insertCell();
	      let text2 = document.createElement("input");
	      text2.setAttribute("type","time");
	      text2.setAttribute("th:value","${ralley.time_of_reporting}");
	      text2.setAttribute("th:name","time_of_reporting");
	      text2.setAttribute("class","form-control");
	      
	      cell2.appendChild(text2);
	      
	      let cell3 = row.insertCell();
	      let text3 = document.createTextNode("Slot-"+i);
	      cell3.appendChild(text3);
	      
	      let cell4 = row.insertCell();
	      let text4 = document.createElement("input");
	      text4.setAttribute("type","number");
	      text4.setAttribute("min","1");
	      text4.setAttribute("th:value","${ralley.no_of_intake}");
	      text4.setAttribute("th:name","no_of_intake");
	      
	      cell4.appendChild(text4);
	      
	      
	    
	  }
	}

function formatDateToString(date){
	   // 01, 02, 03, ... 29, 30, 31
	   var dd = (date.getDate() < 10 ? '0' : '') + date.getDate();
	   // 01, 02, 03, ... 10, 11, 12
	   var MM = ((date.getMonth() + 1) < 10 ? '0' : '') + (date.getMonth() + 1);
	   // 1970, 1971, ... 2015, 2016, ...
	   var yyyy = date.getFullYear();

	   // create the format you want
	   return yyyy + "-" + MM + "-" + dd;
	}