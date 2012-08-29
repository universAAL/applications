package org.universaal.drools.ui.impl;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.drools.DroolsReasoning;
import org.universAAL.ontology.ltba.ActivityReportType;
import org.universAAL.ontology.ltba.LTBAService;

/**
 * Used for make service calls to the LTBA Service
 * 
 * @author mllorente
 * 
 */
public class LTBACaller {

	private static ServiceCaller caller;
	private ModuleContext mc;

	/**
	 * Standard constructor
	 * 
	 * @param context
	 *            ModuleContext
	 */
	public LTBACaller(ModuleContext context) {
		mc = context;
		caller = new DefaultServiceCaller(mc);
	}

	/**
	 * Switch on the LTBA Service (actually switch on the context event
	 * listening feature).
	 * 
	 * @return The service request for the specified profile.
	 */
	private static ServiceRequest switchOnRequest() {
		ServiceRequest switchOn = new ServiceRequest(new LTBAService(), null);
		switchOn.addAddEffect(
				new String[] { LTBAService.PROP_SERVICE_HAS_STATUS_VALUE },
				true);
		return switchOn;
	}

	/**
	 * Switch off the LTBA Service (actually turn off the context event
	 * listening feature).
	 * 
	 * @return The service request for the specified profile.
	 */
	private static ServiceRequest switchOffRequest() {
		ServiceRequest switchOff = new ServiceRequest(new LTBAService(), null);
		switchOff
				.addRemoveEffect(new String[] { LTBAService.PROP_SERVICE_HAS_STATUS_VALUE });
		return switchOff;
	}

	/**
	 * Print the text report.
	 * 
	 * @return The service request for the specified profile.
	 */
	private static ServiceRequest printReportRequest() {
		System.out.println("printReportRequest");
		ServiceRequest printReport = new ServiceRequest(new LTBAService(), null);
		printReport.addAddEffect(
				new String[] { LTBAService.PROP_HAS_TEXT_REPORT }, "atext");
		return printReport;
	}

	/**
	 * Print a week report.
	 * 
	 * @return The service request for the specified profile.
	 */
	private static ServiceRequest printWeekRequest() {
		System.out.println("printWeekRequest");
		ServiceRequest printWeek = new ServiceRequest(new LTBAService(), null);
		printWeek.addAddEffect(
				new String[] { LTBAService.PROP_HAS_WEEK_REPORT }, "atext");
		return printWeek;
	}

	/**
	 * Print a month report.
	 * 
	 * @return The service request for the specified profile.
	 */
	private static ServiceRequest printMonthRequest() {
		System.out.println("printMonthRequest");
		ServiceRequest printMonth = new ServiceRequest(new LTBAService(), null);
		// printMonth.addAddEffect(
		// new String[] { LTBAService.PROP_HAS_ACTIVITY_REPORT,
		// ActivityReportType.MY_URI }, ActivityReportType.month);
		printMonth.addAddEffect(
				new String[] { LTBAService.PROP_HAS_MONTH_REPORT }, "atext");
		return printMonth;
	}

	/**
	 * Switch on the LTBA Service (actually switch on the context event
	 * listening feature).
	 * 
	 * @return True if call successful. Otherwise false.
	 */
	public static boolean switchOn() {
		ServiceResponse sr = caller.call(switchOnRequest());
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			System.out
					.println("LTBA Service: the service couldn't swiched on in switchOn()");
			return false;
		}
	}

	/**
	 * Switch off the LTBA Service (actually turn off the context event
	 * listening feature).
	 * 
	 * @return True if call successful. Otherwise false.
	 */
	public static boolean switchOff() {
		ServiceResponse sr = caller.call(switchOffRequest());
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			System.out
					.println("LTBA Service: the service couldn't swiched off in switchOff()");
			return false;
		}
	}

	/**
	 * Print a day report.
	 */
	public static void printReport() {
		System.out.println("printReport");
		ServiceResponse sr = caller.call(printReportRequest());
		if (sr.getCallStatus() == CallStatus.succeeded) {
		} else {
			System.out
					.println("LTBA Service: the service couldn't print the report");
		}
	}

	/**
	 * Print a week report.
	 */
	public static void printWeek() {
		System.out.println("printWeek");
		ServiceResponse sr = caller.call(printWeekRequest());
		if (sr.getCallStatus() == CallStatus.succeeded) {
		} else {
			System.out
					.println("LTBA Service: the service couldn't print the week report");
		}
	}

	/**
	 * Print a month report.
	 */
	public static void printMonth() {
		System.out.println("printMonth");
		ServiceResponse sr = caller.call(printMonthRequest());
		if (sr.getCallStatus() == CallStatus.succeeded) {
		} else {
			System.out
					.println("LTBA Service: the service couldn't print the month report");
		}
	}
}
