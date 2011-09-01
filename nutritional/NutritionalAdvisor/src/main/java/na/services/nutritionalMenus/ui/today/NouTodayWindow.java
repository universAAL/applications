package na.services.nutritionalMenus.ui.today;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import na.miniDao.DayMenu;
import na.miniDao.Dish;
import na.miniDao.Meal;
import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.services.nutritionalMenus.ui.CustomFrame;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;

import javax.swing.JButton;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NouTodayWindow extends AdaptivePanel {

	private final int HEIGHT_CHANGE_MEAL = 31;
	private final int HEIGHT_BETWEEN_CHANGE_MEAL_SNACK = 31;
	private final int HEIGHT_SNACK_TITLE = 51;
	private final int HEIGHT_SNACKS = 51;
	private final int HEIGHT_GAP_INGREDIENTS = 7;
	private final int WIDTH_GAP_INGREDIENTS = 5;
	private final int GAP_BETWEEN_MEAL_COLUMNS = 15;
	
	private Log log = LogFactory.getLog(NouTodayWindow.class);
	private MenusSubServiceLauncher launcher;
	private DayMenu dayMenu;
	private AdaptiveButton cambiarDesayuno;
	
	private List<AdaptiveButton> listChangeMealButtons = new ArrayList<AdaptiveButton>();
	private List<AdaptiveButton> listVerRecetaButtons = new ArrayList<AdaptiveButton>();
	private List<AdaptiveTextBox> listIngredients = new ArrayList<AdaptiveTextBox>();
	private List<AdaptiveTextBox> listSnacks= new ArrayList<AdaptiveTextBox>();
	private List<JButton> listImagenes = new ArrayList<JButton>();
	private List<AdaptiveLabel> listSnackTitles = new ArrayList<AdaptiveLabel>();
	
	public NouTodayWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, this.GAP_BETWEEN_MEAL_COLUMNS, 0, this.GAP_BETWEEN_MEAL_COLUMNS, 0, 6, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 159, 0, this.HEIGHT_CHANGE_MEAL, this.HEIGHT_BETWEEN_CHANGE_MEAL_SNACK, this.HEIGHT_SNACK_TITLE, this.HEIGHT_SNACKS, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(panel, gbc);
			{
//				JPanel panel_1 = new JPanel();
				CustomFrame panelTitulo = new CustomFrame();
//				panelTitulo.setBorder(BorderFactory.createEmptyBorder());
				AdaptiveLabel label = new AdaptiveLabel();
				label.setFunction(ServiceInterface.Function_boldLabel);
				label.adapt();
				label.setText(Messages.Menus_Today_breakfast);
				panelTitulo.setLabel(label);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
//				panel.add(panel_1, gbc_1);
				panel.add(panelTitulo, gbc_1);
			}
			
			AdaptivePanel panelImagen = new AdaptivePanel();
			panelImagen.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_2 = new GridBagLayout();
			gridBagLayout_2.columnWidths = new int[]{0, 0};
			gridBagLayout_2.rowHeights = new int[]{0, 0};
			gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
			panelImagen.setLayout(gridBagLayout_2);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.insets = new Insets(0, 0, 5, 0);
			gbc_1.fill = GridBagConstraints.BOTH;
			gbc_1.gridx = 0;
			gbc_1.gridy = 1;
			panel.add(panelImagen, gbc_1);
			
			JButton imagenDesayuno= new JButton();
			this.listImagenes.add(imagenDesayuno);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.gridx = 0;
			gbc_2.gridy = 0;
			panelImagen.add(imagenDesayuno, gbc_2);
			
			AdaptivePanel panelIngredientes = new AdaptivePanel();
			GridBagLayout gridBagLayout_4 = new GridBagLayout();
			gridBagLayout_4.columnWidths = new int[]{this.WIDTH_GAP_INGREDIENTS, 0, this.WIDTH_GAP_INGREDIENTS, 0, 0};
			gridBagLayout_4.rowHeights = new int[]{this.HEIGHT_GAP_INGREDIENTS, 0, 0, 0, 0, 0};
			gridBagLayout_4.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panelIngredientes.setLayout(gridBagLayout_4);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.insets = new Insets(0, 0, 5, 0);
			gbc_3.fill = GridBagConstraints.BOTH;
			gbc_3.gridx = 0;
			gbc_3.gridy = 2;
			panel.add(panelIngredientes, gbc_3);
			
//			JLabel label = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text = new na.widgets.textbox.AdaptiveTextBox();
			String text = "empty";
			meal_text.setText(Utils.Strings.capitalize(text));
			meal_text.setLineWrap(true);
			meal_text.setWrapStyleWord(true);
			meal_text.setRows(2);
			meal_text.validate();
			meal_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text);
			
			GridBagConstraints gbc_6 = new GridBagConstraints();
			gbc_6.fill = GridBagConstraints.HORIZONTAL;
			gbc_6.insets = new Insets(0, 0, 5, 5);
			gbc_6.gridx = 1;
			gbc_6.gridy = 1;
			panelIngredientes.add(meal_text, gbc_6);
//			panelIngredientes.add(label, gbc_6);
			
			AdaptiveButton buttonSeeRecipe = new AdaptiveButton();
			buttonSeeRecipe.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe);
			GridBagConstraints gbc_7 = new GridBagConstraints();
			gbc_7.fill = GridBagConstraints.VERTICAL;
			gbc_7.insets = new Insets(0, 0, 5, 0);
			gbc_7.gridx = 3;
			gbc_7.gridy = 1;
			panelIngredientes.add(buttonSeeRecipe, gbc_7);
			
