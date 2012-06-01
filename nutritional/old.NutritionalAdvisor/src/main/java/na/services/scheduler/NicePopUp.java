package na.services.scheduler;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.textbox.AdaptiveTextBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NicePopUp extends JDialog {
	
	private AdaptiveTextBox box;
	private JButton image;
	private String myTitle;

	
	public NicePopUp() {
		setModal(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		getContentPane().setLayout(gridBagLayout);
		{
			JPanel panel = new JPanel();
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{200, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 5, 40, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			getContentPane().add(panel, gbc);
			{
				image = new JButton("");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(image, gbc_1);
			}
			{
				box = new AdaptiveTextBox();
				box.setColumns(20);
				box.setRows(5);
				box.setLineWrap(true);
				box.setEditable(false);
				box.setWrapStyleWord(true);
				box.setSize(300, 200);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 1;
				panel.add(box, gbc_1);
			}
			{
				AdaptiveButton btnAceptar = new AdaptiveButton();
				btnAceptar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
//						System.out.println("Closing dialog");
						setVisible(false);
					}
				});
				btnAceptar.setText(Messages.Basic_Continue);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 3;
				panel.add(btnAceptar, gbc_1);
			}
		}
	}
	
	public void setText(String text) {
		this.box.setText(text);
	}
	
	public void setImage(ImageIcon icon) {
		if (icon!=null) {
			this.image.setIcon(icon);
			this.image.setSize(icon.getIconWidth(), icon.getIconHeight());
		}
	}

	public void setMyTitle(String title) {
		this.myTitle = title;
	}

}
