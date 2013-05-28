package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;

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

   /* User caregiver = new User(USER_SPACE + "Nikola");
    caregiver.setProperty(USER_SPACE + "username", "");
    caregiver.setProperty(USER_SPACE + "password", "");
    caregiver.setProperty(USER_SPACE + "userRole", "CAREGIVER");

    String statusText = userManager.addUser(caregiver);

    System.out.println("statusText = " + statusText);

//    addUsers(userManager);

    Set<User> userSet = userManager.getUsers();

    System.out.println("userSet = " + userSet);*/

    List <User> users = userManager.getAllUsers();

    if (users == null) {
      throw new MedicationManagerUserManagementException("Null result");
    }

    for (User user : users) {
      System.out.println("user.getURI() = " + user.getURI());
      String userProfile = userManager.getUserProfile(user);
      System.out.println("userProfile = " + userProfile);
      List usersubprofiles = userManager.getUserSubprofiles(new UserProfile(userProfile));
      System.out.println("usersubprofiles = " + usersubprofiles);

      User us = userManager.getUser(user.getURI());
      System.out.println("us.getURI() = " + us.getURI());

    }





  }

  private void addUsers(UserManagerImpl userManager) {
    User enduser = new User(USER_SPACE + "User");
    enduser.setProperty(USER_SPACE + "username", "");
    enduser.setProperty(USER_SPACE + "password", "");
    //     enduser.setProperty(USER_SPACE+"confirmpassword", "");
    enduser.setProperty(USER_SPACE + "userRole", new String("ENDUSER"));

    User deployer = new User(USER_SPACE + "Deployer");
    deployer.setProperty(USER_SPACE + "username", "");
    deployer.setProperty(USER_SPACE + "password", "");
    //     deployer.setProperty(USER_SPACE+"confirmpassword", "");
    deployer.setProperty(USER_SPACE + "userRole", "DEPLOYER");

    User tec = new User(USER_SPACE + "Technician");
    tec.setProperty(USER_SPACE + "username", "");
    tec.setProperty(USER_SPACE + "password", "");
    //     tec.setProperty(USER_SPACE+"confirmpassword", "");
    tec.setProperty(USER_SPACE + "userRole", "TECHNICIAN");

    User care = new User(USER_SPACE + "Caregiver");
    care.setProperty(USER_SPACE + "username", "");
    care.setProperty(USER_SPACE + "password", "");
    //     care.setProperty(USER_SPACE+"confirmpassword", "");
    care.setProperty(USER_SPACE + "userRole", "CAREGIVER");

    User assisted = new User(USER_SPACE + "AssistedPerson");
    assisted.setProperty(USER_SPACE + "username", "");
    assisted.setProperty(USER_SPACE + "password", "");
    //     assisted.setProperty(USER_SPACE+"confirmpassword", "");
    assisted.setProperty(USER_SPACE + "userRole", "ASSISTEDPERSON");

    userManager.addUser(enduser);
    userManager.addUser(deployer);
    userManager.addUser(tec);
    userManager.addUser(care);
    userManager.addUser(assisted);
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