//			JLabel label_1 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_2 = new na.widgets.textbox.AdaptiveTextBox();
			String text_2 = "empty";
			meal_text_2.setText(Utils.Strings.capitalize(text_2));
			meal_text_2.setLineWrap(true);
			meal_text_2.setWrapStyleWord(true);
			meal_text_2.setRows(2);
			meal_text_2.validate();
			meal_text_2.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_2);
			
			GridBagConstraints gbc_8 = new GridBagConstraints();
			gbc_8.fill = GridBagConstraints.HORIZONTAL;
			gbc_8.insets = new Insets(0, 0, 5, 5);
			gbc_8.gridx = 1;
			gbc_8.gridy = 2;
//			panelIngredientes.add(label_1, gbc_8);
			panelIngredientes.add(meal_text_2, gbc_8);
			
			AdaptiveButton buttonSeeRecipe_2 = new AdaptiveButton();
			buttonSeeRecipe_2.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_2);
			GridBagConstraints gbc_10 = new GridBagConstraints();
			gbc_10.fill = GridBagConstraints.VERTICAL;
			gbc_10.insets = new Insets(0, 0, 5, 0);
			gbc_10.gridx = 3;
			gbc_10.gridy = 2;
			panelIngredientes.add(buttonSeeRecipe_2, gbc_10);
			
//			JLabel label_2 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_3 = new na.widgets.textbox.AdaptiveTextBox();
			String text_3 = "empty";
			meal_text_3.setText(Utils.Strings.capitalize(text_3));
			meal_text_3.setLineWrap(true);
			meal_text_3.setWrapStyleWord(true);
			meal_text_3.setRows(2);
			meal_text_3.validate();
			meal_text_3.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_3);
			
			GridBagConstraints gbc_9 = new GridBagConstraints();
			gbc_9.fill = GridBagConstraints.HORIZONTAL;
			gbc_9.insets = new Insets(0, 0, 5, 5);
			gbc_9.gridx = 1;
			gbc_9.gridy = 3;
//			panelIngredientes.add(label_2, gbc_9);
			panelIngredientes.add(meal_text_3, gbc_9);
			
			AdaptiveButton buttonSeeRecipe_3 = new AdaptiveButton();
			buttonSeeRecipe_3.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_3);
			GridBagConstraints gbc_11 = new GridBagConstraints();
			gbc_11.fill = GridBagConstraints.VERTICAL;
			gbc_11.insets = new Insets(0, 0, 5, 0);
			gbc_11.gridx = 3;
			gbc_11.gridy = 3;
			panelIngredientes.add(buttonSeeRecipe_3, gbc_11);
			
