
@prefix ns: <http://ontology.universAAL.org/UI.owl#> .
@prefix pvn: <http://ontology.universAAL.org/uAAL.owl#> .
@prefix ns1: <http://ontology.universAAL.org/Language.owl#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix ns2: <http://ui.universAAL.org/dmFileStorage.owl#> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
@prefix ns3: <http://ontology.universaal.org/InteractionPreferencesProfile.owl#> .
@prefix ns4: <urn:org.universAAL.safety:test_environment#> .
@prefix ns5: <http://ontology.universaal.org/language.owl#> .
@prefix : <http://ontology.universAAL.org/Dialog.owl#> .
_:BN00000e ns2:suspended [
      ns3:interactionPreferences ns4:saiedSubprofileUIPreferencesGeneralInteractionPreferences ;
      ns:altPresentationModality pvn:voice ;
      ns3:visualPreferences ns4:saiedSubprofileUIPreferencesVisualPreferences ;
      ns:dialogLanguage "en"^^xsd:language ;
      ns:presentationModality pvn:gui ;
      a ns:UIRequest ;
      ns:dialogForm <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1477> ;
      ns:dialogPrivacyLevel pvn:insensible ;
      ns3:audioPreferences ns4:saiedSubprofileUIPreferencesAuditoryPreferences ;
      ns3:alertPreferences ns4:saiedSubprofileUIPreferencesAlertPreferences ;
      ns:addressedUser ns4:saied ;
      ns3:accessMode ns4:saiedSubprofileUIPreferencesAccessMode ;
      ns:dialogPriority pvn:middle
    ] ,
    [
      ns3:interactionPreferences ns4:saiedSubprofileUIPreferencesGeneralInteractionPreferences ;
      ns:altPresentationModality pvn:voice ;
      ns3:visualPreferences ns4:saiedSubprofileUIPreferencesVisualPreferences ;
      ns:dialogLanguage "en"^^xsd:language ;
      ns:presentationModality pvn:gui ;
      a ns:UIRequest ;
      ns:dialogForm <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1488> ;
      ns:dialogPrivacyLevel pvn:insensible ;
      ns3:audioPreferences ns4:saiedSubprofileUIPreferencesAuditoryPreferences ;
      ns3:alertPreferences ns4:saiedSubprofileUIPreferencesAlertPreferences ;
      ns:addressedUser ns4:saied ;
      ns3:accessMode ns4:saiedSubprofileUIPreferencesAccessMode ;
      ns:dialogPriority pvn:middle
    ] ,
    [
      ns3:interactionPreferences ns4:saiedSubprofileUIPreferencesGeneralInteractionPreferences ;
      ns:altPresentationModality pvn:voice ;
      ns3:visualPreferences ns4:saiedSubprofileUIPreferencesVisualPreferences ;
      ns:dialogLanguage "en"^^xsd:language ;
      ns:presentationModality pvn:gui ;
      a ns:UIRequest ;
      ns:dialogForm <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1793> ;
      ns:dialogPrivacyLevel pvn:insensible ;
      ns3:audioPreferences ns4:saiedSubprofileUIPreferencesAuditoryPreferences ;
      ns3:alertPreferences ns4:saiedSubprofileUIPreferencesAlertPreferences ;
      ns:addressedUser ns4:saied ;
      ns3:accessMode ns4:saiedSubprofileUIPreferencesAccessMode ;
      ns:dialogPriority pvn:middle
    ] ;
  ns2:active [
      ns3:interactionPreferences ns4:saiedSubprofileUIPreferencesGeneralInteractionPreferences ;
      ns:altPresentationModality pvn:voice ;
      ns3:visualPreferences ns4:saiedSubprofileUIPreferencesVisualPreferences ;
      ns:dialogLanguage "en"^^xsd:language ;
      ns:presentationModality pvn:gui ;
      a ns:UIRequest ;
      ns:dialogForm <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1b7e> ;
      ns:dialogPrivacyLevel pvn:insensible ;
      ns3:audioPreferences ns4:saiedSubprofileUIPreferencesAuditoryPreferences ;
      ns3:alertPreferences ns4:saiedSubprofileUIPreferencesAlertPreferences ;
      ns:addressedUser ns4:saied ;
      ns3:accessMode ns4:saiedSubprofileUIPreferencesAccessMode ;
      ns:dialogPriority pvn:middle
    ] .
ns4:saiedSubprofileUIPreferencesAccessMode ns3:auditoryModeStatus ns3:on ;
  ns3:olfactoryModeStatus ns3:off ;
  a ns3:AccessMode ;
  ns3:textualModeStatus ns3:on ;
  ns3:tactileModeStatus ns3:off ;
  ns3:visualModeStatus ns3:on .
