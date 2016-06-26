/*******************************************************************************
Created June 2016


File used to deduce meaningful information from sensor data

Replace call to web services: /swot/rule and /swot
by /slor/rule and /slor

Amelie Gyrard

Refactoring to clean the project

 *******************************************************************************/


//used by SLOR
//move to javascript/SLOR/slor.js
function executeSwotAndRuleTemplateRfid(){
	sendData(displayResultSwotTemplate,'/slor', document.getElementById('rfid').value, 'rfid_template', 'li');
	sendData(displayResultRuleTemplate,'/slor/rule', document.getElementById('rfid').value, 'rfid_rule_template', 'li');
}

//used by SLOR
//move to javascript/SLOR/slor.js
function executeSwotAndRuleTemplateActuator(){
	sendData(displayResultSwotTemplate,'/slor', document.getElementById('actuator').value, 'actuator_template', 'li');
	sendData(displayResultRuleTemplate,'/slor/rule', document.getElementById('actuator').value, 'actuator_rule_template', 'li');
}

//used by SLOR
//move to javascript/SLOR/slor.js
function executeSwotAndRuleTemplateSensor(){
	sendData(displayResultSwotTemplate,'/slor', document.getElementById('sensor').value, 'sensor_template', 'li');
	sendData(displayResultRuleTemplate,'/slor/rule', document.getElementById('sensor').value, 'sensor_rule_template', 'li');
}