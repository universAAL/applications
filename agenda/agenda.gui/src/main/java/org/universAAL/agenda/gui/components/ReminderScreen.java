package org.universAAL.agenda.gui.components;

import org.universAAL.agenda.ont.ReminderType;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.universAAL.agenda.gui.Activator;
import org.universAAL.agenda.gui.util.DateTimeInstance;
import org.universAAL.agenda.gui.util.DateUtilities;
import org.universAAL.agenda.gui.util.GuiConstants;

public class ReminderScreen extends JPanel {
	private static final long serialVersionUID = 507077964351420749L;
	private DatePanel remDate;
	private JTextArea messageArea;
	private JComboBox remTypeCombo;
	private boolean isActive;
	private final ReminderPanel parent;
	private Calendar itsDate;
	private String itsMessage;
	private int itsType;
	private MiscPanel miscP;
	private int itsInterval, itsRepeatTimes;

	public ReminderScreen(ReminderPanel parent) {
		this.parent = parent;
		initComponents();

		setEditable(false);
	}

	private void initComponents() {
		ImageIcon setRemSmallIcon = new ImageIcon(getClass().getResource(
				Activator.ICON_PATH_PREFIX + "/set_reminder.jpg")); //$NON-NLS-1$
		ImageIcon setCancelIcon = new ImageIcon(getClass().getResource(
				Activator.ICON_PATH_PREFIX + "/cancel_reminder.jpg")); //$NON-NLS-1$
		final ImageIcon closeIcon = new ImageIcon(getClass().getResource(
				Activator.ICON_PATH_PREFIX + "/close_icon.jpg")); //$NON-NLS-1$
		final ImageIcon closeHoverIcon = new ImageIcon(getClass().getResource(
				Activator.ICON_PATH_PREFIX + "/close_hover_icon.jpg")); //$NON-NLS-1$
		final ImageIcon closePressedIcon = new ImageIcon(getClass()
				.getResource(
						Activator.ICON_PATH_PREFIX + "/close_pressed_icon.jpg")); //$NON-NLS-1$

		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
				Color.LIGHT_GRAY, null, null, null));
		this.setLayout(new GridBagLayout());

		remDate = new DatePanel(" " + //$NON-NLS-1$
				Messages.getString("ReminderScreen.ReminderDate") + //$NON-NLS-1$
				" "); //$NON-NLS-1$

		final JButton closeB = new JButton(closeIcon);
		closeB.setFocusPainted(false);
		closeB.setBorderPainted(false);
		closeB.setContentAreaFilled(false);

		closeB.setPressedIcon(closePressedIcon);
		closeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInvisible();
			}
		});

		closeB.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				closeB.setIcon(closeHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				closeB.setIcon(closeIcon);
			}
		});

		messageArea = new JTextArea(3, 25);
		messageArea.setFont(new Font("MyriadPro", Font.PLAIN, 20)); //$NON-NLS-1$
		messageArea.setBackground(new Color(0xf1ee9d));
		messageArea.setForeground(new Color(0x00c129));
		messageArea.setOpaque(true);
		messageArea.setEditable(true);

		JScrollPane scroll = new JScrollPane(messageArea);
		scroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel messagePanel = new JPanel();
		TitledBorder b = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
				Messages.getString("ReminderScreen.Message") + //$NON-NLS-1$
				" "); //$NON-NLS-1$
		b.setTitleFont(GuiConstants.mediumFont);
		b.setTitleColor(GuiConstants.labelColor);
		messagePanel.setBorder(b);
		messagePanel.setBackground(Color.WHITE);
		messagePanel.add(scroll);

		// ////////////////
		Object[] mi = ReminderType.getEnumerationMembers();
		remTypeCombo = new JComboBox(mi);
		remTypeCombo.setFont(new Font("MyriadPro", Font.PLAIN, 20)); //$NON-NLS-1$
		remTypeCombo.setBackground(new Color(0xf1ee9d));
		remTypeCombo.setForeground(new Color(0x00c129));
		remTypeCombo.setOpaque(true);
		remTypeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b;
				if (remTypeCombo.getSelectedIndex() == 0) {
					b = false;
				} else {
					b = true;
				}
				setEditable(b);
			}
		});
		// ////////////////

		JPanel typePanel = new JPanel();
		b = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
				Messages.getString("ReminderScreen.ReminderType") + //$NON-NLS-1$
				" "); //$NON-NLS-1$
		b.setTitleFont(GuiConstants.mediumFont);
		b.setTitleColor(GuiConstants.labelColor);
		typePanel.setBorder(b);
		typePanel.setBackground(Color.WHITE);
		typePanel.add(remTypeCombo);

		JButton setReminderB = new JButton(setRemSmallIcon);
		setReminderB.setFocusPainted(false);
		setReminderB.setBorderPainted(false);
		setReminderB.setContentAreaFilled(false);

		// setReminderB.setPressedIcon(setRemHoverIcon);
		setReminderB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeAllCurrentValues();
				if (remTypeCombo.getSelectedIndex() != 0 && (getDate() != null)) {
					parent.setReminderDate(getDate());
					parent.setReminderMessage(getReminderMessage());
					parent.setReminderType(getReminderTypeName());
					parent.setActiveReminder();
					isActive = true;
				} else {
					parent.setInactiveReminder();
					isActive = false;
				}
				setInvisible();
			}
		});

		JButton cancelReminderB = new JButton(setCancelIcon);
		cancelReminderB.setFocusPainted(false);
		cancelReminderB.setBorderPainted(false);
		cancelReminderB.setContentAreaFilled(false);

		// cancelReminderB.setPressedIcon(setCancelHoverIcon);
		cancelReminderB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInvisible();
			}
		});

		JPanel butttonPanel = new JPanel(new GridLayout(1, 2));

		butttonPanel.setBackground(Color.WHITE);
		butttonPanel.add(setReminderB);
		butttonPanel.add(cancelReminderB);

		JPanel infoPanel = new JPanel(new GridLayout(1, 2));
		infoPanel.add(remDate);

		miscP = new MiscPanel();
		infoPanel.add(miscP);

		GridBagConstraints gbc = new GridBagConstraints();

		setGridBagConstraints(gbc, 0, 0, 1, 1, 0.9, 0.1,
				GridBagConstraints.LINE_START);
		this.add(typePanel, gbc);

		setGridBagConstraints(gbc, 1, 0, 1, 1, 0.1, 0.1,
				GridBagConstraints.CENTER);
		this.add(closeB, gbc);

		setGridBagConstraints(gbc, 0, 1, 2, 1, 1, 0.3,
				GridBagConstraints.CENTER);
		this.add(messagePanel, gbc);

		setGridBagConstraints(gbc, 0, 2, 2, 1, 1, 0.4,
				GridBagConstraints.CENTER);
		this.add(infoPanel, gbc);

		setGridBagConstraints(gbc, 0, 3, 2, 1, 1, 0.2,
				GridBagConstraints.CENTER);
		this.add(butttonPanel, gbc);
	}

	public void storeAllCurrentValues() {
		storeReminderDate(remDate.getDate());
		storeReminderMessage(messageArea.getText());
		storeReminderType(remTypeCombo.getSelectedIndex());
		storeReminderInterval(getInterval());
		storeReminderRepeatTimes(getRepeatTimes());
	}

	private void setInvisible() {
		// this.setVisible(false);
		parent.closeReminderInfoScreen();
		// this.setFocusable(false);
	}

	private static void setGridBagConstraints(GridBagConstraints c, int gridx,
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

	public static void main(String[] str) {
		JFrame f = new JFrame();
		f.setBackground(Color.white);
		f.add(new ReminderScreen(null));
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	// returns null, if not specified or is invalid
	public Calendar getDate() {
		// return remDate.getDate();
		return this.itsDate;
	}

	public DateTimeInstance getDateTime() {
		return remDate.getDateTime();
	}

	public String getReminderMessage() {
		// return messageArea.getText();
		return this.itsMessage;
	}

	public int getReminderType() {
		// if return = -1 -> no reminder
		// return remTypeCombo.getSelectedIndex();
		return this.itsType;
	}

	public String getReminderTypeName() {
		return remTypeCombo.getSelectedItem().toString();
	}

	public boolean hasValidDate() {
		if (getDate() == null)
			return false;

		return true;
	}

	public boolean isReminderActive() {
		return this.isActive;
	}

	public boolean isReminderSet() {
		return (remTypeCombo.getSelectedIndex() == 0) ? false : true;
	}

	public void setEditable(boolean isEditable) {
		// if (isEditable && remTypeCombo.getSelectedIndex() == 0) return;
		remDate.setEditable(isEditable);
		messageArea.setEditable(isEditable);
		miscP.setEditable(isEditable);
		// this.isActive = isEditable;

		if (isEditable)
			messageArea.setBackground(GuiConstants.textActiveBackground);
		else {
			messageArea.setBackground(GuiConstants.textInactiveBackground);
			remTypeCombo.setSelectedIndex(0);
		}

	}

	public void setReminderDate(Calendar c) {
		if (c == null)
			return;

		DateTimeInstance dti = DateUtilities.calendar2dti(c);
		remDate.setDate(dti.date);
		remDate.setTime(dti.time);
	}

	public void storeReminderDate(Calendar c) {
		this.itsDate = c;
	}

	public void storeReminderMessage(String message) {
		this.itsMessage = message;
	}

	public void storeReminderType(int remType) {
		this.itsType = remType;
	}

	public void storeReminderInterval(int itsInterval) {
		this.itsInterval = itsInterval;
	}

	public void storeReminderRepeatTimes(int itsRepeatTimes) {
		this.itsRepeatTimes = itsRepeatTimes;
	}

	public void setReminderDate(DateTimeInstance dti) {
		if (dti == null)
			return;

		remDate.setDate(dti.date);
		remDate.setTime(dti.time);
	}

	public void setReminderMessage(String message) {
		if (message == null)
			messageArea.setText(""); //$NON-NLS-1$
		else
			messageArea.setText(message);
	}

	public void setReminderType(int reminderType) {
		// first index point to NO REMINDER
		if (reminderType == 0) {
			this.isActive = false;
		} else {
			this.isActive = true;
		}
		remTypeCombo.setSelectedIndex(reminderType);
	}

	public void clearFields() {
		remDate.clearFields();
		messageArea.setText(""); //$NON-NLS-1$
		remTypeCombo.setSelectedIndex(0);
		miscP.clearFields();
	}

	public boolean equals(Object o) {
		if (!(o instanceof ReminderScreen))
			return false;

		ReminderScreen other = (ReminderScreen) o;
		if (!(this.remDate.equals(other.remDate)))
			return false;

		if (!(this.messageArea.getText() != other.messageArea.getText()))
			return false;

		if (this.remTypeCombo.getSelectedIndex() != other.remTypeCombo
				.getSelectedIndex())
			return false;

		if (!(this.miscP.equals(other.miscP)))
			return false;

		return true;
	}

	// misc panel setters-getters

	public int getInterval() {
		return (Integer) miscP.intervalSpinner.getValue() * 1000 * 60; // minutes
		// to
		// milliseconds
	}

	public int getRepeatTimes() {
		return (Integer) miscP.repeatTimesSpinner.getValue();
	}

	public void setInterval(int milliseconds) {
		int value = (int) Math.round(milliseconds / (1000.0 * 60)); // millis to
		// minutes
		if ((value <= MiscPanel.INTERNAL_MAX)
				&& (value >= MiscPanel.INTERNAL_MIN))
			miscP.intervalSpinner.setValue(value);
	}

	public void setRepeatTimes(int times) {
		if ((times <= MiscPanel.REPEAT_MAX) && (times >= MiscPanel.REPEAT_MIN))
			miscP.repeatTimesSpinner.setValue(times);
	}

	static class MiscPanel extends JPanel {
		private static final long serialVersionUID = -4693708799917882829L;
		private static final int INTERNAL_DEFAULT = 10; // minutes
		private static final int REPEAT_DEFAULT = 0; // times
		private static final int INTERNAL_MIN = 0; // minutes
		private static final int REPEAT_MIN = 0; // times
		private static final int INTERNAL_MAX = 120; // minutes
		private static final int REPEAT_MAX = 10; // times

		MiscPanel() {
			initComponents();
		}

		void clearFields() {
			intervalSpinner.setValue(INTERNAL_DEFAULT);
			repeatTimesSpinner.setValue(REPEAT_DEFAULT);
		}

		void initComponents() {
			GridBagConstraints gbc1 = new GridBagConstraints();
			this.setLayout(new GridBagLayout());
			TitledBorder b = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
					Messages.getString("ReminderScreen.Misc") + //$NON-NLS-1$
					" "); //$NON-NLS-1$
			b.setTitleFont(GuiConstants.mediumFont);
			b.setTitleColor(GuiConstants.labelColor);
			this.setBorder(b);
			this.setBackground(Color.WHITE);

			JLabel repeatLabel = new JLabel(Messages
					.getString("ReminderScreen.RepeatsTimes") + //$NON-NLS-1$
					":"); //$NON-NLS-1$
			repeatLabel.setFont(GuiConstants.mediumFont);

			SpinnerModel model1 = new SpinnerNumberModel(REPEAT_DEFAULT,
					REPEAT_MIN, REPEAT_MAX, 1); // numbers in times
			repeatTimesSpinner = new JSpinner(model1);
			repeatTimesSpinner.setFont(GuiConstants.mediumFont);

			SpinnerModel model2 = new SpinnerNumberModel(INTERNAL_DEFAULT,
					INTERNAL_MIN, INTERNAL_MAX, 1); // number in minutes
			intervalSpinner = new JSpinner(model2);
			intervalSpinner.setFont(GuiConstants.mediumFont);
			JSpinner.DefaultEditor intervalEditor = (JSpinner.DefaultEditor) intervalSpinner
					.getEditor();
			intervalEditor.getTextField().setColumns(2);

			JLabel intervalLabel = new JLabel(Messages
					.getString("ReminderScreen.IntervalMins") + //$NON-NLS-1$
					":"); //$NON-NLS-1$
			intervalLabel.setFont(GuiConstants.mediumFont);

			setGridBagConstraints(gbc1, 0, 1, 1, 1, 0.8, 0.3,
					GridBagConstraints.LINE_START);
			gbc1.ipady = 50;
			this.add(repeatLabel, gbc1);
			setGridBagConstraints(gbc1, 1, 1, 1, 1, 0.2, 0.3,
					GridBagConstraints.LINE_END);
			gbc1.ipady = 0;
			this.add(repeatTimesSpinner, gbc1);
			setGridBagConstraints(gbc1, 0, 2, 2, 1, 1, 0.2,
					GridBagConstraints.LINE_START);
			this.add(new JLabel(), gbc1);
			setGridBagConstraints(gbc1, 0, 3, 1, 1, 0.8, 0.3,
					GridBagConstraints.LINE_START);
			this.add(intervalLabel, gbc1);
			setGridBagConstraints(gbc1, 1, 3, 1, 1, 0.2, 0.3,
					GridBagConstraints.LINE_END);
			this.add(intervalSpinner, gbc1);
		}

		void setEditable(boolean isEditable) {
			intervalSpinner.setEnabled(isEditable);
			repeatTimesSpinner.setEnabled(isEditable);

			JSpinner.DefaultEditor repeatEditor = (JSpinner.DefaultEditor) repeatTimesSpinner
					.getEditor();
			JSpinner.DefaultEditor intervalEditor = (JSpinner.DefaultEditor) intervalSpinner
					.getEditor();
			if (isEditable) {
				repeatEditor.getTextField().setBackground(
						GuiConstants.textActiveBackground);
				intervalEditor.getTextField().setBackground(
						GuiConstants.textActiveBackground);
			} else {
				repeatEditor.getTextField().setBackground(
						GuiConstants.textInactiveBackground);
				intervalEditor.getTextField().setBackground(
						GuiConstants.textInactiveBackground);
			}
		}

		public boolean equals(Object o) {
			if (!(o instanceof MiscPanel))
				return false;
			MiscPanel other = (MiscPanel) o;
			return this.intervalSpinner.getValue().equals(
					other.intervalSpinner.getValue())
					&& this.repeatTimesSpinner.getValue().equals(
							other.repeatTimesSpinner.getValue());
		}

		private JSpinner intervalSpinner, repeatTimesSpinner;
	}
}
