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



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.atlas.io.OutStreamUTF8;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.QuerySolutionMap;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.JSONOutputResultSet;

import eurecom.common.util.ReadFile;
import eurecom.common.util.WSUtils;
import eurecom.constrained.devices.ResultSensorDataReasoning;
import eurecom.search.knowledge.ResultDomainKnowledge;

/**
 * Class to design a generic SPARQL query for all semantic-based IoT scenarios
 * @author Amelie Gyrard
 *
 */
public class ExecuteSparqlGeneric {


	public Model model;
	public Query query;

	public ExecuteSparqlGeneric(Model model, String sparqlRequest) {
		super();
		this.model = model;
		
		//load the sparql query
		this.query = QueryFactory.create(ReadFile.readContentFile(sparqlRequest));
	}

	public ArrayList<ResultDomainKnowledge> searchIoTApplicationTemplate(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		ArrayList<ResultDomainKnowledge> res = new ArrayList<ResultDomainKnowledge>();
		ResultDomainKnowledge sparqlResult = new ResultDomainKnowledge();

		QuerySolution soln = null;

		for ( ; results.hasNext() ; ){
			sparqlResult = new ResultDomainKnowledge();
			soln = results.nextSolution() ; 

			if (soln.getResource("m2mappli")!=null) {
				sparqlResult.setM2mappliUri(soln.getResource("m2mappli").getURI());
			}
			if (soln.getResource("onto")!=null) {
				sparqlResult.setOnto(soln.getResource("onto").getURI());
			}
			if (soln.getResource("dataset")!=null) {
				sparqlResult.setDataset(soln.getResource("dataset").getURI());
			}
			if (soln.getResource("rule")!=null) {
				sparqlResult.setRule(soln.getResource("rule").getURI());
			}
			if (soln.getResource("sparql")!=null) {
				sparqlResult.setSparql(soln.getResource("sparql").getURI());
			}
			if (soln.getLiteral("m2mapplilabel")!=null) {
				sparqlResult.setM2mapplilabel(soln.getLiteral("m2mapplilabel").toString());
			}
			if (soln.getLiteral("m2mapplicomment")!=null) {
				sparqlResult.setM2mapplicomment(soln.getLiteral("m2mapplicomment").toString());
			}
			if (soln.getResource("inferTypeUri")!=null) {
				sparqlResult.setInferTypeUri(soln.getResource("inferTypeUri").getURI());
			}
			if (soln.getResource("typeRecommendedUri")!=null) {
				sparqlResult.setTypeRecommendedUri(soln.getResource("typeRecommendedUri").getURI());
			}

			res.add(sparqlResult);	
		}

		qe.close();
		return res;
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
	
	//test
	public String getSelectResultAsJson(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(b, results);
		String json = b.toString( );

		qe.close();
		return json;
	}

	public ArrayList<ResultSensorDataReasoning> getSelectResultAnsParseIt(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		ArrayList<ResultSensorDataReasoning> res = new ArrayList<ResultSensorDataReasoning>();
		ResultSensorDataReasoning sparqlResult = new ResultSensorDataReasoning();

		//System.out.println(ResultSetFormatter.asXMLString(results));
		QuerySolution soln = null;

		for ( ; results.hasNext() ; ){
			sparqlResult = new ResultSensorDataReasoning();
			soln = results.nextSolution() ;  

			if (soln.getLiteral("name")!= null) {

				String name = soln.get("name").toString();
				int index = name.indexOf("^^");//delete ^^http://www.w3.org/2001/XMLSchema#string
				name = name.substring(0, index);
				sparqlResult.setName(name);
				//System.out.println(name);
			}	
			if (soln.getLiteral("value")!= null) {
				String value = soln.get("value").toString();
				int index = value.indexOf("^^");//delete ^^http://www.w3.org/2001/XMLSchema#decimal
				value = value.substring(0, index);
				sparqlResult.setValue(value);
			}	
			if (soln.getLiteral("unit")!= null) {
				String unit = soln.get("unit").toString();
				int index = unit.indexOf("^^");//delete ^^http://www.w3.org/2001/XMLSchema#string
				unit = unit.substring(0, index);
				sparqlResult.setUnit(unit);
			}	
			if (soln.getLiteral("inferType")!= null) {

				sparqlResult.setInferType(deleteEndLabel(soln.get("inferType").toString()));

				/*	String inferType = soln.getResource("inferType").getURI();
				int index = inferType.indexOf("#");//get end uri
				inferType = inferType.substring(index+1, inferType.length());
				sparqlResult.setInferType(inferType);*/
			}	
			if (soln.getLiteral("deduce")!= null) {
				sparqlResult.setDeduce(deleteEndLabel(soln.get("deduce").toString()));
				System.out.println(soln.get("deduce").toString());
			}	
			if (soln.getLiteral("suggest")!= null) {
				sparqlResult.setSuggest(deleteEndLabel(soln.get("suggest").toString()));
			}	


			res.add(sparqlResult);	
		}

		qe.close();
		return res;
	}



	/*	public String getDeleteResult(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		ResultSet results = qe. ;
		String res = "No results";
		res = ResultSetFormatter.asXMLString(results);

		qe.close();
		return res;
	}
	 */
	//test 20/02/14
	public Model getDescribeResult(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		Model results = qe.execDescribe() ;
		qe.close();
		return results;
	}

	public List<QuerySolution> getSparqlResultAsList(ArrayList<VariableSparql> var){

		QueryExecution qe = replaceVariableInRequest(this.model, this.query, var);
		//get result from sparql request
		ResultSet results = qe.execSelect() ;
		List<QuerySolution> res;
		res = ResultSetFormatter.toList(results);

		qe.close();
		return res;
	}

	/**
	 * delete @en dans les labels
	 * @param label
	 * @return
	 */
	public static String deleteEndLabel(String label){
		String s = label;
		String res = "";
		int i = s.indexOf("@");
		if(i>0 && i< s.length()){
			res= s.substring(0, i);
		}
		return res;
	}

	/**
	 * 
	 * @param label
	 * @return
	 */
	public static String deleteTypeXsd(String value){
		if(value.contains("^")){
			int index = value.indexOf("^");
			value = value.substring(0, index);

		}	
		//System.out.println("value: " + value);
		return value;
	}


	/**
	 * Retrieve the correct label with the lang 
	 * @param lc
	 * @param label
	 * @return
	 */
	public static SparqlResultMatchingFood retrieveLabel(SparqlResultMatchingFood lc, String label){
		if(label.contains("@")){
			int index = label.indexOf("@");
			lc.setLabel(label.substring(0, index));
		}
		else{
			lc.setLabel(label);
		}
		return lc;
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
