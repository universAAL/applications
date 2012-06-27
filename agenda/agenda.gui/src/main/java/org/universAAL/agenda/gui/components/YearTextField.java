package org.universAAL.agenda.gui.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
/**
 * Field for year text. Used in {@link DatePanel}
 * 
 */
public class YearTextField extends JTextField implements KeyListener,
	FocusListener {
    private static final long serialVersionUID = -6722036237870255921L;

    public YearTextField() {
	super();
	initListeners();
    }

    public YearTextField(int colSize) {
	super(colSize);
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

	if (c >= '0' && c <= '9') {
	    if (this.getText().equals(this.getSelectedText())) {
		this.setText("");
		text = "";
	    }
	    if (text.length() < 4) {
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
	// if (text.length() < 4) {
	// String newYear = text;
	// for (int i = text.length(); i < 4; ++i) {
	// newYear = 0 + newYear;
	// }
	// this.setText(newYear);
	// return;
	// }

	if (text.length() == 1) {
	    if (text.charAt(0) == '0') {
		this.setText("01");
		return;
	    }
	    this.setText("0" + text);
	    return;
	}
    }

}
