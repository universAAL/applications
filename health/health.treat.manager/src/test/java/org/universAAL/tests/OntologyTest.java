package org.universAAL.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.universAAL.AALapplication.health.treat.manager.profiles.TreatmentManagerProfilesOnt;
import org.universAAL.container.JUnit.JUnitModuleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;

public class OntologyTest {

	@Test
	public void test() {
		ModuleContext mc = new JUnitModuleContext();
		OntologyManagement.getInstance().register(mc, new TreatmentManagerProfilesOnt());
		
	}

}
