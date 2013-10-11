// person table

insert into medication_manager.person (id, name, person_uri, role)
values (1, 'Saied', 'urn:org.universAAL.aal_space:test_environment#saied', 'patient');

insert into medication_manager.person (id, name, person_uri, role, username, password)
values (2, 'admin', 'urn:org.universAAL.aal_space:user_env#admin', 'admin', 'admin', 'admin');

// dispenser table

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (1, 'dispenser one', 'urn:org.universaal.aal_space:device_env#dispenser-1', 'instructions_1.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (2, 'dispenser two', 'urn:org.universaal.aal_space:device_env#dispenser-2', 'instructions_2.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (3, 'dispenser three', 'urn:org.universaal.aal_space:device_env#dispenser-3', 'instructions_3.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (4, 'dispenser four', 'urn:org.universaal.aal_space:device_env#dispenser-4', 'instructions_4.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (5, 'dispenser five', 'urn:org.universaal.aal_space:device_env#dispenser-5', 'instructions_5.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (6, 'dispenser six', 'urn:org.universaal.aal_space:device_env#dispenser-6', 'instructions_6.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (7, 'dispenser seven', 'urn:org.universaal.aal_space:device_env#dispenser-7', 'instructions_7.txt', true, true, true, true);

