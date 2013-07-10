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
  private final String username;
  private final String password;
  private final String caregiverSms;

  public Person(int id, String name, String personUri, Role role,
                String username, String password, String caregiverSms) {

    super(id);

    validate(name, personUri, role);

    this.name = name;
    this.personUri = personUri;
    this.role = role;
    this.username = username;
    this.password = password;
    this.caregiverSms = caregiverSms;
  }

  public Person(String name, String personUri, Role role) {
    this(0, name, personUri, role, null, null, null);

  }

  public Person(int id, String name, String personUri, Role role) {
    this(id, name, personUri, role, null, null, null);

  }

  public Person(String name, String personUri, Role role, String username, String password, String caregiverSms) {
    this(0, name, personUri, role, username, password, caregiverSms);
  }

  public Person(String name, String personUri, Role role, String caregiverSms) {
    this(0, name, personUri, role, null, null, caregiverSms);
  }

  public Person(int id, String name, String personUri, Role role, String caregiverSms) {
    this(id, name, personUri, role, null, null, caregiverSms);
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

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getCaregiverSms() {
    return caregiverSms;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", personUri='" + personUri + '\'' +
        ", role=" + role +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", caregiverSms='" + caregiverSms + '\'' +
        '}';
  }
}