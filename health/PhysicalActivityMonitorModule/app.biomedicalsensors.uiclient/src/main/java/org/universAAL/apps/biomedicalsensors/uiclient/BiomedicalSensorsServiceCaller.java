package org.universAAL.apps.biomedicalsensors.uiclient;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Intersection;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.TypeURI;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ontology.biomedicalsensors.AlertService;
import org.universAAL.ontology.biomedicalsensors.BiomedicalSensorService;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
import org.universAAL.ontology.biomedicalsensors.MeasuredEntity;
import org.universAAL.ontology.biomedicalsensors.Zephyr;

/**
 * @author billyk,joemoul
 * 
 */
public class BiomedicalSensorsServiceCaller extends ContextSubscriber {

	public static AlertUI al = new AlertUI(SharedResources.moduleContext);
	public static boolean orangeAlertActive = false;
	public static boolean redAlertActive = false;
	public static String orangeAlertTime = "";
	public static String orangeAlertPostureValue = "";
	private static ServiceCaller caller;

	private static final String BIOMEDICALSENSORS_CONSUMER_NAMESPACE = "http://ontology.universaal.org/BiomedicalSensorsCaller.owl#";

	private static final String OUTPUT_LIST_OF_SENSORS = BIOMEDICALSENSORS_CONSUMER_NAMESPACE
			+ "controlledBiomedicalSensors";
	private static final String OUTPUT_SENSOR_TYPE = CompositeBiomedicalSensor.PROP_SENSOR_TYPE;
	private static final String OUTPUT_SENSOR_BTURL = CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE;
	private static final String OUTPUT_SENSOR_MEASUREMENTS = CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS;

	private static ContextEventPattern[] getContextSubscriptionParams() {

		ContextEventPattern cep = new ContextEventPattern();

		Intersection xsection = new Intersection();
		xsection.addType(new TypeURI(CompositeBiomedicalSensor.MY_URI, false));
		xsection.addType(MergedRestriction.getFixedValueRestriction(
				CompositeBiomedicalSensor.PROP_SENSOR_TYPE, Zephyr.zephyr));
		MergedRestriction subjectRestriction = MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						ContextEvent.PROP_RDF_SUBJECT, xsection, 1, 1);
		cep.addRestriction(subjectRestriction);

