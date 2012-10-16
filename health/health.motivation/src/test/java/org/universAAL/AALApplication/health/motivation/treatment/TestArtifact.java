package org.universAAL.AALApplication.health.motivation.treatment;

import org.osgi.framework.Bundle;
import org.universAAL.itests.IntegrationTest;

public class TestArtifact extends IntegrationTest {  
	public TestArtifact() {
		setIgnoreVersionMismatch(true);
	}
	public void testLoad() {
		logAllBundles();
	}
	   protected void logAllBundles() {
		   log("\n\n\nThe following bundles are installed in the integration testing framework:");
		   int i = 1;
		   for (Bundle b : bundleContext.getBundles()) {
		       log(formatMsg("     %2s. %s-%s", b.getBundleId(), b.getSymbolicName(),
		        ((String) b.getHeaders().get("Bundle-Version"))
		         .replaceFirst("\\.SNAPSHOT", "-SNAPSHOT")));
		   }
		   log("\n\n\n");
		      }
}

