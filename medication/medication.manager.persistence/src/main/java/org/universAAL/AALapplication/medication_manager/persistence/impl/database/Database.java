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

package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.persistence.layer.SqlUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public interface Database {

  //This method is used only for the purposes of console shell commands and must be removed in the production
    SqlUtility getSqlUtility();

  void initDatabase() throws Exception;

  int getNextIdFromIdGenerator();

  Map<String, Column> getById(String tableName, int id);

  Map<String, Column> findDispenserByPerson(String tableName, String personTableName, int personId);

  Map<String,Column> executeQueryExpectedSingleRecord(String tableName, String sql);

  Map<String,Column> executeQueryExpectedSingleRecord(String tableName, PreparedStatement ps);

  List<Map<String,Column>> executeQueryExpectedMultipleRecord(String tableName, PreparedStatement statement);

  Connection getConnection();
}
