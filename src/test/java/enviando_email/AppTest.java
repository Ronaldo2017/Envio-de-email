package enviando_email;

/**
 * olhar as configurações smtp para cada tipo de email para envio
 */
public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception {

		// CRIANDO O HTML
		StringBuilder stringBuilderTextoEmail = new StringBuilder();

		stringBuilderTextoEmail.append("Olá, <br/><br/>");
		stringBuilderTextoEmail.append("Você está recebendo o acesso ao google.<br/><br/>");
		stringBuilderTextoEmail.append("Clique no link abaixo.<br/><br/>");
		stringBuilderTextoEmail.append("<a target=\"blank\" href=\"https://www.google.com/\" style=\"color=#2525a7;padding=14px 25px; text-align:center; text-decoration: none;display:inline-block; border-radius:30px; font-size:20px; font-family:Arial; border:3px solid green; background-color: #99DA39\">Acesso</a><br/><br/>");

		stringBuilderTextoEmail
				.append("<span style=\"font-size=8px\">Ass.: Ronaldo Costa, Desenvolvedor de software em JAVA</span>");

		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("luisronaldocosta@gmail.com, costaronaldoluis@gmail.com",
				"Ronaldo Costa", "Testando envio de e-mail com java", stringBuilderTextoEmail.toString());

		/*
		 * O PARAMETRO FALSE = VEM DA CLASSE ONDE É VERIFICADO SE ENVIA O EMAIL COM HTML
		 * OU NAO
		 */
		enviaEmail.enviarEmail(true);
	}

}
