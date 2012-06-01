package na.services.myNutritionalProfile.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import na.utils.NP;
import na.oasisUtils.profile.ProfileConnector;
import na.ws.UProperty;
import na.ws.UPropertyValues;
import na.services.myNutritionalProfile.business.Business;
import na.services.myNutritionalProfile.cache.TranslationCache;
import na.utils.Utils;
import na.utils.lang.Messages;
import na.widgets.list.AdaptiveList;
import na.widgets.panel.AdaptivePanel;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
//import javax.swing.JList;

@SuppressWarnings("serial")
public class MyProfilePanelRenewed extends AdaptivePanel {
	
	private DefaultListModel 	modelChronicDiseases = new DefaultListModel();
	private DefaultListModel 	modelTimes = new DefaultListModel();
	private DefaultListModel 	modelDiet = new DefaultListModel();
	private DefaultListModel 	modelLikes = new DefaultListModel();
	private DefaultListModel 	modelDislikes = new DefaultListModel();
	private DefaultListModel 	modelFavorites = new DefaultListModel();
	private DefaultListModel 	modelAllergies = new DefaultListModel();
	private DefaultListModel 	modelInto = new DefaultListModel();
	
	private Business business;
	private Log log = LogFactory.getLog(MyProfilePanelRenewed.class);
	private AdaptiveList listTimes;
	private AdaptiveList listDiets;
	private AdaptiveList listLikes;
	private AdaptiveList listFavorites;
	private AdaptiveList listChronicDiseases;
	private AdaptiveList listAllergies;
	private AdaptiveList listIntollerances;
	private AdaptiveList listDislikes;
	
	public MyProfilePanelRenewed() {
		Font f = this.getFont();
		f = f.deriveFont(f.getStyle() ^ Font.BOLD);
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 150, 5, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 10, 0, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 15, 400, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{175, 15, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0, 0, 0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(panel_1, gbc_1);
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					panel_2.setBorder(BorderFactory.createEmptyBorder());
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{0, 0};
					gridBagLayout_3.rowHeights = new int[]{0, 0};
					gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{1.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					panel_2.setBorder(new TitledBorder(null, Messages.Profile_Prefer_MealTimes, TitledBorder.LEADING, TitledBorder.TOP, f, null));
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(panel_2, gbc_2);
					{
						JScrollPane scrollPane = new JScrollPane();
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.BOTH;
						gbc_3.gridx = 0;
						gbc_3.gridy = 0;
						panel_2.add(scrollPane, gbc_3);
						{
							listTimes = new AdaptiveList();
							listTimes.setBorder(null);
							scrollPane.setViewportView(listTimes);
						}
					}
				}
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					panel_2.setBorder(BorderFactory.createEmptyBorder());
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{0, 0};
					gridBagLayout_3.rowHeights = new int[]{0, 0};
					gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{1.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					panel_2.setBorder(new TitledBorder(null, Messages.NutriProfile_MyDietTypeIs, TitledBorder.LEADING, TitledBorder.TOP, f, null));
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 1;
					panel_1.add(panel_2, gbc_2);
					{
						JScrollPane scrollPane = new JScrollPane();
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.BOTH;
						gbc_3.gridx = 0;
						gbc_3.gridy = 0;
						panel_2.add(scrollPane, gbc_3);
						{
							listDiets = new AdaptiveList();
							listDiets.setBorder(null);
							scrollPane.setViewportView(listDiets);
						}
					}
				}
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					panel_2.setBorder(BorderFactory.createEmptyBorder());
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{0, 0};
					gridBagLayout_3.rowHeights = new int[]{0, 0};
					gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{1.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					panel_2.setBorder(new TitledBorder(null, Messages.Profile_Prefer_ISpeciallyLike, TitledBorder.LEADING, TitledBorder.TOP, f, null));
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 5);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 2;
					panel_1.add(panel_2, gbc_2);
					{
						JScrollPane scrollPane = new JScrollPane();
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.BOTH;
						gbc_3.gridx = 0;
						gbc_3.gridy = 0;
						panel_2.add(scrollPane, gbc_3);
						{
							listLikes = new AdaptiveList();
							listLikes.setBorder(null);
							scrollPane.setViewportView(listLikes);
						}
					}
				}
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					panel_2.setBorder(BorderFactory.createEmptyBorder());
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{0, 0};
					gridBagLayout_3.rowHeights = new int[]{0, 0};
					gridBagLayout_3.columnWeights = new double[]{1.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{1.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					panel_2.setBorder(new TitledBorder(null, Messages.Profile_Prefer_MyFaoriteRecipesAre, TitledBorder.LEADING, TitledBorder.TOP, f, null));
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 3;
					panel_1.add(panel_2, gbc_2);
					{
						JScrollPane scrollPane = new JScrollPane();
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.BOTH;
						gbc_3.gridx = 0;
						gbc_3.gridy = 0;
						panel_2.add(scrollPane, gbc_3);
						{
							listFavorites = new AdaptiveList();
							listFavorites.setBorder(null);
							scrollPane.setViewportView(listFavorites);
						}
					}
				}
			}
			{
				JButton button = new JButton("");
				URL picURL = this.getClass().getResource("Portrait.png");
				ImageIcon j = new ImageIcon(picURL);
				button.setIcon(j);
				button.setSize(j.getIconWidth(), j.getIconHeight());
				
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.gridx = 2;
				gbc_1.gridy = 0;
				panel.add(button, gbc_1);
			}
		}
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0, 0, 0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 3;
			add(panel, gbc);
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				panel_1.setBorder(new TitledBorder(null, Messages.Profile_Prefer_Ihave, TitledBorder.LEADING, TitledBorder.TOP, f, null));
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(panel_1, gbc_1);
				{
					JScrollPane scrollPane = new JScrollPane();
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(scrollPane, gbc_2);
					{
						listChronicDiseases = new AdaptiveList();
						listChronicDiseases.setBorder(null);
						scrollPane.setViewportView(listChronicDiseases);
					}
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				panel_1.setBorder(new TitledBorder(null, Messages.Profile_Prefer_IamAllergicTo, TitledBorder.LEADING, TitledBorder.TOP, f, null));
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 1;
				gbc_1.gridy = 0;
				panel.add(panel_1, gbc_1);
				{
					JScrollPane scrollPane = new JScrollPane();
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(scrollPane, gbc_2);
					{
						listAllergies = new AdaptiveList();
						listAllergies.setBorder(null);
						scrollPane.setViewportView(listAllergies);
					}
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				panel_1.setBorder(new TitledBorder(null, Messages.Profile_Prefer_IamIntolerantTo, TitledBorder.LEADING, TitledBorder.TOP, f, null));
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 2;
				gbc_1.gridy = 0;
				panel.add(panel_1, gbc_1);
				{
					JScrollPane scrollPane = new JScrollPane();
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(scrollPane, gbc_2);
					{
						listIntollerances = new AdaptiveList();
						listIntollerances.setBorder(null);
						scrollPane.setViewportView(listIntollerances);
					}
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				panel_1.setBorder(new TitledBorder(null, Messages.Profile_Prefer_IDontLike, TitledBorder.LEADING, TitledBorder.TOP, f, null));
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 3;
				gbc_1.gridy = 0;
				panel.add(panel_1, gbc_1);
				{
					JScrollPane scrollPane = new JScrollPane();
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(scrollPane, gbc_2);
					{
						listDislikes = new AdaptiveList();
						listDislikes.setBorder(null);
						scrollPane.setViewportView(listDislikes);
					}
				}
			}
		}
	}
	