//			JLabel label_3 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_4 = new na.widgets.textbox.AdaptiveTextBox();
			String text_4 = "empty";
			meal_text_4.setText(Utils.Strings.capitalize(text_4));
			meal_text_4.setLineWrap(true);
			meal_text_4.setWrapStyleWord(true);
			meal_text_4.setRows(2);
			meal_text_4.validate();
			meal_text_4.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_4);
			
			panelIngredientes.add(buttonSeeRecipe_3, gbc_11);
			GridBagConstraints gbc_13 = new GridBagConstraints();
			gbc_13.fill = GridBagConstraints.HORIZONTAL;
			gbc_13.insets = new Insets(0, 0, 0, 5);
			gbc_13.gridx = 1;
			gbc_13.gridy = 4;
			panelIngredientes.add(meal_text_4, gbc_13);
			
			AdaptiveButton buttonSeeRecipe_4 = new AdaptiveButton();
			buttonSeeRecipe_4.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_4);
			GridBagConstraints gbc_12 = new GridBagConstraints();
			gbc_12.fill = GridBagConstraints.VERTICAL;
			gbc_12.gridx = 3;
			gbc_12.gridy = 4;
			panelIngredientes.add(buttonSeeRecipe_4, gbc_12);
			
			AdaptivePanel panelChangeMeal = new AdaptivePanel();
			panelChangeMeal.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_3 = new GridBagLayout();
			gridBagLayout_3.columnWidths = new int[]{0, 0};
			gridBagLayout_3.rowHeights = new int[]{40, 0};
			gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_3.rowWeights = new double[]{0.0, 1.0E-4};
			panelChangeMeal.setLayout(gridBagLayout_3);
			GridBagConstraints gbc_4 = new GridBagConstraints();
			gbc_4.insets = new Insets(0, 0, 5, 0);
			gbc_4.fill = GridBagConstraints.BOTH;
			gbc_4.gridx = 0;
			gbc_4.gridy = 4;
			panel.add(panelChangeMeal, gbc_4);
			
			cambiarDesayuno = new AdaptiveButton();
			cambiarDesayuno.setText(Messages.Menus_ChangeBreakfast);
			GridBagConstraints gbc_5 = new GridBagConstraints();
			gbc_5.fill = GridBagConstraints.VERTICAL;
			gbc_5.gridx = 0;
			gbc_5.gridy = 0;
			panelChangeMeal.add(cambiarDesayuno, gbc_5);
			this.listChangeMealButtons.add(cambiarDesayuno);
			
			CustomFrame panelTituloSnack = new CustomFrame();
			panelTituloSnack.setBorder(BorderFactory.createEmptyBorder());
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Menus_Today_midmorning_snack);
			this.listSnackTitles.add(label);
			panelTituloSnack.setLabel(label);
			GridBagConstraints gbc_15 = new GridBagConstraints();
			gbc_15.insets = new Insets(0, 0, 5, 0);
			gbc_15.fill = GridBagConstraints.BOTH;
			gbc_15.gridx = 0;
			gbc_15.gridy = 6;
			panel.add(panelTituloSnack, gbc_15);
			
			AdaptivePanel panelSnacks = new AdaptivePanel();
			panelSnacks.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_5 = new GridBagLayout();
			gridBagLayout_5.columnWidths = new int[]{this.WIDTH_GAP_INGREDIENTS, 0, this.WIDTH_GAP_INGREDIENTS, 0, 0};
			gridBagLayout_5.rowHeights = new int[]{0, 0};
			gridBagLayout_5.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_5.rowWeights = new double[]{0.0, 1.0E-4};
			panelSnacks.setLayout(gridBagLayout_5);
			GridBagConstraints gbc_14 = new GridBagConstraints();
			gbc_14.fill = GridBagConstraints.BOTH;
			gbc_14.gridx = 0;
			gbc_14.gridy = 7;
			panel.add(panelSnacks, gbc_14);
			
			na.widgets.textbox.AdaptiveTextBox snack_text = new na.widgets.textbox.AdaptiveTextBox();
			String textSnack = "empty";
			snack_text.setText(Utils.Strings.capitalize(textSnack));
			snack_text.setLineWrap(true);
			snack_text.setWrapStyleWord(true);
			snack_text.setRows(2);
			snack_text.validate();
			snack_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listSnacks.add(snack_text);
			GridBagConstraints gbc_16 = new GridBagConstraints();
			gbc_16.fill = GridBagConstraints.HORIZONTAL;
			gbc_16.insets = new Insets(0, 0, 0, 5);
			gbc_16.gridx = 1;
			gbc_16.gridy = 0;
			panelSnacks.add(snack_text, gbc_16);
			
			AdaptiveButton btnSeeRecipe = new AdaptiveButton();
			btnSeeRecipe.setText("Default see");
			GridBagConstraints gbc_17 = new GridBagConstraints();
			gbc_17.gridx = 3;
			gbc_17.gridy = 0;
			panelSnacks.add(btnSeeRecipe, gbc_17);
			this.listVerRecetaButtons.add(btnSeeRecipe);
		}
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 159, 0, this.HEIGHT_CHANGE_MEAL, this.HEIGHT_BETWEEN_CHANGE_MEAL_SNACK, this.HEIGHT_SNACK_TITLE, this.HEIGHT_SNACKS, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(panel, gbc);
			{
				CustomFrame panelTitulo = new CustomFrame();
				AdaptiveLabel label = new AdaptiveLabel();
				label.setFunction(ServiceInterface.Function_boldLabel);
				label.adapt();
				label.setText(Messages.Menus_Today_lunch);
				panelTitulo.setLabel(label);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
//				panel.add(panel_1, gbc_1);
				panel.add(panelTitulo, gbc_1);
			}
			
			AdaptivePanel panelImagen = new AdaptivePanel();
			panelImagen.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_2 = new GridBagLayout();
			gridBagLayout_2.columnWidths = new int[]{0, 0};
			gridBagLayout_2.rowHeights = new int[]{0, 0};
			gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
			panelImagen.setLayout(gridBagLayout_2);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.insets = new Insets(0, 0, 5, 0);
			gbc_1.fill = GridBagConstraints.BOTH;
			gbc_1.gridx = 0;
			gbc_1.gridy = 1;
			panel.add(panelImagen, gbc_1);
			
			JButton imagenDesayuno  = new JButton();
			this.listImagenes.add(imagenDesayuno);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.gridx = 0;
			gbc_2.gridy = 0;
			panelImagen.add(imagenDesayuno, gbc_2);
			
			AdaptivePanel panelIngredientes = new AdaptivePanel();
			GridBagLayout gridBagLayout_4 = new GridBagLayout();
			gridBagLayout_4.columnWidths = new int[]{this.WIDTH_GAP_INGREDIENTS, 0, this.WIDTH_GAP_INGREDIENTS, 0, 0};
			gridBagLayout_4.rowHeights = new int[]{this.HEIGHT_GAP_INGREDIENTS, 0, 0, 0, 0, 0};
			gridBagLayout_4.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panelIngredientes.setLayout(gridBagLayout_4);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.insets = new Insets(0, 0, 5, 0);
			gbc_3.fill = GridBagConstraints.BOTH;
			gbc_3.gridx = 0;
			gbc_3.gridy = 2;
			panel.add(panelIngredientes, gbc_3);
			
//			JLabel label = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text = new na.widgets.textbox.AdaptiveTextBox();
			String text = "empty";
			meal_text.setText(Utils.Strings.capitalize(text));
			meal_text.setLineWrap(true);
			meal_text.setWrapStyleWord(true);
			meal_text.setRows(2);
			meal_text.validate();
			meal_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text);
			
			GridBagConstraints gbc_6 = new GridBagConstraints();
			gbc_6.fill = GridBagConstraints.HORIZONTAL;
			gbc_6.insets = new Insets(0, 0, 5, 5);
			gbc_6.gridx = 1;
			gbc_6.gridy = 1;
			panelIngredientes.add(meal_text, gbc_6);
//			panelIngredientes.add(label, gbc_6);
			
			AdaptiveButton buttonSeeRecipe = new AdaptiveButton();
			buttonSeeRecipe.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe);
			GridBagConstraints gbc_7 = new GridBagConstraints();
			gbc_7.fill = GridBagConstraints.VERTICAL;
			gbc_7.insets = new Insets(0, 0, 5, 0);
			gbc_7.gridx = 3;
			gbc_7.gridy = 1;
			panelIngredientes.add(buttonSeeRecipe, gbc_7);
			
