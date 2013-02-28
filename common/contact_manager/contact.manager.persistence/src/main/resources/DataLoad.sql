// VCARD TABLE

INSERT INTO CONTACT_MANAGER.VCARD (USER_URI, VCARD_VERSION, LAST_REVISION, NICKNAME,
DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA, ABOUT_ME, BDAY, FN)
VALUES ('URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY', '4.0', '2012-02-25 12:01:20', 'HARY', 'HARY BUSH',
'UCILABLE1', 'UCIADDITIONALDATA1','ABOUTME1', '1980-01-15 19:05:25', 'HARY JOHN BUSH');

INSERT INTO CONTACT_MANAGER.VCARD (USER_URI, VCARD_VERSION, LAST_REVISION, NICKNAME,
DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA, ABOUT_ME, BDAY, FN)
VALUES ('URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#BOND', '4.0', '2012-02-27 15:06:31', 'BOND', 'JAMES BOND',
'UCILABLE2', 'UCIADDITIONALDATA2','ABOUTME2', '1956-11-17 18:01:27', 'JAMES BOND 007');

INSERT INTO CONTACT_MANAGER.VCARD (USER_URI, VCARD_VERSION, LAST_REVISION, NICKNAME,
DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA, ABOUT_ME, BDAY, FN)
VALUES ('URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#RICK', '4.0', '2012-01-05 18:11:42', 'RICK', 'RICK JOHNSON',
'UCILABLE3', 'UCIADDITIONALDATA3', 'ABOUTME3', '1945-07-24 11:55:35', 'RICK R. JOHNSON');


// TYPES TABLE

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (1, 'TEL', 'MSG', '+359887456321', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (2, 'TEL', 'VOICE', '+359887321456', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (3, 'TEL', 'VIDEO', '+359877741258', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (4, 'TEL', 'FAX', '+359877963258', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (5, 'TEL', 'CELL', '+359876159753', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (6, 'EMAIL', 'INTERNET', 'BOND@GMAIL.COM', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#BOND');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (7, 'EMAIL', 'X400', 'JAMES@GMAIL.COM', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#BOND');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (8, 'EMAIL', 'INTERNET', 'HARY@GMAIL.COM', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)
VALUES (9, 'EMAIL', 'X400', 'BUSH@GMAIL.COM', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#HARY');

