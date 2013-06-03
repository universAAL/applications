package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
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


  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});


    UserManager userManager = new UserManagerImpl(mc);


//    userManager.loadDummyUsersIntoChe();

    printUsers(userManager);


  }

  private void printUsers(UserManager userManager) {
    List<User> users = userManager.getAllUsers();

    for (User user : users) {
      System.out.println("user.getURI() = " + user.getURI());
      if (user.getClass().equals(AssistedPerson.class)) {
        System.out.println("The user is a AssistedPerson");
      } else if (user.getClass().equals(Caregiver.class)) {
        System.out.println("The user is a Caregiver");
      }

    }
  }

  public void stop(BundleContext context) throws Exception {

  }


}
