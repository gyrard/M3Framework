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
package eurecom.data.converter;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.vocabulary.RDF;

import eurecom.common.util.Var;
import eurecom.store.jdo.GenericJDO;
/**
 * Semantically annotate sensor data
 * Input: SenML sensor data (XML or JSON)
 * Output: Sensor data in Resource Description Framework (RDF) compatible with the M3 ontology
 * SenML: http://www.ietf.org/archive/id/draft-jennings-senml-10.txt
 * SenML: Media Types for Sensor Markup Language (SENML) [Jennings 2012]
 * @author Gyrard Amelie 
 * Last Updated: September 2014
 */
public class ConvertSensorDataToM3 {

	/** To give a unique name to the URI **/
	public static int URI_NUM_UNIQUE = 0;
	
	/** Jena model to store RDF data in a graph and manipulate it or query it **/
	public Model model;

	public ConvertSensorDataToM3() {
		model = ModelFactory.createDefaultModel();
		model.setNsPrefix("m3",  Var.NS_M3);
	}

	/**
	 * Convert SenML/XML data into RDF according to the M3 ontology
	 * and store it in google app engine triplestore
	 * @param sensorMeasurements: SenML/XML data from WLBox
	 * @param kindJDO to store it in google app egnine datastore
	 * @param keyNameJDO to store it in google app egnine datastore
	 * @return
	 * @throws IOException 
	 * @throws JAXBException 
	 */
	public String convertXMLSenMLIntoRDF(String sensorMeasurements, String kindJDO, String keyNameJDO) throws IOException, JAXBException {

			//System.out.println(sensorMeasurements);
			Domain measurement;
			
			//TO MEASURE M3 CONVERTER PERFORMANCE
			long startTime = System.currentTimeMillis();
			
			measurement = convertXMLToJavaObjects(sensorMeasurements);
			if(measurement!=null){
				convertJavaObjectsToM3(measurement, kindJDO, keyNameJDO);
			}
			
			//TO MEASURE M3 CONVERTER PERFORMANCE
			long stopTime = System.currentTimeMillis();
			long total = stopTime - startTime;
			System.out.println("M3 converter in milliseconds: " + total);
			
			StringWriter writer = new StringWriter();
			model.write(writer);
			return writer.toString();
			
	}


	/**
	 * Convert an XML file into java object (MeasurementSenml)
	 * @param Measurements
	 * @return
	 * @throws JAXBException
	 */
	public Domain convertXMLToJavaObjects(String result) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(Domain.class);
		Unmarshaller um = context.createUnmarshaller();
		Domain measurement = (Domain) um.unmarshal(new StringReader(result));
		return measurement;
	}

	/**
	 * Convert java object (MeasurementsSenml) into RDF data and save in file
	 * @param measurement
	 * @param fileSaved
	 * @throws IOException
	 */
	public Model convertJavaObjectsToM3(Domain senmls, String kindJDO, String keyNameJDO) throws IOException{
		Model infModel = null;
		for (Measurements senml : senmls.senmlList) {
			if (senml!=null && senml.elementList!=null ){//convertit que une partie
				for (Measurement element : senml.elementList) {

					String uri = createUriUnique();
					Resource measurementURI = createMeasurementInstance(model, uri, element, senml.bn);
					Resource sensorURI = createSensorInstance(model, senml.bn, measurementURI);
					createFeatureOfInterestInstance(model, senmls.getNameZone(), sensorURI);
					infModel = addTypeSensorMeasurementAccordingToContext();//senmls.getNameZone()

				}
			}

		}
		infModel.write(System.out);
		GenericJDO.createOrUpdateMeasurementsSaved(kindJDO, keyNameJDO, infModel);
		System.out.println("kindJDO:" + kindJDO + " keyNameJDO:" + keyNameJDO);

		return infModel;
	}

	/**
	 * Create a MeasurementSenml into RDF
	 * @param model
	 * @param measurementURI
	 * @param element
	 * @throws IOException
	 */
	public Resource createMeasurementInstance(Model model, String measurementURI, Measurement element, String baseName) throws IOException{
		Property hasName = ResourceFactory.createProperty(Var.NS_M3, "hasName"); 
		Property hasValue = ResourceFactory.createProperty(Var.NS_M3, "hasValue"); 
		Property hasTime = ResourceFactory.createProperty(Var.NS_M3, "hasDateTimeValue"); 
		Property hasUnit = ResourceFactory.createProperty(Var.NS_M3, "hasUnit");

		Resource resource = model.createResource(measurementURI);
		resource.addProperty(RDF.type, model.getResource(Var.NS_M3 + "Measurement"));

		resource.addProperty(hasName, ResourceFactory.createTypedLiteral(element.getName(), XSDDatatype.XSDstring)); 	
		resource.addProperty(hasValue, ResourceFactory.createTypedLiteral(Double.toString(element.getValue()), XSDDatatype.XSDdecimal));
		resource.addProperty(hasTime, ResourceFactory.createTypedLiteral(Double.toString(element.getTime()),  XSDDatatype.XSDdateTime )); 		
		
		
		
		resource.addProperty(hasUnit, ResourceFactory.createTypedLiteral(element.getUnits(),  XSDDatatype.XSDstring)); 
		return resource;
	}

	public Resource createFeatureOfInterestInstance(Model model, String zone, Resource sensorUri) throws IOException{
		String featureOfInterestUri = Var.NS_M3 + zone;
		Property observes = ResourceFactory.createProperty(Var.NS_M3, "observes"); 
		Resource featureOfInterestResource = model.createResource(featureOfInterestUri);
		featureOfInterestResource.addProperty(RDF.type, model.getResource(Var.NS_M3 + "FeatureOfInterest"));
		sensorUri.addProperty(observes, featureOfInterestResource); 
		return featureOfInterestResource;
	}

	public Resource createSensorInstance(Model model, String sensorName, Resource measurementURI) throws IOException{
		String sensorURI = Var.NS_M3 + sensorName;
		Property produces = ResourceFactory.createProperty(Var.NS_M3, "produces"); 
		Resource resource = model.createResource(sensorURI);
		resource.addProperty(RDF.type, model.getResource(Var.NS_M3 + "Sensor"));
		resource.addProperty(produces, measurementURI); 
		return resource;
	}


	public String createUriUnique(){
		String uri = Var.NS_M3 + "Measurement"+ URI_NUM_UNIQUE;
		URI_NUM_UNIQUE ++;
		return uri;
	}

	/**
	 * Add a more specific type to understand the context
	 * ex domain = weather, measurement = temperature => new type = weatherTemperature
	 * easier for reasoning after
	 * @return
	 */
	public Model addTypeSensorMeasurementAccordingToContext(){//Resource measurementURI, Measurement element, String zoneName
		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(Var.RULE_M3_NEW_TYPE));// read rules
		reasoner.setDerivationLogging(true);
		InfModel infModel = ModelFactory.createInfModel(reasoner, model); //apply the reasoner
		
		model = infModel;

		return infModel;
	}

}
