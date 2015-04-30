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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.VariableSparql;

/**

 * @author Gyrard Amelie
 * To query the LOV4IoT RDF dataset
 * Mainly used for statistics, to count the total number of ontologies, the number of ontology by domain, according to their status, etc.
 */

@Path("/lov4iot")
public class LOV4IoTWS {
	
	
	/**
	 * Execute a SPARQL query to count the total number of ontology-based project referenced in the LOV4IoT RDF dataset 
	 * Last updated January 29th 2015
	 * @param 
	 * @return the number of ontologies referenced in the LOV4IoT RDF dataset 
	 * E.g., http://sensormeasurement.appspot.com/lov4iot/totalOnto/
	 */
	@GET
	@Path("/totalOnto/")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTotalNumberOntology() {
		//load the LOV4IoT dataset into the model
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.LOV4IOT_DATASET_PATH);
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);

		//SPARQL query
		ExecuteSparql sparqlQuery = new ExecuteSparql(model, Var.ROOT_SPARQL_LOV4IoT + "countTotalOntology.sparql");
		
		//no variable to replace in the SPARQL query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		String resultSparqlsenml = sparqlQuery.getSelectResultAsXML(var);

		return Response.status(200).entity(resultSparqlsenml).build();
	}
	
	
	/**
	 * Execute a SPARQL query to count the different status of ontologies 
	 * Last updated February 2nd 2015
	 * @param 
	 * @return the number of ontologies referenced in the LOV4IoT RDF dataset according to their status
	 * E.g., http://sensormeasurement.appspot.com/lov4iot/ontoStatus/?status=Online
	 */
	@GET
	@Path("/ontoStatus/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response getOntologyStatus(@QueryParam("status") String status) {
		//load the LOV4IoT dataset into the model
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.LOV4IOT_DATASET_PATH);
		ReadFile.enrichJenaModelOntologyDataset(model, Var.LOV4IOT_ONTOLOGY_PATH);// load LOV4IOT ontology
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);

		//SPARQL query
		ExecuteSparql sparqlQuery = new ExecuteSparql(model, Var.ROOT_SPARQL_LOV4IoT + "ontoStatus.sparql");
		
		//no variable to replace in the SPARQL query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		
		if(status.compareTo("Confidential")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("ontologyStatus", Var.NS_LOV4IOT + "Confidential", false));
		}
		else if(status.compareTo("OngoingProcessOnline")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("ontologyStatus", Var.NS_LOV4IOT + "OngoingProcessOnline", false));
		}
		else if(status.compareTo("WaitForAnswer")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("ontologyStatus", Var.NS_LOV4IOT + "WaitForAnswer", false));
		}		
		else if(status.compareTo("Online")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("ontologyStatus", Var.NS_LOV4IOT + "Online", false));
		}
		else if(status.compareTo("OnlineLOV")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("ontologyStatus", Var.NS_LOV4IOT + "OnlineLOV", false));
		}
		else if(status.compareTo("AlreadyLOV")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("ontologyStatus", Var.NS_LOV4IOT + "AlreadyLOV", false));
		}
		String resultSparqlsenml = sparqlQuery.getSelectResultAsXML(var);

		//System.out.println(resultSparqlsenml);
		return Response.status(200).entity(resultSparqlsenml).build();
	}
	
	/**
	 * Execute a SPARQL query to count the different ontologies in all domains
	 * Last updated February 25th 2015
	 * @param 
	 * @return the number of ontologies in each domain referenced in the LOV4IoT RDF dataset according
	 * E.g., http://sensormeasurement.appspot.com/lov4iot/nbOntoDomain/?domain=BuildingAutomation
	 */
	@GET
	@Path("/nbOntoDomain/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response getNumberOntologyByDomain(@QueryParam("domain") String domain) {

		//load the LOV4IoT dataset into the model
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.LOV4IOT_DATASET_PATH);
		ReadFile.enrichJenaModelOntologyDataset(model, Var.LOV4IOT_ONTOLOGY_PATH);// load LOV4IOT ontology
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);

		//SPARQL query
		ExecuteSparql sparqlQuery = new ExecuteSparql(model, Var.ROOT_SPARQL_LOV4IoT + "numberOntoByDomain.sparql");
		
		//no variable to replace in the SPARQL query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		
		if(domain.compareTo("BuildingAutomation")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "BuildingAutomation", false));
		}
		else if(domain.compareTo("Weather")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Weather", false));
		}
		else if(domain.compareTo("Emotion")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Emotion", false));
		}
		else if(domain.compareTo("Agriculture")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Agriculture", false));
		}
		else if(domain.compareTo("Health")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Health", false));
		}
		else if(domain.compareTo("Tourism")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Tourism", false));
		}		
		else if(domain.compareTo("Transportation")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Transportation", false));
		}
		else if(domain.compareTo("City")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "City", false));
		}
		else if(domain.compareTo("Energy")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Energy", false));
		}
		else if(domain.compareTo("Environment")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Environment", false));
		}
		else if(domain.compareTo("TrackingFood")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "TrackingFood", false));
		}
		else if(domain.compareTo("Activity")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Activity", false));
		}
		else if(domain.compareTo("Fire")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Fire", false));
		}
		else if(domain.compareTo("TrackingCD")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "TrackingCD", false));
		}
		else if(domain.compareTo("TrackingDVD")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "TrackingDVD", false));
		}
		else if(domain.compareTo("SensorNetworks")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "SensorNetworks", false));
		}
		else if(domain.compareTo("Security")==0){
			var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("context", Var.NS_M3 + "Security", false));
		}
		String resultSparqlsenml = sparqlQuery.getSelectResultAsXML(var);

		//System.out.println(resultSparqlsenml);
		return Response.status(200).entity(resultSparqlsenml).build();
	}
	
	//
	
	/**
	 * The LOB4IOT bot to send email to encourage people to share their domain knowledge (ontologies, datasets, and rules)
	 * @param recipient: email of the author, creator of the domaoin knowledge
	 * @param paper: title of the research article that we are intereted in where they mentionned ontologies, rules relevant for Internet of Things
	 * @return
	 */
	@GET
	@Path("/sendEmail/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public static Response sendEmail(@QueryParam(value = "recipient") String recipient, @QueryParam(value = "paper") String paper) {
    try {
    	
    	System.out.println("Paper: "+ paper);
    	System.out.println("Recipient: "+ recipient);
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
       String message = "Dear authors, \n" +
    		  "I'm a PhD student in EURECOM, France, "+
       		"I am interested in the ontology that you describe in the paper '"+ paper + "'." +
       		" \n \nCould it be possible to share your ontology and rule files?  \n  \n"+
       		"If you are agree, I will reference your domain ontology here:  \n" +
       		 "http://www.sensormeasurement.appspot.com/?p=ontologies  \n \n" +
       		"Secondly, I will try to extract the domain knowledge to update our Sensor-based Linked Open Rules (S-LOR) \n" +
       		"http://sensormeasurement.appspot.com/?p=swot_template \n \n" +
       		"Thank you in advance for your reply and your help for domain knowledge sharing and reuse.  \n \n"+
       		 "I look forward to your ontology URL of file.  \n" +
       		 
       		"\nBest Regards, ";
       		
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("ameliegyrard@gmail.com", "gyrard@eurecom.fr"));
        msg.addRecipient(Message.RecipientType.TO,
                         new InternetAddress(recipient, ""));
        msg.addRecipient(Message.RecipientType.CC,
                new InternetAddress("gyrard@eurecom", "Amelie Gyrard"));
        msg.setSubject("ontology and rule");
        msg.setText(message);
        Transport.send(msg);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
    catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    
	return Response.status(200).entity("Email sent!").build();
    
	}
	
	

}
