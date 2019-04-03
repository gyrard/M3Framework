package genericIoTReasoner.application.generic;
/**
 * Last Updated: August 2018
 * @author Amelie Gyard, Knoesis, Wright State University, Ohio, USA
 *
 */
public class old_VariablesFileLocation {
	
	// STEP 1: LOAD RAW SENSOR DATA (e.g., SEML/XML)
			public static final String SENML_XML_HEALTH_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Health_Data.xml";
			public static final String SENML_XML_OUTDOOR_AIR_QUALITY_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_OutdoorAirQuality_Data.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Patient_Data.xml";
			
			// SCENARIO: STEPS COUNT
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/stepsCount/senML_Patient_Data_StepsCount_ActivePatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_MODERATE_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/stepsCount/senML_Patient_Data_StepsCount_ModerateActivePatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_MILD_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/stepsCount/senML_Patient_Data_StepsCount_MildActivePatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_HIGH_ACTIVE_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/stepsCount/senML_Patient_Data_StepsCount_HighActivePatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_STEPS_COUNT_SENDENTATY_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/stepsCount/senML_Patient_Data_StepsCount_SedentaryPatientScenario.xml";
			
			// SCENARIO: FOOD CALORIES
			public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_VERY_LOW_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/calories/senML_food_VeryLowCaloricContent_Scenario.xml";
			public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_LOW_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/calories/senML_food_LowCaloricContent_Scenario.xml";
			public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_MEDIUM_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/calories/senML_food_MediumCaloricContent_Scenario.xml";
			public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_HIGH_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/calories/senML_food_HighCaloricContent_Scenario.xml";
			public static final String SENML_XML_RAW_SENSOR_DATA_FOOD_VERY_HIGH_CALORIC_CONTENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/calories/senML_food_VeryHighCaloricContent_Scenario.xml";
			
			// SCENARIO: SNORING LEVEL
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_NORMAL_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_NormalSnoringLevel_PatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_MILD_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_MildSnoringLevel_PatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_MODERATE_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_ModereteSnoringLevel_PatientScenario.xml";
			public static final String SENML_XML_PATIENT_RAW_SENSOR_DATA_SEVERE_SNORING_LEVEL_PATIENT_SCENARIO = "./STEP1_SenML_XML_rawSensorDataset/snoringLevel/senML_Patient_Data_SevereSnoringLevel_PatientScenario.xml";
			
			public static final String SENML_XML_POLLEN_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Pollen_Data.xml";//pollenOneInstanceJsonkhealth
			public static final String SENML_XML_PEAK_FLOW_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_PeakFlow_Data.xml";
			public static final String SENML_XML_HEART_RATE_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_HeartRate_Data.xml";
			public static final String SENML_XML_INSIDE_TEMPERATURE_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_InsideTemperature_Data.xml";
			public static final String SENML_XML_OUTSIDE_HUMIDITY_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_OutsideHumidity_Data.xml";
	
		
		/*// STEP 1: LOAD RAW SENSOR DATA (e.g., SEML/XML)
		public static final String HEALTH_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Health_Data.xml";
		public static final String OUTDOOR_AIR_QUALITY_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_OutdoorAirQuality_Data.xml";
		public static final String POLLEN_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Pollen_Data.xml";//pollenOneInstanceJsonkhealth

		public static final String PEAK_FLOW_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_PeakFlow_Data.xml";
		public static final String HEART_RATE_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_HeartRate_Data.xml";
		public static final String INSIDE_TEMPERATURE_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_InsideTemperature_Data.xml";
		public static final String OUTSIDE_HUMIDITY_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_OutsideHumidity_Data.xml";
		public static final String PATIENT_RAW_SENSOR_DATA = "./STEP1_SenML_XML_rawSensorDataset/senML_Patient_Data.xml";*/
		
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
		public static final String KAO_ONTOLOGY_V2_UTKARSHANI = "./STEP34_ontologies/kaoV2IntegrationUtkarshani.owl";//kaoV2IntegrationUtkarshani //kHealthAsthma
		
