/*******************************************************************************
 * Copyright 2013 Universidad Politécnica de Madrid
 * Copyright 2013 Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
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

package org.universAAL.AALapplication.health.manager.ui;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;

/**
 * @author amedrano
 *
 */
public class AssistedPersonSelector extends AbstractHealthForm {

   
    /**
     * 
     */
    private static final String NAMESPACE = "http://lst.tfo.upm.es/Heatlh.owl#";
    private static final String USRS = NAMESPACE + "userList";
    private static final String SELECTED = NAMESPACE + "selected";
    private static final String SELECT_CMD = NAMESPACE + "select";
    private static final String BACK_CMD = NAMESPACE + "back";
    
    /**
     * @param context
     * @param inputUser
     * @param targetUser
     */
    public AssistedPersonSelector(ModuleContext context, User inputUser,
	    AssistedPerson targetUser) {
	super(context, inputUser, targetUser);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param ahf
     */
    public AssistedPersonSelector(AbstractHealthForm ahf) {
	super(ahf);
	// TODO Auto-generated constructor stub
    }

    /** {@ inheritDoc}	 */
    @Override
    public void handleUIResponse(UIResponse uiResponse) {
	String cmd = uiResponse.getSubmissionID();
	if (cmd.equals(SELECT_CMD)) {
	    AssistedPerson ap = (AssistedPerson) uiResponse
		    .getUserInput(new String[] { SELECTED });
	    new MainMenu(owner, inputUser, ap).show();
	}
    }
    
    public void show(){
	Resource r = new Resource();
	List l = listAssistedPersons();
	r.changeProperty(USRS, l);
	Form f = Form.newDialog("Select Assisted Person", new Resource());
	
	Select1 s = new Select1(f.getIOControls(), new Label("Assisted Person", null), 
		new PropertyPath(null, false, new String[]{SELECTED}),
		null, null);
	//TODO use display names instead of URIs.
	s.generateChoices(l.toArray());
	
	new Submit(f.getSubmits(), new Label("Select",null),  SELECT_CMD);
	new Submit(f.getSubmits(), new Label("Back", null),  BACK_CMD);
	sendForm(f);
    }
    
    
    private List<AssistedPerson> listAssistedPersons(){
 	ServiceRequest sr = new ServiceRequest(new ProfilingService(null), null);
 	ProcessOutput output0 = new ProcessOutput(null);
 	output0.setParameterType(User.MY_URI);
 	sr.addSimpleOutputBinding(output0, new String[]{ProfilingService.PROP_CONTROLS});
 	sr.addRequiredOutput(USRS, new String[]{ProfilingService.PROP_CONTROLS});
 	ServiceResponse sre = new DefaultServiceCaller(owner).call(sr);
 	if (sre.getCallStatus().equals(CallStatus.succeeded)){
 	    List<Resource> usrs = sre.getOutput(USRS, true);
 	    ArrayList<AssistedPerson> apUsrs = new ArrayList<AssistedPerson>();
 	    for (Resource res : usrs) {
 		if (res instanceof AssistedPerson){
 		    apUsrs.add((AssistedPerson) res);
 		}
 	    }
 	    return apUsrs;
 	}
 	return new ArrayList<AssistedPerson>();
     }

}
