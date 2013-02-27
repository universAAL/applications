package org.universAAL.AALapplication.contact_manager.persistence.impl.database;


import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

/**
 * @author George Fournadjiev
 */
public interface Database {

  void initDatabase() throws Exception;

  void printData(); //temporary method to see the data inside database tables

  int getNextIdFromIdGenerator();

  boolean saveVCard(VCard vCard);

  VCard getVCard(String personUri);
}
