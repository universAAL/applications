package org.universaal.ontology.health.owl;

public class Diet extends HealthyHabitsAdoption{

	public static final String MY_URI = HealthOntology.NAMESPACE
    + "Diet";


  public Diet () {
    super();
  }
  
  public Diet (String uri) {
    super(uri);
  }
  
  public Diet (String name, String description){
	  super(name, description);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true; 
  }

	
}
