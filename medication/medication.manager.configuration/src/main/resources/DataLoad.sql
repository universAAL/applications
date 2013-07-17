// person table

insert into medication_manager.person (id, name, person_uri, role)
values (1, 'Said', 'urn:org.universAAL.aal_space:user_env#said', 'patient');

insert into medication_manager.person (id, name, person_uri, role)
values (2, 'George', 'urn:org.universAAL.aal_space:user_env#george', 'patient');

insert into medication_manager.person (id, name, person_uri, role)
values (3, 'Venelin', 'urn:org.universAAL.aal_space:user_env#venelin', 'patient');

insert into medication_manager.person (id, name, person_uri, role, username, password, caregiver_sms)
values (4, 'd-r Pencho Penchev', 'urn:org.universAAL.aal_space:user_env#pencho', 'physician', 'pencho', 'pass', '+3598123987');

insert into medication_manager.person (id, name, person_uri, role, username, password, caregiver_sms)
values (5, 'd-r Ivan Ivanov', 'urn:org.universAAL.aal_space:user_env#ivan', 'physician', 'ivan', 'pass', '+3598654987');

insert into medication_manager.person (id, name, person_uri, role, username, password, caregiver_sms)
values (6, 'Simeon', 'urn:org.universAAL.aal_space:user_env#simeon', 'caregiver', 'simeon', 'pass', '+3598654123');

insert into medication_manager.person (id, name, person_uri, role, username, password, caregiver_sms)
values (7, 'Asparuh', 'urn:org.universAAL.aal_space:user_env#asparuh', 'caregiver', 'asparuh', 'pass', '+3598654765');

insert into medication_manager.person (id, name, person_uri, role, username, password, caregiver_sms)
values (8, 'Ivailo', 'urn:org.universAAL.aal_space:user_env#ivailo', 'caregiver', 'ivailo', 'pass', '+3598654159');

insert into medication_manager.person (id, name, person_uri, role, username, password)
values (9, 'admin', 'urn:org.universAAL.aal_space:user_env#admin', 'admin', 'admin', 'admin');

// patient_links table

insert into medication_manager.patient_links (id, doctor_fk_id, patient_fk_id, caregiver_fk_id)
values (1, 4, 2, 6);

insert into medication_manager.patient_links (id, doctor_fk_id, patient_fk_id, caregiver_fk_id)
values (2, 4, 3, 7);

insert into medication_manager.patient_links (id, doctor_fk_id, patient_fk_id, caregiver_fk_id)
values (3, 5, 1, 8);

// medicine table

INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1007, 'Analgin', 'Analgin is a medicine of the pyrazolone group, possessing hard analgesic and antipyretic effects and moderate antiinflammatory activity. Blocking of the synthesis of endogenous pyrogens - prostaglandins D and E - is the cause for the antipyretic activity and also for the analgesic action of this drug. The decrease of the prostaglandis production in the periphery (and respective decrease of nerve endings sensitivity) plays a relatively smaller role. In contrast to the other nonnarcotic analgesic drugs, Analgin stimulates the release of ?-endorphins, explaining its activity in cases of visceral pain. Analgin has a slight spasmolytic activity on the smooth muscle cells of the biliary and urinary tracts and also on the muscle of the uterus.', 'The following are some of the side effects that are known to be associated with this medicine. Because a side effect is stated here, it does not mean that all people using this medicine will experience that or any side effect.\nAfter prolonged administration very rarely can be observed agranulocytosis, leucopenia and thrombocytopenia, proteinuria, interstitial nephritis. In sensitive patients rashes, urticaria, Quincke\''s edema, asthmatic attacks, and very rarely anaphylactic shock are possible.\nThe side effects listed above may not include all of the side effects reported by the drug\''s manufacturer.', 'Tell your doctor or pharmacist what medicines you are already taking, including those bought without a prescription and herbal medicines, before you start treatment with this medicine.\nAnalgin potentiates the analgesic and antipyretic action of the nonsteroidal antiinflammatory drugs. The Analgin effects are potentiated by the tricyclic antidepressants, oral contraceptive agents, allopurinol, alcohol. Analgin decreases the activity of the coumarin anticoagulants and plasma levels of cyclosporin. The enzyme inductors (barbiturates, glutethimide, phenylbutazone) attenuate the Analgin effects. Analgin potentiates the effects of the drugs possessing CNS depressant activity. Concurrent administration with chlorpromazine is related with a risk of severe hypothermia. Analgin can be used in combination with buscolysin, atropin, codein.', 'AFTER', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1008, 'Aspirin', 'Aspirin-also known as acetylsalicylic acid-is sold over the counter and comes in many forms, from the familiar white tablets to chewing gum and rectal suppositories. Coated, chewable, buffered, and extended release forms are available. Many other over-the-counter medicine contain aspirin. Alka-Seltzer Original Effervescent Antacid Pain Reliever, for example, contains aspirin for pain relief and sodium bicarbonate to relieve acid indigestion, heartburn, and sour stomach.\nAspirin belongs to a group of drugs called salicylates. Other members of this group include sodium salicylate, choline salicylate, and magnesium salicylate. These drugs are more expensive and no more effective than aspirin. However, they are a little easier on the stomach. Aspirin is quickly absorbed into the bloodstream and provides quick and relatively long-lasting pain relief. Aspirin also reduces inflammation. Researchers believe these effects come about because aspirin blocks the production of pain-producing chemicals called prostaglandins.\nIn addition to relieving pain and reducing inflammation, aspirin also lowers fever by acting on the part of the brain that regulates temperature. The brain then signals the blood vessels to widen, which allows heat to leave the body more quickly.', 'The most common side effects include stomachache, heartburn, loss of appetite, and small amounts of blood in stools. Less common side effects are rashes, hives, fever, vision problems, liver damage, thirst, stomach ulcers, and bleeding. People who are allergic to aspirin or those who have asthma, rhinitis, or polyps in the nose may have trouble breathing after taking aspirin.', 'Aspirin-even children\''s aspirin-should never be given to children or teenagers with flu-like symptoms or chickenpox. Aspirin can cause Reye\''s syndrome, a life-threatening condition that affects the nervous system and liver. As many as 30% of children and teenagers who develop Reye\''s syndrome die. Those who survive may have permanent brain damage.\nCheck with a physician before giving aspirin to a child under 12 years for arthritis, rheumatism, or any condition that requires long-term use of the drug.\nNo one should take aspirin for more than 10 days in a row unless told to do so by a physician. Anyone with fever should not take aspirin for more than 3 days without a physician\''s consent. Do not to take more than the recommended daily dosage.', 'AFTER', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1046, 'Centrum', 'Centrum Multivitamins are a popular choice for users looking for a good all-around multivitamin with moderate potencies. Many doctor\''s have recommended a Centrum-family vitamin to their patients, perhaps because doctors already know and trust the pharmaceutical company, Wyeth, that makes and markets Centrum vitamins. What\''s in Centrum? Centrum\''s not a high potency multi, so for a one-a-day vitamin, it\''s able to give you a pretty broad range of your basic vitamins and minerals. Wyeth has updated the Centrum formulas several times by including new beneficial-but-not-essential nutrients like lycopene and lutein. For best absorption and zero stomach discomfort, take Centrum like you would any other multi - with solid food, never on an empty stomach.', 'Constipation, diarrhea, or upset stomach may occur. These effects are usually temporary and may disappear as your body adjusts to this medication. If any of these effects persist or worsen, contact your doctor or pharmacist promptly.\n\nIron may cause your stools to turn black, an effect that is not harmful.\n\nA very serious allergic reaction to this drug is rare. However, seek immediate medical attention if you notice any of the following symptoms of a serious allergic reaction:\n\nrash\nitching/swelling (especially of the face/tongue/throat)\nsevere dizziness\ntrouble breathing\nThis is not a complete list of possible side effects. If you notice other effects not listed above, contact your doctor or pharmacist.', 'Before taking this product, tell your doctor or pharmacist if you are allergic to any of its ingredients; or if you have any other allergies. This product may contain inactive ingredients, which can cause allergic reactions or other problems. Talk to your pharmacist for more details.\n\nThis medication should not be used if you have certain medical conditions. Before using this medication, consult your doctor or pharmacist if you have:\n\niron overload disorder (e.g., hemochromatosis, hemosiderosis)\nBefore taking this medication, tell your doctor or pharmacist your medical history, especially of:\n\nuse/abuse of alcohol\nliver problems\nstomach/intestinal problems (e.g., ulcer, colitis)\nIf your brand of multivitamin also contains folic acid, be sure to tell your doctor or pharmacist if you have vitamin B12 deficiency (pernicious anemia) before taking it. Folic acid may affect certain laboratory tests for vitamin B12 deficiency without treating this anemia. Untreated vitamin B12 deficiency may result in serious nerve problems (e.g., peripheral neuropathy). Consult your doctor or pharmacist for details.\n\nThis medicine may contain aspartame. If you have phenylketonuria (PKU) or any other condition that requires you to restrict your intake of aspartame (or phenylalanine), consult your doctor or pharmacist about using this drug safely.\n\nTell your doctor if you are pregnant before using this medication.\n\nThis medication passes into breast milk. Consult your doctor before breast-feeding.', 'WITH_MEAL', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1070, 'Milgama N', 'Milgamma is a vitamin b complex drug, composed vitamin B1 (either as thiamine or its derivative, benfotiamine), vitamin B6 (Pyridoxine) and vitamin B12 (cyanocobalamin). It is used as a replenisher, to treat neurological symptoms associated with the deficiencies of these B vitamins as well as neuropathy associated with different disease states such as diabetes mellitus. Neuritis & neuralgia esp cervical syndrome, shoulder-arm syndrome, lumbago, sciatica, root irritation due to degenerative changes of the vertebral column, herpes zoster. Follow-up treatment of trigeminal neuralgia; supportive treatment in facial paresis. Deficiency or raised requirement of vit B1, B6', 'mild anaphylactic reactions , specially after local injection, urine dark colouration, development of peripheral neuropathy in lng term use.', 'May enhance the effect of neuromuscular blocking agents.May enhance the effect of neuromuscular blocking agents.', 'AFTER', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1071, 'Doppelherz® aktiv Magnesium + Calcium + D3', 'An optimum supply with vitamins and minerals is an important prerequisite for your well-being and for the fitness of your body. \nMagnesium and Calcium are two essential minerals which we need for activating and maintaining numerous biological processes in the body. The “calcium integration helper" Vitamin D3 controls Calcium uptake and enhances the integration of Calcium in the bones.', 'Constipation or stomach upset may occur. If any of these effects persist or worsen, tell your doctor or pharmacist promptly.\nIf your doctor has directed you to use this medication, remember that he or she has judged that the benefit to you is greater than the risk of side effects. Many people using this medication do not have serious side effects.\nTell your doctor immediately if any of these unlikely but serious side effects occur: nausea/vomiting, loss of appetite, unusual weight loss, mental/mood changes, change in the amount of urine, bone/muscle pain, headache, increased thirst, increased urination, weakness, tiredness, fast/pounding heartbeat.\nA very serious allergic reaction to this drug is rare. However, seek immediate medical attention if you notice any symptoms of a serious allergic reaction, including: rash, itching/swelling (especially of the face/tongue/throat), severe dizziness, trouble breathing.\nThis is not a complete list of possible side effects. If you notice other effects not listed above, contact your doctor or pharmacist.', 'Before taking this medication, tell your doctor if you have any allergies. This product may contain inactive ingredients, which can cause allergic reactions or other problems. Talk to your pharmacist for more details.\nThis medication should not be used if you have certain medical conditions. Before using this medicine, consult your doctor or pharmacist if you have: high calcium/vitamin D levels (hypercalcemia/hypervitaminosis D), difficulty absorbing nutrition from food (malabsorption syndrome).\nBefore using this medication, tell your doctor or pharmacist your medical history, especially of: heart/blood vessel disease, kidney stones, kidney disease, certain immune system disorder (sarcoidosis), liver disease, certain bowel diseases (Crohn\''s disease, Whipple\''s disease), little or no stomach acid (achlorhydria), low levels of bile, untreated phosphate imbalance.\nChewable tablets may contain sugar or aspartame. Caution is advised if you have diabetes, phenylketonuria (PKU), or any other condition that requires you to limit/avoid these substances in your diet. Ask your doctor or pharmacist about using this product safely.\nTell your doctor if you are pregnant before using this medication. During pregnancy, doses of vitamin D greater than the recommended dietary allowance should be used only when clearly needed. Discuss the risks and benefits with your doctor.\nThis medication passes into breast milk. Consult your doctor before breast-feeding.', 'AFTER', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1149, 'Thalitone', ' CHLORTHALIDONE is a diuretic. It increases the amount of urine passed, which causes the body to lose salt and water. This medicine is used to treat high blood pressure and edema or water retention.\n\nThis medicine may be used for other purposes; ask your health care provider or pharmacist if you have questions.\n\nYour health care provider needs to know if you have any of these conditions:\n•asthma\n•diabetes\n•gout\n•kidney disease\n•liver disease\n•parathyroid disease\n•systemic lupus erythematosus (SLE)\n•taking cortisone, digoxin, lithium carbonate, or drugs for diabetes\n•an unusual or allergic reaction to chlorthalidone, sulfa drugs, other medicines, foods, dyes, or preservatives\n•pregnant or trying to get pregnant\n•breast-feeding', 'Side effects that you should report to your doctor or health care professional as soon as possible:\n•allergic reactions like skin rash, itching or hives, swelling of the face, lips, or tongue\n•dark urine\n•dry mouth\n•excess thirst\n•fast, irregular heart rate\n•fever, chills\n•muscle pain, cramps, or spasm\n•nausea, vomiting\n•redness, blistering, peeling or loosening of the skin, including inside the mouth\n•tingling, pain or numbness in the hands or feet\n•unusually weak or tired\n•yellowing of the eyes or skin\n\nSide effects that usually do not require medical attention (report to your doctor or health care professional if they continue or are bothersome):\n•diarrhea or constipation\n•headache\n•impotence\n•loss of appetite\n•stomach upset\n\nThis list may not describe all possible side effects. Call your doctor for medical advice about side effects. You may report side effects to FDA at 1-800-FDA-1088.\n\nVisit your doctor or health care professional for regular check ups. Check your blood pressure as directed. Ask your doctor or health care professional what your blood pressure should be and when you should contact him or her.\n\nYou may need to be on a special diet while taking this medicine. Ask your doctor.\n\nYou may get drowsy or dizzy. Do not drive, use machinery, or do anything that needs mental alertness until you know how this medicine affects you. Do not stand or sit up quickly, especially if you are an older patient. This reduces the risk of dizzy or fainting spells. Alcohol may interfere with the effect of this medicine. Avoid alcoholic drinks.\n\nThis medicine may affect your blood sugar level. If you have diabetes, check with your doctor or health care professional before changing the dose of your diabetic medicine.\n\nThis medicine can make you more sensitive to the sun. Keep out of the sun. If you cannot avoid being in the sun, wear protective clothing and use sunscreen. Do not use sun lamps or tanning beds/booths.', 'The following drugs may interact with Thalitone®:\n•barbiturate medicines for sleep or seizure control\n•digoxin\n•lithium\n•medicines for diabetes\n•norepinephrine\n•other medicines for high blood pressure\n•some pain medicines\n•steroid hormones like prednisone, cortisone, hydrocortisone, corticotropin\n•tubocurarine\n\nThis list may not describe all possible interactions. Give your health care provider a list of all the medicines, herbs, non-prescription drugs, or dietary supplements you use. Also tell them if you smoke, drink alcohol, or use illegal drugs. Some items may interact with your medicine.', 'AFTER', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1150, 'Diamox', 'ACETAZOLAMIDE is used to treat glaucoma. It is also used to treat and to prevent altitude or mountain sickness.\n\nThis medicine may be used for other purposes; ask your health care provider or pharmacist if you have questions.\n\nYour health care provider needs to know if you have any of these conditions:\n•diabetes\n•kidney disease\n•liver disease\n•lung disease\n•an unusual or allergic reaction to acetazolamide, sulfa drugs, other medicines, foods, dyes, or preservatives\n•pregnant or trying to get pregnant\n•breast-feeding', 'Side effects that you should report to your doctor or health care professional as soon as possible:\n•allergic reactions like skin rash, itching or hives, swelling of the face, lips, or tongue\n•breathing problems\n•confusion, depression\n•dark urine\n•fever\n•numbness, tingling in hands or feet\n•redness, blistering, peeling or loosening of the skin, including inside the mouth\n•ringing in the ears\n•seizure\n•unusually weak or tired\n•yellowing of the eyes or skin\n\nSide effects that usually do not require medical attention (report to your doctor or health care professional if they continue or are bothersome):\n•change in taste\n•diarrhea\n•headache\n•loss of appetite\n•nausea, vomiting\n•passing urine more often\n\nThis list may not describe all possible side effects. Call your doctor for medical advice about side effects. You may report side effects to FDA at 1-800-FDA-1088.\n\nVisit your doctor or health care professional for regular checks on your progress. You will need blood work done regularly. If you are diabetic, check your blood sugar as directed.\n\nYou may need to be on a special diet while taking this medicine. Ask your doctor. Also, ask how many glasses of fluid you need to drink a day. You must not get dehydrated.\n\nYou may get drowsy or dizzy. Do not drive, use machinery, or do anything that needs mental alertness until you know how this medicine affects you. Do not stand or sit up quickly, especially if you are an older patient. This reduces the risk of dizzy or fainting spells.\n\nThis medicine can make you more sensitive to the sun. Keep out of the sun. If you cannot avoid being in the sun, wear protective clothing and use sunscreen. Do not use sun lamps or tanning beds/booths.', 'Do not take this medicine with any of the following medications:\n•methazolamide\n\nThis medicine may also interact with the following medications:\n•aspirin and aspirin-like medicines\n•cyclosporine\n•lithium\n•medicine for diabetes\n•methenamine\n•other diuretics\n•phenytoin\n•primidone\n•quinidine\n•sodium bicarbonate\n•stimulant medicines like dextroamphetamine\n\nThis list may not describe all possible interactions. Give your health care provider a list of all the medicines, herbs, non-prescription drugs, or dietary supplements you use. Also tell them if you smoke, drink alcohol, or use illegal drugs. Some items may interact with your medicine.', 'BEFORE', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1151, 'Cardura', 'Cardura is indicated in the treatment of benign prostatic hyperplasia, a condition in which the prostate gland grows larger, pressing on the urethra and threatening to block the flow of urine from the bladder. \nThe drug relieves symptoms such as a weak stream, dribbling, incomplete emptying of the bladder, frequent urination, and burning during urination. \nCardura is indicated in the treatment of high blood pressure. \nIt is effective when indicated alone or in combination with other blood pressure medications, such as diuretics, beta-blocking medications, calcium channel blockers or ACE inhibitors. \nDoctors prescribe Cardura, along with other drugs such as digitalis and diuretics, for treatment of congestive heart failure. \nIf you have high blood pressure, you must take Cardura regularly for it to be effective. \nSince blood pressure declines gradually, it may be several weeks before you get the full benefit of Cardura and you must continue taking it even if you are feeling well. \nCardura does not cure high blood pressure it merely keeps it under control.', 'Cardura side effects that you should report to your health care professional or doctor as soon as possible: \n\nMore common side effects may include: \n- Headache; \n- Fatigue; \n- Drowsiness; \n- Dizziness; \n\nLess common side effects may include: \n- Weakness; \n- Tingling or pins and needles; \n- Thirst; \n- Shortness of breath; \n- Ringing in ears; \n- Rash; \n- Pain; \n- Nosebleeds; \n- Nervousness; \n- Nausea; \n- Nasal stuffiness; \n- Muscle weakness; \n- Muscle pain; \n- Muscle cramps; \n- Motion disorders; \n- Low blood pressure; \n- Lack of muscle coordination; \n- Joint pain; \n- Itching; \n- Inflammation of conjunctiva (pinkeye); \n- Indigestion; \n- Increased sweating; \n- Inability to hold urine or other urination problems; \n- Gas; \n- Flushing; \n- Flushes; \n- Flu-like symptoms; \n- Fluid retention; \n- Eye pain; \n- Dry mouth; \n- Difficulty sleeping; \n- Diarrhea; \n- Depression; \n- Constipation; \n- Arthritis; \n- Abnormal vision; \n- Abdominal pain; \n\nRare side effects may include: \n- Yellow skin and eyes; \n- Wheezing; \n- Weight loss; \n- Weight gain; \n- Vomiting; \n- Urinary disorders; \n- Twitching; \n- Tremors; \n- Stroke; \n- Sore throat; \n- Slow heartbeat; \n- Slight or partial paralysis; \n- Sinus inflammation; \n- Sexual problems; \n- Rapid pounding heartbeat; \n- Prolonged erections; \n- Pallor; \n- Nighttime urination; \n- Morbid dreams; \n- Migraine headache; \n- Loss of sense of personal identity; \n- Loss of appetite; \n- Infection; \n- Increased thirst; \n- Increased appetite; \n- Inability to tolerate light; \n- Inability to concentrate; \n- Hives; \n- Heart attack; \n- Hair loss; \n- Gout; \n- Frequent urination; \n- Fever; \n- Fecal incontinence; \n- Fainting; \n- Earache; \n- Dry skin; \n- Dizziness when standing up; \n- Decreased sensation; \n- Coughing; \n- Confusion; \n- Chest pain; \n- Changes in taste; \n- Changeable emotions; \n- Breathing difficulties; \n- Breast pain; \n- Breast development in men; \n- Blood in the urine; \n- Amnesia; \n- Altered sense of smell; \n- Allergic reactions; \n- Agitation; \n- Abnormal thinking;', 'Do not take Cardura with any of the following drugs: \nNo significant interactions have been reported.', 'ANY', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1193, 'Fludrocortisone', 'Fludrocortisone is a man-made form of a natural substance (glucocorticoid) made by the body. It is used along with other medications (e.g., hydrocortisone) to treat low glucocorticoid levels caused by disease of the adrenal gland (e.g., Addison\''s disease, adrenocortical insufficiency, salt-losing adrenogenital syndrome). Glucocorticoids are needed in many ways for the body to function well. They are important for salt and water balance and keeping blood pressure normal. They are also needed to break down carbohydrates in your diet.', 'Stomach upset, headache, and menstrual changes (e.g., delayed/irregular/absent periods) may occur. If any of these effects persist or worsen, contact your doctor or pharmacist promptly.\nRemember that your doctor has prescribed this medication because he or she has judged that the benefit to you is greater than the risk of side effects. Many people using this medication do not have serious side effects.\nTell your doctor immediately if any of these unlikely but serious side effects occur: change in skin appearance (e.g., color changes, thinning, fatty areas), easy bleeding/bruising, dizziness, slow wound healing, signs of infection (e.g., fever, persistent sore throat, skin sores), bone/joint/muscle pain, stomach/abdominal pain, puffy face, swelling of the hands/feet, severe tiredness, increased thirst/urination, unusual weight gain, muscle weakness.\nTell your doctor immediately if any of these rare but very serious side effects occur: black stools, eye problems (e.g., pain, redness, vision changes), severe/continuous headaches, fast/pounding/irregular heartbeat, mental/mood changes (e.g., agitation, depression, mood swings), seizure, vomit that looks like coffee grounds.\nA very serious allergic reaction to this drug is rare. However, seek immediate medical attention if you notice any of the following symptoms of a serious allergic reaction: rash, itching/swelling (especially of the face/tongue/throat), severe dizziness, trouble breathing.\nThis is not a complete list of possible side effects. If you notice other effects not listed above, contact your doctor or pharmacist.', 'Before taking fludrocortisone, tell your doctor or pharmacist if you are allergic to it; or if you have any other allergies. This product may contain inactive ingredients, which can cause allergic reactions or other problems. Talk to your pharmacist for more details.\nThis medication should not be used if you have certain medical conditions. Before using this medicine, consult your doctor or pharmacist if you have: fungal infection in your blood (e.g., candidiasis, valley fever).\nBefore using this medication, tell your doctor or pharmacist your medical history, especially of: bleeding problems, blood clots, brittle bones (osteoporosis), diabetes, eye problems (e.g., cataracts, glaucoma, infection of the eye), heart problems (e.g., congestive heart failure), high blood pressure, infections (e.g., herpes, tuberculosis), kidney disease, liver disease (e.g., cirrhosis), mental/mood disorders (e.g., anxiety, depression, psychosis), low blood minerals (e.g., calcium, potassium), stomach/intestinal problems (e.g., diverticulitis, peptic ulcer disease, ulcerative colitis), seizures, thyroid problems.\nFludrocortisone makes your body hold on to salt (sodium) and get rid of other salts (e.g., calcium, potassium). Follow your doctor\''s advice on how much salt, potassium, and calcium should be in your diet.\nThis medication may mask signs of infection or put you at greater risk of developing very serious infections. Report any injuries or signs of infection (e.g., persistent sore throat/fever/cough, pain while urinating, skin sores) that occur during treatment.\nDo not have immunizations/vaccinations without the consent of your doctor, and avoid contact with people who have recently received oral polio vaccine or flu vaccine inhaled through the nose.\nAvoid exposure to chickenpox or measles infection while taking this medication unless you have previously had these infections (e.g., in childhood). If you are exposed to either of these infections and you have not previously had them, seek immediate medical attention.\nUsing corticosteroid medications for a long time can make it more difficult for your body to respond to physical stress. Therefore, before having surgery or emergency treatment, or if you get a serious illness/injury, tell your doctor or dentist that you are using this medication or have used this medication within the past 12 months. Tell your doctor right away if you develop unusual/extreme tiredness or weight loss. If you will be using this medication for a long time, carry a warning card or medical ID bracelet that identifies your use of this medication.\nIf you have a history of ulcers or take large doses of aspirin or other arthritis medicine, limit alcoholic beverages while taking this medication to lower the risk of stomach/intestinal bleeding.\nIf you have diabetes, this drug may make it harder to control your blood sugar levels. Monitor your blood sugar levels regularly and inform your doctor of the results. Your diabetic medication or diet may need to be adjusted.\nThis medication may slow down a child\''s growth if used for a long time. Consult the doctor or pharmacist for more details. See the doctor regularly so your child\''s height and growth can be checked.\nThe elderly may be more sensitive to the effects of this drug, especially water retention.\nDuring pregnancy, this medication should be used only when clearly needed. Discuss the risks and benefits with your doctor. Infants born to mothers who have been using this medication for an extended time may have low levels of corticosteroid hormone. Tell your doctor immediately if you notice symptoms such as persistent nausea/vomiting, severe diarrhea, or weakness in your newborn.\nThis medication passes into breast milk and may have undesirable effects on a nursing infant. Consult your doctor before breast-feeding.', 'BEFORE', 'pill');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1209, 'Mucosolvan', 'Ambroxol is used for infections of the upper respiratory tract. It clears airways and eases cough. Preclinically, ambroxol, the active ingredient of MUCOSOLVAN, has been shown to increase respiratory tract secretion. It enhances pulmonary surfactant production and stimulates ciliary activity. These actions result in improved mucus flow and transport (mucociliary clearance). Improvement of mucociliary clearance has been shown in clinical pharmacologic studies. Enhancement of fluid secretion and mucociliary clearance facilitates expectoration and eases cough.', 'MUCOSOLVAN is generally well tolerated. Mild upper gastro-intestinal side effects (primarily pyrosis, dyspepsia, and occasionally nausea, vomiting) have been reported, principally following parenteral administration. Allergic reactions have occurred rarely, primarily skin rashes. There have been extremely rare case reports of severe acute anaphylactic-type reactions but their relationship to ambroxol is uncertain. Some of these patients have also shown allergic reactions to other substances.', 'MUCOSOLVAN should not be used in patients known to be hypersensitive to ambroxol or other components of the formulation.', 'BEFORE', 'drops');
INSERT INTO MEDICATION_MANAGER.MEDICINE (ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION, UNITS) VALUES (1210, 'Eferalgan', 'Acetaminophen is a pain reliever and a fever reducer.\n\nAcetaminophen is used to treat many conditions such as headache, muscle aches, arthritis, backache, toothaches, colds, and fevers.\n\nAcetaminophen may also be used for purposes other than those listed in this medication guide.\n\nHow should I take EFFERALGAN (Acetaminophen (oral/rectal))?\nTake acetaminophen exactly as directed by your doctor or follow the instructions on the package. If you do not understand these instructions, ask your pharmacist, nurse, or doctor to explain them to you.\n\nTake each oral dose with a full glass of water.\n\nAcetaminophen can be taken with or without food.\n\nWash your hands before and after using the rectal suppositories. Run the suppository under cold water or put it in the refrigerator for a few minutes before using it. Remove any wrapping from the suppository and moisten the suppository with cold water. Squat, stand, or lie down with one leg straight and the other bent, in a comfortable position that allows access to the rectal area. Use your finger, or the applicator if one is provided, to deposit the suppository as far as it will comfortably go into the rectum. Insert the narrow end first. Close your legs and lie still for a few minutes. If the applicator will be reused, take it apart and wash it with warm water and mild soap, then dry it completely. Avoid having a bowel movement for at least 1 hour after inserting the suppository.\n\nTo ensure that you get a correct dose, measure the liquid forms of acetaminophen with a special dose-measuring spoon or cup, not with a regular table spoon. If you do not have a dose-measuring device, ask your pharmacist where you can get one. Shake the liquid well before measuring.\n\nNever take more acetaminophen than is directed. The maximum amounts for adults are 1 gram (1000 mg) per dose and 4 grams (4000 mg) per day. Taking more acetaminophen could be damaging to the liver. If you drink more than three alcoholic beverages per day, talk to your doctor before taking acetaminophen.\n\nUse acetaminophen for up to 3 days for fever or up to 10 days for pain (or up to 5 days to treat a child’s pain). If the symptoms do not improve, or if they get worse, stop using acetaminophen and see a doctor.\n\nIf you are treating a child, read the package carefully and use a pediatric form of the medication if possible. Talk to a doctor first if the child is younger than 2 years of age.\n\nStore acetaminophen at room temperature away from heat, moisture, and the reach of children. The rectal suppositories can be stored at room temperature or in the refrigerator.', 'If you experience any of the following rare but serious side effects, stop taking acetaminophen and seek emergency medical attention or contact your doctor immediately:\n\nan allergic reaction (difficulty breathing; closing of the throat; swelling of the lips, tongue, or face; or hives);\n\nliver damage (yellowing of the skin or eyes, nausea, abdominal pain or discomfort, unusual bleeding or bruising, severe fatigue);\n\nblood problems (easy or unusual bleeding or bruising).\n\nOther, less serious side effects are not known to occur.\n\nSide effects other than those listed here may occur. Talk to your doctor about any side effect that seems unusual or that is especially bothersome.', 'Be aware of the acetaminophen content of other over-the-counter and prescription products. Care should be taken to avoid taking more than the recommended amount of acetaminophen per dose or per day.\n\nAcetaminophen may cause false urine glucose test results. Talk to your doctor if you have diabetes and you notice changes in your glucose levels while taking acetaminophen.\n\nOther medications may interact with acetaminophen. Talk to your doctor and pharmacist before taking any prescription or over-the-counter medicines, including herbal products while taking acetaminophen.\n\nWhat should I avoid while taking EFFERALGAN (Acetaminophen (oral/rectal))?\nAvoid alcohol during treatment with acetaminophen. Together, alcohol and acetaminophen can be damaging to the liver.\n\nBe aware of the acetaminophen content of other over-the-counter and prescription products. Care should be taken to avoid taking more than the recommended amount of acetaminophen per dose or per day.', 'AFTER', 'pill');



