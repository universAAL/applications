package org.universAAL.AALapplication.medication_manager.client.impl;

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