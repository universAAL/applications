package org.universAAL.drools.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import junit.framework.TestCase;

import org.junit.Test;
import org.universAAL.drools.engine.RulesEngine;

public class RulesEngineTest {
	@Test
	void testCreation() {
//		String lepath = System.getProperty("java.class.path");
//		System
//				.setProperty(
//						"java.class.path",
//						lepath.substring(0, lepath.length() - 1)
//								+ ";C:\\Desarrollo\\drools\\runtime5\\org.eclipse.jdt.core_3.5.2.v_981_R35x.jar");
//		String[] paths = System.getProperty("java.class.path").split(";");
//
//		for (String string : paths) {
//			System.out.println(string);
//		}
		assertNotNull(RulesEngine.getInstance());
		//RulesEngine re = RulesEngine.createRulesEngine(null);
	}

}
