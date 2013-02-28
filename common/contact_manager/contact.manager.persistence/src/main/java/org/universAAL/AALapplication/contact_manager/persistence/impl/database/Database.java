package org.universAAL.AALapplication.contact_manager.persistence.impl.database;


import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

import java.sql.SQLException;

/**
 * @author George Fournadjiev
 */
public interface Database {

  void initDatabase() throws Exception;

  void printData(); //temporary method to see the data inside database tables

  int getNextIdFromIdGenerator();

  void setAutocommit(boolean autocommit);

  void saveVCard(VCard vCard) throws SQLException;

  VCard getVCard(String personUri) throws SQLException;
}
