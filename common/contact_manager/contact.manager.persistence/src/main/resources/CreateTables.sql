CREATE TABLE CONTACT_MANAGER.VCARD (
  ID                      INT NOT NULL,
  VCARD_VERSION           VARCHAR(40),
  LAST_REVISION           TIMESTAMP,
  NICKNAME                VARCHAR(50),
  DISPLAY_NAME            VARCHAR(100),
  UCI_LABEL               CLOB,
  UCI_ADDITIONAL_DATA     CLOB,
  ABOUT_ME                CLOB,
  BDAY                    TIMESTAMP,
  FN                      VARCHAR(144),
  USER_URI                VARCHAR(144) NOT NULL,
  CONSTRAINT PERSON_PK PRIMARY KEY (ID)
);


