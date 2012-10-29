/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */package org.universAAL.EnergyReader;

 

/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_6#Publishing_context_events */
import java.util.List;
import java.util.TimerTask;

import javax.swing.Timer;

import org.osgi.framework.BundleContext;
import org.universAAL.EnergyReader.database.EnergyReaderDBInterface;
import org.universAAL.EnergyReader.model.ChallengeModel;
import org.universAAL.EnergyReader.model.EnergyScoreModel;
import org.universAAL.EnergyReader.model.MeasurementModel;
import org.universAAL.EnergyReader.model.ReadEnergyModel;
import org.universAAL.EnergyReader.utils.PowerReader;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.aalfficiency.scores.*;
import org.universAAL.ontology.energy.reader.EnergyMeasurement;
import org.universAAL.ontology.energy.reader.ReadEnergy;
import org.universAAL.ontology.energy.reader.ReadEnergyDevice;

public class DailyPublisher extends TimerTask{ 
	
	private ContextPublisher cp;
	ContextProvider info = new ContextProvider();
	ModuleContext mc;
	public final static String NAMESPACE = "http://tsbtecnologias.es/ReadEnergy#";
	private PowerReader reader; 
	private ReadEnergyModel[] consumptions;
	   
    public DailyPublisher(BundleContext context) {
    	System.out.print("New Publisher\n");
		info = new ContextProvider("http://tsbtecnologias.es#MyNewContext");
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		info.setType(ContextProviderType.controller);
		info
				.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
		cp = new DefaultContextPublisher(mc, info);
	}
    
    public void publishMeassurement(ReadEnergyModel consumption) {
    	
    	System.out.print("Publishing meassurements the context bus\n");
    	
    	ReadEnergyDevice device = new ReadEnergyDevice(NAMESPACE+consumption.getDevice().getName());
    	device.setName(consumption.getDevice().getName());
    	device.setDeviceType("Plug");
    	device.setPlace("Office");
    	
    	EnergyMeasurement con = new EnergyMeasurement(NAMESPACE+"Measurement"+consumption.getMeasure().getMeasurement());
    	con.setUnit("w");
    	con.setValue(consumption.getMeasure().getMeasurement());
    	
    	ReadEnergy energy = new ReadEnergy(NAMESPACE+"Energy"+consumption.getDevice().getName());
    	energy.setMeasurement(con);
    	energy.setDevice(device);
    	energy.setDaily("true");
    	
    	cp.publish(new ContextEvent(energy, ReadEnergy.PROP_HAS_MEASUREMENT));
    	
	}
    
    public void publishEnergyScore(EnergyScoreModel energy) {
    	
    	
    	
    	Challenge c = new Challenge(NAMESPACE+"ElectricityDataChallenge");
    	c.setDescription(energy.getChallenge().getDescription());
    	c.setGoal(energy.getChallenge().getGoal());
    	
    	ElectricityScore e = new ElectricityScore(NAMESPACE+"ElectricityData");
    	e.setTodayElectricityScore(energy.getTodayScore());
    	e.setSaving(energy.getPercentage());
    	e.setChallenge(c);
    	
    	AalfficiencyScores sc = new AalfficiencyScores(NAMESPACE+"Scores");
    	sc.setElectricityScore(e);
    	
    	System.out.print("Publishing scores the context bus\n");
    	System.out.print("--------------------------------\n");
    	cp.publish(new ContextEvent(sc, AalfficiencyScores.PROP_ELECTRICITY_SCORE));
    	
	}
    
    

	@Override
	public void run() {
		reader = new PowerReader();
		consumptions = reader.readEnergyConsumption();
		EnergyReaderDBInterface db = new EnergyReaderDBInterface();
		
		try {
			
			for (ReadEnergyModel c : consumptions){
				int m = db.totalMeasurements(c.getDevice().getName());
				System.out.print("TOTAL: "+m+"\n");
				db.insertHistoricMeasurement(c.getDevice().getName(), m);
				c.getMeasure().setMeasurement(m);
			}
			db.deleteMeasurements();
			for (int i=0;i<consumptions.length;i++){
				publishMeassurement(consumptions[i]);
			}
			ChallengeModel c = db.getActiveChallenges();
			int total = db.getChallengeConsumption(c.getId());
			System.out.print("CHALLENGE "+ c.getDescription()+". CONSUMPTION "+total+"\n");
			EnergyScoreModel score = getElectricityChallengeScore(c,total);
			System.out.print("SCORE "+ score +"\n");
			publishEnergyScore(score);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public EnergyScoreModel getElectricityChallengeScore(ChallengeModel challenge, int consumption){
		
		EnergyScoreModel energyScore = new EnergyScoreModel();
		int score =0;
		int average = challenge.getOriginalMeasurement();
		int currentScore = challenge.getCurrentScore();
		int totalScore = challenge.getTotalScore();
		
		String goal = challenge.getGoal();
		String[] splitGoal = goal.split(" ");
		int g = Integer.parseInt(splitGoal[0]);
		
		EnergyReaderDBInterface db = new EnergyReaderDBInterface();
		
		energyScore.setChallenge(challenge);
		//To calculate the percentage of saving multiply the current 
		//consumption by 100 and divide by the previous consumption
		int percentage = (consumption*100)/average;
		
		energyScore.setPercentage(percentage);
		
		//Once we know the percentage of saving, we can calculate the 
		//percentage of the total score corresponding to that saving
		if (percentage < 100){
			score = ((percentage*totalScore)/g)-currentScore;
		}
		
		energyScore.setTodayScore(score);
		
		//Save the new currentScore (previous+new) in the db
		challenge.setCurrentScore(score+currentScore);
		try {
			db.modifyCurrentScore(challenge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return energyScore;
		
	}
	
	
}