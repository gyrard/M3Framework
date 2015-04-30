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
 * Scenario tourism to suggest activities or garments according to the weather forecasting
 * @author Amelie Gyrard
 *
 */
@Path("/tourism")
public class TourismWS {
	
	/**
	 * Convert SenML weather measurements according to the M3 nomenclature implemented in the M3 ontology
	 * @return RDF sensor data compliant with M3
	 */
	@GET
	@Path("/convert_tourism/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertFoodMNeasuremt() {
		ConvertSensorDataToM3 ag = new ConvertSensorDataToM3();
		String webServiceWeather = ReadFile.queryWebServiceXML(Var.URL_SENML_DOMAIN_WEATHER);
		String msg;
		try {
			msg = ag.convertXMLSenMLIntoRDF(webServiceWeather, Var.KIND_JDO_WEATHER, Var.KEY_NAME_JDO_WEATHER);
		} catch (IOException | JAXBException e) {
			msg= e.getMessage();
		}// base name sensor
		System.out.println(msg);
		return Response.status(200).entity(msg).build();
	}
	
	//@queryParam
		
	/**
	 * Get all activities related to weather measurements
	 * @param property
	 * @return interpretation of weather and suggestions of activities
	 * E.g., sensormeasurement.appspot.com/tourism/activity_weather/Precipitation
	 * E.g., sensormeasurement.appspot.com/tourism/activity_weather/WeatherLuminosity
	 * E.g., sensormeasurement.appspot.com/tourism/activity_weather/WeatherTemperature
	 * E.g., sensormeasurement.appspot.com/tourism/activity_weather/WindSpeed
	 */
	@GET
	@Path("/activity_weather/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String luminosityWeatherActivity(@PathParam("property") String property) {
		try {
			//load the M2M measurement
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + property, false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TOURISM_ONTOLOGY+ "Activity", false));
			
			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all garments related to weather measurements
	 * @param property
	 * @return interpretation of weather and suggestions of garments
	 * E.g., sensormeasurement.appspot.com/tourism/clothes_weather/WeatherLuminosity
	 * E.g., sensormeasurement.appspot.com/tourism/clothes_weather/WeatherTemperature
	 * E.g., sensormeasurement.appspot.com/tourism/clothes_weather/WindSpeed
	 * 
	 */
	@GET
	@Path("/clothes_weather/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String clothesWeather(@PathParam("property") String property) {
		try {
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER, Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + property, false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TOURISM_ONTOLOGY+ "Garment", false));
			
			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Get all garments related to weather measurements
	 * This is a new scenario to test a more complicated rule involving two sensors at the same time
	 * @param property
	 * @return interpretation of snow and suggestions of garments
	 * E.g., sensormeasurement.appspot.com/tourism/snowGarment/
	 */
	@GET
	@Path("/snowGarment/")
	@Produces(MediaType.APPLICATION_XML)
	public String deduceSnowSuggestGarment() {
		String result="No result";
		try {
						
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.SNOW_DATASET);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_WEATHER_DATASET+ "Snowy", false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TOURISM_ONTOLOGY+ "Garment", false));

			result =  m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);
			
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all activities related to weather measurements
	 * This is a new scenario to test a more complicated rule involving two sensors at the same time
	 * @param property
	 * @return interpretation of snow and suggestions of activities
	 * 
	 */
	@GET
	@Path("/snowActivity/")
	@Produces(MediaType.APPLICATION_XML)
	public String deduceSnowSuggestActivity() {
		String result="No result";
		try {
						
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.SNOW_DATASET);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TOURISM_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_WEATHER_DATASET+ "Snowy", false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TOURISM_ONTOLOGY+ "Activity", false));

			result =  m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);
			
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
