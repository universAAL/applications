package org.universAAL.FitbitPublisher;

import java.util.ArrayList;

import java.util.List;
import java.util.Date;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.osgi.framework.BundleContext;
import org.universAAL.FitbitPublisher.FitbitAPI.FitbitException;
import org.universAAL.FitbitPublisher.FitbitAPI.FitbitService;
import org.universAAL.FitbitPublisher.database.FitbitDBInterface;
import org.universAAL.FitbitPublisher.model.*;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.aalfficiency.scores.AalfficiencyScores;
import org.universAAL.ontology.aalfficiency.scores.ActivityScore;
import org.universAAL.ontology.aalfficiency.scores.Challenge;
import org.universAAL.ontology.fitbitdata.Activity;
import org.universAAL.ontology.fitbitdata.FitbitData;
import org.universAAL.ontology.fitbitdata.Sleep;


public class FitbitPublisher extends TimerTask {

	private FitbitService fitbit;
	
	private ContextPublisher cp;
	ContextProvider info = new ContextProvider();
	ModuleContext mc;
	public final static String NAMESPACE = "http://tsbtecnologias.es/FitbitData#";	
	
	public FitbitPublisher(BundleContext context) {
    	System.out.print("New Publisher\n");
		info = new ContextProvider("http://tsbtecnologias.es#MyNewContext");
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		info.setType(ContextProviderType.controller);
		info
				.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
		cp = new DefaultContextPublisher(mc, info);
	}
	
    public void publishFitbitData(){
    	
    	FitbitData fitbitData = new FitbitData();
    	
    	try {
    		fitbit = new FitbitService();
			String activities=fitbit.getActivities();
			String sleep=fitbit.getSleep();
			
			System.out.print(activities);
			System.out.print("\n ******************************\n");
			System.out.print(sleep);
			
			ActivityModel a = formatActivities(activities);
		
						
			Activity ac = new Activity();
			
			ac.setCalories(String.valueOf(a.caloriesOut));
			ac.setSteps(String.valueOf(a.steps));
		
			SleepModel sleeptime = formatSleep(sleep);
			Sleep sp = new Sleep();
			
			sp.setIsMainSleep(String.valueOf(sleeptime.isMainSleep));
			sp.setMinutesAsleep(String.valueOf(sleeptime.minutesAsleep));
			sp.setTimeInBed(String.valueOf(sleeptime.timeInBed));
			
			fitbitData.setSleep(sp);
			fitbitData.setActivity(ac);
				
			System.out.print("\n---------------\n");
			System.out.print("Publishing Fitbit Data\n");
			cp.publish(new ContextEvent(fitbitData, FitbitData.PROP_SLEEP));
			
			
			AalfficiencyScores as = setActivityScore(ac);
			System.out.print("\n---------------\n");
			System.out.print("Publishing Scores\n");
			cp.publish(new ContextEvent(as, AalfficiencyScores.PROP_ACTIVITY_SCORE));
			
		} catch (FitbitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public ActivityModel formatActivities(String activities){
    	
    	Object obj=JSONValue.parse(activities);
    	JSONObject jsonObject = (JSONObject) obj;
    	
    	JSONObject summary = (JSONObject)jsonObject.get("summary");
    	
    	ActivityModel a = new ActivityModel();
    	
    	a.caloriesOut= (Long) summary.get("caloriesOut");
    	a.steps = (Long)summary.get("steps");
    	a.activeScore=(Long)summary.get("activeScore");
    	a.activityCalories=(Long)summary.get("activityCalories");
    	a.elevation=(Double)summary.get("elevation");
    	               
    	return a;
    	
    }
    
    public SleepModel formatSleep(String sleep){
    	SleepModel acts=new SleepModel();
    	
    	Object obj=JSONValue.parse(sleep);
    	JSONObject jsonObject = (JSONObject) obj;
    	
    	JSONArray sleeps = (JSONArray)jsonObject.get("sleep");
    	
    	for (Object o : sleeps){
    		JSONObject s = (JSONObject) o;  		
    		if ((Boolean)s.get("isMainSleep")){
    			acts.startTime=(String)s.get("startTime");
    			acts.minutesAsleep = (Long)s.get("minutesAsleep");
    			acts.isMainSleep = true; 
    			acts.minutesAfterWakeup = (Long)s.get("minutesAfterWakeup");
    			acts.minutesToFallAsleep = (Long)s.get("minutesToFallAsleep");
    			acts.timeInBed = (Long)s.get("timeInBed");
    		}
    	}
    	
    	return acts;
    	
    }
    
    public AalfficiencyScores setActivityScore(Activity a){
    	AalfficiencyScores scores = new AalfficiencyScores(NAMESPACE+"ActivityScores");
    	FitbitDBInterface db = new FitbitDBInterface();
    	
    	try {
			ChallengeModel c = db.getActiveChallenges();
			ActivityScore as = new ActivityScore(NAMESPACE+"ActivityScore");
			Challenge challenge = new Challenge(NAMESPACE+"ActivityChallenge");
    	
			challenge.setDescription(c.getDescription());
			challenge.setGoal(c.getGoal());
			
			int score = calculateScore(a,c);
			
			
			as.setChallenge(challenge);
			as.setKcal(Integer.parseInt(a.getCalories()));
			as.setSteps(Integer.parseInt(a.getSteps()));
			
			as.setTodayActivityScore(score);
			
			scores.setActivityScore(as);
			
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return scores;
    }
    
    
    public int calculateScore(Activity a, ChallengeModel c){
    	int score=0;
    	FitbitDBInterface db = new FitbitDBInterface();
    	
			try{
			String[] goal = c.getGoal().split(" ");
			int total = Integer.parseInt(goal[0]);
			if (goal[1].compareTo("kcal")==0){
				int newscore = ((Integer.parseInt(a.getCalories())*c.getTotalScore())/total);
				score = newscore-c.getCurrentScore();
				c.setCurrentScore(newscore);
				db.modifyCurrentScore(c);
			}
			else{
				int newscore = ((Integer.parseInt(a.getSteps())*c.getTotalScore())/total);
				score = newscore-c.getCurrentScore();
				c.setCurrentScore(newscore);
				db.modifyCurrentScore(c);
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	
    	return score;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		publishFitbitData();
	}
	
}
