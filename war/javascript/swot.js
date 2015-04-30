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

function sendEmail(result_email){
	var to = document.getElementById('to').value;
	
	var paper = document.getElementById('paper').value;

	

	var data = "recipient=" + to + "&paper=" + paper;
	
	request(afficherText, 'lov4iot/sendEmail/?' + data, 'result_email');
	
	alert("Thank you. The message has been sent!");
	
}


/***************************************** SCENARIOS DISPLAY GENERIC SPARQL RESULT ************************************************************************/

/* tourism: suggest activity according to the weather*/
/* transport: suggest safety devices according to the weather*/
/* suggest emotion according to the weather, the luminosity */
/* suggest emotion according to the hearbeat, skin conductance, blood pressure*/
/* display food recommended when you are sick*/
/* display food according to the weather*/
function displayGenericSparqlResult(oData, nameDiv, nameSubDiv) {
	
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="", suggest_comment ="";
	var ul = document.getElementById(nameDiv), li, li2;
	var type = "", indexBegin, indexEnd;

	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		//IF NO RESULT FOUND
		if (result.length == 0){
			//alert(result.length);
			msg = "Sorry, No result found!";
			li = document.createElement(nameSubDiv);
			li2 = document.createElement('li');
			li2.appendChild(document.createTextNode(msg));
			li.appendChild(li2);
			ul.appendChild(li);
		}
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "", suggest_comment="";
			for(k = 0; k < binding.length; k++)	{
				if (binding[k].getAttribute("name") == "name"){
					msg = msg + "Name=" + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "unit"){
					msg = msg + ", Unit=" + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "value"){
					msg = msg + ", Value = " + binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "inferType"){
					msg = msg + ", InferType = " + binding[k].childNodes[1].firstChild.data ;
				}

				if (binding[k].getAttribute("name") == "deduce"){
					msg = msg + ", Deduce = " + binding[k].childNodes[1].firstChild.data ;
				}
				else if (binding[k].getAttribute("name") == "suggest"){
					msg = msg + ", Suggest= " + binding[k].childNodes[1].firstChild.data ;
				}
				else if (binding[k].getAttribute("name") == "suggest_comment"){
					suggest_comment = binding[k].childNodes[1].firstChild.data ;
				}
				
				
			}
			li = document.createElement("li");
			li.setAttribute('title', suggest_comment);//display proof suggestion dc:description
			li.appendChild(document.createTextNode(msg));
			ul.appendChild(li);
			
			
		}
	}
}


/***************************************** IOT APPLICATION TEMPLATE ************************************************************************/

//SEARCH IOT APPLICATION TEMPLATES
//INPUT: SENSOR + DOMAIN
//OUTPUT: IOT APPLI TEMPLATE
function searchIoTApplicationTemplate(){
	var sensor = document.getElementById('sensor').value;
	var index = sensor.indexOf("#"); 
	sensor = sensor.substring(index+1, sensor.length);//get the end of the M3 sensor URI

	var domain = document.getElementById('domain').value;
	index = domain.indexOf("#"); 
	domain = domain.substring(index+1, domain.length);//get the end of the M3 domain URI

	var data = "sensorName=" + sensor + "&domain=" + domain + "&format=xml";

	request(displayProposedIoTApplication, '/m3/searchTemplate/?' + data, 'iot_application_template', 'option');
}

