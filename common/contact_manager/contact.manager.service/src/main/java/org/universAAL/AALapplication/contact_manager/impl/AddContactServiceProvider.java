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


package org.universAAL.AALapplication.contact_manager.impl;

import org.universAAL.middleware.container.ModuleContext;


/**
 * @author George Fournadjiev
 */
public final class AddContactServiceProvider extends VCardReceivedContactServiceProvider {


  public AddContactServiceProvider(ModuleContext context) {
    super(context, AddContactService.ADD_CONTACT, AddContactService.profiles,
        ADD, AddContactService.INPUT_ADD_CONTACT);

  }


}
