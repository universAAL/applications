package na.services;


import java.awt.GridBagLayout;

import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.profile.ProfileConnector;
import na.oasisUtils.trustedSecurityNetwork.Login;
import na.services.help.HelpSubServiceLauncher;
import na.services.myNutritionalProfile.NutriProfileSubServiceLauncher;
import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.services.recipes.RecipesSubServiceLauncher;
import na.services.scheduler.EventScheduler;
import na.services.shoppinglist.ShoppingListSubServiceLauncher;
import na.utils.ButtonLab;
import na.utils.InitialSetup;
import na.utils.LoginWindow;
import na.utils.Setup;
import na.utils.lang.Messages;
import na.widgets.button.MainMenuButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.panel.MainNavigationBar;
import na.widgets.panel.StatusBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ServiceFrame extends AdaptivePanel {
	private static final int DEFAULT_AGE = 50;
	
	private int BUTTON_WIDHT = 150;
	
	private Log log = LogFactory.getLog(ServiceFrame.class);
	private static final String STATUS_BAR_NORMAL = "normal";
	private static final String STATUS_BAR_MULTITEL = "mutitel";
	private na.widgets.panel.AdaptivePanel canvas;
    private na.widgets.panel.MainNavigationBar navigationBar;
    private na.widgets.panel.StatusBar statusBar;

    private na.widgets.button.MainMenuButton 	nutritionalMenus_button;
    private na.widgets.button.MainMenuButton 	recipes_button;
    private na.widgets.button.MainMenuButton 	shoppingList_button;
    private na.widgets.button.MainMenuButton 	myNutriProfile_button;
    private na.widgets.button.MainMenuButton 	helpButton;
    private na.widgets.button.MainMenuButton 	exitButton;
    
    private na.widgets.button.MainMenuButton[] mainButtons = new MainMenuButton[6];
    
    //multitel data
    private StatusBarPanel standardStatusBarpanel;
    private JLabel label_1;
//    private AdaptiveLabel label_1;
//    private widgets.progressbar.AdaptiveProgressBar progressBar;

	private JFrame frame;

	/**
	 * Create the panel.
	 * @param multitelService 
	 */
	protected ServiceFrame(BundleContext context, JFrame frame) {
		{
			/*
			 * 1. Comprobar usuario uAAL
			 * 2. Login to NutriWeb-Server
			 * 3. Si login ok
			 * 		comparar profile local con remoto
			 * 		si no hay profile, es null o X, descargar profile remoto.
			 */
			this.frame = frame;
			initBasicUIComponents();
			boolean isAuthenticated = true;
			
			log.info("Authenticated: "+isAuthenticated);
			// 1. uAAL authenticated?
			if (isAuthenticated==true) {
				String user =  InitialSetup.AMI_USERNAME_VALUE;; // get user from uAAL!
				if (user!=null) {
					log.info("User logged in: "+user);
					Setup.AMI_UserName = user;
					// 2. Setup NA DIR
					InitialSetup.initNutriAdvisorFolder();

					Login login = new Login();
					boolean login_succesful = login.logMeIn();
					// 3. nutritional login
					if (login_succesful == true) {
						// 4. Start profile API and download profile
//						if (Setup.download_profile_on_start()) {
//							log.info("Profile: Download profile from server enabled");
							ProfileConnector.getInstance().downloadProfileFromServer();
//						} else {
//							log.info("Profile: Download profile from server disabled");
//						}
						
						// 5. Last: show application
						startApp(context);
					} else {
						log.fatal("Couldn't login :(");
					}
				} else {
					// show message
					log.warn("User not logged in, user is null");
				}
			} else {
				// show message
				log.warn("User nooot logged in");
				this.loadLoginWindow();
			}
			
		}
	}

	private void startApp(BundleContext context) {
		// 4. Scheduler
		if (Setup.use_Scheduler()) {
			log.trace("launching event scheduler");
			EventScheduler events = new EventScheduler();
			events.start();
			log.info("Event scheduler launched");
			log.info(" STARTED");	
		} else {
			log.trace("Scheduler disabled");
		}
		
        this.extraChanges();
       	this.loadDefaultService();
	}

	

//	private void initMultiTel(IVoiceRecognitionService multitelService2) {
//		this.multitel = new MultitelHandler();
//		this.multitel.setService(multitelService2);
//		this.multitel.createPanel();
//		this.multitel.setServiceFrame(this);
//		this.statusBar.add(this.multitel.getPanel(), STATUS_BAR_MULTITEL);
//	}
	
	
	
	private void initBasicUIComponents() {
		this.setBorder(BorderFactory.createEmptyBorder());
		{
			navigationBar = new na.widgets.panel.MainNavigationBar();
		}
		{
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{347, 0};
			gridBagLayout.rowHeights = new int[]{67, 200, 42, 0};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
			setLayout(gridBagLayout);
		}
		{
			navigationBar = new MainNavigationBar();
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{64, BUTTON_WIDHT, BUTTON_WIDHT, BUTTON_WIDHT, BUTTON_WIDHT, BUTTON_WIDHT, 64, 0};
			gridBagLayout.rowHeights = new int[]{62, 0};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
			gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
			navigationBar.setLayout(gridBagLayout);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(navigationBar, gbc);
		}
		{
			canvas = new AdaptivePanel();
			canvas.setLayout(new GridLayout(1, 0, 0, 0));
			canvas.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(canvas, gbc);
		}
		{
			statusBar = new StatusBar();
			statusBar.setLayout(new CardLayout(0, 0));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(statusBar, gbc);
			
			standardStatusBarpanel = new StatusBarPanel();
//			standardStatusBarpanel.setLayout(new GridLayout(1, 0, 0, 0));
			standardStatusBarpanel.setBorder(BorderFactory.createEmptyBorder());
			statusBar.add(standardStatusBarpanel, STATUS_BAR_NORMAL);
			
//			progressBar = new AdaptiveProgressBar();
//			progressBar.setIndeterminate(false);
			
			AdaptiveLabel label = new AdaptiveLabel();
			label.setText("");
			standardStatusBarpanel.setContent(label);
//			standardStatusBarpanel.add(progressBar);
		}
		
				        
//		label_1 = new AdaptiveLabel();
//		label_1.setText(" [Nutritional Advisor]");
//		label_1.setFunction(ServiceInterface.Function_boldLabel);
//		label_1.adapt();
				        label_1 = new JLabel();
				        label_1.setIcon(new ImageIcon(ServiceFrame.class.getResource("/na/utils/logo_nutritional_3.png")));
				        //		label_1.setIcon(new ImageIcon(ServiceFrame.class.getResource("/na/utils/NATT_Logo.png")));
				        //		label_1.setIcon(new ImageIcon(ServiceFrame.class.getResource("/na/utils/nutritional_logo.png")));
				        //		label_1.setIcon(new ImageIcon(ServiceFrame.class.getResource("/na/utils/nutritional_logo_naranja.png")));
				        		GridBagConstraints gbc_1 = new GridBagConstraints();
				        		gbc_1.anchor = GridBagConstraints.WEST;
				        		gbc_1.fill = GridBagConstraints.VERTICAL;
				        		gbc_1.insets = new Insets(0, 0, 0, 5);
				        		gbc_1.gridx = 0;
				        		gbc_1.gridy = 0;
				        		navigationBar.add(label_1, gbc_1);
				        nutritionalMenus_button = new na.widgets.button.MainMenuButton();
				        GridBagConstraints gbc_1_2 = new GridBagConstraints();
				        gbc_1_2.fill = GridBagConstraints.BOTH;
				        gbc_1_2.insets = new Insets(0, 0, 0, 5);
				        gbc_1_2.gridx = 1;
				        gbc_1_2.gridy = 0;
				        this.navigationBar.add(nutritionalMenus_button, gbc_1_2);
				        
				        mainButtons[0] = nutritionalMenus_button;
				                ButtonLab.getInstance().addObject(nutritionalMenus_button, ButtonLab.main_NutritionalMenus);
				                recipes_button = new na.widgets.button.MainMenuButton();
				                GridBagConstraints gbc_2_1 = new GridBagConstraints();
				                gbc_2_1.fill = GridBagConstraints.BOTH;
				                gbc_2_1.insets = new Insets(0, 0, 0, 5);
				                gbc_2_1.gridx = 2;
				                gbc_2_1.gridy = 0;
				                this.navigationBar.add(recipes_button, gbc_2_1);
				                        mainButtons[1] = recipes_button;
				                                
				                                ButtonLab.getInstance().addObject(recipes_button, ButtonLab.main_Recipes);
				        shoppingList_button = new na.widgets.button.MainMenuButton();
				        GridBagConstraints gbc_3_1 = new GridBagConstraints();
				        gbc_3_1.fill = GridBagConstraints.BOTH;
				        gbc_3_1.insets = new Insets(0, 0, 0, 5);
				        gbc_3_1.gridx = 3;
				        gbc_3_1.gridy = 0;
				        this.navigationBar.add(shoppingList_button, gbc_3_1);
				                mainButtons[2] = shoppingList_button;
				                        ButtonLab.getInstance().addObject(shoppingList_button, ButtonLab.main_ShoppingList);
				                myNutriProfile_button = new na.widgets.button.MainMenuButton();
				                {
				                	GridBagConstraints gbc_4 = new GridBagConstraints();
				                	gbc_4.fill = GridBagConstraints.BOTH;
				                	gbc_4.insets = new Insets(0, 0, 0, 5);
				                	gbc_4.gridx = 4;
				                	gbc_4.gridy = 0;
				                	this.navigationBar.add(myNutriProfile_button, gbc_4);
	        mainButtons[3] = myNutriProfile_button;
	        ButtonLab.getInstance().addObject(myNutriProfile_button, ButtonLab.main_MyNutritionalProfile);
				                }
				        helpButton = new na.widgets.button.MainMenuButton();
				        GridBagConstraints gbc_5_1 = new GridBagConstraints();
				        gbc_5_1.fill = GridBagConstraints.BOTH;
				        gbc_5_1.insets = new Insets(0, 0, 0, 5);
				        gbc_5_1.gridx = 5;
				        gbc_5_1.gridy = 0;
				        this.navigationBar.add(helpButton, gbc_5_1);
				                mainButtons[4] = helpButton;
				                        ButtonLab.getInstance().addObject(helpButton, ButtonLab.main_Help);
				                        
                        exitButton = new na.widgets.button.MainMenuButton();
//                        URL picURL = this.getClass().getResource("exit.jpg");
//        				ImageIcon j = new ImageIcon(picURL);
//        				exitButton.setIcon(j);
				        GridBagConstraints gbc_6_1 = new GridBagConstraints();
				        gbc_5_1.fill = GridBagConstraints.BOTH;
				        gbc_5_1.insets = new Insets(0, 0, 0, 5);
				        gbc_5_1.gridx = 5;
				        gbc_5_1.gridy = 0;
				        this.navigationBar.add(exitButton, gbc_6_1);
				                mainButtons[5] = exitButton;
				                        ButtonLab.getInstance().addObject(exitButton, ButtonLab.main_Exit);
		
	}
	
	
	/**
     * Reset Text Messages
     */
    private void extraChanges() {
        nutritionalMenus_button.setText(Messages.MainMenu_But_NutritionalMenus);
        recipes_button.setText(Messages.MainMenu_But_Recipes);
        shoppingList_button.setText(Messages.MainMenu_But_ShoppingList);
        myNutriProfile_button.setText(Messages.MainMenu_But_MyNutritionalProfile);
        helpButton.setText(Messages.MainMenu_But_Help);
        exitButton.setText("Exit");
        
        nutritionalMenus_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nutritionalMenus_clicked(evt);
            }
        });
        
        recipes_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recipes_clicked(evt);
            }
        });

        myNutriProfile_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myNutritionalProfile_clicked(evt);
            }
        });
        
        shoppingList_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shoppingList_clicked(evt);
            }
        });
        
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                help_clicked(evt);
//            	System.out.println("Help... Action!");
            }
        });
        
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	System.out.println("Exiting nutritional advisor");
        		log.info("EXIT nutritional advisor");
        		ProfileConnector.closeProfile();
        		AmiConnector.closeFrontend();
