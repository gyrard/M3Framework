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

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Object which transforms XML data to RDF data.
 * XML data are from the Eurecom API senml
 * We convert these XML data into RDF data
 */


public class MeasurementPi {

  private String s_name;
  private String s_type;
  private String s_timestamp;
  private String s_value;
  private String s_unit;
  private String s_domainname;

@XmlAttribute(name = "n")
  public String getName() {
    return s_name;
  }

public void setName(String name) {
    this.s_name = name;
  }

@XmlAttribute(name = "type")
  public String getType() {
	return s_type;
}

public void setType(String type) {
	this.s_type = type;
}

  @XmlAttribute(name = "t")
  public String getTime() {
    return s_timestamp;
  }

  public void setTime(String time) {
    this.s_timestamp = time;
  }

  @XmlAttribute(name = "v")
  public String getValue() {
    return s_value;
  }

  public void setValue(String value) {
    this.s_value = value;
  }
  
  @XmlAttribute(name = "u")
  public String getUnits() {
    return s_unit;
  }

  public void setUnits(String units) {
    this.s_unit = units;
  }

} 