package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import org.universAAL.AALApplication.health.motivation.motivationalMessages.MotivationalMessageContent;

public class MessageWithVariables implements MotivationalMessageContent{
	public String messageContent ="Good $partOfDay $username! Don't forget to carry your dog $userPetName to the veterinarian today. The date is at $veterinarianDate in $veterinarianPlace. Have a nice day!";

	public Object getMessageContent() {
		return messageContent;
	}
}
