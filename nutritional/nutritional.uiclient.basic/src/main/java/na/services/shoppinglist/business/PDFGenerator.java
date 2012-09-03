package na.services.shoppinglist.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import na.miniDao.FoodItem;
import na.services.shoppinglist.VIsualShoppingList;
import na.utils.Utils;
import na.utils.lang.Messages;



/*import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;*/

public class PDFGenerator {
	
	public void createPDF(){}
	//public void createPDF(VIsualShoppingList superSL, Calendar startDate, int size) throws DocumentException, IOException {
		/*
		 * Ordenar por categoria
		 */
	/*	Map<String, FoodItem> sortedSL = VIsualShoppingList.sortByCategoryThenName(superSL.items);
//		Iterator itt = sortedSL.values().iterator();
//		while (itt.hasNext()) {
//			FoodItem item = ((FoodItem)itt.next());
//			log.info("it: "+item.getCategory() + 
//					"  "+ item.getName());
//		}
		
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(na.utils.ServiceInterface.PATH_PDF_FILE));
		document.open();
		
		// TITLE
		String selectedDays = "";
		String startMonth = Utils.Dates.getStringMonth(startDate.get(Calendar.MONTH));
		Calendar end = (Calendar) startDate.clone();
//		log.info("sizeDays: "+size);
		if (size <= 1) {
			selectedDays += startMonth + " " + startDate.get(Calendar.DAY_OF_MONTH);
		} else {
			end.add(Calendar.DAY_OF_MONTH, size-1);
			String endMonth = Utils.Dates.getStringMonth(end.get(Calendar.MONTH));
			selectedDays += startMonth + " " + startDate.get(Calendar.DAY_OF_MONTH) +
						" - " + endMonth + " " + end.get(Calendar.DAY_OF_MONTH);			
		}
//		log.info("selectedDays: '"+selectedDays+"'");
	
//		URL url = this.getClass().getResource("arialuni.ttf");
//		Utils.println("Font: "+url.getFile());
//		BaseFont unicode = BaseFont.createFont(url.getFile(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		BaseFont unicode = this.getIdentityFont("/na/services/shoppinglist/business/arialuni.ttf");
        FontSelector fs_18_bold = new FontSelector();
        fs_18_bold.addFont(new Font(unicode, 18, Font.BOLDITALIC));
        FontSelector fs_16_bold = new FontSelector();
        fs_16_bold.addFont(new Font(unicode, 15, Font.BOLD));
        FontSelector fs_normal = new FontSelector();
        fs_normal.addFont(new Font(unicode));
		
        
//		Paragraph title1 = new Paragraph(Messages.Shopping_pdf_MyShoppingList+": "+ selectedDays,
//				FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC));
        Phrase phrase = fs_18_bold.process(Messages.Shopping_pdf_MyShoppingList+": "+ selectedDays);
        Paragraph title1 = new Paragraph(phrase);
		Chapter chapter1 = new Chapter(title1, 1);

		chapter1.setNumberDepth(0);

		String cat = "-";
		List l = new List(true, false, 10);
		Section section = null;
		
		Iterator<FoodItem> it = sortedSL.values().iterator();
		int contador = 0;
		while (it.hasNext()) {
			FoodItem item = it.next();
//			log.info("Procesando: "+item.getName() + " origen: "+item.getSource());
			if (item.getCategory().compareTo(cat)!=0) { // Nueva seccion
				if (item.getSource().compareTo(VIsualShoppingList.Source_EXTRA)==0 || item.getSource().compareTo(VIsualShoppingList.Source_WEB)==0) {
				cat = item.getCategory();
				Phrase phrase_2 = fs_16_bold.process(Utils.Strings.capitalize(item.getCategory())+":");
				Paragraph title = new Paragraph(phrase_2);
//				Paragraph title = new Paragraph(Utils.Strings.capitalize(item.getCategory())+":", 
//				           FontFactory.getFont(FontFactory.HELVETICA, 16,
//				           Font.BOLD));
				section = chapter1.addSection(title);
				l = new List(true, false, 10);
					contador++;
					section.add(l);
					this.addItem(l, item.getName(), item.getAmount(), fs_normal);
				} else {
//					log.info(" no es extra ni web");
				}
			} else {
				if (item.getSource().compareTo(VIsualShoppingList.Source_EXTRA)==0 || item.getSource().compareTo(VIsualShoppingList.Source_WEB)==0) {
					contador++;
					this.addItem(l, item.getName(), item.getAmount(), fs_normal);
				} else {
//					log.info(" no es extra ni web");
				}
			}
		}
//		log.info("contador: "+contador);
		
		document.add(chapter1);
		document.close();
	}
	
	private BaseFont getIdentityFont(String path) throws DocumentException,
			IOException {
		URL fontResource = getClass().getClassLoader().getResource(path);
		if (fontResource == null)
			return null;
		String fontPath = fontResource.toExternalForm();
		if (path.toLowerCase().endsWith(".ttc")) {
			// String[] ttcNames = BaseFont.enumerateTTCNames(path);
			// first entry
			fontPath = fontPath + ",0";
		}
		BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		baseFont.setSubset(true);
		return baseFont;
	}
	
	private void addItem(List lista,String name, String amoun, FontSelector fs) {
		String amount = "";
		if (amoun==null)
			amount = "";
		else  {
			// hide grams
//			amount = " ("+ Utils.Strings.roundUp(amoun) +" grams)";
		}
//		lista.add(new ListItem("    "+Utils.Strings.capitalize(name) + amount));
		Phrase phrase_2 = fs.process("    "+Utils.Strings.capitalize(name) + amount);
		lista.add(new ListItem(phrase_2));
	}*/
}
 