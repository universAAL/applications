package org.universAAL.agenda.gui;

import org.universAAL.ontology.agenda.Calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.universAAL.ontology.profile.User;
import org.universAAL.agenda.gui.components.ImagePanel;
import org.universAAL.agenda.gui.osgi.Activator;
import org.universAAL.agenda.gui.util.GuiConstants;

public class JSelectionCalendar implements IPersonaWindow {
    public static final String CARD_NAME = "JCalendarSelectCard"; //$NON-NLS-1$

    CalendarGUI parent;
    private JPanel navScreen, mainScreen, titleScreen;
    private JLabel headerMessage;
    private List<CalendarEntry> calendarEntryList = new ArrayList<CalendarEntry>();

    private JPanel calendarPanel;
    private Map<String, Calendar> nameToCalendar;
    private JCalendar myCalendar = null;

    public JSelectionCalendar(CalendarGUI parent) {
	this.parent = parent;
	this.calendarEntryList = new ArrayList<CalendarEntry>();
	this.calendarPanel = new JPanel();
	this.nameToCalendar = new HashMap<String, Calendar>();
	initComponents();
    }

    public void initComponents() {
	this.mainScreen = createMainScreen();
	this.navScreen = createNavScreen();
	this.titleScreen = createTitleScreen();

	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new BorderLayout());
	centerPanel.add(this.titleScreen, BorderLayout.NORTH);
	centerPanel.add(this.mainScreen, BorderLayout.CENTER);
	mainPanel.add(centerPanel, BorderLayout.CENTER);
	mainPanel.add(this.navScreen, BorderLayout.EAST);
	this.parent.addNewCardFrame(mainPanel, CARD_NAME);
	this.parent.showScreen(CARD_NAME);
    }

    private JPanel createTitleScreen() {
	ImageIcon persona_logo = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/month_header.jpg")); //$NON-NLS-1$
	headerMessage = new JLabel();

	updateTitleScreen();

	headerMessage.setForeground(GuiConstants.headerMessageForeground);
	headerMessage.setFont(GuiConstants.headerFont); //$NON-NLS-1$

	JPanel headPanel = new ImagePanel(persona_logo.getImage()); // (persona_logo.getImage());
	headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	headPanel.setBackground(GuiConstants.headerPanelBackground);

	headPanel.add(headerMessage);

	// breadcrumbs
	JLabel l = new JLabel(Messages
		.getString("JEventInfo.Breadcrumb.Home.SearchEvents"));
	l.setBackground(GuiConstants.breadcrumbsLabelColor);
	l.setFont(GuiConstants.breadcrumbsLabelFont);

	JPanel whole = new JPanel(new FlowLayout(FlowLayout.LEFT));
	whole.setBackground(GuiConstants.wholePanelBackground);
	whole.add(headPanel);
	whole.add(l);
	return whole;
    }

    private JPanel createMainScreen() {
	final ImageIcon selectAll_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/select_all.jpg")); //$NON-NLS-1$
	final ImageIcon selectAllHover_icon = new ImageIcon(getClass()
		.getResource(
			IconsHome.getIconsHomePath() + "/select_all_hover.jpg")); //$NON-NLS-1$
	final ImageIcon selectNone_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/select_none.jpg")); //$NON-NLS-1$
	final ImageIcon selectNoneHover_icon = new ImageIcon(
		getClass()
			.getResource(
				IconsHome.getIconsHomePath()
					+ "/select_none_hover.jpg")); //$NON-NLS-1$

	updateCalendarsInScreen();

	JScrollPane mainSP = new JScrollPane(calendarPanel);
	mainSP.setPreferredSize(new Dimension(600, 520));
	mainSP
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	mainSP
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	mainSP.setBorder(null);
	JPanel root = new JPanel(new BorderLayout());
	root.setBackground(GuiConstants.wholePanelBackground);

	JPanel inter = new JPanel();
	inter.setBackground(GuiConstants.wholePanelBackground);
	inter.add(mainSP);
	root.add(inter, BorderLayout.CENTER);
	JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	footer.setBackground(GuiConstants.wholePanelBackground);

	JButton selectAll = new JButton(selectAll_icon);
	selectAll.setFocusPainted(false);
	selectAll.setBorderPainted(false);
	selectAll.setContentAreaFilled(false);

	selectAll.setPressedIcon(selectAllHover_icon);
	selectAll.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		selectCalendars(true);
	    }
	});

	JButton selectNone = new JButton(selectNone_icon);
	selectNone.setFocusPainted(false);
	selectNone.setBorderPainted(false);
	selectNone.setContentAreaFilled(false);

	selectNone.setPressedIcon(selectNoneHover_icon);
	selectNone.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		selectCalendars(false);
	    }
	});

	footer.add(selectAll);
	footer.add(selectNone);
	root.add(footer, BorderLayout.SOUTH);

	return root;
    }

    private void selectCalendars(boolean setSelected) {
	for (CalendarEntry ce : this.calendarEntryList)
	    ce.setSelected(setSelected);
    }

    // Select a calendar screen
    public JPanel createNavScreen() {
	JPanel nav = new JPanel();
	ImageIcon add_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/add.jpg")); //$NON-NLS-1$
	ImageIcon remove_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/delete.jpg")); //$NON-NLS-1$
	ImageIcon show_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/next.jpg")); //$NON-NLS-1$
	ImageIcon exit_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/exit.jpg")); //$NON-NLS-1$

	JButton add = new JButton(add_icon);
	add.setFocusPainted(false);
	add.setBorderPainted(false);
	add.setContentAreaFilled(false);
	add.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		showNewCalendarDialog();
	    }
	});

	JButton remove = new JButton(remove_icon);
	remove.setFocusPainted(false);
	remove.setBorderPainted(false);
	remove.setContentAreaFilled(false);
	remove.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		showDeleteConfirmationDialog();
	    }
	});

	JButton show = new JButton(show_icon);
	show.setFocusPainted(false);
	show.setBorderPainted(false);
	show.setContentAreaFilled(false);
	show.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		proceedToCalendarView();
	    }
	});

	JButton exitb = new JButton(exit_icon);
	exitb.setFocusPainted(false);
	exitb.setBorderPainted(false);
	exitb.setContentAreaFilled(false);
	exitb.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		closeCalendarGUI();
	    }
	});

	JButton voice = new VoiceButton();

	// Create the right zone of buttons
	nav.setLayout(new GridLayout(6, 1));
	nav.setBackground(GuiConstants.wholePanelBackground);

	// SC2011 dodan sat i zakomentirana linija
	// nav.add(new JLabel()); //<<Create a void combined with
	// nav.setLayout(new GridLayout(6, 1));

	AnalogClock clock = new AnalogClock();
	nav.add(clock);
	nav.add(add);
	nav.add(remove);
	nav.add(show);
	nav.add(exitb);
	nav.add(voice);

	return nav;
    }

    public JPanel getMainScreenPanel() {
	return this.mainScreen;
    }

    public JPanel getNavigationScreenPanel() {
	return this.navScreen;
    }

    public JPanel getTitleScreenPanel() {
	return this.titleScreen;
    }

    private void updateTitleScreen() {

	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\"> " + //$NON-NLS-1$
			"AGENDA"
			+ //$NON-NLS-1$
			" <p></font>" //$NON-NLS-1$
			+ "<font size=\"5\" color=\"#2f594f\">" + //$NON-NLS-1$
			Messages
				.getString("JSelectionCalendar.AddRemoveAndBrowseYourCalendars") + //$NON-NLS-1$
			"</font>"); //$NON-NLS-1$
    }

    private void overrideTitle(String headerText, String explanatoryText) {
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\">" //$NON-NLS-1$
			+ headerText + "</font><br/>" //$NON-NLS-1$
			+ "<font size=\"5\" color=\"#2f594f\">" //$NON-NLS-1$
			+ explanatoryText + "</font></html>"); //$NON-NLS-1$
    }

    public CalendarGUI getParentScreen() {
	return this.parent;
    }

    private List<Calendar> getCalendarsByOwner(User owner) {
	return parent.getCalendarsByOwner(owner);
    }

    private NewCalendarFrame ff = null;

    private void showNewCalendarDialog() {
	if (ff == null) {
//	    System.out
//		    .println("1. Number of calendars: " + this.nameToCalendar.keySet().size()); //$NON-NLS-1$
	    ff = new NewCalendarFrame(this.nameToCalendar.keySet());// (this.calendarNames);
	} else {
//	    System.out
//		    .println("2. Number of calendars: " + this.nameToCalendar.keySet().size()); //$NON-NLS-1$
	    ff.setExistingCalendars(this.nameToCalendar.keySet());
	    ff.setAlwaysOnTop(true);
	    ff.setVisible(true);
	}
    }

    private void showDeleteConfirmationDialog() {
	List<Calendar> selectedCalendars = getSelectedCalendars();

	if (selectedCalendars.size() == 0)
	    return;

	DeleteConfirmationDialog confimationDialog = new DeleteConfirmationDialog(
		selectedCalendars.size());
	confimationDialog.setAlwaysOnTop(true);
	confimationDialog.setVisible(true);
	//System.err.println("Did i close dialog?"); //$NON-NLS-1$
	if (confimationDialog.getConfirmation() == DeleteConfirmationDialog.CANCEL) {
	    //System.err.println("Cancel oparation"); //$NON-NLS-1$
	    return;
	}

	//System.err.println("Confirm operation"); //$NON-NLS-1$
	removeCalendars(selectedCalendars);
    }

    private void proceedToCalendarView() {
	List<Calendar> selectedCalendars = getSelectedCalendars();
	if (selectedCalendars.size() == 0)
	    return;
	this.parent.setActiveCalendars(selectedCalendars);

	if (myCalendar == null)
	    this.myCalendar = new JCalendar(this.parent);
	else
	    this.myCalendar.populateCalendar(java.util.Calendar.getInstance());
	this.parent.showScreen(JCalendar.CARD_NAME);
    }

    private void closeCalendarGUI() {
	parent.f.dispose();
    }

    private List<Calendar> getSelectedCalendars() {
	List<Calendar> cals = new ArrayList<Calendar>();
	for (CalendarEntry entry : calendarEntryList) {
	    if (entry.isSelected()) {
		cals.add(nameToCalendar.get(entry.getCalendarName()));
	    }
	}
	return cals;
    }

    private void addNewCalendar(String name, User owner) {
	Calendar c = parent.addNewCalendar(name, owner);
	if (c != null) {
	    updateCalendarsInScreen();
	}

    }

    private void removeCalendars(List<Calendar> calendars) {
	boolean atLeastOneRemoved = false;
	for (Calendar cal : calendars) {
	    boolean removed = parent.removeCalendar(cal);
	    if (removed == true)
		atLeastOneRemoved = true;
	}

	if (atLeastOneRemoved) {
	    updateCalendarsInScreen();
	}
    }

    private void updateCalendarsInScreen() {

	// get not all calendars, but the user's ones
	List<Calendar> allCalendars = getCalendarsByOwner(Activator.testUser);
	int calNo = allCalendars.size();
	int minCalPositions = 7; // gui appearance issue
	GridLayout g = new GridLayout((calNo + 1 > minCalPositions) ? calNo
		: minCalPositions, 1);
	g.setVgap(3);
	calendarPanel.removeAll();
	calendarPanel.setLayout(g);
	calendarPanel.setBackground(GuiConstants.wholePanelBackground);

	if (calNo + 1 <= minCalPositions)
	    calendarPanel.add(new JLabel());
	calendarEntryList = new ArrayList<CalendarEntry>(calNo);

	// clear map and rebuild it
	nameToCalendar.clear();
	for (Calendar calendar : allCalendars) {
	    CalendarEntry ce = new CalendarEntry(calendar.getName(), false);
	    ce.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
		    GuiConstants.jSelectionCalendarBorderColor, null, null,
		    null));
	    calendarPanel.add(ce);
	    calendarEntryList.add(ce);
	    // calendarNames.add(calendar.getName().toLowerCase());
	    nameToCalendar
		    .put(calendar.getName()/* .toLowerCase() */, calendar);
	}
	calendarPanel.updateUI();
    }

    class NewCalendarFrame extends JFrame {
	private static final long serialVersionUID = 769864332762979644L;
	final ImageIcon setIcon = new ImageIcon(NewCalendarFrame.class
		.getResource(IconsHome.getIconsHomePath() + "/set.jpg")); //$NON-NLS-1$
	final ImageIcon setHoverIcon = new ImageIcon(NewCalendarFrame.class
		.getResource(IconsHome.getIconsHomePath() + "/set_hover.jpg")); //$NON-NLS-1$
	final ImageIcon cancelIcon = new ImageIcon(NewCalendarFrame.class
		.getResource(IconsHome.getIconsHomePath() + "/cancel.jpg")); //$NON-NLS-1$
	final ImageIcon cancelHoverIcon = new ImageIcon(
		NewCalendarFrame.class.getResource(IconsHome.getIconsHomePath()
			+ "/cancel_hover.jpg")); //$NON-NLS-1$

	private Set<String> existingCalendars;
	private boolean isNameValid;
	private String candidateCalendarName;
	final String collisionName = "<html>" + //$NON-NLS-1$
		Messages
			.getString("JSelectionCalendar.SorryThisNameAlreadyExists") + //$NON-NLS-1$
		".<p/> "
		+ //$NON-NLS-1$
		Messages
			.getString("JSelectionCalendar.PleaseChooseAnAlternativeOne") + //$NON-NLS-1$
		"</html>"; //$NON-NLS-1$
	final String emptyName = Messages
		.getString("JSelectionCalendar.YouhaveToSpecifyAName"); //$NON-NLS-1$
	final String okMessage = Messages
		.getString("JSelectionCalendar.CalendarnameIsOK"); //$NON-NLS-1$
	final JLabel causionLabel = new JLabel("", SwingConstants.RIGHT); //$NON-NLS-1$
	final JTextField calendarName = new JTextField(18);

	NewCalendarFrame(Set<String> existingCalendars) {
	    this.existingCalendars = existingCalendars;
	    System.out.println("List"); //$NON-NLS-1$
	    for (String string : existingCalendars) {
		System.out.println(string);
	    }
	    this.isNameValid = false;
	    initComponents();
	}

	void initComponents() {
	    this.setUndecorated(true);// << K.S.
	    this
		    .getRootPane()
		    .setBorder(
			    BorderFactory
				    .createLineBorder(GuiConstants.jSelectionCalendarRootBorderColor));
	    //this.setTitle(Messages.getString("JSelectionCalendar.CalendarName")); //$NON-NLS-1$

	    this.setLayout(new BorderLayout());

	    JPanel main = new JPanel(new GridLayout(3, 1));
	    main.setBackground(GuiConstants.wholePanelBackground);

	    String message = Messages
		    .getString("JSelectionCalendar.GiveANameForTheNewCalendar"); //$NON-NLS-1$
	    JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
	    messageLabel.setFont(GuiConstants.mediumFont);

	    JPanel center = new JPanel();
	    center.setBackground(GuiConstants.wholePanelBackground);

	    JLabel nameL = new JLabel(Messages
		    .getString("JSelectionCalendar.Name") + //$NON-NLS-1$
		    ":   "); //$NON-NLS-1$
	    nameL.setFont(GuiConstants.mediumFont);

	    center.add(nameL);
	    center.add(calendarName);

	    JPanel south = new JPanel();
	    south.setBackground(GuiConstants.wholePanelBackground);
	    // south.setBorder(BorderFactory.createTitledBorder(""));
	    causionLabel.setFont(GuiConstants.mediumFont);
	    causionLabel
		    .setForeground(GuiConstants.jSelectionCalendarCausionLabelRed);
	    // causionLabel.setVisible(false);
	    south.add(causionLabel);

	    main.add(messageLabel);
	    main.add(center);
	    main.add(south);

	    JPanel footer = new JPanel(new GridLayout(2, 1));
	    final JButton okButton = new JButton(setIcon);
	    okButton.setFocusPainted(false);
	    okButton.setBorderPainted(false);
	    okButton.setContentAreaFilled(false);
	    okButton.setPressedIcon(setHoverIcon);
	    okButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if (hasValidName()) {
			saveNewCalendar(getCandidateCalendarName(),
				Activator.testUser);
			calendarName.setText(""); // reset text //$NON-NLS-1$
			emptyCalendarName();
			dispose();
		    }
		}
	    });

	    final JButton cancelButton = new JButton(cancelIcon);
	    cancelButton.setFocusPainted(false);
	    cancelButton.setBorderPainted(false);
	    cancelButton.setContentAreaFilled(false);
	    cancelButton.setPressedIcon(cancelHoverIcon);

	    cancelButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    calendarName.setText(""); // reset text //$NON-NLS-1$
		    emptyCalendarName();
		    dispose();
		}
	    });

	    calendarName.setFont(GuiConstants.mediumFont);
	    calendarName
		    .setForeground(GuiConstants.jSelectionCalendarCalendarName);
	    // at the beginning there is an invalid calendar name (empty string)
	    causionLabel.setText(emptyName);
	    causionLabel
		    .setBackground(GuiConstants.jSelectionCalendarCausionLabelGreen);
	    calendarName
		    .setBackground(GuiConstants.jSelectionCalendarTextFieldBackground);

	    calendarName.addKeyListener(new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
		    String calName = (calendarName.getText() + e.getKeyChar())
			    .trim();
		    // String text = calName.toLowerCase();

		    // '\u007F' : the delete character
		    if (calName.isEmpty() || ("\u007F".equals(calName))) { //$NON-NLS-1$
			emptyCalendarName();
		    } else if (containsIgnoreCase(existingCalendars, calName)) {
			invalidCalendarName();
		    } else {
			validCalendarName(calName);
		    }
		}
	    });

	    footer.add(okButton);
	    footer.add(cancelButton);
	    footer.setBackground(GuiConstants.wholePanelBackground);

	    this.add(main, BorderLayout.CENTER);
	    this.add(footer, BorderLayout.EAST);
	    this.pack();
	    this.setAlwaysOnTop(true);// -------------
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    // Center POP-UP<---K.S
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension window = this.getSize();
	    this.setLocation((screen.width - window.width) / 2,
		    (screen.height - window.height) / 2);
	}

	private boolean containsIgnoreCase(Set<String> set, String name) {
	    for (String string : set) {
		if (string.equalsIgnoreCase(name))
		    return true;
	    }
	    return false;
	}

	void emptyCalendarName() {
	    causionLabel.setText(emptyName);
	    causionLabel
		    .setBackground(GuiConstants.jSelectionCalendarCausionLabelGreen);
	    calendarName
		    .setBackground(GuiConstants.jSelectionCalendarTextFieldBackground);
	    isValidName(false);
	}

	void invalidCalendarName() {
	    // okButton.setEnabled(false);
	    causionLabel.setText(collisionName);
	    causionLabel
		    .setBackground(GuiConstants.jSelectionCalendarCausionLabelGreen);
	    calendarName
		    .setBackground(GuiConstants.jSelectionCalendarTextFieldBackground);
	    isValidName(false);
	}

	void validCalendarName(String name) {
	    // okButton.setEnabled(true);
	    causionLabel.setText(okMessage);
	    causionLabel
		    .setBackground(GuiConstants.jSelectionCalendarCausionLabelRed);
	    calendarName.setBackground(GuiConstants.wholePanelBackground);
	    setCandidateCalendarName(name);
	    isValidName(true);
	}

	void isValidName(boolean isValid) {
	    this.isNameValid = isValid;
	}

	boolean hasValidName() {
	    return this.isNameValid;
	}

	void setCandidateCalendarName(String name) {
	    this.candidateCalendarName = name;
	}

	String getCandidateCalendarName() {
	    return this.candidateCalendarName;
	}

	void saveNewCalendar(String name, User owner) {
	    addNewCalendar(name, owner);
	}

	void setExistingCalendars(Set<String> existingCalendars) {
	    this.existingCalendars = existingCalendars;
	}
    }

}

