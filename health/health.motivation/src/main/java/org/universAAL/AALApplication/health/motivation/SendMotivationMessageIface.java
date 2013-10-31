/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.AALApplication.health.motivation;

import java.util.ArrayList;

import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.owl.MotivationalMessage;

/**
 * Implements the mechanism that sends the message to the platform.
 * Should be connected to the context bus and send messages there.
 * @author mdelafuente
 *
 */
public interface SendMotivationMessageIface {
	
	public void sendMessageToAP (MotivationalMessage mm, Treatment t);
	public ArrayList <MotivationalMessage> getMMsentToAP(); 
	public void sendMessageToCaregiver (MotivationalMessage mm, Treatment t);
	public ArrayList <MotivationalMessage> getMMsentToCaregiver(); 

}
