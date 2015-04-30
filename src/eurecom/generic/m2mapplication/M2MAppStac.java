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

import java.io.ByteArrayInputStream;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.Var;
import eurecom.store.jdo.GenericJDO;

/**
 * to refactor
 * should be deleted
 * replaced by M2MAppGeneric 
 * the web service java code should use the function M2M AppGeneric executeLinkedOpenRulesAndSparqlQuery
 */
public class M2MAppStac {

	public Model model;
	boolean reasoner;
	public static String KIND_JDO = "stac_collaboratif";
	public static String KEYNAME_JDO = "stac_create";

	public M2MAppStac() {
		super();
		this.reasoner = false;
		model = loadDatabaseFromGoogle();

	}

	public Model loadDatabaseFromGoogle(){
		//load database from google
		Entity product = GenericJDO.getMeasurementSaved(KIND_JDO, KEYNAME_JDO);//getSTACSaved
		ByteArrayInputStream modelByte = null;
		if (product!=null){
			Blob blob = (Blob) product.getProperty("description");
			byte[] byteTable = blob.getBytes();
			modelByte = new ByteArrayInputStream(byteTable); 
		}
		Model modelGoogle = ModelFactory.createDefaultModel();
		modelGoogle.read(modelByte, Var.NS_STAC_ONTO);
		return modelGoogle;
	}
}
