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
import java.util.Set;

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
import eurecom.common.util.WSUtils;
import eurecom.data.converter.ConvertSensorDataToM3;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.generic.m2mapplication.M2MAppNaturopathy;
import eurecom.generic.m2mapplication.M2MAppRecipe;
import eurecom.sparql.result.SparqlResultMatchingFood;
import eurecom.sparql.result.SparqlResultRecipe;
import eurecom.sparql.result.SparqlResultRecipeNaturopathy;
import eurecom.sparql.result.VariableSparql;

/**
 * Naturopathy scenarios used in the corresponding HTML web page
 * @author Amelie Gyrard
 *
 */
@Path("/naturopathy")
public class NaturopathyWS {
	
	/**
	 * Suggest home remedies according to the body temperature scenario
	 * @return home remedies according to the body temperature
	 */	
	@GET
	@Path("/sick/")
	@Produces(MediaType.APPLICATION_XML)
	public String bodyTemperatureSick() {
		try{
			//load the M2M measurement
		//	M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_HEALTH, Var.KEY_NAME_JDO_HEALTH);
				
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.HEALTH_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);

			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HEALTH_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HEALTH_DATASET_PATH);
	
			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "BodyTemperature", false));// we look for body temperature measurement


			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_HEALTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Convert SenML health measurements according to the M3 nomenclature implemented in the M3 ontology
	 * @return RDF sensor data compliant with M3
	 */
	@GET
	@Path("/convert_health/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertHealthMeasurement() {
		ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();
		String sensorMeasurements = ReadFile.queryWebServiceXML(Var.URL_SENML_DOMAIN_HEALTH);
		String msg;
		try {
			msg = m3.convertXMLSenMLIntoRDF(sensorMeasurements, Var.KIND_JDO_HEALTH, Var.KEY_NAME_JDO_HEALTH);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			msg= e.getMessage();
		}// base name sensor
		return Response.status(200).entity(msg).build();
	}
	
	/**
	 * Convert SenML health measurements according to the M3 nomenclature implemented in the M3 ontology
	 * @return RDF sensor data compliant with M3
	 */
	@GET
	@Path("/convert_weather/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertFoodMNeasuremt() {
		ConvertSensorDataToM3 ag = new ConvertSensorDataToM3();		
		String webServiceWeather = ReadFile.queryWebServiceXML(Var.URL_SENML_DOMAIN_WEATHER);
		String msg;
		try {
			msg = ag.convertXMLSenMLIntoRDF(webServiceWeather, Var.KIND_JDO_WEATHER, Var.KEY_NAME_JDO_WEATHER);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			msg= e.getMessage();
		}// base name sensor		
		return Response.status(200).entity(msg).build();
	}
	
	/**
	 * Scenario seasonal affective disorder
	 * According to the luminosity measurement we can interpret your mood
	 * @return mood deduced from the luminosity measurement
	 */
	
	@GET
	@Path("/emotion_luminosity/")
	@Produces(MediaType.APPLICATION_XML)
	public String luminosityWeatherEmotion() {
		try {
			
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER, Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_PATH);

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "WeatherLuminosity", false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_NATUROPATHY_ONTOLOGY + "Emotion", false));	
			
			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);
			//SPARQL_QUERY_WEATHER_EMOTION
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Search food according to the season (naturopathy:hasSeason)
	 * The Season is deduced according to the external temperature (jena rule reasoner)
	 * File with jena rule temperature
	 * @return the jena xml binding: result of the sparql query
	 */
	@GET
	@Path("/seasonTemperatureFoodRecipe/")
	@Produces(MediaType.APPLICATION_XML)
	public String searchSeasonForTemperature() {
		try {
			//load the M2M measurement
			//M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER, Var.KEY_NAME_JDO_WEATHER);
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_PATH);
			
			//use generic sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "WeatherTemperature", false));		
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_NATUROPATHY_ONTOLOGY + "Food", false));	
			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_GENERIC, var, Var.LINKED_OPEN_RULES_WEATHER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** scenario relationships between emotion and disease
	 * 
	 * @param property
	 * @return
	 */
	@GET
	@Path("/emotion_disease/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String emotionDisease(@PathParam("property") String property) {
		try {			
			//load the M2M measurement
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.HEALTH_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);

			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_PATH);

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + property, false));//"HeartBeat"

			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_HEALTH_EMOTION, var, Var.LINKED_OPEN_RULES_HEALTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	

