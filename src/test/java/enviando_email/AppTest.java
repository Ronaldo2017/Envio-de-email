package enviando_email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * olhar as configurações smtp para cada tipo de email para envio
 */
public class AppTest {

	private String userName = "luisronaldocosta@gmail.com";
	private String password = "qdbuvpqvaqqzvocj";

	@org.junit.Test
	public void testeEmail() {
		// criando a conexao com o servidor
		try {

			Properties properties = new Properties();

			// autorização
			properties.put("mail.smtp.auth", "true");
			// autenticação
			properties.put("mail.smtp.starttls", "true");
			// servidor gmail google
			properties.put("mail.smtp.host", "smtp.gmail.com");
			// porta do servidor
			properties.put("mail.smtp.port", "465");
			// especifica a porta a ser conectada pelo socket
			properties.put("mail.smtp.socketFactory.port", "465");
			// classe socket de conexao ao SMTP
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		
			// faz a conexao e autentica, autoriza, obtem o objeto de excessao
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
		//	System.out.println("Conectou: " + session);

			// destinatarios
			Address[] toUser = InternetAddress
					.parse("luisronaldocosta@gmail.com, ronaldo_so10@hotmail.com, costaronaldoluis@gmail.com");
			Message message = new MimeMessage(session);
			// quem esta enviando
			message.setFrom(new InternetAddress(userName));
			// email de destino
			message.setRecipients(Message.RecipientType.TO, toUser);
			// assunto do email
			message.setSubject("Chegou e-mail enviado com java");
			// texto do email
			message.setText(
					"Olá, você acaba de receber um e-mail enviado com Java do treinamento Formação Java Web. :D");

			/**Método para enviar a mensagem criada*/
			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
