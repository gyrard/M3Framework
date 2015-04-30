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
package eurecom.test.stuff;

import java.io.InputStream;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.common.util.WSUtils;

public class Main {
	
	public static final String MORIGNOT_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/transport/Perception.owl";
	public static final String RUTA_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/transport/iDriveSafe.txt";
	public static final String GRAUSBERG_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/transport/DrivingContext.owl";
	public static final String ESCOLA_ONTOLOGY_PATH = "./WAR/ont/transport/traffic";
	public static final String RIBONI_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/smartHome/locont-2.0.owl";
	public static final String MAIR_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/transport/adressauswertung.owl";
	public static final String KOFLER_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/weather/WeatherOntologyKofler.owl";
	
	public static final String KONLAKORN_ONTOLOGY_PATH = "./WAR/ont/home/homeActivity";
	public static final String LAFTI_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/smartHome/Task_SH_Ontology.owl";
	public static final String STAR_CITY_ONTOLOGY_PATH = "./WAR/WEB-INF/ontologies/weather/WeatherOntologyStarCity.owl";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = ModelFactory.createDefaultModel();

		ReadFile.enrichJenaModelOntologyDataset(model, STAR_CITY_ONTOLOGY_PATH);
		//WSUtils.readFile(model, KOFLER_ONTOLOGY_PATH);
		//
		Query query = QueryFactory.create("Describe <http://www.ibm.com/SCTC/ontology/WeatherOntology/WeatherOntology.owl#NoPrecipitation>") ;
		
		//Query query = QueryFactory.create("Describe <https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/WeatherOntology.owl#NoPrecipitation>") ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		Model resultModel = qexec.execDescribe() ;
		qexec.close() ;
		resultModel.write(System.out);
		
		//WSUtils.readFile(model, LAFTI_ONTOLOGY_PATH);
		//WSUtils.readFile(model, STAR_CITY_ONTOLOGY_PATH);
		
/*		NodeIterator iter = model.listObjectsOfProperty(OWL.Class, RDF.type);
		Node node = null;
		while (iter.hasNext()) {
			System.out.println("test");
			if( ! iter.next().isLiteral()){
				node = (Node) iter.next();
				System.out.println(node.getURI());
			}
		
		}*/
		
/*		StmtIterator iter = model.listStatements();
		while (iter.hasNext()) {
		Statement stmt = (Statement) iter.next();
		System.out.println(stmt.getSubject()+"� ("+stmt.getPredicate()+")�>"+stmt.getObject().toString());
		}*/
		
		//morignot Generated by the OWL API (version 3.3.1957)
		//WSUtils.readFile(model, MORIGNOT_ONTOLOGY_PATH);//error org.apache.jena.riot.RiotException: [line: 347, col: 36] {E201} Multiple children of property element

		//WSUtils.readFile(model, KONLAKORN_ONTOLOGY_PATH);
		

		//InputStream in = FileManager.get().open(MAIR_ONTOLOGY_PATH);
		//model.read(in, "RDF/XML");// nothing read
		
	
	}
	
	public static void getAllClass(){
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, RUTA_ONTOLOGY_PATH);//works
	}
	
	
	public static void jenaReadOntologies(){
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, Var.M3_ONTOLOGY_PATH);// works with m3
		ReadFile.enrichJenaModelOntologyDataset(model, RUTA_ONTOLOGY_PATH);//works

		ReadFile.enrichJenaModelOntologyDataset(model, GRAUSBERG_ONTOLOGY_PATH);//ok
		ReadFile.enrichJenaModelOntologyDataset(model, ESCOLA_ONTOLOGY_PATH);//ok
		ReadFile.enrichJenaModelOntologyDataset(model, RIBONI_ONTOLOGY_PATH); //ok owl api also
	}


}
