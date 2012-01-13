package org.universAAL.agenda.gui.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class HourTextField extends JTextField implements KeyListener,
		FocusListener {
	private static final long serialVersionUID = -5454892563175504145L;

	public HourTextField() {
		super();
		initListeners();
	}

	public HourTextField(int columns) {
		super(columns);
		initListeners();
	}

	private void initListeners() {
		this.addKeyListener(this);
		this.addFocusListener(this);
	}

	public void keyTyped(KeyEvent e) {
		String text = this.getText().trim();
		char c = e.getKeyChar();
		if ((c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))
			return;

		if ((c >= '0') && (c <= '9')) {
			if (this.getText().equals(this.getSelectedText())) {
				this.setText("");
				text = "";
			}
			if (("0".equals(text) || "1".equals(text))
					|| ("2".equals(text) && (c <= '3')) || text.isEmpty()) {
				return;
			}
		}
		getToolkit().beep();
		e.consume();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// setText("#");
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// setText("@");
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		this.selectAll();
	}

	public void focusLost(FocusEvent e) {
		String text = this.getText().trim();
		// if (text.length() == 0) {
		// this.setText("00");
		// return;
		// }

		if (text.length() == 1) {
			this.setText("0" + text);
			return;
		}
	}

}
