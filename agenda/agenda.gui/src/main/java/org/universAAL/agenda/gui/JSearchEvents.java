package org.universAAL.agenda.gui;

import org.universAAL.ontology.agenda.Event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.universAAL.agenda.gui.components.ImagePanel;
import org.universAAL.agenda.gui.osgi.Activator;
import org.universAAL.agenda.gui.util.DateUtilities;

public class JSearchEvents extends JPanel implements PersonaWindow {
    private static final long serialVersionUID = 1449159192602147400L;
    public static final String CARD_NAME = "JSearchEvents"; //$NON-NLS-1$

    private static final Color greenFont = new Color(0, 102, 102);
    public static final int MAIN_SCREEN = 1;
    public static final int NAVIGATION_SCREEN = 2;
    private static String[] resultsNumber = { "5", "10", "15", "20", "25+" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    public JPanel mainScreen;
    private JPanel navScreen;
    private JPanel titleScreen;
    private CalendarGUI parent;
    private JButton home, search, voice;
    private final ImageIcon search_icon = new ImageIcon(getClass().getResource(
	    Activator.ICON_PATH_PREFIX + "/search.jpg")); //$NON-NLS-1$
    private final ImageIcon home_icon = new ImageIcon(getClass().getResource(
	    Activator.ICON_PATH_PREFIX + "/main.jpg")); //$NON-NLS-1$
    private JTextField descTextField;
    private JComboBox monthCB, yearCB, rNoCB, categoryList;
    private JButton presentBut, limitBut;
    private JLabel headerMessage;
    private JEventList eventScreen;

    private final ImageIcon trueIcon = new ImageIcon(getClass().getResource(
	    Activator.ICON_PATH_PREFIX + "/yes_small.jpg")); //$NON-NLS-1$
    private final ImageIcon falseIcon = new ImageIcon(getClass().getResource(
	    Activator.ICON_PATH_PREFIX + "/no_small.jpg")); //$NON-NLS-1$

    public JSearchEvents(CalendarGUI parent, JEventList eventScreen) {
	this.parent = parent;
	this.eventScreen = eventScreen;
	initComponents();
    }

    private void initComponents() {
	this.navScreen = createNavScreen();
	this.mainScreen = createMainScreen();
	this.titleScreen = createTitleScreen();

	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new BorderLayout());
	centerPanel.add(this.titleScreen, BorderLayout.NORTH);
	centerPanel.add(this.mainScreen, BorderLayout.CENTER);
	mainPanel.add(centerPanel, BorderLayout.CENTER);
	mainPanel.add(this.navScreen, BorderLayout.EAST);
	this.parent.addNewCardFrame(mainPanel, CARD_NAME);
    }

    private JPanel createTitleScreen() {
	ImageIcon persona_logo = new ImageIcon(getClass().getResource(
		Activator.ICON_PATH_PREFIX + "/month_header.jpg")); //$NON-NLS-1$
	headerMessage = new JLabel();

	updateTitle();

	headerMessage.setForeground(Color.white);
	headerMessage.setFont(new Font("MyriadPro", Font.PLAIN, 35)); //$NON-NLS-1$

	JPanel headPanel = new ImagePanel(persona_logo.getImage()); // (persona_logo.getImage());
	headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	headPanel.setBackground(Color.white);

	headPanel.add(headerMessage);

	//breadcrumbs
	JLabel l = new JLabel(
		"<html><font face=\"MyriadPro\" size=\"5\" >"+Messages.getString("JEventInfo.Breadcrumb.Home.SearchEvents")+"</font></html>");
	l.setBackground(Color.white);

	JPanel whole = new JPanel(new FlowLayout(FlowLayout.LEFT));
	whole.setBackground(Color.white);
	whole.add(headPanel);
	whole.add(l);
	return whole;
    }