class DeleteConfirmationDialog extends JDialog {
    private static final long serialVersionUID = -944973719622883424L;
    private final String infoText;
    private ImageIcon danger_icon = new ImageIcon(
	    DeleteConfirmationDialog.class.getResource(IconsHome
		    .getIconsHomePath()
		    + "/icon_danger_small.jpg")); //$NON-NLS-1$
    private ImageIcon yesMiniIcon = new ImageIcon(
	    DeleteConfirmationDialog.class.getResource(IconsHome
		    .getIconsHomePath()
		    + "/yes_mini.jpg")); //$NON-NLS-1$
    private ImageIcon yesMiniHoverIcon = new ImageIcon(
	    DeleteConfirmationDialog.class.getResource(IconsHome
		    .getIconsHomePath()
		    + "/yes_mini_hover.jpg")); //$NON-NLS-1$
    private ImageIcon noMiniIcon = new ImageIcon(DeleteConfirmationDialog.class
	    .getResource(IconsHome.getIconsHomePath() + "/no_mini.jpg")); //$NON-NLS-1$
    private ImageIcon noMiniHoverIcon = new ImageIcon(
	    DeleteConfirmationDialog.class.getResource(IconsHome
		    .getIconsHomePath()
		    + "/no_mini_hover.jpg")); //$NON-NLS-1$
    public static final int OK = 1;
    public static final int CANCEL = 0;
    private int buttonPressed;

