/*******************************************************************************
    Machine to Machine Measurement (M3) Framework 
    Copyright(c) 2012 - 2015 Eurecom

    M3 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.


    M3 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with M3. The full GNU General Public License is 
   included in this distribution in the file called "COPYING". If not, 
   see <http://www.gnu.org/licenses/>.

  Contact Information
  M3 : gyrard__at__eurecom.fr, bonnet__at__eurecom.fr, karima.boudaoud__at__unice.fr
  
The M3 framework has been designed and implemented during Amelie Gyrard's thesis.
She is a PhD student at Eurecom under the supervision of Prof. Christian Bonnet (Eurecom) and Dr. Karima Boudaoud (I3S-CNRS/University of Nice Sophia Antipolis).
This work is supported by the Com4Innov platform of the Pole SCS and DataTweet (ANR-13-INFR-0008). 
  
  Address      : Eurecom, Campus SophiaTech, 450 Route des Chappes, CS 50193 - 06904 Biot Sophia Antipolis cedex, FRANCE

 *******************************************************************************/

/***************************************** BEGIN RESTAURANT ************************************************************************/
function executeRestaurant(url, foodType, city, rating, elem) {
	submitForm(url, foodType);	
	
	//retrieve response	
	request(resultSearchRestaurant, url + "/"+ foodType + "/"+ city + "/"+ rating, elem);
	
	//reset the select	
	var child = document.getElementById(elem).FirstChild;
	//alert(child);
	if(child!=undefined){
		document.getElementById(elem).removeChild(child);
	}
}



function resultSearchRestaurant(oData, nameDiv, select) {
	var location = oData.getElementsByTagName("location");
	var city = oData.getElementsByTagName("city");
	var rating = oData.getElementsByTagName("rating");
	var uri = oData.getElementsByTagName("uri");
	var foodType = oData.getElementsByTagName("foodType");
	var select = document.getElementById(nameDiv);
	var option, cn;
	
	resetElement(select);	
	ifNoResultDisplayMsg(uri, nameDiv);
	
	for (var i=0, c=uri.length; i<c; i++) {		
		option = document.createElement("li");
		if(location[i].firstChild != null && location[i].firstChild.data != null){
			cn = document.createTextNode("Location:" + location[i].firstChild.data +
					", City:" + city[i].firstChild.data + 
					", FoodType:" + foodType[i].firstChild.data +
					", Rating:" + rating[i].firstChild.data 
					) ;
			option.setAttribute('title', uri[i].firstChild.data);
			option.appendChild(cn);
			select.appendChild(option);				
		}		
	}	
}


//gel cities, food type and rating and displays them in a list (select option)
function getCityFoodTypeRating(oData, nameDiv) {
	var label = oData.getElementsByTagName("label");
	var select = document.getElementById(nameDiv), option, cn;

	for (var i=0, c=label.length; i<c; i++) {
		option = document.createElement("option");
		if(label[i].firstChild != null){
			cn = document.createTextNode(label[i].firstChild.data) ;
			option.appendChild(cn);
		}		
		if(select!= null){
			select.appendChild(option);				
		}
	}	
}

/***************************************** END RESTAURANT ************************************************************************/



function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}



/* il faut que dans la page restaurant il y ai un div pour la carte google map dont l'id est map
 * il faut aussi que la page restaurant ai dans son style le fichier style.css */
function showRestaurantOnMap(oData,a,b) {
    // Initialiser la map
    var myLatlng = new google.maps.LatLng(48.856809, 2.3493);
    // Carte centrée sur myLatLng, zoom 12
	var myMapOptions = {
		zoom: 12,
		center: myLatlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var geocoder = new google.maps.Geocoder();
	// Création de la carte
	var myMap = new google.maps.Map(
		document.getElementById('map'),
		myMapOptions
	);
	// placer les points
	var location = oData.getElementsByTagName("location");
	var city = oData.getElementsByTagName("city");
	var rating = oData.getElementsByTagName("rating");
	var uri = oData.getElementsByTagName("uri");
	var foodType = oData.getElementsByTagName("foodType");
	var option, cn;
	var locArray = new Array(3);
	var length = 12;// to get 10 points
	var iGlobal = 0;
	for (var i=0, c=uri.length; i<c && i<length; i++) {
		if(location[i].firstChild != null && location[i].firstChild.data != null) {
			//alert('address:'+location+";"+city);
geocoder.geocode(
		    { 'address': location[i].firstChild.data + " " + city[i].firstChild.data},
		    function(results, status) {
			    if (status == google.maps.GeocoderStatus.OK) {
			        var loc = (location[iGlobal].firstChild === null) ? "?" : location[iGlobal].firstChild.data;
			        var fdt = (foodType[iGlobal].firstChild === null) ? "?" : foodType[iGlobal].firstChild.data;
			        var rat = (rating[iGlobal].firstChild   === null) ? "?" : rating[iGlobal].firstChild.data;
				    var marker = new google.maps.Marker({
					    map: myMap,
					    position: results[0].geometry.location,
					    title: loc +
					    "\nFood Type : " + fdt +
					    "\nRating:" + rat
				    });
				    if(iGlobal == 0){
					    myMap.setCenter(results[0].geometry.location);
				    }
				    //alert("i="+iGlobal+";lat:"+results[0].geometry.location.lat()+";");
				    // centrer la carte sur le premier restaurant
				    iGlobal++;
			    } else {
				    alert("Geocode was not successful for the following reason: " + status);
			    }
});
		}
	}
}

