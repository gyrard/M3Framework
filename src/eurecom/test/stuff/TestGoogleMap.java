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

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import eurecom.generic.m2mapplication.Coordinates;
import eurecom.generic.m2mapplication.Place;


public class TestGoogleMap {
    
    public static List<Coordinates> getCoordinates(String address) {
        
        List<Coordinates> lst = new ArrayList();
        
        // on remplace les blanc par un plus
        address = address.replaceAll("\\s","+" );
        
        Geocoder geocoder = new Geocoder();
        GeocoderRequest request = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
        GeocodeResponse response = geocoder.geocode(request);
        
        for(GeocoderResult r : response.getResults()) {
            r.getGeometry().getLocation();
            LatLng location = r.getGeometry().getLocation();
            lst.add(new Coordinates(location.getLat().doubleValue(), location.getLng().doubleValue()));
        }
        
        return lst;  
    }
    
    public static List<Place> getPlace(double lat, double lon) {
        
        List<Place> lst = new ArrayList();
        
        LatLng latlng = new LatLng(new BigDecimal(lat), new BigDecimal(lon));
        
        Geocoder geocoder = new Geocoder();
        GeocoderRequest request = new GeocoderRequestBuilder().setLocation(latlng).getGeocoderRequest();
        GeocodeResponse response = geocoder.geocode(request);
        
        for(GeocoderResult r : response.getResults()) {
            List<GeocoderAddressComponent> listComp = r.getAddressComponents();
            String countryN = "", countryCode = "", name = "", adminC = "";
            for(GeocoderAddressComponent comp : listComp) {
                
                if(comp.getTypes().contains("country")) {
                    countryN = comp.getLongName();
                    countryCode = comp.getShortName();
                }else if(comp.getTypes().contains("administrative_area_level_1")) {
                    adminC = comp.getLongName();
                }
                else if(comp.getTypes().contains("locality")) {
                    name = comp.getLongName();
                }
            }
            r.getGeometry().getLocation();
            LatLng location = r.getGeometry().getLocation();
            lst.add(new Place(name, location.getLat().doubleValue(),
                    location.getLng().doubleValue(), countryCode,
                    countryN, adminC));
        }
        
        return lst;
    }
}