//CHOOSE APPLICATION TEMPLATE
function displayProposedIoTApplication(oData, nameDiv, nameSubDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, m2mappli, msg ="", m2mapplicomment="", m2mapplilabel="";
	var ul = document.getElementById(nameDiv), li;
	var ul2 = document.getElementById('ul'), li2;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(results, nameDiv);//si pas de result trouve// resultat rouge

	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		if (result.length == 0){
			msg = "Sorry, No result found!";
			li = document.createElement(nameSubDiv);
			li2 = document.createElement('li');
			li2.appendChild(document.createTextNode(msg));
			li.appendChild(li2);
			ul.appendChild(li);
		}
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "", m2mapplilabel ="", m2mappli="", ruleComment="";
			for(k = 0; k < binding.length; k++)	{				
				if (binding[k].getAttribute("name") == "m2mapplilabel" && binding[k].childNodes[1].firstChild!=null){
					m2mapplilabel = binding[k].childNodes[1].firstChild.data;
				}
				if (binding[k].getAttribute("name") == "m2mapplicomment" && binding[k].childNodes[1].firstChild!=null){
					m2mapplicomment = binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "m2mappli" && binding[k].childNodes[1].firstChild!=null){
					m2mappli = binding[k].childNodes[1].firstChild.data ;
				}


			}
			li = document.createElement(nameSubDiv);
			li.setAttribute('title', m2mapplicomment);
			li.setAttribute('value', m2mappli);
			li.appendChild(document.createTextNode(m2mapplilabel));
			ul.appendChild(li);
		}
	}
}


//STEP 2: GENERATE APLLICATION TEMPLATE (ONTO + DATASET + SPARQL + VAR + RULES)
function generateIoTApplicationTemplate(){
	var iot_application_template = document.getElementById('iot_application_template').value;
	var index = iot_application_template.indexOf("#"); 	
	iot_application_template = iot_application_template.substring(index+1, iot_application_template.length);//get the end of the appli template URI
	requestText(generateIoTApplication, '/m3/generateTemplate/?' + "iotAppli=" +iot_application_template, 'generate_iot_application_template', 'li');
}

//TO DISPLAY THE ONTOLOGY, DATASET, SPARQL AND RULE URL
function generateIoTApplication(oData, nameDiv, nameSubDiv) {
	var ul = document.getElementById(nameDiv), li;
	var files = oData.split("@"); ;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(files, nameDiv);//si pas de result trouve// resultat rouge 

/*	for(i = 0; i < files.length-1; i++)	{
		li = document.createElement(nameSubDiv);
		li.appendChild(document.createTextNode(files[i]));
		ul.appendChild(li);

	}*/	
	
	create_zip(files);
}

//READ FILES (ONTOLOGIES, DATASETS, RULES, SPARQL)
function readTextFile(file)
{
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status == 0)
            {
                var allText = rawFile.responseText;//here we get all lines from text file*
              
                document.getElementById('content_file').textContent = allText;
                
            }
        }
    }
    rawFile.send(null);
}



//CREATE A ZIP FILE WITH THE ONTOLOGIES, SPARQL, DATASET AND RULES
//IOT APPLICATION TEMPLATE
function create_zip(files) {
	var zip = new JSZip();

	for(i = 0; i < files.length-1; i++)	{
		var fileName = files[i];
		//IF ONTOLOGY
		if (fileName.lastIndexOf("#")>0){
			//http://sensormeasurement.appspot.com/ont/m3/transport#
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length-1);// get transport
			readTextFile("./ont/m3/" + fileName);
			zip.file(fileName + ".owl", document.getElementById('content_file').textContent);
		}

		//IF RULES
		if (fileName.lastIndexOf(".txt")>0){
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length);
			readTextFile("./RULES/" + fileName);
			zip.file(fileName, document.getElementById('content_file').textContent);
		}  

		//IF SPARQL
		if (fileName.lastIndexOf(".sparql")>0){
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length);
			readTextFile("./SPARQL/template/" + fileName);
			zip.file(fileName, document.getElementById('content_file').textContent);
		} 

		//IF DATASET 
		// should be at the end, since in all URL you have / 
		else{
			fileName= fileName.substring(0, fileName.lastIndexOf("/"));//before we cannot
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length+1);
			if (fileName!=""){
				readTextFile("./dataset/" + fileName);
				zip.file(fileName + ".rdf", document.getElementById('content_file').textContent);
			}
		}
	}

	//GENERATE THE ZIP FILE
	var content = null;
	if (JSZip.support.uint8array) {
		content = zip.generate({type : "uint8array"});
	} else {
		content = zip.generate({type : "string"});
	}
	var blob = zip.generate({type:"blob"});
	saveAs(blob, "M3IoTApplicationTemplate.zip");
}

