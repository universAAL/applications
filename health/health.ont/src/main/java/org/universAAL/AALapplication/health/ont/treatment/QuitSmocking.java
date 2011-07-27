/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politécnica de Madrdid
	
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


package org.universAAL.AALapplication.health.ont.treatment;

import org.universAAL.middleware.owl.ManagedIndividual;

public class QuitSmocking extends HealthyHabitsAdoption {

	public static final String TREATMENT_NAMESPACE = Treatment.TREATMENT_NAMESPACE;
	public static final String MY_URI;
	
	static {
		MY_URI = TREATMENT_NAMESPACE + "QuitSmocking";
		register(QuitSmocking.class);
    }
	
	public static String getRDFSComment() {
		return "QuitSmocking is a type of 'Healthy habits adoption'";
	}

	public static String getRDFSLabel() {
		return "Quit smocking";
	}

	public int getPropSerializationType(String propURI) {
		return ManagedIndividual.PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}

	//Constructors
		
	public QuitSmocking(){
			
	}

	public QuitSmocking(String uri) {
		super(uri);
	}

	public QuitSmocking(String uriPrefix, int numProps) {
		super(uriPrefix, numProps);
	}
	
}
