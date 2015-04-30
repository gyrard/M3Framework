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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import com.hp.hpl.jena.rdf.model.Model;
//import sun.misc.IOUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Funtions to read file from a path and enrich the jena model or get the content of the file in a string
 * @author Amelie gyrard
 *
 */
public class ReadFile {


	/**
	 * Read ontologies or RDF dataset, included directly from the file (a path) and add it to the jena model
	 * @param model
	 * @param file
	 */
	public static void enrichJenaModelOntologyDataset(Model model, String file){
		try {
			InputStream in = new FileInputStream(file);
			model.read( in, file );//file:"+
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Read the content of a file, ex. Sparql, or ontologies or datasets and store it in string
	 * @param file
	 * @return String the content of the file
	 */
	public static String readContentFile(String file){
		String text="";
		try{
			InputStream ips=new FileInputStream(file); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				text+=ligne+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * Execute a web service remotely
	 * @param urlWebService: the url of the web service
	 * @return String the result return by the web service
	 */
	public static String queryWebServiceXML(String urlWebService){
		Client client = Client.create();
		client.setConnectTimeout(120000);  //120 Seconds
		client.setReadTimeout(120000);  //120 Seconds
		WebResource webResource = client.resource(urlWebService);
		WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_XML_TYPE);
		String result = builder.get(String.class);	//

		// delete database
		// if com.sun.jersey.api.client.UniformInterfaceException: GET http://emulator-box-services.appspot.com/senml/zones/ahdzfmVtdWxhdG9yLWJveC1zZXJ2aWNlc3IWCxIJWm9uZUFkbWluIgdraXRjaGVuDA returned a response status of 500 Internal Server Error
		//at com.sun.jersey.api.client.WebResource.handle
		//System.out.println("result senml api:" + result);
		return result;
	}

}
