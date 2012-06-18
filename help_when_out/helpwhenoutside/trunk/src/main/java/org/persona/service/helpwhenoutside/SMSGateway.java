package org.persona.service.helpwhenoutside;



public interface SMSGateway {
	/**
	 * Send a message to a mobile phone
	 * @param number The number where the message is sent. It must be in the form: 
	 * InternationalCode without 00 or + character
	 * PhoneNumber
	 * Example 393481234567 where 39 is the international
	 * code for Italy and 3481234567 is the phone number
	 * @param message The message to be sent
	 * @return False if the message has not been delivered to the server, true otherwise
	 * @throws IllegalArgumentException If the number is null or not in correct form
	 */
	public boolean sendSMS(String number, String message) throws IllegalArgumentException ;

	public String getHost();
	/**
	 * Set the hostname (with the full path to the sms gateway application without the ending slash)
	 * @param host The hostname where the servlet is running, e.g. 10.128.208.17:8080/MAM
	 * or 83.224.72.209:8080
	 */
	public void setHost(String host);
}
