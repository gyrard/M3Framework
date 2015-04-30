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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eurecom.test.stuff;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import eurecom.generic.m2mapplication.Coordinates;
import eurecom.generic.m2mapplication.Place;


public class TestGeonamesGoogleMapLOV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Client de l'api Lov
        InputStream is = LOVClient.executeSparql(
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\r\n"
                + "PREFIX dcterms:<http://purl.org/dc/terms/>\r\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\r\n"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>\r\n"
                + "PREFIX skos:<http://www.w3.org/2004/02/skos/core#>\r\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\r\n"
                + "PREFIX void:<http://rdfs.org/ns/void#>\r\n"
                + "PREFIX bibo:<http://purl.org/ontology/bibo/>\r\n"
                + "PREFIX vann:<http://purl.org/vocab/vann/>\r\n"
                + "PREFIX voaf:<http://purl.org/vocommons/voaf#>\r\n"
                + "PREFIX frbr:<http://purl.org/vocab/frbr/core#>\r\n"
                + "PREFIX lov:<http://lov.okfn.org/dataset/lov/lov#>\r\n\r\n"
                + "SELECT ?vocabPrefix ?vocabURI \r\n"
                + "WHERE{\r\n"
                + "\t?vocabURI a voaf:Vocabulary.\r\n"
                + "\t?vocabURI vann:preferredNamespacePrefix ?vocabPrefix.\r\n"
                + "} \r\n"
                + "ORDER BY ?vocabPrefix \r\n");

        
        System.out.println("API LOV ------------------------");
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
          //  StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            rd.close();
            is.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // Client de l'api google map : 
        /* l'avantage c'est qu'elle fonctionne avec les adresses, pas seulement
         * les noms de villes*/
        List<Coordinates> res = TestGoogleMap.getCoordinates("8085 hyannisport dr cupertino");
        System.out.println("API GOOGLE MAP.getCoordinates(8085 hyannisport dr cupertino) ------------------------");
        for (Coordinates c : res) {
            System.out.println(c.lat + " : " + c.lon);
        }
        List<Place> res4 = TestGoogleMap.getPlace(37.323, -122.03218);
        System.out.println("API GOOGLE MAP.getPlace(37.323, -122.03218) ------------------------");
        for (Place p : res4) {
            System.out.println(p);
        }
        
        // Client de l'api geonames
        List<Coordinates> res3 = TestGeonames.getCoordinates("cupertino");
        System.out.println("API GEONAMES.getCoordinates(\"cupertino\") ------------------------");
        for (Coordinates c : res3) {
            System.out.println(c);
        }
        List<Place> res2 = TestGeonames.getPlace(37.323, -122.03218);
        System.out.println("API GEONAMES.getPlace(37.323, -122.03218) ------------------------");
        for (Place p : res2) {
            System.out.println(p);
        }
    }
}
