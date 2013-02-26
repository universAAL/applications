package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.Activator;
import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;
import org.universAAL.AALapplication.contact_manager.persistence.impl.database.Database;
import org.universAAL.ontology.vcard.Cell;
import org.universAAL.ontology.vcard.Fax;
import org.universAAL.ontology.vcard.Msg;
import org.universAAL.ontology.vcard.Tel;
import org.universAAL.ontology.vcard.Video;
import org.universAAL.ontology.vcard.Voice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.universAAL.AALapplication.contact_manager.persistence.layer.TelEnum.*;

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
  private List<Telephone> telephones;

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

  public void buildTelephones(List<Tel> telephones) {
    this.telephones = getTelephones(telephones);
  }

  private List<Telephone> getTelephones(List<Tel> telephones) {
    if (telephones == null || telephones.isEmpty()) {
      return new ArrayList<Telephone>();
    }

    List<Telephone> telephoneList = new ArrayList<Telephone>();

    for (Tel tel : telephones) {
      Telephone telephone = createTelephone(tel);
      telephoneList.add(telephone);
    }

    return telephoneList;
  }

  private Telephone createTelephone(Tel tel) {

    TelEnum telEnum;

    if (tel instanceof Cell) {
      telEnum = CELL;
    } else if (tel instanceof Voice) {
      telEnum = VOICE;
    } else if (tel instanceof Msg) {
      telEnum = MSG;
    } else if (tel instanceof Video) {
      telEnum = VIDEO;
    } else if (tel instanceof Fax) {
      telEnum = FAX;
    } else {
      throw new ContactManagerPersistenceException("Unsupported version of the Tel subclass : " + tel.getClass());
    }

    String value = (String) tel.getProperty(Tel.PROP_VALUE);
    return new Telephone(value, telEnum);
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
        fn,
        telephones
    );


    return vCard;
  }
}
