package application;

public class Flashcard {
	 String pergunta, resposta;
	 int caixaLeitner;
	
	public Flashcard(String pergunta, String resposta,int caixaLeitner)
	{
		this.pergunta = pergunta;
		this.resposta = resposta;	
		this.caixaLeitner = caixaLeitner;
	}
	
	public String getPergunta() {
		return pergunta;
	}
	
	public String getResposta() {
		return resposta;
	}
	
	public String getCaixaLeitner() {
		String Leitner = String.valueOf(caixaLeitner);
		return Leitner;
	}
}
