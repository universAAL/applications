package org.universAAL.AALapplication.contact_manager.persistence.impl;

import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

import java.sql.PreparedStatement;

import static org.universAAL.AALapplication.contact_manager.persistence.impl.Activator.*;
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

    PreparedStatement statementVCard = null;
    PreparedStatement statementTypes = null;
    try {
      database.setAutocommit(false);
      statementVCard = database.createAddStatementVCard();
      statementTypes = database.createAddStatementTypes();
      database.saveVCard(vCard, statementVCard, statementTypes);
      database.commit();
    } catch (Exception e) {
      throw new ContactManagerPersistenceException("Problem saving the VCard", e);
    } finally {
      database.setAutocommit(true);
      database.rollback();
      closeStatement(statementVCard);
      closeStatement(statementTypes);
    }
  }

  public void editVCard(String userUri, VCard vCard) {
    validateParameter(userUri, "userUri");
    validateParameter(vCard, "vCard");

    userUri = userUri.toUpperCase();

    PreparedStatement statementVCard = null;
    PreparedStatement statementTypes = null;
    PreparedStatement statementDeleteTypes = null;
    try {
      database.setAutocommit(false);
      statementVCard = database.createEditStatementVCard(userUri);
      statementTypes = database.createAddStatementTypes();
      statementDeleteTypes = database.createEditDeleteStatementTypes(userUri);
      database.editVCard(userUri, vCard, statementVCard, statementDeleteTypes, statementTypes);
      database.commit();
    } catch (Exception e) {
      throw new ContactManagerPersistenceException("Problem updating the VCard", e);
    } finally {
      database.setAutocommit(true);
      database.rollback();
      closeStatement(statementVCard);
      closeStatement(statementTypes);
      closeStatement(statementDeleteTypes);
    }
  }

  public VCard getVCard(String userUri) {

    validateParameter(userUri, "userUri");
    userUri = userUri.toUpperCase();
    //TODO
    try {
      return database.getVCard(userUri);
    } catch (Exception e) {
      throw new ContactManagerPersistenceException(e);
    }
  }

  public void printData() {
    database.printData();
  }

  public void removeVCard(String userUri) {
    validateParameter(userUri, "userUri");
    userUri = userUri.toUpperCase();

    PreparedStatement statementVCard = null;
    PreparedStatement statementTypes = null;
    try {
      database.setAutocommit(false);
      statementVCard = database.createDeleteStatementVCard(userUri);
      statementTypes = database.createDeleteStatementTypes(userUri);
      database.removeVCard(userUri, statementVCard, statementTypes);
      database.commit();
    } catch (Exception e) {
      throw new ContactManagerPersistenceException("Problem deleting the VCard", e);
    } finally {
      database.setAutocommit(true);
      database.rollback();
      closeStatement(statementVCard);
      closeStatement(statementTypes);
    }


  }
}
