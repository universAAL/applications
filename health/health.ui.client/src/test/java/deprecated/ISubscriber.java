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
package deprecated;


import java.util.TreeMap;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIResponse;

import deprecated.org.universAAL.AALapplication.health.manager.ui.InputListener;

public class ISubscriber extends UICaller{

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
	
	public void registerUI(String formID, InputListener listener) {
		inputMapper.put(formID, listener);
	}

	public void unresgisterUI(String formID) {
		inputMapper.remove(formID);
	}

	@Override
	public void handleUIResponse(UIResponse event) {
		inputMapper.get(event.getDialogID()).handleEvent(event);
	}
}
