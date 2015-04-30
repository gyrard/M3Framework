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
// Copyright 2011, Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package eurecom.store.jdo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;


/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 */
public class UtilJDO {

  private static final Logger logger = Logger.getLogger(UtilJDO.class.getCanonicalName());
  private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();  

/**
 * 
 * @param entity  : entity to be persisted
 */
  public static void persistEntity(Entity entity) {
  	logger.log(Level.INFO, "Saving entity");
  	datastore.put(entity);  	
  }

	/**
	 * Delete the entity from persistent store represented by the key
	 * @param key : key to delete the entity from the persistent store
	 */
  public static void deleteEntity(Key key) {
    logger.log(Level.INFO, "Deleting entity");
    datastore.delete(key);  	
  }
  
  /**
   * Delete list of entities given their keys
   * @param keys
   */
  public static void deleteEntity(final List<Key> keys){
    datastore.delete(new Iterable<Key>() {
		public Iterator<Key> iterator() {
		  return keys.iterator();
		}
      });    
  }

	/**
	 * Search and return the entity from datastore.
	 * @param key : key to find the entity
	 * @return  entity
	 */
 
  public static Entity findEntity(Key key) {
  	logger.log(Level.INFO, "Search the entity");
  	try {	  
  	  return datastore.get(key);
  	} catch (EntityNotFoundException e) {
  	  return null;
  	}
  }
 

	/***
	 * Search entities based on search criteria
	 * @param kind
	 * @param searchBy
	 *            : Searching Criteria (Property)
	 * @param searchFor
	 *            : Searching Value (Property Value)
	 * @return List all entities of a kind from the cache or datastore (if not
	 *         in cache) with the specified properties
	 */
  public static Iterable<Entity> listEntities(String kind, String searchBy,
			String searchFor) {
  	logger.log(Level.INFO, "Search entities based on search criteria");
  	Query q = new Query(kind);
  	if (searchFor != null && !"".equals(searchFor)) {
  	  q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
  	}
  	PreparedQuery pq = datastore.prepare(q);
  	return pq.asIterable();
  }
  
  
  /**
   * Search entities based on ancestor
   * @param kind
   * @param ancestor
   * @return
   */
  public static Iterable<Entity> listChildren(String kind, Key ancestor) {
  	logger.log(Level.INFO, "Search entities based on parent");
  	Query q = new Query(kind);
  	q.setAncestor(ancestor);
  	q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
  	PreparedQuery pq = datastore.prepare(q);
  	return pq.asIterable();
  }
  
  /**
   * 
   * @param kind
   * @param ancestor
   * @return
   */
  public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
  	logger.log(Level.INFO, "Search entities based on parent");
  	Query q = new Query(kind);
  	q.setAncestor(ancestor).setKeysOnly();
  	q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
  	PreparedQuery pq = datastore.prepare(q);
  	return pq.asIterable();
  }

	/**
	 * List the entities in JSON format
	 * 
	 * @param entities  entities to return as JSON strings
	 */
  public static String writeJSON(Iterable<Entity> entities) {
    logger.log(Level.INFO, "creating JSON format object");
  	StringBuilder sb = new StringBuilder();
  	
  	int i = 0;
  	sb.append("{\"data\": [");
  	for (Entity result : entities) {
  	  Map<String, Object> properties = result.getProperties();
  	  sb.append("{");
  	  if (result.getKey().getName() == null)
  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
  	  else
  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
  
  	  for (String key : properties.keySet()) {
  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
  	  }
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	  sb.append("},");
  	  i++;
  	}
  	if (i > 0) {
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	}  
  	sb.append("]}");
  	return sb.toString();
  }
  
  /**
	 * Retrieves Parent and Child entities into JSON String
	 * 
	 * @param entities
	 *            : List of parent entities
	 * @param childKind
	 *            : Entity type for Child
	 * @param fkName
	 *            : foreign-key to the parent in the child entity
	 * @return JSON string
	 */
  public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName) {
  	logger.log(Level.INFO, "creating JSON format object for parent child relation");
  	StringBuilder sb = new StringBuilder();
  	int i = 0;
  	sb.append("{\"data\": [");
  	for (Entity result : entities) {
  	  Map<String, Object> properties = result.getProperties();
  	  sb.append("{");
  	  if (result.getKey().getName() == null)
  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
  	  else
  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
  	  for (String key : properties.keySet()) {
  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
  	  }
  	  Iterable<Entity> child = listEntities(childKind, fkName,
  	  String.valueOf(result.getKey().getId()));
  	  for (Entity en : child) {
  		for (String key : en.getProperties().keySet()) {
  		  sb.append("\"" + key + "\" : \"" + en.getProperties().get(key)+ "\",");
  		}
  	  }
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	  sb.append("},");
  	  i++;
  	}
  	if (i > 0) {
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	}  
  	sb.append("]}");
  	return sb.toString();
  }
 
  
	/**
	 * Utility method to send the error back to UI
	 * @param data
	 * @param resp
	 * @throws IOException 
	 */
  public static String getErrorMessage(Exception ex) throws IOException{
    return "Error:"+ex.toString();
  }
 
  /**
   * get DatastoreService instance
   * @return DatastoreService instance
   */
  public static DatastoreService getDatastoreServiceInstance(){
	  return datastore;
  }
}