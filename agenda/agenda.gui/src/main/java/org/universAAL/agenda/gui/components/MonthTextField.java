package org.universAAL.agenda.gui.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
/**
 * Field for month text. Used in {@link DatePanel}
 * 
 */
public class MonthTextField extends JTextField implements KeyListener,
	FocusListener {
    private static final long serialVersionUID = -6504288830505628300L;

    public MonthTextField() {
	super();
	initListeners();
    }

    public MonthTextField(int colSize) {
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

	if (c < '0' || c > '9') {
	    getToolkit().beep();
	    e.consume();
	    return;
	}
	if (this.getText().equals(this.getSelectedText())) {
	    this.setText("");
	    text = "";
	}

	if (!(text.length() > 1)) {
	    if (text.isEmpty()) {
		return;
	    }

	    if (text.charAt(0) == '0') {
		if (c != '0') {
		    return;
		}
	    }

	    if (text.charAt(0) == '1') {
		if (c >= '0' && c <= '2') {
		    return;
		}
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
	// this.setText("01");
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
