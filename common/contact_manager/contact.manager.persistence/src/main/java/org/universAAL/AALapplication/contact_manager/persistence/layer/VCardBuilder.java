package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.Activator;
import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Database;

import java.util.Date;

/**
 * @author George Fournadjiev
 */
public final class VCardBuilder {

  private String vcardVersion;
  private Date lastRevision;
  private String nickname;
  private String displayName;
  private String uciLabel;
  private String uciAdditionalData;
  private String aboutMe;
  private Date bday;
  private String fn;

  public void buildVcardVersion(String vCardVersion) {
    this.vcardVersion = vCardVersion;
  }

  public void buildLastRevision(Date lastRevision) {
    this.lastRevision = lastRevision;
  }

  public void buildNickname(String nickname) {
    this.nickname = nickname;
  }

  public void buildDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void buildUciLabel(String uciLabel) {
    this.uciLabel = uciLabel;
  }

  public void buildUciAdditional_data(String uciAdditionalData) {
    this.uciAdditionalData = uciAdditionalData;
  }

  public void buildAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public void buildBday(Date bday) {
    this.bday = bday;
  }

  public void buildFn(String fn) {
    this.fn = fn;
  }

  public VCard buildVCard() {
    Database database = Activator.getDatabase();
    VCard vCard = new VCard(
        database.getNextIdFromIdGenerator(),
        vcardVersion,
        lastRevision,
        nickname,
        displayName,
        uciLabel,
        uciAdditionalData,
        aboutMe,
        bday,
        fn
    );


    return vCard;
  }
}
