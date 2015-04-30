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

//send data to execute a function
function sendData(functionToExecute, url, data, elem, subelem) {
	var index = data.indexOf("#"); 
	var res = data.substring(index+1,data.length);
	submitForm(url, res);	
	//retrieve response	
	//alert(url + "/"+ res);
	request(functionToExecute, url + "/"+ res, elem, subelem);//displayRecipe
	//reset the select

	//document.getElementById(elem).options.length = 0;
}


function getXMLHttpRequest() {
	var xhr = null;
	
	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest(); 
		}
	} else {
		alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
		return null;
	}
	
	return xhr;
}

function afficherText(oData, nameDiv){
	document.getElementById(nameDiv).textContent = oData;
}

function displayMessage(oData, nameDiv){
	document.getElementById(nameDiv).textContent = oData;
	//document.getElementById(nameDiv).style.color = 'red';	
}


function resetElement(elem){
	var childs = elem.childNodes;	
	while( elem.firstChild) {
	    // La liste n'est pas une copie, elle sera donc réindexée à chaque appel
		elem.removeChild( elem.firstChild);
	}
}

//si pas de result trouve: affiche un message
function ifNoResultDisplayMsg(uri, nameDiv){
	if(uri.length==0){
		document.getElementById(nameDiv).data = "No results found";
		
		//document.getElementById(nameDiv).style.color = 'red';	
		
		//var option = document.createElement('option');
		//option.appendChild(document.createTextNode("No results found"));
		//var select = document.getElementById(nameDiv); 
		//select.appendChild(option);
	}
	else{
		//document.getElementById(nameDiv).style.color = '#380B61';
	}
}

/***************************** READ DATA FROM REST SERVICES VIA AJAX *****************************************/

//retrieve XML
function request(callback, file, nameDiv, nameSubDiv) {
	var xhr = getXMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			callback(xhr.responseXML, nameDiv, nameSubDiv);
		}
	};
	xhr.open("GET", file, true);
	xhr.send(null);
}


//retrieve text
function requestText(callback, file, nameDiv, nameSubDiv) {
	var xhr = getXMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			callback(xhr.responseText, nameDiv, nameSubDiv);
		}
	};
	
	xhr.open("GET", file, true);
	xhr.send(null);
}


/***************************** SEND DATA TO REST SERVICES VIA AJAX*****************************************/
function submitForm(url, data){
    var xhr;
    try {  xhr = new ActiveXObject('Msxml2.XMLHTTP');   }
    catch (e){
        try {   xhr = new ActiveXObject('Microsoft.XMLHTTP'); }
        catch (e2)
        {
           try {  xhr = new XMLHttpRequest();  }
           catch (e3) {  xhr = false;   }
         }
    }
  
   /* xhr.onreadystatechange  = function(){
       if(xhr.readyState  == 4)
       {
        if(xhr.status  == 200)
            document.ajax.dyn="Received:"  + xhr.responseText;
        else
            document.ajax.dyn="Error code " + xhr.status;
        }
    };*/



     xhr.open( "GET", url + data,  true);
  xhr.send(null);
} 

/***************************** AFFICHER CACHER DU TEXTE *****************************************/
function afficher_cacher(id)
{
        if(document.getElementById(id).style.visibility=="visible")
        {
                document.getElementById(id).style.visibility="hidden";
                //document.getElementById('bouton_'+id).innerHTML='Cacher le texte';
        }
        else
        {
                document.getElementById(id).style.visibility="visible";
                //document.getElementById('bouton_'+id).innerHTML='Afficher le texte';
        }
        return true;
}


