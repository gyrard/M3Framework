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

import java.util.ArrayList;

import org.json.JSONException;

import com.hp.hpl.jena.rdf.model.Model;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.ExecuteSparqlGeneric;
import eurecom.sparql.result.VariableSparql;

/**
 * M2M application to reason on sensor data provided by raspberryPi
 * @author Amelie gyrard
 *
 */
public class MainReasonSensorData {

	public static String SENSOR_DATA_FILE = "./WAR/dataset/sensor_data/raspberry_sensor_data";
	public static String RASPBERRYPI_WEATHER_LUMINOSITY = "http://192.168.22.42/get_sensor_data/?s_id=3edcvfr4";
	public static String M3_RULES_PATH ="./WAR/RULES/ruleM3newType.txt";
	public static String M3_ONTOLOGY_PATH ="./WAR/WEB-INF/ontologies/m3";
	public static String LOR_FILE_PATH ="./WAR/RULES/LinkedOpenRules.txt";
	
	//tourism scenario
	public static String TOURISM_ONTOLOGY_PATH ="./WAR/WEB-INF/ontologies/tourism";
	public static String TOURISM_DATASET_PATH ="./WAR/dataset/tourism-dataset";
	public static String SPARQL_QUERY_WEATHER_ACTIVITY_PATH = "./WAR/SPARQL/weatherTourismActivityClothes.sparql";
	
	//transport scenario
	public static String SPARQL_QUERY_WEATHER_SAFETY_DEVICES_PATH = "./WAR/SPARQL/weatherTransportSafetyDevice.sparql";
	public static String SPARQL_QUERY_WEATHER_DRIVER_STATE_PATH = "./WAR/SPARQL/transport/driverState.sparql";
	public static String TRANSPORT_ONTOLOGY_PATH ="./WAR/WEB-INF/ontologies/transport";
	public static String TRANSPORT_DATASET_PATH ="./WAR/dataset/transport-dataset";
	
	public static String WEATHER_ONTOLOGY_PATH ="./WAR/WEB-INF/ontologies/weather";
	public static String WEATHER_DATASET_PATH ="./WAR/dataset/weather-dataset";


	public static void main(String[] args) {
		//String sensor_data = WSUtils.queryWebServiceJSON(RASPBERRYPI_WEATHER_LUMINOSITY);
		String sensor_data = ReadFile.readContentFile(SENSOR_DATA_FILE);
		ConvertRaspberrySensorData converter = new ConvertRaspberrySensorData();
		Model model;
		try {
			model = converter.convertRaspberrySensorDataToRDF(sensor_data, M3_RULES_PATH);
		
		//load the M2M measurement
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);

		//load domain specific datasets and ontologies
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, M3_ONTOLOGY_PATH);//no need
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, TRANSPORT_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, TRANSPORT_DATASET_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, WEATHER_ONTOLOGY_PATH);
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, WEATHER_DATASET_PATH);

		//variable in the sparql query
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "WeatherLuminosity", false));
		var.add(new VariableSparql("typeRecommendedUri", Var.NS_TRANSPORT_ONTOLOGY+ "SafetyDevice", false));//SafetyDevice

		Model inf = m2mappli.reasonWithJenaRules(LOR_FILE_PATH);//reasoner for jena rules //Var.LINKED_OPEN_RULES
		ExecuteSparqlGeneric reqSenml = new ExecuteSparqlGeneric(inf, SPARQL_QUERY_WEATHER_SAFETY_DEVICES_PATH);

		ArrayList<ResultSensorDataReasoning> resultAfterReasoning = reqSenml.getSelectResultAnsParseIt(var);
		for (int i = 0; i < resultAfterReasoning.size(); i++) {
			System.out.println(resultAfterReasoning.get(i).toString());
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String convertXMLIntoJavaObjects(){
		String result = "";

		return result;
	}
}
