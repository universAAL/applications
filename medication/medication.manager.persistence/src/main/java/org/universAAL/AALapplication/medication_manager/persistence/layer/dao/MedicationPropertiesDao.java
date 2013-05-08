package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class MedicationPropertiesDao extends AbstractDao {

  public static final String NAME = "NAME";
  public static final String VALUE = "VALUE";

  static final String TABLE_NAME = "PROPERTIES";

  public MedicationPropertiesDao(Database database) {
    super(database, TABLE_NAME);
  }


  @Override
  @SuppressWarnings("unchecked")
  public Person getById(int id) {
    throw new UnsupportedOperationException("Not implemented");

  }

  private void addProperty(Map<String, Column> columns, Map<String, String> properties) {
    Column col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(VALUE);
    String value = (String) col.getValue();

    properties.put(name, value);

  }

  public Map<String, String> loadProperties() {
    Log.info("Loading properties", getClass());

    PreparedStatement ps = null;
    try {
      String sql = "select * from MEDICATION_MANAGER.PROPERTIES";

      ps = getPreparedStatement(sql);

      List<Map<String, Column>> propertiesList = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);
      Map<String, String> properties = new HashMap<String, String>();
      for (Map<String, Column> columnMap : propertiesList) {
        addProperty(columnMap, properties);
      }

      return properties;

    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }

  }


  public void setSystemPropertiesLoadedFromDatabase() {
    Map<String, String> properties = loadProperties();

    Set<String> keys = properties.keySet();
    for (String key : keys) {
      String value = properties.get(key);
      System.setProperty(key, value);
    }
  }
}
