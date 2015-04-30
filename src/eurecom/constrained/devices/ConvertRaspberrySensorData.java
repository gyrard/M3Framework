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
package eurecom.constrained.devices;

import org.json.JSONArray;
import org.json.JSONObject;

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
import eurecom.data.converter.ConvertSensorDataToM3;

/**
 * Enrich SenML/json data with semantics
 * Sensor data are provided by the raspberry pi - WLBOX
 * @author Amelie gyrard
 *
 */
public class ConvertRaspberrySensorData {

	public  Model convertRaspberrySensorDataToRDF(String sensor_data, String m3_rules_url) throws org.json.JSONException{
		InfModel infModel = null;

		ConvertSensorDataToM3 converter = new ConvertSensorDataToM3();

		JSONObject jo_senml=null;
		if (sensor_data != null) {

			jo_senml = new JSONObject(sensor_data);

			// get json array node
			JSONArray raw_data = jo_senml.getJSONArray("e");

			String measurementURI = converter.createUriUnique();
			Property hasName = ResourceFactory.createProperty(Var.NS_M3, "hasName"); 
			Resource resourceMeasurement = converter.model.createResource(measurementURI);
			resourceMeasurement.addProperty(RDF.type, converter.model.getResource(Var.NS_M3 + "Measurement"));

			// looping through the entire m2m device configurations
			for (int i = 0; i < raw_data.length(); i++) {
				JSONObject c = raw_data.getJSONObject(i);

				// get type sensor measurement
				resourceMeasurement.addProperty(hasName, ResourceFactory.createTypedLiteral(c.getString("s_typemeasurement"), XSDDatatype.XSDstring)); 

				//get value sensor data
				Property hasValue = ResourceFactory.createProperty(Var.NS_M3, "hasValue"); 
				resourceMeasurement.addProperty(hasValue, ResourceFactory.createTypedLiteral(c.getString("s_value"), XSDDatatype.XSDdecimal));


				Property hasTime = ResourceFactory.createProperty(Var.NS_M3, "hasDateTimeValue"); 
				resourceMeasurement.addProperty(hasTime, ResourceFactory.createTypedLiteral(c.getString("s_timestamp"),  XSDDatatype.XSDdateTime)); 		

				Property hasUnit = ResourceFactory.createProperty(Var.NS_M3, "hasUnit");
				resourceMeasurement.addProperty(hasUnit, ResourceFactory.createTypedLiteral(c.getString("s_unit"),  XSDDatatype.XSDstring)); 

				String sensorURI = Var.NS_M3 + c.getString("s_typemeasurement") + "sensor";
				Property produces = ResourceFactory.createProperty(Var.NS_M3, "produces"); 
				Resource resourceSensor = converter.model.createResource(sensorURI);
				resourceSensor.addProperty(RDF.type, converter.model.getResource(Var.NS_M3 + "Sensor"));
				resourceSensor.addProperty(produces, resourceMeasurement); 

				//add domain = feature of interest
				String featureOfInterestUri = Var.NS_M3 + c.getString("s_domainname");;
				Property observes = ResourceFactory.createProperty(Var.NS_M3, "observes"); 
				Resource featureOfInterestResource = converter.model.createResource(featureOfInterestUri);
				featureOfInterestResource.addProperty(RDF.type, converter.model.getResource(Var.NS_M3 + "FeatureOfInterest"));

				resourceSensor.addProperty(observes, featureOfInterestResource); 

			}

			//apply the reasoner to get new type to later reason in a unified way
			Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(m3_rules_url));// read rules
			reasoner.setDerivationLogging(true);
			infModel = ModelFactory.createInfModel(reasoner, converter.model); //apply the reasoner

			return infModel;


		}
		return infModel;
	}
}