//			JLabel label_1 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_2 = new na.widgets.textbox.AdaptiveTextBox();
			String text_2 = "empty";
			meal_text_2.setText(Utils.Strings.capitalize(text_2));
			meal_text_2.setLineWrap(true);
			meal_text_2.setWrapStyleWord(true);
			meal_text_2.setRows(2);
			meal_text_2.validate();
			meal_text_2.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_2);
			
			GridBagConstraints gbc_8 = new GridBagConstraints();
			gbc_8.fill = GridBagConstraints.HORIZONTAL;
			gbc_8.insets = new Insets(0, 0, 5, 5);
			gbc_8.gridx = 1;
			gbc_8.gridy = 2;
//			panelIngredientes.add(label_1, gbc_8);
			panelIngredientes.add(meal_text_2, gbc_8);
			
			AdaptiveButton buttonSeeRecipe_2 = new AdaptiveButton();
			buttonSeeRecipe_2.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_2);
			GridBagConstraints gbc_10 = new GridBagConstraints();
			gbc_10.fill = GridBagConstraints.VERTICAL;
			gbc_10.insets = new Insets(0, 0, 5, 0);
			gbc_10.gridx = 3;
			gbc_10.gridy = 2;
			panelIngredientes.add(buttonSeeRecipe_2, gbc_10);
			
//			JLabel label_2 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_3 = new na.widgets.textbox.AdaptiveTextBox();
			String text_3 = "empty";
			meal_text_3.setText(Utils.Strings.capitalize(text_3));
			meal_text_3.setLineWrap(true);
			meal_text_3.setWrapStyleWord(true);
			meal_text_3.setRows(2);
			meal_text_3.validate();
			meal_text_3.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_3);
			
			GridBagConstraints gbc_9 = new GridBagConstraints();
			gbc_9.fill = GridBagConstraints.HORIZONTAL;
			gbc_9.insets = new Insets(0, 0, 5, 5);
			gbc_9.gridx = 1;
			gbc_9.gridy = 3;
//			panelIngredientes.add(label_2, gbc_9);
			panelIngredientes.add(meal_text_3, gbc_9);
			
			AdaptiveButton buttonSeeRecipe_3 = new AdaptiveButton();
			buttonSeeRecipe_3.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_3);
			GridBagConstraints gbc_11 = new GridBagConstraints();
			gbc_11.fill = GridBagConstraints.VERTICAL;
			gbc_11.insets = new Insets(0, 0, 5, 0);
			gbc_11.gridx = 3;
			gbc_11.gridy = 3;
			panelIngredientes.add(buttonSeeRecipe_3, gbc_11);
			
