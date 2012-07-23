package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.universAAL.AALApplication.health.motivation.MotivationInterface;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.owl.Answer;
import org.universaal.ontology.owl.AnsweredQuestionnaire;
import org.universaal.ontology.owl.Question;
import org.universaal.ontology.owl.Questionnaire;


public class Solution  implements MotivationInterface{
	
	public Solution(){
		
	}
	
	public AnsweredQuestionnaire getSolution (Questionnaire q){
		
		Question[] questions = q.getQuestions();
		
		User caregiver =  getCaregiver(getAssistedPerson());// pedirlo en el método molón de la interfaz
		
		ArrayList <Answer> answers = new ArrayList <Answer>();
		
		for (int i=0;i<questions.length;i++){
			if(questions[i].isHasCorrectAnswer()){
				Object[] correctAnswersContent = questions[i].getCorrectAnswers();
				
				/*int k=0;
				Object[] content = new Object[correctAnswersAux.length];
				
				for (int j=0;j<correctAnswersAux.length;j++){
					content[j]=correctAnswersAux[j].getAnswerContent();
				}
				*/
				
				for (int j=0;j<correctAnswersContent.length;j++){
					System.out.println("Contenidos de las soluciones correctas: " + questions[i].getQuestionWording());
					System.out.println(correctAnswersContent[j]);
					System.out.println("Tamaño del array de respuestas: " + correctAnswersContent.length);
					
				}
				
				Answer a = new Answer(correctAnswersContent, questions[i]);
				answers.add(a);	
			}
			
		}
		
		Answer[] correctAnswers = answers.toArray(new Answer[answers.size()]);
		AnsweredQuestionnaire solution = new AnsweredQuestionnaire(q,correctAnswers,caregiver);
		return solution;
	}

	public User getAssistedPerson() {
		User assistedPerson = new User("Peter");
		return assistedPerson;
	}

	public User getCaregiver(User assistedPerson) {
		User caregiver = new User("caregiver");
		return caregiver;
	}

	public HealthProfile getHealthProfile(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerClassesNeeded() {
		// TODO Auto-generated method stub
		
	}


}
