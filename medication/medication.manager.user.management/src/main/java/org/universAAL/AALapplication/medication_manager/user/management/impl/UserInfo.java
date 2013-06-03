package org.universAAL.AALapplication.medication_manager.user.management.impl;

/**
 * @author George Fournadjiev
 */
public abstract class UserInfo {

  private final int id;
  private final String uri;
  private final String name;


  protected UserInfo(int id, String uri, String name) {
    this.id = id;
    this.uri = uri;
    this.name = name;
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
