package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.ontology.profile.User;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public interface UserManager {

  void addUser(User user);

  List<User> getAllUsers();
}
