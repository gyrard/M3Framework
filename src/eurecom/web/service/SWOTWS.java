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

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

import org.json.JSONException;

import com.hp.hpl.jena.rdf.model.Model;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.constrained.devices.ConvertRaspberrySensorData;
import eurecom.data.converter.ConvertSensorDataToM3;
import eurecom.search.knowledge.SearchProject;
import eurecom.search.knowledge.SearchRule;


/**
 * SenML to RDF converter web services
 * @author Amelie Gyrard
 *
 */
@Path("/swot")
public class SWOTWS {;

//static String  swot_dataset = Var.LOV4IOT_DATASET_PATH;
static	String m3_onto = Var.M3_ONTOLOGY_PATH;
static	String stac_dataset = Var.STAC_DATASET_PATH;
static	String stac_onto = Var.STAC_ONTOLOGY_PATH;
static	String sparql_query = Var.SPARQL_QUERY_SWOT_TEMPLATE;
Logger logger = Logger.getLogger("Web service");



/**
 * SenML to RDF converter web service from the url
 * @param uriInfo
 * @return
 */
@GET
@Path("/convert_senml_to_rdf/{url}")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public Response getSenmlUrlAndConvert(@Context UriInfo uriInfo) {
	ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();

	//String msg = "I return test web service:" + uriInfo.getPath();
	String[] get_url_requested = uriInfo.getPath().split("http://");

	String res_url_senml= "http://" + get_url_requested[1];
	String sensorMeasurements = ReadFile.queryWebServiceXML(res_url_senml);//res_url_senml
	//System.out.println(res_url_senml);
	//System.out.println(get_url_requested.length + "hello: " +  get_url_requested[1]);
	//	System.out.println(sensorMeasurements);
	String msg ="";
	try {
		msg = m3.convertXMLSenMLIntoRDF(sensorMeasurements, "featureOfInterest", "featureOfInterest" + "Measurement");
	} catch (IOException | JAXBException e) {
		// TODO Auto-generated catch block
		msg= e.getMessage();
	}// base name sensor
	return Response.status(200).entity(msg).build();
}

/**
 * get senml sensor data in a textarea and convert them into RDF
 * Web service used in http://sensormeasurement.appspot.com/?p=senml_converter
 * @param senmlData
 * @return
 */
@GET
@Path("/convert_senml_to_rdf/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public Response getSenmlTextAndConvert(@QueryParam(value = "data") String senmlData, @QueryParam(value = "format") String format) {
	String msg = "No results";	
	System.out.println(format + " " + senmlData);
	try {

		if (format.compareTo("xml")==0){			
			ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();
			msg = m3.convertXMLSenMLIntoRDF(senmlData, "featureOfInterest", "featureOfInterest" + "Measurement");
			System.out.println("if: "+ format + " " +msg);
		}
	/*	else if (format.compareTo("json")==0){			
			ConvertRaspberrySensorData m3 = new ConvertRaspberrySensorData();
			Model model = m3.convertRaspberrySensorDataToRDF(senmlData, Var.RULE_M3_NEW_TYPE);
			msg = model.toString();
			System.out.println("else if: " + format + " " +msg);
		}*/
	} catch (IOException | JAXBException e) {
		// TODO Auto-generated catch block
		msg= e.getMessage();
	}// base name sensor

	return Response.status(200).entity(msg).build();
}



/**
 * Get all devices classified by domains
 * Web service used in http://sensormeasurement.appspot.com/?p=swot_page
 * @param m2mdevice referenced in the M3 nomenclature implemented in the M3 ontology
 * @return
 */
@GET
@Path("/{m2mdevice}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public static String getProjectsSpecificToSensor(@PathParam("m2mdevice") String m2mdevice) {
	SearchProject project = new SearchProject(Var.LOV4IOT_DATASET_PATH, m3_onto, sparql_query, stac_onto, stac_dataset);
	return project.getProjectSpecificToSensor(m2mdevice);
}

/**
 * Get all rules associated to a specific sensor
 * Web service used in http://sensormeasurement.appspot.com/?p=swot_page
 * Web service used in http://sensormeasurement.appspot.com/?p=swot_template
 * @param m2mdevice referenced in the M3 nomenclature implemented in the M3 ontology
 * @return
 */
@GET
@Path("/rule/{m2mdevice}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public static String getRulesSpecificToSensor(@PathParam("m2mdevice") String m2mdevice) {
	try{
		SearchRule rule = new SearchRule(Var.LOV4IOT_DATASET_PATH, Var.SPARQL_QUERY_SWOT_TEMPLATE_RULE, Var.RULE_DATASET_PATH);
		return rule.getRuleSpecificToSensor(m2mdevice);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}





}