	public void getReady(SubServiceFrame service) {
		this.listTimes.setModel(this.modelTimes);
		this.listDiets.setModel(this.modelDiet);
		this.listLikes.setModel(this.modelLikes);
		this.listDislikes.setModel(this.modelDislikes);
		this.listAllergies.setModel(this.modelAllergies);
		this.listChronicDiseases.setModel(this.modelChronicDiseases);
		this.listFavorites.setModel(this.modelFavorites);
		this.listIntollerances.setModel(this.modelInto);
		
		// Meal times // breakfast
		try {
			String time = ProfileConnector.getInstance().getBreakfastTime();
			Utils.println("Time is: "+time);
			String value = Messages.Profile_Prefer_Breakfast +" "+ time;
			this.modelTimes.addElement(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Meal times // lunch
		try {
			String time = ProfileConnector.getInstance().getLunchTime();
			Utils.println("Time is: "+time);
			String value = Messages.Profile_Prefer_Lunch +" "+ time;
			this.modelTimes.addElement(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Meal times // dinner
		try {
			String time = ProfileConnector.getInstance().getDinnerTime();
			Utils.println("Time is: "+time);
			String value = Messages.Profile_Prefer_Dinner +" "+ time;
			this.modelTimes.addElement(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// diet types
		{
			String original = ProfileConnector.getInstance().getDietType();
			String value = ProfileConnector.getInstance().getDietType();
			this.modelDiet.addElement(value);
//			String[] values = {original};
//			if (values!=null && values.length>0) {
//				UProperty translatedDietType = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.Habits.DIET_TYPE, values);
//				if (translatedDietType.getValues()!=null && translatedDietType.getValues().length>0) {
//					for (UPropertyValues val : translatedDietType.getValues()) {
//						if (val!=null) {
//							try {
//								String value = Utils.Strings.capitalize(val.getValue());
//								this.modelDiet.addElement(value);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			}
		}
		
//		// likes
		{
		try {
			List<String> likes = ProfileConnector.getInstance().getPreferred_foods();
			if (likes!=null && likes.size()>0) {
				Iterator<String> it = likes.iterator();
				while (it.hasNext()) {
					String val = it.next();
					String[] intoValues = val.split("@");
					String value = Utils.Strings.capitalize(intoValues[2]);
					this.modelLikes.addElement(value);
				}
//				UProperty arraylikes = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.NutriPreferences.PREFERRRED_FOODS, likes.toArray(new String[likes.size()]));
//				if (arraylikes.getValues()!=null && arraylikes.getValues().length>0) {
//					for (UPropertyValues val : arraylikes.getValues()) {
//						if (val!=null) {
//								String[] intoValues = val.getValue().split("@");
//								String value = Utils.Strings.capitalize(intoValues[2]);
//								this.modelLikes.addElement(value);
//						}
//					}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
//		
		// disLikes
		{
		try {
			List<String> likes = ProfileConnector.getInstance().getFood_Dislikes();
			if (likes!=null && likes.size()>0) {
				Iterator<String> it = likes.iterator();
				while (it.hasNext()) {
					String val = it.next();
					String[] intoValues = val.split("@");
					String value = Utils.Strings.capitalize(intoValues[2]);
					this.modelDislikes.addElement(value);
				}
//				UProperty arraylikes = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.NutriPreferences.FOOD_DISLIKES, likes.toArray(new String[likes.size()]));
//				if (arraylikes.getValues()!=null && arraylikes.getValues().length>0) {
//					for (UPropertyValues val : arraylikes.getValues()) {
//						if (val!=null) {
//							Utils.println("Value is: "+val.getValue());
//								String[] intoValues = val.getValue().split("@");
//								String value = Utils.Strings.capitalize(intoValues[2]);
//								this.modelDislikes.addElement(value);
//						}
//					}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		// favorite recipes
		{
			try {
			List<String> likes = ProfileConnector.getInstance().getFavourite_Recipes();
			if (likes!=null && likes.size()>0) {
				Iterator<String> it = likes.iterator();
				while (it.hasNext()) {
					String val = it.next();
//					String[] intoValues = val.split("@");
					String value = Utils.Strings.capitalize(val);
					this.modelFavorites.addElement(value);
				}
//				String[] arraylikes = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.NutriPreferences.FAVOURITE_RECIPES, likes.toArray(new String[likes.size()]));
//				if (arraylikes!=null && arraylikes.length>0) {
//					for (String val : arraylikes) {
//						if (val!=null) {
//								// si se usa los datos del profile
//								String value = Utils.Strings.capitalize(val);
//								String[] intoValues = val.split("@");
//								if (intoValues!=null && intoValues.length>0) {
//									value = Utils.Strings.capitalize(intoValues[1]);
//								}
//								this.modelFavorites.addElement(value);
//						}
//					}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		// allergies
		{
			try {
			List<String> likes = ProfileConnector.getInstance().getFood_Allergies();
			if (likes!=null && likes.size()>0) {
				Iterator<String> it = likes.iterator();
				while (it.hasNext()) {
					String val = it.next();
					String[] intoValues = val.split("@");
					String value = Utils.Strings.capitalize(intoValues[2]);
					this.modelAllergies.addElement(value);
				}
//				String[] arraylikes = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.Health.FOOD_ALLERGIES, likes.toArray(new String[likes.size()]));
//				if (arraylikes!=null && arraylikes.length>0) {
//					for (String val : arraylikes) {
//						if (val!=null) {
//							try {
//								String[] intoValues = val.split("@");
//								String value = Utils.Strings.capitalize(intoValues[2]);
//								this.modelAllergies.addElement(value);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// intollerances
		{
			try {
			List<String> likes = ProfileConnector.getInstance().getFood_Intolerances();
			if (likes!=null && likes.size()>0) {
				Iterator<String> it = likes.iterator();
				while (it.hasNext()) {
					String val = it.next();
					String[] intoValues = val.split("@");
					String value = Utils.Strings.capitalize(intoValues[2]);
					this.modelInto.addElement(value);
				}
//				String[] arraylikes = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.Health.FOOD_INTOLERANCES, likes.toArray(new String[likes.size()]));
//				if (arraylikes!=null && arraylikes.length>0) {
//					for (String val : arraylikes) {
//						if (val!=null) {
//							try {
//								String[] intoValues = val.split("@");
//								String value = Utils.Strings.capitalize(intoValues[2]);
//								this.modelInto.addElement(value);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// chronic diseases, muestra los valores encontrados en el profile del Nutritonal. Los advises, en cambio, usan el perfil del Health ojo!
		{
			try {
			List<String> likes = ProfileConnector.getInstance().getLocalChronic_diseases();
			if (likes!=null && likes.size()>0) {
				Iterator<String> it = likes.iterator();
				while (it.hasNext()) {
					String val = it.next();
//					String[] intoValues = val.split("@");
//					String value = Utils.Strings.capitalize(intoValues[2]);
					String value = Utils.Strings.capitalize(val);
					this.modelChronicDiseases.addElement(value);
				}
//				UProperty arraylikes = TranslationCache.getInstance().getLocalisedValue(NP.Nutrition.Health.CHRONIC_DISEASES, likes.toArray(new String[likes.size()]));
//				if (arraylikes.getValues()!=null && arraylikes.getValues().length>0) {
//					for (UPropertyValues val : arraylikes.getValues()) {
//						if (val!=null) {
//							try {
//								String value = Utils.Strings.capitalize(val.getValue());
//								this.modelChronicDiseases.addElement(value);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
