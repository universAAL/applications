/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
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
package org.universAAL.agenda.remote;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.agenda.ont.Calendar;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.io.owl.PrivacyLevel;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.output.OutputPublisher;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.ontology.profile.User;

/**
 * 
 * @author alfiva
 */
public class OPublisher extends OutputPublisher {

    private final static Logger log = LoggerFactory.getLogger(OPublisher.class);

    protected OPublisher(ModuleContext mcontext) {
	super(mcontext);
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    public void showMainScreen(User user) {
	log
		.info("Publishing Output: showMainScreen for user {}", user
			.getURI());
	Form f = Activator.gui.getMainScreenMenuForm();
	OutputEvent oe = new OutputEvent(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.guiinput.subscribe(f.getDialogID());
	publish(oe);
    }

    public void showMessageScreen(User user, String msg) {
	log
		.info("Publishing Output: showMainScreen for user {}", user
			.getURI());
	Form f = Activator.gui.getMessageForm(msg);
	OutputEvent oe = new OutputEvent(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.guiinput.subscribe(f.getDialogID());
	publish(oe);
    }

    // SC2011 show events screen
    public void showEventsScreen(User user, Calendar cal) {
	log.info("Publishing Output: showEventsScreen for user {}", user
		.getURI());
	Form f = Activator.gui.getEventsForm(cal);
	OutputEvent oe = new OutputEvent(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.guiinput.subscribe(f.getDialogID());
	publish(oe);
    }

    // SC2011 show google screen
    public void showGoogleScreen(User user, Calendar cal) {
	log.info("Publishing Output: showGoogleScreen for user {}", user
		.getURI());
	Form f = Activator.gui.getGoogleForm(cal);
	OutputEvent oe = new OutputEvent(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.guiinput.subscribe(f.getDialogID());
	publish(oe);
    }

}
