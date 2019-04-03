package genericIoTReasoner.application.generic;
/**
 * Last Updated: March 2019
 * Updated: August 2018
 * @author Amelie Gyard, Knoesis, Wright State University, Ohio, USA
 * 
 * Last updated: March 2019
 * - patient snoring level scenario
 * - patient step counts scenario
 *
 */
public class VariablesFileLocation {
		
		// STEP 1: LOAD RAW SENSOR DATA (e.g., SEML/XML)
		public static final String SENML_XML_HEALTH_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Health_Data.xml";
		public static final String SENML_XML_OUTDOOR_AIR_QUALITY_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_OutdoorAirQuality_Data.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Patient_Data.xml";
		
		// SCENARIO: STEPS COUNT
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/StepsCount/senML_Patient_Data_StepsCount_ActivePatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_MODERATE_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/StepsCount/senML_Patient_Data_StepsCount_ModerateActivePatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_MILD_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/StepsCount/senML_Patient_Data_StepsCount_MildActivePatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_HIGH_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/StepsCount/senML_Patient_Data_StepsCount_HighActivePatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_SENDENTATY_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/StepsCount/senML_Patient_Data_StepsCount_SedentaryPatientScenario.xml";
		
		// SCENARIO: BODY MASS INDEX
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_BMI_UNDERWEIGHT = "./STEP1_SenML_XML_rawSensorDataset/BodyMassIndex/senML_Patient_Data_BMI_UnderWeightCondition.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_BMI_NORMAL = "./STEP1_SenML_XML_rawSensorDataset/BodyMassIndex/senML_Patient_Data_BMI_NormalWeightCondition.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_BMI_OBESE = "./STEP1_SenML_XML_rawSensorDataset/BodyMassIndex/senML_Patient_Data_BMI_ObeseWeightCondition.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_BMI_OVERWEIGHT = "./STEP1_SenML_XML_rawSensorDataset/BodyMassIndex/senML_Patient_Data_BMI_OverWeightCondition.xml";
		
		// SCENARIO: FOOD CALORIES
		public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_VERY_LOW_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/Calories/senML_food_VeryLowCaloricContent_Scenario.xml";
		public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_LOW_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/Calories/senML_food_LowCaloricContent_Scenario.xml";
		public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_MEDIUM_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/Calories/senML_food_MediumCaloricContent_Scenario.xml";
		public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_HIGH_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/Calories/senML_food_HighCaloricContent_Scenario.xml";
		public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_VERY_HIGH_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/Calories/senML_food_VeryHighCaloricContent_Scenario.xml";
		
		// SCENARIO: SNORING LEVEL
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_NORMAL_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_NormalSnoringLevel_PatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_MILD_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_MildSnoringLevel_PatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_MODERATE_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_ModereteSnoringLevel_PatientScenario.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_SEVERE_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_SevereSnoringLevel_PatientScenario.xml";
		
		
		// SCENARIO: SLEEP DISORDER BREATHING (SDB)
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_NORMAL_SLEEP_DISORDER_BREATHING_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/SleepDisorderBreathing/senML_Patient_Data_NormalSleepDisorderBreathing.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_MILD_SLEEP_DISORDER_BREATHING_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/SleepDisorderBreathing/senML_Patient_Data_MildSleepDisorderBreathing.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_MODERATE_SLEEP_DISORDER_BREATHING_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/SleepDisorderBreathing/senML_Patient_Data_ModerateSleepDisorderBreathing.xml";
		public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_SEVERE_SLEEP_DISORDER_BREATHING_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/SleepDisorderBreathing/senML_Patient_Data_SevereSleepDisorderBreathing.xml";
		
		public static final String SENML_XML_POLLEN_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Pollen_Data.xml";//pollenOneInstanceJsonkhealth
		public static final String SENML_XML_PEAK_FLOW_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_PeakFlow_Data.xml";
		public static final String SENML_XML_HEART_RATE_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_HeartRate_Data.xml";
		public static final String SENML_XML_INSIDE_TEMPERATURE_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_InsideTemperature_Data.xml";
		public static final String SENML_XML_OUTSIDE_HUMIDITY_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_OutsideHumidity_Data.xml";
			
		// STEP 1: LOAD RAW SENSOR DATA (e.g., JSON data from kHealth project)
		public static final String POLLEN_RAW_SENSOR_DATA_JSON_KHEALTH = "./STEP1A_rawSensorDataset_khealth/RealDatasetPollenJsonCompliant3Indented250Instances.json";//
						
