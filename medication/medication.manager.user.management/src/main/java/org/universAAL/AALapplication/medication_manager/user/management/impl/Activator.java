package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.user.management.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  public static ModuleContext mc;
  public static BundleContext bundleContext;


  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    PersistentService persistentService = getPersistentService();

    UserManager userManager = new UserManagerImpl(mc, persistentService);


//    userManager.loadDummyUsersIntoChe();

    printUsers(userManager, persistentService);


  }

  private void printUsers(UserManager userManager, PersistentService persistentService) {
    List<UserInfo> users = userManager.getAllUsers();

    for (UserInfo user : users) {
      System.out.println("\n******** user *****************");
      String uri = user.getUri();
      System.out.println("user.getURI() = " + uri);
      if (user.getClass().equals(AssistedPersonUserInfo.class)) {
        System.out.println("The user is a AssistedPerson");
      } else if (user.getClass().equals(CaregiverUserInfo.class)) {
        System.out.println("The user is a Caregiver");
      }

      PersonDao personDao = persistentService.getPersonDao();
      Person person = personDao.getPersonByPersonUri(uri);
      boolean presentInDatabase = person != null;

      System.out.println("presentInDatabase = " + presentInDatabase);

      System.out.println("\n******** end *****************");

    }
  }

  public void stop(BundleContext context) throws Exception {

  }

  public static PersistentService getPersistentService() {
    if (bundleContext == null) {
      throw new MedicationManagerUserManagementException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerUserManagementException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) bundleContext.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerUserManagementException("The PersistentService is missing");
    }
    return persistentService;
  }


}
