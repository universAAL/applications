package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.configuration.FormatEnum;
import org.universAAL.AALapplication.medication_manager.configuration.Pair;
import org.universAAL.AALapplication.medication_manager.configuration.PropertyInfo;
import org.universAAL.AALapplication.medication_manager.configuration.TypeEnum;
import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class MedicationPropertiesDao extends AbstractDao {

  public static final String NAME = "NAME";
  public static final String FORMAT = "FORMAT";
  public static final String TYPE = "TYPE";
  public static final String DESCRIPTION = "DESCRIPTION";
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

  public Set<PropertyInfo> getAllProperties() {
    Log.info("Loading properties", getClass());

    PreparedStatement ps = null;
    try {
      String sql = "select * from MEDICATION_MANAGER.PROPERTIES";

      ps = getPreparedStatement(sql);

      List<Map<String, Column>> propertiesList = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);
      Set<PropertyInfo> propertyInfos = new HashSet<PropertyInfo>();
      for (Map<String, Column> columnMap : propertiesList) {
        addProperty(columnMap, propertyInfos);
      }

      return propertyInfos;

    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }

  }

  private void addProperty(Map<String, Column> columns, Set<PropertyInfo> propertyInfos) {

    Column col = columns.get(ID);
    int id = (Integer) col.getValue();

    col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(VALUE);
    String value = (String) col.getValue();

    col = columns.get(FORMAT);
    String format = (String) col.getValue();
    FormatEnum formatEnum = FormatEnum.getEnumFromValue(format);

    col = columns.get(TYPE);
    String type = (String) col.getValue();
    TypeEnum typeEnum = TypeEnum.getEnumFromValue(type);

    col = columns.get(DESCRIPTION);
    String description = (String) col.getValue();

    PropertyInfo info = new PropertyInfo(id, name, value, formatEnum, typeEnum, description);

    propertyInfos.add(info);
  }

  private void addProperty(Map<String, Column> columns, Map<String, String> properties) {
    Column col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(VALUE);
    String value = (String) col.getValue();

    properties.put(name, value);

  }

  private Map<String, String> loadProperties() {
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

  public void updatePropertiesValues(Set<Pair<Integer, String>> propertyValues) {
    for (Pair<Integer, String> pair : propertyValues) {
      updatePropertyValues(pair);
    }
  }

  private void updatePropertyValues(Pair<Integer, String> pair) {
    String sql = "UPDATE MEDICATION_MANAGER.PROPERTIES SET VALUE = ? WHERE ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setString(1, pair.getSecond());
      ps.setInt(2, pair.getFirst());

      ps.execute();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }

    setSystemPropertiesLoadedFromDatabase();
  }
}
