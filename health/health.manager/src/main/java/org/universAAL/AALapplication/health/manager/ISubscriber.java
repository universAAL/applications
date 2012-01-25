/*******************************************************************************
 * Copyright 2011 Universidad Polit�cnica de Madrid
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
package org.universAAL.AALapplication.health.manager;


import java.util.TreeMap;

import org.universAAL.AALapplication.health.manager.ui.InputListener;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;

public class ISubscriber extends InputSubscriber{

	private TreeMap<String, InputListener> inputMapper;
	
	protected ISubscriber(ModuleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
	}

	public void handleInputEvent(InputEvent event) {
		/*
		 * Delegate Handlement to subscribed UI
		 */
		inputMapper.get(event.getDialogID()).handleEvent(event);
	}
	
	public void registerUI(String formID, InputListener listener) {
		inputMapper.put(formID, listener);
		addNewRegParams(formID);
	}

	public void unresgisterUI(String formID) {
		inputMapper.remove(formID);
		removeMatchingRegParams(formID);
	}
}
