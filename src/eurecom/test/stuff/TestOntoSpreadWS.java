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
package eurecom.test.stuff;
/*package eurecom.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightRDFImpl;
import org.ontospread.dao.JenaOntologyDAOImpl;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.JenaRDFModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.ExternalizeFilesResourceLoader;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.player.SpreadPlayer;
import org.ontospread.player.SpreadSimplePlayer;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.post.OntoSpreadPostAdjustmentImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfig;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfigImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.process.run.OntoSpreadDegradationFunction;
import org.ontospread.process.run.OntoSpreadDegradationFunctionIterationsImpl;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.process.run.OntoSpreadRunImpl;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinActivationValue;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.strategy.OntoSpreadSelectConceptStrategy;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.OntoSpreadStrategy;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.utils.ToStringHelper;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.data.convert.Employe.Employee;
import eurecom.data.convert.M3Algo;
import eurecom.delete.TestGhislainApp;

import eurecom.m2m.application.M2MAppGeneric;
import eurecom.test.ontospread.TestRunner;

@Path("/test")
public class TestWS {
	static Logger logger = Logger.getLogger(TestRunner.class);
	@GET
	@Path("/hihi/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testReveRule() {
		try {
			logger.debug("Init ontospread");
			System.out.println("Init ontospread");
			String []conceptUris = new String[]{
					"http://www.co-ode.org/ontologies/galen#AdvancedBreastCancer",
			"http://www.co-ode.org/ontologies/galen#NAMEDSymptom"};

			OntoSpreadState ontoSpreadState = new OntoSpreadState();
			ontoSpreadState.setInitialConcepts(createScoredConcepts(conceptUris,1.0));
			SpreadPlayer player = new SpreadSimplePlayer(
					createDefaultGalenOntoSpreadProcess(
							20, 
							50, 
							1.0,
							10.0,
							createOntologyGalenDAO(),
							createDegradationFunction()),
							ontoSpreadState);
			while(player.hasNext()){
				ontoSpreadState = player.next();
			}
			logger.info("Activated nodes: "+ontoSpreadState.getConcepts().size());
			System.out.println("Activated nodes: "+ontoSpreadState.getConcepts().size());
			logger.info("Spreaded nodes: "+ontoSpreadState.getSpreadedConcepts().size());
			logger.info("Time: "+(ontoSpreadState.getSpreadTime())+" mseg.");
			logger.info(ToStringHelper.arrayToString(
					ontoSpreadState.getFinalSpreadedConcepts(),"\n"));
			String msg = "test ontospread";
			return Response.status(200).entity(msg).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static ScoredConceptTO[] createScoredConcepts(String [] uris, double initialScore){
		System.out.println("createScoredConcepts");
		List <ScoredConceptTO> scoredConcepts = new LinkedList<ScoredConceptTO>();
		for(int i = 0; i<uris.length;i++){
			scoredConcepts.add(new ScoredConceptTO(uris[i],initialScore));
		}
		return scoredConcepts.toArray(new ScoredConceptTO[uris.length]);
	}
	

	public static OntoSpreadProcess createDefaultGalenOntoSpreadProcess(
			int min, int max,  double minActivation, double initialPredAdjustment, OntologyDAO dao, 
			OntoSpreadDegradationFunction degradationFunction){
		System.out.println("createDefaultGalenOntoSpreadProcess");
		return new OntoSpreadProcess(
				dao,
				createDefaultPreAdjustment(initialPredAdjustment), 
				createDefaultRunWithDegradation(min, max, minActivation,degradationFunction), 
				createDefaultPost()); 
	}
	

	public static OntoSpreadPreAdjustment createDefaultPreAdjustment(double initialPredAdjustment){
		System.out.println("createDefaultPreAdjustment");
		OntoSpreadPreAdjustment pre = new OntoSpreadPreAdjustmentImpl();
		OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig = new OntoSpreadPreAdjustmentConfigImpl(initialPredAdjustment);
		pre.setOntoPreAdjustmentConfig(ontoSpreadPreConfig );
		return pre;
	}
	
	public static OntoSpreadRun createDefaultRunWithDegradation(int min, int max,  
			double minActivation, 
			OntoSpreadDegradationFunction degradationFunction) {	
		System.out.println("createDefaultRunWithDegradation");
		return new OntoSpreadRunImpl(
			createOntologyGalenDAO(), 
			createStopStrategy(min,max, minActivation), 
			createSelectStrategy(minActivation),
			createRelationWeight(),
			degradationFunction);		
	}
	
	public static OntoSpreadPostAdjustment createDefaultPost() {
		return new OntoSpreadPostAdjustmentImpl(Boolean.TRUE);
	}

	
	public static OntologyDAO createOntologyGalenDAO() {
		String[] filenames = new String[]{Var.ROOT_OWL + "full-galen.owl"};		
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	
	public static OntoSpreadModelWrapper createOntoModelWrapper(String []filenames){
		//ResourceLoader resource = new FilesResourceLoader(filenames);
		ExternalizeFilesResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return model;
	}
	
	public static OntoSpreadStrategyVisitorPair createStopStrategy(int min, int max, double minActivation){
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(min));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(max));	
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		OntoSpreadStrategy stopStrategy = new OntoSpreadSimpleStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(stopStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}
	
	public static OntoSpreadStrategyVisitorPair createSelectStrategy(double minActivation){
		OntoSpreadCompositeRestriction restrictionsSelect = new OntoSpreadCompositeRestriction();
		restrictionsSelect.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		OntoSpreadStrategy selectStrategy = new OntoSpreadSelectConceptStrategy(restrictionsSelect);		
		return new OntoSpreadStrategyVisitorPair(selectStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}
	
	private static OntoSpreadRelationWeight createRelationWeight() {		
		return new OntoSpreadRelationWeightRDFImpl(createModelWrapper());
	}
	
	public static OntoSpreadModelWrapper createModelWrapper() {
		String[] filenames = new String[]{Var.ROOT_DATASET + "relation-weights-galen.rdf"};
		//ResourceLoader resource = new FilesResourceLoader(filenames);
		ExternalizeFilesResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		return new JenaRDFModelWrapper(resource);	
		
	}
	
	public static OntoSpreadDegradationFunction createDegradationFunction() {		
		//	return new OntoSpreadDegradationFunctionImpl(); //h1
		return new OntoSpreadDegradationFunctionIterationsImpl();
	}		
	
	void sendRequest()throws Exception {  
        System.out.println("Start sending " + " request");  
        URL url = new URL("http://localhost:8888/health/xmltest/");  
        HttpURLConnection rc = (HttpURLConnection)url.openConnection();  
        //System.out.println("Connection opened " + rc );  
        rc.setRequestMethod("POST");  
        rc.setDoOutput( true );  
        rc.setDoInput( true );   
        rc.setRequestProperty( "Content-Type", "text/xml; charset=utf-8" );  
        String reqStr = rc.getRequest();  // the entire payload in a single String  
        int len = reqStr.length();  
        rc.setRequestProperty( "Content-Length", Integer.toString( len ) );  
        rc.connect();      
        OutputStreamWriter out = new OutputStreamWriter( rc.getOutputStream() );   
        out.write( reqStr, 0, len );  
        out.flush();  
        System.out.println("Request sent, reading response ");  
        InputStreamReader read = new InputStreamReader( rc.getInputStream() );  
        StringBuilder sb = new StringBuilder();     
        int ch = read.read();  
        while( ch != -1 ){  
          sb.append((char)ch);  
          ch = read.read();  
        }  
        String response = sb.toString(); // entire response ends up in String  
        read.close();  
        rc.disconnect();  
      }  


	@POST  
	@Path("/xmltest")  
	@Consumes("application/xml")  
	@Produces("text/plain")  
	public String postOnlyXML(String incomingXML) {  
		System.out.println("incomingXML :" + incomingXML);  
		// here is the XML file....enjoy.  
		return "Return your string mesage";  
	}  

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response getEmployee(Employee employee) {
		employee.setEmployeeName(employee.getEmployeeName() + " Welcome");
		return Response.status(Status.OK).entity(employee).build();
	}


	//test read the file senml xml and convert it
	@GET
	@Path("/convertv2/{senml_input}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({"application/xml", "application/json"})
	public Response convertHealthMeasurementV2(@PathParam("senml_input") String senml_input) {
		M3Algo m3 = new M3Algo();
		String file_senml_xml= WSUtils.readFile("./WEB-INF/XML/senml.xml");
		System.out.println("convert v2: " + file_senml_xml);
		String msg = m3.convert(file_senml_xml, Var.KIND_JDO_HEALTH_V2, Var.KEY_NAME_JDO_HEALTH_V2);// base name sensor
		return Response.status(200).entity(msg).build();
	}
	 

	@GET
	@Path("/convertv2/")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({"application/xml", "application/json"})
	public Response convertHealthMeasurementV2() {
		M3Algo m3 = new M3Algo();
		String file_senml_xml= WSUtils.readFile("./WEB-INF/XML/senml.xml");
		String res, msg= "";
		try {
			res = postRequest(file_senml_xml, "http://localhost:8888/test/");
			System.out.println("convert v2: " + res);
			msg = m3.convert(res, Var.KIND_JDO_HEALTH_V2, Var.KEY_NAME_JDO_HEALTH_V2);// base name sensor
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return Response.status(200).entity(msg).build();
	}


	*//**
	 * Convert a xml string into a json string
	 * @param xml the string to convert into json
	 * @return the json string
	 * @throws JSONException
	 *//*
	@GET
	@Path("/xml_to_json/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response convertXmlToJson()  {
		String xml_senml = WSUtils.readFile("./WEB-INF/SENML/senml.xml");
		JSONObject json;
		String res ="";
		try {
			res = WSUtils.convertXmlToJson(xml_senml);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(res).build();
	}

	@GET
	@Path("/json_to_xml/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response convertJsonToXML()  {
		String json_senml = WSUtils.readFile("./WEB-INF/SENML/senml.json");
		JSONObject json;
		String res ="";
		try {
			res = WSUtils.convertJsonToXml(json_senml);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(res).build();
	}

	// POST the XML string as text/xml  via HTTPS
	public static String postRequest(String strRequest, String strURL) throws Exception {
		String responseXML = null;

		try {
			URL url = new URL(strURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;

			byte[] requestXML = strRequest.getBytes();

			// Set the appropriate HTTP parameters.
			httpConn.setRequestProperty("Content-Length", String.valueOf(requestXML.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			// Send the String that was read into postByte.
			OutputStream out = httpConn.getOutputStream();
			out.write(requestXML);
			out.close();

			// Read the response and write it to standard out.
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String temp;
			String tempResponse = "";

			//Create a string using response from web services
			while ((temp = br.readLine()) != null)
				tempResponse = tempResponse + temp;
			responseXML = tempResponse;
			br.close();
			isr.close();
		} catch (java.net.MalformedURLException e) {
			System.out.println("Error in postRequest(): Secure Service Required");
		} catch (Exception e) {
			System.out.println("Error in postRequest(): " + e.getMessage());
		}
		return responseXML;
	}

		@POST
	@Path("/convertv2/{senml_input}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({"application/xml", "application/json"})
	//MediaType.TEXT_XML does not work no error
	//MediaType.APPLICATION_XHTML_XML does not work no error
	public Response convertHealthMeasurementV2(@PathParam("senml_input") String senml_input) {
		M3Algo m3 = new M3Algo();
		String file_senml_xml= WSUtils.readFile("./WEB-INF/XML/senml.xml");
		//String file_senml_xml= WSUtils.readFile(property);
		//System.out.println("convert v2: " + file_senml_xml);// works if you read the xml file
		System.out.println("convert v2: " + senml_input);
		//String sensorMeasurements = WSUtils.queryWebServiceXML(file_senml_xml);
		//String msg= "test";
		String msg = m3.convert(file_senml_xml, Var.KIND_JDO_HEALTH_V2, Var.KEY_NAME_JDO_HEALTH_V2);// base name sensor
		return Response.status(200).entity(msg).build();
	}

	*//** bdd jdo health empty problem
	 * @return
	 *//*
	@GET
	@Path("/sick/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deduceFluFromBodyTemperature() {
		String res = "error";
		M2MAppGeneric m2m = new M2MAppGeneric("KIND_JDO_HEALTH", "KEY_NAME_JDO_HEALTH");
		WSUtils.readFile(m2m.model, Var.ROOT_DATASET_EURECOM + "naturopathy-dataset");
		String queryString = WSUtils.readFile(Var.SPARQL_CONSTRUCT_TEMP_SICK) ;
		QueryExecution qexec = QueryExecutionFactory.create(queryString, m2m.model) ;
		try {
			m2m.model = qexec.execConstruct() ;
			m2m.model.write(System.out);
		} finally { qexec.close() ; }


		return Response.status(200).entity(res).build();
	}


	*//**
	 * DO NOT delete
	 * replace SWRL rule by SPARQL construct
	 * to deduce season from the weather temperature
	 * @return
	 *//*
	@GET
	@Path("/winter/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deduceWinterFromWeatherTemperature() {
		String res = "error";
		M2MAppGeneric m2m = new M2MAppGeneric(Var.KIND_JDO_TEMPERATURE, Var.KEY_NAME_JDO_TEMPERATURE);
		WSUtils.readFile(m2m.model, Var.ROOT_DATASET_EURECOM + "naturopathy-dataset");
		String queryString = WSUtils.readFile(Var.SPARQL_CONSTRUCT_TEMP_DEDUCE_WINTER) ;
		QueryExecution qexec = QueryExecutionFactory.create(queryString, m2m.model) ;
		try {
			m2m.model = qexec.execConstruct() ;
			m2m.model.write(System.out);
		} finally { qexec.close() ; }


		return Response.status(200).entity(res).build();
	}


	*//** 
	 * DO NOT DELETE
	 * Query a sparql result and return the binding as a string in xml
	 * @return
	 *//*
	@GET
	@Path("/result/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testResultFormatterToReturnTheBinding() {
		String res = "error";
		Model model = ModelFactory.createDefaultModel();
		WSUtils.readFile(model, Var.ROOT_DATASET_EURECOM + "naturopathy-dataset");
		String queryString = WSUtils.readFile(Var.ROOT_SPARQL_EURECOM_GENERIC + "GetType.sparql") ;
		QueryExecution qexec = QueryExecutionFactory.create(queryString, model) ;
		try {
			ResultSet results = qexec.execSelect() ;
			res = ResultSetFormatter.asXMLString(results);//ResultSetFormatter.asXMLString(results);
			System.out.println(res);
		} finally { qexec.close() ; }


		return Response.status(200).entity(res).build();
	}

}
*/