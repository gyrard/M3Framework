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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eurecom.generic.m2mapplication.WlboxApi;
import eurecom.sparql.result.SparqlResultMatchingFood;

/**
 * DO NOT USE IT ANYMORE
 * Still used by the WLBOX?
 * if yes do not delete
 * @author gyrard amelie
 * To return the web services in JSON
 * After I found the solution to return the result either as JSON or as XML
 * */
@Path("/json/m3")
public class M3JsonWS {
	
	/**
	 * Get all sensors (e.g., thermometer) referenced in the M3 nomenclature implemented in the M3 ontology
	 * @return
	 */
	@GET
	@Path("/sensor")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultMatchingFood> getAllSensors() {
		return WlboxApi.getSubclassOf("Sensor");	
	}
	
	/**
	 * Get all domains (e.g., health, transport) referenced in the M3 nomenclature implemented in the M3 ontology
	 * @return
	 */
	@GET
	@Path("/featureOfInterest")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultMatchingFood> getAllFeatureOfInterests() {
		return WlboxApi.getSubclassOf("FeatureOfInterest");	
	}
	
	/**
	 * I do not remember the purpose of this web service
	 * @return
	 */
	@GET
	@Path("/featureOfInterest/{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultMatchingFood> getAllFeatureOfInterests(@PathParam("name") String name) {
		return WlboxApi.getSubclassOf(name);	
	}
	
	
	
	/**
	 * Get all measurement types (e.g., bodytemperature, rfid on food) referenced in the M3 nomenclature implemented in the M3 ontology
	 * @return
	 */
	@GET
	@Path("/measurement")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SparqlResultMatchingFood> getAllMeasurementType() {
		//measurement and rfid tags
		ArrayList<SparqlResultMatchingFood> res = WlboxApi.getSubclassOf("RFIDMeasurementType");	
		res.addAll(WlboxApi.getSubclassOf("MeasurementType"));
		return res;
	}

}