/***************************************** LOV4IoT ************************************************************************/


//web page swot template
//the user chooses a sensor, all projects and related information using this sensor are displayed
function displayResultSwotTemplate(oData, nameDiv, nameSubDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="", comment="", uri="", project="", security="", techno="", security_mechanism_comment="", onto="", rule="", domain="", security_uri="";
	var ul = document.getElementById(nameDiv), li;
	var ul2 = document.getElementById('ul'), li2;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(results, nameDiv);//si pas de result trouve// resultat rouge

	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		//IF NO RESULT FOUND
		if (result.length == 0){
			//alert(result.length);
			msg = "Sorry, No result found!";
			li = document.createElement(nameSubDiv);
			li2 = document.createElement('li');
			li2.appendChild(document.createTextNode(msg));
			li.appendChild(li2);
			ul.appendChild(li);
		}
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "", m2mappli_comment ="", uri="", security_uri="", domain = "", security="", techno="", onto="", rule="", security_mechanism_comment="";
			for(k = 0; k < binding.length; k++)	{				
				if ((binding[k].getAttribute("name") == "domain_label") && binding[k].childNodes[1].firstChild!=null){
					domain = binding[k].childNodes[1].firstChild.data;
				}
				if ((binding[k].getAttribute("name") == "m2mappli_label") && binding[k].childNodes[1].firstChild!=null){
					project = binding[k].childNodes[1].firstChild.data;
				}
				if (binding[k].getAttribute("name") == "m2mappli_comment" && binding[k].childNodes[1].firstChild!=null){
					m2mappli_comment = binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "security_mechanism" && binding[k].childNodes[1].firstChild!=null){
					security_uri = binding[k].childNodes[1].firstChild.data ;
				}
				if (binding[k].getAttribute("name") == "security_mechanism_comment" && binding[k].childNodes[1].firstChild!=null){
					security_mechanism_comment = binding[k].childNodes[1].firstChild.data ;
				}
				if ((binding[k].getAttribute("name") == "security_mechanism_label") && binding[k].childNodes[1].firstChild!=null){
					security = binding[k].childNodes[1].firstChild.data;
				}
				if ((binding[k].getAttribute("name") == "techno_label") && binding[k].childNodes[1].firstChild!=null){
					techno =   "Technology: " + binding[k].childNodes[1].firstChild.data ;
				}				
				if ((binding[k].getAttribute("name") == "onto") && binding[k].childNodes[1].firstChild!=null){
					onto = binding[k].childNodes[1].firstChild.data ;
				}
				if ((binding[k].getAttribute("name") == "rule") && binding[k].childNodes[1].firstChild!=null){
					rule =   binding[k].childNodes[1].firstChild.data ;
				}

			}
			li = document.createElement(nameSubDiv);
			li2 = document.createElement('li');
			if(domain!=""){
				li2.appendChild(document.createTextNode("Domain: "+ domain));
				li2.appendChild(document.createElement("br"));
			}
			if(project!=""){
				li2.appendChild(document.createTextNode("Project: "+ project + ", " + m2mappli_comment ));
				li2.appendChild(document.createElement("br"));
			}		
			if(security!=""){
				li2.appendChild(document.createTextNode("Security mechanism: "+ security));
				li2.appendChild(document.createElement("br"));
			}
			if(techno!=""){//a href = url onto
				li2.appendChild(document.createTextNode(techno));
				li2.appendChild(document.createElement("br"));
			}
			if(onto!=""){
				li2.appendChild(document.createTextNode("Ontology url: "));
				var createA = document.createElement('a');
				var createAText = document.createTextNode(onto);
				createA.setAttribute('href', onto);
				createA.appendChild(createAText);
				li2.appendChild(createA);
				li2.appendChild(document.createElement("br"));
			}
			if(rule!=""){
				li2.appendChild(document.createTextNode("Rule url: "));
				var createA = document.createElement('a');
				var createAText = document.createTextNode(rule);
				createA.setAttribute('href', rule);
				createA.appendChild(createAText);
				li2.appendChild(createA);
				li2.appendChild(document.createElement("br"));
			}

			li.appendChild(li2);
			ul.appendChild(li);
		}
	}
}

