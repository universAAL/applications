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


package org.universAAL.AALapplication.medication_manager.persistence.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerPersistenceException extends RuntimeException {

  private final int code;


  public static final int MISSING_RECORD = 1;
  public static final int NOT_ENOUGH_INVENTORY = 2;

  public MedicationManagerPersistenceException() {
    this(0);
  }

  public MedicationManagerPersistenceException(String message) {
    this(message, 0);
  }

  public MedicationManagerPersistenceException(String message, Throwable cause) {
    this(message, cause, 0);
  }

  public MedicationManagerPersistenceException(Throwable cause) {
    this(cause, 0);
  }

  public MedicationManagerPersistenceException(int code) {
    this.code = code;
  }

  public MedicationManagerPersistenceException(String message, int code) {
    super(message);

    this.code = code;
  }

  public MedicationManagerPersistenceException(String message, Throwable cause, int code) {
    super(message, cause);

    this.code = code;
  }

  public MedicationManagerPersistenceException(Throwable cause, int code) {
    super(cause);

    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
