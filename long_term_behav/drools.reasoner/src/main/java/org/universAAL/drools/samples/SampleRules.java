package org.universAAL.drools.samples;

public class SampleRules {

	public static String SIMPL_RULE_2 = "package org.universAAL.AALapplication\r\n"

			+ "import org.universAAL.middleware.context.ContextEvent;\n"
			+ "import org.universAAL.drools.engine.RulesEngine;\n"
			+ "import org.universAAL.ontology.location.Location;\n"
			+ "import org.universAAL.middleware.rdf.Resource;\n"
			+ "import org.universAAL.ontology.phThing.Sensor;\n"
			+ "import java.util.Hashtable;\n"
			+ "import java.util.ArrayList;\n"
			+ "dialect \"java\" "
			// + "declare ContextEvent\n"
			// + "@role(event)\n"
			// + "@expires(2m)\n"
			// + "end\n"
			// + "declare Activity\n"
			// + "place : String @key\n"
			// + "intensity: String\n"
			// + "counter : int\n"
			// + "end\n"
			+ "rule \"SimpleRuleForTest\"\n"
			+ "when\n"
			+ "ContextEvent(  )\n"
			+ "then\n" + "System.out.println(\"Polo!\");\n" + "end\n";

}
