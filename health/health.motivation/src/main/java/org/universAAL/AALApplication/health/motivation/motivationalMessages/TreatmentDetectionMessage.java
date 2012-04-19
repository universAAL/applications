package org.universAAL.AALApplication.health.motivation.motivationalMessages;

public class TreatmentDetectionMessage {
	private String qWording = "$partOfDay $username! A new treatment, named $treatmentName" +
	"has been asigned to you. This treatment consists of $treatmentPurpose." +
	"Are you planning of following it?";


private ChoiceLabel choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
private ChoiceLabel choice2 = new ChoiceLabel(Boolean.FALSE, "No");
private ChoiceLabel[] booleanChoices = {choice1,choice2};

private SingleChoiceQuestion question = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);
private Questionnaire questionnaire = new Questionnaire("First inquiry", "This message will be displayed whenever a new treatment is " +
	"detected, to ask the user about his/her predisposition to follow the treatment.", question);

private Object inquiry; 

public Object getContent() {
return inquiry;
}

}
