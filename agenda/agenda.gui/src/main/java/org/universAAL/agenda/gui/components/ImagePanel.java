package org.universAAL.agenda.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * Used for loading image. Used for header image and logo.
 *
 */
public class ImagePanel extends JPanel {
    private static final long serialVersionUID = -5946348562781265321L;
    private Image img;

    public ImagePanel(String img) {
	this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
	this.img = img;
	Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	setPreferredSize(size);
	setMinimumSize(size);
	setMaximumSize(size);
	setSize(size);
	setLayout(null);
    }

    public void paintComponent(Graphics g) {
	g.drawImage(this.img, 0, 0, null);
    }
}