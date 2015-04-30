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


/**
 * Last Updated: 7 October 2014
 * Add getSparqlQuery web service for generic sparql queries with variables replaced
 * Add convert web service more generic used by home scenario
 * Add iotAppli web service used by home scenario
 * -> add sound scenario
 * -> update room temperature scenario
 * 
 * Add web service format (xml or json) subclassOf web service and searchTemplate
 * @author Gyrard Amelie
 *
 */

@Path("/m3")
public class M3WS {
	//ckeck with riina code
	// check in html files domain onto other???

	/**
	 * Convert SenML/XML data into RDF/XML data according to the M3 ontology
	 * @param urlSenml URI where you find senml sensor data (e.g., http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgd3ZWF0aGVyDA)
	 * @param featureOfInterest to store in google datastore (Java Data Object - JDO)
	 * @return RDF/XML sensor data according to the M3 ontology 
	 */
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
			// TODO Auto-generated catch block
			msg = e.getMessage();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
		}// base name sensor
		return Response.status(200).entity(msg).build();
	}


	/**
	 * More generic
	 * Last update 22 September 2014
	 * Used by home scenario
	 * @param domain
	 * @return
	 */
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

	/**
	 * @param: the M3 measurement name (e.g., RoomTemperature, Sound)
	 * Check with M3 nomenclature http://sensormeasurement.appspot.com/publication/NomenclatureSensorData.pdf
	 * Interpret the room temperature value http://sensormeasurement.appspot.com/m3/iotAppli?measurementName=RoomTemperature
	 * Interpret the sound value http://sensormeasurement.appspot.com/m3/iotAppli?measurementName=Sound
	 * Used by home scenario
	 * @return the interpretation of sensor measurement values
	 */
	@GET
	@Path("/iotAppli/")
	@Produces(MediaType.APPLICATION_XML)
	public String anormalTemperature(@QueryParam(value = "measurementName") String measurementName) {
		try{
			System.out.println("measurementName: " + measurementName);
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_HOME, Var.KEY_NAME_JDO_HOME);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.HOME_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);

			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HOME_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HOME_DATASET_PATH);

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + measurementName, false));// RoomTemperature, Noise
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_HOME_ONTOLOGY + "Status", false));// RoomTemperature, Noise
			
			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_HOME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Query the M3 ontology, all subclassOf
	 * E.G., /subclassOf/Sensor, /subclassOf/FeatureOfInterest
	 * @param nameClass class in the M3 ontology
	 * @return xml or json
	 */
	@GET
	@Path("/subclassOf/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public static Response getAllSensors(@QueryParam("nameClass") String nameClass, @QueryParam(value = "format") String format) {
		//http://localhost:53827/m3/subclassOf/?nameClass=Sensor&format=xml
		M2MAppGeneric m2mappli = new M2MAppGeneric();		
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_PATH);
		ExecuteSparql req = new ExecuteSparql(m2mappli.model, Var.SPARQL_QUERY_GENERIC_SUBCLASSOF);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_M3 + nameClass, false));
		String resultSparql = "";
		if (format.equals("xml")){
			resultSparql = req.getSelectResultAsXML(var);
		}
		else if (format.equals("json")){
			resultSparql = req.getSelectResultAsJson(var);
		}
		return Response
				// Set the status and Put your entity here.
				.ok(resultSparql)
				// Add the Content-Type header to tell Jersey which format it should marshall the entity into.
				.header(HttpHeaders.CONTENT_TYPE, "json".equals(format) ? MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML)
				.build();
	}

	/**
	 * Search IoT application templates according to a sensor and a domain
	 * @param sensorName: name sensor e.g., (LightSensor)
	 * @param domain: name sensor (e.g., Weather)
	 * @param format: xml or json
	 * @return xml or json describing iot application templates
	 */
	@GET
	@Path("/searchTemplate/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public static Response searchIotApplicationTemplate(@QueryParam(value = "sensorName") String sensorName, @QueryParam(value = "domain") String domain, @QueryParam(value = "format") String format) {
		//http://www.sensormeasurement.appspot.com/m3/searchTemplate/?sensorName=LightSensor&domain=Weather&format=json

		M2MAppGeneric m2mappli = new M2MAppGeneric();		
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.IOT_APPPLICATION_TEMPLATE_DATASET);
		//SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE
		ExecuteSparqlGeneric req = new ExecuteSparqlGeneric(m2mappli.model, Var.SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("m2mdevice", Var.NS_M3 + sensorName, false));
		var.add(new VariableSparql("domain", Var.NS_M3 + domain, false));
		String resultSparql = "";
		if (format.equals("xml")){
			resultSparql = req.getSelectResultAsXML(var);
		}
		else if (format.equals("json")){
			resultSparql = req.getSelectResultAsJson(var);
		}
		return Response
				// Set the status and Put your entity here.
				.ok(resultSparql)
				// Add the Content-Type header to tell Jersey which format it should marshall the entity into.
				.header(HttpHeaders.CONTENT_TYPE, "json".equals(format) ? MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML)
				.build();

	}

	/**
	 * Used in javascript and android
	 * Last update 7 October 2014
	 * Cannot directly create the SPARQL generic file with variables replaced
	 * because of app engine (cannot write into a file)
	 * so we created a new service /getSparqlQuery to get the content of the sparql query
	 * @param iotAppli
	 * @return
	 */

	@GET
	@Path("/generateTemplate/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public static Response generateIotApplicationTemplate(@QueryParam(value = "iotAppli") String iotAppli) {
		//http://localhost:58742/m3/generateTemplate/?iotAppli=WeatherTransportationSafetyDevice
		M2MAppGeneric m2mappli = new M2MAppGeneric();		
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.IOT_APPPLICATION_TEMPLATE_DATASET);
		//SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE
		ExecuteSparqlGeneric req = new ExecuteSparqlGeneric(m2mappli.model, Var.SPARQL_QUERY_SWOT_TEMPLATE_M3);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("m2mappli", Var.NS_M3 + iotAppli, false)); //	TO DO: the user chooses an application
		ArrayList<ResultDomainKnowledge> result = req.searchIoTApplicationTemplate(var);
		String msg= "";
		ArrayList<String> files = new ArrayList<String>();

		for (int i = 0; i < result.size(); i++) {

			if (!files.contains(result.get(i).getOnto())){
				files.add(result.get(i).getOnto());				
			}
			if (!files.contains(result.get(i).getRule())){
				files.add(result.get(i).getRule());				
			}
			if (!files.contains(result.get(i).getSparql())){
				files.add(result.get(i).getSparql());				
			}
			if (!files.contains(result.get(i).getDataset())){
				files.add(result.get(i).getDataset());				
			}
		}

		for (int i = 0; i < files.size(); i++) {
			msg += files.get(i) + "@";

		}

		return Response.status(200).entity(msg).build();

	}



	/**
	 * Created 7 October 2014
	 * for SPARQL QUERIES automatically generated
	 * @param iotAppli (e.g., WeatherTransportationSafetyDeviceLight)
	 * @return the sparql query qutomqticqlly generated with variables replaced inside
	 */
	@GET
	@Path("/getSparqlQuery/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public static Response getSPARQLQuery(@QueryParam(value = "iotAppli") String iotAppli) {
		//tested with http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=WeatherTransportationSafetyDeviceLight
		M2MAppGeneric m2mappli = new M2MAppGeneric();		
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.IOT_APPPLICATION_TEMPLATE_DATASET);
		//SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE
		ExecuteSparqlGeneric req = new ExecuteSparqlGeneric(m2mappli.model, Var.SPARQL_QUERY_SWOT_TEMPLATE_M3);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("m2mappli", Var.NS_M3 + iotAppli, false)); //	TO DO: the user chooses an application
		ArrayList<ResultDomainKnowledge> result = req.searchIoTApplicationTemplate(var);
		String msg= "";
		ArrayList<String> files = new ArrayList<String>();
		String sparql_result="";

		for (int i = 0; i < result.size(); i++) {
			//SPARQL QUERY REPLACED
			if (!files.contains(result.get(i).getSparql())){
				//replace sparql query with the variables

				// read file
				sparql_result = ReadFile.readContentFile(Var.SPARQL_QUERY_GENERIC);
				sparql_result = sparql_result.replace("?inferTypeUri", "<" + result.get(i).getInferTypeUri()+ ">");
				sparql_result = sparql_result.replace("?typeRecommendedUri", "<" + result.get(i).getTypeRecommendedUri()+ ">");
				System.out.println(sparql_result);
				files.add(result.get(i).getSparql());				
			}
		}
		//return jo_iot_template.toString();
		return Response.status(200).entity(sparql_result).build();
	}
	
	
	//does not work yet
	@GET
	@Path("/presenceLight/")
	@Produces(MediaType.APPLICATION_XML)
	public String deduceNobodySwitvhOffLight() {
		String result="No result";
		try {
						
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.PRESENCE_LIGHT_DATASET);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HOME_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HOME_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_HOME_DATASET+ "NobodyInTheRoom", false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_HOME_ONTOLOGY+ "Status", false));

			result =  m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_LIGHT_PRESENCE, var, Var.LINKED_OPEN_RULES_HOME);

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//test
	// soumya
	// for androJena
	//replace generateTemplate
	//V2
	//to delete?
	//tested json object and array
	/*@GET
		@Path("/generateIoTTemplate/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public static String generateIotApplicationTemplateJson(@QueryParam(value = "iotAppli") String iotAppli) {
			//http://localhost:58742/m3/generateTemplate/?iotAppli=WeatherTransportationSafetyDevice
			M2MAppGeneric m2mappli = new M2MAppGeneric();		
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.IOT_APPPLICATION_TEMPLATE_DATASET);
			//SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE
			ExecuteSparqlGeneric req = new ExecuteSparqlGeneric(m2mappli.model, Var.SPARQL_QUERY_SWOT_TEMPLATE_M3);
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("m2mappli", Var.NS_M3 + iotAppli, false)); //	TO DO: the user chooses an application
			ArrayList<ResultDomainKnowledge> result = req.searchIoTApplicationTemplate(var);
			String msg= "";
			ArrayList<String> files = new ArrayList<String>();

			JSONArray ja_ontology;
			JSONArray ja_rule;
			JSONArray ja_dataset;
			JSONObject jo_iot_template;

			ja_ontology = new JSONArray();
			ja_dataset = new JSONArray();
			ja_rule = new JSONArray();
			jo_iot_template = new JSONObject();



			JSONObject jo_ontology = new JSONObject();
			JSONObject jo_sparql = new JSONObject();
			JSONObject jo_rule = new JSONObject();
			JSONObject jo_dataset = new JSONObject();

			String inferTypeUri ="";
			String typeRecommendedUri = "";
			String sparql_result="";

			try{
				for (int i = 0; i < result.size(); i++) {

					//GET ONTOLOGIES URL and put it in a JSON OBJECT
					if (!files.contains(result.get(i).getOnto())){
						files.add(result.get(i).getOnto());	
						jo_ontology.append("uri", result.get(i).getOnto());	
					}

					//RULES
					if (!files.contains(result.get(i).getRule())){
						files.add(result.get(i).getRule());	
						jo_rule.append("uri", result.get(i).getRule());
					}

					//DATASET
					if (!files.contains(result.get(i).getDataset())){
						files.add(result.get(i).getDataset());	
						jo_dataset.append("uri", result.get(i).getDataset());			
					}

					//SPARQL QUERY REPLACED
					if (!files.contains(result.get(i).getSparql())){
						//replace sparql query with the variables

						// read file
						sparql_result = ReadFile.readContentFile(Var.SPARQL_QUERY_GENERIC);
						sparql_result = sparql_result.replace("?inferTypeUri", "<" + result.get(i).getInferTypeUri()+ ">");
						sparql_result = sparql_result.replace("?typeRecommendedUri", "<" + result.get(i).getTypeRecommendedUri()+ ">");
						System.out.println(sparql_result);
						//sparql_result.replace("\n", "");
						jo_sparql.append("uri", sparql_result);
						files.add(result.get(i).getSparql());				
					}


				}

				ja_ontology.put(jo_ontology);
				ja_rule.put(jo_rule);
				ja_dataset.put(jo_dataset);

				jo_iot_template.put("ontology", ja_ontology);
				jo_iot_template.put("rule", ja_rule);
				jo_iot_template.put("dataset", ja_dataset);
				jo_iot_template.put("sparql", jo_sparql);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			//return jo_iot_template.toString();
			return sparql_result;

		}*/

}
