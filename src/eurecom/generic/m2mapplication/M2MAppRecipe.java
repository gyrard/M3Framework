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
import java.util.HashSet;
import java.util.Set;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.ExecuteSparqlRecipe;
import eurecom.sparql.result.ExecuteSparqlRecipeNaturopathy;
import eurecom.sparql.result.SparqlResultMatchingFood;
import eurecom.sparql.result.SparqlResultRecipe;
import eurecom.sparql.result.SparqlResultRecipeNaturopathy;
import eurecom.sparql.result.VariableSparql;

/**
 * to refactor
 * should be deleted
 * replaced by M2MAppGeneric 
 * the web service java code should use the function M2M AppGeneric executeLinkedOpenRulesAndSparqlQuery
 */
public class M2MAppRecipe extends M2MAppGeneric {
	
	public M2MAppRecipe() {
		
		super(Var.KIND_JDO_FOOD, Var.KEY_NAME_JDO_FOOD);
		
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_WAR + "m3");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_WAR + "naturopathy");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_DATASET_WAR + "naturopathy-dataset");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_FOOD_SMARTPRODUCT + "food_taxonomy.owl");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_FOOD_SMARTPRODUCT + "recipes.owl");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_FOOD_SMARTPRODUCT + "food.owl");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_FOOD_SMARTPRODUCT + "generic.owl");
	}


	public ArrayList<SparqlResultRecipeNaturopathy> getFoodMeasurements() {
		//ExecuteSparqlTempSeasonFoodRecipeDisease reqSenml = new ExecuteSparqlTempSeasonFoodRecipeDisease(model, Var.ROOT_SPARQL_EURECOM_FOOD + "GetTypeFood.sparql");
		ExecuteSparqlRecipeNaturopathy reqSenml = new ExecuteSparqlRecipeNaturopathy(model, Var.ROOT_SPARQL_EURECOM_GENERIC + "GetMeasurementGeneric.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("type", Var.NS_M3 + "Food", false));
		ArrayList<SparqlResultRecipeNaturopathy> resultSparqlsenml = reqSenml.getSparqlResult(var);
		return resultSparqlsenml;
	}

	public ArrayList<SparqlResultRecipe> searchRecipeWithIngredient(String ingt) {
		ExecuteSparqlRecipe reqSenml = new ExecuteSparqlRecipe(model, Var.ROOT_SPARQL_EURECOM_FOOD + "SearchRecipeContainIngt.sparql");
		ArrayList<VariableSparql> varsenml = new ArrayList<VariableSparql>();
		varsenml.add(new VariableSparql("ingtUser", ingt, true));
		return reqSenml.getSparqlResult(varsenml);
	}

	public ArrayList<SparqlResultMatchingFood> matchingFood() {
		ArrayList<SparqlResultMatchingFood> res = new ArrayList<SparqlResultMatchingFood>();
		ArrayList<SparqlResultMatchingFood> resultSparql  = null;

		ExecuteSparql reqSenml = new ExecuteSparql(model, Var.ROOT_SPARQL_EURECOM_FOOD + "GetSenmlData.sparql");
		ArrayList<VariableSparql> varsenml = null;

		ArrayList<SparqlResultMatchingFood> resultSparqlsenml = reqSenml.getSparqlResult(varsenml);
		System.out.println(resultSparqlsenml.size());

		ExecuteSparql req = new ExecuteSparql(model, Var.ROOT_SPARQL_EURECOM_FOOD + "FilterLabel.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();

		String label = "";
		for (SparqlResultMatchingFood labelUri : resultSparqlsenml) {	
			
			//delete ^^http://www.w3.org/2001/XMLSchema#string
			label = labelUri.getLabel();
			int index = label.indexOf("^^");
			label = label.substring(0, index);

			var.add(new VariableSparql("labelUser",label, true));
			resultSparql = req.getSparqlResult(var);
			res.addAll(resultSparql);
		}
		return res;
	}

	public Set<SparqlResultRecipeNaturopathy> searchFoodAvailableKitchenSeason(ArrayList<VariableSparql> var){
		//matching food sensors with food in the smart products

		ArrayList<SparqlResultMatchingFood> resultSparql = matchingFood();

		ExecuteSparqlRecipeNaturopathy req = null;

		Set<SparqlResultRecipeNaturopathy> ingtSeason = new HashSet<SparqlResultRecipeNaturopathy>();
		ArrayList<SparqlResultRecipeNaturopathy> season = new ArrayList<SparqlResultRecipeNaturopathy>();
		//ArrayList<SparqlResult> ingtsKitchen = resultSparql.getResults();

		//for all ingredients in the kitchen: search the season
		for (SparqlResultMatchingFood ingt : resultSparql) {
			//System.out.println("ingt:" + ingt.getLabel() + " " + ingt.getUri());
			req = new ExecuteSparqlRecipeNaturopathy(model, Var.ROOT_SPARQL_EURECOM_FOOD + "hasSeason.sparql");	
			System.out.println(Var.NS_NATUROPATHY_DATASET + ingt.getLabel());
			var.add(new VariableSparql("foodUri", Var.NS_NATUROPATHY_DATASET + ingt.getLabel(), false));
			//var.add(new VariableSparql("food", ingt.getUri(), false));
			season = req.getSparqlResult(var);
			ingtSeason.addAll(season);
		}
		return ingtSeason;
	}
	
	public ArrayList<SparqlResultRecipe> getRecipeDetails(String recipe){
		ExecuteSparqlRecipe reqSenml = new ExecuteSparqlRecipe(model, Var.ROOT_SPARQL_EURECOM_FOOD + "GetRecipeDetail.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("recipeUser", recipe, true));
		ArrayList<SparqlResultRecipe> resultSparqlsenml = reqSenml.getSparqlResult(var);
		return resultSparqlsenml;
	}	

}