		/*// STEP 5: RULES TO DEDUCE MEANINGFUL KNOWLEDGE
		public static final String LINKED_OPEN_RULES_HEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHealth.txt";
		public static final String LINKED_OPEN_RULES_ASTHMA = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesAsthma.txt";
		public static final String LINKED_OPEN_RULES_AIR_QUALITY_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesAirQualitykHealth.txt";
		public static final String LINKED_OPEN_RULES_POLLEN_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesPollenkHealth.txt";
		public static final String LINKED_OPEN_RULES_PEAK_FLOW_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesPeakFlowkHealth.txt";
		public static final String LINKED_OPEN_RULES_HEART_RATE_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHeartRatekHealth.txt";
		public static final String LINKED_OPEN_RULES_INSIDE_TEMPERATURE_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesInsideTemperature.txt";
		public static final String LINKED_OPEN_RULES_OUTSIDE_HUMIDITY_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesOutsideHumidity.txt";*/
		
		// STEP 5: RULES TO DEDUCE MEANINGFUL KNOWLEDGE
		public static final String LINKED_OPEN_RULES_HEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHealth.txt";
		public static final String LINKED_OPEN_RULES_ASTHMA = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesAsthma.txt";
		public static final String LINKED_OPEN_RULES_ASTHMA_CONTROL_LEVEL = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesAsthmaControlLevel.txt";
				
		public static final String LINKED_OPEN_RULES_OBESITY_BODY_MASS_INDEX = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesBodyMassIndex.txt";
				
		public static final String LINKED_OPEN_RULES_AIR_QUALITY_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesAirQualitykHealth.txt";
		public static final String LINKED_OPEN_RULES_HEALTH_CALORIE = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHealthCalorie.txt";
		public static final String LINKED_OPEN_RULES_HEALTH_STEP_COUNT = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHealthStepCount.txt";
		public static final String LINKED_OPEN_RULES_HEALTH_SNORING_LEVEL = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHealthSnoringLevel.txt";
		public static final String LINKED_OPEN_RULES_POLLEN_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesPollenkHealth.txt";
		public static final String LINKED_OPEN_RULES_PEAK_FLOW_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesPeakFlowkHealth.txt";
		public static final String LINKED_OPEN_RULES_HEART_RATE_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesHeartRatekHealth.txt";
		public static final String LINKED_OPEN_RULES_INSIDE_TEMPERATURE_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesInsideTemperature.txt";
		public static final String LINKED_OPEN_RULES_OUTSIDE_HUMIDITY_KHEALTH = "./STEP5_ruleDeduceMeaningfulKnowledge/LinkedOpenRulesOutsideHumidity.txt";
	
		// STEP 6: SPARQL QUERY
		//public static final String SPARQL_QUERY_NATUROPATHY = "./sparql/m3SparqlNaturopathyScenario.sparql";
		//public static final String SPARQL_QUERY_NATUROPATHY_MINIMAL = "./sparql/m3SparqlNaturopathyScenarioMinimal.sparql";
		public static final String GENERIC_IOT_REASONER_SPARQL_QUERY_SOSA_COMPLIANT = "./STEP6_sparqlQuery/m3SparqlGenericSOSACompliant.sparql";
		public static final String SPARQL_QUERY_SOSA_COMPLIANT_OBESITY_RULE_BMI_SCENARIO = "./STEP6_sparqlQuery/tutorial_obesity_reasoner/getPatient_hasBodyMassIndex_viaRule.sparql";
		public static final String SPARQL_QUERY_PATIENT_WOKE_UP_SCENARIOS = "./STEP6_sparqlQuery/patientWokeUpScenario.sparql";
		public static final String SPARQL_QUERY_GET_SYMPTOMS_RISK_FACTORS_THERAPY_PATIENT = "./STEP6_sparqlQuery/getSymptomsRiskFactorsTherapyPatientAge.sparql";

		// NAMESPACE FOR ONTOLOGIES
		public static final String NAMESPACE_M3= "http://sensormeasurement.appspot.com/m3#";
		public static final String NAMESPACE_SOSA= "http://www.w3.org/ns/sosa/";
		public static final String NAMESPACE_KAO= "http://purl.org/kao#";
		public static final String NAMESPACE_SSN_V2= "http://www.w3.org/ns/ssn/";
		public static final String NAMESPACE_QUDT= "http://qudt.org/schema/qudt#";
		public static final String NAMESPACE_KOFLER_WEATHER_ONTOLOGY= "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/WeatherOntology.owl";
		public static final String NAMESPACE_STAROCH_WEATHER_ONTOLOGY= "http://paul.staroch.name/thesis/SmartHomeWeather.owl";
		
		// DOMAIN DATASETS
		public static final String NATUROPATHY_DATASET = "./semanticDomainDataset/naturopathy-dataset.rdf";
		public static final String HEALTH_DATASET = "./semanticDomainDataset/health-dataset.rdf";

}
