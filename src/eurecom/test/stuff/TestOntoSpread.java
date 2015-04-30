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
/*package eurecom.test.ontospread;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightRDFImpl;
import org.ontospread.dao.JenaOntologyDAOImpl;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.JenaRDFModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.ExternalizeFilesResourceLoader;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.player.SpreadPlayer;
import org.ontospread.player.SpreadSimplePlayer;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.post.OntoSpreadPostAdjustmentImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfig;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfigImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.process.run.OntoSpreadDegradationFunction;
import org.ontospread.process.run.OntoSpreadDegradationFunctionIterationsImpl;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.process.run.OntoSpreadRunImpl;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinActivationValue;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.strategy.OntoSpreadSelectConceptStrategy;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.OntoSpreadStrategy;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.utils.ToStringHelper;

import eurecom.common.util.Var;


public class TestRunner {
	static Logger logger = Logger.getLogger(TestRunner.class);
	public static void main(String []args) throws ConceptNotFoundException{		
		logger.debug("Init ontospread");
		String []conceptUris = new String[]{
				"http://www.co-ode.org/ontologies/galen#AdvancedBreastCancer",
				"http://www.co-ode.org/ontologies/galen#NAMEDSymptom"};

		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(createScoredConcepts(conceptUris,1.0));
		SpreadPlayer player = new SpreadSimplePlayer(
						createDefaultGalenOntoSpreadProcess(
						20, 
						50, 
						1.0,
						10.0,
						createOntologyGalenDAO(),
						createDegradationFunction()),
						ontoSpreadState);
		while(player.hasNext()){
			ontoSpreadState = player.next();
		}

		logger.info("Activated nodes: "+ontoSpreadState.getConcepts().size());
		logger.info("Spreaded nodes: "+ontoSpreadState.getSpreadedConcepts().size());
		logger.info("Time: "+(ontoSpreadState.getSpreadTime())+" mseg.");
		logger.info(ToStringHelper.arrayToString(
				ontoSpreadState.getFinalSpreadedConcepts(),"\n"));
	}
	

	
	public static ScoredConceptTO[] createScoredConcepts(String [] uris, double initialScore){
		List <ScoredConceptTO> scoredConcepts = new LinkedList<ScoredConceptTO>();
		for(int i = 0; i<uris.length;i++){
			scoredConcepts.add(new ScoredConceptTO(uris[i],initialScore));
		}
		return scoredConcepts.toArray(new ScoredConceptTO[uris.length]);
	}
	

	public static OntoSpreadProcess createDefaultGalenOntoSpreadProcess(
			int min, int max,  double minActivation, double initialPredAdjustment, OntologyDAO dao, 
			OntoSpreadDegradationFunction degradationFunction){
		return new OntoSpreadProcess(
				dao,
				createDefaultPreAdjustment(initialPredAdjustment), 
				createDefaultRunWithDegradation(min, max, minActivation,degradationFunction), 
				createDefaultPost()); 
	}
	

	public static OntoSpreadPreAdjustment createDefaultPreAdjustment(double initialPredAdjustment){
		OntoSpreadPreAdjustment pre = new OntoSpreadPreAdjustmentImpl();
		OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig = new OntoSpreadPreAdjustmentConfigImpl(initialPredAdjustment);
		pre.setOntoPreAdjustmentConfig(ontoSpreadPreConfig );
		return pre;
	}
	
	public static OntoSpreadRun createDefaultRunWithDegradation(int min, int max,  
			double minActivation, 
			OntoSpreadDegradationFunction degradationFunction) {	
		return new OntoSpreadRunImpl(
			createOntologyGalenDAO(), 
			createStopStrategy(min,max, minActivation), 
			createSelectStrategy(minActivation),
			createRelationWeight(),
			degradationFunction);		
	}
	
	public static OntoSpreadPostAdjustment createDefaultPost() {
		return new OntoSpreadPostAdjustmentImpl(Boolean.TRUE);
	}

	
	public static OntologyDAO createOntologyGalenDAO() {
		String[] filenames = new String[]{Var.ROOT_OWL + "full-galen.owl"};		
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	
	public static OntoSpreadModelWrapper createOntoModelWrapper(String []filenames){
		//ResourceLoader resource = new FilesResourceLoader(filenames);
		ExternalizeFilesResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return model;
	}
	
	public static OntoSpreadStrategyVisitorPair createStopStrategy(int min, int max, double minActivation){
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(min));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(max));	
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		OntoSpreadStrategy stopStrategy = new OntoSpreadSimpleStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(stopStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}
	
	public static OntoSpreadStrategyVisitorPair createSelectStrategy(double minActivation){
		OntoSpreadCompositeRestriction restrictionsSelect = new OntoSpreadCompositeRestriction();
		restrictionsSelect.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		OntoSpreadStrategy selectStrategy = new OntoSpreadSelectConceptStrategy(restrictionsSelect);		
		return new OntoSpreadStrategyVisitorPair(selectStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}
	
	private static OntoSpreadRelationWeight createRelationWeight() {		
		return new OntoSpreadRelationWeightRDFImpl(createModelWrapper());
	}
	
	public static OntoSpreadModelWrapper createModelWrapper() {
		String[] filenames = new String[]{ Var.ROOT_DATASET + "relation-weights-galen.rdf"};
		ExternalizeFilesResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		//ResourceLoader resource  new FilesResourceLoader(filenames);
		return new JenaRDFModelWrapper(resource);	
		
	}
	
	public static OntoSpreadDegradationFunction createDegradationFunction() {		
		//	return new OntoSpreadDegradationFunctionImpl(); //h1
		return new OntoSpreadDegradationFunctionIterationsImpl();
	}		
	
}
*/