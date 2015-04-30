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
/*package eurecom.test.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.VariableSparql;

public class MainEval {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
			
			try {
			//load the M2M measurement
			M2MAppGeneric m2mappli = new M2MAppGeneric(Var.KIND_JDO_WEATHER , Var.KEY_NAME_JDO_WEATHER);
			
			//load domain specific datasets and ontologies
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.ROOT_OWL + "m3");
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.TRANSPORT_DATASET_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_ONTOLOGY_PATH);
			ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, Var.WEATHER_DATASET_PATH);
			

			//variable in the sparql query
			ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
			var.add(new VariableSparql("inferTypeUri", Var.NS_M3 + "Precipitation", false));
			var.add(new VariableSparql("typeRecommendedUri", Var.NS_TRANSPORT_ONTOLOGY+ "SafetyDevice", false));

		
			m2mappli.executeLinkedOpenRulesAndSparqlQuery(Var.SPARQL_QUERY_WEATHER_SAFETY_DEVICE, var, Var.LINKED_OPEN_RULES_WEATHER);
			
			avg_access_data = avg_access_data + Var.TIME_ACCESS_SENSOR_DATA;
			
			avg_reaoning = avg_reaoning + Var.TIME_REASONING;
			
			avg_sparql = avg_sparql + Var.TIME_SPARQL_QUERY;
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Access to data:" + avg_access_data);
		
		System.out.println("Reasoning:" + avg_reaoning);
		
		System.out.println("Sparql:" + avg_sparql);


	}

}
*/