/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.AALapplication.medication_manager.persistence.layer.UserInfo;

import java.io.File;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public interface UserManager {

  String SAIED_URI = "urn:org.universAAL.aal_space:test_environment#saied";

  void loadDummyUsersIntoChe(); //temporary method

  List<UserInfo> getAllUsers();

  void insertUserFromVCard(File propertyFile);

}
