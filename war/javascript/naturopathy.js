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
//naturopathy
function callWebServiceInstance(url, data, elem) {
	var index = data.lastIndexOf("/"); 
	var res = data.substring(index+1,data.length);
	submitForm(url, res);	
	//retrieve response	
	request(displayFood, url + "/"+ res, elem);
	
	//reset the select
	document.getElementById(elem).options.length = 0;
}

//naturopathy
function displayDisease(oData, nameDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="";
	var ul = document.getElementById(nameDiv), li;
		
	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "";
			for(k = 0; k < binding.length; k++)	{
				if (binding[k].getAttribute("name") == "label_en"){
				msg = msg + "" + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "label_fr"){
				msg = msg + ", " + binding[k].childNodes[1].firstChild.data ;
				}
				
			}
		li = document.createElement("option");
		li.appendChild(document.createTextNode(msg));
		ul.appendChild(li);
		}
	}
}

//naturopathy
function displayFood(oData, nameDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="";
	var select = document.getElementById(nameDiv), option;
		
	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "";
			for(k = 0; k < binding.length; k++)	{
				if (binding[k].getAttribute("name") == "food_label_en"){
				msg = msg + "" + binding[k].childNodes[1].firstChild.data ;
				}
				
				option = document.createElement("option");
				option.setAttribute('value', binding[k].childNodes[1].firstChild.data);
			}

		option.appendChild(document.createTextNode(msg));
		select.appendChild(option);
		}
	}
}



/* display food recommended when you have cholesterol*/
function searchFoodIfCholesterol(oData, nameDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result;
	var binding;
	var uri;
	var ul = document.getElementById(nameDiv), li;
	var msg ="";	
	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "";
			for(k = 0; k < binding.length; k++)	{
				if (binding[k].getAttribute("name") == "value"){
					msg = msg + "Value = " + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "unit"){
					msg = msg + ", Unit = " + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "typeLabel"){
					msg = msg + ", Type = " + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "ingtLabel"){
					msg = msg + ", Food = " + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "recipeLabel"){
					msg = msg + ", Recipe = " + binding[k].childNodes[1].firstChild.data ;
				}
			}
		li = document.createElement("li");
		li.appendChild(document.createTextNode(msg));
		ul.appendChild(li);
		}
	}
}



function displayIngredientFat(oData, nameDiv, subelem) {
	var ingtUri = oData.getElementsByTagName("ingtUri");
	var ul = document.getElementById(nameDiv), li;
	
	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(ingtUri, nameDiv);//si pas de result trouve

	for (var i=0; i<ingtUri.length; i++) {
		li = document.createElement(subelem);
		li.setAttribute('value', ingtUri[i].firstChild.data);
		
		//get the end of the uri
		var ingt = ingtUri[i].firstChild.data;
		ingt = ingt.substring(ingt.indexOf('#') + 1, ingt.length + " " );
				
		li.appendChild(document.createTextNode(ingt + " "));
		ul.appendChild(li);		
	}	
	
}

function getRecipeLight(oData, nameDiv, subelem) {
	var recipeUri = oData.getElementsByTagName("recipeUri");
	var recipeLabel = oData.getElementsByTagName("recipeLabel");
	var ingtUri = oData.getElementsByTagName("ingtUri");
	var ul = document.getElementById(nameDiv), li;
	
	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(recipeUri, nameDiv);//si pas de result trouve
	
	var listIngt = " ";
	
	for (var i=0; i<recipeUri.length; i++) {
		li = document.createElement(subelem);
		li.setAttribute('value', recipeUri[i].firstChild.data);
		li.appendChild(document.createTextNode(recipeLabel[i].firstChild.data));
		
		ul.appendChild(li);
	}		
}


function getAllElementsWithAttribute(oData, attribute)
{
  var matchingElements = [];
  var allElements = oData.getElementsByTagName('*');
  for (var i = 0; i < allElements.length; i++)
  {
    if (allElements[i].getAttribute(attribute))
    {
      // Element exists with attribute. Add to array.
      matchingElements.push(allElements[i]);
    }
  }
  return matchingElements;
}



/***************************************** BEGIN SMART FRIDGE ************************************************************************/

function readFoodSeason(oData, nameDiv, subelem) {
	var food = oData.getElementsByTagName("food");
	var season = oData.getElementsByTagName("season");

	var ul = document.getElementById(nameDiv), li;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(food, nameDiv);//si pas de result trouve
	
	for (var i=0; i<food.length; i++) {
		li = document.createElement(subelem);
		li.appendChild(document.createTextNode("Food: " + food[i].firstChild.data 
				+ ", Season: " + season[i].firstChild.data));
		ul.appendChild(li);
	}		
}

function displayRecipe(oData, nameDiv, subelem) {
	var label = oData.getElementsByTagName("recipeLabel");
	var uri = oData.getElementsByTagName("recipeUri");
	var ul = document.getElementById(nameDiv), li;
	
	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(uri, nameDiv);//si pas de result trouve

	for (var i=0; i<uri.length; i++) {
		li = document.createElement(subelem);
		li.setAttribute('value', uri[i].firstChild.data);
		li.appendChild(document.createTextNode(label[i].firstChild.data));
		
		ul.appendChild(li);
	}		
}

function displayRecipeIngredient(oData, nameDiv, subelem) {
	var ingtUri = oData.getElementsByTagName("ingtUri");
	var ingtQuantity = oData.getElementsByTagName("ingtQuantity");
	var ul = document.getElementById(nameDiv), li;
	
	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(ingtUri, nameDiv);//si pas de result trouve

	for (var i=0; i<ingtUri.length; i++) {
		li = document.createElement(subelem);
		li.setAttribute('value', ingtUri[i].firstChild.data);
		
		//get the end of the uri
		var ingt = ingtUri[i].firstChild.data;
		ingt = ingt.substring(ingt.indexOf('#') + 1, ingt.length );
		
		//get the end of the uri
		var quantity = ingtQuantity[i].firstChild.data;
		quantity = quantity.substring(quantity.indexOf('#') + 1, quantity.length );
		
		li.appendChild(document.createTextNode(ingt + ": " + quantity));
		
		ul.appendChild(li);
	}		
}

function getIngtInKitchen(oData, nameDiv, subelem) {
	var label = oData.getElementsByTagName("label");
	var uri = oData.getElementsByTagName("uri");
	var ul = document.getElementById(nameDiv), li;
	
	for (var i=0; i<uri.length; i++) {
		li = document.createElement(subelem);
		li.setAttribute('value', uri[i].firstChild.data);
		li.appendChild(document.createTextNode(label[i].firstChild.data));
		
		ul.appendChild(li);
	}		
}


/***************************************** SMART FRIDGE ************************************************************************/
