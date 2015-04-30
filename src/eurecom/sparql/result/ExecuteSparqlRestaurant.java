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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.QuerySolutionMap;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

/**
 * To refactor according to the generic SPARQL query
 * @author Amelie Gyrard
 *
 */
public class ExecuteSparqlRestaurant {


	Model model;
	String sparqlRequest;

	public ExecuteSparqlRestaurant(Model model, String sparqlRequest) {
		super();
		this.model = model;
		this.sparqlRequest = sparqlRequest;
	}

	public ArrayList<SparqlResultRestaurant> executeRequest(ArrayList<VariableSparql> var){

		String queryString = readFile(sparqlRequest);
		Query query = QueryFactory.create(queryString);
		QuerySolutionMap initialBinding = new QuerySolutionMap();
		RDFNode node = null;
		QueryExecution qe = null;

		//replace sparql request by variables
		if(var!=null){

			for (VariableSparql variableSparql : var) {				
				if (variableSparql.isLiterral()){
					node = model.createLiteral(variableSparql.getValue());
				}
				else{
					node = model.getResource(variableSparql.getValue());
				}
				initialBinding.add(variableSparql.getVariableName(), node);			
			}		
			qe = QueryExecutionFactory.create(query, model, initialBinding);
		}
		else{
			qe = QueryExecutionFactory.create(query, model);
		}

		//get result from sparql request
		ArrayList<SparqlResultRestaurant> result = null;
		ResultSet results = qe.execSelect() ;
		result = getSparqlResult(results);
		qe.close();
		return result;

	}

	public static ArrayList<SparqlResultRestaurant> getSparqlResult(ResultSet results){
		QuerySolution soln = null;
		ArrayList<SparqlResultRestaurant> result = new ArrayList<SparqlResultRestaurant>();
		for ( ; results.hasNext() ; ){
			SparqlResultRestaurant res = new SparqlResultRestaurant();
			soln = results.nextSolution() ;  

			if (soln.get("subject") instanceof ResourceImpl) {
				res.setUri(soln.getResource("subject").getURI());
			}
			if (soln.get("cityLabel") instanceof LiteralImpl) {
				res.setCity(soln.get("cityLabel").toString());
			}
			if (soln.get("location") instanceof LiteralImpl) {
				res.setLocation(soln.get("location").toString());
			}
			if (soln.get("ratingString") instanceof LiteralImpl) {
				res.setRating(soln.get("ratingString").toString());
			}
			if (soln.get("foodType") instanceof LiteralImpl) {
				res.setFoodType(soln.get("foodType").toString());
			}
			
			result.add(res);	
		}
		return result;
	}

/*	*//**
	 * Retrieve the correct label with the lang 
	 * @param lc
	 * @param label
	 * @return
	 *//*
	public static SparqlResultRestaurant retrieveLabel(SparqlResultRestaurant lc, String label){
		if(label.contains("@")){
			int index = label.indexOf("@");
			lc.setLabel(label.substring(0, index));
		}
		else{
			lc.setLabel(label);
		}
		return lc;
	}*/

	/**
	 * Read a file, ex. Sparql
	 * @param file
	 * @return
	 */
	public static String readFile(String file){
		String text="";
		try{
			InputStream ips=new FileInputStream(file); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				text+=ligne+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			e.printStackTrace();
		}
		return text;
	}

}