ns3:visualAndAudio a ns3:AlertType .
<http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1477> :creationTimestamp "2013-07-26T15:30:00.702+03:00"^^xsd:dateTime ;
  :dialogType pvn:message ;
  a :Form ;
  :dialogDataRoot [] ;
  :rootGroup _:BN00000b .
pvn:middle a pvn:LevelRating .
ns3:overlap a ns3:WindowLayoutType .
<http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1793> :creationTimestamp "2013-07-26T15:33:42.786+03:00"^^xsd:dateTime ;
  :dialogType pvn:message ;
  a :Form ;
  :dialogDataRoot [] ;
  :rootGroup _:BN00000d .
_:BN000000 :groupChildren [
      :theContent "Lights are on"^^xsd:string ;
      :parentControl _:BN000000 ;
      a :SimpleOutput
    ] ,
    [
      :contentURL "bundle://88.0:1/images/light_on.png"^^xsd:string ;
      :parentControl _:BN000000 ;
      a :MediaObject ;
      :contentType "image/png"^^xsd:string ;
      :controlLabel [
        a :Label ;
        rdfs:label ""^^xsd:string
      ]
    ] ;
  :parentControl _:BN000001 ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "ioControlsGroup"^^xsd:string
  ] .
_:BN000001 :groupChildren _:BN000005 ,
    _:BN000000 ;
  :parentControl <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1488> ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "Lights Alert Message"^^xsd:string
  ] .
<http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1b7e> :creationTimestamp "2013-07-26T15:39:40.816+03:00"^^xsd:dateTime ;
  :dialogType pvn:message ;
  a :Form ;
  :dialogDataRoot [] ;
  :rootGroup _:BN000004 .
ns4:saiedSubprofileUIPreferencesAuditoryPreferences a ns3:AuditoryPreferences ;
  ns3:pitch ns3:medium ;
  ns3:keySound ns3:off ;
  ns3:volume ns3:medium ;
  ns3:systemSounds ns3:on ;
  ns3:speechRate ns3:medium ;
  ns3:voiceGender ns3:female .
_:BN000002 :groupChildren [
      pvn:submissionID "deleteMessage"^^xsd:string ;
      :parentControl _:BN000002 ;
      a :Submit ;
      :controlLabel _:BN000007
    ] ,
    [
      pvn:submissionID "preserveMessage"^^xsd:string ;
      :parentControl _:BN000002 ;
      a :Submit ;
      :controlLabel _:BN000006
    ] ;
  :parentControl _:BN000004 ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "submitsGroup"^^xsd:string
  ] .
_:BN000003 :groupChildren [
      :theContent "Lights are on"^^xsd:string ;
      :parentControl _:BN000003 ;
      a :SimpleOutput
    ] ,
    [
      :contentURL "bundle://88.0:1/images/light_on.png"^^xsd:string ;
      :parentControl _:BN000003 ;
      a :MediaObject ;
      :contentType "image/png"^^xsd:string ;
      :controlLabel [
        a :Label ;
        rdfs:label ""^^xsd:string
      ]
    ] ;
  :parentControl _:BN000004 ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "ioControlsGroup"^^xsd:string
  ] .
ns4:saiedSubprofileUIPreferencesVisualPreferences ns3:fontColor ns3:black ;
  ns3:componentSpacing ns3:medium ;
  ns3:highlightColor ns3:white ;
  ns3:screenSaverUsage ns3:off ;
  ns3:screenResolution ns3:medium ;
  ns3:fontSize ns3:medium ;
  ns3:dayNightMode ns3:on ;
  a ns3:VisualPreferences ;
  ns3:contentContrast ns3:high ;
  ns3:backgroundColor ns3:lightBlue ;
  ns3:flashingResources ns3:on ;
  ns3:fontFamily ns3:serif ;
  ns3:brightness ns3:medium ;
  ns3:windowLayout ns3:overlap ;
  ns3:cursorSize ns3:medium .
pvn:insensible a pvn:PrivacyLevel .
_:BN000005 :groupChildren [
      pvn:submissionID "deleteMessage"^^xsd:string ;
      :parentControl _:BN000005 ;
      a :Submit ;
      :controlLabel _:BN000007
    ] ,
    [
      pvn:submissionID "preserveMessage"^^xsd:string ;
      :parentControl _:BN000005 ;
      a :Submit ;
      :controlLabel _:BN000006
    ] ;
  :parentControl _:BN000001 ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "submitsGroup"^^xsd:string
  ] .
_:BN000004 :groupChildren _:BN000002 ,
    _:BN000003 ;
  :parentControl <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1b7e> ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "Lights Alert Message"^^xsd:string
  ] .