//			JLabel label_3 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_4 = new na.widgets.textbox.AdaptiveTextBox();
			String text_4 = "empty";
			meal_text_4.setText(Utils.Strings.capitalize(text_4));
			meal_text_4.setLineWrap(true);
			meal_text_4.setWrapStyleWord(true);
			meal_text_4.setRows(2);
			meal_text_4.validate();
			meal_text_4.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_4);
			
			panelIngredientes.add(buttonSeeRecipe_3, gbc_11);
			GridBagConstraints gbc_13 = new GridBagConstraints();
			gbc_13.fill = GridBagConstraints.HORIZONTAL;
			gbc_13.insets = new Insets(0, 0, 0, 5);
			gbc_13.gridx = 1;
			gbc_13.gridy = 4;
			panelIngredientes.add(meal_text_4, gbc_13);
			
			AdaptiveButton buttonSeeRecipe_4 = new AdaptiveButton();
			buttonSeeRecipe_4.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_4);
			GridBagConstraints gbc_12 = new GridBagConstraints();
			gbc_12.fill = GridBagConstraints.VERTICAL;
			gbc_12.gridx = 3;
			gbc_12.gridy = 4;
			panelIngredientes.add(buttonSeeRecipe_4, gbc_12);
			
			AdaptivePanel panelChangeMeal = new AdaptivePanel();
			panelChangeMeal.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_3 = new GridBagLayout();
			gridBagLayout_3.columnWidths = new int[]{0, 0};
			gridBagLayout_3.rowHeights = new int[]{40, 0};
			gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_3.rowWeights = new double[]{0.0, 1.0E-4};
			panelChangeMeal.setLayout(gridBagLayout_3);
			GridBagConstraints gbc_4 = new GridBagConstraints();
			gbc_4.insets = new Insets(0, 0, 5, 0);
			gbc_4.fill = GridBagConstraints.BOTH;
			gbc_4.gridx = 0;
			gbc_4.gridy = 4;
			panel.add(panelChangeMeal, gbc_4);
			
			cambiarDesayuno = new AdaptiveButton();
			cambiarDesayuno.setText(Messages.Menus_ChangeLunch);
			GridBagConstraints gbc_5 = new GridBagConstraints();
			gbc_5.fill = GridBagConstraints.VERTICAL;
			gbc_5.gridx = 0;
			gbc_5.gridy = 0;
			panelChangeMeal.add(cambiarDesayuno, gbc_5);
			this.listChangeMealButtons.add(cambiarDesayuno);
			
			CustomFrame panelTituloSnack = new CustomFrame();
			panelTituloSnack.setBorder(BorderFactory.createEmptyBorder());
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Menus_Today_afternoon_snack);
			this.listSnackTitles.add(label);
			panelTituloSnack.setLabel(label);
			GridBagConstraints gbc_15 = new GridBagConstraints();
			gbc_15.insets = new Insets(0, 0, 5, 0);
			gbc_15.fill = GridBagConstraints.BOTH;
			gbc_15.gridx = 0;
			gbc_15.gridy = 6;
			panel.add(panelTituloSnack, gbc_15);
			
			AdaptivePanel panelSnacks = new AdaptivePanel();
			panelSnacks.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_5 = new GridBagLayout();
			gridBagLayout_5.columnWidths = new int[]{this.WIDTH_GAP_INGREDIENTS, 0, this.WIDTH_GAP_INGREDIENTS, 0, 0};
			gridBagLayout_5.rowHeights = new int[]{0, 0};
			gridBagLayout_5.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_5.rowWeights = new double[]{0.0, 1.0E-4};
			panelSnacks.setLayout(gridBagLayout_5);
			GridBagConstraints gbc_14 = new GridBagConstraints();
			gbc_14.fill = GridBagConstraints.BOTH;
			gbc_14.gridx = 0;
			gbc_14.gridy = 7;
			panel.add(panelSnacks, gbc_14);
			
			na.widgets.textbox.AdaptiveTextBox snack_text = new na.widgets.textbox.AdaptiveTextBox();
			String textSnack = "empty";
			snack_text.setText(Utils.Strings.capitalize(textSnack));
			snack_text.setLineWrap(true);
			snack_text.setWrapStyleWord(true);
			snack_text.setRows(2);
			snack_text.validate();
			snack_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listSnacks.add(snack_text);
			GridBagConstraints gbc_16 = new GridBagConstraints();
			gbc_16.fill = GridBagConstraints.HORIZONTAL;
			gbc_16.insets = new Insets(0, 0, 0, 5);
			gbc_16.gridx = 1;
			gbc_16.gridy = 0;
			panelSnacks.add(snack_text, gbc_16);
			
			AdaptiveButton btnSeeRecipe = new AdaptiveButton();
			btnSeeRecipe.setText("Default see");
			GridBagConstraints gbc_17 = new GridBagConstraints();
			gbc_17.gridx = 3;
			gbc_17.gridy = 0;
			panelSnacks.add(btnSeeRecipe, gbc_17);
			this.listVerRecetaButtons.add(btnSeeRecipe);
		}
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 159, 0, this.HEIGHT_CHANGE_MEAL, this.HEIGHT_BETWEEN_CHANGE_MEAL_SNACK, this.HEIGHT_SNACK_TITLE, this.HEIGHT_SNACKS, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 4;
			gbc.gridy = 0;
			add(panel, gbc);
			{
//				JPanel panel_1 = new JPanel();
				CustomFrame panelTitulo = new CustomFrame();
//				panelTitulo.setBorder(BorderFactory.createEmptyBorder());
				AdaptiveLabel label = new AdaptiveLabel();
				label.setFunction(ServiceInterface.Function_boldLabel);
				label.adapt();
				label.setText(Messages.Menus_Today_dinner);
				panelTitulo.setLabel(label);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
//				panel.add(panel_1, gbc_1);
				panel.add(panelTitulo, gbc_1);
			}
			
			AdaptivePanel panelImagen = new AdaptivePanel();
			panelImagen.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_2 = new GridBagLayout();
			gridBagLayout_2.columnWidths = new int[]{0, 0};
			gridBagLayout_2.rowHeights = new int[]{0, 0};
			gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
			panelImagen.setLayout(gridBagLayout_2);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.insets = new Insets(0, 0, 5, 0);
			gbc_1.fill = GridBagConstraints.BOTH;
			gbc_1.gridx = 0;
			gbc_1.gridy = 1;
			panel.add(panelImagen, gbc_1);
			
			JButton imagenDesayuno = new JButton();
			this.listImagenes.add(imagenDesayuno);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.gridx = 0;
			gbc_2.gridy = 0;
			panelImagen.add(imagenDesayuno, gbc_2);
			
			AdaptivePanel panelIngredientes = new AdaptivePanel();
			GridBagLayout gridBagLayout_4 = new GridBagLayout();
			gridBagLayout_4.columnWidths = new int[]{this.WIDTH_GAP_INGREDIENTS, 0, this.WIDTH_GAP_INGREDIENTS, 0, 0};
			gridBagLayout_4.rowHeights = new int[]{this.HEIGHT_GAP_INGREDIENTS, 0, 0, 0, 0, 0};
			gridBagLayout_4.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panelIngredientes.setLayout(gridBagLayout_4);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.insets = new Insets(0, 0, 5, 0);
			gbc_3.fill = GridBagConstraints.BOTH;
			gbc_3.gridx = 0;
			gbc_3.gridy = 2;
			panel.add(panelIngredientes, gbc_3);
			
//			JLabel label = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text = new na.widgets.textbox.AdaptiveTextBox();
			String text = "empty";
			meal_text.setText(Utils.Strings.capitalize(text));
			meal_text.setLineWrap(true);
			meal_text.setWrapStyleWord(true);
			meal_text.setRows(2);
			meal_text.validate();
			meal_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text);
			
			GridBagConstraints gbc_6 = new GridBagConstraints();
			gbc_6.fill = GridBagConstraints.HORIZONTAL;
			gbc_6.insets = new Insets(0, 0, 5, 5);
			gbc_6.gridx = 1;
			gbc_6.gridy = 1;
			panelIngredientes.add(meal_text, gbc_6);
//			panelIngredientes.add(label, gbc_6);
			
			AdaptiveButton buttonSeeRecipe = new AdaptiveButton();
			buttonSeeRecipe.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe);
			GridBagConstraints gbc_7 = new GridBagConstraints();
			gbc_7.fill = GridBagConstraints.VERTICAL;
			gbc_7.insets = new Insets(0, 0, 5, 0);
			gbc_7.gridx = 3;
			gbc_7.gridy = 1;
			panelIngredientes.add(buttonSeeRecipe, gbc_7);
			