    public void updateTitle() {
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\"> " + //$NON-NLS-1$
			Messages
				.getString("JSearchEvents.SearchForAppointments") + //$NON-NLS-1$
			" </font>"); //$NON-NLS-1$
    }

    public JPanel createMainScreen() {
	JPanel main = new JPanel(new GridBagLayout());
	main.setBackground(Color.WHITE);

	// panel for category search
	JPanel categoryP = new JPanel(new GridLayout(1, 1));
	TitledBorder b = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.SearchByCategory") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b.setTitleFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	b.setTitleColor(new Color(0x565656));
	categoryP.setBorder(b);
	categoryP.setBackground(Color.WHITE);

	List<String> allCategories = this.parent.getAllEventCategories();
	allCategories.add(""); //$NON-NLS-1$
	Collections.sort(allCategories);
	categoryList = new JComboBox(allCategories.toArray());
	categoryList.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	categoryList.setForeground(Color.BLUE);
	categoryList.setBackground(Color.white);
	categoryList.setOpaque(true);
	categoryList.setEditable(true);
	categoryP.add(categoryList);

	// panel for description search
	JPanel descriptionP = new JPanel(new GridLayout(1, 1));
	TitledBorder b1 = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.SearchByDescription") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b1.setTitleFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	b1.setTitleColor(new Color(0x565656));
	descriptionP.setBorder(b1);
	descriptionP.setBackground(Color.WHITE);

	descTextField = new JTextField(32);
	descTextField.setHorizontalAlignment(JTextField.LEFT);
	descTextField.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	descTextField.setForeground(greenFont);
	descTextField.setBackground(Color.white);
	descTextField.setOpaque(true);
	descTextField.setEnabled(true);
	descriptionP.add(descTextField);

	// panel for time search
	GridLayout gl = new GridLayout(2, 2);
	gl.setVgap(5);
	JPanel timeMainP = new JPanel(new BorderLayout());
	JPanel timeP = new JPanel(gl);
	TitledBorder b2 = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.SearchByDescription") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b2.setTitleFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	b2.setTitleColor(new Color(0x565656));
	timeP.setBorder(b2);
	timeP.setBackground(Color.WHITE);

	JLabel monthL = new JLabel(Messages
		.getString("JSearchEvents.PickAMonth") + //$NON-NLS-1$
		":               "); //$NON-NLS-1$
	monthL.setHorizontalAlignment(JLabel.RIGHT);
	monthL.setFont(new Font("MyriadPro", Font.PLAIN, 20)); //$NON-NLS-1$
	monthL.setForeground(Color.BLUE);
	monthL.setBackground(Color.white);
	monthL.setOpaque(true);
	JLabel yearL = new JLabel(
		Messages.getString("JSearchEvents.PickAYear") + //$NON-NLS-1$
			":             "); //$NON-NLS-1$
	yearL.setHorizontalAlignment(JLabel.RIGHT);
	yearL.setFont(new Font("MyriadPro", Font.PLAIN, 20)); //$NON-NLS-1$
	yearL.setForeground(greenFont);
	yearL.setBackground(Color.white);
	yearL.setOpaque(true);

	yearCB = new JComboBox(buildYearSelectionCombo());
	yearCB.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	yearCB.setForeground(Color.BLUE);
	yearCB.setBackground(Color.white);
	yearCB.setOpaque(true);

	monthCB = new JComboBox(DateUtilities.months);
	monthCB.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	monthCB.setForeground(greenFont);
	monthCB.setBackground(Color.white);
	monthCB.setOpaque(true);
	monthCB.setEnabled(false);
	yearCB.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (yearCB.getSelectedIndex() == 0) {
		    monthCB.setEnabled(false);
		} else {
		    monthCB.setEnabled(true);
		}
	    }
	});

	timeP.add(monthL);
	timeP.add(monthCB);
	timeP.add(yearL);
	timeP.add(yearCB);
	timeMainP.add(timeP, BorderLayout.NORTH);
	JPanel empty = new JPanel();
	empty.setBackground(Color.WHITE);
	timeMainP.add(empty, BorderLayout.CENTER);

	// Miscellaneous options
	JPanel miscP = new JPanel(new GridLayout(2, 2));
	TitledBorder b4 = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.MiscOptions") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b4.setTitleFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	b4.setTitleColor(new Color(0x565656));
	miscP.setBorder(b4);
	miscP.setBackground(Color.WHITE);

	JLabel onlyUpcomming = new JLabel(
		"<html>" + //$NON-NLS-1$
			Messages
				.getString("JSearchEvents.ShoWOnlyPresentAndFurureEvents") + //$NON-NLS-1$
			"</html>"); //$NON-NLS-1$
	onlyUpcomming.setHorizontalAlignment(JLabel.LEFT);
	onlyUpcomming.setFont(new Font("MyriadPro", Font.PLAIN, 20)); //$NON-NLS-1$
	onlyUpcomming.setForeground(greenFont);
	onlyUpcomming.setBackground(Color.white);
	onlyUpcomming.setOpaque(true);

	presentBut = new JButton(falseIcon);
	presentBut.setFocusPainted(false);
	presentBut.setBorderPainted(false);
	presentBut.setContentAreaFilled(false);
	presentBut.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (presentBut.getIcon() == trueIcon) {
		    presentBut.setIcon(falseIcon);
		} else {
		    presentBut.setIcon(trueIcon);
		}
	    }
	});

	JLabel limitEvents = new JLabel("<html>" + //$NON-NLS-1$
		Messages.getString("JSearchEvents.MaximumNumberOfEvents") + //$NON-NLS-1$
		": <html>"); //$NON-NLS-1$
	limitEvents.setHorizontalAlignment(JLabel.LEFT);
	limitEvents.setFont(new Font("MyriadPro", Font.PLAIN, 20)); //$NON-NLS-1$
	limitEvents.setForeground(greenFont);
	limitEvents.setBackground(Color.white);
	limitEvents.setOpaque(true);

	JPanel dummy1 = new JPanel(new BorderLayout());

	rNoCB = new JComboBox(resultsNumber);
	rNoCB.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	rNoCB.setForeground(Color.BLUE);
	rNoCB.setBackground(Color.white);
	rNoCB.setOpaque(true);
	rNoCB.setVisible(false);

	limitBut = new JButton(falseIcon);
	limitBut.setFocusPainted(false);
	limitBut.setBorderPainted(false);
	limitBut.setContentAreaFilled(false);
	limitBut.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (limitBut.getIcon() == trueIcon) {
		    limitBut.setIcon(falseIcon);
		    rNoCB.setSelectedIndex(resultsNumber.length - 1);
		    rNoCB.setVisible(false);
		} else {
		    limitBut.setIcon(trueIcon);
		    rNoCB.setVisible(true);
		}
	    }
	});

	dummy1.add(rNoCB, BorderLayout.WEST);
	JLabel dL3 = new JLabel(" "); //$NON-NLS-1$
	dL3.setFont(new Font("MyriadPro", Font.PLAIN, 15)); //$NON-NLS-1$
	dummy1.add(dL3, BorderLayout.CENTER);
	JLabel dL1 = new JLabel(" "); //$NON-NLS-1$
	dL1.setFont(new Font("MyriadPro", Font.PLAIN, 15)); //$NON-NLS-1$
	JLabel dL2 = new JLabel(" "); //$NON-NLS-1$
	dL2.setFont(new Font("MyriadPro", Font.PLAIN, 15)); //$NON-NLS-1$
	dummy1.add(dL1, BorderLayout.NORTH);
	dummy1.add(dL2, BorderLayout.SOUTH);
	dummy1.setBackground(Color.WHITE);
	JPanel dummy10 = new JPanel(new GridLayout(1, 2));
	dummy10.add(presentBut);
	dummy10.add(new JLabel("")); //$NON-NLS-1$
	dummy10.setBackground(Color.WHITE);
	JPanel dummy11 = new JPanel(new GridLayout(1, 2));
	dummy11.add(limitBut);
	dummy11.add(dummy1);
	dummy11.setBackground(Color.WHITE);
	miscP.add(onlyUpcomming);
	miscP.add(dummy10);
	miscP.add(limitEvents);
	miscP.add(dummy11);

	GridBagConstraints gbc = new GridBagConstraints();

	setGridBagConstraints(gbc, 0, 0, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	gbc.fill = GridBagConstraints.HORIZONTAL;
	main.add(timeMainP, gbc);
	setGridBagConstraints(gbc, 0, 1, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	main.add(categoryP, gbc);
	setGridBagConstraints(gbc, 0, 2, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	main.add(descriptionP, gbc);
	setGridBagConstraints(gbc, 0, 3, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	main.add(miscP, gbc);

	JPanel grigoro = new JPanel(new BorderLayout());
	grigoro.setBackground(Color.white);
	JLabel adeio1 = new JLabel("    "); //$NON-NLS-1$
	grigoro.add(adeio1, BorderLayout.CENTER);

	grigoro.add(main, BorderLayout.EAST);
	return grigoro;

    }

    private String[] buildYearSelectionCombo() {
	int numberOfPreviousYears = 6;

	// TODO: possible property configured externally
	int numberOfNextYears = 5;
	// TODO: possible property configured externally
	int sumYears = numberOfNextYears + numberOfPreviousYears + 1; // +1:
								      // current
								      // year
	String[] years = new String[sumYears + 1];// +1: all years
	years[0] = "<html><i>" + //$NON-NLS-1$
		Messages.getString("JSearchEvents.AllYears") + //$NON-NLS-1$
		"</i></html>"; //$NON-NLS-1$
	Calendar c = Calendar.getInstance();
	int baseYear = c.get(Calendar.YEAR) - numberOfPreviousYears;

	for (int i = 1; i <= sumYears;) {
	    years[i++] = "" + (baseYear + i); //$NON-NLS-1$
	}

	return years;

    }

    private List<Event> getFilteredEvents() {
	String category = categoryList.getSelectedItem().toString().trim();// catTextField.getText().trim();
	String description = descTextField.getText().trim();
	int month;
	int year;
	try {
	    if (!(monthCB.isEnabled()))
		month = -1;
	    else
		month = Integer.valueOf(monthCB.getSelectedIndex()).intValue();
	} catch (NumberFormatException nfe) {
	    month = -1;
	}

	try {
	    year = Integer.valueOf((String) yearCB.getSelectedItem())
		    .intValue();
	} catch (NumberFormatException nfe) {
	    year = -1;
	    month = -1;
	}

	boolean notPastEvents = false;
	if (presentBut.getIcon() == trueIcon)
	    notPastEvents = true;
	int eventMaxNo = -1;
	if (limitBut.getIcon() == trueIcon) {
	    try {
		eventMaxNo = Integer.valueOf((String) rNoCB.getSelectedItem())
			.intValue();
	    } catch (NumberFormatException nfe) {
		eventMaxNo = -1;
	    }
	}
	return parent.getFilteredEvents(category, description, year, month,
		notPastEvents, eventMaxNo);
    }

    private JPanel createNavScreen() {
	search = new JButton(search_icon);
	search.setFocusPainted(false);
	search.setBorderPainted(false);
	search.setContentAreaFilled(false);
	search.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent a) {
		List<Event> events = getFilteredEvents();
		eventScreen.updateMainScreen(events);
		eventScreen.updateTitleScreen(events.size());
		parent.showScreen(JEventList.CARD_NAME);
	    }
	});

	home = new JButton(home_icon);
	home
		.setToolTipText(Messages
			.getString("JSearchEvents.RemiveThisEvent")); //$NON-NLS-1$
	home.setFocusPainted(false);
	home.setBorderPainted(false);
	home.setContentAreaFilled(false);
	home.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent a) {
		parent.showScreen(JCalendar.CARD_NAME);
	    }
	});

	voice = new VoiceButton();

	// Create the right zone of buttons
	JPanel homeP = new JPanel(new GridLayout(6, 1));
	homeP.setBackground(Color.white);

	// SC2011 added analog clock
	// homeP.add(new JLabel()); //<< Create a void combined with
	// nav.setLayout(new GridLayout(6, 1));

	AnalogClock clock = new AnalogClock();
	homeP.add(clock);

	homeP.add(home);
	homeP.add(search);
	JLabel l1 = new JLabel(""); //$NON-NLS-1$
	l1.setBackground(Color.WHITE);
	JLabel l2 = new JLabel(""); //$NON-NLS-1$
	l1.setBackground(Color.WHITE);
	homeP.add(l1);
	homeP.add(l2);
	homeP.add(voice);

	return homeP;
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

    public static void main(String[] str) {
	JFrame f = new JFrame();
	f.add(new JSearchEvents(null, null).createMainScreen());
	f.pack();
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setGridBagConstraints(GridBagConstraints c, int gridx,
	    int gridy, int gridwidth, int gridheight, double weightx,
	    double weighty, int anchor) {
	c.gridx = gridx;
	c.gridy = gridy;
	c.gridwidth = gridwidth;
	c.gridheight = gridheight;
	c.weightx = weightx;
	c.weighty = weighty;
	c.anchor = anchor;
    }
}
