package org.universAAL.AALapplication.contact_manager.persistence.impl.database;


import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

import java.util.Map;

/**
 * @author George Fournadjiev
 */
public interface Database {

  void initDatabase() throws Exception;

  void printData(); //temporary method to see the data inside database tables

  int getNextIdFromIdGenerator();

  Map<String, Column> getById(String tableName, int id);

  boolean saveVCard(VCard vCard);
}
