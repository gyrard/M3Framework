/**
 * August 2018
 * Refactoring TO REMOVE
 * Used in m3 web site not here
 */
	

/*package eurecom.web.service;

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

*//**

 * @author Gyrard Amelie
 * Tag4IoT To analyze/get popular terms in ontologies
 * 
 *//*

@Path("/tag4iot")
public class Tag4IoTWS {
	
	
	*//**
	 * Get list of ontologies that we already try to get popular terms without generating issues
	 * Last updated June 17 2015
	 * @param 
	 * @return list of ontologies, name - url
	 * E.g., /tag4iot/getListOnto/
	 *//*
	@GET
	@Path("/getListOnto/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getTotalNumberOntology() {
		String table_onto_ok = "Onto 1; Onto 2";

		return Response.status(200).entity(table_onto_ok).build();
	}
	
}
*/