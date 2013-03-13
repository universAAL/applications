package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class Person extends Entity {

  private final String name;
  private final String personUri;
  private final Role role;

  public Person(int id, String name, String personUri, Role role) {
    super(id);

    validate(name, personUri, role);

    this.name = name;
    this.personUri = personUri;
    this.role = role;
  }

  public Person(String name, String personUri, Role role) {
    this(0, name, personUri, role);
  }

  private void validate(String name, String personUri, Role role) {

    validateParameter(name, "name");
    validateParameter(personUri, "personUri");
    validateParameter(role, "role");
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
        "id=" + getId() +
        ", name='" + name + '\'' +
        ", personUri='" + personUri + '\'' +
        ", role=" + role +
        '}';
  }


}
