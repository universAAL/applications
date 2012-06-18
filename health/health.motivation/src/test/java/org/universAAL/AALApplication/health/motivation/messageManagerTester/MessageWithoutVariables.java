package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import org.universAAL.AALApplication.health.motivation.motivationalMessages.MotivationalMessageContent;

public class MessageWithoutVariables implements MotivationalMessageContent {

	public String messageWithoutVariables = "Hello Peter! Today is a great day to start yogging. Come on, put on your trainers and take a walk!";
	
	public Object getMessageContent() {
		return messageWithoutVariables;
	}
}
