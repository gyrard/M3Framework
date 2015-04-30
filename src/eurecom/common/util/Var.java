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
package eurecom.common.util;

/**
 * Variables used in the project to access to:
 * ontologies, datasets, rules, SPARQL queries, namespaces,
 * Sensor data,
 * Google database: Java Data Object (JDO)
 * 
 * TIPS: to easily rename a file or a directory without impacts on the project
 * 
 * @author Amelie gyrard
 * Last updated: September 2014
 *
 */
public class Var {
	
	/** VARIABLES USED FOR THE EVALUATION PART:
	 * - REASONING
	 * - SPARQL QUERY
	 * - ACCESS TO/LOAD DATA (GOOGLE APP ENGINE JDO, JENA TDB TRIPLESTORE, FILE) */	
	public static long TIME_REASONING = 0;
	public static long TIME_SPARQL_QUERY = 0;
	public static long TIME_ACCESS_SENSOR_DATA = 0;
	
	/** URL SENML API TO SIMULATE SENSOR MEASUREMENTS **/
	public static String URL_SENML_DOMAIN_KITCHEN = "http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgdraXRjaGVuDA";
	public static String URL_SENML_DOMAIN_WEATHER = "http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgd3ZWF0aGVyDA";
	public static String URL_SENML_DOMAIN_HEALTH = "http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IVCxIJWm9uZUFkbWluIgZoZWFsdGgM";
	public static String URL_SENML_DOMAIN_PLACE = "http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IUCxIJWm9uZUFkbWluIgVwbGFjZQw";
	public static String URL_SENML_DOMAIN_HOME = "http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3ITCxIJWm9uZUFkbWluIgRob21lDA";
	public static String URL_SENML_DOMAIN_TRANSPORT = "http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgd3ZWF0aGVyDA";
	
	/** NAMESPACE USED IN ONTOLOGIES AND DATASETS **/
	public static String NS_M3= "http://sensormeasurement.appspot.com/m3#";
	public static String NS_LOV4IOT= "http://sensormeasurement.appspot.com/ont/m3/lov4iot#";
	public static String NS_NATUROPATHY_DATASET= "http://sensormeasurement.appspot.com/naturopathy-dataset/";
	public static String NS_HEALTH_DATASET= "http://sensormeasurement.appspot.com/dataset/health-dataset/";
	public static String NS_WEATHER_DATASET= "http://sensormeasurement.appspot.com/dataset/weather-dataset/";
	public static String NS_NATUROPATHY_ONTOLOGY= "http://sensormeasurement.appspot.com/naturopathy#";
	public static String NS_TOURISM_DATASET= "http://sensormeasurement.appspot.com/dataset/tourism-dataset/";
	public static String NS_TRANSPORT_DATASET= "http://sensormeasurement.appspot.com/dataset/transport-dataset/";
	public static String NS_TOURISM_ONTOLOGY= "http://sensormeasurement.appspot.com/ont/m3/tourism#";
	public static String NS_HOME_ONTOLOGY= "http://sensormeasurement.appspot.com/ont/m3/home#";
	public static String NS_HOME_DATASET= "http://sensormeasurement.appspot.com/dataset/home-dataset/";
	public static String NS_EMOTION_ONTOLOGY= "http://sensormeasurement.appspot.com/ont/m3/emotion#";
	public static String NS_TRANSPORT_ONTOLOGY= "http://sensormeasurement.appspot.com/ont/m3/transport#";
	public static String NS_STAC_ONTO = "http://securitytoolbox.appspot.com/stac#";
	public static String NS_STAC_DATASET = "http://securitytoolbox.appspot.com/stac-dataset/";
	public static String NS_DCTERMS = "http://purl.org/dc/terms/";
	
	/** ONTOLOGY & DATASET ROOT **/
	public static final String ROOT_DATASET_WAR = "./dataset/";
	public static final String ROOT_OWL_WAR = "./ont/m3/";
	public static final String ROOT_OWL_SECURITY_WAR = "./ont/security/";
	
