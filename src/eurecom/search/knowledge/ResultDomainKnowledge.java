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
package eurecom.search.knowledge;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
/**
 * Object to represent the sensor measurements after the reasoning
 * @author Amelie Gyrard
 *
 */
@XmlRootElement(name = "domainKnowledge")
public class ResultDomainKnowledge {
	
	String onto;
	String dataset;
	String rule;
	String sparql;
	String inferTypeUri;
	String typeRecommendedUri;
	String m2mappliUri;
	String m2mapplilabel;
	String m2mapplicomment;
	
	@XmlElement
	public String getM2mappliUri() {
		return m2mappliUri;
	}

	public void setM2mappliUri(String m2mappliUri) {
		this.m2mappliUri = m2mappliUri;
	}
	
	@XmlElement
	public String getM2mapplilabel() {
		return m2mapplilabel;
	}

	public void setM2mapplilabel(String m2mapplilabel) {
		this.m2mapplilabel = m2mapplilabel;
	}

	@XmlElement
	public String getM2mapplicomment() {
		return m2mapplicomment;
	}

	public void setM2mapplicomment(String m2mapplicomment) {
		this.m2mapplicomment = m2mapplicomment;
	}

	@XmlElement
	public String getInferTypeUri() {
		return inferTypeUri;
	}

	public void setInferTypeUri(String inferTypeUri) {
		this.inferTypeUri = inferTypeUri;
	}

	@XmlElement
	public String getTypeRecommendedUri() {
		return typeRecommendedUri;
	}

	public void setTypeRecommendedUri(String typeRecommendedUri) {
		this.typeRecommendedUri = typeRecommendedUri;
	}

	
	
	
	@Override
	public String toString(){
		String result = "m2mapplilabel=" + m2mapplilabel + ", ";
		result += "m2mapplicomment=" + m2mapplicomment + ", ";
		result += "Onto=" + onto + ", ";
		result += "Dataset=" + dataset + ", ";
		result += "Rule=" + rule + ", ";
		result += "Sparql=" + sparql + ", ";
		result += "InferTypeUri=" + inferTypeUri + ", ";
		result += "TypeRecommendedUri=" + typeRecommendedUri;
		return result;
	}

	@XmlElement
	public String getOnto() {
		return onto;
	}

	public void setOnto(String onto) {
		this.onto = onto;
	}

	@XmlElement
	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	@XmlElement
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@XmlElement
	public String getSparql() {
		return sparql;
	}

	public void setSparql(String sparql) {
		this.sparql = sparql;
	}
	

	
	

	
}