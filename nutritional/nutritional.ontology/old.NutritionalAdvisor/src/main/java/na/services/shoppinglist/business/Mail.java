package na.services.shoppinglist.business;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Mail {
	private Log log = LogFactory.getLog(Mail.class);
	
	private boolean sendMailWithGMail(String destination,
			String subject, String content, String file) throws MessagingException {
		String host = "smtp.gmail.com";
	    String from = "oasisnutritionaladvisor";
	    String pass = "nutritional2010";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");

	    String[] to = {destination}; // added this line

	    Session session = Session.getDefaultInstance(props, null);
		 // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         
 	    InternetAddress[] toAddress = new InternetAddress[to.length];
         
         	    // To get the array of addresses
         	    for( int i=0; i < to.length; i++ ) { // changed from a while loop
         	        try {
         				toAddress[i] = new InternetAddress(to[i]);
         			} catch (AddressException e) {
         				log.info("error proccessing email...");
         				e.printStackTrace();
         			}
         	    }
 	    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
	        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	        log.info("to: "+ toAddress[i]);
	    }

         // Set Subject: header field
         message.setSubject(subject);

         // Create the message part 
         MimeBodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         messageBodyPart.setText(content, "utf-8");
         messageBodyPart.setHeader("Content-Type","text/plain; charset=\"utf-8\"");
         messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
         
         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         if (file!=null && file.length()>0) {
	         String filename = file;
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);
         }

         // Send the complete message parts
         message.setContent(multipart );

         // Send message
 		Transport transport = session.getTransport("smtp");
 	    transport.connect(host, from, pass);
 	    transport.sendMessage(message, message.getAllRecipients());
 	    transport.close();
         log.info("Sent message successfully....");
         return true;
	}

	public boolean sendMail(String to, String subject, String content, String file) throws Exception {
		// Send a test message
		return sendMailWithGMail(to, subject, content, file);
	}
}