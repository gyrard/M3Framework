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
package eurecom.data.converter;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Object which transforms XML data to RDF data.
 * XML data are from the Eurecom API senml
 * We convert these XML data into RDF data
 * @author Amelie Gyrard
 *
 */
public class Measurement {

  private String name;
  private String type;
  private double time;
  private double value;
  private String units;

@XmlAttribute(name = "n")
  public String getName() {
    return name;
  }

public void setName(String name) {
    this.name = name;
  }

@XmlAttribute(name = "type")
  public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

  @XmlAttribute(name = "t")
  public double getTime() {
    return time;
  }

  public void setTime(double time) {
    this.time = time;
  }

  @XmlAttribute(name = "v")
  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
  
  @XmlAttribute(name = "u")
  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

} 