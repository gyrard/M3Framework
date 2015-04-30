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

import eurecom.common.util.ReadFile;
import eurecom.data.converter.ConvertSensorDataToM3;
import eurecom.generic.m2mapplication.M2MAppRecipe;
import eurecom.sparql.result.SparqlResultMatchingFood;
import eurecom.sparql.result.SparqlResultRecipe;
import eurecom.sparql.result.SparqlResultRecipeNaturopathy;

/**
 * DO NOT USE IT ANYMORE
 * Still used by Soumya?
 * @author gyrard amelie
 * To return the web services in JSON
 * After I found the solution to return the result either as JSON or as XML
 * By adding:
 * @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 *	public static Response nameFunction( @QueryParam(value = "format") String format) {
 */
@Path("json/kitchen")
public class ApiJsonWS {

	
	/**
	 * Convert senml measurements into semantic measurements
	 * @param data converted according to the format RDF
	 * @return
	 */
	@GET
	@Path("/convert/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response convertFoodMeasurement() {
		ConvertSensorDataToM3 m3 = new ConvertSensorDataToM3();
		String sensorMeasurements = ReadFile.queryWebServiceXML("http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgdraXRjaGVuDA");
		System.out.println("Senml measurements: " + sensorMeasurements);
		String msg;
		try {
			msg = m3.convertXMLSenMLIntoRDF(sensorMeasurements, "food", "foodMeasurement");
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
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultMatchingFood> matchingFood() {
		//String msg =  "Error";
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		ArrayList<SparqlResultMatchingFood> resultSparql = m2mappli.matchingFood();
		return resultSparql;
	}


	/**
	 * Search all food measurements
	 * @return
	 */
	@GET
	@Path("/food")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultRecipeNaturopathy> getFoodMeasurements() {
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		return m2mappli.getFoodMeasurements();	
	}

	@GET
	@Path("/searchRecipe/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultRecipe> searchRecipeWithIngredient(@PathParam("property") String property) {
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		return m2mappli.searchRecipeWithIngredient(property);	
	}


	@GET
	@Path("/recipeDetail/{property}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultRecipe> getRecipeDetails(@PathParam("property") String property) {
		System.out.println(property);
		M2MAppRecipe m2mappli = new M2MAppRecipe();
		return m2mappli.getRecipeDetails(property);	
	}

}
