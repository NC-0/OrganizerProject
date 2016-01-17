package organizer.logic.impl.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.models.Task;
import organizer.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class MailSender {

	@Autowired
	@Qualifier("simpleMailSender")
	private JavaMailSender simpleMailSender;

	@Async
	public void sendMail(String mail,String text,String subject,String body) throws MessagingException{
		simpleMailSender.send(createCustomMessage(mail,text,subject,body));
	}

	private MimeMessage createCustomMessage(String mail,String text,String subject,String body) throws  MessagingException{
		MimeMessage message = simpleMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		messageHelper.setFrom(MessageContent.MAIL);
		messageHelper.setTo(mail);
		messageHelper.setSubject(subject);
		messageHelper.setText(String.format(body, mail,text,text),true);
		return message;
	}
}