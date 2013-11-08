package org.universAAL.AALapplication.nutritional.dialogs;

import java.util.Locale;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import na.miniDao.profile.PatientList;
import na.oasisUtils.profile.ProfileConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.ws.UserNutritionalProfile;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.services.OntoFactory;
import org.universAAL.AALapplication.nutritional.utils.Utils;
import org.universAAL.middleware.owl.IntRestriction;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Range;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.nutrition.profile.DietType;
import org.universAAL.ontology.nutrition.profile.NutritionalHabits;
import org.universAAL.ontology.nutrition.profile.NutritionalPreferences;
import org.universAAL.ontology.nutrition.profile.NutritionalSubProfile;
import org.universAAL.ontology.recommendations.HorizontalAlignment;
import org.universAAL.ontology.recommendations.HorizontalLayout;
import org.universAAL.ontology.recommendations.VerticalLayout;

/**
 * The UI class that builds the Profile Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIProfile {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIProfile"; //$NON-NLS-1$

    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String SUBMIT_SAVEPROFILE = PREFIX + "saveprofile"; //$NON-NLS-1$
    static final String SUBMIT_FOODDISLIKES = PREFIX + "fooddislikes"; //$NON-NLS-1$
    static final String SUBMIT_FAVRECIPES = PREFIX + "favrecipes"; //$NON-NLS-1$
    static final String SUBMIT_MEDINTERACT = PREFIX + "medinteract"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final String USER_INPUT_REF2 = PREFIX + "ref2"; //$NON-NLS-1$
    static final String USER_INPUT_REF3 = PREFIX + "ref3"; //$NON-NLS-1$
    static final String USER_INPUT_REF4 = PREFIX + "ref4"; //$NON-NLS-1$
    static final String USER_INPUT_REF5 = PREFIX + "ref5"; //$NON-NLS-1$
    static final String USER_INPUT_REF6 = PREFIX + "ref6"; //$NON-NLS-1$
    static final String USER_INPUT_REF7 = PREFIX + "ref7"; //$NON-NLS-1$
    static final String USER_INPUT_REF8 = PREFIX + "ref8"; //$NON-NLS-1$
    static final String USER_INPUT_REF9 = PREFIX + "ref9"; //$NON-NLS-1$
    static final String USER_INPUT_REF10 = PREFIX + "ref10"; //$NON-NLS-1$
    static final String USER_INPUT_REF11 = PREFIX + "ref11"; //$NON-NLS-1$
    static final String USER_INPUT_REF12 = PREFIX + "ref12"; //$NON-NLS-1$
    static final String USER_INPUT_REF13 = PREFIX + "ref13"; //$NON-NLS-1$
    static final String USER_INPUT_REF14 = PREFIX + "ref14"; //$NON-NLS-1$
    static final String USER_INPUT_REF15 = PREFIX + "ref15"; //$NON-NLS-1$
    static final String USER_INPUT_REF16 = PREFIX + "ref16"; //$NON-NLS-1$
    static final String USER_INPUT_REF17 = PREFIX + "ref17"; //$NON-NLS-1$
    static final String USER_INPUT_REF18 = PREFIX + "ref18"; //$NON-NLS-1$
    static final String USER_INPUT_REF19 = PREFIX + "ref19"; //$NON-NLS-1$
    static final String USER_INPUT_REF20 = PREFIX + "ref20"; //$NON-NLS-1$
    static final String USER_INPUT_REF21 = PREFIX + "ref21"; //$NON-NLS-1$

    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF1 });
    static final PropertyPath PROP_PATH_REF2 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF2 });
    static final PropertyPath PROP_PATH_REF3 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF3 });
    static final PropertyPath PROP_PATH_REF4 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF4 });
    static final PropertyPath PROP_PATH_REF5 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF5 });
    static final PropertyPath PROP_PATH_REF6 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF6 });
    static final PropertyPath PROP_PATH_REF7 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF7 });
    static final PropertyPath PROP_PATH_REF8 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF8 });
    static final PropertyPath PROP_PATH_REF9 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF9 });
    static final PropertyPath PROP_PATH_REF10 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF10 });
    static final PropertyPath PROP_PATH_REF11 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF11 });
    static final PropertyPath PROP_PATH_REF12 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF12 });
    static final PropertyPath PROP_PATH_REF13 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF13 });
    static final PropertyPath PROP_PATH_REF14 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF14 });
    static final PropertyPath PROP_PATH_REF15 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF15 });
    static final PropertyPath PROP_PATH_REF16 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF16 });
    static final PropertyPath PROP_PATH_REF17 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF17 });
    static final PropertyPath PROP_PATH_REF18 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF18 });
    static final PropertyPath PROP_PATH_REF19 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF19 });
    static final PropertyPath PROP_PATH_REF20 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF20 });
    static final PropertyPath PROP_PATH_REF21 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF21 });

    private static boolean stopLoading = false;

//    public static Form getForm() {
//	Utils.println(Messages.getString("UIProfile.1")); //$NON-NLS-1$
//
//	final Form f = Form.newDialog(Messages.getString("UIProfile.35"), new Resource()); //$NON-NLS-1$
//	 f.getIOControls().addAppearanceRecommendation(new VerticalLayout());
//	    f.getIOControls().addAppearanceRecommendation(HorizontalAlignment.center);
//	
//	new SimpleOutput(f.getIOControls(), null, null, Messages.getString("UIProfile.36")); //$NON-NLS-1$
//
//	new Submit(f.getSubmits(), new Label(
//		Messages.getString("UIProfile.9"), null), SUBMIT_GOBACK); //$NON-NLS-1$
//	
//	new Thread() {
//	    public void run() {
//		ProfileConnector profConnect = ProfileConnector.getInstance();
//		profConnect.downloadProfileFromServer();
//		UserNutritionalProfile prof = profConnect.getProfile();
//		NutritionalSubProfile nutrProf = OntoFactory.getNutrProfile(prof);
//		NutritionalHabits nutrHab = nutrProf.getNutritionalHabits();
//		if (!stopLoading) {
//		    SharedResources.uIProvider.abortDialog(f.getDialogID());
//		    UIRequest out = new UIRequest(SharedResources.user,
//			    UIProfile.getForm(prof, nutrProf, nutrHab),
//			    LevelRating.middle, Locale.getDefault(),
//			    PrivacyLevel.insensible);
//		    SharedResources.uIProvider.sendUIRequest(out);
//		    stopLoading=false;
//		}
//	    }
//	}.start();
//	
//	return f;
//    }
    
    /**
     * Compose the form for this dialog.
     * 
     * @return The form.
     */
    public static Form getForm(/*UserNutritionalProfile prof, NutritionalSubProfile nutrProf, NutritionalHabits nutrHab*/) {
	Utils.println(Messages.getString("UIProfile.1")); //$NON-NLS-1$
	Form f;

	ProfileConnector profConnect = ProfileConnector.getInstance();
	profConnect.downloadProfileFromServer();
	UserNutritionalProfile prof = profConnect.getProfile();
	NutritionalSubProfile nutrProf = OntoFactory.getNutrProfile(prof);
	NutritionalHabits nutrHab = nutrProf.getNutritionalHabits();
	// try {
	// PatientList[] list = profConnect.getPatientsList();
	// } catch (OASIS_ServiceUnavailable e) {
	// e.printStackTrace();
	// }

	f = Form.newDialog(Messages.getString("UIProfile.2"), new Resource()); //$NON-NLS-1$
	 f.getIOControls().addAppearanceRecommendation(new VerticalLayout());
	    f.getIOControls().addAppearanceRecommendation(HorizontalAlignment.right);
//	f.setProperty(
//		"http://ontology.itaca.es/ClassicGUI.owl#layout", "alternate,vertical,right"); //$NON-NLS-1$ //$NON-NLS-2$

	Group nutrHabits = new Group(
		f.getIOControls(),
		new Label(Messages.getString("UIProfile.14"), null), PROP_PATH_REF1, null, null); //$NON-NLS-1$
	nutrHabits.addAppearanceRecommendation(new HorizontalLayout());
	nutrHabits.addAppearanceRecommendation(HorizontalAlignment.right);
	// TODO AM PM stuff
	Group hours = new Group(nutrHabits,
		new Label("", null), PROP_PATH_REF2, null, null); //$NON-NLS-1$
	hours.addAppearanceRecommendation(new VerticalLayout());
	hours.addAppearanceRecommendation(HorizontalAlignment.right);
	Select1 s1 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.15"), (String) null), PROP_PATH_REF3, null, null); //$NON-NLS-1$
	s1.generateChoices(new String[] {
		"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s1.storeUserInputByLabelString(getHour(nutrHab.getGetUpHour()));
	Select1 s2 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.16"), (String) null), PROP_PATH_REF4, null, null); //$NON-NLS-1$
	s2.generateChoices(new String[] {
		"05:00", "6:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s2.storeUserInputByLabelString(getHour(nutrHab.getBreakfastHour()));
	Select1 s3 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.17"), (String) null), PROP_PATH_REF5, null, null); //$NON-NLS-1$
	s3.generateChoices(new String[] {
		"07:00", "8:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s3.storeUserInputByLabelString(getHour(nutrHab.getMorningSnackHour()));
	Select1 s4 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.18"), (String) null), PROP_PATH_REF6, null, null); //$NON-NLS-1$
	s4.generateChoices(new String[] {
		"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s4.storeUserInputByLabelString(getHour(nutrHab.getLunchHour()));
	Select1 s5 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.19"), (String) null), PROP_PATH_REF7, null, null); //$NON-NLS-1$
	s5.generateChoices(new String[] {
		"13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s5.storeUserInputByLabelString(getHour(nutrHab.getAfternoonSnackHour()));
	Select1 s6 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.20"), (String) null), PROP_PATH_REF8, null, getHour(nutrHab.getDinnerHour())); //$NON-NLS-1$
	s6.generateChoices(new String[] {
		"15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s6.storeUserInputByLabelString(getHour(nutrHab.getDinnerHour()));
	Select1 s7 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.21"), (String) null), PROP_PATH_REF9, null, null); //$NON-NLS-1$
	s7.generateChoices(new String[] {
		"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s7.storeUserInputByLabelString(getHour(nutrHab
		.getAfterDinnerSnackHour()));
	Select1 s8 = new Select1(
		hours,
		new Label(Messages.getString("UIProfile.22"), (String) null), PROP_PATH_REF10, null, null); //$NON-NLS-1$
	s8.generateChoices(new String[] {
		"19:00", "20:00", "21:00", "22:00", "23:00", "24:00", "01:00", "02:00" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	s8.storeUserInputByLabelString(getHour(nutrHab.getBedHour()));

	Group misc = new Group(nutrHabits,
		new Label("", null), PROP_PATH_REF11, null, null); //$NON-NLS-1$
	misc.addAppearanceRecommendation(new VerticalLayout());
	misc.addAppearanceRecommendation(HorizontalAlignment.right);
	new InputField(
		misc,
		new Label(Messages.getString("UIProfile.23"), (String) null), PROP_PATH_REF12, //$NON-NLS-1$
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			PROP_PATH_REF12.getLastPathElement(),
			TypeMapper.getDatatypeURI(Boolean.class), 1, 1),
		Boolean.valueOf(nutrHab.isConsumesAlcohol()));
	new InputField(
		misc,
		new Label(Messages.getString("UIProfile.24"), (String) null), PROP_PATH_REF13, //$NON-NLS-1$
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			PROP_PATH_REF13.getLastPathElement(),
			TypeMapper.getDatatypeURI(Boolean.class), 1, 1),
		Boolean.valueOf(nutrHab.isConsumesTobacco()));
	//	 new InputField(misc, new Label(Messages.getString("UIProfile.25"), (String) null), PROP_PATH_REF14,  //$NON-NLS-1$
	// MergedRestriction.getAllValuesRestrictionWithCardinality(PROP_PATH_REF14.getLastPathElement(),TypeMapper.getDatatypeURI(Boolean.class),
	// 1, 1), Boolean.valueOf(nutrHab.isConsumesDrugs()));

	Select1 s9 = new Select1(
		misc,
		new Label(Messages.getString("UIProfile.26"), (String) null), PROP_PATH_REF16, null, nutrHab.getDietType()); //$NON-NLS-1$
	s9.generateChoices(new DietType[] { DietType.Mediterranean,
		DietType.Saxon, DietType.Vegetarian, DietType.Vegan,
		DietType.Other });
	new Range(
		misc,
		new Label(Messages.getString("UIProfile.27"), (String) null), PROP_PATH_REF17, //$NON-NLS-1$
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			PROP_PATH_REF17.getLastPathElement(),
			new IntRestriction(0, true, 10, true), 1, 1), Integer
			.valueOf(nutrHab.getNumberCompanionsAtTable()));
	new Range(
		misc,
		new Label(Messages.getString("UIProfile.28"), (String) null), PROP_PATH_REF18, //$NON-NLS-1$
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			PROP_PATH_REF18.getLastPathElement(),
			new IntRestriction(0, true, 10, true), 1, 1), Integer
			.valueOf(nutrHab.getNumberDailyMeals()));

	new InputField(
		misc,
		new Label(Messages.getString("UIProfile.29"), (String) null), PROP_PATH_REF19, //$NON-NLS-1$
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			PROP_PATH_REF19.getLastPathElement(),
			TypeMapper.getDatatypeURI(Boolean.class), 1, 1),
		Boolean.valueOf(nutrHab.isSelfCooking()));
	new InputField(
		misc,
		new Label(Messages.getString("UIProfile.30"), (String) null), PROP_PATH_REF20, //$NON-NLS-1$
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			PROP_PATH_REF20.getLastPathElement(),
			TypeMapper.getDatatypeURI(Boolean.class), 1, 1),
		Boolean.valueOf(nutrHab.isSelfShopping()));

	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIProfile.31"), null), SUBMIT_SAVEPROFILE); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIProfile.32"), null), SUBMIT_FOODDISLIKES); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIProfile.33"), null), SUBMIT_FAVRECIPES); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIProfile.34"), null), SUBMIT_MEDINTERACT); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIProfile.9"), null), //$NON-NLS-1$
		SUBMIT_GOBACK);

	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this
     * class� one (it�s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIProfile"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		stopLoading =true;
		Utils.println("UIProfile: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			// UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_SAVEPROFILE.equals(id)) {

		Utils.println(" TODO UIProfile: submit saveprofile"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.user,
			Form.newMessage("", //$NON-NLS-1$
				""), //$NON-NLS-1$
			LevelRating.middle, Locale.getDefault(),
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_FOODDISLIKES.equals(id)) {
		Utils.println(" TODO UIProfile: submit fooddis"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			UIItemList.getForm(true), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_FAVRECIPES.equals(id)) {
		Utils.println(" TODO UIProfile: submit favrec"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			UIItemList.getForm(false), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_MEDINTERACT.equals(id)) {
		Utils.println(" TODO UIProfile: submit medinteract"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			UIMedication.getForm(), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	}
	Utils.println("Finished delegation of UI response in UIProfile"); //$NON-NLS-1$
    }

    private static String getHour(XMLGregorianCalendar hour) {
	if (hour != null) {
	    int h = hour.getHour();
	    if (h != DatatypeConstants.FIELD_UNDEFINED) {
		return (h < 10 ? ("0" + h) : (h)) + ":00"; //$NON-NLS-1$ //$NON-NLS-2$
	    }
	    return null;
	}
	return null;
    }

}
