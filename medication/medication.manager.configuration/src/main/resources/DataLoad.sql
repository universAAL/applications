// person table

insert into medication_manager.person (id, name, person_uri, role)
values (1, 'Said', 'urn:org.universaal.aal_space:test_env#saied', 'patient');

insert into medication_manager.person (id, name, person_uri, role, username, password)
values (2, 'd-r Pencho Penchev', 'urn:org.universaal.aal_space:test_env#pencho', 'physician', 'pencho', 'pass');

insert into medication_manager.person (id, name, person_uri, role)
values (3, 'Alejandro', 'urn:org.universaal.aal_space:test_env#alejandro', 'patient');

insert into medication_manager.person (id, name, person_uri, role)
values (4, 'George', 'urn:org.universaal.aal_space:test_env#george', 'patient');

insert into medication_manager.person (id, name, person_uri, role)
values (5, 'Venelin', 'urn:org.universaal.aal_space:test_env#venelin', 'patient');

insert into medication_manager.person (id, name, person_uri, role, username, password)
values (6, 'd-r Ivan Ivanov', 'urn:org.universaal.aal_space:test_env#ivan', 'physician', 'ivan', 'PaSS');

// doctor_patient table

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (1, 2, 1);

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (2, 2, 4);

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (3, 2, 5);

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (4, 6, 3);




// medicine table

insert into medication_manager.medicine (id, medicine_name, medicine_info, side_effects, incompliances, meal_relation)
values (1, 'Aspirin', 'aspirin description', 'no sideeffect', 'these medications must not be used with alcohol', 'with_meal');

insert into medication_manager.medicine (id, medicine_name, medicine_info, side_effects, incompliances, meal_relation)
values (2, 'Analgin', 'analgin description', 'analgin side_effects', 'analgin incompliances', 'any');

insert into medication_manager.medicine (id, medicine_name, medicine_info, side_effects, incompliances, meal_relation)
values (3, 'Viteral', 'viteral description', 'viteral side_effects', 'viteral incompliances', 'before');

insert into medication_manager.medicine (id, medicine_name, medicine_info, side_effects, incompliances, meal_relation)
values (4, 'Validol', 'validol description', 'validol side_effects', 'validol incompliances', 'any');

insert into medication_manager.medicine (id, medicine_name, medicine_info, side_effects, incompliances, meal_relation)
values (5, 'Benalgin', 'benalgin description', 'benalgin side_effects', 'benalgin incompliances', 'after');

insert into medication_manager.medicine (id, medicine_name, medicine_info, side_effects, incompliances, meal_relation)
values (6, 'Zocor', 'zocor description',
'severe allergic reactions (rash; hives; itching; difficulty breathing; tightness in the chest;swelling of the mouth, face, lips, or tongue; unusual hoarseness); burning, numbness, or tingling;change in the amount of urine produced; confusion; dark or red-colored urine; decreased sexual ability; depression; dizziness; fast or irregular heartbeat; fever, chills, or persistent sore throat; joint pain; loss of appetite; memory problems; muscle pain, tenderness, or weakness (with or without fever and fatigue); pale stools; red, swollen, blistered, or peeling skin; severe or persistent nausea or stomach or back pain; shortness of breath; trouble sleeping; unusual bruising or bleeding; unusual tiredness or weakness; vomiting;  yellowing of the skin or eyes.',
'these medications must not be used with alcohol', 'after');

// dispenser table

insert into medication_manager.dispenser (id, patient_fk_id, dispenser_uri) values (1, 1, 'dispenseruri');

insert into medication_manager.dispenser (id, patient_fk_id, dispenser_uri) values (2, 4, 'dispenserurigeorge');

// medicine_inventory table

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (1, 1, 1, 'pill', 100, 10);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (2, 1, 2, 'pill', 200, 10);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (3, 1, 3, 'pill', 100, 10);

// inventory_log table

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (1, '2012-12-19 16:03:20', 1, 1, 30, 'pill', 'purchase');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (2, '2012-12-19 16:12:24', 1, 1, -1, 'pill', 'intake');