    public DeleteConfirmationDialog(final int count) {
	if (count > 1)
	    this.infoText = "<html>&nbsp;&nbsp; " + //$NON-NLS-1$
		    Messages
			    .getString("JSelectionCalendar.AreYouSureYouWantToRemoveTheseCalendars") + //$NON-NLS-1$
		    "</p>(" //$NON-NLS-1$
		    + count
		    + " " //$NON-NLS-1$
		    + Messages.getString("JSelectionCalendar.Calendars") + //$NON-NLS-1$
		    ")<p/>" //$NON-NLS-1$
		    + "&nbsp;&nbsp; " + //$NON-NLS-1$
		    Messages
			    .getString("JSelectionCalendar.AlongWiththeCalendars") + //$NON-NLS-1$
		    ", <p/>&nbsp;&nbsp; "
		    + //$NON-NLS-1$
		    Messages
			    .getString("JSelectionCalendar.AllAssociatedEventsWillBeRemovedToo") + //$NON-NLS-1$
		    "</html>"; //$NON-NLS-1$
	else
	    this.infoText = "<html>&nbsp;&nbsp; " + //$NON-NLS-1$
		    Messages
			    .getString("JSelectionCalendar.AreYouSureYouWantToRemoveThisCalendar") + //$NON-NLS-1$
		    " <p/>" //$NON-NLS-1$
		    + "&nbsp;&nbsp; " + //$NON-NLS-1$
		    Messages
			    .getString("JSelectionCalendar.AlongWiththeCalendar") + //$NON-NLS-1$
		    ", <p/>&nbsp;&nbsp; " + //$NON-NLS-1$
		    Messages
			    .getString("JSelectionCalendar.AllAssociatedEventsWillBeRemovedToo"); //$NON-NLS-1$
	buttonPressed = CANCEL;
	initComponents();
    }

