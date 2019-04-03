/**
 * August 2018
 * Refactoring TO REMOVE
 * Used in m3 web site not here
 */

/*package eurecom.web.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.data.converter.ConvertSensorDataToM3;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.search.knowledge.ResultDomainKnowledge;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.ExecuteSparqlGeneric;
import eurecom.sparql.result.VariableSparql;


*//**
 * Created June 2016
 * 
 * Refactoring -> move all web services related to semantic annotation here
 * Some web services are from M3WS.java
 * @author Gyrard Amelie
 *
 *//*

@Path("/semanticAnnotator")
public class SemanticAnnotatorWS {
	//ckeck with riina code
	// check in html files domain onto other???

	*//**
	 * Convert SenML/XML data into RDF/XML data according to the M3 ontology
	 * @param urlSenml URI where you find senml sensor data (e.g., http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgd3ZWF0aGVyDA)
	 * @param featureOfInterest to store in google datastore (Java Data Object - JDO)
	 * @return RDF/XML sensor data according to the M3 ontology 
	 *//*
	@GET
	@Path("/convert/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertHealthMeasurement(String urlSenml, String featureOfInterest) {
		ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();
		String sensorMeasurements = ReadFile.queryWebServiceXML(urlSenml);
		String msg ="";
		try {
			msg = m3.convertXMLSenMLIntoRDF(sensorMeasurements, featureOfInterest, featureOfInterest + "Measurement");
		} catch (IOException e) {
			msg = e.getMessage();
		} catch (JAXBException e) {
			msg = e.getMessage();
		}// base name sensor
		return Response.status(200).entity(msg).build();
	}


	*//**
	 * More generic
	 * Last update 22 September 2014
	 * Used by home scenario
	 * @param domain
	 * @return
	 *//*
	@GET
	@Path("/convertGeneric/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertGeneric(@QueryParam(value = "domain") String domain) {
		System.out.println("domain: " + domain);
		if (domain != null && domain.compareTo("BuildingAutomation")==0){
			ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();
			String sensorMeasurements = ReadFile.queryWebServiceXML(Var.URL_SENML_DOMAIN_HOME);
			String msg="";
			try {
				msg = m3.convertXMLSenMLIntoRDF(sensorMeasurements, Var.KIND_JDO_HOME, Var.KEY_NAME_JDO_HOME);
			} catch (IOException | JAXBException e) {
				// TODO Auto-generated catch block
				msg= e.getMessage();
			}// base name sensor
			return Response.status(200).entity(msg).build();
		}
		return Response.status(200).entity("This domain does not exist!").build();
	}
	}
*/