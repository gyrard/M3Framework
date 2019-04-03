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
package eurecom.web.service;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import genericIoTReasoner.application.generic.Generic_Application_Template;
import genericIoTReasoner.application.generic.VariablesFileLocation;
//TO DO: duplication with eurecom package to fix
import knoesis.application.skeleton.VariableSparql;


/**
 * Sensor-based Linked Open Rules (S-LOR) tool <br/>
 * S-LOR is a rule-based reasoning engine and an approach for sharing and reusing interoperable rules to deduce meaningful knowledge from sensor measurements.<br/><br/>
 * 
 * 
 * Demo for the KHealth reasoner to interpret IoT datasets provided by the kHealth project.
 * 
 * S-LOR documentations: http://sensormeasurement.appspot.com/documentation/SLORDocumentation.pdf <br/> 
 * More documentation: http://sensormeasurement.appspot.com/?p=documentation <br/> <br/> 
 * 
 * @author Amelie Gyrard <br/>
 * 
 * Updated: March 2019<br/>
 * - add new scenarios
 * - Issue with deployment (gcloud.app.deploy) Error Response: [13] An internal error occurred.
 * 
 * Updated: August 2018<br/>
 * Created: July 2018 <br/>
 *
 *
 * TO DO: rename the package as slor.web.service? perhaps web.xml must be updated, can generate other issues?
 */
@Path("/healthreasoner")
public class HealthReasonerWS {;

//static String  swot_dataset = Var.LOV4IOT_DATASET_PATH;
static	String m3_onto = Var.M3_ONTOLOGY_PATH;
static	String stac_dataset = Var.STAC_DATASET_PATH;
static	String stac_onto = Var.STAC_ONTOLOGY_PATH;
static	String sparql_query = Var.SPARQL_QUERY_SWOT_TEMPLATE;
Logger logger = Logger.getLogger("Web service");


/**
 * TO DELETE
 * Execute the kHealth reasoner
 * 
 * @return
 * Updated: July 2018
 * Created: July 2018
 */
/*@GET
@Path("/rule/{device}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public static String getRulesSpecificToSensor(@PathParam("device") String device) {
	try{
		SLOR_SearchRule rule = new SLOR_SearchRule(
				Var.LOV4IOT_DATASET_PATH, 
				Var.SPARQL_QUERY_SWOT_TEMPLATE_RULE, 
				Var.RULE_DATASET_PATH);
		return rule.getRuleSpecificToSensor(device);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}*/



/**
 * Read raw data file
 * Updated: March 2019
 * Created: August 2018
 * Hard coded to test the GUI prototype
 * http://localhost:8082/healthreasoner/executeReasoner?scenario=PollenLevel
 */	
@GET
@Path("/readrawdatafile/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public String readRawDataFile(@QueryParam(value = "scenario") String scenario) {
	try {

		System.out.println("read raw data file web service called");
		System.out.println("scenario=" + scenario);

		ArrayList<VariableSparql> variablesToReplaceWithinSparqlQuery = new ArrayList<VariableSparql>();

		if (scenario.compareTo("PollenLevel")==0) {
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_POLLEN_RAW_SENSOR_DATA);
		}
		else if (scenario.compareTo("OutdoorAirQualityIndex")==0) {
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_OUTDOOR_AIR_QUALITY_RAW_SENSOR_DATA);
		}

