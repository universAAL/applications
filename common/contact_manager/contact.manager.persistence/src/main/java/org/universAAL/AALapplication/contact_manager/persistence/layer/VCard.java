package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Entity;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class VCard extends Entity {

  private final String vcardVersion;
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

  public VCard(int id, String vcardVersion, Date lastRevision, String nickname, String displayName, String uciLabel,
               String uciAdditionalData, String aboutMe, Date bday,
               String fn, List<Telephone> telephones, List<Mail> emails) {

    super(id);

    this.vcardVersion = vcardVersion;
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

  public VCard(String vcardVersion, Date lastRevision, String nickname, String displayName, String uciLabel,
               String uciAdditionalData, String aboutMe, Date bday,
               String fn, List<Telephone> telephones, List<Mail> emails) {

    this(0, vcardVersion, lastRevision, nickname, displayName, uciLabel,
        uciAdditionalData, aboutMe, bday, fn, telephones, emails);

  }

  public String getVcardVersion() {
    return vcardVersion;
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
}