	public static String PATH_M3_PROJECT_DATASET = "E:/workspace/m3/war/dataset/";
	public static String PATH_M3_PROJECT_ONTOLOGY = "E:/workspace/m3/war/ont/m3/";
	
	/** SENML M3 RDF SENSOR DATA **/
	public static final String WEATHER_M3_SENSOR_DATA_WAR = "./dataset/sensor_data/weatherData_8KB_17Septembre2014.rdf";
	public static final String HOME_M3_SENSOR_DATA_WAR = "./dataset/sensor_data/senml_m3_home_data.rdf";
	public static final String HEALTH_M3_SENSOR_DATA_WAR = "./dataset/sensor_data/senml_m3_health_data.rdf";
	public static final String SNOW_DATASET = "./dataset/sensor_data/snow_dataset.rdf";
	public static final String PRESENCE_LIGHT_DATASET = "./dataset/sensor_data/presenceLight.rdf";
	
	public static String PATH_M3_PROJECT_WEATHER_SENSOR_DATA = "E:/workspace/m3/war/dataset/sensor_data/weatherData_8KB_17Septembre2014.rdf";
	public static String PATH_M3_PROJECT_HEALTH_SENSOR_DATA = "E:/workspace/m3/war/dataset/sensor_data/senml_m3_health_data.rdf";
	
	
	public static String SURREY_DATASET = "E:/workspace/m3/war/dataset/real_dataset/data_surrey_kat.csv";

	
	/** ONTOLOGIES & DATASETS **/
	
	/** M3 DOMAIN KNOWLEDGE **/
	public static final String M3_ONTOLOGY_PATH = Var.ROOT_OWL_WAR + "m3";
	public static final String LOV4IOT_ONTOLOGY_PATH = Var.ROOT_OWL_WAR + "lov4iot";
	public static final String LOV4IOT_DATASET_PATH = Var.ROOT_DATASET_WAR + "lov4iot-dataset";
	public static final String IOT_APPPLICATION_TEMPLATE_DATASET = Var.ROOT_DATASET_WAR + "iot-application-template-dataset";
	public static final String RULE_DATASET_PATH = Var.ROOT_DATASET_WAR+ "rule-dataset";
	
	/** TRANSPORT DOMAIN KNOWLEDGE **/
	public static final String TRANSPORT_ONTOLOGY_PATH =  Var.ROOT_OWL_WAR + "transport";
	public static final String TRANSPORT_DATASET_PATH =  Var.ROOT_DATASET_WAR + "transport-dataset";

	/** EMOTION DOMAIN KNOWLEDGE **/
	public static final String EMOTION_ONTOLOGY_PATH =  Var.ROOT_OWL_WAR + "emotion";
	public static final String EMOTION_DATASET_PATH =  Var.ROOT_DATASET_WAR + "emotion-dataset";
	
	/** WEATHER DOMAIN KNOWLEDGE **/
	public static final String WEATHER_ONTOLOGY_PATH =  Var.ROOT_OWL_WAR + "weather";
	public static final String WEATHER_DATASET_PATH =  Var.ROOT_DATASET_WAR + "weather-dataset";
	
	/** SECURITY DOMAIN KNOWLEDGE **/
	public static final String STAC_ONTOLOGY_PATH = Var.ROOT_OWL_WAR + "stac";
	public static final String STAC_DATASET_PATH = Var.ROOT_DATASET_WAR+ "stac-dataset";
	public static final String SECURITY_ONTO_KIM_ALGO = Var.ROOT_OWL_SECURITY_WAR + "Kim/securityAlgorithms";

	public static final String TOURISM_ONTOLOGY_PATH =  Var.ROOT_OWL_WAR + "tourism";
	public static final String TOURISM_DATASET_PATH =  Var.ROOT_DATASET_WAR + "tourism-dataset";
	
