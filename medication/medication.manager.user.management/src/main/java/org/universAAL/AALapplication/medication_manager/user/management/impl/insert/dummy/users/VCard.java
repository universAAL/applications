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

package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;


/**
 * @author George Fournadjiev
 */
public final class VCard {

  private final String userUri;
  private final String vCardVersion;
  private final Date lastRevision;
  private final String nickname;
  private final String displayName;
  private final String uciLabel;
  private final String uciAdditionalData;
  private final String aboutMe;
  private final Date bday;
  private final String fn;
  private final List<Telephone> telephones;
  private final List<Mail> emails;

  public VCard(String userUri, String vCardVersion, Date lastRevision, String nickname,
               String displayName, String uciLabel, String uciAdditionalData, String aboutMe, Date bday,
               String fn, List<Telephone> telephones, List<Mail> emails) {

    validateParameter(userUri, "userUri");
    validateParameter(telephones, "telephones");
    validateParameter(emails, "emails");

    this.userUri = userUri.toUpperCase();
    this.vCardVersion = vCardVersion;
    this.lastRevision = lastRevision;
    this.nickname = nickname;
    this.displayName = displayName;
    this.uciLabel = uciLabel;
    this.uciAdditionalData = uciAdditionalData;
    this.aboutMe = aboutMe;
    this.bday = bday;
    this.fn = fn;
    this.telephones = telephones;
    this.emails = emails;
  }

  public String getUserUri() {
    return userUri;
  }

  public String getVCardVersion() {
    return vCardVersion;
  }

  public Date getLastRevision() {
    return lastRevision;
  }

  public String getNickname() {
    return nickname;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getUciLabel() {
    return uciLabel;
  }

  public String getUciAdditionalData() {
    return uciAdditionalData;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public Date getBday() {
    return bday;
  }

  public String getFn() {
    return fn;
  }

  public List<Telephone> getTelephones() {
    return Collections.unmodifiableList(telephones);
  }

  public List<Mail> getEmails() {
    return Collections.unmodifiableList(emails);
  }

  @Override
  public String toString() {
    return "VCard{" +
        "userUri='" + userUri + '\'' +
        ", vCardVersion='" + vCardVersion + '\'' +
        ", lastRevision=" + lastRevision +
        ", nickname='" + nickname + '\'' +
        ", displayName='" + displayName + '\'' +
        ", uciLabel='" + uciLabel + '\'' +
        ", uciAdditionalData='" + uciAdditionalData + '\'' +
        ", aboutMe='" + aboutMe + '\'' +
        ", bday=" + bday +
        ", fn='" + fn + '\'' +
        ", telephones=" + telephones +
        ", emails=" + emails +
        '}';
  }
}
