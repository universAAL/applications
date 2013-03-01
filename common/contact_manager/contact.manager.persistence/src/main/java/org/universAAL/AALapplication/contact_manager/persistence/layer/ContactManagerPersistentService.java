package org.universAAL.AALapplication.contact_manager.persistence.layer;


/**
 * @author George Fournadjiev
 */
public interface ContactManagerPersistentService {


  void saveVCard(VCard vCard);

  void editVCard(String userUri, VCard vCard);

  VCard getVCard(String userUri);

  void printData(); //temporary method to see the data inside database tables

  void removeVCard(String uri);
}