/***************************************** S-LOR ************************************************************************/


//web page swot template
//the user chooses a sensor, all rules and related information using this sensor are displayed
function displayResultRuleTemplate(oData, nameDiv, nameSubDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="", ruleComment="", ruleLabel="", ruleLabel="", project="", ruleUrl="";
	var ul = document.getElementById(nameDiv), li;
	var ul2 = document.getElementById('ul'), li2;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(results, nameDiv);//si pas de result trouve// resultat rouge

	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		if (result.length == 0){
			//alert(result.length);
			msg = "Sorry, No result found!";
			li = document.createElement(nameSubDiv);
			li2 = document.createElement('li');
			li2.appendChild(document.createTextNode(msg));
			li.appendChild(li2);
			ul.appendChild(li);
		}
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "", ruleLabel ="", uri="", ruleComment="", project="", ruleUrl="";
			for(k = 0; k < binding.length; k++)	{				
				if ((binding[k].getAttribute("name") == "ruleLabel") && binding[k].childNodes[1].firstChild!=null){
					ruleLabel = binding[k].childNodes[1].firstChild.data;
				}
				if (binding[k].getAttribute("name") == "ruleComment" && binding[k].childNodes[1].firstChild!=null){
					ruleComment = binding[k].childNodes[1].firstChild.data ;
				}
				if ((binding[k].getAttribute("name") == "m2mappli_label") && binding[k].childNodes[1].firstChild!=null){
					project = binding[k].childNodes[1].firstChild.data;
				}
				if ((binding[k].getAttribute("name") == "lorUrl") && binding[k].childNodes[1].firstChild!=null){
					ruleUrl = binding[k].childNodes[1].firstChild.data;
				}

			}
			li = document.createElement(nameSubDiv);
			li2 = document.createElement('li');
			if(ruleLabel!="" && ruleComment!=""){
				li2.appendChild(document.createTextNode("Rule: "+ ruleLabel + ", " + ruleComment));
				li2.appendChild(document.createElement("br"));
			}
			if(project!="" ){
				li2.appendChild(document.createTextNode("Project: "+  project));
				li2.appendChild(document.createElement("br"));
			}
			if(ruleUrl!=""){
				li2.appendChild(document.createTextNode("Linked Open Rules URL: "));
				var createA = document.createElement('a');
				var createAText = document.createTextNode(ruleUrl);
				createA.setAttribute('href', ruleUrl);
				createA.appendChild(createAText);
				li2.appendChild(createA);
				li2.appendChild(document.createElement("br"));
			}

			li.appendChild(li2);
			ul.appendChild(li);
		}
	}
}


function executeSwotAndRuleTemplateRfid(){
	sendData(displayResultSwotTemplate,'/swot', document.getElementById('rfid').value, 'rfid_template', 'li');
	sendData(displayResultRuleTemplate,'/swot/rule', document.getElementById('rfid').value, 'rfid_rule_template', 'li');
}

function executeSwotAndRuleTemplateActuator(){
	sendData(displayResultSwotTemplate,'/swot', document.getElementById('actuator').value, 'actuator_template', 'li');
	sendData(displayResultRuleTemplate,'/swot/rule', document.getElementById('actuator').value, 'actuator_rule_template', 'li');
}

function executeSwotAndRuleTemplateSensor(){
	sendData(displayResultSwotTemplate,'/swot', document.getElementById('sensor').value, 'sensor_template', 'li');
	sendData(displayResultRuleTemplate,'/swot/rule', document.getElementById('sensor').value, 'sensor_rule_template', 'li');
}

