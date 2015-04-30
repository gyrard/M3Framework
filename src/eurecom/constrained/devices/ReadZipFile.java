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
package eurecom.constrained.devices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.json.JSONException;

import com.hp.hpl.jena.rdf.model.Model;

import eurecom.common.util.ReadFile;
import eurecom.generic.m2mapplication.M2MAppGeneric;
import eurecom.sparql.result.ExecuteSparqlGeneric;

/**
 * Read the content of the M3 template (a zip file)
 * To recognize ontologies, rules, datasets, SPARQL query
 * @author Amelie gyrard
 *
 */
public class ReadZipFile {
	
	public static String ZIP_FILE_IOT_APPLI = "e:\\M3IoTApplicationTemplate.zip";
	public static ArrayList<String> FILE_TO_LOAD = new ArrayList<String>();
	public static String M3_RULES_PATH ="./WAR/RULES/ruleM3newType.txt";
	public static String SENSOR_DATA_FILE = "./WAR/dataset/sensor_data/raspberry_sensor_data";
	
	public static void main(String[] args) {
		ZipFile zipfile;
		String sensor_data = ReadFile.readContentFile(SENSOR_DATA_FILE);
		ConvertRaspberrySensorData converter = new ConvertRaspberrySensorData();
		Model model;
		try {
			model = converter.convertRaspberrySensorDataToRDF(sensor_data, M3_RULES_PATH);
		
		M2MAppGeneric m2mappli = new M2MAppGeneric(model);
		
		//LOAD ONTOLOGIES AND DATASETS
	//ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, "./WAR/M3IoTApplicationTemplate/transport");
	//	ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, "./WAR/M3IoTApplicationTemplate/weather");
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, "./WAR/M3IoTApplicationTemplate/transport-dataset");
		ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, "./WAR/M3IoTApplicationTemplate/weather-dataset");
		
		//EXECUTE SPARQL QUERY
		
		Model inf = m2mappli.reasonWithJenaRules("./WAR/M3IoTApplicationTemplate/LinkedOpenRules.txt");
		ExecuteSparqlGeneric reqSenml = new ExecuteSparqlGeneric(inf, "./WAR/M3IoTApplicationTemplate/weatherTransportSafetyDevice.sparql");

		ArrayList<ResultSensorDataReasoning> resultAfterReasoning = reqSenml.getSelectResultAnsParseIt(null);

		for (int i = 0; i < resultAfterReasoning.size(); i++) {
			System.out.println(resultAfterReasoning.get(i).toString());
		}
		
		//EXECUTE RULES
		
		//try {
		/*	zipfile = new ZipFile(ZIP_FILE_IOT_APPLI);
			File file = new File("./WAR/M3IoTApplicationTemplate");
			unzip(zipfile, file);*/
			
			for (int i = 0; i < FILE_TO_LOAD.size(); i++) {
				System.out.println(FILE_TO_LOAD.get(i));
				
				//if extension is sparql then execute sparql
				if (FILE_TO_LOAD.get(i).contains(".sparql")){
					
				}
				
				// if extension is .txt then execute rules
				if (FILE_TO_LOAD.get(i).contains(".txt")){
					
				}
				
				//otherwise load ontology and dataset
				else{
					ReadFile.enrichJenaModelOntologyDataset(m2mappli.model, "./WAR/M3IoTApplicationTemplate/transport-dataset");
					// + "\\" +FILE_TO_LOAD.get(i)
					m2mappli.model.write(System.out);
				}
				
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	public static void unzip(final ZipFile zipfile, final File directory)
		    throws IOException {

		    final Enumeration<? extends ZipEntry> entries = zipfile.entries();
		    while (entries.hasMoreElements()) {
		        final ZipEntry entry = entries.nextElement();
		        final File file = file(directory, entry);
		        if (entry.isDirectory()) {
		            continue;
		        }
		        final InputStream input = zipfile.getInputStream(entry);
		        try {
		            // copy bytes from input to file
		        } finally {
		            input.close();
		        }
		    }
		}
	
	protected static File file(final File root, final ZipEntry entry)
		    throws IOException {

		    final File file = new File(root, entry.getName());

		    File parent = file;
		    if (!entry.isDirectory()) {
		        final String name = entry.getName();
		        
		        //get name file from the zip
		        FILE_TO_LOAD.add(name);
		        
		        final int index = name.lastIndexOf('/');
		        if (index != -1) {
		            parent = new File(root, name.substring(0, index));
		        }
		    }
		    if (parent != null && !parent.isDirectory() && !parent.mkdirs()) {
		        throw new IOException(
		            "failed to create a directory: " + parent.getPath());
		    }

		    return file;
		}
}
