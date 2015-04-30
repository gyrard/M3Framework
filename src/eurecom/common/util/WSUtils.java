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

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

//import sun.misc.IOUtils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import eurecom.data.converter.Measurements;

/**
 * Functions to read a file, query a web service, etc.
 * @author Amelie gyrard
 *
 */
public class WSUtils {


	/**
	 * Convert a xml string into a json string
	 * @param xml the string to convert into json
	 * @return the json string
	 * @throws JSONException
	 */
	public static String convertXmlToJson(String xml) throws JSONException  {
		JSONObject json;
		String res = "";
		json = XML.toJSONObject(xml);
		res = json.toString();
		return res;
	}
	
	public static String convertJsonToXml(String jsonData) throws JSONException  {
         XMLSerializer serializer = new XMLSerializer(); 
         JSON json = JSONSerializer.toJSON( jsonData ); 
         String xml = serializer.write( json );  
         //System.out.println(xml); 
         return xml;
	}

	

	public static String queryWebServiceJSON(String urlWebService){
		Client client = Client.create();
		client.setConnectTimeout(120000);  //120 Seconds
		client.setReadTimeout(120000);  //120 Seconds
		WebResource webResource = client.resource(urlWebService);
		WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON_TYPE);
		String result = builder.get(String.class);	//

		//System.out.println("result senml api:" + result);
		return result;
	}
	
	public static String queryWebServicSPARQLGenerated(String urlWebService){
		Client client = Client.create();
		client.setConnectTimeout(120000);  //120 Seconds
		client.setReadTimeout(120000);  //120 Seconds
		WebResource webResource = client.resource(urlWebService);
		WebResource.Builder builder = webResource.accept(MediaType.TEXT_PLAIN);
		String result = builder.get(String.class);	//

		//System.out.println("result senml api:" + result);
		return result;
	}
	
	

	/**
	 * Read an XML file and transforms XML data into RDF data
	 * @param file
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static Measurements readXMLFile(String file) throws JAXBException, FileNotFoundException{
		JAXBContext context = JAXBContext.newInstance(Measurements.class);
		Unmarshaller um = context.createUnmarshaller();
		Measurements measurement = (Measurements) um.unmarshal(new FileReader(file));
		return measurement;
	}

}
