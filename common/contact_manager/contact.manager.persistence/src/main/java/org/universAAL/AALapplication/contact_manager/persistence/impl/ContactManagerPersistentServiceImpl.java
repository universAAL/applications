package org.universAAL.AALapplication.contact_manager.persistence.impl;

import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

/**
 * @author George Fournadjiev
 */
public final class ContactManagerPersistentServiceImpl implements ContactManagerPersistentService {

  private final Database database;


  public ContactManagerPersistentServiceImpl(Database database) {
    this.database = database;
  }

  public boolean saveVCard(VCard vCard) {
    return database.saveVCard(vCard);
  }

  public void printData() {
     database.printData();
  }
}
