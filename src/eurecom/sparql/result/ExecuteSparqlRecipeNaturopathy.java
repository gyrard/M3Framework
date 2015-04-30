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
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * To refactor according to the generic SPARQL query
 * @author Amelie Gyrard
 *
 */
public class ExecuteSparqlRecipeNaturopathy extends ExecuteSparqlGeneric{

	public ExecuteSparqlRecipeNaturopathy(Model model, String sparqlRequest) {
		super(model, sparqlRequest);
	}	
	
	public ArrayList<SparqlResultRecipeNaturopathy> getSparqlResult(ArrayList<VariableSparql> var){
		QueryExecution qe = replaceVariableInRequest(this.model, query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		
		//System.out.println("Query"+ query);
		
		QuerySolution soln = null;
		ArrayList<SparqlResultRecipeNaturopathy> res = new ArrayList<SparqlResultRecipeNaturopathy>();
		SparqlResultRecipeNaturopathy sparqlResult = null;

		for ( ; results.hasNext() ; ){
			sparqlResult = new SparqlResultRecipeNaturopathy();
			soln = results.nextSolution() ;  

			if (soln.getResource("subject")!=null) {
				//System.out.println("subject uri: " + soln.getResource("subject").getURI());
				sparqlResult.setUri(soln.getResource("subject").getURI());
			}
			
			if (soln.getResource("seasonUri")!=null) {
				System.out.println("season uri: " + soln.getResource("seasonUri").getURI());
				sparqlResult.setSeasonUri(soln.getResource("seasonUri").getURI());
			}
			
			if (soln.getLiteral("season")!= null) {	
				System.out.println("season: " + soln.get("season").toString());
				sparqlResult.setSeason(deleteEndLabel(soln.get("season").toString()));
			}	
			
			if (soln.getResource("foodUri")!=null) {
				sparqlResult.setFoodUri(soln.getResource("foodUri").getURI());
			}
			
			if (soln.getLiteral("food")!= null) {	
				sparqlResult.setFood(deleteEndLabel(soln.get("food").toString()));
			}
			
			if (soln.getResource("diseaseUri")!=null) {
				sparqlResult.setDiseaseUri(soln.getResource("diseaseUri").getURI());
			}
			
			String name = "";
			if (soln.getLiteral("name")!= null) {				
				name = soln.get("name").toString();
				int index = name.indexOf("^^");//delete ^^http://www.w3.org/2001/XMLSchema#string
				name = name.substring(0, index);
				
				sparqlResult.setName(name);
				System.out.println("test" + name);
			}
			
			if (soln.getLiteral("baseName")!= null) {
				sparqlResult.setBaseName(soln.get("baseName").toString());
			}
			
			if (soln.getLiteral("value")!= null) {
				String r = soln.get("value").toString();
				sparqlResult.setValue(Double.valueOf(deleteTypeXsd(soln.get("value").toString())));
				System.out.println("hihi" + r);
			}	
			
			if (soln.getLiteral("time")!= null) {
				sparqlResult.setTime(deleteTypeXsd(soln.get("time").toString()));
			}
			
			if (soln.getLiteral("diseaseLabel")!= null) {	
				sparqlResult.setDiseaseLabel(deleteEndLabel(soln.get("diseaseLabel").toString()));
			}
		
			
			if (soln.getLiteral("unit")!= null) {
				sparqlResult.setUnits(deleteTypeXsd(soln.get("unit").toString()));
			}	
			
			if (soln.getResource("type")!= null) {
				sparqlResult.setType(soln.getResource("type").getURI());
				System.out.println("type" + soln.getResource("type").getURI());
			}
			
			if (soln.getResource("recipeUri")!=null) {
				sparqlResult.setRecipeUri(soln.getResource("recipeUri").getURI());
			}
			
			if (soln.getLiteral("recipeLabel")!=null) {
				sparqlResult.setRecipeLabel(soln.getLiteral("recipeLabel").toString());
			}
			
			if (soln.getResource("quantity")!=null) {
				sparqlResult.setIngtQuantity(soln.getResource("quantity").getURI());
			}

			res.add(sparqlResult);	
		}
		qe.close();
		return res;
	}

}
