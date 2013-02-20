package org.universAAL.AALapplication.contact_manager.persistence.impl;

import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;

/**
 * @author George Fournadjiev
 */
public final class ContactManagerPersistentServiceImpl implements ContactManagerPersistentService {

  private final Database database;


  public ContactManagerPersistentServiceImpl(Database database) {
    this.database = database;
  }

  public void printData() {
     database.printData();
  }
}
