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
package eurecom.generic.m2mapplication;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.SparqlResultMatchingFood;
import eurecom.sparql.result.VariableSparql;
/**
 * Still used by the WLBOX?
 * if not delete
 * @author gyrard amelie
 *
 */
public class WlboxApi {
	
	static Model model = loadDatabase(false);
	
	public static Model loadDatabase(boolean reasoner){
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_WAR + "m3");

		if(reasoner == true){
			model = ModelFactory.createRDFSModel(model);
		}
		return model;
	}
	
	public static ArrayList<SparqlResultMatchingFood> getSubclassOf(String name) {		
		ExecuteSparql resultSparql = new ExecuteSparql(model, Var.ROOT_SPARQL_EURECOM_GENERIC + "GetSubClass.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("object", Var.NS_M3 + name, false));
		ArrayList<SparqlResultMatchingFood> res = resultSparql.getSparqlResult(var);
		return res;	
	}

}
