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
package eurecom.rule.extractor;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import eurecom.common.util.ReadFile;

public class MainRuleExtractor {
	
	public static String STAROCH_ONTO_PATH = "./WAR/WEB-INF/ontologies/weatherOnto/WeatherOntologyKofler.owl" ;
	public static String NS_KOFLER = "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/WeatherOntology.owl#";
	
	public static void main(String[] args) {
		OntModel model = ModelFactory.createOntologyModel();
		ReadFile.enrichJenaModelOntologyDataset(model, STAROCH_ONTO_PATH);
	//	model.write(System.out);
		
		Resource r = model.getResource("hasIntensity");
		
		ExtendedIterator<Restriction> ontClass= model.listRestrictions();
		while (ontClass.hasNext()) {
			Restriction restriction = (Restriction) ontClass.next();
			//get owl restriciton on property
			
		//if ( restriction.getOnProperty().toString() == NS_KOFLER + "hasIntensity"){
			System.out.println(restriction.getOnProperty());
		//	}
			
		}
		
		
		//1) get concept with wind
		
	}

}