		else if (scenario.compareTo("OutsideHumidity")==0){//OutsideHumidity
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "OutsideHumidity", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_OUTSIDE_HUMIDITY_RAW_SENSOR_DATA);

		}
		else if (scenario.compareTo("HeartBeat")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "HeartBeat", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_HEART_RATE_RAW_SENSOR_DATA);
		}
		else if (scenario.compareTo("RoomTemperature")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "RoomTemperature", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_INSIDE_TEMPERATURE_RAW_SENSOR_DATA);

		}
			
		//New: March 2019
		else if (scenario.compareTo("SnoringLevel")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "SnoringLevel", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_PATIENT_RAW_SENSOR_DATA_NORMAL_SNORING_LEVEL_PATIENT_SCENARIO);
		}
		else if (scenario.compareTo("CaloricLevel")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "CaloricLevel", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_RAW_SENSOR_DATA_FOOD_HIGH_CALORIC_CONTENT_SCENARIO);
		}
		else if (scenario.compareTo("StepsCount")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "StepsCount", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_ACTIVE_PATIENT_SCENARIO);
		}
		else if (scenario.compareTo("PeakExpiratoryFlow")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "PeakExpiratoryFlow", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_PEAK_FLOW_RAW_SENSOR_DATA);
		}
		else if (scenario.compareTo("BodyMassIndex")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "BodyMassIndex", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_PEAK_FLOW_RAW_SENSOR_DATA);
		}
		else if (scenario.compareTo("SleepDisorderBreathing")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "SleepDisorderBreathing", false));
			return ReadFile.readContentFile(VariablesFileLocation.SENML_XML_PEAK_FLOW_RAW_SENSOR_DATA);
		}
		else {
			System.out.println("No scenario");
			return "No scenario matching";
		}




	} catch (Exception e) {
		e.printStackTrace();
	}
	return "Nothing returned!";
}



/**
 * Updated: March 2019
 * Updated: January 2019
 * Created: August 2018
 * Hard coded to test the GUI prototype
 * http://localhost:8082/healthreasoner/executeReasoner?scenario=PollenLevel
 */	