//			JLabel label_1 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_2 = new na.widgets.textbox.AdaptiveTextBox();
			String text_2 = "empty";
			meal_text_2.setText(Utils.Strings.capitalize(text_2));
			meal_text_2.setLineWrap(true);
			meal_text_2.setWrapStyleWord(true);
			meal_text_2.setRows(2);
			meal_text_2.validate();
			meal_text_2.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_2);
			
			GridBagConstraints gbc_8 = new GridBagConstraints();
			gbc_8.fill = GridBagConstraints.HORIZONTAL;
			gbc_8.insets = new Insets(0, 0, 5, 5);
			gbc_8.gridx = 1;
			gbc_8.gridy = 2;
//			panelIngredientes.add(label_1, gbc_8);
			panelIngredientes.add(meal_text_2, gbc_8);
			
			AdaptiveButton buttonSeeRecipe_2 = new AdaptiveButton();
			buttonSeeRecipe_2.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_2);
			GridBagConstraints gbc_10 = new GridBagConstraints();
			gbc_10.fill = GridBagConstraints.VERTICAL;
			gbc_10.insets = new Insets(0, 0, 5, 0);
			gbc_10.gridx = 3;
			gbc_10.gridy = 2;
			panelIngredientes.add(buttonSeeRecipe_2, gbc_10);
			
//			JLabel label_2 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_3 = new na.widgets.textbox.AdaptiveTextBox();
			String text_3 = "empty";
			meal_text_3.setText(Utils.Strings.capitalize(text_3));
			meal_text_3.setLineWrap(true);
			meal_text_3.setWrapStyleWord(true);
			meal_text_3.setRows(2);
			meal_text_3.validate();
			meal_text_3.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_3);
			
			GridBagConstraints gbc_9 = new GridBagConstraints();
			gbc_9.fill = GridBagConstraints.HORIZONTAL;
			gbc_9.insets = new Insets(0, 0, 5, 5);
			gbc_9.gridx = 1;
			gbc_9.gridy = 3;
//			panelIngredientes.add(label_2, gbc_9);
			panelIngredientes.add(meal_text_3, gbc_9);
			
			AdaptiveButton buttonSeeRecipe_3 = new AdaptiveButton();
			buttonSeeRecipe_3.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_3);
			GridBagConstraints gbc_11 = new GridBagConstraints();
			gbc_11.fill = GridBagConstraints.VERTICAL;
			gbc_11.insets = new Insets(0, 0, 5, 0);
			gbc_11.gridx = 3;
			gbc_11.gridy = 3;
			panelIngredientes.add(buttonSeeRecipe_3, gbc_11);
			
