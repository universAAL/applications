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

package org.universAAL.AALApplication.health.profile.manager.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;

import org.universAAL.AALApplication.health.profile.manager.IHealthProfileProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.serialization.MessageContentSerializer;
import org.universAAL.middleware.util.GraphIterator;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.profile.AssistedPerson;

/**
 * @author amedrano
 *
 */
public class FileHealthProfileManager implements IHealthProfileProvider {
    private static final String UTF_8 = "utf-8";
    
    private MessageContentSerializer contentSerializer;
    private ModuleContext context;
    private File dir;

    /**
     * 
     */
    public FileHealthProfileManager(ModuleContext context, File dir) {
	this.context = context;
	this.dir = dir;
	this.contentSerializer = (MessageContentSerializer) context
		.getContainer()
		.fetchSharedObject(
			context,
			new Object[] { MessageContentSerializer.class.getName() });
	if (contentSerializer == null) {
	    LogUtils.logError(context, getClass(), "Constructor",
		    new Object[] { "no serializer found" }, null);
	    throw new IllegalArgumentException("unable to Initialize ContentSerializer");
	}
	if (!dir.exists()&&!dir.mkdirs()){
	    LogUtils.logError(context, getClass(), "Constructor", "Unable to allocate repository directory");
	    throw new IllegalArgumentException("unable to Initialize FileHealthProfileManager");
	}
    }

    /** {@ inheritDoc}	 */
    public HealthProfile getHealthProfile(Resource userURI) {
	if (userURI == null
		|| userURI.getURI() == null
		|| userURI.getURI().isEmpty())
	    return null;
	File profileFile = new File(dir, Integer.toString(userURI.getURI().hashCode()));
	if (profileFile.exists()){
	    return (HealthProfile) read(profileFile);
	}
	else {
	    return newHealthProfile(userURI);
	}
    }

    private HealthProfile newHealthProfile(Resource ap) {
	HealthProfile hp = new HealthProfile(ap.getURI() + "HealthSubprofile");
	if (ap instanceof AssistedPerson) {
	    hp.setAssignedAssistedPerson((AssistedPerson) ap);
	}
	File profileFile = new File(dir, Integer.toString(ap.getURI().hashCode()));
	write(profileFile, hp);
	return hp;
    }
    
    /** {@ inheritDoc}	 */
    public void updateHealthProfile(HealthProfile healthProfile) {
	if (healthProfile == null
		|| healthProfile.getURI() == null
		|| healthProfile.getURI().isEmpty())
	    return;
	File profileFile = new File(dir, Integer.toString(healthProfile.getAssignedAssistedPerson().getURI().hashCode()));
	write(profileFile, healthProfile);

    }
    
    private void write(File file, Resource root){
	Iterator it = GraphIterator.getResourceIterator(root);
	while (it.hasNext()) {
	    Resource r = (Resource) it.next();
	    r.unliteral();
	}
	
	String serialized = contentSerializer.serialize(root);

	//wirting
	OutputStreamWriter osw;
	try {
		osw = new OutputStreamWriter(new FileOutputStream(file), Charset.forName(UTF_8));
		osw.write(serialized);
		osw.close();
	} catch (FileNotFoundException e) {
		// Highly improbable.
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    private Resource read(File file){
	String serialized = "";
	try {
		serialized = new Scanner(file,UTF_8).useDelimiter("\\Z").next();
	} catch (Exception e){
		/*
		 *  either:
		 *  	- empty file
		 *  	- non existent file
		 *  	- Scanner failture...
		 *  Nothing to do here
		 */
		return null;
	}
	
	try {
		Resource root = (Resource) contentSerializer.deserialize(serialized);
		if (serialized.length() > 5 && root !=null){
			return root;
		}
	} catch (Exception e) {
		LogUtils.logWarn(context, getClass(),"read", 
				new Object[]{"unable to deserialize file: ", file},e);
	}
	return null;
    }

}
