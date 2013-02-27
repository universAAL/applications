package org.universAAL.AALapplication.contact_manager.persistence.layer;


/**
 * @author George Fournadjiev
 */
public interface ContactManagerPersistentService {


  boolean saveVCard(VCard vCard);

  VCard getVCard(String personUri);

  void printData(); //temporary method to see the data inside database tables

}
