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
import java.util.List;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;
import eurecom.sparql.result.ExecuteSparqlRecipeNaturopathy;
import eurecom.sparql.result.ExecuteSparqlRestaurant;
import eurecom.sparql.result.SparqlResultRecipeNaturopathy;
import eurecom.sparql.result.SparqlResultRestaurant;
import eurecom.sparql.result.VariableSparql;
import eurecom.test.stuff.TestGeonames;

/**
 * to refactor
 * should be deleted
 * replaced by M2MAppGeneric 
 * the web service java code should use the function M2M AppGeneric executeLinkedOpenRulesAndSparqlQuery
 */
public class M2MAppResto extends M2MAppGeneric {

	public M2MAppResto() {
		super("location", "locationMeasurement");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_WAR + "m3");
		ReadFile.enrichJenaModelOntologyDataset(model, Var.ROOT_OWL_RESTAURANT + "restaurantMooney.owl");
	}

	public ArrayList<SparqlResultRecipeNaturopathy> getMeasurements() {
		ExecuteSparqlRecipeNaturopathy reqSenml = new ExecuteSparqlRecipeNaturopathy(model, Var.ROOT_SPARQL_EURECOM_LOCATION + "GetLocation.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		ArrayList<SparqlResultRecipeNaturopathy> resultSparqlsenml = reqSenml.getSparqlResult(var);
		return resultSparqlsenml;
	}

	public Place linkToGeonames() {

		Place res = null;
		ArrayList<SparqlResultRecipeNaturopathy> loc = getMeasurements();
		double latitude = 0;
		double longitude = 0;

		for (SparqlResultRecipeNaturopathy values : loc) {
			if (values.getName().compareTo("lat")==0) {
				latitude= values.getValue();
				System.out.println("lat amelie" + latitude);
				
				
			}
			if (values.getName().compareTo("lon")==0) {
				longitude= values.getValue();
			}			
		}

		List<Place> res2 = TestGeonames.getPlace(latitude, longitude);
		if (! res2.isEmpty()) {
			res = res2.get(0);
		}

		return res;
	}

	public ArrayList<SparqlResultRestaurant> linkToResto() {

		Place city = linkToGeonames();
		//appeler web service precedent
		System.out.println("city.name: " + city.name);

		ExecuteSparqlRestaurant req = new ExecuteSparqlRestaurant(model, Var.ROOT_SPARQL_RESTAURANT + "searchRestaurantSpecificCity.sparql");
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		VariableSparql v1 = new VariableSparql("labelCityUser", city.name, true);
		var.add(v1);
		ArrayList<SparqlResultRestaurant> resultSparql = req.executeRequest(var);
		return resultSparql;
	}
}
