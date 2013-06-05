package org.universAAL.AALapplication.medication_manager.user.management;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class UserInfo {

  private int id;
  private final String uri;
  private final String name;
  private boolean presentInDatabase;


  protected UserInfo(int id, String uri, String name) {
    validate(id, uri, name);

    this.id = id;
    this.uri = uri;
    this.name = name;
  }

  private void validate(int id, String uri, String name) {
    validateParameter(id, "id");
    validateParameter(uri, "id");
    validateParameter(name, "id");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isPresentInDatabase() {
    return presentInDatabase;
  }

  public void setPresentInDatabase(boolean presentInDatabase) {
    this.presentInDatabase = presentInDatabase;
  }

  public String getUri() {
    return uri;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
        "id=" + id +
        ", uri='" + uri + '\'' +
        ", name='" + name + '\'' +
        ", presentInDatabase=" + presentInDatabase +
        '}';
  }
}