pvn:message a pvn:DialogType .
ns3:serif a ns3:GenericFontFamily .
ns3:detailed a ns3:ContentDensityType .
_:BN000006 a :Label ;
  rdfs:label "Preserve"^^xsd:string .
_:BN000007 a :Label ;
  rdfs:label "Delete"^^xsd:string .
_:BN000008 :groupChildren [
      pvn:submissionID "deleteMessage"^^xsd:string ;
      :parentControl _:BN000008 ;
      a :Submit ;
      :controlLabel _:BN000007
    ] ,
    [
      pvn:submissionID "preserveMessage"^^xsd:string ;
      :parentControl _:BN000008 ;
      a :Submit ;
      :controlLabel _:BN000006
    ] ;
  :parentControl _:BN00000b ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "submitsGroup"^^xsd:string
  ] .
pvn:gui a pvn:Modality .
_:BN000009 :groupChildren [
      :theContent "Window is open"^^xsd:string ;
      :parentControl _:BN000009 ;
      a :SimpleOutput
    ] ,
    [
      :contentURL "bundle://88.0:1/images/opened_window.png"^^xsd:string ;
      :parentControl _:BN000009 ;
      a :MediaObject ;
      :contentType "image/png"^^xsd:string ;
      :controlLabel [
        a :Label ;
        rdfs:label "Window"^^xsd:string
      ]
    ] ;
  :parentControl _:BN00000b ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "ioControlsGroup"^^xsd:string
  ] .
_:BN00000a :groupChildren [
      pvn:submissionID "deleteMessage"^^xsd:string ;
      :parentControl _:BN00000a ;
      a :Submit ;
      :controlLabel _:BN000007
    ] ,
    [
      pvn:submissionID "preserveMessage"^^xsd:string ;
      :parentControl _:BN00000a ;
      a :Submit ;
      :controlLabel _:BN000006
    ] ;
  :parentControl _:BN00000d ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "submitsGroup"^^xsd:string
  ] .
pvn:voice a pvn:Modality .
ns3:on a ns3:Status .
ns3:off a ns3:Status .
ns4:saiedSubprofileUIPreferencesGeneralInteractionPreferences ns3:secondaryLanguage ns5:greek ;
  ns3:secondaryModality pvn:voice ;
  a ns3:GeneralInteractionPreferences ;
  ns3:contentDensity ns3:detailed ;
  ns3:preferredLanguage ns5:greek ;
  ns3:preferredModality pvn:gui .
ns5:greek ns1:englishLabel "Greek, Modern"^^xsd:string ;
  a ns1:Greek ;
  ns1:iso639code "el"^^xsd:string ;
  ns1:nativeLabel "ελληνικά"^^xsd:string .
ns3:lightBlue a ns3:ColorType .
<http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1488> :creationTimestamp "2013-07-26T15:30:04.665+03:00"^^xsd:dateTime ;
  :dialogType pvn:message ;
  a :Form ;
  :dialogDataRoot [] ;
  :rootGroup _:BN000001 .
ns4:saied a <http://ontology.universAAL.org/Profile.owl#User> ,
    <http://ontology.universaal.org/PhThing.owl#PhysicalThing> .
ns4:saiedSubprofileUIPreferencesAlertPreferences ns3:alertOption ns3:visualAndAudio ;
  a ns3:AlertPreferences .
ns3:black a ns3:ColorType .
ns3:medium a ns3:Intensity .
ns3:female a ns3:VoiceGender .
_:BN00000b :groupChildren _:BN000008 ,
    _:BN000009 ;
  :parentControl <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1477> ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "Window Alert Message"^^xsd:string
  ] .
ns3:high a ns3:Intensity .
_:BN00000c :groupChildren [
      :theContent "Lights are on"^^xsd:string ;
      :parentControl _:BN00000c ;
      a :SimpleOutput
    ] ,
    [
      :contentURL "bundle://88.0:1/images/light_on.png"^^xsd:string ;
      :parentControl _:BN00000c ;
      a :MediaObject ;
      :contentType "image/png"^^xsd:string ;
      :controlLabel [
        a :Label ;
        rdfs:label ""^^xsd:string
      ]
    ] ;
  :parentControl _:BN00000d ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "ioControlsGroup"^^xsd:string
  ] .
_:BN00000d :groupChildren _:BN00000a ,
    _:BN00000c ;
  :parentControl <http://ontology.universAAL.org/Dialog.owl#_:a0283cedf5b165c5:1793> ;
  a :Group ;
  :controlLabel [
    a :Label ;
    rdfs:label "Lights Alert Message"^^xsd:string
  ] .
ns3:white a ns3:ColorType .
