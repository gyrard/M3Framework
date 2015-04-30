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

import java.util.HashMap;

public class MainAgileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("slice!");

		System.out.println(args[0]);

		System.out.println("nb chapeau " + args[0] + ", prix chapeau ht: " +args[1]);

		try{
			int nb_chapeau = Integer.parseInt(args[0]);
			try{
				int prix_chapeau = Integer.parseInt(args[1]);
				int total_ht = nb_chapeau * prix_chapeau;
				System.out.println("total_ht:" + total_ht);

				int i=0;
				int[][] tab_discount_rate = {{1000,5000,7000,10000,50000}, {3,5,7, 10, 15}};
				for(i=0; i < tab_discount_rate[0].length; i++ ){
					if(total_ht> tab_discount_rate[0][i]){
						System.out.println(total_ht> tab_discount_rate[0][i]);
					}
					else{
						System.out.println(total_ht> tab_discount_rate[0][i]);
						break;
					}
				}
				int drate = tab_discount_rate[1][i-1];
				System.out.println("Discount rate:" + drate);
				double discount = drate * total_ht / 100.0;
				System.out.println("Discount:" + discount);
				double ht = total_ht - discount;
				System.out.println("Price:" + ht);
				
				HashMap state_tax_rate_all = new HashMap();
				state_tax_rate_all.put("UT", 6.85);
				state_tax_rate_all.put("NV", 8.0);
				state_tax_rate_all.put("TX", 6.25);
				state_tax_rate_all.put("AL", 4.0);
				state_tax_rate_all.put("CA", 8.25);
				
				
				String state = args[2];
				
				double state_tax_rate = 6.25;
				double ttc = ht * state_tax_rate /  100.0 + ht;
				System.out.println("ttc: " + ttc);
						
				
				//
				
			}
			catch(NumberFormatException e){
				System.err.println("prix chapeau invalide rentrer un nombre");
			}
		}
		catch(NumberFormatException e){
			System.err.println("nb chapeau invalide rentrer un nombre");
		}








	}

}
