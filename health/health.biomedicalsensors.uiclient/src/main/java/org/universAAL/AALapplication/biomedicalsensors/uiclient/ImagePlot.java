/*
	Copyright 2012 CERTH, http://www.certh.gr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.biomedicalsensors.uiclient;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePlot {
	int PAD = 40;

	public ImagePlot(String fpOutfile, double[] plotData, String dataLabel,
			String axisX, String axisY, int w, int h) {
		double[] normData;
		if (plotData.length > 1) {
			normData = normalize(plotData);
		} else {
			normData = plotData;
		}
		double scale = (double) (h - 2 * PAD) / getMax(normData);
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = (Graphics2D) image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setBackground(Color.LIGHT_GRAY);
		g2.clearRect(0, 0, w, h);
		// Draw ordinate.
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
		// Draw abcissa.
		Font font = g2.getFont();
		FontRenderContext frc = g2.getFontRenderContext();
		LineMetrics lm = font.getLineMetrics("0", frc);
		float sh = lm.getAscent() + lm.getDescent();

		if ((getMin(plotData) * getMax(plotData) <= 0) && (plotData.length > 1)) {
			if (getMin(plotData) < 0) {
				g2.draw(new Line2D.Double(PAD, h - PAD - scale
						* Math.abs(getMin(plotData)), w - PAD, h - PAD - scale
						* Math.abs(getMin(plotData))));
			} else {
				g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
			}

			// Draw labels.
			g2.setPaint(Color.WHITE);
			if (getMin(plotData) < 0) {
				g2.drawString(
						"0",
						(int) (PAD - font.getStringBounds("0", frc).getWidth()),
						(int) ((h - PAD - scale * Math.abs(getMin(plotData)) + (lm
								.getAscent() / 3))));
			} else {
				g2.drawString("0", (int) (PAD - font.getStringBounds("0", frc)
						.getWidth()), (int) ((h - PAD + (lm.getAscent() / 3))));
			}
		}
		g2.setPaint(Color.BLACK);

		// Ordinate label.
		String s = axisY;
		float sy = PAD + ((h - 2 * PAD) - s.length() * sh) / 2 + lm.getAscent();
		for (int i = 0; i < s.length(); i++) {
			String letter = String.valueOf(s.charAt(i));
			float sw = (float) font.getStringBounds(letter, frc).getWidth();
			float sx = (PAD - sw) / 3;
			g2.drawString(letter, sx, sy);
			sy += sh;
		}

		// Abcissa label.
		s = axisX;
		sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
		float sw = (float) font.getStringBounds(s, frc).getWidth();
		float sx = (w - sw) / 2;
		g2.drawString(s, sx, sy);

		// LEGEND
		s = dataLabel;
		Font font2 = new Font(font.SANS_SERIF, Font.BOLD, (int) (h / 20));

		g2.setFont(font2);
		FontMetrics fm = g2.getFontMetrics();

		sy = 5 + lm.getHeight();

		g2.setPaint(Color.BLACK);
		g2.drawString(s, w / 2 - (fm.stringWidth(s) / 2), sy);

		g2.setFont(font);

		// Draw lines.
		double xInc = (double) (w - 2 * PAD) / (plotData.length - 1);

		g2.setPaint(Color.green.darker());
		int j = 0;
		double x2 = 0, y2 = 0;
		if (plotData.length != 1) {
			for (int i = 0; i < normData.length - 1; i++) {
				j = i;
				double x1 = PAD + i * xInc;
				double y1 = h - PAD - scale * normData[i];
				x2 = PAD + (i + 1) * xInc;
				y2 = h - PAD - scale * normData[i + 1];
				g2.draw(new Line2D.Double(x1, y1, x2, y2));
				g2.setPaint(Color.DARK_GRAY);
				g2.drawString(String.valueOf(plotData[i]), (int) (x1), (int) y1);
				g2.setPaint(Color.green.darker());
			}

			g2.setPaint(Color.DARK_GRAY);
			g2.drawString(String.valueOf(plotData[j + 1]), (int) (x2), (int) y2);
			g2.setPaint(Color.green.darker());

		} else {
			g2.setPaint(Color.DARK_GRAY);
			g2.drawString(String.valueOf(plotData[0]), (int) (w / 2 + PAD),
					(int) (h - PAD - scale * plotData[0]));
			g2.setPaint(Color.green.darker());
		}
		// Mark data points.
		g2.setPaint(Color.red);
		for (int i = 0; i < normData.length; i++) {
			double x = PAD + i * xInc;
			if (Double.isNaN(x)) {
				x = w / 2 + PAD;
			}
			double y = h - PAD - scale * normData[i];
			g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
		}

		File f = new File(fpOutfile);
		try {
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double getMax(double[] data1) {
		double max = -Double.MAX_VALUE;
		for (int i = 0; i < data1.length; i++) {
			if (data1[i] > max)
				max = data1[i];
		}
		return max;
	}

	private double getMin(double[] data1) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < data1.length; i++) {
			if (data1[i] < min)
				min = data1[i];
		}
		return min;
	}

	private double[] normalize(double[] plotData) {
		double[] normData = new double[plotData.length];
		if (getMin(plotData) < 0) {
			for (int i = 0; i < plotData.length; i++)
				normData[i] = plotData[i] + Math.abs(getMin(plotData));
		} else {
			for (int i = 0; i < plotData.length; i++)
				normData[i] = plotData[i] - Math.abs(getMin(plotData));
		}
		return normData;
	}
}