// dispenser table

insert into medication_manager.dispenser (id, patient_fk_id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (1, 2, 'dispenser one', 'urn:org.universaal.aal_space:device_env#dispenser-1', 'instructions_1.txt', true, true, true, true);

insert into medication_manager.dispenser (id, patient_fk_id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (2, 3, 'dispenser two', 'urn:org.universaal.aal_space:device_env#dispenser-2', 'instructions_2.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (3, 'dispenser three', 'urn:org.universaal.aal_space:device_env#dispenser-3', 'instructions_3.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (4, 'dispenser four', 'urn:org.universaal.aal_space:device_env#dispenser-4', 'instructions_4.txt', true, true, true, true);

insert into medication_manager.dispenser (id, patient_fk_id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (5, 1, 'dispenser five', 'urn:org.universaal.aal_space:device_env#dispenser-5', 'instructions_5.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (6, 'dispenser six', 'urn:org.universaal.aal_space:device_env#dispenser-6', 'instructions_6.txt', true, true, true, true);

insert into medication_manager.dispenser (id, name, dispenser_uri, instructions_file_name, upside_down_alert, successful_intake_alert, missed_intake_alert, due_intake_alert)
values (7, 'dispenser seven', 'urn:org.universaal.aal_space:device_env#dispenser-7', 'instructions_7.txt', true, true, true, true);

// medicine_inventory table

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (1, 1, 1150, 'pill', 45, 9);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (2, 1, 1151, 'pill', 10, 3);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (3, 1, 1149, 'pill', 5, 2);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (4, 1, 1008, 'pill', 30, 8);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (5, 1, 1007, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (6, 1, 1046, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (7, 2, 1071, 'pill', 10, 3);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (8, 2, 1070, 'pill', 30, 8);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (9, 2, 1193, 'pill', 5, 2);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (10, 2, 1008, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (11, 2, 1046, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (12, 3, 1046, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (13, 3, 1210, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (14, 3, 1209, 'drops', 150, 50);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (15, 3, 1008, 'pill', 15, 5);

insert into medication_manager.medicine_inventory (id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)
values (16, 3, 1193, 'pill', 5, 2);


// inventory_log table

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (1, 'current_date0 00:00:00', 1, 1150, 45, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (2, 'current_date0 00:00:00', 1, 1151, 10, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (3, 'current_date0 00:00:00', 1, 1149, 5, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (4, 'current_date0 00:00:00', 1, 1008, 30, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (5, 'current_date0 00:00:00', 1, 1007, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (6, 'current_date0 00:00:00', 1, 1046, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (7, 'current_date0 00:00:00', 2, 1071, 10, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (8, 'current_date0 00:00:00', 2, 1070, 30, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (9, 'current_date0 00:00:00', 2, 1193, 5, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (10, 'current_date0 00:00:00', 2, 1008, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (11, 'current_date0 00:00:00', 2, 1046, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (12, 'current_date0 00:00:00', 3, 1046, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (13, 'current_date0 00:00:00', 3, 1210, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (14, 'current_date0 00:00:00', 3, 1209, 150, 'drops', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (15, 'current_date0 00:00:00', 3, 1008, 15, 'pill', 'refill');

insert into medication_manager.inventory_log (id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)
values (16, 'current_date0 00:00:00', 3, 1193, 5, 'pill', 'refill');



// prescription table

INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1006, 'current_date0 12:25:43', 1, 5, 'Cold.\nThe patient must stay at home in a warm room.\nTo drink tea several times per day.\nTo take the prescribed medicines', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1045, 'current_date0 12:36:31', 1, 5, 'Overstrain.\nThe patient needs full rest, nourishing food and lots of fruits and vegetables.', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1069, 'current_date0 12:56:33', 2, 4, 'Sports trauma\nmuscles and tendons ', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1105, 'current_date0 12:58:50', 2, 4, 'Cold', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1148, 'current_date0 14:37:21', 1, 5, 'High blood pressure', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1192, 'current_date0 14:44:36', 2, 4, 'Low blood pressure\nThe patient needs rest and to drink lots of water.', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1208, 'current_date0 14:50:36', 3, 4, 'Cold\nThe patient should rest in home in a warm room and to drink tea.', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1263, 'current_date0 14:51:54', 3, 4, 'Overstrain\nThe patient should rest as much as possible', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1284, 'current_date0 14:53:27', 3, 4, 'Low blood pressure', 'ACTIVE');
INSERT INTO MEDICATION_MANAGER.PRESCRIPTION (ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) VALUES (1295, 'current_date0 14:55:15', 1, 5, 'headache', 'ACTIVE');


// treatment table


INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1009, 1006, 1007, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1025, 1006, 1008, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1047, 1045, 1046, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1072, 1069, 1070, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1088, 1069, 1071, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1106, 1105, 1046, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1122, 1105, 1008, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1152, 1148, 1149, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1158, 1148, 1150, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1174, 1148, 1151, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1194, 1192, 1193, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1211, 1208, 1209, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1227, 1208, 1210, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1243, 1208, 1008, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1264, 1263, 1046, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1285, 1284, 1193, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);

INSERT INTO MEDICATION_MANAGER.TREATMENT (ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS, MISSED_INTAKE_ALERT, NEW_DOSE_ALERT, SHORTAGE_ALERT)
VALUES (1296, 1295, 1008, 'current_date0 00:00:00', 'current_date5 00:00:00', 'Y', true, true, true);


// intake table


INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1010, 1009, 1, 'PILL', 'current_date0 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1011, 1009, 1, 'PILL', 'current_date1 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1012, 1009, 1, 'PILL', 'current_date2 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1013, 1009, 1, 'PILL', 'current_date3 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1014, 1009, 1, 'PILL', 'current_date4 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1015, 1009, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1016, 1009, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1017, 1009, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1018, 1009, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1019, 1009, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1020, 1009, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1021, 1009, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1022, 1009, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1023, 1009, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1024, 1009, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1026, 1025, 1, 'PILL', 'current_date0 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1027, 1025, 1, 'PILL', 'current_date1 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1028, 1025, 1, 'PILL', 'current_date2 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1029, 1025, 1, 'PILL', 'current_date3 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1030, 1025, 1, 'PILL', 'current_date4 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1031, 1025, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1032, 1025, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1033, 1025, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1034, 1025, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1035, 1025, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1036, 1025, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1037, 1025, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1038, 1025, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1039, 1025, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1040, 1025, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1048, 1047, 1, 'PILL', 'current_date0 12:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1049, 1047, 1, 'PILL', 'current_date1 12:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1050, 1047, 1, 'PILL', 'current_date2 12:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1051, 1047, 1, 'PILL', 'current_date3 12:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1052, 1047, 1, 'PILL', 'current_date4 12:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1053, 1047, 1, 'PILL', 'current_date0 18:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1054, 1047, 1, 'PILL', 'current_date1 18:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1055, 1047, 1, 'PILL', 'current_date2 18:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1056, 1047, 1, 'PILL', 'current_date3 18:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1057, 1047, 1, 'PILL', 'current_date4 18:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1058, 1047, 1, 'PILL', 'current_date0 06:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1059, 1047, 1, 'PILL', 'current_date1 06:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1060, 1047, 1, 'PILL', 'current_date2 06:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1061, 1047, 1, 'PILL', 'current_date3 06:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1062, 1047, 1, 'PILL', 'current_date4 06:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1073, 1072, 2, 'PILL', 'current_date0 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1074, 1072, 2, 'PILL', 'current_date1 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1075, 1072, 2, 'PILL', 'current_date2 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1076, 1072, 2, 'PILL', 'current_date3 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1077, 1072, 2, 'PILL', 'current_date4 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1078, 1072, 2, 'PILL', 'current_date0 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1079, 1072, 2, 'PILL', 'current_date1 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1080, 1072, 2, 'PILL', 'current_date2 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1081, 1072, 2, 'PILL', 'current_date3 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1082, 1072, 2, 'PILL', 'current_date4 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1083, 1072, 2, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1084, 1072, 2, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1085, 1072, 2, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1086, 1072, 2, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1087, 1072, 2, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1089, 1088, 1, 'PILL', 'current_date0 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1090, 1088, 1, 'PILL', 'current_date1 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1091, 1088, 1, 'PILL', 'current_date2 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1092, 1088, 1, 'PILL', 'current_date3 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1093, 1088, 1, 'PILL', 'current_date4 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1094, 1088, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1095, 1088, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1096, 1088, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1097, 1088, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1098, 1088, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1107, 1106, 1, 'PILL', 'current_date0 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1108, 1106, 1, 'PILL', 'current_date1 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1109, 1106, 1, 'PILL', 'current_date2 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1110, 1106, 1, 'PILL', 'current_date3 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1111, 1106, 1, 'PILL', 'current_date4 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1112, 1106, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1113, 1106, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1114, 1106, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1115, 1106, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1116, 1106, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1117, 1106, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1118, 1106, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1119, 1106, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1120, 1106, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1121, 1106, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1123, 1122, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1124, 1122, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1125, 1122, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1126, 1122, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1127, 1122, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1128, 1122, 1, 'PILL', 'current_date0 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1129, 1122, 1, 'PILL', 'current_date1 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1130, 1122, 1, 'PILL', 'current_date2 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1131, 1122, 1, 'PILL', 'current_date3 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1132, 1122, 1, 'PILL', 'current_date4 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1133, 1122, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1134, 1122, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1135, 1122, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1136, 1122, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1137, 1122, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1153, 1152, 1, 'PILL', 'current_date0 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1154, 1152, 1, 'PILL', 'current_date1 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1155, 1152, 1, 'PILL', 'current_date2 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1156, 1152, 1, 'PILL', 'current_date3 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1157, 1152, 1, 'PILL', 'current_date4 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1159, 1158, 3, 'PILL', 'current_date0 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1160, 1158, 3, 'PILL', 'current_date1 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1161, 1158, 3, 'PILL', 'current_date2 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1162, 1158, 3, 'PILL', 'current_date3 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1163, 1158, 3, 'PILL', 'current_date4 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1164, 1158, 3, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1165, 1158, 3, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1166, 1158, 3, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1167, 1158, 3, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1168, 1158, 3, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1169, 1158, 3, 'PILL', 'current_date0 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1170, 1158, 3, 'PILL', 'current_date1 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1171, 1158, 3, 'PILL', 'current_date2 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1172, 1158, 3, 'PILL', 'current_date3 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1173, 1158, 3, 'PILL', 'current_date4 14:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1175, 1174, 1, 'PILL', 'current_date0 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1176, 1174, 1, 'PILL', 'current_date1 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1177, 1174, 1, 'PILL', 'current_date2 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1178, 1174, 1, 'PILL', 'current_date3 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1179, 1174, 1, 'PILL', 'current_date4 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1180, 1174, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1181, 1174, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1182, 1174, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1183, 1174, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1184, 1174, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1195, 1194, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1196, 1194, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1197, 1194, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1198, 1194, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1199, 1194, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1212, 1211, 10, 'DROPS', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1213, 1211, 10, 'DROPS', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1214, 1211, 10, 'DROPS', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1215, 1211, 10, 'DROPS', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1216, 1211, 10, 'DROPS', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1217, 1211, 10, 'DROPS', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1218, 1211, 10, 'DROPS', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1219, 1211, 10, 'DROPS', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1220, 1211, 10, 'DROPS', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1221, 1211, 10, 'DROPS', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1222, 1211, 10, 'DROPS', 'current_date0 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1223, 1211, 10, 'DROPS', 'current_date1 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1224, 1211, 10, 'DROPS', 'current_date2 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1225, 1211, 10, 'DROPS', 'current_date3 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1226, 1211, 10, 'DROPS', 'current_date4 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1228, 1227, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1229, 1227, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1230, 1227, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1231, 1227, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1232, 1227, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1233, 1227, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1234, 1227, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1235, 1227, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1236, 1227, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1237, 1227, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1238, 1227, 1, 'PILL', 'current_date0 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1239, 1227, 1, 'PILL', 'current_date1 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1240, 1227, 1, 'PILL', 'current_date2 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1241, 1227, 1, 'PILL', 'current_date3 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1242, 1227, 1, 'PILL', 'current_date4 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1244, 1243, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1245, 1243, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1246, 1243, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1247, 1243, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1248, 1243, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1249, 1243, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1250, 1243, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1251, 1243, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1252, 1243, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1253, 1243, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1254, 1243, 1, 'PILL', 'current_date0 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1255, 1243, 1, 'PILL', 'current_date1 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1256, 1243, 1, 'PILL', 'current_date2 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1257, 1243, 1, 'PILL', 'current_date3 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1258, 1243, 1, 'PILL', 'current_date4 22:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1265, 1264, 1, 'PILL', 'current_date0 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1266, 1264, 1, 'PILL', 'current_date1 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1267, 1264, 1, 'PILL', 'current_date2 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1268, 1264, 1, 'PILL', 'current_date3 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1269, 1264, 1, 'PILL', 'current_date4 21:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1270, 1264, 1, 'PILL', 'current_date0 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1271, 1264, 1, 'PILL', 'current_date1 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1272, 1264, 1, 'PILL', 'current_date2 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1273, 1264, 1, 'PILL', 'current_date3 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1274, 1264, 1, 'PILL', 'current_date4 15:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1275, 1264, 1, 'PILL', 'current_date0 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1276, 1264, 1, 'PILL', 'current_date1 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1277, 1264, 1, 'PILL', 'current_date2 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1278, 1264, 1, 'PILL', 'current_date3 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1279, 1264, 1, 'PILL', 'current_date4 08:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1286, 1285, 1, 'PILL', 'current_date0 17:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1287, 1285, 1, 'PILL', 'current_date1 17:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1288, 1285, 1, 'PILL', 'current_date2 17:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1289, 1285, 1, 'PILL', 'current_date3 17:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1290, 1285, 1, 'PILL', 'current_date4 17:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1297, 1296, 1, 'PILL', 'current_date0 10:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1298, 1296, 1, 'PILL', 'current_date1 10:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1299, 1296, 1, 'PILL', 'current_date2 10:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1300, 1296, 1, 'PILL', 'current_date3 10:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1301, 1296, 1, 'PILL', 'current_date4 10:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1302, 1296, 1, 'PILL', 'current_date0 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1303, 1296, 1, 'PILL', 'current_date1 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1304, 1296, 1, 'PILL', 'current_date2 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1305, 1296, 1, 'PILL', 'current_date3 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1306, 1296, 1, 'PILL', 'current_date4 20:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1307, 1296, 1, 'PILL', 'current_date0 16:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1308, 1296, 1, 'PILL', 'current_date1 16:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1309, 1296, 1, 'PILL', 'current_date2 16:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1310, 1296, 1, 'PILL', 'current_date3 16:00:00', null, null);
INSERT INTO MEDICATION_MANAGER.INTAKE (ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN, TIME_TAKEN, DISPENSER_FK_ID) VALUES (1311, 1296, 1, 'PILL', 'current_date4 16:00:00', null, null);





