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
package knoesis.application.skeleton;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

/**
 * The generic semantic-based IoT application
 * We try to design all application in this way
 * The other files in this package should be adapted to this generic class
 * 
 * @author Amelie Gyrard
 * 
 * Created 2015 - 2015 PhD Thesis
 * Update June 2016
 *
 */
public class GenericApplication {

	public Model model;
	String kindJDO;
	String keynameJDO;
	String query;


	public GenericApplication() {
		model = ModelFactory.createDefaultModel();
	}
	
	// constructor with data stored in the Jena model
	public GenericApplication(Model model) {
		this.model = model;

	}
	
	
	/**
	 * Apply the Jena reasoner
	 * https://jena.apache.org/documentation/inference/
	 * Execute the Sensor-based Linked Open Rules (S-LOR) component form the M3 framework
	 * @param JenaRuleFile where are the jena rules
	 * @return the model with inference
	 */
	public Model executeReasoningEngine(String JenaRuleFile){
		
		//LOAD THE JENA RULES TO DEDUCE MEANINGFUL KNOWLEDGE
		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(JenaRuleFile));// read rules
		
		//DO NOT DELETE - REMINDER FOR SOUMYA 
		//for android use Rule.parseRule 
		reasoner.setDerivationLogging(true);
		
		//APPLY THE JENA REASONING ENGINE
		InfModel inf = ModelFactory.createInfModel(reasoner, this.model);
		
		//RETURN DATA WITH MORE MEANINGFUL KNOWLEDGE
		return inf;
	}

}
