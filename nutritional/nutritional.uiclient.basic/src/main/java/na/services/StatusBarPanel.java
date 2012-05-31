package na.services;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;

import na.oasisUtils.ami.NutritionalCache;
import na.oasisUtils.profile.ProfileConnector;
import na.utils.Setup;
import na.widgets.button.AdaptiveButton;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class StatusBarPanel extends AdaptivePanel {
	
	private Log log = LogFactory.getLog(StatusBarPanel.class);
	
	public StatusBarPanel() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			if (Setup.getShowEmptyCache()) {
				AdaptiveButton btnEmptyCache = new AdaptiveButton();
				btnEmptyCache.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
//						AmiPropertyChangeEvent p = new AmiPropertyChangeEvent();
//						p.setNewValue("high");
//						ProfileConnector.getInstance().amiPropertyChange(p);
//						log.info("Clicked!!");
//						ProfileConnector.getInstance().amiPropertyChange(p)
						log.info("Emptying cache...");
						NutritionalCache cache = new NutritionalCache();
						cache.emptyCache();
						log.info("Cache empty.");
					}
				});
				btnEmptyCache.setText("Empty Cache");
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.VERTICAL;
				gbc.gridx = 1;
				gbc.gridy = 0;
				add(btnEmptyCache, gbc);
			}
		}
	}
	
	public void setContent(JComponent j) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(j, gbc);
	}

}
