package org.universAAL.AALapplication.medication_manager.persistence;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Person {

  private final int personId;
  private final String name;
  private final String personUri;
  private final Role role;

  public Person(int personId, String name, String personUri, Role role) {
    validate(personId, name, personUri, role);

    this.personId = personId;
    this.name = name;
    this.personUri = personUri;
    this.role = role;
  }

  private void validate(int personId, String name, String personUri, Role role) {

    validateParameter(personId, "personId");
    validateParameter(name, "name");
    validateParameter(personUri, "personUri");
    validateParameter(role, "role");
  }

  public int getPersonId() {
    return personId;
  }

  public String getName() {
    return name;
  }

  public String getPersonUri() {
    return personUri;
  }

  public Role getRole() {
    return role;
  }

  @Override
  public String toString() {
    return "Person{" +
        "personId=" + personId +
        ", name='" + name + '\'' +
        ", personUri='" + personUri + '\'' +
        ", role=" + role +
        '}';
  }


}
