package na.services.shoppinglist.ui.actionsPanel;


import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import na.utils.lang.Messages;
import na.services.shoppinglist.ui.SubServiceFrame;
import na.utils.Utils;
import na.widgets.button.AdaptiveButton;
import na.widgets.checkbox.AdaptiveCheckBox;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.spinner2.AdaptiveSpinner2;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ActionsWindow extends AdaptivePanel {
	
	private Log log = LogFactory.getLog(ActionsWindow.class);
	private AdaptiveSpinner2 spinner;
	private AdaptiveLabel lblObtainShoppingList;
	
	private List<DayPanel> listDayPanel = new ArrayList<DayPanel>();
	
	//buttons
	private AdaptiveButton digitalButton;
	private AdaptiveButton sendToMobileButton;
	private AdaptiveButton sendToCarerButton;
	
	private AdaptiveLabel daysLabel;
	private AdaptiveButton printingButton;
	public AdaptiveCheckBox chckbxUseSocialCommunitys;
	
	public ActionsWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{150, 50, 5, 30, 40, 0, 10, 0, 25, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				AdaptivePanel actionsPanel = new AdaptivePanel();
				actionsPanel.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{20, 0, 200, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
				actionsPanel.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.VERTICAL;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(actionsPanel, gbc_1);
				{
					JButton button = new JButton();
					URL picURL = this.getClass().getResource("IconDisplay.png");
					ImageIcon image = new ImageIcon(picURL);
					button.setSize(image.getIconWidth(), image.getIconHeight());
					button.setIcon(image);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 1;
					actionsPanel.add(button, gbc_2);
				}
				{
					digitalButton = new AdaptiveButton();
					digitalButton.setText(Messages.ShoppingList_Digital);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 2;
					gbc_2.gridy = 1;
					actionsPanel.add(digitalButton, gbc_2);
				}
				{
					JButton button = new JButton();
					URL picURL = this.getClass().getResource("IconMobile.png");
					ImageIcon image = new ImageIcon(picURL);
					{
						JButton button_1 = new JButton();
						URL picURL2 = this.getClass().getResource("IconPrint.png");
						ImageIcon image2 = new ImageIcon(picURL2);
						button_1.setIcon(image2);
						GridBagConstraints gbc_2 = new GridBagConstraints();
						gbc_2.insets = new Insets(0, 0, 5, 5);
						gbc_2.gridx = 1;
						gbc_2.gridy = 2;
						actionsPanel.add(button_1, gbc_2);
						button_1.setVisible(false); //TODO no printing at pilot sites
						log.info("Print button disabled at pilot site");
					}
					{
						printingButton = new AdaptiveButton();
						printingButton.setText(Messages.ShoppingList_Print);
						GridBagConstraints gbc_2 = new GridBagConstraints();
						gbc_2.fill = GridBagConstraints.HORIZONTAL;
						gbc_2.insets = new Insets(0, 0, 5, 5);
						gbc_2.gridx = 2;
						gbc_2.gridy = 2;
						actionsPanel.add(printingButton, gbc_2);
						printingButton.setVisible(false); //TODO no printing at pilot sites
					}
					button.setSize(image.getIconWidth(), image.getIconHeight());
					button.setIcon(image);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 3;
					actionsPanel.add(button, gbc_2);
				}
				{
					sendToMobileButton = new AdaptiveButton();
					sendToMobileButton.setText(Messages.ShoppingList_SendToEmail);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 2;
					gbc_2.gridy = 3;
					actionsPanel.add(sendToMobileButton, gbc_2);
				}
				{
					JButton button = new JButton();
					URL picURL = this.getClass().getResource("IconCarer.png");
					ImageIcon image = new ImageIcon(picURL);
					button.setSize(image.getIconWidth(), image.getIconHeight());
					button.setIcon(image);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 4;
					actionsPanel.add(button, gbc_2);
				}
				{
					sendToCarerButton = new AdaptiveButton();
					sendToCarerButton.setText(Messages.ShoppingList_SendCarer);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 2;
					gbc_2.gridy = 4;
					actionsPanel.add(sendToCarerButton, gbc_2);
				}
				{
					JButton button = new JButton();
					URL picURL = this.getClass().getResource("IconCommerce.png");
					ImageIcon image = new ImageIcon(picURL);
					button.setSize(image.getIconWidth(), image.getIconHeight());
					button.setIcon(image);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 5;
					actionsPanel.add(button, gbc_2);
				}
				{
					AdaptiveButton button = new AdaptiveButton();
					button.setText(Messages.ShoppingList_SendECommerce);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.gridx = 2;
					gbc_2.gridy = 5;
					actionsPanel.add(button, gbc_2);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 1;
				panel.add(panel_1, gbc_1);
				{
					chckbxUseSocialCommunitys = new AdaptiveCheckBox();
					chckbxUseSocialCommunitys.setText(Messages.Shopping_UseSocialCommunitiesAgenda);
//					Setup.
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(chckbxUseSocialCommunitys, gbc_2);
				}
			}
			{
				AdaptivePanel selectionPanel = new AdaptivePanel();
				selectionPanel.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 10, 0, 0, 0, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
				selectionPanel.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 3;
				panel.add(selectionPanel, gbc_1);
				{
					lblObtainShoppingList = new AdaptiveLabel();
					lblObtainShoppingList.setText(Messages.ShoppingList_ObtainShoppingListFor);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.anchor = GridBagConstraints.WEST;
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					selectionPanel.add(lblObtainShoppingList, gbc_2);
				}
				{
					daysLabel = new AdaptiveLabel();
					daysLabel.setText("days");
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 3;
					gbc_2.gridy = 0;
					selectionPanel.add(daysLabel, gbc_2);
				}
				{
					spinner = new AdaptiveSpinner2();
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 4;
					gbc_2.gridy = 0;
					selectionPanel.add(this.spinner, gbc_2);
					spinner.setMin(1);
					spinner.setMax(7);
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 5;
				panel.add(panel_1, gbc_1);
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.anchor = GridBagConstraints.WEST;
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 2;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 3;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 4;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 5;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					DayPanel d = new DayPanel();
					this.listDayPanel.add(d);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 6;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.WEST;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.VERTICAL;
				gbc_1.gridx = 0;
				gbc_1.gridy = 7;
				panel.add(panel_1, gbc_1);
				{
					DayPanel d = new DayPanel();
					d.setType(DayPanel.GREEN);
					d.setDay("");
					d.setNumber("");
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.anchor = GridBagConstraints.WEST;
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					JLabel lblNotPrintedsendedShopping = new JLabel(Messages.ShoppingList_NotPrinted);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 1;
					gbc_2.gridy = 0;
					panel_1.add(lblNotPrintedsendedShopping, gbc_2);
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.WEST;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.VERTICAL;
				gbc_1.gridx = 0;
				gbc_1.gridy = 8;
				panel.add(panel_1, gbc_1);
				{
					DayPanel d = new DayPanel();
					d.setType(DayPanel.RED);
					d.setDay("");
					d.setNumber("");
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.anchor = GridBagConstraints.WEST;
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(d, gbc_2);
				}
				{
					JLabel lblAlreadyPrintedsended = new JLabel(Messages.ShoppingList_AlreadyPrinted);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 1;
					gbc_2.gridy = 0;
					panel_1.add(lblAlreadyPrintedsended, gbc_2);
				}
			}
		}
	}
	
	public void getReady(final SubServiceFrame service) {
		service.listDayPanel = this.listDayPanel;
		service.daysLabel = this.daysLabel;
		String[] dayNames = Utils.Dates.getDaysOfWeekFromNow();
		for (int i=0; i<7; i++) {
			this.listDayPanel.get(i).setDay(dayNames[i].substring(0, 3));
		}
		
		int[] dayumbers = Utils.Dates.getNextIntSevenDays();
		for (int i=0; i<7; i++) {
			this.listDayPanel.get(i).setNumber(""+dayumbers[i]);
		}
		
		service.spinner = this.spinner;
		service.spinner.setVisible(true);
		if (service.sizeSelection == 1) {
			this.daysLabel.setText(""+service.sizeSelection +" "+ Messages.ShoppingList_GoingShopping_ShoppingList_DAY);
		} else {
			this.daysLabel.setText(""+service.sizeSelection +" "+ Messages.ShoppingList_DAYS);
		}
		this.spinner.setValue(service.sizeSelection);
		service.spinner.addChangeListener(service);
		
		service.applyShowRules(service.sizeAlreadyGenerated, service.sizeSelection);
		
		//buttons
		this.digitalButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				service.print_method = SubServiceFrame.PDF;
				service.butPrintShoppingList(evt, chckbxUseSocialCommunitys.isSelected());
			}
		});
		
		this.printingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				service.print_method = SubServiceFrame.PRINTER;
				service.butPrintShoppingList(evt, chckbxUseSocialCommunitys.isSelected());
			}
		});
		
		this.sendToMobileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				service.butSendToMobile(evt);
			}
		});
		
		this.sendToCarerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				service.butSendToCarer(evt);
			}
		});
//		//temporary button
//		service.moreBut = this.moreBut;
//		this.moreBut.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				service.butIncreaseDays(evt);
//			}
//		});
//		service.lessBut = this.lessBut;
//		this.lessBut.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				service.butDecreaseDays(evt);
//			}
//		});

		
	}

}