//used by ontologies.html to show the m3 ontologies: sensors, domains, etc
//used by swot template tool
//used by security
function displayResultBindingSelect(oData, nameDiv, nameSubDiv) {
	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="", comment="", uri="";
	var ul = document.getElementById(nameDiv), li;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(results, nameDiv);//si pas de result trouve// resultat rouge

	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "", comment ="", uri="";
			for(k = 0; k < binding.length; k++)	{

				if ((binding[k].getAttribute("name") == "label" || binding[k].getAttribute("name") == "label_en")
						&& binding[k].childNodes[1].firstChild!=null){
					msg = msg  + binding[k].childNodes[1].firstChild.data;
				}
				if ((binding[k].getAttribute("name") == "uri" || binding[k].getAttribute("name") == "subject" )
						&& binding[k].childNodes[1].firstChild!=null){
					uri = uri + binding[k].childNodes[1].firstChild.data ;
				}
				if ( (binding[k].getAttribute("name") == "comment" || binding[k].getAttribute("name") == "paper") && binding[k].childNodes[1].firstChild!=null){
					comment = comment  + binding[k].childNodes[1].firstChild.data ;
				}
	
			}
			li = document.createElement(nameSubDiv);
			li.setAttribute('title', comment);
			li.setAttribute('value', uri);
			li.appendChild(document.createTextNode(msg));
			ul.appendChild(li);
		}
	}
}


function displayLOV4IOTStatistics(oData, nameDiv, nameSubDiv) {

	var racine = oData.documentElement;
	var results = racine.getElementsByTagName("results");
	var result, binding, uri, msg ="", comment="", uri="";
	var ul = document.getElementById(nameDiv), li;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(results, nameDiv);//si pas de result trouve// resultat rouge

	for(i = 0; i < results.length; i++)	{//all results only 1 tour
		result = results[i].getElementsByTagName("result");
		for(j = 0; j < result.length; j++)	{
			binding = result[j].getElementsByTagName("binding");
			msg = "", comment ="", uri="";
			for(k = 0; k < binding.length; k++)	{

				if ((binding[k].getAttribute("name") == "ontologyTotal" )
						&& binding[k].childNodes[1].firstChild!=null){
					msg = msg  + binding[k].childNodes[1].firstChild.data;
				}
				if ((binding[k].getAttribute("name") == "nbOntoDomain" )
						&& binding[k].childNodes[1].firstChild!=null){
					msg = msg  + binding[k].childNodes[1].firstChild.data;
				}
			}
			
			document.getElementById(nameDiv).innerHTML=msg;
			// You need to use innerHTML not value
			// .value is a property assigned to input elements like input, select, textarea

		}
	}
}

/***************************************** BEGIN SECURITY ************************************************************************/

function searchInformationRelatedToTechnology(subelem) {
	sendData(displayResultBindingSelect,'/stac/techno', document.getElementById('network_comm').value, 'securityMechanism_network_comm', subelem);
	sendData(displayResultBindingSelect,'/stac/attack', document.getElementById('network_comm').value, 'attack_network_comm', subelem);
}	



function searchCountermeasureInformation(url, data, feature, securityProperty, subelem) {
	var index = data.lastIndexOf("/"); 
	var res = data.substring(index+1,data.length);
	submitForm(url, res);	
	//retrieve response	
	document.getElementById(feature).options.length = 0;
	document.getElementById(securityProperty).options.length = 0;

	request(displayResultBindingSelect, url + "/hasFeature/"+ res, feature, subelem);
	request(displayResultBindingSelect, url + "/satisfy/"+ res, securityProperty, subelem);
}

function searchAccessControlMethod(url, data, elem, subElem) {
	var index = data.indexOf("#"); 
	var res = data.substring(index+1,data.length);
	submitForm(url, res);	
	//retrieve response	

	request(displayResultBindingSelect, url + "/"+ res, elem, subElem);//displayListXml
}