@GET
@Path("/executeGenericReasoner/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public String executeReasoner(@QueryParam(value = "scenario") String scenario) {
	try {

		System.out.println("execute reasoner web service called");
		System.out.println("scenario=" + scenario);

		ArrayList<VariableSparql> variablesToReplaceWithinSparqlQuery = new ArrayList<VariableSparql>();

		if (scenario.compareTo("PollenLevel")==0) {

			// Retrieve one specific type of data within the graph
			// pollen scenario
			// TO DO: IMPROVEMENT - SHOULD NOT SEARCH FOR the inferred type, but the usual type e.g., kao:PollenLevel
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "PollenLevel", false));


			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_POLLEN_RAW_SENSOR_DATA,
					VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					null,
					VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					VariablesFileLocation.LINKED_OPEN_RULES_POLLEN_KHEALTH,
					VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					variablesToReplaceWithinSparqlQuery);
			return genericAppli.execute();


		}
		else if (scenario.compareTo("OutdoorAirQualityIndex")==0) {

			// Retrieve one specific type of data within the graph
			// pollen scenario
			// TO DO: IMPROVEMENT - SHOULD NOT SEARCH FOR the inferred type, but the usual type e.g., kao:PollenLevel
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "OutdoorAirQualityIndex", false));


			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_OUTDOOR_AIR_QUALITY_RAW_SENSOR_DATA,
					VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					null,
					VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					VariablesFileLocation.LINKED_OPEN_RULES_AIR_QUALITY_KHEALTH,
					VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					variablesToReplaceWithinSparqlQuery);
			return genericAppli.execute();

		}

		else if (scenario.compareTo("OutsideHumidity")==0){//OutsideHumidity
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "OutsideHumidity", false));


			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_OUTSIDE_HUMIDITY_RAW_SENSOR_DATA,
					VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					null,
					VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					VariablesFileLocation.LINKED_OPEN_RULES_OUTSIDE_HUMIDITY_KHEALTH,
					VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					variablesToReplaceWithinSparqlQuery);
			return genericAppli.execute();
		}
		else if (scenario.compareTo("HeartBeat")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "HeartBeat", false));


			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_HEART_RATE_RAW_SENSOR_DATA,
					VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					null,
					VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					VariablesFileLocation.LINKED_OPEN_RULES_HEART_RATE_KHEALTH,
					VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					variablesToReplaceWithinSparqlQuery);
			return genericAppli.execute();
		}
		else if (scenario.compareTo("RoomTemperature")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "RoomTemperature", false));


			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_INSIDE_TEMPERATURE_RAW_SENSOR_DATA,
					VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					null,
					VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					VariablesFileLocation.LINKED_OPEN_RULES_INSIDE_TEMPERATURE_KHEALTH,
					VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					variablesToReplaceWithinSparqlQuery);
			return genericAppli.execute();
		}
		else if (scenario.compareTo("PeakExpiratoryFlow")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "PeakExpiratoryFlow", false));
			
			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_PEAK_FLOW_RAW_SENSOR_DATA,
					VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					null,
					VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					VariablesFileLocation.LINKED_OPEN_RULES_PEAK_FLOW_KHEALTH,
					VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					variablesToReplaceWithinSparqlQuery);
			return genericAppli.execute();
		}
		// SnoringLevel
		else if (scenario.compareTo("SnoringLevel")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "SnoringLevel", false));
						
			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_PATIENT_RAW_SENSOR_DATA_NORMAL_SNORING_LEVEL_PATIENT_SCENARIO,
					 VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					 VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					 null,
					 VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					 VariablesFileLocation.LINKED_OPEN_RULES_HEALTH_SNORING_LEVEL,
					 VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
					 variablesToReplaceWithinSparqlQuery);
			
			return genericAppli.execute();
		}
		// SleepDisorderBreathing
		else if (scenario.compareTo("SleepDisorderBreathing")==0){
			variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "SleepDisorderBreathing", false));
			
			Generic_Application_Template genericAppli = new Generic_Application_Template(
					VariablesFileLocation.SENML_XML_PATIENT_RAW_SENSOR_DATA_NORMAL_SLEEP_DISORDER_BREATHING_PATIENT_SCENARIO,
					 VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
					 VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
					 null,
					 VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
					 VariablesFileLocation.LINKED_OPEN_RULES_SLEEP_DISORDER_BREATHING,
					 VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,//or SPARQL_QUERY_TUTORIAL_PATIENT_STEP_COUNT
					 variablesToReplaceWithinSparqlQuery);
	
			return genericAppli.execute();
		}
		// BodyMassIndex
				else if (scenario.compareTo("BodyMassIndex")==0){
					variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "BodyMassIndex", false));
					
					Generic_Application_Template genericAppli = new Generic_Application_Template(
							VariablesFileLocation.SENML_XML_PATIENT_RAW_SENSOR_DATA_BMI_OBESE,
							 VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
							 VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
							 null,
							 VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
							 VariablesFileLocation.LINKED_OPEN_RULES_OBESITY_BODY_MASS_INDEX,
							 VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,//or SPARQL_QUERY_TUTORIAL_PATIENT_STEP_COUNT
							 variablesToReplaceWithinSparqlQuery);
					
					return genericAppli.execute();
				}
		
		
		
		// add symptom example
		
		//add medication example
		
		// add diet

		// add nut scenario
		
		// add allergy scenario
		
		// add asthma very well controlled scenario
		
		// add obesity BMI scenario
		else {
			System.out.println("No scenario");
			return "No scenario matching";
		}




	} catch (Exception e) {
		e.printStackTrace();
	}
	return "Nothing returned!";
}




/**
 * 
 * Created: August 2018
 * Hard coded to test the GUI prototype
 * http://localhost:8082/healthreasoner/executeReasoner
 */	
@GET
@Path("/executeReasoner/")
@Produces(MediaType.APPLICATION_XML)
public String executeReasoner() {
	try {

		System.out.println("execute reasoner web service called");

		ArrayList<VariableSparql> variablesToReplaceWithinSparqlQuery = new ArrayList<VariableSparql>();

		// Retrieve one specific type of data within the graph
		// pollen scenario
		// TO DO: IMPROVEMENT - SHOULD NOT SEARCH FOR the inferred type, but the usual type e.g., kao:PollenLevel
		variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "PollenLevel", false));


		Generic_Application_Template genericAppli = new Generic_Application_Template(
				VariablesFileLocation.SENML_XML_POLLEN_RAW_SENSOR_DATA,
				VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
				VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
				null,
				VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
				VariablesFileLocation.LINKED_OPEN_RULES_POLLEN_KHEALTH,
				VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
				variablesToReplaceWithinSparqlQuery);

		return genericAppli.execute();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return "Nothing returned!";
}


