package org.universaal.ontology.disease.owl;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;

import org.universaal.ontology.health.owl.Treatment;

import org.universAAL.middleware.owl.ManagedIndividual;

public class Disease extends ManagedIndividual {
	
	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "Disease";
	public static final String PROP_NAME =  DiseaseOntology.NAMESPACE
	+ "name";
	public static final String PROP_SYMPTHOMS =  DiseaseOntology.NAMESPACE
	+ "sympthoms";
	public static final String PROP_DISEASE_STATUS =  DiseaseOntology.NAMESPACE
	+ "diseaseStatus";
	public static final String PROP_ETIOLOGY =  DiseaseOntology.NAMESPACE
	+ "etiology";
	public static final String PROP_PATOGENY =  DiseaseOntology.NAMESPACE
	+ "patogeny";
	public static final String PROP_EPIDEMIOLOGY =  DiseaseOntology.NAMESPACE
	+ "epidemiology";
	public static final String PROP_CONTAGIOUS =  DiseaseOntology.NAMESPACE
	+ "contagious";
	public static final String PROP_DIAGNOSTIC =  DiseaseOntology.NAMESPACE
	+ "diagnostic";
	public static final String PROP_PRONOSTIC =  DiseaseOntology.NAMESPACE
	+ "pronostic";
	public static final String PROP_HAS_TREATMENT =  DiseaseOntology.NAMESPACE
	+ "hasTreatment";

  public Disease () {
    super();
  }
  
  public Disease (String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	  return true 
      && props.containsKey(PROP_NAME)
      && props.containsKey(PROP_SYMPTHOMS)
      && props.containsKey(PROP_DISEASE_STATUS)
      && props.containsKey(PROP_ETIOLOGY)
      && props.containsKey(PROP_PATOGENY)
      && props.containsKey(PROP_EPIDEMIOLOGY)
      && props.containsKey(PROP_CONTAGIOUS)
      && props.containsKey(PROP_DIAGNOSTIC)
      && props.containsKey(PROP_PRONOSTIC)
      && props.containsKey(PROP_HAS_TREATMENT)
      ;
  }
  
//GETTERS AND SETTERS
  
  public String getDiseaseName() {
	  return (String)props.get(PROP_NAME);
  }		

  public void setAssociatedQuestion(String diseaseName) {
	  if (diseaseName != null)
		  props.put(PROP_NAME, diseaseName);
  }		

  public DiseaseSeverityStatus getDiseaseStatus() {
	  return (DiseaseSeverityStatus)props.get(PROP_DISEASE_STATUS);
  }		

  public void setDiseaseStatus(DiseaseSeverityStatus diseaseStatus) {
	  if (diseaseStatus != null)
		  props.put(PROP_DISEASE_STATUS, diseaseStatus);
  }	
  
  public Etiology getEtiology() {
	  return (Etiology)props.get(PROP_ETIOLOGY);
  }		

  public void setEtiology(Etiology etiology) {
	  if (etiology != null)
		  props.put(PROP_ETIOLOGY, etiology);
  }	

  public Patogeny getPatogeny() {
	  return (Patogeny)props.get(PROP_PATOGENY);
  }		

  public void setPatogeny(Patogeny patogeny) {
	  if (patogeny != null)
		  props.put(PROP_PATOGENY, patogeny);
  }	

  public Epidemiology getEpidemiology() {
	  return (Epidemiology)props.get(PROP_EPIDEMIOLOGY);
  }		

  public void setEpidemiology(Epidemiology epidemiology) {
	  if (epidemiology != null)
		  props.put(PROP_EPIDEMIOLOGY, epidemiology);
  }	

  public Diagnostic getDiagnostic() {
	  return (Diagnostic)props.get(PROP_DIAGNOSTIC);
  }		

  public void setDiagnostic(Diagnostic diagnostic) {
	  if (diagnostic != null)
		  props.put(PROP_DIAGNOSTIC, diagnostic);
  }	
  public Epidemiology getPronostic() {
	  return (Epidemiology)props.get(PROP_EPIDEMIOLOGY);
  }		

  public void setPronostic(Pronostic pronostic) {
	  if (pronostic != null)
		  props.put(PROP_EPIDEMIOLOGY, pronostic);
  }	

  
  
  public boolean isContagious() {
	  Boolean b = (Boolean) props.get(PROP_CONTAGIOUS);
	return (b == null) ? false : b.booleanValue();
  }		

  public void setIsContagious(Boolean isContagious) {
	  props.put(PROP_CONTAGIOUS, new Boolean(isContagious));
  }	
  
  public Sympthom[] getSympthoms() {

		Object propList = props.get(PROP_SYMPTHOMS);
		if (propList instanceof List) {
			return (Sympthom[]) ((List) propList).toArray(new Sympthom[0]);
		} else {
			List returnList = new ArrayList();
			if (propList != null)
				returnList.add((Sympthom) propList);
			return (Sympthom[]) returnList.toArray(new Sympthom[0]);
		}  
	}	

	public void setSympthoms(Sympthom[] sympthoms) {
		List propList = new ArrayList(sympthoms.length);
		for (int i = 0; i < sympthoms.length; i++) {
			propList.add(sympthoms[i]);
		}
		props.put(PROP_SYMPTHOMS, propList);
	}


	
	public void addSympthom(Sympthom sympthom) {
		Object propList = props.get(PROP_SYMPTHOMS);
		if (propList instanceof List) {
			List list = (List) propList;
			list.add(sympthom);
			props.put(PROP_SYMPTHOMS, list);
		} else if (propList == null) {
			props.put(PROP_SYMPTHOMS, sympthom);
		} else {
			List list = new ArrayList();
			list.add((Sympthom) propList);
			list.add(sympthom);
			props.put(PROP_SYMPTHOMS, list);
		}
	}  
	
	
	public Treatment[] getTreatments() {

		Object propList = props.get(PROP_HAS_TREATMENT);
		if (propList instanceof List) {
			return (Treatment[]) ((List) propList).toArray(new Treatment[0]);
		} else {
			List returnList = new ArrayList();
			if (propList != null)
				returnList.add((Treatment) propList);
			return (Treatment[]) returnList.toArray(new Treatment[0]);
		}  
	}	

	public void setTreatments(Treatment[] treatments) {
		List propList = new ArrayList(treatments.length);
		for (int i = 0; i < treatments.length; i++) {
			propList.add(treatments[i]);
		}
		props.put(PROP_HAS_TREATMENT, propList);
	}


	
	public void addTreatment(Treatment treatment) {
		Object propList = props.get(PROP_HAS_TREATMENT);
		if (propList instanceof List) {
			List list = (List) propList;
			list.add(treatment);
			props.put(PROP_HAS_TREATMENT, list);
		} else if (propList == null) {
			props.put(PROP_HAS_TREATMENT, treatment);
		} else {
			List list = new ArrayList();
			list.add((Sympthom) propList);
			list.add(treatment);
			props.put(PROP_HAS_TREATMENT, list);
		}
	}  
  




}