function callWebServiceInstance(url, data, elem, subElem) {
	var index = data.lastIndexOf("/"); 
	var res = data.substring(index+1,data.length);
	submitForm(url, res);	
	//retrieve response	
	request(displayResultBindingSelect, url + "/"+ res, elem, subElem);

	//reset the select
	document.getElementById(elem).options.length = 0;
}


/***************************************** BEGIN SENML ************************************************************************/

// sensor data are froma an url
function convertSenMLToRDF(){
	var url = document.getElementById('url').value;
	requestText(displaySenmlConvertToRDF, "/swot/convert_senml_to_rdf/"+ encodeURIComponent(url), "res_convert");
}


//sensor data are in a textarea
function convertSenMLToRDFFormat(format){	

	if (format=="xml"){
		var sensor_data = document.getElementById('senmlText').value;
		requestText(displaySenmlConvertToRDF, "/swot/convert_senml_to_rdf/?data="+ sensor_data + "&format=" + format, "res_convert_xml" );
	}
	else if	(format=="json"){
		var sensor_data = document.getElementById('senmlTextJson').value;
		requestText(displaySenmlConvertToRDF, "/swot/convert_senml_to_rdf/?data="+ sensor_data + "&format=" + format, "res_convert");
	}
}

function displaySenmlConvertToRDF(oData, nameDiv) {
	alert(oData);// do not delete this alert, used for users

	//	var link = document.createElement('textarea');
	//link.appendChild(document.createTextNode(oData));
	// document.getElementById('content').appendChild(link);
	document.getElementById(nameDiv).innerText = oData ;
}

function readFoodTempSeason(oData, nameDiv) {
	var food = oData.getElementsByTagName("food");
	var season = oData.getElementsByTagName("season");
	var ul = document.getElementById(nameDiv), li;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(food, nameDiv);//si pas de result trouve

	for (var i=0; i<food.length; i++) {
		li = document.createElement("li");
		li.appendChild(document.createTextNode("Food: " + food[i].firstChild.data 
				+ ", Season: " + season[i].firstChild.data));
		ul.appendChild(li);
	}		
}

function readSenml(oData, nameDiv) {
	var name = oData.getElementsByTagName("name");
	var time = oData.getElementsByTagName("time");
	var unit = oData.getElementsByTagName("unit");
	var value = oData.getElementsByTagName("value");
	var uri = oData.getElementsByTagName("uri");
	var ul = document.getElementById(nameDiv), li;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(uri, nameDiv);//si pas de result trouve

	for (var i=0; i<uri.length; i++) {
		li = document.createElement("li");
		li.appendChild(document.createTextNode("Uri: " + uri[i].firstChild.data 
				+ ", Name: " + name[i].firstChild.data
				+ ", Units: " + unit[i].firstChild.data
				+ ", Value: " + value[i].firstChild.data
				+ ", Time: " + time[i].firstChild.data));
		ul.appendChild(li);
	}		
}

function readSenmlSeason(oData, nameDiv) {
	var name = oData.getElementsByTagName("name");
	var unit = oData.getElementsByTagName("unit");
	var value = oData.getElementsByTagName("value");
	var uri = oData.getElementsByTagName("uri");
	var season = oData.getElementsByTagName("season");
	var ul = document.getElementById(nameDiv), li;

	resetElement(ul);//vider la liste existante
	ifNoResultDisplayMsg(uri, nameDiv);//si pas de result trouve

	for (var i=0; i<uri.length; i++) {
		li = document.createElement("li");
		li.appendChild(document.createTextNode("Uri: " + uri[i].firstChild.data 
				+ ", Name: " + name[i].firstChild.data
				+ ", Units: " + unit[i].firstChild.data
				+ ", Value: " + value[i].firstChild.data
				+ ", Season: " + season[i].firstChild.data));
		ul.appendChild(li);
	}
}	


/***************************************** END SENML ************************************************************************/