/**
 * Cholesterol scenario: get recipe fat free
 * @return
 */
	@GET
	@Path("/cholesterol/")
	@Produces(MediaType.APPLICATION_XML)
	public String searchFoodHighCholesterol() {
		try{
			//load the M2M measurement
			Model model = ModelFactory.createDefaultModel();
			ReadFile.enrichJenaModelOntologyDataset(model, Var.HEALTH_M3_SENSOR_DATA_WAR);
			M2MAppGeneric m2mappli = new M2MAppGeneric(model);

			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL_WAR + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.FOOD_TAXONOMY_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.FOOD_RECIPE_ONTOLOGY_PATH );
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.FOOD_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.FOOD_GENERIC_ONTOLOGY_PATH);
			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("typeUri", Var.NS_NATUROPATHY_DATASET + "HighCholesterol", false));

			return m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_RECIPE_FAT_FREE, var, Var.LINKED_OPEN_RULES_HEALTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Search all diseases in the naturopathy dataset
	 * @return
	 */
	@GET
	@Path("/disease/")
	@Produces(MediaType.APPLICATION_XML)
	public String getDiseases() {
		try {
			M2MAppNaturopathy nat = new M2MAppNaturopathy();
			return nat.getTypeOf("Disease");	

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Search food related to the cold symptom
	 * @return
	 */
	@GET
	@Path("/isRecommendedFor/")
	@Produces(MediaType.APPLICATION_XML)
	public String isRecommendedFor() {
		try {
			System.out.println("isRecommendedFor");
			M2MAppNaturopathy nat = new M2MAppNaturopathy();
			return nat.isRecommendedFor("Cold");	

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * Convert senml measurements into semantic measurements
	 * @param data converted according to the format RDF
	 * @return
	 */
	@GET
	@Path("/convert_food/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertFoodMeasurement() {
		ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();
		//"http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3ITCxIJWm9uZUFkbWluIgRob21lDA"
		String sensorMeasurements = ReadFile.queryWebServiceXML(Var.URL_SENML_DOMAIN_KITCHEN);
		//String sensorMeasurements = WSUtils.queryWebService("http://wlboxv2.appspot.com/senml/zones/aglzfndsYm94djJyJgsSCVpvbmVBZG1pbiIXQW1lbGllRG9Ob3REZWxldGVQbGVhc2UM");
		
		System.out.println("Senml measurements: " + sensorMeasurements);
		String msg;
		try {
			msg = m3.convertXMLSenMLIntoRDF(sensorMeasurements, Var.KIND_JDO_FOOD, Var.KEY_NAME_JDO_FOOD);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			msg= e.getMessage();
		}// base name sensor
		return Response.status(200).entity(msg).build();
	}

	/**
	 * Link semantic food measurements to existing semantic dataset (recipe, food, naturopathy)
	 * @return SparqlResult: food available in the kitchen
	 */
	@GET
	@Path("/matchingFood/")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<SparqlResultMatchingFood> matchingFood() {
		//String msg =  "Error";
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		ArrayList<SparqlResultMatchingFood> resultSparql = m2mappli.matchingFood();
		return resultSparql;
	}

	/**
	 * Search a recipe
	 * @param property
	 * @return
	 */
	@GET
	@Path("/searchRecipe/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<SparqlResultRecipe> searchRecipeWithIngredient(@PathParam("property") String property) {
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		return m2mappli.searchRecipeWithIngredient(property);	
	}


	/**
	 * Search detail of a recipe
	 * @param property
	 * @return
	 */
	@GET
	@Path("/recipeDetail/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<SparqlResultRecipe> getRecipeDetails(@PathParam("property") String property) {
		System.out.println(property);
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		return m2mappli.getRecipeDetails(property);	
	}
	


	/**
	 * Get food available in the kitchen and their season
	 * @return
	 */
	@GET
	@Path("/foodKitchenSeason")
	@Produces(MediaType.APPLICATION_XML)
	public Set<SparqlResultRecipeNaturopathy> getFoodAvailableKitchenSeason() {
		try {
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			M2MAppRecipe m2mappli = new M2MAppRecipe();
			return m2mappli.searchFoodAvailableKitchenSeason(var);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
