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



import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolutionMap;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 * Class to design a generic SPARQL query for all semantic-based IoT scenarios
 * @author Amelie Gyrard
 *
 */
public class ExecuteQueryEngine {


	public Model model;
	public Query query;

	public ExecuteQueryEngine(Model model, String sparqlRequest) {
		super();
		this.model = model;
		
		//load the sparql query
		this.query = QueryFactory.create(ReadFile.readContentFile(sparqlRequest));
	}
	
	public ExecuteQueryEngine(Model model, Query query) {
		super();
		this.model = model;
		this.query = query;
	}

	/**
	 * really important returns directly returns the jena sparql result
	 * @param var variables to replace in the sparql query
	 * @return xml sparql result
	 */
	public String getSelectResultAsXML(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		String res = "No results";
		res = ResultSetFormatter.asXMLString(results);

		qe.close();
		return res;
	}
	
	public static QueryExecution replaceVariableInRequest(Model model, Query query, ArrayList<VariableSparql> var){
		QueryExecution qe = null;
		RDFNode node = null;
		QuerySolutionMap initialBinding = new QuerySolutionMap();
		//replace sparql request by variables
		if(var!=null){

			for (VariableSparql variableSparql : var) {				
				if (variableSparql.isLiterral()){
					node = model.createLiteral(variableSparql.getValue());
				}
				else{
					node = model.getResource(variableSparql.getValue());
					//System.out.println("node: " + node);
				}
				initialBinding.add(variableSparql.getVariableName(), node);			
			}		
			qe = QueryExecutionFactory.create(query, model, initialBinding);
		}
		else{
			qe = QueryExecutionFactory.create(query, model);
		}
		return qe;
	}

}
