package application;
import java.io.IOException;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class MenuController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int contador;
    
    private ObservableList<Flashcard> list = FXCollections.observableArrayList();
    
    public void armazenarFlashcards(ObservableList<Flashcard> list,int contador) {
		this.list = list;
		this.contador = contador; 
	}
    
    public void irSorteio(ActionEvent event) throws IOException {
    	Random random = new Random();
    	int randomIndex = random.nextInt(list.size());
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Sorteio.fxml"));	
		root = loader.load();	
	
		SorteioController sorteioController = loader.getController();
		sorteioController.armazenarFlashcards(list,contador,randomIndex);
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void irGerenciar(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));	
		root = loader.load();	
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(list,contador);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
    public void irPraticar(ActionEvent event) throws IOException{ 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Pratica.fxml"));	
		root = loader.load();	
		PraticaController praticaController = loader.getController();
		praticaController.armazenarFlashcards(list,contador);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
