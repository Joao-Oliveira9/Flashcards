package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SorteioController {
	private ObservableList<Flashcard> list = FXCollections.observableArrayList(); 
	int indiceAleatorio;
    @FXML
    private Label perguntaLabel;

    @FXML
    private Label respostaLabel;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int contador;
    
	
	
	public void armazenarFlashcards( ObservableList<Flashcard>list,int contador,int indiceAleatorio)
	{
		this.list = list;
		this.contador = contador;
		
		perguntaLabel.setText("Resposta: " + list.get(indiceAleatorio).pergunta);
		respostaLabel.setText("Pergunta: " + list.get(indiceAleatorio).resposta);
	}
	
	public void voltarMenu(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));	
		root = loader.load();	
		MenuController menuController = loader.getController();
		menuController.armazenarFlashcards(list,contador);

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	

}