		return new ContextEventPattern[] { cep };
	}

	BiomedicalSensorsServiceCaller(ModuleContext context) {
		// the constructor register us to the bus
		super(context, getContextSubscriptionParams());

		caller = new DefaultServiceCaller(context);

	}

	// *****************************************************************
	// Services Requests
	// *****************************************************************

	private static ServiceRequest getMeasurementsRequest(String bsURI) {

		ServiceRequest getMeasSR = new ServiceRequest(
				new BiomedicalSensorService(), null);

		getMeasSR.addValueFilter(
				new String[] { BiomedicalSensorService.PROP_CONTROLS },
				new CompositeBiomedicalSensor(bsURI));

		getMeasSR.addChangeEffect(new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS },
				new Boolean(true));

		getMeasSR.addRequiredOutput(

		OUTPUT_SENSOR_MEASUREMENTS,

		new String[] { BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS });
		return getMeasSR;
	}

	private static ServiceRequest getInfoRequest(String bsURI) {
		ServiceRequest getInfoSR = new ServiceRequest(
				new BiomedicalSensorService(), null);

		getInfoSR.addValueFilter(
				new String[] { BiomedicalSensorService.PROP_CONTROLS },
				new CompositeBiomedicalSensor(bsURI));
		getInfoSR.addRequiredOutput(

		OUTPUT_SENSOR_TYPE,

		new String[] { BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_SENSOR_TYPE });
		getInfoSR.addRequiredOutput(

		OUTPUT_SENSOR_BTURL,

		new String[] { BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE });

		return getInfoSR;
	}

	public static ServiceRequest getAllSensorsRequest() {

		ServiceRequest getAllbs = new ServiceRequest(
				new BiomedicalSensorService(), null);

		getAllbs.addRequiredOutput(

		OUTPUT_LIST_OF_SENSORS,

		new String[] { BiomedicalSensorService.PROP_CONTROLS });

		return getAllbs;
	}

	private static ServiceRequest startWaitForAlertsRequest(String bsURI) {
		// System.out.println("TRYING TO START WAIT FOR ALERTS (inside SR)");
		ServiceRequest startWaitForAlertsSR = new ServiceRequest(
				new BiomedicalSensorService(), null);
		String[] ppMonitors = new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				AlertService.PROP_MONITORS };

		startWaitForAlertsSR.addValueFilter(ppMonitors,
				new CompositeBiomedicalSensor(bsURI));
		startWaitForAlertsSR.addChangeEffect(ppMonitors, new Boolean(true));

		return startWaitForAlertsSR;
	}

	private static ServiceRequest stopWaitForAlertsRequest(String bsURI) {
		// System.out.println("TRYING TO STOP WAIT FOR ALERTS (inside SR)");
		ServiceRequest stopWaitForAlertsSR = new ServiceRequest(
				new BiomedicalSensorService(), null);
		String[] ppMonitors = new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				AlertService.PROP_MONITORS };

		stopWaitForAlertsSR.addValueFilter(ppMonitors,
				new CompositeBiomedicalSensor(bsURI));
		stopWaitForAlertsSR.addChangeEffect(ppMonitors, new Boolean(false));

		return stopWaitForAlertsSR;
	}

	// *****************************************************************
	// Controller Methods
	// *****************************************************************

	// Get a list of all available light-source in the system
	public static CompositeBiomedicalSensor[] getControlledSensors() {

		ServiceResponse sr = caller.call(getAllSensorsRequest());

		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				List<?> bsList = sr.getOutput(OUTPUT_LIST_OF_SENSORS, true);

				if (bsList == null || bsList.size() == 0) {
					LogUtils.logInfo(SharedResources.moduleContext,
							BiomedicalSensorsServiceCaller.class,
							"getControlledSensors",
							new Object[] { "there are no biomedicalSensors" },
							null);
					return null;
				}

				CompositeBiomedicalSensor[] bsensors = (CompositeBiomedicalSensor[]) bsList
						.toArray(new CompositeBiomedicalSensor[bsList.size()]);

				return bsensors;

			} catch (Exception e) {
				LogUtils.logError(SharedResources.moduleContext,
						BiomedicalSensorsServiceCaller.class,
						"getControlledSensors", new Object[] { "got exception",
								e.getMessage() }, e);
				return null;
			}
		} else {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class,
					"getControlledSensors",
					new Object[] { "callstatus is not succeeded" }, null);
			return null;
		}
	}

	public static boolean getMeasurements(String bsURI) {
		// check if input is valid
		if ((bsURI == null) || !(bsURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "getMeasurements",
					new Object[] { "wrong bsURI...." }, null);
			return false;
		}

		// make a call with the appropriate request
		ServiceResponse sr = caller.call(getMeasurementsRequest(bsURI));
		LogUtils.logDebug(SharedResources.moduleContext,
				BiomedicalSensorsServiceCaller.class, "getMeasurements",
				new Object[] { "Call status: ", sr.getCallStatus().name() },
				null);

		// check the call status and return true if succeeded
		if (sr.getCallStatus() == CallStatus.succeeded) {

			List outputList = sr.getOutput(
					CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS, true);

			for (int i = 0; i < outputList.size(); i++) {
				MeasuredEntity me = (MeasuredEntity) outputList.get(i);
				// MeasuredEntity me[]=(MeasuredEntity[])
				// entity.getLastMeasurement();
				System.out.println("VALUE:" + me.getMeasurementValue()
						+ " NAME:" + me.getMeasurementName());
			}
			return true;
		} else {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "getMeasurements",
					new Object[] { "Unable to fetch measurements" }, null);
			return false;
		}
	}

	public static boolean getInfo(String bsURI) {

		if ((bsURI == null) || !(bsURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "getINFO",
					new Object[] { "wrong bsURI...." }, null);
			return false;
		}

		ServiceResponse sr = caller.call(getInfoRequest(bsURI));
		LogUtils.logDebug(SharedResources.moduleContext,
				BiomedicalSensorsServiceCaller.class, "getINFO", new Object[] {
						"Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded) {
			return true;
		} else {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "getINFO",
					new Object[] { "Could not get Sensor Info!" }, null);
			return false;
		}
	}

	public static boolean startWaitForAlerts(String bsURI) {

		if ((bsURI == null) || !(bsURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "WAIT FOR ALERTS",
					new Object[] { "wrong bsURI...." }, null);
			return false;
		}

		ServiceResponse sr = caller.call(startWaitForAlertsRequest(bsURI));
		LogUtils.logDebug(SharedResources.moduleContext,
				BiomedicalSensorsServiceCaller.class, "WAIT FOR ALERTS",
				new Object[] { "Call status: ", sr.getCallStatus().name() },
				null);

		if (sr.getCallStatus() == CallStatus.succeeded) {

			return true;
		} else {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "WAIT FOR ALERTS",
					new Object[] { "Could not start wait for Alerts" }, null);
			return false;
		}
	}

	public static boolean stopWaitForAlerts(String bsURI) {

		if ((bsURI == null) || !(bsURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "WAIT FOR ALERTS",
					new Object[] { "wrong bsURI...." }, null);
			return false;
		}

		ServiceResponse sr = caller.call(stopWaitForAlertsRequest(bsURI));
		LogUtils.logDebug(SharedResources.moduleContext,
				BiomedicalSensorsServiceCaller.class, "WAIT FOR ALERTS",
				new Object[] { "Call status: ", sr.getCallStatus().name() },
				null);

		if (sr.getCallStatus() == CallStatus.succeeded) {

			return true;
		} else {
			LogUtils.logWarn(SharedResources.moduleContext,
					BiomedicalSensorsServiceCaller.class, "WAIT FOR ALERTS",
					new Object[] { "Could not stop wait for Alerts" }, null);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContextSubscriber#handleContextEvent(ContextEvent)
	 */
	public void handleContextEvent(ContextEvent event) {

		String formatedTime = "";
		String postureValue = "";
		String tempValue = "";
		boolean alertActive = false;
		MeasuredEntity me[] = (MeasuredEntity[]) event.getRDFObject();
		List<Double> posturePlotData = new ArrayList<Double>();
		List<Double> activityPlotData = new ArrayList<Double>();
		List<Double> hrPlotData = new ArrayList<Double>();
		for (int i = 0; i < me.length; i++) {
			System.out.println("Received context event with name: "
					+ me[i].getMeasurementName() + ": "
					+ me[i].getMeasurementValue() + " "
					+ me[i].getMeasurementUnit() + " at "
					+ me[i].getMeasurementTime());
			if (me[i].getMeasurementName().equals("Posture")) {
				postureValue = me[i].getMeasurementValue();
				posturePlotData.add(Double.valueOf(postureValue));
			} else if (me[i].getMeasurementName().equals("Heart Rate")) {
				tempValue = me[i].getMeasurementValue();
				hrPlotData.add(Double.valueOf(tempValue));
			} else if (me[i].getMeasurementName().equals("Activity Level")) {
				tempValue = me[i].getMeasurementValue();
				activityPlotData.add(Double.valueOf(tempValue));
			}

			if (me[i].getMeasurementName().equals("Posture Alert")) {
				formatedTime = me[i].getMeasurementTime();
				if (me[i].getMeasurementValue().equals("true")) {
					alertActive = true;
				} else {
					alertActive = false;
					orangeAlertTime = "";
				}
				System.out.println("Orange Alert Active: " + orangeAlertActive
						+ " Red Alert Active: " + redAlertActive
						+ " Alert Active: " + alertActive);
			}
		}

		String alertType = "";
		if ((redAlertActive) && (alertActive)) {
			// When Red Alert is active a Sound Alert is played to inform the
			// user (if the sound file is stored locally)
			AePlayWave aw = new AePlayWave(
					"../app.biomedicalsensors.uiclient/siren.wav");
			aw.start();
		} else if ((!orangeAlertActive) && (alertActive)) {
			orangeAlertTime = formatedTime;
			orangeAlertPostureValue = postureValue;
			double[] ppData = convertDoubles(posturePlotData);
			double[] alData = convertDoubles(activityPlotData);
			double[] hrData = convertDoubles(hrPlotData);
			alertType = "Orange";
			/*
			 * new ImagePlot(
			 * "C:\\uaal\\workspace\\rundir\\confadmin\\ui.handler.gui.swing\\images\\"
			 * +alertType+"posture.png", ppData, "Last Posture Measurements",
			 * "Measurement", "Degrees", 350, 250); new ImagePlot(
			 * "C:\\uaal\\workspace\\rundir\\confadmin\\ui.handler.gui.swing\\images\\"
			 * +alertType+"activity.png", alData,
			 * "Last Activity Level Measurements", "Measurement", "", 350, 250);
			 * new ImagePlot(
			 * "C:\\uaal\\workspace\\rundir\\confadmin\\ui.handler.gui.swing\\images\\"
			 * +alertType+"heartrate.png", hrData,
			 * "Last Heart Rate Measurements", "Measurement", "beats/min", 350,
			 * 250);
			 */
			try {
				new ImagePlot(
						new File(
								"../app.biomedicalsensors.uiclient/Alert_image_").getCanonicalPath()
								+ alertType + "Posture.png", ppData,
						"Last Posture Measurements", "Measurement", "Degrees",
						350, 250);
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				new ImagePlot(
						new File(
								"../app.biomedicalsensors.uiclient/Alert_image_").getCanonicalPath()
								+ alertType + "Activity.png", alData,
						"Last Activity Level Measurements", "Measurement", "",
						350, 250);
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				new ImagePlot(
						new File(
								"../app.biomedicalsensors.uiclient/Alert_image_").getCanonicalPath()
								+ alertType + "Heartrate.png", hrData,
						"Last Heart Rate Measurements", "Measurement",
						"beats/min", 350, 250);
			} catch (IOException e) {

				e.printStackTrace();
			}
			Form f = al.startOrangeDialog(alertType, LevelRating.high);
			orangeAlertActive = true;

		} else if ((orangeAlertActive) && (alertActive)) {
			Date alDate = new Date(), newAlDate = new Date();
			DateFormat fm;
			fm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			if (orangeAlertTime.equals("")) {
				orangeAlertTime = formatedTime;
			}
			try {
				alDate = (Date) fm.parse(orangeAlertTime);
			} catch (ParseException e1) {

				e1.printStackTrace();
			}
			try {
				newAlDate = (Date) fm.parse(formatedTime);
			} catch (ParseException e1) {

				e1.printStackTrace();
			}
			double timeDiff = (Math.abs(newAlDate.getTime() - alDate.getTime()) / 1000);
			System.out.println("Seconds from orange alert:" + timeDiff);
			if (timeDiff > 20.0) {

				double[] ppData = convertDoubles(posturePlotData);

				double[] alData = convertDoubles(activityPlotData);
				double[] hrData = convertDoubles(hrPlotData);
				alertType = "RED";
				/*
				 * new ImagePlot(
				 * "C:\\uaal\\workspace\\rundir\\confadmin\\ui.handler.gui.swing\\images\\"
				 * +alertType+"posture.png", ppData,
				 * "Last Posture Measurements", "Measurement", "Degrees", 350,
				 * 250); new ImagePlot(
				 * "C:\\uaal\\workspace\\rundir\\confadmin\\ui.handler.gui.swing\\images\\"
				 * +alertType+"activity.png", alData,
				 * "Last Activity Level Measurements", "Measurement", "", 350,
				 * 250); new ImagePlot(
				 * "C:\\uaal\\workspace\\rundir\\confadmin\\ui.handler.gui.swing\\images\\"
				 * +alertType+"heartrate.png", hrData,
				 * "Last Heart Rate Measurements", "Measurement", "beats/min",
				 * 350, 250);
				 */

				try {
					new ImagePlot(
							new File(
									"../app.biomedicalsensors.uiclient/Alert_image_").getCanonicalPath()
									+ alertType + "Posture.png", ppData,
							"Last Posture Measurements", "Measurement",
							"Degrees", 350, 250);
				} catch (IOException e) {

					e.printStackTrace();
				}
				try {
					new ImagePlot(
							new File(
									"../app.biomedicalsensors.uiclient/Alert_image_").getCanonicalPath()
									+ alertType + "Activity.png", alData,
							"Last Activity Level Measurements", "Measurement",
							"", 350, 250);
				} catch (IOException e) {

					e.printStackTrace();
				}
				try {
					new ImagePlot(
							new File(
									"../app.biomedicalsensors.uiclient/Alert_image_").getCanonicalPath()
									+ alertType + "Heartrate.png", hrData,
							"Last Heart Rate Measurements", "Measurement",
							"beats/min", 350, 250);
				} catch (IOException e) {

					e.printStackTrace();
				}

				Form f = al.startOrangeDialog(alertType, LevelRating.full);
				redAlertActive = true;
				AePlayWave aw = new AePlayWave("C:\\siren.wav");
				aw.start();

			}
		}
		// abortDialog();

	}

	public static double[] convertDoubles(List<Double> integers) {
		double[] ret = new double[integers.size()];
		Iterator<Double> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().doubleValue();
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContextSubscriber#communicationChannelBroken()
	 */
	public void communicationChannelBroken() {

	}

}
