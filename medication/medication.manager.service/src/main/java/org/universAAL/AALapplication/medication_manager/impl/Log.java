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


package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.middleware.container.utils.LogUtils;

public class Log {


  /**
   * Helper method for logging.
   */
  public static void info(String format, Class aClass, Object... args) {
    StackTraceElement callingMethod = Thread.currentThread().getStackTrace()[2];
    LogUtils.logInfo(Activator.mc, aClass, callingMethod.getMethodName(),
        new Object[]{formatMsg(format, args)}, null);
  }

  /**
   * Helper method for logging.
   */
  public static void error(Throwable t, String format, Class aClass, Object... args) {
    StackTraceElement callingMethod = Thread.currentThread().getStackTrace()[2];
    LogUtils.logError(Activator.mc, aClass, callingMethod.getMethodName(),
        new Object[]{formatMsg(format, args)}, t);
  }

  public static String formatMsg(String format, Object... args) {
    if (args != null) {
      return String.format(format, args);
    } else {
      return format;
    }
  }
}
