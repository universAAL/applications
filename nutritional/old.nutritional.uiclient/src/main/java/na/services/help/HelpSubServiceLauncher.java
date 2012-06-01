package na.services.help;


import na.services.help.ui.*;

public class HelpSubServiceLauncher {

	public na.widgets.panel.AdaptivePanel canvas;
	private SubServiceFrame subServiceFrame;
	/*
	 * Draws the first window for Nutritional Menus 
	 **/

	public void showSubService() {

		/**
		 * 
		 * Help content
		 *
		 */
		this.subServiceFrame = new SubServiceFrame(this);
		this.canvas.add(this.subServiceFrame);
		// by default show Today menus
		
//		this.repaint(this.canvas);
		this.redraw();
	}

	
//	private void repaint(JPanel panel) {
//		panel.repaint();
//		panel.revalidate();
//	}
	private void redraw() {
		this.canvas.validate();
		this.canvas.repaint();
	}
	
}
