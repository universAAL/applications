/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
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
package org.universAAL.ltba.service;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ltba.manager.Activator;
import org.universAAL.ontology.drools.DroolsReasoning;
import org.universAAL.ontology.ltba.LTBAService;

public class ProvidedLTBAService extends LTBAService {

	public ProvidedLTBAService(String uri) {
		super(uri);

	}

	public static final String LTBA_NAMESPACE = "http:www.tsbtecnologias.es/LTBA.owl#";
	public static final String MY_URI = LTBA_NAMESPACE + "ProvidedLTBAService";
	// LTBA ON
	public static final String SERVICE_SWITCH_ON = LTBA_NAMESPACE + "switchOn";
	// LTBA OFF
	public static final String SERVICE_SWITCH_OFF = LTBA_NAMESPACE
			+ "switchOff";
	// LTBA SHOW TEXT REPORT
	public static final String SERVICE_PRINT_REPORT = LTBA_NAMESPACE
			+ "printReport";
	// LTBA SHOW WEEK REPORT
	public static final String SERVICE_SHOW_WEEK = LTBA_NAMESPACE + "showWeek";
	// LTBA SHOW MONTH REPORT
	public static final String SERVICE_SHOW_MONTH = LTBA_NAMESPACE
			+ "showMonth";

	static final ServiceProfile[] profiles = new ServiceProfile[5];

	private static final String INPUT_STATUS = LTBA_NAMESPACE + "inputStatus";
	private static final String INPUT_PRINT = LTBA_NAMESPACE + "inputPrint";
	private static final String INPUT_REPORT = LTBA_NAMESPACE + "inputReport";

	static {
		OntologyManagement.getInstance().register(
				Activator.mc,
				new SimpleOntology(MY_URI, DroolsReasoning.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new ProvidedLTBAService(instanceURI);
							}
						}));
		// Switch ON
		// TODO Add property for avoiding passing null parameters to the effect
		// (e.g. LTBA_STATE)
		ProvidedLTBAService switchOn = new ProvidedLTBAService(
				SERVICE_SWITCH_ON);

		/**
		 * addRule.addFilteringInput(INPUT_RULE, Rule.MY_URI, 1, 1, new String[]
		 * { DroolsReasoning.PROP_KNOWS_RULES }); // MergedRestriction restr1 =
		 * // MergedRestriction.getFixedValueRestriction(DroolsReasoning.
		 * PROP_KNOWS_RULES, // input1.asVariableReference()); //
		 * addRule.addInstanceLevelRestriction(restr1, new //
		 * String[]{DroolsReasoning.PROP_KNOWS_RULES}); // Not sure if the next
		 * line works as I supose addRule.myProfile.addAddEffect( new String[] {
		 * DroolsReasoning.PROP_KNOWS_RULES }, input1);
		 */
		ProcessInput inputStatus = new ProcessInput(INPUT_STATUS);
		inputStatus.setParameterType(TypeMapper.getDatatypeURI(Boolean.class));
		switchOn.addFilteringInput(INPUT_STATUS, TypeMapper
				.getDatatypeURI(Boolean.class), 0, 1,
				new String[] { LTBAService.PROP_SERVICE_HAS_STATUS_VALUE });
		switchOn.myProfile.addAddEffect(
				new String[] { LTBAService.PROP_SERVICE_HAS_STATUS_VALUE },
				inputStatus);
		profiles[0] = switchOn.myProfile;
		// Switch OFF
		// TODO Add property for avoiding passing null parameters to the effect
		// (e.g. LTBA_STATE)

		ProvidedLTBAService switchOff = new ProvidedLTBAService(
				SERVICE_SWITCH_OFF);
		switchOff.getProfile().addRemoveEffect(
				new String[] { LTBAService.PROP_SERVICE_HAS_STATUS_VALUE });
		profiles[1] = switchOff.myProfile;

		ProvidedLTBAService printReport = new ProvidedLTBAService(
				SERVICE_PRINT_REPORT);

		ProcessInput inputPrint = new ProcessInput(INPUT_PRINT);
		inputPrint.setParameterType(TypeMapper.getDatatypeURI(String.class));
		printReport.addFilteringInput(INPUT_PRINT, TypeMapper
				.getDatatypeURI(String.class), 0, 1,
				new String[] { LTBAService.PROP_HAS_TEXT_REPORT });
		printReport.myProfile.addAddEffect(
				new String[] { LTBAService.PROP_HAS_TEXT_REPORT }, inputPrint);

		// printReport.myProfile.addAddEffect(
		// new String[] { LTBAService.PROP_HAS_TEXT_REPORT }, null);
		profiles[2] = printReport.myProfile;

		ProvidedLTBAService showWeek = new ProvidedLTBAService(
				SERVICE_SHOW_WEEK);

		ProcessInput inputWeek = new ProcessInput(INPUT_PRINT);
		inputWeek.setParameterType(TypeMapper.getDatatypeURI(String.class));
		showWeek.addFilteringInput(INPUT_PRINT, TypeMapper
				.getDatatypeURI(String.class), 0, 1,
				new String[] { LTBAService.PROP_HAS_WEEK_REPORT });
		showWeek.myProfile.addAddEffect(
				new String[] { LTBAService.PROP_HAS_WEEK_REPORT }, inputPrint);

		// printReport.myProfile.addAddEffect(
		// new String[] { LTBAService.PROP_HAS_TEXT_REPORT }, null);
		profiles[3] = showWeek.myProfile;

		ProvidedLTBAService showMonth = new ProvidedLTBAService(
				SERVICE_SHOW_MONTH);

		ProcessInput inputMonth = new ProcessInput(INPUT_PRINT);
		inputMonth.setParameterType(TypeMapper.getDatatypeURI(String.class));
		showMonth.addFilteringInput(INPUT_PRINT, TypeMapper
				.getDatatypeURI(String.class), 0, 1,
				new String[] { LTBAService.PROP_HAS_MONTH_REPORT });
		showMonth.myProfile.addAddEffect(
				new String[] { LTBAService.PROP_HAS_MONTH_REPORT }, inputPrint);

		// printReport.myProfile.addAddEffect(
		// new String[] { LTBAService.PROP_HAS_TEXT_REPORT }, null);
		profiles[4] = showMonth.myProfile;

		// ProvidedLTBAService showMonth = new ProvidedLTBAService(
		// SERVICE_SHOW_MONTH);
		//
		// ProcessInput inputReport = new ProcessInput(INPUT_REPORT);
		// inputReport.setParameterType(ActivityReportType.MY_URI);
		// showMonth.addFilteringInput(INPUT_REPORT, ActivityReportType.MY_URI,
		// 1,
		// 1, new String[] { LTBAService.PROP_HAS_ACTIVITY_REPORT,
		// ActivityReportType.MY_URI });
		// showWeek.myProfile.addAddEffect(
		// new String[] { LTBAService.PROP_HAS_ACTIVITY_REPORT,
		// ActivityReportType.MY_URI }, inputReport);
		//
		// // printReport.myProfile.addAddEffect(
		// // new String[] { LTBAService.PROP_HAS_TEXT_REPORT }, null);
		// profiles[4] = showMonth.myProfile;

	}

}
