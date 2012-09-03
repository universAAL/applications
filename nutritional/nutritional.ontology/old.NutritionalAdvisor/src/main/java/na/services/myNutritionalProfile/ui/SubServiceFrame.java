package na.services.myNutritionalProfile.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

import na.miniDao.Answer;
import na.miniDao.Exercise;
import na.miniDao.Question;
import na.services.myNutritionalProfile.Constants;
import na.utils.lang.Messages;
import na.services.myNutritionalProfile.NutriProfileSubServiceLauncher;
import na.services.myNutritionalProfile.ui.question.AnswerComparator;
import na.services.myNutritionalProfile.ui.question.AnswerItem;
import na.services.myNutritionalProfile.ui.question.MyCheckBox;
import na.services.myNutritionalProfile.ui.question.MyRadioButton;
import na.services.myNutritionalProfile.ui.question.Spinner2NumberSelectorPanel;
import na.services.myNutritionalProfile.ui.question.Spinner2TimeSelectorPanel;
import na.services.myNutritionalProfile.ui.question.handler.HourHandler;
import na.services.myNutritionalProfile.ui.question.handler.MinuteHandler;
import na.services.myNutritionalProfile.ui.question.handler.NumberHandler;
import na.utils.Utils;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import na.services.myNutritionalProfile.newUI.question.NumberSelectorPanel;
//import na.services.myNutritionalProfile.newUI.question.TimeSelectorPanel;



@SuppressWarnings("serial")
public class SubServiceFrame extends AdaptivePanel implements ActionListener, ItemListener {
	private Log log = LogFactory.getLog(SubServiceFrame.class);	
	private static final int QUESTIONNAIRES_PER_PAGE = 4;
	public NutriProfileSubServiceLauncher launcher;
	private Menu secNavBar;
	public AdaptivePanel content;
	
	// active info
	private na.miniDao.Exercise[] pending_exercises;
	private int pending_current_index;
	
	//show pending questionnaires
	protected AdaptiveLabel pending_intro;
	protected List<AdaptiveLabel> pending_listTitles;
	protected List<AdaptiveLabel> pending_listDescriptions;
	protected List<AdaptiveLabel> pending_listStates;
	protected List<AdaptiveButton> pending_listButtons;
	protected AdaptiveButton pending_butPreviousQuestionnaires;
	protected AdaptiveButton pending_butNextQuestionnaires;
	List<AdaptivePanel> pending_listPanels;
	AdaptivePanel pending_panelButtons;
	protected Border pending_defaultBorder;
	
	//show single question
	public AdaptivePanel question_answers_panel;
	public AdaptiveLabel question_questionnaire_title;
	public na.widgets.textbox.AdaptiveTextBox question_text;
	public na.widgets.textbox.AdaptiveTextBox question_description;
	public AdaptiveButton question_image;
//	public List<Component> question_listAnswers;
	public AdaptiveButton question_but_next_question;
	public AdaptiveButton question_but_previous_question;
	public List<AnswerItem> answers = new ArrayList<AnswerItem>();
	public AdaptiveLabel errorLabel;
	
	public na.miniDao.Exercise[] getExercises() {
		return pending_exercises;
	}

	public void setExercises(na.miniDao.Exercise[] exercises) {
		this.pending_exercises = exercises;
	}
	