//        		this.content = null;
//        		this.canvas = null;
        		EventScheduler.stop();
        		log.info("Nutritional advisor exited");
            	frame.dispose();
            }
        });
    }
    
    private void nutritionalMenus_clicked(java.awt.event.ActionEvent evt) {                                                
    	log.info("Nutritional Menus Clicked");
    	this.deselectAll();
    	// select actual
    	nutritionalMenus_button.setSelected(true);
    	this.emptyBox();
   		this.loadNutritionalMenus();
   		this.redraw();
    } 
    
    private void recipes_clicked(java.awt.event.ActionEvent evt) {                                                
    	log.info("Recipes Clicked");
    	this.deselectAll();
    	// select actual
    	recipes_button.setSelected(true);
    	this.emptyBox();
    	this.loadRecipes();
    	this.redraw();
    }
    
    private void myNutritionalProfile_clicked(java.awt.event.ActionEvent evt) {                                                
    	log.info("MyNutritionalProfile Clicked");
    	this.deselectAll();
    	// select actual
    	myNutriProfile_button.setSelected(true);
    	this.emptyBox();
    	this.loadMyNutritionalProfile();
//    	this.redraw();
    }
    
    private void shoppingList_clicked(java.awt.event.ActionEvent evt) {                                                
    	log.info("ShoppingList Clicked");
    	this.deselectAll();
    	// select actual
    	shoppingList_button.setSelected(true);
    	this.emptyBox();
        this.loadShoppingList();
//    	this.redraw();
    	
//    	progressBar.setIndeterminate(true);
//        Thread worker = new Thread() {
//            public void run() {
//            	loadShoppingList();
//                
//                // Report the result using invokeLater().
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
////                    	progressBar.setIndeterminate(false);
//                    }
//                });
//            }
//        };
//        worker.start(); // So we don't hold up the dispatch thread.
    	
    }
   
	
    private void help_clicked(java.awt.event.ActionEvent evt) {                                                
    	log.info("Help Clicked");
    	this.deselectAll();
    	// select actual
    	helpButton.setSelected(true);
    	this.emptyBox();
    	this.loadHelp();
    	this.redraw();
    }
    
    public void showSingleRecipe(int recipeID) {                                                
    	log.info("ShowSingleRecipe");
    	this.deselectAll();
    	// select actual
    	recipes_button.setSelected(true);
    	this.emptyBox();
    	this.loadRecipe(recipeID);
    	this.redraw();
    }
	
    private void deselectAll() {
    	for (MainMenuButton button : mainButtons) {
    		button.setSelected(false);
		}
    }
    
    /*
     * LOAD SERVICES
     */
    
    private void loadNutritionalMenus() {
    	MenusSubServiceLauncher nutritionalMenus = new MenusSubServiceLauncher(this);
    	nutritionalMenus.canvas = this.canvas;
    	nutritionalMenus.showSubService();
    	this.redraw();
//    	/*
//    	 * The Service is available in some remote bundle, check if it's available
//    	 */
//    	ServiceReference s = Activator.bundleContext.getServiceReference(ServiceInterface.SUBSERVICE_NUTRITIONAL_ADVISOR_NUTRITIONAL_MENUS);
//		if (s!=null) {
//			na.services.nutritionalMenus.MenusSubServiceLauncher nutritionalMenus = (na.services.nutritionalMenus.MenusSubServiceLauncher) Activator.bundleContext.getService(s);
//			nutritionalMenus.canvas = this.canvas;
//			nutritionalMenus.showSubService();
//		} else {
//			log.info(" Nutritional Menus bundle NOT available!");
//			Utils.Errors.showError(this.canvas, "Nutritional Menus bundle NOT available!");
//		}
    }
	
	private void loadRecipes() {
		RecipesSubServiceLauncher recipesSubService = new RecipesSubServiceLauncher();
		recipesSubService.canvas = this.canvas;
		recipesSubService.showSubService();
		this.redraw();
    }
	
	private void loadRecipe(int recipeID) {
		RecipesSubServiceLauncher recipesSubService = new RecipesSubServiceLauncher();
		recipesSubService.canvas = this.canvas;
		recipesSubService.showSubServiceWithRecipe(recipeID);
		this.redraw();
    }
	
	 private void loadShoppingList() {
		 ShoppingListSubServiceLauncher shoppingListSubService = new ShoppingListSubServiceLauncher();
		 shoppingListSubService.canvas = this.canvas;
		 shoppingListSubService.showSubService();
		 this.redraw();
	 }
 
	private void loadMyNutritionalProfile() {
		NutriProfileSubServiceLauncher myProfileSubService = new NutriProfileSubServiceLauncher();
		myProfileSubService.canvas = this.canvas;
		myProfileSubService.showSubService();
		this.redraw();
    }
	
	private void loadHelp() {
		log.info("loading help");
		HelpSubServiceLauncher helpServiceLauncher = new HelpSubServiceLauncher();
		helpServiceLauncher.canvas = this.canvas;
		helpServiceLauncher.showSubService();
		this.redraw();
    }
    
    private void emptyBox() {
    	this.canvas.removeAll();
    	this.canvas.validate();
		this.canvas.repaint();
	}
    
    public void redraw() {
    	this.canvas.validate();
		this.canvas.repaint();
	}

    private void loadDefaultService() {
    	this.deselectAll();
    	this.emptyBox();
    	
//    	NUTRITIONAL MENUS
    	nutritionalMenus_button.setSelected(true);
    	this.loadNutritionalMenus();
    	
//    	RECIPES
//    	recipes_button.setSelected(true);
//    	this.loadRecipes();
    	
//    	SHOPPING LIST
//    	shoppingList_button.setSelected(true);
//    	this.loadShoppingList();
    	
//    	PROFILE
//    	myNutriProfile_button.setSelected(true);
//    	this.loadMyNutritionalProfile();
    	
//    	HELP
//    	helpButton.setSelected(true);
//    	this.loadHelp();
    }

    private void loadLoginWindow() {
    	this.removeAll();
    	LoginWindow login = new LoginWindow();
    	login.setLabel("User not logged in. Please login first using the uAAL login!");
//    	this.canvas.add(login);
    	this.add(login);
    }

}
