// person table

insert into medication_manager.person (id, name, person_uri, role, caregiver_sms)
values (1, 'Said', 'urn:org.universaal.aal_space:test_env#saied', 'patient', '+35987852963');

insert into medication_manager.person (id, name, person_uri, role, caregiver_sms)
values (2, 'George', 'urn:org.universaal.aal_space:test_env#george', 'patient', '+35989159753');

insert into medication_manager.person (id, name, person_uri, role, caregiver_sms)
values (3, 'Venelin', 'urn:org.universaal.aal_space:test_env#venelin', 'patient', '+35988756123');

insert into medication_manager.person (id, name, person_uri, role, username, password)
values (4, 'd-r Pencho Penchev', 'urn:org.universaal.aal_space:test_env#pencho', 'physician', 'pencho', 'pass');

insert into medication_manager.person (id, name, person_uri, role, username, password)
values (5, 'd-r Ivan Ivanov', 'urn:org.universaal.aal_space:test_env#ivan', 'physician', 'ivan', '123');

// doctor_patient table

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (1, 4, 2);

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (2, 4, 3);

insert into medication_manager.doctor_patient (id, doctor_fk_id, patient_fk_id)
values (3, 5, 1);



// medicine table



// dispenser table

insert into medication_manager.dispenser (id, patient_fk_id, dispenser_uri, instructions_file_name)
values (1, 2, 'urn:org.universaal.aal_space:test_env#dispenser-1', 'instructions_1.txt');

insert into medication_manager.dispenser (id, patient_fk_id, dispenser_uri, instructions_file_name)
values (2, 3, 'urn:org.universaal.aal_space:test_env#dispenser-2', 'instructions_2.txt');

// medicine_inventory table



// inventory_log table



// prescription table



// treatment table



// intake table






