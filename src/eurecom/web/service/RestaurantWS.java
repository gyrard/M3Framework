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
import eurecom.common.util.WSUtils;
import eurecom.data.converter.ConvertSensorDataToM3;
import eurecom.generic.m2mapplication.M2MAppResto;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.ExecuteSparqlRestaurant;
import eurecom.sparql.result.SparqlResultMatchingFood;
import eurecom.sparql.result.SparqlResultRecipeNaturopathy;
import eurecom.sparql.result.SparqlResultRestaurant;
import eurecom.sparql.result.VariableSparql;


@Path("/restaurant")
public class RestaurantWS {

	/** Get the SenML measurement from the WLBOX
	 * longitude and latitude simulated Latitude (37.323), Longitude (-122.03218)
	 * @return RDF sensor data compliant with M3
	 */
	@GET
	@Path("/convert/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertLocationMeasurement() {
		ConvertSensorDataToM3 ag = new ConvertSensorDataToM3();
		String sensorMeasurements = ReadFile.queryWebServiceXML("http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IUCxIJWm9uZUFkbWluIgVwbGFjZQw");
		String msg;
		try {
			msg = ag.convertXMLSenMLIntoRDF(sensorMeasurements, "location", "locationMeasurement");
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			msg= e.getMessage();
		}// base name sensor
		return Response.status(200).entity(msg).build();
	}

	/**
	 * Search all food measurements
	 * @param property
	 * @return
	 */
	@GET
	@Path("/measurement")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<SparqlResultRecipeNaturopathy> getLocationMeasurements() {
		M2MAppResto m2mappli = new M2MAppResto();
		return m2mappli.getMeasurements();	
	}
	
	/**
	 * Link to geonames, a daset to retrieve information from a longitude and a latitude
	 * @param property
	 * @return
	 */
	@GET
	@Path("/geonames")
	@Produces(MediaType.TEXT_PLAIN)
	public String linkToGeonames() {
		M2MAppResto m2mappli = new M2MAppResto();
		return m2mappli.linkToGeonames().toString();	
	}
	
	/**
	 * Link to resto ontologies
	 * @param property
	 * @return
	 */
	@GET
	@Path("/resto")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<SparqlResultRestaurant> linkToResto() {
		M2MAppResto m2mappli = new M2MAppResto();
		return m2mappli.linkToResto();	
	}

	/**
	 * Search all type of restaurant (e.g., pizzeria) in the restaurant ontology and dataset
	 * @param property
	 * @return an XML result all type of restaurant 
	 */
	@GET
	@Path("/foodType")
	@Produces(MediaType.APPLICATION_XML)
	public static ArrayList<SparqlResultMatchingFood> getFoodType() {
		Model model = loadDatabaseRestaurant();
		ExecuteSparql req = new ExecuteSparql(model, Var.ROOT_SPARQL_RESTAURANT + "GetFoodType.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		ArrayList<SparqlResultMatchingFood> resultSparql = req.getSparqlResult(var);
		return resultSparql;
	}
	
	/**
	 * Search all cities available in the restaurant ontology and dataset
	 * @param property
	 * @return an XML result all cities of restaurant 
	 */	
	@GET
	@Path("/city")
	@Produces(MediaType.APPLICATION_XML)
	public static ArrayList<SparqlResultMatchingFood> getCity() {
		Model model = loadDatabaseRestaurant();
		ExecuteSparql req = new ExecuteSparql(model, Var.ROOT_SPARQL_RESTAURANT + "GetCity.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		ArrayList<SparqlResultMatchingFood> resultSparql = req.getSparqlResult(var);
		return resultSparql;
	}
	
	/**
	 * Search all ratings of restaurant in the ontology and dataset
	 * @param property
	 * @return an XML result all ratings of restaurant 
	 */	
	@GET
	@Path("/rating")
	@Produces(MediaType.APPLICATION_XML)
	public static ArrayList<SparqlResultMatchingFood> getRating() {
		Model model = loadDatabaseRestaurant();
		ExecuteSparql req = new ExecuteSparql(model, Var.ROOT_SPARQL_RESTAURANT + "GetRating.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		ArrayList<SparqlResultMatchingFood> resultSparql = req.getSparqlResult(var);
		
		//faire ce code en sparql
		/*		String rating = "";
		int index = 0;
		for(int i = 0 ; i< resultSparql.getResults().size(); i++){
			rating = resultSparql.getResults().get(i).getLabel();
			index = rating.indexOf("^^");
			rating = rating.substring(0, index);
			resultSparql.getResults().get(i).setLabel(rating);
		}*/
			
		return resultSparql;
	}
	
/*	@GET
	@Path("/search/{foodType}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static ArrayList<SparqlResultRestaurant> searchRestoInCity(@PathParam("foodType") String foodType) {
		Model model = loadDatabaseRestaurant();
		String city = "san francisco";
		//String foodType = "seafood";
		System.out.println("hihi" + foodType);
		ExecuteSparqlRestaurant req = new ExecuteSparqlRestaurant(model, Var.ROOT_RESTAURANT + "searchRestaurantInCity.sparql");
		ArrayList<VariableSparql> var = new ArrayList<>();
		VariableSparql v1 = new VariableSparql("labelCityUser", city, true);
		VariableSparql v2 = new VariableSparql("foodTypeUser", foodType, true);
		var.add(v1);
		var.add(v2);
		ArrayList<SparqlResultRestaurant> resultSparql = req.executeRequest(var);
		return resultSparql;
	}*/
	
	
	@GET
	@Path("/search/{foodType}/{city}/{rating}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static ArrayList<SparqlResultRestaurant> searchRestoInCity(@PathParam("foodType") String foodType, @PathParam("city") String city, @PathParam("rating") String rating) {
		Model model = loadDatabaseRestaurant();
		//String city = "san francisco";
		//String foodType = "seafood";
		System.out.println("hihi" + foodType);
		ExecuteSparqlRestaurant req = new ExecuteSparqlRestaurant(model, Var.ROOT_SPARQL_RESTAURANT + "searchRestaurantInCity.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		VariableSparql v1 = new VariableSparql("labelCityUser", city, true);
		VariableSparql v2 = new VariableSparql("foodTypeUser", foodType, true);
		VariableSparql v3 = new VariableSparql("ratingStringUser", rating, true);
		var.add(v1);
		var.add(v2);
		var.add(v3);
		ArrayList<SparqlResultRestaurant> resultSparql = req.executeRequest(var);
		return resultSparql;
	}
	
	/**
	 * Search a resto for a specific city
	 * @param city
	 * @return
	 */
	@GET
	@Path("/searchResto/{city}/")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static ArrayList<SparqlResultRestaurant> searchRestoInSpecificCity(@PathParam("city") String city) {
		Model model = loadDatabaseRestaurant();
		//String city = "san francisco";
		//String foodType = "seafood";
		System.out.println("city" + city);
		ExecuteSparqlRestaurant req = new ExecuteSparqlRestaurant(model, Var.ROOT_SPARQL_RESTAURANT + "searchRestaurantSpecificCity.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		VariableSparql v1 = new VariableSparql("labelCityUser", city, true);
		var.add(v1);
		ArrayList<SparqlResultRestaurant> resultSparql = req.executeRequest(var);
		return resultSparql;
	}
	
	public static Model loadDatabaseRestaurant(){
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_RESTAURANT + "restaurantMooney.owl");
		return model;
	}
	
}