/**
 * TO DELETE
 * Pollen scenario
 * Created: August 2018
 */	
@GET
@Path("/pollenscenario/")
@Produces(MediaType.APPLICATION_XML)
public String pollenScenario() {
	try {

		ArrayList<VariableSparql> variablesToReplaceWithinSparqlQuery = new ArrayList<VariableSparql>();

		// Retrieve one specific type of data within the graph
		// pollen scenario
		// TO DO: IMPROVEMENT - SHOULD NOT SEARCH FOR the inferred type, but the usual type e.g., kao:PollenLevel
		variablesToReplaceWithinSparqlQuery.add(new VariableSparql("semanticAnnotationTypeUri", VariablesFileLocation.NAMESPACE_KAO + "PollenLevel", false));


		Generic_Application_Template genericAppli = new Generic_Application_Template(
				VariablesFileLocation.SENML_XML_POLLEN_RAW_SENSOR_DATA,
				VariablesFileLocation.GENERATED_SEMANTIC_SENSOR_DATA,
				VariablesFileLocation.KAO_ONTOLOGY_V2_REASONER,
				null,
				VariablesFileLocation.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY,
				VariablesFileLocation.LINKED_OPEN_RULES_POLLEN_KHEALTH,
				VariablesFileLocation.GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT,
				variablesToReplaceWithinSparqlQuery);

		return genericAppli.execute();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return "Nothing returned!";
}


/**
 *  TO DELETE
 * Hard coded to test the GUI prototype
 * http://localhost:8082/healthreasoner/executeReasoner
 */	
@GET
@Path("/test/")
@Produces(MediaType.APPLICATION_XML)
public String bodyTemperatureSick() {



	try{
		return "<?xml version=\"1.0\"?>\r\n" + 
				"<sparql xmlns=\"http://www.w3.org/2005/sparql-results#\">\r\n" + 
				"  <head>\r\n" + 
				"    <variable name=\"name\"></variable>\r\n" + 
				"    <variable name=\"value\"></variable>\r\n" + 
				"    <variable name=\"unit\"></variable>\r\n" + 
				"    <variable name=\"semanticAnnotationType\"></variable>\r\n" + 
				"    <variable name=\"deduce\"></variable>\r\n" + 
				"    <variable name=\"suggest\"></variable>\r\n" + 
				"    <variable name=\"suggest_comment\"></variable>\r\n" + 
				"    <variable name=\"deduceUri\"></variable>\r\n" + 
				"    <variable name=\"suggestUri\"></variable>\r\n" + 
				"  </head>\r\n" + 
				"  <results>\r\n" + 
				"    <result>\r\n" + 
				"      <binding name=\"name\">\r\n" + 
				"        <literal datatype=\"http://www.w3.org/2001/XMLSchema#string\">pollen</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"value\">\r\n" + 
				"        <literal datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">10.4</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"unit\">\r\n" + 
				"        <literal datatype=\"http://www.w3.org/2001/XMLSchema#string\">X</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"semanticAnnotationType\">\r\n" + 
				"        <literal xml:lang=\"en\">Pollen Level</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"deduce\">\r\n" + 
				"        <literal xml:lang=\"en\">High Pollen Level</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"suggest\">\r\n" + 
				"        <literal xml:lang=\"en\">Do Not Go Out</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"suggest_comment\">\r\n" + 
				"        <literal xml:lang=\"en\">Do not go out when pollen is high.</literal>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"deduceUri\">\r\n" + 
				"        <uri>http://purl.org/kao#HighPollenLevel</uri>\r\n" + 
				"      </binding>\r\n" + 
				"      <binding name=\"suggestUri\">\r\n" + 
				"        <uri>http://purl.org/kao#DoNotGoOut</uri>\r\n" + 
				"      </binding>\r\n" + 
				"    </result>\r\n" + 
				"  </results>\r\n" + 
				"</sparql>\r\n" ;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}




}