	public SubServiceFrame() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{175, 100, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			this.secNavBar = new Menu();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(secNavBar, gbc);
		}
		{
			this.content = new AdaptivePanel();
			this.content.setBorder(BorderFactory.createEmptyBorder());
			content.setLayout(new GridLayout(1, 0, 0, 0));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(content, gbc);
		}
	}

	public void getReady(NutriProfileSubServiceLauncher launch) {
		this.launcher = launch;
		secNavBar.launcher = this.launcher;
	}
	
	/**************************
	 * DRAWING METHODS
	 ***************************/
	
	public void drawPendingQuestionnaires(int start_index) {
		this.pending_current_index = start_index;
		if (pending_exercises==null || pending_exercises.length==0) {
			log.info("There's nothing to show");
			this.pending_intro.setText(Messages.Questionnaire_NoPendingQuestionnaires);
		}
		int index = 0;
		for (int i=start_index, j=0; (i<(start_index+QUESTIONNAIRES_PER_PAGE)); i++, j++) {
			log.info("start_index: "+start_index + " i: "+i + " size:");
			if ((pending_exercises==null) || (i>=pending_exercises.length) || (pending_exercises[i]==null)) {
				log.info("exercise: "+i + " is null");
				this.pending_listTitles.get(index).setText("");
				this.pending_listDescriptions.get(index).setText("");
				this.pending_listStates.get(index).setText("");
				this.pending_listButtons.get(index).setVisible(false);
				this.pending_listPanels.get(index).setBorder(BorderFactory.createEmptyBorder());
			} else {
				this.pending_listPanels.get(index).setBorder(this.pending_defaultBorder);
				na.miniDao.Exercise exercise = pending_exercises[i];
				if (exercise == null) {
					log.error("exercise is null");
					continue;
				}
				final int userQuestID = exercise.getUserID();
				final int exerciseID = exercise.getID();
				final int lastQuestionID = exercise.getLastQuestion().getID();
				this.pending_listTitles.get(index).setText(exercise.getQuestionnaire().getTitle());
				this.pending_listDescriptions.get(index).setText(exercise.getQuestionnaire().getDescription());
				this.pending_listStates.get(index).setText(exercise.getState());
				// action button
				this.pending_listButtons.get(j).setVisible(true);
				for (ActionListener listener : this.pending_listButtons.get(j).getActionListeners()) {
					this.pending_listButtons.get(j).removeActionListener(listener);
				}
				if (exercise.getState().compareTo(Constants.EXERCISE_STATE_ONGOING_str)==0) {
					this.pending_listButtons.get(j).setText(Messages.Questionnaire_Continue);
					this.pending_listButtons.get(j).addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						butContinueQuestionnaireClicked(evt, userQuestID, exerciseID, lastQuestionID);
					}
					});
				} else if (exercise.getState().compareTo(Constants.EXERCISE_STATE_NOT_STARTED_str)==0) {
					this.pending_listButtons.get(j).setText(Messages.Questionnaire_Start);
					this.pending_listButtons.get(j).addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						butStartQuestionnaireClicked(evt, userQuestID, exerciseID);
					}
					});
				} else {
					log.info("Mal rollo!");
					
				} 
			} 
			index++;
		}
		
		// but previous questionnaires
		if (start_index >= QUESTIONNAIRES_PER_PAGE) {
			this.pending_butPreviousQuestionnaires.setVisible(true);
			this.pending_butPreviousQuestionnaires.setEnabled(true);
		} else {
			this.pending_butPreviousQuestionnaires.setVisible(false);
			this.pending_butPreviousQuestionnaires.setEnabled(false);
		}
		
		// but next questionnaires
		if (pending_exercises!=null && (start_index+QUESTIONNAIRES_PER_PAGE)<pending_exercises.length) {
			this.pending_butNextQuestionnaires.setVisible(true);
			this.pending_butNextQuestionnaires.setEnabled(true);
		} else {
			this.pending_butNextQuestionnaires.setVisible(false);
			this.pending_butNextQuestionnaires.setEnabled(false);
		}
		
		// but border
		if (this.pending_butNextQuestionnaires.isVisible() || this.pending_butPreviousQuestionnaires.isVisible())
			this.pending_panelButtons.setBorder(this.pending_defaultBorder);
		else
			this.pending_panelButtons.setBorder(BorderFactory.createEmptyBorder());
	}
	
	@SuppressWarnings("unchecked")
	public void drawQuestionnaire(Exercise exercise, boolean isFirstQuestion) {
		this.answers.clear();
		Question q = exercise.getQuestionnaire().getQuestions()[0];
		
		// questionnaire title
		String questionnaire_title = "Default questionnaire title";
		if (exercise!=null && exercise.getQuestionnaire()!=null && exercise.getQuestionnaire().getTitle()!=null )
			questionnaire_title = exercise.getQuestionnaire().getTitle();
		this.question_questionnaire_title.setText(questionnaire_title);
		
		// question title
		String question_text = "Default question text";
		question_text = q.getQuestionText();
		this.question_text.setText(question_text);
		
		// question description
		String question_description = "";
		if (q.getQuestionExplanation()!=null)
			question_description = q.getQuestionExplanation();
		this.question_description.setText(question_description);
		
		// Image
		if (q.getMultimediaType()!=null) {
			if (q.getMultimediaType().compareTo(Constants.MULTIMEDIA_IMAGE_str)==0) {
				if (q.getMultimediaBytes()==null)
					log.error("NO BYTES!");
				else {
					ImageIcon ii = new ImageIcon(q.getMultimediaBytes());
					this.question_image.setIcon(ii);
					this.question_image.setSize(ii.getIconWidth(), ii.getIconHeight());
					this.question_image.setVisible(true);
				}
			} else {
				log.error("Question multimedia type NOT IMPLEMENTED");
			}
		}
		
		// ANSWERS
		if (q.getAnswers()==null) {
			log.error("Pregunta con answers= null!");
		} else if (q.getAnswers().length == 0) {
			log.error("Pregunta con 0 answers");
		}else {
			//sort answers
			Arrays.sort(q.getAnswers(), new AnswerComparator());
			if (q.getType().compareTo(Constants.ANSWER_TYPE_SINGLE)==0) {
				log.info("Question con single answer");
				ButtonGroup radioButtongroup = new ButtonGroup();
				if (q.getAnswers().length>1) {
					log.info("Multiple single answer");
					for (na.miniDao.Answer answer : q.getAnswers()) {
						MyRadioButton radio= new MyRadioButton();
						radio.setText(answer.getAnswerText());
						radio.setID(answer.getID());
						radio.addActionListener(this);
						radioButtongroup.add(radio);
						AnswerItem ansItem = new AnswerItem();
						ansItem.setId(answer.getID());
						radio.setReference(ansItem);
						if (answer.getUserValue()!=null && answer.getUserValue().compareToIgnoreCase("selected")==0) {
							radio.setSelected(true);
							this.answers.clear();
							this.answers.add(ansItem);
						}
						this.question_answers_panel.add(radio);
					}
				} else { // one answer
					na.miniDao.Answer answer = q.getAnswers()[0];
					log.info("Answer "+answer.getID() + " type: "+answer.getType());
					switch (answer.getType()) {
					case 1: {//Fixed text
						log.info("Fixed text, This doesn't really make much sense");
						MyRadioButton radio= new MyRadioButton();
						radio.setText(answer.getAnswerText());
						radio.setID(answer.getID());
						radio.addActionListener(this);
						radioButtongroup.add(radio);
						AnswerItem ansItem = new AnswerItem();
						ansItem.setId(answer.getID());
						radio.setReference(ansItem);
						if (answer.getUserValue()!=null && answer.getUserValue().compareTo(Constants.ANSWER_SELECTED)==0) {
							radio.setSelected(true);
							this.answers.clear();
							this.answers.add(ansItem);
						}
						this.question_answers_panel.add(radio);
						break;
						}
	
					case 2: //Time selector
						{
						log.info("Time selector");
//						TimeSelectorPanel time = new TimeSelectorPanel();
						Spinner2TimeSelectorPanel time = new Spinner2TimeSelectorPanel();
						AnswerItem answerItem = new AnswerItem();
						answerItem.setId(answer.getID());
						Integer value_hours = time.getHour();
						Integer value_minutes = time.getMinute();
						answerItem.setValue(value_hours + "-" + value_minutes);
						this.answers.add(answerItem);
//						time.myAnswer=answerItem;
						time.setMinuteChangeListener(new MinuteHandler(answerItem));
						time.setHourChangeListener(new HourHandler(answerItem));
						
						// restore user's answer, if exists
						String userAnswer = answer.getUserValue();
						log.info("UserValue: "+userAnswer);
						if (userAnswer!=null && userAnswer.length()==5 && userAnswer.contains("-")==true) {
							String[] items = userAnswer.split("-");
							time.setHour(items[0]);
							time.setMinute(items[1]);
						}
						this.question_answers_panel.add(time);
						break;
					}
	
					case 3: { // Height
						log.info("Height selector");
//						NumberSelectorPanel height = new NumberSelectorPanel();
						Spinner2NumberSelectorPanel height = new Spinner2NumberSelectorPanel();
						height.setTitle(Messages.Questionnaire_Centimeters);
						height.setDeafultValue(170);
						height.setMin(40);
						height.setMax(300);
						AnswerItem answerItem = new AnswerItem();
						answerItem.setId(answer.getID());
						Integer value = height.getValue();
						answerItem.setValue(value.toString());
//						height.myAnswer = answerItem;
						this.answers.add(answerItem);
						height.setChangeListener(new NumberHandler(answerItem));
						
						// restore user's answer, if exists
						String userAnswer = answer.getUserValue();
						if (userAnswer!=null && userAnswer.compareTo("null")!=0) {
							height.setValue(userAnswer);
							height.setDeafultValue(new Integer(userAnswer));
						}
						this.question_answers_panel.add(height);
						break;
					}
	
					case 4: //Weight
					{
						log.info("Weight selector");
//						NumberSelectorPanel weight = new NumberSelectorPanel();
						Spinner2NumberSelectorPanel weight = new Spinner2NumberSelectorPanel();
						weight.setTitle(Messages.Questionnaire_Kilograms);
						weight.setDeafultValue(70);
						weight.setMin(30);
						weight.setMax(300);
						AnswerItem answerItem = new AnswerItem();
						answerItem.setId(answer.getID());
						Integer value = weight.getValue();
						answerItem.setValue(value.toString());
						this.answers.add(answerItem);
//						weight.myAnswer = answerItem;
						weight.setChangeListener(new NumberHandler(answerItem));
						// restore user's answer, if exists
						String userAnswer = answer.getUserValue();
						if (userAnswer!=null && userAnswer.compareTo("null")!=0) {
							weight.setValue(userAnswer);
							weight.setDeafultValue(new Integer(userAnswer));
						}
						this.question_answers_panel.add(weight);
						break;
					}
	
					default:
						log.info("Unknown answer type! answer "+answer.getID());
						break;
					}
				}
			} else if (q.getType().compareTo(Constants.ANSWER_TYPE_MULTIPLE)==0) {
				log.info("Question con multiple answer");
				for (na.miniDao.Answer answer : q.getAnswers()) {
					log.info("Answer:" +answer.getAnswerText() + " order: "+answer.getOrder()+";  ");
					MyCheckBox check= new MyCheckBox();
					check.setID(answer.getID());
					check.setText(answer.getAnswerText());
					check.addItemListener(this);
					AnswerItem answerItem = new AnswerItem();
					answerItem.setId(answer.getID());
					check.setReference(answerItem);
					
					if (answer.getUserValue()!=null && answer.getUserValue().compareToIgnoreCase(Constants.ANSWER_SELECTED)==0) {
						check.setSelected(true);
					}
					
					this.question_answers_panel.add(check);
				}
				log.info("");
			}
		}
		// previous button
		final int exerciseID = exercise.getID();
		final int questionID = q.getID();
		final int userID = q.getUserQuestionnaireID();
		if (q.isFirst()) { //es el primero, no mostrar
			this.question_but_previous_question.setVisible(false);
		} else { // no es el primero
			log.info("no es la primera pregunta");
			this.question_but_previous_question.setVisible(true);
			Utils.removeActionsListeners(this.question_but_previous_question);
			this.question_but_previous_question.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					log.info("Cargar pregunta anterior!!");
					launcher.loadAndShowPreviousQuestion(exerciseID, questionID);
				}
			});
		}
		
		// next button
		final AdaptiveLabel error = this.errorLabel;
		final List<AnswerItem> tempAnswers = this.answers;
		Utils.removeActionsListeners(this.question_but_next_question);
		this.question_but_next_question.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				log.info("Cargar pregunta siguiente!!");
				log.info("Respuestas seleccionadas:");
				for (AnswerItem answerItem : tempAnswers) {
					log.info(" "+answerItem.getId() + " " + answerItem.getValue());
				}
				if (answers.isEmpty()) {
					log.info("ERROR: debe responder!");
					error.setText(Messages.Questionnaire_PleaseChooseAnAnswerFirst);
//					error.setBounds(550, 105, 200, 30);
					error.setVisible(true);
					error.setForeground(Color.RED);
					
				} else {
					// generate answers
					na.miniDao.Answer[] finalAnswers = new na.miniDao.Answer[tempAnswers.size()]; 
					int i=0;
					for (AnswerItem answerItem : tempAnswers) {
						log.info("Found answer: "+answerItem.getId() + " " + answerItem.getValue());
						finalAnswers[i] = new Answer();
						if (answerItem.getValue()!=null)
							finalAnswers[i].setUserValue(answerItem.getValue());
						finalAnswers[i].setID(answerItem.getId());
						i++;
					}
					launcher.loadAndshowNextQuestion(exerciseID, userID, questionID, finalAnswers);
				}
			}
		});
	}

	//BUTTONS ACTIONS
	
	private void butStartQuestionnaireClicked(ActionEvent evt,
			int userQuestID, int exerciseID) {
		log.info("Staring questionnaire: "+exerciseID);		
		this.launcher.showStartQuestionnaire(userQuestID, exerciseID);
	}

	private void butContinueQuestionnaireClicked(ActionEvent evt,
			int userQuestID, int exerciseID, int lastQuestionID) {
		log.info("continuing questionnaire: "+exerciseID);
		launcher.showContinueQuestionnaire(exerciseID, userQuestID, lastQuestionID);
	}

	protected void butMoreQuestionnairesClicked(ActionEvent evt) {
		this.drawPendingQuestionnaires(this.pending_current_index+QUESTIONNAIRES_PER_PAGE);
	}
	protected void butPreviousQuestionnairesClicked(ActionEvent evt) {
		this.drawPendingQuestionnaires(this.pending_current_index-QUESTIONNAIRES_PER_PAGE);
	}

//	@Override
	// RADIOBUTTON
	public void actionPerformed(ActionEvent e) {  
        MyRadioButton source = (MyRadioButton) e.getSource();  
        log.info("actionPerformed:" + source.getText());  
		if (source.isSelected()) {
			this.answers.clear(); //only one answer possible
			this.answers.add(source.getReference());
			this.errorLabel.setVisible(false);
		}
		log.info("Listado: "+this.answers);
    }

	// CHECKBOX
	public void itemStateChanged(ItemEvent e) {
		log.info("CheckBox clicked");
		MyCheckBox source = (MyCheckBox)e.getItemSelectable();
		log.info("Respuesta seleccionada: "+source.getID());
//		log.info("seleccionadas antes: "+this.answers.size());
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			this.answers.remove(source.getReference());
		} else {
			this.answers.add(source.getReference());
			this.errorLabel.setVisible(false);
		}
//		log.info("seleccionadas despues: "+this.answers.size());
//		log.info("Listado: "+this.answers);
	}

}
