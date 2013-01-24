package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

/**
 * @author George Fournadjiev
 */
public final class Column<N, V> {

  private N name; //first member of pair
  private V value; //second member of pair

  public Column(N name, V value) {
    this.name = name;
    this.value = value;
  }

  public void setName(N name) {
    this.name = name;
  }

  public void setValue(V value) {
    this.value = value;
  }

  public N getName() {
    return  name;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Column{" +
        "name=" + name +
        ", value=" + value +
        '}';
  }
}
