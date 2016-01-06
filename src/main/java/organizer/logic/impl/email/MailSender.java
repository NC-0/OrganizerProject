package organizer.logic.impl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import organizer.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {

	@Autowired
	@Qualifier("simpleMailSender")
	private JavaMailSender simpleMailSender;

	@Async
	public void sendMail(User user,String verificationId) throws MessagingException{
		simpleMailSender.send(createCustomMessage(user,verificationId));
		System.out.println("lala");
	}

	public MimeMessage createCustomMessage(User user,String verificationId) throws  MessagingException{
		MimeMessage message = simpleMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		messageHelper.setFrom("oda21101@outlook.com");
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject("SuperOrganizer.com email verification");
		messageHelper.setText("<html><body><b>Hello "+user.getName()+" you succesfully registered on SuperOrganizer.com. Please go to this link <a href='http://aa.com'>http://superorganizer.com?verific="+verificationId+"</a></body></html>",true);
		return message;
	}
}