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
package eurecom.m3.junit;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.ExecuteSparqlGeneric;
import eurecom.sparql.result.VariableSparql;

/**
 * To Test the IoT application template
 *
 * @author Amelie Gyrard 
 * Created November 17, 2015
 * The M3 framework is now maintained from Insight, Galway, Ireland
 */
public class IoTTemplateJunit {

	
	//display summer
    @Test
    public void testScenarioTemperatureWeatherSeasonFood() {
    	
    	String URL = "http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=OutsideTemperatureSeasonFood";
    	
    	String sparql_query_generated = WSUtils.queryWebService(URL);
    	
    	String result_iot_appli = WSUtils.queryWebServiceXML("http://sensormeasurement.appspot.com/naturopathy/seasonTemperatureFoodRecipe");
    	
    	Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
		model.write(System.out);
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_WAR);
		
		//execute sparql query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		m2mappli.model = m2mappli.reasonWithJenaRules(Var.LINKED_OPEN_RULES_WEATHER_WAR);
		Query query = QueryFactory.create(sparql_query_generated);
		ExecuteSparqlGeneric reqSenml = new ExecuteSparqlGeneric(m2mappli.model, query);
		String resultSparqlsenml = reqSenml.getSelectResultAsXML(var);
		System.out.println(resultSparqlsenml);
		
		//assertTrue(resultSparqlsenml.equals(result_iot_appli));

    }
    
    // test http://sensormeasurement.appspot.com/m3/generateTemplate/?iotAppli=WeatherLuminosityEmotion
    @Test
    public void testScenarioWeatherLuminosityEmotion() {
    	String URL = "http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=WeatherLuminosityEmotion";
    	
    	String sparql_query_generated = WSUtils.queryWebService(URL);
    	
    	String result_iot_appli = WSUtils.queryWebServiceXML("http://sensormeasurement.appspot.com/naturopathy/emotion_luminosity");
    	
    	Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
		model.write(System.out);
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_WAR );
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_WAR);
		
		//execute sparql query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		Model inf = m2mappli.reasonWithJenaRules(Var.LINKED_OPEN_RULES_WEATHER_WAR);
		Query query = QueryFactory.create(sparql_query_generated);
		ExecuteSparqlGeneric reqSenml = new ExecuteSparqlGeneric(inf, query);
		String resultSparqlsenml = reqSenml.getSelectResultAsXML(var);
		System.out.println(resultSparqlsenml);
		
		//assertTrue(resultSparqlsenml.equals(result_iot_appli));

    }
    
    
    //should display sunny cloudy
    @Test
    public void testLuminosityTransportationSafetyDevice() {
    	String URL = "http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=WeatherTransportationSafetyDeviceLight";
    	
    	String sparql_query_generated = WSUtils.queryWebService(URL);
    	
    	String result_iot_appli = WSUtils.queryWebServiceXML("http://sensormeasurement.appspot.com/transport/safety_device_weather/WeatherLuminosity");
    	
    	Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.WEATHER_M3_SENSOR_DATA_WAR);
		model.write(System.out);
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_WAR );
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_DATASET_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_WAR);
		
		//execute sparql query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		m2mappli.model = m2mappli.reasonWithJenaRules(Var.LINKED_OPEN_RULES_WEATHER_WAR);
		Query query = QueryFactory.create(sparql_query_generated);
		ExecuteSparqlGeneric reqSenml = new ExecuteSparqlGeneric(m2mappli.model, query);
		String resultSparqlsenml = reqSenml.getSelectResultAsXML(var);
		System.out.println(resultSparqlsenml);
		
		//assertTrue(resultSparqlsenml.equals(result_iot_appli));
    }
    
    //should display HighFever, lemon, etc.
    @Test
    public void testBodyTemperatureSymptomsHomeRemedies() {
    	String URL = "http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=BodyTemperatureSymptomHomeRemedy";
    	
    	String sparql_query_generated = WSUtils.queryWebService(URL);
    	
    	String result_iot_appli = WSUtils.queryWebServiceXML("http://sensormeasurement.appspot.com/naturopathy/sick");
    	
    	Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.HEALTH_M3_SENSOR_DATA_WAR);
		
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.M3_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.NATUROPATHY_DATASET_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HEALTH_ONTOLOGY_WAR);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.HEALTH_DATASET_WAR);
		//m2mappli.model.write(System.out);
		
		//testJunitHomeRemedies.sparql
		
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "BodyTemperature", false));// we look for body temperature measurement
		
		try {
			String resultSparqlsenml = m2mappli.executeLinkedOpenRulesAndSparqlQuery
					("./WAR/SPARQL/template/testJunitHomeRemedies.sparql", var, Var.LINKED_OPEN_RULES_HEALTH_WAR);
			System.out.println(resultSparqlsenml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//execute sparql query
		/*ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		Model inf = m2mappli.reasonWithJenaRules(Var.LINKED_OPEN_RULES_HEALTH_WAR);
		Query query = QueryFactory.create(sparql_query_generated);
		ExecuteSparqlGeneric reqSenml = new ExecuteSparqlGeneric(inf, query);
		String resultSparqlsenml = reqSenml.getSelectResultAsXML(var);*/

		
		//assertTrue(resultSparqlsenml.equals(result_iot_appli));

    }
    
    //
    
 
}