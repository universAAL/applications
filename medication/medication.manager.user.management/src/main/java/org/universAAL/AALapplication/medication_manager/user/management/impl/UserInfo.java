package org.universAAL.AALapplication.medication_manager.user.management.impl;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class UserInfo {

  private final int id;
  private final String uri;
  private final String name;


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
        '}';
  }
}
