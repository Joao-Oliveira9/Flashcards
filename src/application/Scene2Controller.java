package application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Scene2Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int contador;

    @FXML
    private TableColumn<Flashcard, String> pergunta;

    @FXML
    private TableColumn<Flashcard, String> resposta;

    @FXML
    private TableView<Flashcard> table;
    
    
    @FXML
    private TableColumn<Flashcard, String> caixa;

    private ObservableList<Flashcard> list = FXCollections.observableArrayList(); 

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pergunta.setCellValueFactory(new PropertyValueFactory<>("pergunta"));
        resposta.setCellValueFactory(new PropertyValueFactory<>("resposta"));
        caixa.setCellValueFactory(new PropertyValueFactory<>("caixaLeitner"));
        
        table.setItems(list);
    }

    public void displayName(ObservableList<Flashcard> listNova,int contador) {
        list.addAll(listNova);
        this.contador = contador;
    }
    
    public void criarFlashcard(ActionEvent event) throws IOException {
    	
    	for (Flashcard flashcard : list) {
	        System.out.println("Pergunta: " + flashcard.getPergunta() + ", Resposta: " + flashcard.getResposta());
	    }
    	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
        root = loader.load();
        
        Scene1Controller scene1Controller = loader.getController();
        
        scene1Controller.armazenarFlashcards(list,contador);
        //scene1Controller = new Scene1Controller(list);
        
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void menu(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        root = loader.load();
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        
        MenuController scene1Controller = loader.getController();
        
        scene1Controller.armazenarFlashcards(list,contador);
        
        
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void removerFlashcard(ActionEvent event) throws IOException {
    	 File file = new File("./src/application/dados.txt");
    		
		 
 		String FILE_PATH  = file.getAbsolutePath().replace("\\", "/");
 		FILE_PATH = FILE_PATH .replaceAll("/\\.\\/", "/");
    	ObservableList<Flashcard> allFlashcards,singleFlashcard;
    	allFlashcards = table.getItems();
    	singleFlashcard = table.getSelectionModel().getSelectedItems();
    	 
    	 
    	 List<String> lines = readLinesFromFile("dados.txt");
    	 lines = removeLinesContainingText(lines,singleFlashcard.get(0).pergunta);
    	 writeLinesToFile("dados.txt", lines);
    	 singleFlashcard.forEach(allFlashcards::remove);	
    }
    
    
    public void carregar(ActionEvent event) {
	           try (BufferedWriter writer = new BufferedWriter(new FileWriter("CarregarDados.txt",false))) {
	        	   for(int i = 0; i<10;i++) 
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
    
     public void trocar(ActionEvent event) throws IOException  {
    	 ObservableList<Flashcard> singleFlashcard;
    	 singleFlashcard = table.getSelectionModel().getSelectedItems();
 
     	FXMLLoader loader = new FXMLLoader(getClass().getResource("Teste.fxml"));
     	 
        root = loader.load();
       
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
     
        TesteController teste = loader.getController();
        teste.mostrar(list,contador,singleFlashcard);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
     	
     }
     
    
     
     
     
     public void loadE() throws IOException{
    	 	FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            root = loader.load();
            
            //stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            
            
            MenuController scene1Controller = loader.getController();
            
            scene1Controller.armazenarFlashcards(list,contador);
            
            
            
            scene = new Scene(root);
            //stage.setScene(scene);
            stage.show();
        	
    	 
     }
    public void exportarArquivo(ActionEvent event) throws IOException {
    	/*
   	 File file = new File("./src/application/dados.txt");
   		
		 
		String FILE_PATH  = file.getAbsolutePath().replace("\\", "/");
		FILE_PATH = FILE_PATH .replaceAll("/\\.\\/", "/");
		 
   	
   	
   	 ObservableList<Flashcard> allFlashcards,singleFlashcard;
   	 allFlashcards = table.getItems();
   	 singleFlashcard = table.getSelectionModel().getSelectedItems();
   	 //System.out.print(singleFlashcard.get(0).resposta);
   	 //String caixaLeitner = String.valueOf(singleFlashcard.get(0).caixaLeitner);
   	 
   	 //removerFlashcardBd(singleFlashcard.get(0).pergunta);
   	 //removerFlashcardBd(singleFlashcard.get(0).resposta);
   	 //removerFlashcardBd(caixaLeitner);
   	 
   	 
   	 List<String> lines = readLinesFromFile(FILE_PATH);
   	 lines = removeLinesContainingText(lines,singleFlashcard.get(0).pergunta);
   	 writeLinesToFile(FILE_PATH, lines);
   	 criarArquivo(singleFlashcard.get(0).pergunta,singleFlashcard.get(0).resposta,singleFlashcard.get(0).getCaixaLeitner());
   	 
   	 
   	
   	 
   	 
   	 singleFlashcard.forEach(allFlashcards::remove);
   	 
   	*/
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("Trocar.fxml"));
         root = loader.load();
         
         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         
         
         //Scene3Controller trocaController = loader.getController();
         
         //trocaController.armazenarDados();
         
         //trocaController.teste();
         
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
       
   	 
   	 }
    
    
   
    public void criarArquivo(String pergunta,String resposta,String caixaLeitner) {
        // Cria um objeto File com o nome especificado
    	
    	Random random = new Random();
        
        //Gerar um número inteiro aleatório entre 0 e 99
        int randomInt = random.nextInt(100);
        String numeroAleatorio = String.valueOf(randomInt);
        File arquivo = new File("teste1" + numeroAleatorio+ ".txt");
        
        // Tenta criar e escrever no arquivo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
        	escritor.write(pergunta);
        	escritor.write("\n");
        	escritor.write(resposta);
        	escritor.write("\n");
        	escritor.write(caixaLeitner);
        	escritor.write("\n");
        	
            System.out.println("Arquivo criado e conteúdo escrito com sucesso!");
        } catch (IOException e) {
            // Caso ocorra alguma exceção, exibe a mensagem de erro
            System.err.println("Erro ao criar ou escrever no arquivo: " + e.getMessage());
        }
    }
    
    
    
    
    private static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private static List<String> removeLinesContainingText(List<String> lines, String textToFind) {
        List<String> modifiedLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(textToFind)) {
            	System.out.println("parte do remover" + lines.get(i));
                i += 2;  // Pular a linha atual e as duas seguintes
            } else {
                modifiedLines.add(lines.get(i));
            }
        }
        return modifiedLines;
    }

    private static void writeLinesToFile(String filePath, List<String> lines) throws IOException {
    	
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String line : lines) {
        	//System.out.println("linha:" + line);
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
    
    
    
    
}
