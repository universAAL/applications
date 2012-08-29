/**
 * 
 */
package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.itests.IntegrationTest;
import org.universaal.ontology.health.owl.HealthProfile;

/**
 * @author amedrano
 *
 */
public class MapHealthProfileProviderTest extends IntegrationTest {


	private static final String userURI = "http://ontology.upm.es/Test.owl#saied";

	private MapHealthProfileProvider mHPP;

	public void onSetUp() {
		mHPP = new MapHealthProfileProvider();
	}

	public void testGetProfile() {
		assertTrue(mHPP.getHealthProfile(userURI) instanceof HealthProfile);
	}

	public void testUpdateProfile() {
		HealthProfile hp = mHPP.getHealthProfile(userURI);
		mHPP.updateHealthProfile(hp);
		assertTrue(hp == mHPP.getHealthProfile(userURI));
	}
}
