package org.universAAL.ontology.activity;



public abstract class MedicalComplianceActivity extends Activity {
  public static final String MY_URI = ActivityOntology.NAMESPACE
    + "MedicalComplianceActivity";


  public MedicalComplianceActivity () {
    super();
  }
  
  public MedicalComplianceActivity (String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	// TODO Implement or if for Device subclasses: remove 
	return 0;
  }

  public boolean isWellFormed() {
	return true ;
  }
}
