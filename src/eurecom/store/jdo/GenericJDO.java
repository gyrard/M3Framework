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
package eurecom.store.jdo;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * This class handles all the CRUD operations related to
 * Product entity.
 *
 */
public class GenericJDO {

	/**
	 * Update the product
	 * @param name: name of the product
	 * @param description : description
	 * @return  updated product
	 */
	public static void createOrUpdateMeasurementsSaved(String kind, String keyName, Model model) {
		Entity product = getMeasurementSaved(kind, keyName);//foodMeasurement
		ByteArrayOutputStream modelByte = new ByteArrayOutputStream(); 

		//convert the jena model into byte array to store it in google application engine jdo
		model.write(modelByte);
		//System.out.println("Model jena saved: "+ modelByte);
		byte[] byteTable = modelByte.toByteArray();
		Blob blob = new Blob(byteTable);
		//System.out.println(blob.getBytes());
		
		if (product == null) {			
			product = new Entity(kind, keyName);			
			product.setProperty("description", blob);

		} else {
			product.setProperty("description", blob);
		}
		UtilJDO.persistEntity(product);
	}

	/**
	 * Retrun all the products
	 * @param kind : of kind product
	 * @return  products
	 */
	public static Iterable<Entity> getAllMeasurementSaved(String kind) {
		return UtilJDO.listEntities(kind, null, null);
	}

	/**
	 * Get product entity
	 * @param name : name of the product
	 * @return: product entity
	 */
	public static Entity getMeasurementSaved(String kindJDO, String keyNameJDO) {
		System.out.println("Find entity, kindJDO:" + kindJDO + " keyNameJDO:" + keyNameJDO);
		Key key = KeyFactory.createKey(kindJDO,keyNameJDO);
		return UtilJDO.findEntity(key);
	}

	/**
	 * Get all items for a product
	 * @param name: name of the product
	 * @return list of items
	 */

	public static List<Entity> getItems(String kind, String keyName) {
		Query query = new Query();
		Key parentKey = KeyFactory.createKey(kind, keyName);
		query.setAncestor(parentKey);
		query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, parentKey);
		List<Entity> results = UtilJDO.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());
		return results;
	}

	/**
	 * Delete product entity
	 * @param productKey: product to be deleted
	 * @return status string
	 */
	public static String deleteMeasurementSaved(String kind, String productKey)
	{
		Key key = KeyFactory.createKey(kind,productKey);	   

		List<Entity> items = getItems(kind, productKey);	  
		if (!items.isEmpty()){
			return "Cannot delete, as there are items associated with this product.";	      
		}	    
		UtilJDO.deleteEntity(key);
		return "Product deleted successfully";

	}
}