    private void initComponents() {
	this.setUndecorated(true);// << K.S.
	this
		.getRootPane()
		.setBorder(
			BorderFactory
				.createLineBorder(GuiConstants.jSelectionCalendarRootBorderColor));

	this.setModalityType(ModalityType.APPLICATION_MODAL);
	this.setBackground(GuiConstants.wholePanelBackground);
	this.setLayout(new BorderLayout());
	this.setBackground(GuiConstants.wholePanelBackground);

	JPanel center = new JPanel();
	center.setBackground(GuiConstants.wholePanelBackground);
	JLabel info = new JLabel(infoText, SwingConstants.RIGHT);// ,
	// danger_icon,
	// SwingConstants.LEFT);
	JLabel icon = new JLabel(danger_icon);
	info.setFont(GuiConstants.mediumFont); //$NON-NLS-1$

	JPanel footer = new JPanel();
	footer.setBackground(GuiConstants.wholePanelBackground);
	final JButton yesButton = new JButton(yesMiniIcon);
	yesButton.setFocusPainted(false);
	yesButton.setBorderPainted(false);
	yesButton.setContentAreaFilled(false);
	yesButton.setPressedIcon(yesMiniHoverIcon);
	yesButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		buttonPressed = OK;
		dispose();
	    }
	});

	final JButton noButton = new JButton(noMiniIcon);
	noButton.setFocusPainted(false);
	noButton.setBorderPainted(false);
	noButton.setContentAreaFilled(false);
	noButton.setPressedIcon(noMiniHoverIcon);

	noButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		buttonPressed = CANCEL;
		dispose();
	    }
	});

	footer.add(yesButton);
	footer.add(noButton);
	center.add(icon);
	center.add(info);
	this.add(center, BorderLayout.CENTER);
	this.add(footer, BorderLayout.SOUTH);
	this.pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	// Center POP-UP<---K.S
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension window = this.getSize();
	this.setLocation((screen.width - window.width) / 2,
		(screen.height - window.height) / 2);
    }

    public int getConfirmation() {
	return this.buttonPressed;
    }
}
