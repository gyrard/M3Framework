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
package eurecom.sparql.result;

import java.util.ArrayList;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * To refactor according to the generic SPARQL query
 * @author Amelie Gyrard
 *
 */
public class ExecuteSparqlRecipe extends ExecuteSparqlGeneric {

	public ExecuteSparqlRecipe(Model model, String sparqlRequest) {
		super(model, sparqlRequest);
	}

	public ArrayList<SparqlResultRecipe> getSparqlResult(ArrayList<VariableSparql> var){
		QueryExecution qe = replaceVariableInRequest(this.model, query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		
		QuerySolution soln = null;
		//SparqlResultsRecipe res = new SparqlResultsRecipe();
		ArrayList<SparqlResultRecipe> sparqlResults = new ArrayList<SparqlResultRecipe>();	
		SparqlResultRecipe sparqlResult;
		
		for ( ; results.hasNext() ; ){
			sparqlResult = new SparqlResultRecipe();
			soln = results.nextSolution() ;  
			
			if (soln.getResource("recipe")!=null) {
				sparqlResult.setRecipeUri(soln.getResource("recipe").getURI());
			}
			
			if (soln.getLiteral("recipeLabel")!=null) {
				sparqlResult.setRecipeLabel(soln.getLiteral("recipeLabel").toString());
			}
			
			if (soln.getResource("ingt")!=null) {
				sparqlResult.setIngtUri(soln.getResource("ingt").getURI());
			}
			
			if (soln.getResource("quantity")!=null) {
				sparqlResult.setIngtQuantity(soln.getResource("quantity").getURI());
			}
			
			if (soln.getLiteral("season")!=null) {				
				sparqlResult.setSeason(deleteEndLabel(soln.getLiteral("season").toString()));
			}
			sparqlResults.add(sparqlResult);	
		}
		qe.close();
		return sparqlResults;
	}
}
