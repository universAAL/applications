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
 */
package org.universAAL.AALfficiency.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.universAAL.AALfficiency.model.ChallengeModel;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;

public class Setup {
public static final String SETUP_FILENAME = "AALfficiencyService.properties";
	
	private static String setupFileName = null;
	
	private static String configFolderPath = System.getProperty(
            BundleConfigHome.uAAL_CONF_ROOT_DIR, System
                    .getProperty("user.dir"));
	
	static public String getSetupFileName() {
		if (setupFileName != null) {
			return setupFileName;
		}
		File dir1 = new File(".");
		try {
			setupFileName = configFolderPath+"/AALfficiencyService/"+SETUP_FILENAME;
            System.out.println("Fichero: "+setupFileName);
			return setupFileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	public int getTotalScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("totalScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void setTotalScore(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("totalScore",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getTodayScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("todayScore");
		    
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void setTodayScore(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("todayScore",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getTotalElectricScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("totalElectricScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {e.printStackTrace();
		}
		
		return 0;
	}
	public void setTotalElectricScore(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("totalElectricScore",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getTodayElectricScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("todayElectricScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public void setTodayElectricScore(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("todayElectricScore",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getElectricitySaving(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("ElectricitySaving");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {e.printStackTrace();
		}
		
		return 0;
	}
	public void setElectricitySaving(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("ElectricitySaving",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getTotalActivityScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("totalActivityScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public void setTotalActivityScore(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("totalActivityScore",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getTodayActivityScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("todayActivityScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public void setTodayActivityScore(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("todayActivityScore",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getActivitySteps(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("ActivitySteps");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public void setActivitySteps(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("ActivitySteps",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getActivityKcal(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("ActivityKcal");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public void setActivityKcal(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("ActivityKcal",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setElectricityChallengeDescription(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.setProperty("ElectricityChallengeDescription",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setElectricityChallengeGoal(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.setProperty("ElectricityChallengeGoal",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public ChallengeModel getElectricityChallenge(){
		ChallengeModel c = new ChallengeModel();
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String desc = properties.getProperty("ElectricityChallengeDescription");
		    String goal = properties.getProperty("ElectricityChallengeGoal");
		    c.setActive("true");
		    c.setType("Electricity");
		    if (desc!=null)
		    	c.setDescription(desc);
		    if (goal!=null)
		    	c.setGoal(goal);
		    
		    return c;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public void setActivityChallengeDescription(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.setProperty("ActivityChallengeDescription",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setActivityChallengeGoal(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.setProperty("ActivityChallengeGoal",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ChallengeModel getActivityChallenge(){
		ChallengeModel c = new ChallengeModel();
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String desc = properties.getProperty("ActivityChallengeDescription");
		    String goal = properties.getProperty("ActivityChallengeGoal");
		    c.setActive("true");
		    c.setType("Activity");
		    if (desc!=null)
		    	c.setDescription(desc);
		    if (goal!=null)
		    	c.setGoal(goal);
		    
		    return c;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	
	
}
