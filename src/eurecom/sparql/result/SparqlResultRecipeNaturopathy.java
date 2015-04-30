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
package eurecom.sparql.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Recipe")
public class SparqlResultRecipeNaturopathy {

	private String uri;
	private String name;
	private String baseName;
	private String time;
	private double value;
	private String units;
	private String season;
	private String seasonUri;
	private String type;
	private String food;
	private String foodUri;
	private String diseaseLabel;
	private String diseaseUri;
	private String recipeUri;
	private String recipeLabel;
	private String ingtUri;
	private String ingtQuantity;

	@XmlElement
	public String getDiseaseUri() {
		return diseaseUri;
	}

	public void setDiseaseUri(String diseaseUri) {
		this.diseaseUri = diseaseUri;
	}

	@XmlElement
	public String getDiseaseLabel() {
		return diseaseLabel;
	}

	public void setDiseaseLabel(String diseaseLabel) {
		this.diseaseLabel = diseaseLabel;
	}

	@XmlElement
	public String getRecipeUri() {
		return recipeUri;
	}

	public void setRecipeUri(String recipeUri) {
		this.recipeUri = recipeUri;
	}

	@XmlElement
	public String getRecipeLabel() {
		return recipeLabel;
	}

	public void setRecipeLabel(String recipeLabel) {
		this.recipeLabel = recipeLabel;
	}

	@XmlElement
	public String getIngtQuantity() {
		return ingtQuantity;
	}

	public void setIngtQuantity(String ingtQuantity) {
		this.ingtQuantity = ingtQuantity;
	}
	@XmlElement
	public String getSeasonUri() {
		return seasonUri;
	}

	public void setSeasonUri(String seasonUri) {
		this.seasonUri = seasonUri;
	}
	
	@XmlElement
	public String getFoodUri() {
		return foodUri;
	}

	public void setFoodUri(String foodUri) {
		this.foodUri = foodUri;
	}
	
	@XmlElement
	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	@XmlElement
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
	
	@XmlElement
	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	@XmlElement(name = "uri")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@XmlElement(name = "value")
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@XmlElement(name = "unit")
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

} 