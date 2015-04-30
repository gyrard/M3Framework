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
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.ExecuteSparqlGeneric;
import eurecom.sparql.result.VariableSparql;
/**
 * All web service used in the STAC (Security Toolbox: Attacks and Countermeasures) tool, a security tool
 * @author Amelie Gyrard
 *
 */
@Path("/stac")
public class STACWS {
	//public static Model model = M2MAppGeneric.loadSecurityOntologyDataset();
	public static String FILE = "";
	Logger logger = Logger.getLogger("Web service");
	
	public static Model loadSecurityOntologyDataset(){
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.STAC_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(model, Var.STAC_DATASET_PATH);
		
		ReadFile.enrichJenaModelOntologyDataset(model, Var.SECURITY_ONTO_KIM_ALGO);
		return model;		
	}
	
	@GET
	@Path("describe/{label}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	/@Produces(MediaType.APPLICATION_XML)
	public static void  describe(@PathParam("label") String label) {
		System.out.println("stac template: " + label);
		Model model = loadSecurityOntologyDataset();
		///StacApplication stacAppli = new StacApplication(true); //jena reasoner
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_DESCRIBE);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("uri", Var.NS_STAC_DATASET + label, false));
		Model resultSparql = req.getDescribeResult(var);
		resultSparql.write(System.out);
		//return resultSparql;
	}
	
	/**
	 * Get all security mechanisms refenrenced in the stac dataset (e.g., Bluetooth)
	 * @param nametechno
	 * @return
	 */
	@GET
	@Path("techno/{nametechno}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public static String getTechno(@PathParam("nametechno") String nametechno) {
		System.out.println("stac template: " + nametechno);
		Model model = loadSecurityOntologyDataset();
		///StacApplication stacAppli = new StacApplication(true); //jena reasoner
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_TEMPLATE_SECURITY_MECHANISM_TECHNO);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("replace", Var.NS_STAC_ONTO + nametechno, false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}
	
	/**
	 * Get all attacks related to a specific technology
	 * @param nametechno
	 * @return
	 */
	@GET
	@Path("attack/{nametechno}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public static String getAttackRelatedToTechnolgy(@PathParam("nametechno") String nametechno) {
		System.out.println("stac template attack: " + nametechno);
		Model model = loadSecurityOntologyDataset();
		//StacApplication stacAppli = new StacApplication(true); //jena reasoner
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_TEMPLATE_SECURITY_ATTACK_TECHNO);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("replace", Var.NS_STAC_ONTO + nametechno, false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}
	
	/**
	 * Search all subclasses of a STAC concept
	 * @param stacConcept ex.: Attack, SecurityMechnanism, KeyManagement, SecurityTool, SecurityProtocol, SensorProtocol
	 * @return SparqlResult:  list of the subclasses of the concept
	 */
	@GET
	@Path("/type/{stacConcept}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String getTypeOf(@PathParam("stacConcept") String stacConcept) {
		logger.info("WebService: stac/type/"+ Var.NS_STAC_ONTO + stacConcept);
		Model model = loadSecurityOntologyDataset();
		ExecuteSparqlGeneric reqMethod = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_TYPEOF);//Var.ROOT_SPARQL_SECURITY + "GetType.sparql"
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_ONTO + stacConcept, false));
		String resReqMethod = reqMethod.getSelectResultAsXML(var);//getSparqlResultAsXML
		return resReqMethod;
	}
	
	/**
	 * 	@GET
	@Path("/type/{stacConcept}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<SparqlResult> getTypeOf(@PathParam("stacConcept") String stacConcept) {
		logger.info("WebService: stac/type/"+ Var.NS_STAC_ONTO + stacConcept);
		ExecuteSparql reqMethod = new ExecuteSparql(model, Var.ROOT_SPARQL_SECURITY + "GetType.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("replace", Var.NS_STAC_ONTO + stacConcept, false));
		ArrayList<SparqlResult> resReqMethod = reqMethod.getSparqlResult(var);//getSparqlResultAsXML
		return resReqMethod;
	}
	 */
	
	/**
	 * Get associated security mechanisms to a specific attack.
	 * E.g.: the name of the Attack is Jamming, the security mechanism return is SpreadSpectrumCommunication  
	 * @param attack
	 * @return SparqlResult: list of SecurityMechanism (uri/label/comment)
	 */
	@GET
	@Path("hasSecurityMechanism/{attack}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public static String getSecurityMechanismOfAttack(@PathParam("attack") String attack) {
		System.out.println(Var.NS_STAC_DATASET + attack);
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql reqMethod = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_SUBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("subject", Var.NS_STAC_DATASET + attack, false));
		var.add(new VariableSparql("predicate", Var.NS_STAC_ONTO + "hasSecurityMechanism", false));
		String resReqMethod = reqMethod.getSelectResultAsXML(var);
		return resReqMethod;
	}
	
	/**
	 * A securityMechanism can be composed of other securityMechanism
	 * E.g.: The VPN securityMechanism is composed of IKE and IPsec (both are securityMechanism)
	 * @param SecurityMechanism
	 * @return SparqlResult: list of SecurityMechanism (uri/label/comment)
	 */
	@GET
	@Path("/hasPart/{securityMechanism}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String getPartOfSecurityMechanism(@PathParam("securityMechanism") String securityMechanism) {
		logger.info("WebService: stac/hasPart/"+ securityMechanism);
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql reqMethod = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_SUBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("subject", Var.NS_STAC_DATASET + securityMechanism, false));
		var.add(new VariableSparql("predicate", Var.NS_DCTERMS + "hasPart", false));
		String resReqMethod = reqMethod.getSelectResultAsXML(var);
		return resReqMethod;
	}
	
	

	/**
	 * Get the features (strenghts and weaknesses) of the SecurityMechanism 
	 * @param securityMechanismName (e.g.: PGP)
	 * @return SparqlResult: list of features (strengh and weaknesses) of the SecurityMechanism
	 */
	@GET
	@Path("/hasFeature/{securityMechanism}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public static String getFeatureOfSecurityMechanism(@PathParam("securityMechanism") String securityMechanism) {
		System.out.println("has feature" + Var.NS_STAC_DATASET + securityMechanism);
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql reqMethod = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_SUBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("subject", Var.NS_STAC_DATASET + securityMechanism, false));
		var.add(new VariableSparql("predicate", Var.NS_STAC_ONTO + "hasFeature", false));
		String resReqMethod = reqMethod.getSelectResultAsXML(var);
		return resReqMethod;
	}
	
	/**
	 * Get SecurityProperty satisfied by a SecurityMechanism
	 * @param SecurityMechanism e.g.: VPN is a SecurityMechanism, and satisfies the Confidentiality (a SecurityProperty)
	 * @return SparqlResult: list of SecurityProperty satisfied
	 */
	@GET
	@Path("/satisfy/{securityMechanism}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String getPropertySatisifedBySecurityMechanism(@PathParam("securityMechanism") String securityMechanism) {
		System.out.println("satisfy" + Var.NS_STAC_DATASET + securityMechanism);
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_SUBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("subject", Var.NS_STAC_DATASET + securityMechanism, false));
		var.add(new VariableSparql("predicate", Var.NS_STAC_ONTO + "satisfies", false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}
	
	/**
	 * Search all Attack occuring in a specific layer of the OSI model (OSIModelLayer)
	 * @param OSILayer ex.; ApplicationLayer, PhysicalLayer, LinkLayer, NetworkLayer, TransportLayer
	 * @return SparqlResult:  list of Attack for this specific OSIModelLayer
	 */
	@GET
	@Path("/attackLayer/{OSILayer}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String searchAttacksSpecificLayer(@PathParam("OSILayer") String OSILayer) {
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_OBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_DATASET + OSILayer, false));
		var.add(new VariableSparql("predicate", Var.NS_STAC_ONTO + "occursInLayer", false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}

	/**
	 * Search all SecurityMechanism occuring in a specific layer of the OSI model (OSIModelLayer)
	 * @param OSILayer e.g.; ApplicationLayer, PhysicalLayer, LinkLayer, NetworkLayer, TransportLayer
	 * @return SparqlResult:  list of SecurityMechanism for this specific OSIModelLayer
	 */
	@GET
	@Path("/countLayer/{OSILayer}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String searchCountemeasuresSpecificLayer(@PathParam("OSILayer") String OSILayer) {
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_OBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_DATASET + OSILayer, false));
		var.add(new VariableSparql("predicate", Var.NS_STAC_ONTO + "protectsInLayer", false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}

	/**
	 * Search all subclasses of a STAC concept
	 * @param stacConcept ex.: Attack, SecurityMechnanism, KeyManagement, SecurityTool, SecurityProtocol, SensorProtocol
	 * @return SparqlResult:  list of the subclasses of the concept
	 */
	@GET
	@Path("/subclassOf/{stacConcept}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String getSubClassOf(@PathParam("stacConcept") String stacConcept) {
		//StacApplication stacAppli = new StacApplication(true); //jena reasoner
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_SUBCLASSOF);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_ONTO + stacConcept, false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}
	
	
	/**
	 * Get all security property mechanisms
	 * @param nametechno
	 * @return
	 */
	@GET
	@Path("/securityPropertyMethod")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String getSecurityPropertyMethod() {
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_SUBCLASSOF);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_ONTO + "SecurityPropertyMethod", false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}


	/**
	 * Get the name of SecurityMechanism to ensure the SecurityProperty
	 * @param SecurityProperty e.g: Authentication, Integrity, Confidentiality, AccessControl
	 * @return SparqlResult e.g: to ensure the Confidentiality, the SecurityMechanism is EncryptionAlgorithm
	 */
	@GET
	@Path("/conceptSecurityProperty/{securityProperty}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public static String securityPropertyMethod(@PathParam("securityProperty") String securityProperty) {
		Model model = loadSecurityOntologyDataset();
		System.out.println(Var.NS_STAC_ONTO + securityProperty );

		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_SUBCLASSOF);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_ONTO + securityProperty, false));
		String resultSparql = req.getSelectResultAsXML(var);
	/*	for (SparqlResult labelComment : resultSparql) {
			System.out.println(labelComment.getUri() + " " + labelComment.getLabel());
		}*/

		return resultSparql;
	}
	
	/**
	 * Get the name of SecurityMechanism to ensure the SecurityProperty
	 * @param SecurityProperty e.g: Authentication, Integrity, Confidentiality, AccessControl
	 * @return SparqlResult e.g: to ensure the Confidentiality, the SecurityMechanism is EncryptionAlgorithm
	 */


	/**
	 * Get all SecurityMechanism satisfying a specific SecurityProperty (Authentication, Confidentiality, Integrity, AccessControl, etc.)
	 * @param securityProperty (Authentication, Confidentiality, Integrity, AccessControl, etc.)
	 * @return SparqlResult list of all SecurityMechanism
	 */
	@GET
	@Path("/withProperty/{securityProperty}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String searchCountermeasureSatisfyingProperty(@PathParam("securityProperty") String securityProperty) {
		Model model = loadSecurityOntologyDataset();
		ExecuteSparql req = new ExecuteSparql(model, Var.SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_OBJECT);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_STAC_DATASET + securityProperty, false));
		var.add(new VariableSparql("predicate", Var.NS_STAC_ONTO + "satisfies", false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}
}


