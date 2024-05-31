package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ResultadosController {
	    @FXML
	    private Label acertouLabel;
	    @FXML
	    private Label errouLabel;
	    private Stage stage;
    	private Scene scene;
    	private Parent root;
    	private int contador;
    		
    	ObservableList<Flashcard> list = FXCollections.observableArrayList();
    	
	    
	    public void mostrarResultados(int acertos,int erros,ObservableList<Flashcard> list1,int cont) {
	    	acertouLabel.setText("Acertou: " + acertos);
	    	errouLabel.setText("Errou: " + erros);
	    	list.addAll(list1);
	    	contador = cont;
	    }
	    
	    public void voltarMenu(ActionEvent event) throws IOException{
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
	        root = loader.load(); 
	        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 
	        saveTextFieldContent(list);
	        MenuController menuController = loader.getController();
	        menuController.armazenarFlashcards(list,contador);
	        scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    }
	    
	    private void saveTextFieldContent(ObservableList<Flashcard> list) {
	           try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados.txt",false))) {
	        	   for(int i = 0; i<list.size();i++) 
	        	   {
	        		   writer.write(list.get(i).getPergunta());
		               writer.write("\n");
		               writer.write(list.get(i).getResposta());
		               writer.write("\n");
		               writer.write(String.valueOf(list.get(i).getCaixaLeitner()));
		               writer.write("\n");
	        	   }
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
 
	       }
}