//			JLabel label_3 = new JLabel("New label");
			na.widgets.textbox.AdaptiveTextBox meal_text_4 = new na.widgets.textbox.AdaptiveTextBox();
			String text_4 = "empty";
			meal_text_4.setText(Utils.Strings.capitalize(text_4));
			meal_text_4.setLineWrap(true);
			meal_text_4.setWrapStyleWord(true);
			meal_text_4.setRows(2);
			meal_text_4.validate();
			meal_text_4.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listIngredients.add(meal_text_4);
			
			panelIngredientes.add(buttonSeeRecipe_3, gbc_11);
			GridBagConstraints gbc_13 = new GridBagConstraints();
			gbc_13.fill = GridBagConstraints.HORIZONTAL;
			gbc_13.insets = new Insets(0, 0, 0, 5);
			gbc_13.gridx = 1;
			gbc_13.gridy = 4;
			panelIngredientes.add(meal_text_4, gbc_13);
			
			AdaptiveButton buttonSeeRecipe_4 = new AdaptiveButton();
			buttonSeeRecipe_4.setText("Default see");
			this.listVerRecetaButtons.add(buttonSeeRecipe_4);
			GridBagConstraints gbc_12 = new GridBagConstraints();
			gbc_12.fill = GridBagConstraints.VERTICAL;
			gbc_12.gridx = 3;
			gbc_12.gridy = 4;
			panelIngredientes.add(buttonSeeRecipe_4, gbc_12);
			
			AdaptivePanel panelChangeMeal = new AdaptivePanel();
			panelChangeMeal.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_3 = new GridBagLayout();
			gridBagLayout_3.columnWidths = new int[]{0, 0};
			gridBagLayout_3.rowHeights = new int[]{40, 0};
			gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_3.rowWeights = new double[]{0.0, 1.0E-4};
			panelChangeMeal.setLayout(gridBagLayout_3);
			GridBagConstraints gbc_4 = new GridBagConstraints();
			gbc_4.insets = new Insets(0, 0, 5, 0);
			gbc_4.fill = GridBagConstraints.BOTH;
			gbc_4.gridx = 0;
			gbc_4.gridy = 4;
			panel.add(panelChangeMeal, gbc_4);
			
			cambiarDesayuno = new AdaptiveButton();
			cambiarDesayuno.setText(Messages.Menus_ChangeDinner);
			GridBagConstraints gbc_5 = new GridBagConstraints();
			gbc_5.fill = GridBagConstraints.VERTICAL;
			gbc_5.gridx = 0;
			gbc_5.gridy = 0;
			panelChangeMeal.add(cambiarDesayuno, gbc_5);
			this.listChangeMealButtons.add(cambiarDesayuno);
			
			CustomFrame panelTituloSnack = new CustomFrame();
			panelTituloSnack.setBorder(BorderFactory.createEmptyBorder());
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Menus_Today_afterdinner_snack);
			this.listSnackTitles.add(label);
			panelTituloSnack.setLabel(label);
			GridBagConstraints gbc_15 = new GridBagConstraints();
			gbc_15.insets = new Insets(0, 0, 5, 0);
			gbc_15.fill = GridBagConstraints.BOTH;
			gbc_15.gridx = 0;
			gbc_15.gridy = 6;
			panel.add(panelTituloSnack, gbc_15);
			
			AdaptivePanel panelSnacks = new AdaptivePanel();
			panelSnacks.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_5 = new GridBagLayout();
			gridBagLayout_5.columnWidths = new int[]{this.WIDTH_GAP_INGREDIENTS, 0, this.WIDTH_GAP_INGREDIENTS, 0, 0};
			gridBagLayout_5.rowHeights = new int[]{0, 0};
			gridBagLayout_5.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_5.rowWeights = new double[]{0.0, 1.0E-4};
			panelSnacks.setLayout(gridBagLayout_5);
			GridBagConstraints gbc_14 = new GridBagConstraints();
			gbc_14.fill = GridBagConstraints.BOTH;
			gbc_14.gridx = 0;
			gbc_14.gridy = 7;
			panel.add(panelSnacks, gbc_14);
			
			na.widgets.textbox.AdaptiveTextBox snack_text = new na.widgets.textbox.AdaptiveTextBox();
			String textSnack = "empty";
			snack_text.setText(Utils.Strings.capitalize(textSnack));
			snack_text.setLineWrap(true);
			snack_text.setWrapStyleWord(true);
			snack_text.setRows(2);
			snack_text.validate();
			snack_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			this.listSnacks.add(snack_text);
			GridBagConstraints gbc_16 = new GridBagConstraints();
			gbc_16.fill = GridBagConstraints.HORIZONTAL;
			gbc_16.insets = new Insets(0, 0, 0, 5);
			gbc_16.gridx = 1;
			gbc_16.gridy = 0;
			panelSnacks.add(snack_text, gbc_16);
			
			AdaptiveButton btnSeeRecipe = new AdaptiveButton();
			btnSeeRecipe.setText("Default see");
			GridBagConstraints gbc_17 = new GridBagConstraints();
			gbc_17.gridx = 3;
			gbc_17.gridy = 0;
			panelSnacks.add(btnSeeRecipe, gbc_17);
			this.listVerRecetaButtons.add(btnSeeRecipe);
		}
	}

	public void setLauncher(MenusSubServiceLauncher menusSubServiceLauncher) {
		this.launcher = menusSubServiceLauncher;		
	}

	public void setDayMenu(DayMenu todayMenuDescription) {
		this.dayMenu = todayMenuDescription;
	}

	public void drawContent(final boolean isToday) {
		if (this.dayMenu != null) {
			log.info("Drawing... content");
			na.miniDao.DayMenu menu = dayMenu;
			int[] meals_indexes = {menu.getBreakfastIndex(), menu.getLunchIndex(), menu.getDinnerIndex(), menu.getMidmorningSnackIndex(), menu.getAfternoonSnackIndex(), menu.getAfterdinnerSnackIndex()};
			// 3 iteraciones, 0=>breakfast; 1=>lunch; 2=>dinner
			for (int loop=0; loop<3; loop++) {
				try {
					// get current meal
					int meal_index = meals_indexes[loop];
					if (meal_index==-1)
						continue;
					na.miniDao.Meal meal = menu.getMeals()[meal_index];
					
					int pos = 0;
					
					// draw meal's dishes
					// Sort dishes
					Dish[] sortedDishes = meal.getDishes();
					Arrays.sort(sortedDishes, new DishesComparator());
					for (na.miniDao.Dish dish : sortedDishes) {
						na.widgets.textbox.AdaptiveTextBox meal_text = this.listIngredients.get((loop*4)+pos);
						String text = dish.getDescription();
						meal_text.setText(Utils.Strings.capitalize(text));
//						meal_text.setLineWrap(true);
//						meal_text.setWrapStyleWord(true);
//						meal_text.setRows(2);
//						meal_text.validate();
//						meal_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
//						
						// See recipe
						na.widgets.button.AdaptiveButton but = this.listVerRecetaButtons.get((loop*5)+pos);
						but.setText(Messages.Menus_SeeRecipe);
						final int meal_id = dish.getRecipeID();
						but.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								butShowRecipe(evt, meal_id);
							}
						});
						
						pos++;
					}
//					
					byte[] pictBytes = this.getMealPicture(meal);
					// Image
					if (pictBytes==null) {
						URL picbfURL = this.getClass().getResource(ServiceInterface.ImageNotAvailable);
						ImageIcon i = new ImageIcon(picbfURL);
						JButton b = this.listImagenes.get(loop);
						b.setIcon(i);
						b.setBounds(5, 5, 237, 159);
//						this.mainPanel.add(breakfast_img, ((GridBagConstraints)this.menuImages.get(loop)));
					} else {
						ImageIcon j = new ImageIcon(pictBytes);
						JButton breakfast_img = new JButton(j);
						JButton b = this.listImagenes.get(loop);
						b.setIcon(j);
						b.setBounds(5, 5, 237, 159);
					}
					

					// Change meal
					final int va = loop;
					if (Utils.Dates.getIsLastDayOfWeek()==true) {
						this.listChangeMealButtons.get(loop).setVisible(false);
					} else {
						this.listChangeMealButtons.get(loop).setVisible(true);
						this.listChangeMealButtons.get(loop).addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								final int result = Utils.showPopUp(Messages.Menus_ChangeMeal_PopUp_Title, Messages.Menus_ChangeMeal_PopUp_Message, JOptionPane.YES_NO_OPTION);
								if (result == 0) {
									if (va==0)
										butChangeTodayBreakfast(evt, isToday);
									else if (va==1)
										butChangeTodayLunch(evt, isToday);
									else
										butChangeTodayDinner(evt, isToday);
								}
							}
						});
					}
				} catch (Exception e) {
					log.error("there was an error drawing today's menu");
					e.printStackTrace();
				}
			}
			
			// S N A C K S
			{
				// draw components
				for (int loop=0; loop<3; loop++) {
					int meal_index = meals_indexes[loop+3];
					
					if (meal_index != -1) {
						na.miniDao.Meal meal = menu.getMeals()[meal_index];
						// Dishes
						int pos = 0;
						int seePos = 4;
						for (na.miniDao.Dish dish : meal.getDishes()) {
							int position = (loop*1)+pos;
							if (listSnacks.size()>position) {
								AdaptiveTextBox t = this.listSnacks.get(position);
								t.setText(Utils.Strings.capitalize(dish.getDescription()));
								
								// See recipe
								na.widgets.button.AdaptiveButton but = this.listVerRecetaButtons.get((loop*5)+seePos);
								but.setText(Messages.Menus_SeeRecipe);
								final int meal_id = dish.getRecipeID();
								but.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent evt) {
										butShowRecipe(evt, meal_id);
									}
								});
							}
							pos++;
							seePos++;
						}
					} else {
						log.info("Showing no snacks");
//						((SnacksPanel)this.snacks.get(loop)).title.setText(Utils.Strings.capitalize(Messages.Menus_NO_snack));
//						((SnacksPanel)this.snacks.get(loop)).setVisible(false);
					}
				} 
			}
		} else {
			log.error("Error fatal! There is no dayMenu content!!!");
		}
		
		for (AdaptiveTextBox element : this.listIngredients) {
			if (element.getText()!=null && element.getText().compareTo("Empty")==0)
				element.setVisible(false);
		}
		
		for (AdaptiveButton element : this.listVerRecetaButtons) {
			if (element.getText()!=null && element.getText().compareTo("Default see")==0)
				element.setVisible(false);
		}
		
		int index = 0;
		for (AdaptiveTextBox element : this.listSnacks) {
			if (element.getText()!=null && element.getText().compareTo("Empty")==0) {
				element.setVisible(false);
				this.listSnackTitles.get(index).setVisible(false);
			}
			index++;
		}
		
		
		this.repaintCanvas();
	}
	
	private void repaintCanvas() {
		this.validate();
		this.repaint();
	}
	
	private byte[] getMealPicture(Meal meal) {
		// buscar main course 	 = 4
		// buscar first course	 = 3
		// buscar starter course = 6
		// buscar side dish		 = 5
		// buscar breakfast		 = 7
		// mostrar no encontrado = 
		byte[] main = null;
		byte[] first = null;
		byte[] starter = null;
		byte[] side = null;
		byte[] breakfast = null;
		
		// tan pronto encuentro una imagen, termino
		for (Dish dish : meal.getDishes()) {
			if (dish.getDishCategory()==4 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					main = picBytes;
					return main;
				}
			} else if (dish.getDishCategory()==3 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					first = picBytes;
					return first;
				}
			} else if (dish.getDishCategory()==6 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					starter = picBytes;
					return starter;
				}
			} else if (dish.getDishCategory()==5 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					side = picBytes;
					return side;
				}
			} else if (dish.getDishCategory()==7 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					breakfast = picBytes;
					return breakfast;
				}
			} else {
				log.error("Wrong dishCategory: "+dish.getDishCategory() + " image: "+dish.getImage());
			}
		}
		