		// STEP 2: RULES FOR THE SEMANTIC ANNOTATOR
		public static String RULES_SEMANTIC_ANNOTATION_COMPLIANT_WITH_ONTOLOGY = "./STEP2_ruleSemanticAnnotation/rulesSemanticAnnotationCompliantWithOntology.txt";
		
		// STEP 3: AUTOMATIC SEMANTIC ANNOTATION -> SEMANTIC SENSOR DATASETS AUTOMATICALLY GENERATED
		public static final String GENERATED_SEMANTIC_SENSOR_DATA = "./STEP3_semanticSensorDataset/generated_semantic_sensor_data.rdf";
		
		// useful in case of issues with the semantic annotator
		public static final String HEALTH_SEMANTIC_SENSOR_DATA = "./semanticSensorDataset/senml_m3_health_data.rdf";
		public static final String WEATHER_SEMANTIC_SENSOR_DATA = "./semanticSensorDataset/weatherData_8KB_17Septembre2014.rdf";
		
		// STEP 4: LOAD ONTOLOGIES
		public static final String M3_ONTOLOGY = "./STEP4_ontologies/m3.owl";
		public static final String NATUROPATHY_ONTOLOGY = "./STEP4_ontologies/naturopathy.owl";
		public static final String HEALTH_ONTOLOGY = "./STEP4_ontologies/health.owl";
		public static final String KAO_ONTOLOGY_V2_REASONER = "./STEP4_ontologies/kaoV2.owl";
		public static final String DRUG_ONTOLOGY = "./STEP4_ontologies/healthOntologies/dron-full.owl";
		public static final String DRUG_RXNORM_ONTOLOGY = "./STEP4_ontologies/healthOntologies/dron-rxnorm.owl";
		public static final String RXNORM_ONTOLOGY = "./STEP4_ontologies/healthOntologies/RXNORM.ttl";
		public static final String KAO_ONTOLOGY_V2_UTKARSHANI = "./STEP34_ontologies/kaoV2IntegrationUtkarshani.owl";//kaoV2IntegrationUtkarshani //kHealthAsthma
		public static final String SMART_PRODUCTS_FOOD_TAXONOMY = "./STEP4_ontologies/food/smartProducts/food_taxonomy.owl";
		public static final String SMART_PRODUCTS_FOOD_ONTOLOGY = "./STEP4_ontologies/food/smartProducts/food.owl";
		public static final String SMART_PRODUCTS_RECIPES = "./STEP4_ontologies/food/smartProducts/recipes.owl";
		public static final String HUMAN_DISEASE_ONTOLOGY = "./STEP4_ontologies/HumanDiseaseOntology/humanDiseaseOntology.rdf";
		public static final String SYMPTOM_ONTOLOGY = "./STEP4_ontologies/symptom/symptom.owl";
		
		// STEP 5: RULES TO DEDUCE MEANINGFUL KNOWLEDGE
		public static final String LINKED_OPEN_RULES_HEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health.txt";
		public static final String LINKED_OPEN_RULES_ASTHMA = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Asthma.txt";		
		public static final String LINKED_OPEN_RULES_WELL_BEING_HAPPINESS = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_WellBeingHappiness.txt";		
		public static final String LINKED_OPEN_RULES_OBESITY_BODY_MASS_INDEX = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_BodyMassIndex.txt";
		public static final String LINKED_OPEN_RULES_AIR_QUALITY_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_AirQualitykHealth.txt";
		public static final String LINKED_OPEN_RULES_ASTHMA_CONTROL_LEVEL = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_AsthmaControlLevel.txt";
		public static final String LINKED_OPEN_RULES_HEALTH_CALORIE = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health_Calorie.txt";
		public static final String LINKED_OPEN_RULES_HEALTH_SNORING_LEVEL = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health_SnoringLevel.txt";
		public static final String LINKED_OPEN_RULES_HEALTH_STEP_COUNT = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health_StepCount.txt";
		public static final String LINKED_OPEN_RULES_POLLEN_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health_Pollen.txt";
		public static final String LINKED_OPEN_RULES_PEAK_FLOW_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health_PeakFlow.txt";
		public static final String LINKED_OPEN_RULES_HEART_RATE_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_Health_HeartRate.txt";
		public static final String LINKED_OPEN_RULES_INSIDE_TEMPERATURE_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_InsideTemperature.txt";
		public static final String LINKED_OPEN_RULES_OUTSIDE_HUMIDITY_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_OutsideHumidity.txt";
		public static final String LINKED_OPEN_RULES_SLEEP_DISORDER_BREATHING = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRules_SleepDisorderBreathing.txt";
		
	
		// STEP 6: SPARQL QUERY
		//public static final String SPARQL_QUERY_NATUROPATHY = "./sparql/m3SparqlNaturopathyScenario.sparql";
		//public static final String SPARQL_QUERY_NATUROPATHY_MINIMAL = "./sparql/m3SparqlNaturopathyScenarioMinimal.sparql";
		public static final String GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT = "./STEP6_sparqlQuery/m3SparqlGenericSOSACompliant.sparql";
		public static final String SPARQL_QUERY_SOSA_COMPLIANT_OBESITY_RULE_BMI_SCENARIO = "./STEP6_sparqlQuery/tutorial_obesity_reasoner/getPatient_hasBodyMassIndex_viaRule.sparql";
		public static final String SPARQL_QUERY_PATIENT_WOKE_UP_SCENARIOS = "./STEP6_sparqlQuery/patientWokeUpScenario.sparql";
		public static final String SPARQL_QUERY_GET_SYMPTOMS_RISK_FACTORS_THERAPY_PATIENT = "./STEP6_sparqlQuery/getSymptomsRiskFactorsTherapyPatientAge.sparql";
		
