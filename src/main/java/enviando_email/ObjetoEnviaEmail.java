package enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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
			message.setContent(textoEmail, "text/html; charse=utf-8");
		} else {
			message.setText(textoEmail);
		}
		/** Método para enviar a mensagem criada */
		Transport.send(message);

	}

	public void enviarEmailPDF(boolean envioHTML) throws Exception {
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

		/*
		 * Parte 1 do email que é o texto e a descricao do email
		 */

		MimeBodyPart corpoEmail = new MimeBodyPart();

		// verifica se manda o texto com html ou não
		if (envioHTML) {
			corpoEmail.setContent(textoEmail, "text/html; charse=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}

		// mandar uma lista de pdf's
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorPDF());
		arquivos.add(simuladorPDF());
		arquivos.add(simuladorPDF());
		arquivos.add(simuladorPDF());

		// junta parte 1 e parte 2
		// cria o corpo do email
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {

			// parte 2 do e-mail que são os anexos do pdf
			MimeBodyPart anexoEmail = new MimeBodyPart();
			// onde é passado o simuladorPDF voce passa o seu arquivo gravado no bd
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail" + index + ".pdf");

			multipart.addBodyPart(anexoEmail);

			index++;
		}
		message.setContent(multipart);

		/** Método para enviar a mensagem criada */
		Transport.send(message);

	}

	/*
	 * Simula o PDF ou qualquer arquivo que possa se enviado por anexo no email Pode
	 * pegar o arquivo no bd, base 64, byte[], Stream de arquivos Pode estar no bd,
	 * ou em uma pasta
	 * 
	 * Retorna um PDF em branco com o texto do paragrafo de exemplo
	 */

	private FileInputStream simuladorPDF() throws Exception {
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Teste de envio de e-mail, como anexo com java.mail"));
		document.close();

		return new FileInputStream(file);

	}

}

/*
 * Caso o email não esteja sendo enviado colocar um tempo de espera mais so pode
 * ser usado para fins de testes
 * 
 */
// Thread.sleep(3000);
