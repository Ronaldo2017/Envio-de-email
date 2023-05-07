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

public class ObjetoEnviaEmail {

	private String userName = "luisronaldocosta@gmail.com";
	private String password = "qdbuvpqvaqqzvocj";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHTML) throws Exception {
		// criando a conexao com o servidor

		Properties properties = new Properties();

		// autenticação
		properties.put("mail.smtp.ssl.trust", "*");
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
		// System.out.println("Conectou: " + session);

		// destinatarios
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		// quem esta enviando
		message.setFrom(new InternetAddress(userName, nomeRemetente));
		// email de destino
		message.setRecipients(Message.RecipientType.TO, toUser);
		// assunto do email
		message.setSubject(assuntoEmail);
		// texto do email
		message.setText(textoEmail);

		// verifica se manda o texto com html ou não
		if (envioHTML) {
			message.setContent(textoEmail, "text/html");
		} else {
			message.setText(textoEmail);
		}
		/** Método para enviar a mensagem criada */
		Transport.send(message);

	}

}

/*
 * Caso o email não esteja sendo enviado colocar um tempo de espera mais so pode
 * ser usado para fins de testes
 * 
 */
// Thread.sleep(3000);
