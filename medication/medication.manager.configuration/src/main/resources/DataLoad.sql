// PERSON TABLE

INSERT INTO MEDICATION_MANAGER.PERSON (ID, NAME, PERSON_URI, ROLE)
VALUES (1, 'SAID', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#SAIED', 'PATIENT');

INSERT INTO MEDICATION_MANAGER.PERSON (ID, NAME, PERSON_URI, ROLE)
VALUES (2, 'D-R PENCHO PENCHEV', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#PENCHO', 'PHYSICIAN');

INSERT INTO MEDICATION_MANAGER.PERSON (ID, NAME, PERSON_URI, ROLE)
VALUES (3, 'ALEJANDRO', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#ALEJANDRO', 'PATIENT');

INSERT INTO MEDICATION_MANAGER.PERSON (ID, NAME, PERSON_URI, ROLE)
VALUES (4, 'GEORGE', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#GEORGE', 'PATIENT');

INSERT INTO MEDICATION_MANAGER.PERSON (ID, NAME, PERSON_URI, ROLE)
VALUES (5, 'VENELIN', 'URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#VENELIN', 'PATIENT');

// MEDICINE TABLE

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION)
VALUES (1, 'ASPIRIN', 'ASPIRIN DESCRIPTION', 'NO SIDEEFFECT', 'THESE MEDICATIONS MUST NOT BE USED WITH ALCOHOL', 'WITH_MEAL');

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION)
VALUES (2, 'ANALGIN', 'ANALGIN DESCRIPTION', 'ANALGIN SIDE_EFFECTS', 'ANALGIN INCOMPLIANCES', 'ANY');

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION)
VALUES (3, 'VITERAL', 'VITERAL DESCRIPTION', 'VITERAL SIDE_EFFECTS', 'VITERAL INCOMPLIANCES', 'BEFORE');

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION)
VALUES (4, 'VALIDOL', 'VALIDOL DESCRIPTION', 'VALIDOL SIDE_EFFECTS', 'VALIDOL INCOMPLIANCES', 'ANY');

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION)
VALUES (5, 'BENALGIN', 'BENALGIN DESCRIPTION', 'BENALGIN SIDE_EFFECTS', 'BENALGIN INCOMPLIANCES', 'AFTER');

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION)
VALUES (6, 'ZOCOR', 'ZOCOR DESCRIPTION',
'SEVERE ALLERGIC REACTIONS (RASH; HIVES; ITCHING; DIFFICULTY BREATHING; TIGHTNESS IN THE CHEST;SWELLING OF THE MOUTH, FACE, LIPS, OR TONGUE; UNUSUAL HOARSENESS); BURNING, NUMBNESS, OR TINGLING;CHANGE IN THE AMOUNT OF URINE PRODUCED; CONFUSION; DARK OR RED-COLORED URINE; DECREASED SEXUAL ABILITY; DEPRESSION; DIZZINESS; FAST OR IRREGULAR HEARTBEAT; FEVER, CHILLS, OR PERSISTENT SORE THROAT; JOINT PAIN; LOSS OF APPETITE; MEMORY PROBLEMS; MUSCLE PAIN, TENDERNESS, OR WEAKNESS (WITH OR WITHOUT FEVER AND FATIGUE); PALE STOOLS; RED, SWOLLEN, BLISTERED, OR PEELING SKIN; SEVERE OR PERSISTENT NAUSEA OR STOMACH OR BACK PAIN; SHORTNESS OF BREATH; TROUBLE SLEEPING; UNUSUAL BRUISING OR BLEEDING; UNUSUAL TIREDNESS OR WEAKNESS; VOMITING;  YELLOWING OF THE SKIN OR EYES.',
'THESE MEDICATIONS MUST NOT BE USED WITH ALCOHOL', 'AFTER');

// DISPENSER TABLE

INSERT INTO MEDICATION_MANAGER.DISPENSER (ID, PATIENT_FK_ID, DISPENSER_URI) VALUES (1, 1, 'DISPENSERURI');
INSERT INTO MEDICATION_MANAGER.DISPENSER (ID, PATIENT_FK_ID, DISPENSER_URI) VALUES (2, 4, 'DISPENSERURIGEORGE');

// MEDICINE_INVENTORY TABLE

INSERT INTO MEDICATION_MANAGER.MEDICINE_INVENTORY (ID, PATIENT_FK_ID, MEDICINE_FK_ID, UNIT_CLASS, QUANTITY, WARNING_THRESHOLD)
VALUES (1, 1, 1, 'PILLS', 100, 10);

INSERT INTO MEDICATION_MANAGER.MEDICINE_INVENTORY (ID, PATIENT_FK_ID, MEDICINE_FK_ID, UNIT_CLASS, QUANTITY, WARNING_THRESHOLD)
VALUES (2, 1, 2, 'PILLS', 200, 10);

INSERT INTO MEDICATION_MANAGER.MEDICINE_INVENTORY (ID, PATIENT_FK_ID, MEDICINE_FK_ID, UNIT_CLASS, QUANTITY, WARNING_THRESHOLD)
VALUES (3, 1, 3, 'PILLS', 100, 10);

// INVENTORY_LOG TABLE

INSERT INTO MEDICATION_MANAGER.INVENTORY_LOG (ID, TIME_OF_CREATION, PATIENT_FK_ID, MEDICINE_FK_ID, CHANGE_QUANTITY, UNITS, REFERENCE)
VALUES (1, '2012-12-19 16:03:20', 1, 1, 30, 'PILLS', 'PURCHASE');

INSERT INTO MEDICATION_MANAGER.INVENTORY_LOG (ID, TIME_OF_CREATION, PATIENT_FK_ID, MEDICINE_FK_ID, CHANGE_QUANTITY, UNITS, REFERENCE)
VALUES (2, '2012-12-19 16:12:24', 1, 1, -1, 'PILLS', 'INTAKE');

// PRESCRIPTION TABLE

INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS)
VALUES (1, '2012-12-19 17:07:36', 1, 2, 'COLD', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS)
VALUES (2, '2012-12-21 17:07:36', 1, 2, 'BLOOD PRESSURE', 'ACTIVE');

// TREATMENT TABLE

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, STATUS)
VALUES (1, 1, 1, 'Y');
INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, STATUS)
VALUES (2, 1, 3, 'Y');
INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, STATUS)
VALUES (3, 1, 5, 'N');
INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, STATUS)
VALUES (4, 2, 6, 'Y');
INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, STATUS)
VALUES (5, 2, 2, 'Y');

// INTAKE TABLE

INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN)
VALUES (1, 1, 1, 'PILLS', '2012-12-19 16:10:00');
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN)
VALUES (2, 2, 2, 'PILLS', '2012-12-19 16:10:00');
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN)
VALUES (3, 3, 3, 'PILLS', '2012-12-19 16:10:00');
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN)
VALUES (4, 4, 2, 'PILLS', '2013-02-19 21:05:00');
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN)
VALUES (5, 5, 1, 'PILLS', '2013-02-19 21:05:00');