		// SPARQL TUTORIAL ASTHMA
		public static final String SPARQL_DESCRIBE_QUERY_TUTORIAL_VENTOLIN_DRUG_ONTOLOGY= "./STEP6_sparqlQuery/tutorial_asthma/describeVentolinDrugOntology.sparql";
		public static final String SPARQL_DESCRIBE_QUERY_TUTORIAL_MORE_INFORMATION_MEDICATION= "./STEP6_sparqlQuery/tutorial_asthma/describeMedicationGeneric.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_AGE = "./STEP6_sparqlQuery/tutorial_asthma/getPatientAge.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_SYMPTOM = "./STEP6_sparqlQuery/tutorial_asthma/getPatientSymptom.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_SPECIFIC_SYMPTOM = "./STEP6_sparqlQuery/tutorial_asthma/getPatientSpecificSymptom.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_SYMPTOM_COUNT = "./STEP6_sparqlQuery/tutorial_asthma/getPatientSymptomCount.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_WOKE_UP_LAST_NIGHT = "./STEP6_sparqlQuery/tutorial_asthma/getPatientWokeUpLastNight.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_RISK_FACTOR = "./STEP6_sparqlQuery/tutorial_asthma/getPatientRiskFactor.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_SPECIFIC_PATIENT_RISK_FACTOR = "./STEP6_sparqlQuery/tutorial_asthma/getSpecificPatientRiskFactor.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_SPECIFIC_PATIENT_SPECIFIC_RISK_FACTOR = "./STEP6_sparqlQuery/tutorial_asthma/getSpecificPatientSpecificRiskFactor.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_THERAPY = "./STEP6_sparqlQuery/tutorial_asthma/getPatientTherapy.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_THERAPY_INHALER_RESCUE_MEDICATION = "./STEP6_sparqlQuery/tutorial_asthma/getAllPatientTherapyInhalerRescueMedication.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_THERAPY_INHALER_CONTROLLER_MEDICATION = "./STEP6_sparqlQuery/tutorial_asthma/getAllPatientTherapyInhalerControllerMedication.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_HUMAN_DISEASE_ONTOLOGY_VIRAL_INFECTOUS_DISEASE = "./STEP6_sparqlQuery/HumanDiseaseOntology/get_subClassOf_ViralInfectousDisease.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_SYMPTOM_ONTOLOGY_COUGH = "./STEP6_sparqlQuery/symptom/getSpecificConceptMoreInformationCoughExample.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_SYMPTOM_ONTOLOGY_CHEST_SOUND = "./STEP6_sparqlQuery/symptom/getSpecificConceptMoreInformationChestSoundExample.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_SYMPTOM_ONTOLOGY_DRY_COUGH = "./STEP6_sparqlQuery/symptom/getSpecificConceptMoreInformationDryCoughExample.sparql";
		public static final String SPARQL_SEARCH_MEDICATION_LABEL_RXNORM= "./STEP6_sparqlQuery/tutorial_asthma/searchMedicationLabel.sparql";
		public static final String SPARQL_QUERY_DPS_SCORE= "./STEP6_sparqlQuery/tutorial_asthma/get_patient_dps_score.sparql";
		//public static final String SPARQL_QUERY_TUTORIAL_PATIENT_HAS_ALLERGY = "./STEP6_sparqlQuery/tutorial_asthma/getPatient_hasAllergy.sparql";
		
