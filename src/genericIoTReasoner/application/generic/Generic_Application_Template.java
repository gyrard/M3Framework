package genericIoTReasoner.application.generic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import knoesis.application.skeleton.ExecuteQueryEngine;
import knoesis.application.skeleton.GenericApplication;
import knoesis.application.skeleton.ReadFile;
import knoesis.application.skeleton.VariableSparql;
import knoesis.semanticdata.annotator.SemanticAnnotatorV2CompliantWithSOSA;


/**
 * A same generic application working for various scenarios:
 * Asthma - Pollen
 * Asthma - Peak flow meter
 * Asthma - Inside Temperature
 * Asthma - Outside Humidity
 * Asthma - Air Quality
 * Obesity - Hear rate
 * 
 * Compliant With W3C SOSA/SSN ontology
 * @author Amelie Gyrard
 * Created August 2018
 */
		
public class Generic_Application_Template {
	
	/** current implementation supports only SenML**/
	public String RAW_SENSOR_DATA = "";
	
	// for each scenario, the current implementation loads a different dataset
	public String GENERATED_SEMANTIC_SENSOR_DATA = "";

	public String ONTOLOGY_TO_LOAD = "";
	
	/** TO DO: support any datasets form the Linked Open data **/
	public String DATASET_TO_LOAD = "";
	
	
	public String RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY = "";
	
	
	// for each scenario, the current implementation loads a different dataset
	// design choice: one file for the rules for specific kind of data e.g., pollen rule files for all rules related to pollen
	public String RULES_TO_DEDUCE_MEANINGFUL_KNOWLEDGE_TO_LOAD = "";
	
	
	/** variables to query **/
	ArrayList<VariableSparql> VARIABLES_SPARQL_QUERY_DATA_TO_QUERY = new ArrayList<VariableSparql>(); 
	
	public String GENERIC_SPARQL_QUERY_SMARTER_DATA_TO_LOAD = "";

	public Generic_Application_Template(String raw_sensor_data, String generated_semantic_sensor_data,
			String ontology_to_load, String dataset_to_load, String rules_for_semantic_annotation_to_load,
			String rules_to_deduce_meaningful_knowledge_to_load, String sparql_query_smarter_data_to_load, 
			ArrayList<VariableSparql> variable_sparql_query) {
		super();
		RAW_SENSOR_DATA = raw_sensor_data;
		GENERATED_SEMANTIC_SENSOR_DATA = generated_semantic_sensor_data;
		ONTOLOGY_TO_LOAD = ontology_to_load;
		DATASET_TO_LOAD = dataset_to_load;
		RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY = rules_for_semantic_annotation_to_load;
		RULES_TO_DEDUCE_MEANINGFUL_KNOWLEDGE_TO_LOAD = rules_to_deduce_meaningful_knowledge_to_load;
		GENERIC_SPARQL_QUERY_SMARTER_DATA_TO_LOAD = sparql_query_smarter_data_to_load;
		VARIABLES_SPARQL_QUERY_DATA_TO_QUERY = variable_sparql_query;
	}
	
	// TO DO: function to create our own rules for semantic annotation
	 
	// TO DO: function to create our own rules for the reasoner
		
	// TO DO: function to automatically update the ontology for new scenarios
			
	// TO DO: function to automatically retrieve knowledge relevant for the scenarios LOD, LOV, etc
	
	// TO DO: function for the automatic ontology-based recommender system
	
	
	public String execute() throws IOException, JAXBException {


		/** STEP: LOAD RAW DATA (e.g, SENML/XML) in this tutorial/example
			ACTION: Check that the right raw sensor data is loaded
			E.g., Scenario for pollen raw data
		 **/
		String sensorMeasurements = ReadFile.readContentFile(this.RAW_SENSOR_DATA);
		
		System.out.println("Input: \n" + sensorMeasurements);
				
		
		/** STEP: SEMANTIC ANNOTATION
		 *  UPDATE: July 2018: Semantic annotator compliant with W3C SOSA/SSN V2 ontology
		    UPDATE: June 2018: show that we are loading the rules for semantic annotation
		 **/
		SemanticAnnotatorV2CompliantWithSOSA semanticAnnotator = new SemanticAnnotatorV2CompliantWithSOSA();
		semanticAnnotator.convertXMLSenMLIntoRDF(sensorMeasurements, 
				this.RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY);
				

		/** AUTOMATICALLY WRITE SEMANTIC SENSOR DATA IN A FILE
		 * 
		 **/
		//String fileName = this.GENERATED_SEMANTIC_SENSOR_DATA;
		//FileWriter out = new FileWriter(fileName);
		//semanticAnnotator.model.write(out,"RDF/XML-ABBREV");
		// (java.io.FileWriter is a restricted class. Please see the Google  App Engine developer's guide for more details.)
								
		/** STEP: LOAD SEMANTIC SENSOR DATA
		 * 
		 **/
		//Model model = ModelFactory.createDefaultModel();
		
		//ReadFile.enrichJenaModelOntologyDataset(model, this.GENERATED_SEMANTIC_SENSOR_DATA);
								
		// works with semantic sensor data 
		// (file already generated, useful to use in case of some issues with the semantic annotation)
		// ReadFile.enrichJenaModelOntologyDataset(model, GENERATED_SEMANTIC_SENSOR_DATA);
		
		/** GENERIC APPLICATION
		 * 
		 **/
		GenericApplication generic_appli = new GenericApplication(semanticAnnotator.model);

		/** STEP: SPECIFIC DOMAIN ONTOLOGIES AND DATASETS
		 * HERE will be all ontologies and datasets needed can be loaded
		 * to design the Personalized Healthcare Knowledge Graph (PHKG) 
		 * 
		 * kao ontology reuses AO from BioPortal, W3C SSN SOSA, etc.
		 **/
		ReadFile.enrichJenaModelOntologyDataset(generic_appli.model, this.ONTOLOGY_TO_LOAD);
		


		/** STEP: EXECUTING REASONING ENGINE with a set of rules from S-LOR 
		 * S-LOR (Sensor-based Linked Open Rules) project follows Linked Data and Linked Vocabularies Trend
		 * Main goal: Share and reuse interoperable rules to interpret sensor data
		 * http://linkedopenreasoning.appspot.com/
		 * 
		 **/
		Model deduceMeaningfulInformationFromSensorData = 
				generic_appli.executeReasoningEngine(this.RULES_TO_DEDUCE_MEANINGFUL_KNOWLEDGE_TO_LOAD);

		/** STEP: EXECUTING QUERY ENGINE
		 * 
		 **/
		ExecuteQueryEngine resultQueryEngine = new ExecuteQueryEngine(
				deduceMeaningfulInformationFromSensorData, this.GENERIC_SPARQL_QUERY_SMARTER_DATA_TO_LOAD);
		
		
		/** STEP: TO MODIFY THE GENERIC SPARQL QUERY BY ASKING SPECIFIC INFORMATION
		 * ACTION: Use the right sensor data type (e.g., PollenLevel to be compliant with kao ontology)
		 * Variables refers to a concept within the KAO ontology	
		 **/
		String result = resultQueryEngine.getSelectResultAsXML(this.VARIABLES_SPARQL_QUERY_DATA_TO_QUERY);

		
		/** DISPLAY SMARTER DATA
		 *  USER TO DO: DISPLAY THE RESULT IN A USER FRIENDLY INTERFACE
		 **/
		return result;	
		
	}
}