	public static final String NATUROPATHY_ONTOLOGY_PATH = Var.ROOT_OWL_WAR + "naturopathy";
	public static final String NATUROPATHY_DATASET_PATH = Var.ROOT_DATASET_WAR + "naturopathy-dataset";
	
	public static final String HEALTH_ONTOLOGY_PATH = Var.ROOT_OWL_WAR + "health";
	public static final String HEALTH_DATASET_PATH = Var.ROOT_DATASET_WAR + "health-dataset";
	
	public static final String HOME_ONTOLOGY_PATH = Var.ROOT_OWL_WAR + "home";
	public static final String HOME_DATASET_PATH = Var.ROOT_DATASET_WAR + "home-dataset";
	
	public static final String FOOD_TAXONOMY_ONTOLOGY_PATH = Var.ROOT_OWL_FOOD_SMARTPRODUCT + "food_taxonomy.owl";
	public static final String FOOD_RECIPE_ONTOLOGY_PATH = Var.ROOT_OWL_FOOD_SMARTPRODUCT + "recipes.owl";
	public static final String FOOD_ONTOLOGY_PATH = Var.ROOT_OWL_FOOD_SMARTPRODUCT + "food.owl";
	public static final String FOOD_GENERIC_ONTOLOGY_PATH = Var.ROOT_OWL_FOOD_SMARTPRODUCT + "generic.owl";
	
	
	/** OTHERS */
	public static String ROOT_OWL_FOOD_SMARTPRODUCT = "./WEB-INF/ontologies/food/smartProducts/";
	public static String ROOT_OWL_RESTAURANT = "./WEB-INF/ontologies/resto/";
		
	public static String ROOT_EXCEL_FILE= "./WEB-INF/EXCEL/StacApplicationResponses.xlsx";
	
	/** SPARQL **/
	public static String ROOT_SPARQL_SECURITY = "./SPARQL/security/";
	public static String ROOT_SPARQL_RESTAURANT = "./SPARQL/resto/";
	public static String ROOT_SPARQL_EURECOM_GENERIC = "./SPARQL/eurecom/generic/";
	public static String ROOT_SPARQL_EURECOM_FOOD = "./SPARQL/eurecom/food/";
	public static String ROOT_SPARQL_EURECOM_LOCATION = "./SPARQL/eurecom/location/";
	public static String ROOT_SPARQL_HEALTH = "./SPARQL/health/";
	public static String ROOT_SPARQL_EURECOM_TEMPERATURE = "./SPARQL/eurecom/temperature/";
	public static String ROOT_SPARQL_TOURISM = "./SPARQL/tourism/";
	public static String ROOT_SPARQL_EURECOM_TOURISM = "./SPARQL/eurecom/tourism/";
	public static String ROOT_SPARQL_EURECOM_HOME = "./SPARQL/home/";
	public static String ROOT_SPARQL_GENERIC = "./SPARQL/eurecom/generic/";
	public static String ROOT_SPARQL_LOV4IoT = "./SPARQL/LOV4IoT/";
	public static String ROOT_SPARQL = "./SPARQL/";
	public static String ROOT_SPARQL_TEMPLATE = "./SPARQL/template/";
	public static String ROOT_SPARQL_TRANSPORT = "./SPARQL/transport/";

	/** SPARQL QUERIES **/
	public static String ROOT_SPARQL_NATUROPATHY = "./SPARQL/naturopathy/";
	
	/** search and generate IOT application template **/
	public static String SPARQL_QUERY_SWOT_TEMPLATE_M3 = ROOT_SPARQL + "swot_template_m3.sparql";
	public static String SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE = ROOT_SPARQL + "searchIoTApplicationTemplate.sparql";
	public static String SPARQL_QUERY_TEST_SPARQL_GENERATED = ROOT_SPARQL_TEMPLATE + "testSparqlGenerated.sparql";

	
	//!!!!! // use 2 times **/
	public static String SPARQL_QUERY_NATUROPATHY_IS_RECOMMENDED_FOR = ROOT_SPARQL_NATUROPATHY + "isRecommendedFor.sparql"; 
	
