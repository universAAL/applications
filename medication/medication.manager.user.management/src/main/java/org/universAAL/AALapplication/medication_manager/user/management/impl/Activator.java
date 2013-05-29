package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Caregiver;
import org.universAAL.ontology.profile.User;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  public static ModuleContext mc;

  public static final String USER_SPACE = "urn:org.universAAL.aal_space:user_env#";
  public static final String HOME_SPACE = "urn:org.universAAL.aal.space:home_env#my_home_space";

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});


    UserManagerImpl userManager = new UserManagerImpl(mc);


    addUsers(userManager);

    List<User> users = userManager.getAllUsers();

    if (users == null) {
      throw new MedicationManagerUserManagementException("Null result");
    }

    for (User user : users) {
      System.out.println("user.getURI() = " + user.getURI());
      if (user.getClass().equals(AssistedPerson.class)) {
        System.out.println("The user is a AssistedPerson");
      } else if (user.getClass().equals(Caregiver.class)) {
        System.out.println("The user is a Caregiver");
      }

    }


  }

  private void addUsers(UserManagerImpl userManager) {

    Caregiver caregiver = new Caregiver(USER_SPACE + "Said");

    AssistedPerson assistedPerson = new AssistedPerson(USER_SPACE + "Venelin");

    userManager.addUser(caregiver);
    userManager.addUser(assistedPerson);

  }

  public void stop(BundleContext context) throws Exception {

  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerUserManagementException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerUserManagementException("The parameter : " + parameterName + " cannot be null");
    }

  }

}
