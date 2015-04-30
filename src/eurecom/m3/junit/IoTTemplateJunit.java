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

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.VariableSparql;

/**
 * Tests for {@link Foo}.
 *
 * @author user@example.com (John Doe)
 */
public class IoTTemplateJunit {

    @Test
    public void testScenarioTemperatureWeatherSeasonFood() {
    	//test temp season scenario
    	
    	String URL = "http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=OutsideTemperatureSeasonFood";
    	
    	String sparql_query_generated = WSUtils.queryWebServicSPARQLGenerated(URL);
    	
    	//System.out.println(sparql_query_generated);
    	
    	//execute SPARQL query
    	
    	Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.PATH_M3_PROJECT_WEATHER_SENSOR_DATA);
		model.write(System.out);
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.PATH_M3_PROJECT_ONTOLOGY + "" + "m3");
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.PATH_M3_PROJECT_ONTOLOGY + Var.NATUROPATHY_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.PATH_M3_PROJECT_DATASET + Var.NATUROPATHY_DATASET_PATH);
		
		//use generic sparql query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "WeatherTemperature", false));		
		var.add(new VariableSparql("typeRecommendedUri", Var.NS_NATUROPATHY_ONTOLOGY + "Food", false));	
		String res = "";
		try {
			res = m2mappli.executeLinkedOpenRulesAndSparqlQuery(sparql_query_generated, var, Var.LINKED_OPEN_RULES_WEATHER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);

    }
    
 /*   @Test
    public void testBodyTemperatureHomeRemedies() {
    	//test temp season scenario
    	
    	//String URL = "http://sensormeasurement.appspot.com/m3/getSparqlQuery/?iotAppli=OutsideTemperatureSeasonFood";
    	
    	??String sparql_query_generated = ;
    	
    	//System.out.println(sparql_query_generated);
    	
    	//execute SPARQL query
    	
    	Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.PATH_M3_PROJECT_HEALTH_SENSOR_DATA);
		model.write(System.out);
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.PATH_M3_PROJECT_ONTOLOGY + "" + "m3");
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.PATH_M3_PROJECT_ONTOLOGY + Var.NATUROPATHY_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.PATH_M3_PROJECT_DATASET + Var.NATUROPATHY_DATASET_PATH);
		
		//use generic sparql query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "WeatherTemperature", false));		
		var.add(new VariableSparql("typeRecommendedUri", Var.NS_NATUROPATHY_ONTOLOGY + "Food", false));	
		String res = "";
		try {
			res = m2mappli.executeLinkedOpenRulesAndSparqlQuery(sparql_query_generated, var, Var.LINKED_OPEN_RULES_WEATHER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);

    }*/


    @Test
    @Ignore
    public void thisIsIgnored() {
    }
}