	/** SPARQL QUERY **/

	public static String SPARQL_QUERY_RECIPE_FAT_FREE = ROOT_SPARQL_HEALTH + "GetRecipeFatFree.sparql";
	public static String SPARQL_QUERY_SWOT_TEMPLATE = ROOT_SPARQL + "swot_template.sparql";
	public static String SPARQL_QUERY_SWOT_TEMPLATE_RULE = ROOT_SPARQL + "swot_template_rule.sparql";
	public static String SPARQL_QUERY_M3_HUB = ROOT_SPARQL + "m3Hub.sparql";

	// NATUROPATHY SCENARIO **/
	public static String SPARQL_QUERY_WEATHER_ACTIVITY = ROOT_SPARQL + "weatherTourismActivityClothes.sparql";
	public static String SPARQL_QUERY_HEALTH_EMOTION = ROOT_SPARQL + "healthEmotionDisease.sparql";
	
	/** GENERIC **/
	public static String SPARQL_QUERY_GENERIC = ROOT_SPARQL_TEMPLATE + "m3SparqlGeneric.sparql";
	public static String SPARQL_QUERY_LIGHT_PRESENCE = ROOT_SPARQL_TEMPLATE + "testLightPresence.sparql";
	
	/** TEMPLATE **/
	public static String SPARQL_TRANSPORT_SAFETY_DEVICE_LIGHT = ROOT_SPARQL_TEMPLATE + "transportSafetyDeviceWeatherLight.sparql";
	
	
	//public static String SPARQL_QUERY_GENERIC_REASONING = ROOT_SPARQL + "sparqlGenericReasoning.sparql";
	
	public static String SPARQL_QUERY_GENERIC_SUBCLASSOF = ROOT_SPARQL_EURECOM_GENERIC + "GetSubClass.sparql";
	public static String SPARQL_QUERY_GENERIC_TYPEOF = ROOT_SPARQL_EURECOM_GENERIC + "GetType.sparql";
	
	public static String SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_SUBJECT = ROOT_SPARQL_EURECOM_GENERIC + "genericChangePredicateSubject.sparql";
	public static String SPARQL_QUERY_GENERIC_CHANGE_PREDICATE_OBJECT =ROOT_SPARQL_EURECOM_GENERIC + "genericChangePredicateObject.sparql";
	

	/** naturopathy scenario **/
//	public static String SPARQL_QUERY_WEATHER_EMOTION = "./SPARQL/naturopathy/weatherEmotion.sparql";
	
	public static String SPARQL_QUERY_TEMPLATE_SECURITY_MECHANISM_TECHNO = ROOT_SPARQL_SECURITY + "TemplateSecurityMechanismRelatedToTechno.sparql";
	public static String SPARQL_QUERY_TEMPLATE_SECURITY_ATTACK_TECHNO =ROOT_SPARQL_SECURITY + "TemplateAttackRelatedToTechno.sparql";
	public static String SPARQL_QUERY_TEMPLATE_SECURITY_SECURITY_MECHANISM =ROOT_SPARQL_SECURITY + "STAC_securityMechanism.sparql";
	
	public static String SPARQL_QUERY_WEATHER_SAFETY_DEVICE = ROOT_SPARQL + "weatherTransportSafetyDevice.sparql";
	public static String SPARQL_QUERY_TRANSPORT_DRIVER_STATE = ROOT_SPARQL_TRANSPORT + "driverState.sparql";
	
	/** ASK QUERY **/
	public static String ASK_TYPE_COLD = "./SPARQL/ASK/ask_type_cold.sparql";
	public static String ASK_TYPE_HIGH_CHOLESTEROL = "./SPARQL/ASK/ask_type_high_cholesterol.sparql";
	
