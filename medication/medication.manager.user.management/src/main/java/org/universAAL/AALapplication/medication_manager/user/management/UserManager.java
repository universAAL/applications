package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.AALapplication.medication_manager.user.management.impl.UserInfo;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public interface UserManager {

  void loadDummyUsersIntoChe(); //temporary method

  List<UserInfo> getAllUsers();
}