		// SPARQL TUTORIAL FOOD
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_RISK_FACTOR_ALLERGY_NUTS = "./STEP6_sparqlQuery/tutorial_food/getPatientRiskFactorAllergyNut.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_GET_ALL_NUTS = "./STEP6_sparqlQuery/tutorial_food/getAllNuts.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_GENERIC_GET_ANY_FOOD = "./STEP6_sparqlQuery/tutorial_food/genericGetAnyFood.sparql";
		public static final String SPARQL_QUERY_ASK_TUTORIAL_IS_PECAN_NUT = "./STEP6_sparqlQuery/tutorial_food/isPecanNut_askQuery.sparql";
		public static final String SPARQL_QUERY_ASK_TUTORIAL_GENERIC = "./STEP6_sparqlQuery/tutorial_food/askQueryGeneric.sparql";
		public static final String SPARQL_QUERY_GET_RECIPE_MADE_OF_SUBSTANCES = "./STEP6_sparqlQuery/tutorial_food/getRecipeMadeOfSubtance.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_CHOCOLATE_DEPRESSION = "./STEP6_sparqlQuery/tutorial_food/chocolateDepression.sparql";
		
		public static final String SPARQL_QUERY_TUTORIAL_REMOTE_SPARQL_QUERY_FOOD_PROJECT_PERONI = "./STEP6_sparqlQuery/tutorial_food/Peroni2016FoodProject/remoteSparqlQueryExample.sparql";
		
		public static final String SPARQL_QUERY_TUTORIAL_OBESITY_PATIENT_WEIGHT = "./STEP6_sparqlQuery/tutorial_obesity/getPatientWeight.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_OBESITY_PATIENT_HEIGHT = "./STEP6_sparqlQuery/tutorial_obesity/getPatientHeight.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_OBESITY_PATIENT_BODY_MASS_INDEX = "./STEP6_sparqlQuery/tutorial_obesity/getPatientBodyMassIndex.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_OBESITY_PATIENT_HAS_DIET = "./STEP6_sparqlQuery/tutorial_obesity/getPatient_hasDiet.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_HAS_INGESTED_CALORIE = "./STEP6_sparqlQuery/tutorial_obesity/getPatient_hasIngestedCalorie.sparql";
		public static final String SPARQL_QUERY_TUTORIAL_PATIENT_STEP_COUNT = "./STEP6_sparqlQuery/tutorial_stepCount/getPatientStepCount.sparql";
		//public static final String SPARQL_QUERY_TUTORIAL_PATIENT_SNORING_LEVEL = "./STEP6_sparqlQuery/tutorial_snoringLevel/getPatientSnoringLevel.sparql";

		// NAMESPACE FOR ONTOLOGIES
		public static final String NAMESPACE_M3 = "http://sensormeasurement.appspot.com/m3#";
		public static final String NAMESPACE_SOSA = "http://www.w3.org/ns/sosa/";
		public static final String NAMESPACE_KAO = "http://purl.org/kao#";
		public static final String NAMESPACE_AO_BIO_PORTAL = "http://childhealthservicemodels.eu/asthma#";
		public static final String NAMESPACE_SSN_V2 = "http://www.w3.org/ns/ssn/";
		public static final String NAMESPACE_QUDT = "http://qudt.org/schema/qudt#";
		public static final String NAMESPACE_KOFLER_WEATHER_ONTOLOGY = "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/WeatherOntology.owl";
		public static final String NAMESPACE_STAROCH_WEATHER_ONTOLOGY = "http://paul.staroch.name/thesis/SmartHomeWeather.owl";
		public static final String NAMESPACE_ASTHMA_ONTOLOGY_BIOPORTAL = "http://childhealthservicemodels.eu/asthma#";
		public static final String NAMESPACE_FOOD_ONTOLOGY_SMART_PRODUCTS = "http://kmi.open.ac.uk/projects/smartproducts/ontologies/food.owl#";
		public static final String NAMESPACE_NATUROPATHY_ONTOLOGY = "http://sensormeasurement.appspot.com/naturopathy#";
		public static final String NAMESPACE_NATUROPATHY_DATASET = "http://sensormeasurement.appspot.com/naturopathy-dataset/";
		
		// DOMAIN DATASETS
		public static final String NATUROPATHY_DATASET = "./semanticDomainDataset/naturopathy-dataset.rdf";
		public static final String HEALTH_DATASET = "./semanticDomainDataset/health-dataset.rdf";

}
