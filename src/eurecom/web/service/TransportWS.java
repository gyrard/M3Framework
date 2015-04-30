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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.data.converter.ConvertSensorDataToM3;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.VariableSparql;

/**
 * Scenario transport to suggest safety devices according to the weather forecasting
 * @author Amelie Gyrard
 *
 */
@Path("/transport")
public class TransportWS {
	
	/**
	 * Convert SenML weather measurements according to the M3 nomenclature implemented in the M3 ontology
	 * @return RDF sensor data compliant with M3
	 */
	@GET
	@Path("/convert_transport/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertMNeasuremt() {
		ConvertSensorDataToM3 ag = new ConvertSensorDataToM3();
		String webServiceTransport = ReadFile.queryWebServiceXML(Var.URL_SENML_DOMAIN_TRANSPORT);
		String msg;
		try {
			msg = ag.convertXMLSenMLIntoRDF(webServiceTransport, Var.KIND_JDO_TRANSPORT, Var.KEY_NAME_JDO_TRANSPORT);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			msg= e.getMessage();
		}// base name sensor
		return Response.status(200).entity(msg).build();
	}
	

	/**
	 * Get all safety devices related to weather measurements
	 * @param property
	 * @return interpretation of measurements and suggestions of safety devices
	 * E.g., sensormeasurement.appspot.com/transport/safety_device_weather/Precipitation
	 * E.g., sensormeasurement.appspot.com/transport/safety_device_weather/WeatherLuminosity
	 * E.g., sensormeasurement.appspot.com/transport/safety_device_weather/WeatherTemperature
	 */
	@GET
	@Path("/safety_device_weather/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String luminosityWeatherActivity(@PathParam("property") String property) {
		String result="No result";
		try {
			
			/**
			 *  TEST THE SAME SCENARIO TRANSPORT
			 *  PRECIPITATION
			 *  WEATHER DATASET 5KB
			 *  TEST 1ST ROUND
			 *  AND AVG OTHER ROUND UNTIL 10
			 */		
			
			/* FOR EVALUATION
			int nb_round = 10;
			long avg_sparql = 0;
			long avg_reasoning = 0;
			long avg_access_data = 0;
			
			 for (int i = 0; i < nb_round; i++) {
				
				if (i == 1){//first round need more time, i do not know why
					System.out.println("1st round Access to data:" + Var.TIME_ACCESS_SENSOR_DATA);
					System.out.println("1st round Reasoning:" + Var.TIME_REASONING);
					System.out.println("1st round Sparql:" + Var.TIME_SPARQL_QUERY);
				}
				else{// other rounds faster, if we repeat the same scenario without restarting the server
					avg_access_data = avg_access_data + Var.TIME_ACCESS_SENSOR_DATA;
					avg_reasoning = avg_reasoning + Var.TIME_REASONING;
					avg_sparql = avg_sparql + Var.TIME_SPARQL_QUERY;
				}			*/	
				
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + property, false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TRANSPORT_ONTOLOGY+ "SafetyDevice", false));

			result =  m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);
			// works also for SPARQL_TRANSPORT_SAFETY_DEVICE_LIGHT
			//SPARQL_QUERY_GENERIC
			
			//}
			
			/** FOR EVALUATION
			 * 
			System.out.println("AVG Access to data:" + avg_access_data/nb_round);
			System.out.println("AVG Reasoning:" + avg_reasoning/nb_round);
			System.out.println("AVG Sparql:" + avg_sparql/nb_round);
			 */
			
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all safety devices related to weather measurements
	 * This is a new scenario to test a more complicated rule involving two sensors at the same time
	 * @param property
	 * @return interpretation of snow and suggestions of safety devices
	 * E.g., sensormeasurement.appspot.com/transport/snow/
	 */
	@GET
	@Path("/snow/")
	@Produces(MediaType.APPLICATION_XML)
	public String deduceSnowSuggestSafetyDevicesActions() {
		String result="No result";
		try {
						
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.SNOW_DATASET);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_WEATHER_DATASET+ "Snowy", false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TRANSPORT_ONTOLOGY+ "SafetyDevice", false));

			result =  m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);
				
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Deduce driver's state
	 * Seems it doeas not work anymore
	 * Maybe wrong sensore measurements
	 * Check rules and M3 converter
	 * 
	 * @param property
	 * @return Deduce driver's state
	 * E.g., sensormeasurement.appspot.com/transport/driver_state/SkinConductance
	 */
	@GET
	@Path("/driver_state/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String driverState(@PathParam("property") String property) {
		try {
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_HEALTH , Var.KEY_NAME_JDO_HEALTH);
			
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.HEALTH_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.EMOTION_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.EMOTION_DATASET_PATH);

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + property, false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_EMOTION_ONTOLOGY+ "State", false));
			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_TRANSPORT_DRIVER_STATE, var, Var.LINKED_OPEN_RULES_HEALTH);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/test/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String test(@PathParam("property") String property) {
		String result="No result";
		try {
			
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			/*var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + property, false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TRANSPORT_ONTOLOGY+ "SafetyDevice", false));*/

			result =  m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_TEST_SPARQL_GENERATED, var, Var.LINKED_OPEN_RULES_WEATHER);
			// works also for SPARQL_TRANSPORT_SAFETY_DEVICE_LIGHT
			//SPARQL_QUERY_GENERIC
			
			//}
			
			/** FOR EVALUATION
			 * 
			System.out.println("AVG Access to data:" + avg_access_data/nb_round);
			System.out.println("AVG Reasoning:" + avg_reasoning/nb_round);
			System.out.println("AVG Sparql:" + avg_sparql/nb_round);
			 */
			
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
