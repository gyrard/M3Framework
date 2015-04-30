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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import eurecom.search.knowledge.ResultDomainKnowledge;

import java.io.File;
import java.util.ArrayList;

public class ParseSearchIoTApplicationV2 {
	static ArrayList<ResultDomainKnowledge> resultIotAppli = new ArrayList<ResultDomainKnowledge>();
	static ResultDomainKnowledge appliTemplate;

	public static void main(String[] args) {
		//resultSearchIoTTemplate.xml

		//PARSE XML COMPATIBLE WITH ANDROID

		try {



			//RESULT WEB SERVICE http://sensormeasurement.appspot.com/m3/searchTemplate/?sensorName=LightSensor&domain=Weather
			File file = new File("./war/dataset/XML_parsing/resultSearchIoTTemplate.xml");

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			if (doc.hasChildNodes()) {

				ArrayList<ResultDomainKnowledge> resultIotAppli = getJavaXMLResult(doc.getChildNodes());


			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static ArrayList<ResultDomainKnowledge> getJavaXMLResult(NodeList nodeList) {


		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				//System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				//System.out.println("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {
						appliTemplate = new ResultDomainKnowledge();

						Node node = nodeMap.item(i);
						if (node.getNodeValue().equals("m2mapplicomment")){
							appliTemplate.setM2mapplicomment(tempNode.getTextContent());
							System.out.println(appliTemplate.getM2mapplicomment());
						}

						if (node.getNodeValue().equals("m2mappli")){
							appliTemplate.setM2mappliUri(tempNode.getTextContent());
							System.out.println(appliTemplate.getM2mappliUri());
						}
						if (node.getNodeValue().equals("m2mapplilabel")){
							appliTemplate.setM2mapplilabel(tempNode.getTextContent());
							System.out.println(appliTemplate.getM2mapplilabel());
						}
						if(!resultIotAppli.contains(appliTemplate)){
							resultIotAppli.add(appliTemplate);
						}

						//System.out.println("attr name : " + node.getNodeName());
						//System.out.println("attr value : " + node.getNodeValue());


					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					getJavaXMLResult(tempNode.getChildNodes());

				}

				//System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		}
		System.out.println(resultIotAppli.size());
		for (int i = 0; i < resultIotAppli.size(); i++) {
			System.out.println("result:" + resultIotAppli.get(i).toString());

		}
		return resultIotAppli;
	}

}