//		if (main!=null) {
//			return main;
//		} else if (first!=null) {
//			return first;
//		} else if (starter!=null) {
//			return starter;
//		} else if (side!=null) {
//			return side;
//		} else if (breakfast!=null) {
//			return breakfast;
//		}
		
		return null;
	}
	

	private void butChangeTodayBreakfast(java.awt.event.ActionEvent evt, boolean today) {
		log.info("Changing BREAKFAST");
		this.butChangeBreakfast(today);
		if (today) {
			launcher.showTodayMenus();
		} else {
			launcher.showTomorrowMenus();
		}
    }
	
	private void butChangeTodayLunch(java.awt.event.ActionEvent evt, boolean today) {
		log.info("Changing LUNCH");
		this.butChangeLunch(today);
		if (today)
			launcher.showTodayMenus();
		else
			launcher.showTomorrowMenus();
    }
	private void butChangeTodayDinner(java.awt.event.ActionEvent evt, boolean today) {
		log.info("Changing DINNER");
		this.butChangeDinner(today);
		if (today)
			launcher.showTodayMenus();
		else
			launcher.showTomorrowMenus();
    }
	

	private void butChangeBreakfast(boolean today) {
		if (today)
			launcher.changeMeal(ServiceInterface.today_int,ServiceInterface.MealCategory_BreakFast);
		else
			launcher.changeMeal(ServiceInterface.tomorrow_int,ServiceInterface.MealCategory_BreakFast);
    }
	
	private void butChangeLunch(boolean today) {
		if (today)
			launcher.changeMeal(ServiceInterface.today_int,ServiceInterface.MealCategory_Lunch);
		else
			launcher.changeMeal(ServiceInterface.tomorrow_int,ServiceInterface.MealCategory_Lunch);
    }
	
	private void butChangeDinner(boolean today) {
		if (today)
			launcher.changeMeal(ServiceInterface.today_int,ServiceInterface.MealCategory_Dinner);
		else
			launcher.changeMeal(ServiceInterface.tomorrow_int,ServiceInterface.MealCategory_Dinner);
    }

	private void butShowRecipe(java.awt.event.ActionEvent evt, int recipeID) {
		log.info("Showing recipe ID: "+recipeID);
		launcher.showRecipe(recipeID);
    }
}