	/** RULE CONSTRUCT **/
	public static String SPARQL_CONSTRUCT_TEMP_DEDUCE_WINTER = "./SPARQL/CONSTRUCT/constuctTempDeduceWinter.sparql";
	public static String SPARQL_CONSTRUCT_TEMP_SICK = "./SPARQL/CONSTRUCT/tempDeduceFlu.sparql";
	
	/** SPARQL DESCRIBE **/
	public static String SPARQL_DESCRIBE = "./SPARQL/DESCRIBE/describe.rq";
	
	/** RULE SWRL **/
	public static String LINKED_OPEN_RULES = "./RULES/LinkedOpenRules.txt";
	public static String LINKED_OPEN_RULES_HEALTH = "./RULES/LinkedOpenRulesHealth.txt";
	public static String LINKED_OPEN_RULES_HOME = "./RULES/LinkedOpenRulesHome.txt";
	public static String LINKED_OPEN_RULES_WEATHER = "./RULES/LinkedOpenRulesWeather.txt";
	public static String LINKED_OPEN_RULES_ENVIRONMENT = "./RULES/LinkedOpenRulesEnvironment.txt";
	public static String RULE_M3_NEW_TYPE = "./RULES/ruleM3Converter.txt";

	/** BDD JDO SAUV by domain **/
	public static String KIND_JDO = "home";
	public static String KEY_NAME_JDO = "smartHome";
	
	public static String KIND_JDO_TEMPERATURE = "temperature";
	public static String KEY_NAME_JDO_TEMPERATURE = "temperature";
	
	public static String KIND_JDO_HEALTH = "health";
	public static String KEY_NAME_JDO_HEALTH = "healthMeasurement";
	
	public static String KIND_JDO_HEALTH_V2 = "healthv2";
	public static String KEY_NAME_JDO_HEALTH_V2 = "healthMeasurementv2";

	public static String KIND_JDO_FOOD = "food";
	public static String KEY_NAME_JDO_FOOD = "foodMeasurement";
	
	public static String KIND_JDO_WEATHER = "weather";
	public static String KEY_NAME_JDO_WEATHER = "weatherMeasurement";
	
	public static String KIND_JDO_TRANSPORT = "transport";
	public static String KEY_NAME_JDO_TRANSPORT = "transportMeasurement";
	
	public static String KIND_JDO_TEST = "test";
	public static String KEY_NAME_JDO_TEST = "test";
	
	public static String KIND_JDO_PLACE = "location";
	public static String KEY_NAME_JDO_PLACE = "locationMeasurement";
	
	public static String KIND_JDO_HOME = "home";
	public static String KEY_NAME_JDO_HOME = "homeMeasurement";

	public static String SQL = "C:/Users/root/Documents/Dev/workspace/RecommendationWebSem/SQL/";
	

		
	public static String TEST_TDB = "file:/Users/root/Documents/Dev/workspace/RecommendationWebSem/testTDB/";

	
	public static String NS_kmi_taxonomy = "http://kmi.open.ac.uk/projects/smartproducts/ontologies/food_taxonomy.owl#";
	public static String NS_kmi_food = "http://kmi.open.ac.uk/projects/smartproducts/ontologies/food.owl#";
	public static String NS_kmi_recipe = "http://kmi.open.ac.uk/projects/smartproducts/ontologies/recipes.owl#";
	
	public static String NS_wine= "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
	public static String NS_pizza= "http://www.co-ode.org/ontologies/pizza/2005/10/18/pizza.owl#";
	public static String NS_tiscali_food = "http://web.tiscali.it/lchkl/ontology/FoodOntology.owl#";
	
	public static String NS_travel = "http://www.owl-ontologies.com/travel.owl#";
	
	public static String NS_foaf = "http://xmlns.com/foaf/0.1/";
	public static String NS_rdfs = "http://www.w3.org/2000/01/rdf-schema#";

}
