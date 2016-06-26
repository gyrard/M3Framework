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

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eurecom.common.util.Var;
import eurecom.search.knowledge.SearchProject;
import eurecom.search.knowledge.SearchRule;


/**
 * Sensor-based Linked Open Rules (S-LOR) tool <br/>
 * S-LOR is a rule-based reasoning engine and an approach for sharing and reusing interoperable rules to deduce meaningful knowledge from sensor measurements.<br/><br/>
 * 
 * The web services were previously in SWOTWS <br/>
 * We are refactoring, restructuring the project to ease the understandability <br/> 
 * We updated SLOR demos and SLOR documentations<br/><br/> 
 * 
 * S-LOR demo 1: http://sensormeasurement.appspot.com/?p=slor <br/> 
 * S-LOR demo 2 (Better user interface designed by students): http://sensormeasurement.appspot.com/?p=slorv2 <br/> <br/> 
 * 
 * Web service used in http://sensormeasurement.appspot.com/?p=slor
 * Web service used in http://sensormeasurement.appspot.com/?p=swot_page deprecated
 * Web service used in http://sensormeasurement.appspot.com/?p=slorv2
 * Web service used in http://sensormeasurement.appspot.com/?p=swot_template deprecated
 * 
 * S-LOR documentations: http://sensormeasurement.appspot.com/documentation/SLORDocumentation.pdf <br/> 
 * More documentation: http://sensormeasurement.appspot.com/?p=documentation <br/> <br/> 
 * 
 * @author Amelie Gyrard <br/>
 * 
 * Updated: June 2016 (refactoring of the code - new SLORWS class)<br/>
 * Created: 2015 <br/>
 *
 */
@Path("/slor")
public class SLORWS {;

//static String  swot_dataset = Var.LOV4IOT_DATASET_PATH;
static	String m3_onto = Var.M3_ONTOLOGY_PATH;
static	String stac_dataset = Var.STAC_DATASET_PATH;
static	String stac_onto = Var.STAC_ONTOLOGY_PATH;
static	String sparql_query = Var.SPARQL_QUERY_SWOT_TEMPLATE;
Logger logger = Logger.getLogger("Web service");

/**
 * 
 * Sensor-based Linked Open Rules (S-LOR) tool <br/>
 * 
 * Get all rules associated to a specific sensor
 * E.g.: http://sensormeasurement.appspot.com/slor/rule/BodyThermometer
 * @param device referenced in the M3 nomenclature implemented in the M3 ontology (e.g., BodyThermometer)
 * @return
 * 
 * M3 ontology documentation: http://sensormeasurement.appspot.com/documentation/NomenclatureSensorData.pdf <br/>
 * Created: 2015
 * Updated: June 2016
 */
@GET
@Path("/rule/{device}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public static String getRulesSpecificToSensor(@PathParam("device") String device) {
	try{
		SearchRule rule = new SearchRule(Var.LOV4IOT_DATASET_PATH, Var.SPARQL_QUERY_SWOT_TEMPLATE_RULE, Var.RULE_DATASET_PATH);
		return rule.getRuleSpecificToSensor(device);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}


/**
 * Created: June 2016 <br/><br/>
 * 

 * 
 * Sensor-based Linked Open Rules (S-LOR) tool <br/><br/>
 * 
 * Get all devices classified by domains <br/>
 * Web service used in http://sensormeasurement.appspot.com/?p=swot_page <br/>
 * E.g.: http://sensormeasurement.appspot.com/slor/BodyThermometer
 * @param device referenced in the M3 nomenclature implemented in the M3 ontology (e.g., BodyThermometer)<br/>
 * @return SPARQL results with the list of projects employing this sensor
 * 
 * M3 ontology documentation: http://sensormeasurement.appspot.com/documentation/NomenclatureSensorData.pdf <br/>
 */
@Deprecated
@GET
@Path("/{device}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public static String getProjectsSpecificToSensor(@PathParam("device") String device) {
	SearchProject project = new SearchProject(
			Var.LOV4IOT_DATASET_PATH, 
			m3_onto, 
			sparql_query, 
			stac_onto, 
			stac_dataset);
	return project.getProjectSpecificToSensor(device);
}



}


