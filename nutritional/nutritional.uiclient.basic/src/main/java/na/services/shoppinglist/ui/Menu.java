package na.services.shoppinglist.ui;


import na.utils.lang.Messages;
import na.services.shoppinglist.ShoppingListSubServiceLauncher;
import na.utils.ButtonLab;
import na.widgets.button.AdaptiveButton;
import na.widgets.button.SecMenuButton;
import na.widgets.panel.SecNavigationBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class Menu extends SecNavigationBar {
	
	private List<SecMenuButton> buttons = new ArrayList<SecMenuButton>();
	protected ShoppingListSubServiceLauncher launcher;
	
	public Menu() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0};
		gridBagLayout.rowHeights = new int[]{5, 50, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			SecMenuButton button = new SecMenuButton();
			button.addActionListener(new ActionListener() {          
			    public void actionPerformed(ActionEvent e) {
			    	butGoingShopping(e);
			    }
			}); 
			ButtonLab.getInstance().addObject(button, ButtonLab.shopping_GoingShopping);
			button.setText(Messages.ShoppingList_GoingShopping);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.fill = GridBagConstraints.BOTH;
			gbc_1.insets = new Insets(0, 0, 5, 0);
			gbc_1.gridx = 0;
			gbc_1.gridy = 1;
			add(button, gbc_1);
			this.buttons.add(button);
			this.select(button);
		}
		{
			SecMenuButton button_1 = new SecMenuButton();
			button_1.addActionListener(new ActionListener() {          
			    public void actionPerformed(ActionEvent e) {
			    	butAddFoodItem(e);
			    }
			});
			button_1.setText(Messages.ShoppingList_AddFoodItem);
			ButtonLab.getInstance().addObject(button_1, ButtonLab.shopping_AddFood);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(button_1, gbc);
			this.buttons.add(button_1);
		}
	}

	private void butAddFoodItem(ActionEvent evt) {
		this.select((AdaptiveButton)evt.getSource());
		launcher.loadAddFoodItem();
	}

	private void butGoingShopping(ActionEvent evt) {
		this.select((AdaptiveButton)evt.getSource());
		launcher.loadGoingShoppingActionPanel();
//		launcher.subServiceFrame.showPDF(launcher.subServiceFrame.today, launcher.subServiceFrame.sizeSelection);
	}
	
	private void select(AdaptiveButton source) {
		Iterator<SecMenuButton> it = this.buttons.iterator();
		while (it.hasNext()) {
			AdaptiveButton but = it.next();
			but.setSelected(false);
		}
		source.setSelected(true);
	}

}
