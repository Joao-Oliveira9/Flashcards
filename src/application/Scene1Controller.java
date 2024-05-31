package application;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene1Controller {

	@FXML
	TextField perguntaTextField;
	
	@FXML
	TextField respostaTextField;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private int contador;
	
	private ObservableList<Flashcard> list = FXCollections.observableArrayList();
	
	public void armazenarFlashcards(ObservableList<Flashcard> listNova,int contador) {
		list.addAll(listNova);
		this.contador = contador;

	}
	
	public void login(ActionEvent event) throws IOException {
		String pergunta = perguntaTextField.getText();
		String resposta = respostaTextField.getText();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));	
		root = loader.load();	
		
	
		Flashcard flashcard = new Flashcard(pergunta,resposta,3);
		
		list.add(flashcard);
		
		saveTextFieldContent(flashcard);
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(list,contador);	
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	
	private void saveTextFieldContent(Flashcard flashcard) {
    	String pergunta = flashcard.getPergunta();
    	String resposta = flashcard.getResposta();
    	String caixa = String.valueOf(flashcard.getCaixaLeitner());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados.txt",true))) {
            writer.write(pergunta);
            writer.write("\n");
            writer.write(resposta);
            writer.write("\n");
            writer.write(caixa);
            writer.write("\n");
            //writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
}