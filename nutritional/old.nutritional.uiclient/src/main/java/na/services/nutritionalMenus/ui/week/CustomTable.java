package na.services.nutritionalMenus.ui.week;

import java.awt.Color;
import java.awt.Component;

import javax.swing.table.TableCellRenderer;

import na.widgets.table.AdaptiveTable;


@SuppressWarnings("serial")
public class CustomTable extends AdaptiveTable {
	
	 public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
	        Component returnComp = super.prepareRenderer(renderer, row, column);
//	        Color alternateColor = new Color(252,242,206);
	        Color alternateColor = new Color(230,230,230);
	        Color whiteColor = Color.WHITE;
	        if (!returnComp.getBackground().equals(getSelectionBackground())){
	            Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
	            returnComp .setBackground(bg);
	            bg = null;
	        }
	        return returnComp;
	}
	 
}
