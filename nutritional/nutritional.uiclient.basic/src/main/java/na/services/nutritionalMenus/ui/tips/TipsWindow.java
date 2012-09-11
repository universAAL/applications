package na.services.nutritionalMenus.ui.tips;


import java.awt.GridBagLayout;

import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.utils.OASIS_ServiceUnavailable;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;

@SuppressWarnings("serial")
class TipsWindow extends AdaptivePanel {
	private Log log = LogFactory.getLog(TipsWindow.class);	
	private MenusSubServiceLauncher launcher;
	private na.miniDao.Tip[] tips;
	private AdaptiveTextBox text;
	
	public int getCurrent_tip() {
		return current_tip;
	}

	public void setCurrent_tip(int currentTip) {
		current_tip = currentTip;
	}

	private int current_tip = -1;
	private JButton imageBut;
	private TipsButtonsPanel buttonsPanel;
	
	public MenusSubServiceLauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(MenusSubServiceLauncher launcher) {
		this.launcher = launcher;
	}
	
	public TipsWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0};
		gridBagLayout.rowHeights = new int[]{5, 200, 50, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			JButton button = new JButton();
			button.setText("New button");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(button, gbc);
			this.imageBut = button;
		}
		{
			na.widgets.textbox.AdaptiveTextBox text_box = new na.widgets.textbox.AdaptiveTextBox();
			text_box.setColumns(30);
			text_box.setRows(4);
			text_box.setText("default text");
			text_box.setLineWrap(true);
			text_box.setWrapStyleWord(true);
			text_box.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(text_box, gbc);
			this.text = text_box;
		}
		{
			buttonsPanel = new TipsButtonsPanel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(buttonsPanel, gbc);
			buttonsPanel.settipsParent(this);
		}
	}

	public void setTips(na.miniDao.Tip[] tips) {
		this.tips = tips;
	}

	public na.miniDao.Tip[] getTips() {
		return tips;
	}
	
	protected void drawContent() {
		if (current_tip <= 0) {
			this.buttonsPanel.previousTipButton.setVisible(false);
		} else {
			this.buttonsPanel.previousTipButton.setVisible(true);
		}
		if (this.tips!=null && this.tips.length>0 && current_tip < (this.tips.length-1)) {
			this.buttonsPanel.nextTipButton.setVisible(true);
		} else {
			this.buttonsPanel.nextTipButton.setVisible(false);
		}
		
		if (current_tip == -1) {
			log.info("current_tip = -1 !!");
			return;
		}
		
		
		na.miniDao.Tip tip = this.tips[current_tip];
		
		
		
		log.info("Drawing... tip: "+tip.getID() + " "+tip.getDescription());
		
		
		// Description
		this.text.setText(tip.getDescription());
		
		// Image
		byte[] bytes = null;
		try {
			bytes = this.launcher.business.getTipPicture(tip.getID());
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			log.error("couldn't get picture, service unavailable");
		}
		if (bytes!= null) {
			ImageIcon j = new ImageIcon(bytes);
			this.imageBut.setText("");
			this.imageBut.setIcon(j);
		} else {
			log.info("Tip with no image");
		}
//		if (tip.getMultimedia()!=null) {
//			log.info("Question image url: "+tip.getMultimedia());
//			if (tip.getMultimediaBytes()==null)
//				log.info("NO BYTES!");
//			else {
//				ImageIcon j = new ImageIcon(tip.getMultimediaBytes());
//				this.imageBut.setText("");
//				this.imageBut.setIcon(j);
//			}
//		} else {
//			log.info("Tip with no image");
//		}
	}
	
	protected void butNextTipClicked(java.awt.event.ActionEvent evt) {
		log.info("next tip?");
		if (this.current_tip < (this.tips.length-1)) {
			// empty canvas
//			launcher.emptyTipsBox();
//			this.drawTipsCanvas();
			this.current_tip++;
			this.drawContent();
		} else {
			log.info("nothing to do!");
		}
    }

	protected void butPreviousTipClicked(java.awt.event.ActionEvent evt) {
		log.info("previous tip");
		if (this.current_tip > 0) {
			// empty canvas
//			launcher.emptyTipsBox();
//			this.drawTipsCanvas();
			this.current_tip--;
			this.drawContent();
		} else {
			log.info("nothing to do!");
		}

    }

}
