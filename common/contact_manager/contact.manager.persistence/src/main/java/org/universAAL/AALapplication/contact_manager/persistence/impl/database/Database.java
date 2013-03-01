package org.universAAL.AALapplication.contact_manager.persistence.impl.database;


import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author George Fournadjiev
 */
public interface Database {

  void initDatabase() throws Exception;

  void printData(); //temporary method to see the data inside database tables

  int getNextIdFromIdGenerator();

  void setAutocommit(boolean autocommit);

  void saveVCard(VCard vCard, PreparedStatement statementVCard,
                 PreparedStatement statementTypes) throws SQLException;

  void editVCard(String userUri, VCard vCard, PreparedStatement statementVCard,
                 PreparedStatement statementDeleteTypes, PreparedStatement statementTypes) throws SQLException;

  VCard getVCard(String personUri) throws SQLException;

  PreparedStatement createAddStatementVCard() throws SQLException;

  PreparedStatement createAddStatementTypes() throws SQLException;

  PreparedStatement createEditStatementVCard(String userUri) throws SQLException;

  PreparedStatement createEditDeleteStatementTypes(String userUri) throws SQLException;

  public void commit() throws SQLException;

  public void rollback();
}
