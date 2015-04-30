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
/*package eurecom.constrained.devices;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.search.knowledge.ResultDomainKnowledge;
import eurecom.sparql.result.ExecuteSparqlGeneric;
import eurecom.sparql.result.VariableSparql;

public class GenerateDomainKnowledgeV3 {

	static String M3_PATH = "e:/workspace/m3/";
	static String PATH_ONTOLOGY = "e:\\workspace/m3/WAR/WEB-INF/ontologies/";
	static String PATH_DATASET = "e:\\workspace/m3/WAR/dataset/";
	static String PATH_RULE = "e:\\workspace/m3/WAR/RULES/";
	static String PATH_SPARQL = "e:\\workspace/m3/WAR/SPARQL/";
	static String NS_M3 = "http://sensormeasurement.appspot.com/m3#";

	static String M3_SWOT_DATASET = M3_PATH + "WAR/dataset/iot-application-template-dataset";
	static String M3_ONTOLOGY = M3_PATH + "WAR/WEB-INF/ontologies/m3";

	static String SENSOR_PARAM = ""; //Var.NS_M3 + "LightSensor"
	static String DOMAIN_PARAM = ""; //Var.NS_M3 + "Weather"
	public static String SPARQL_QUERY_SWOT_TEMPLATE = M3_PATH + "WAR/SPARQL/swot_template_m3.sparql";
	public static String SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE = M3_PATH + "WAR/SPARQL/searchIoTApplicationTemplate.sparql";

	static String NAME_ZIP_FILE = "e:\\M3IoTApplicationTemplate.zip";

	static String M2M_APPLI_URI_CHOOSE = "" ;//NS_M3 + "WeatherTransportationSafetyDevice"
	static int APPPLI_NUMBER ;

	public static void main(String[] args) {

		// console for the real application
		//askSensorDomainUser();

		//enter sensor and domain as arguments (faster for testing)
		SENSOR_PARAM = Var.NS_M3 + args[0];
		DOMAIN_PARAM = Var.NS_M3 + args[1];

		System.out.println("M3 sensor and domain are: "+ SENSOR_PARAM + " " + DOMAIN_PARAM);

		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, M3_ONTOLOGY);
		ReadFile.enrichJenaModelOntologyDataset(model, M3_SWOT_DATASET);

		ArrayList<ResultDomainKnowledge> result = searchIotApplicationTemplate(model);

		// console for the real application
		//askApplicationTemplateUser(result);

		//enter appli number as arguments (faster for testing)
		APPPLI_NUMBER = Integer.parseInt(args[2]);
		M2M_APPLI_URI_CHOOSE = result.get(APPPLI_NUMBER).getM2mappliUri();

		generateIoTApplicationTemplate(model);
	}

	public static ArrayList<ResultDomainKnowledge> searchIotApplicationTemplate(Model model){
		//search IoT application template according to a sensor and a domain
		ExecuteSparqlGeneric req = new ExecuteSparqlGeneric(model, SPARQL_QUERY_SEARCH_IOT_APPLICATION_TEMPLATE);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("m2mdevice", SENSOR_PARAM, false));
		var.add(new VariableSparql("domain", DOMAIN_PARAM, false));
		ArrayList<ResultDomainKnowledge> result = req.searchIoTApplicationTemplate(var);
		for (int i = 0; i < result.size(); i++) {
			System.out.println("IoT Application Template: "+ i + ") " + result.get(i).getM2mapplicomment());
		}
		return result;
	}

	*//**
	 * Ask in the console to the user the application template number
	 * @param 
	 * @param 
	 *//*
	public static void askApplicationTemplateUser(ArrayList<ResultDomainKnowledge> result){
		//ask for the sensor used
		System.out.println("Please enter the number of the IoT application that you are interested in? E.g., 2)");

		try{
			Scanner scanInput = new Scanner(System.in);
			String appli = scanInput.nextLine();
			APPPLI_NUMBER = Integer.parseInt(appli);
			M2M_APPLI_URI_CHOOSE = result.get(APPPLI_NUMBER).getM2mappliUri();

			System.out.println("Appli for the user: " + M2M_APPLI_URI_CHOOSE);
			scanInput.close();  
		}

		catch (NoSuchElementException e){
			e.printStackTrace();
		}


	}

	*//**
	 * Ask in the console to the user the sensor used in which domain
	 * @param 
	 * @param 
	 *//*
	public static void askSensorDomainUser(){
		//ask for the sensor used
		System.out.println("Please enter sensor deployed? E.g. LightSensor (Please check M3 nomenclature http://sensormeasurement.appspot.com/publication/NomenclatureSensorData.pdf)");
		Scanner scanInput = new Scanner(System.in);
		SENSOR_PARAM = scanInput.nextLine();
		SENSOR_PARAM = Var.NS_M3 + SENSOR_PARAM;

		//ask for the domain
		System.out.println("Please enter domain? E.g. Weather (Please check M3 nomenclature http://sensormeasurement.appspot.com/publication/NomenclatureSensorData.pdf)");
		DOMAIN_PARAM = scanInput.nextLine();
		DOMAIN_PARAM = Var.NS_M3 + DOMAIN_PARAM;

		scanInput.close();  
	}

	public static void generateIoTApplicationTemplate(Model model){
		//generate deomain knowledge in a zip file according the the iot application template
		ExecuteSparqlGeneric req = new ExecuteSparqlGeneric(model, SPARQL_QUERY_SWOT_TEMPLATE);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("m2mappli", M2M_APPLI_URI_CHOOSE, false)); //	TO DO: the user chooses an application
		ArrayList<ResultDomainKnowledge> result = req.searchIoTApplicationTemplate(var);

		String filePath = "", fileName = "";
		ArrayList<String> fileToLoad = new ArrayList<String>();
		int index;

		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
			if (result.get(i).getOnto()!=null){//get url ontology
				//get name ONTOLOGY to search the exact path
				filePath = result.get(i).getOnto();
				index = filePath.lastIndexOf("/");
				fileName = filePath.substring(index +1, filePath.length()-1);// length - 1 to delete #
				filePath= PATH_ONTOLOGY + fileName;//"e:\\workspace/m3/WAR/WEB-INF/ontologies/m3"
				//	System.out.println("Onto to load: " + filePath);
				if (! fileToLoad.contains(filePath)){
					fileToLoad.add(fileName);
					fileToLoad.add(filePath);
				}


				//DATASET
				filePath = result.get(i).getDataset().substring(0, result.get(i).getDataset().length()-1);
				index = filePath.lastIndexOf("/");
				fileName = filePath.substring(index +1, filePath.length());// length - 1 to delete #
				filePath= PATH_DATASET + fileName ;//"e:\\workspace/m3/WAR/WEB-INF/ontologies/m3"
				if (! fileToLoad.contains(filePath)){
					fileToLoad.add(fileName);
					fileToLoad.add(filePath);
				}

				//LOR FILE
				filePath = result.get(i).getRule();
				index = filePath.lastIndexOf("/");
				fileName = filePath.substring(index +1, filePath.length());// length - 1 to delete #
				filePath= PATH_RULE + fileName;//"e:\\workspace/m3/WAR/WEB-INF/ontologies/m3"
				System.out.println("fileName: " + fileName);
				if (! fileToLoad.contains(filePath)){
					fileToLoad.add(fileName);
					fileToLoad.add(filePath);
				}

				//SPARQL FILE
				filePath = result.get(i).getSparql();



				index = filePath.lastIndexOf("/");
				fileName = filePath.substring(index +1, filePath.length());// length - 1 to delete #
				filePath= PATH_SPARQL + fileName ;//"e:\\workspace/m3/WAR/WEB-INF/ontologies/m3"
				System.out.println("fileName: " + fileName);

				//replace variable in sparql for the specific IoT application
				String new_sparql_file_path;
				try {
					//new sparql query in a new file to put in the zip
					new_sparql_file_path = replaceVariableInSparqlRequest(filePath, fileName);

					if (! fileToLoad.contains(new_sparql_file_path)){
						fileToLoad.add(fileName);
						fileToLoad.add(new_sparql_file_path);
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}

		}
		createZipFile(NAME_ZIP_FILE, fileToLoad);
		System.out.println("Zip file generated here: " + NAME_ZIP_FILE);
	}

	public static String replaceVariableInSparqlRequest(String filePath, String fileName) throws FileNotFoundException, UnsupportedEncodingException{
		//get string sparql
		//replace 2 variables

		String sparql_query = ReadFile.readContentFile(filePath);
		sparql_query = sparql_query.replace("?inferTypeUri", "<" + Var.NS_M3 + "WeatherLuminosity" + ">");
		sparql_query = sparql_query.replace("?typeRecommendedUri", "<" + Var.NS_TRANSPORT_ONTOLOGY+ "SafetyDevice" + ">");


		System.out.println("sparql_query" + sparql_query);

		File file = new File(fileName);
		FileWriter fw;
		try {

			//store the new sparql query in a file
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sparql_query);
			bw.close();
			System.out.println("file.getAbsoluteFile().toString(): " + file.getAbsoluteFile().toString());
			return file.getAbsoluteFile().toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";


	}

	*//**
	 * Generate a ZIP file
	 * @param zipeFileName: name of the zip file
	 * @param fileToLoad: list of (name file, file path)
	 *//*
	public static void createZipFile(String zipeFileName, ArrayList<String> fileToLoad){
		byte[] buffer = new byte[1024];

		try{				

			FileOutputStream fos = new FileOutputStream(zipeFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (int i = 0; i < fileToLoad.size()-1; i+=2) {
				ZipEntry ze= new ZipEntry(fileToLoad.get(i));//name file
				zos.putNextEntry(ze);
				//file path
				System.out.println("file: " + fileToLoad.get(i+1));
				FileInputStream in = new FileInputStream(fileToLoad.get(i+1));//"e:\\workspace/m3/WAR/WEB-INF/ontologies/m3"

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}
			zos.closeEntry();
			//remember close it
			zos.close();

			

		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}


*/