// prescription table

insert into medication_manager.prescription (id, time_of_creation, patient_fk_id, physician_fk_id, description, status)
values (1, '2012-12-19 17:07:36', 4, 2, 'cold', 'active');

insert into medication_manager.prescription (id, time_of_creation, patient_fk_id, physician_fk_id, description, status)
values (2, '2012-12-21 17:07:36', 1, 2, 'blood pressure', 'active');

insert into medication_manager.prescription (id, time_of_creation, patient_fk_id, physician_fk_id, description, status)
values (3, '2012-12-24 15:00:00', 3, 6, 'sleeping problems', 'active');

insert into medication_manager.prescription (id, time_of_creation, patient_fk_id, physician_fk_id, description, status)
values (4, '2013-03-25 17:00:00', 1, 2, 'pain', 'active');

// treatment table

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (1, 1, 1, 'y', '2013-04-02 21:00:00', '2013-04-09 20:00:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (2, 1, 3, 'y', '2013-04-02 21:00:00', '2013-04-09 20:00:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (3, 1, 5, 'n', '2012-12-19 00:00:00', '2012-12-19 23:59:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (4, 2, 6, 'y', '2013-02-19 00:00:00', '2013-02-19 23:59:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (5, 2, 2, 'y', '2013-02-19 00:00:00', '2013-02-19 23:59:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (6, 3, 4, 'y', '2013-03-01 00:00:00', '2013-03-19 23:59:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (7, 4, 5, 'y', '2013-03-25 00:00:00', '2013-04-11 21:00:00');

insert into medication_manager.treatment (id, prescription_fk_id, medicine_fk_id, status, start_date, end_date)
values (8, 1, 2, 'y', '2013-04-02 21:00:00', '2013-04-09 20:00:00');

// intake table

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (1, 1, 1, 'pill', '2013-04-02 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (2, 2, 2, 'pill', '2012-12-19 16:10:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (3, 3, 3, 'pill', '2012-12-19 16:10:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (4, 4, 2, 'pill', '2013-02-19 21:05:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (5, 5, 1, 'pill', '2013-02-19 21:05:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (6, 6, 3, 'pill', '2013-04-19 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (7, 7, 2, 'pill', '2013-04-01 20:10:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (8, 1, 1, 'pill', '2013-04-03 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (9, 1, 1, 'pill', '2013-04-03 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (10, 1, 1, 'pill', '2013-04-03 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (11, 1, 1, 'pill', '2013-04-04 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (12, 1, 1, 'pill', '2013-04-04 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (13, 1, 1, 'pill', '2013-04-04 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (14, 1, 1, 'pill', '2013-04-05 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (15, 1, 1, 'pill', '2013-04-05 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (16, 1, 1, 'pill', '2013-04-05 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (17, 1, 1, 'pill', '2013-04-06 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (18, 1, 1, 'pill', '2013-04-06 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (19, 1, 1, 'pill', '2013-04-06 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (20, 1, 1, 'pill', '2013-04-07 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (21, 1, 1, 'pill', '2013-04-07 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (22, 1, 1, 'pill', '2013-04-07 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (23, 1, 1, 'pill', '2013-04-08 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (24, 1, 1, 'pill', '2013-04-08 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (25, 1, 1, 'pill', '2013-04-08 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (26, 1, 1, 'pill', '2013-04-09 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (27, 1, 1, 'pill', '2013-04-09 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (28, 1, 1, 'pill', '2013-04-09 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (29, 1, 1, 'pill', '2013-04-10 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (30, 8, 3, 'pill', '2013-04-02 22:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (31, 8, 2, 'pill', '2013-04-03 09:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (32, 8, 3, 'pill', '2013-04-03 16:00:00');

insert into medication_manager.intake (id, treatment_fk_id, quantity, units, time_plan)
values (33, 8, 4, 'pill', '2013-04-03 22:00:00');




