package org.universAAL.AALapplication.contact_manager.persistence.impl;

import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

import java.sql.SQLException;

import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class ContactManagerPersistentServiceImpl implements ContactManagerPersistentService {

  private final Database database;


  public ContactManagerPersistentServiceImpl(Database database) {
    this.database = database;
  }

  public void saveVCard(VCard vCard) {

    validateParameter(vCard, "vCard");

    try {
      database.setAutocommit(false);
      database.saveVCard(vCard);
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    } finally {
      database.setAutocommit(true);
    }
  }

  public VCard getVCard(String personUri) {

    validateParameter(personUri, "personUri");

    try {
      return database.getVCard(personUri);
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    }
  }

  public void printData() {
    database.printData();
  }
}
