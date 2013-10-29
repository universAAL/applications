package org.universAAL.ontology.activity;



public class BathroomUsage extends PersonalHygeneActivity {
  public static final String MY_URI = ActivityOntology.NAMESPACE
    + "BathroomUsage";


  public BathroomUsage () {
    super();
  }
  
  public BathroomUsage (String uri) {
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
