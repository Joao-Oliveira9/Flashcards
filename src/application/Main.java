package application;	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	private Parent root;
	private ObservableList<Flashcard> list = FXCollections.observableArrayList();
	
@Override
public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));	
			root = loader.load();
			MenuController menuController = loader.getController();
			list = lerArquivo();
			
			int contador = 0;
			menuController.armazenarFlashcards(list,contador);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

private static ObservableList<Flashcard> lerArquivo() throws IOException {
		 ObservableList<Flashcard> listaDeFlashcards = FXCollections.observableArrayList();
	        try (BufferedReader reader = new BufferedReader(new FileReader("dados.txt"))) {
	            String pergunta, resposta,caixaString;
	            int caixa;
	            while ((pergunta = reader.readLine()) != null && (resposta = reader.readLine()) != null && (caixaString = reader.readLine()) != null) {
	            	caixa = Integer.parseInt(caixaString);
	            	Flashcard usuario = new Flashcard(pergunta,resposta,caixa);
	            	listaDeFlashcards.add(usuario);
	            }  
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return listaDeFlashcards;
	    }
}
