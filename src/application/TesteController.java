package application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

public class TesteController {
	    private Stage stage;
	    private Scene scene;
	    private Parent root;
	    private int contador;
	    
	    
	    @FXML
	    private TextField nomeDoArquivo;

	    private ObservableList<Flashcard> list = FXCollections.observableArrayList();
	    private ObservableList<Flashcard> singleFlashcard = FXCollections.observableArrayList();
	
	    
	    
	    public void mostrar(ObservableList<Flashcard> listNova,int contador,ObservableList<Flashcard>singleFlashcard) {

			list.addAll(listNova);
			this.singleFlashcard = singleFlashcard;
			this.contador = contador;
		}
	    
	    
	    public void EscreverNoArquivo(ActionEvent event) throws IOException {
	    	
	    	System.out.print("Passei aqui");
	    	String nomeArquivo = criarArquivo(singleFlashcard.get(0).pergunta,singleFlashcard.get(0).resposta,singleFlashcard.get(0).getCaixaLeitner());
	    	  File arquivo = new File(nomeArquivo);
	    	  try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
		        	escritor.write(singleFlashcard.get(0).pergunta);
		        	escritor.write("\n");
		        	escritor.write(singleFlashcard.get(0).resposta);
		        	escritor.write("\n");
		        	escritor.write(singleFlashcard.get(0).getCaixaLeitner());
		        	escritor.write("\n");
		        } catch (IOException e) { 
		            System.err.println( e.getMessage());
		        }

	    	//vai rescrever meu arquivo sem o texto selecionado
	    	 List<String> lines = readLinesFromFile2("./src/application/dados.txt");
	    	 lines = removeLinesContainingText(lines,singleFlashcard.get(0).pergunta);
	    	 writeLinesToFile("./src/application/dados.txt", lines);
	    	 singleFlashcard.forEach(list ::remove);
	    	
	     
	
	        // Tenta criar e escrever no arquivo
	      
	        
	        //preciso ler um arquivo e jogar ele na lista e rescrever
	         list.addAll(readLinesFromFile(nomeDoArquivo.getText()));
	         saveTextFieldContent(list);   
	        
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));	
	 		root = loader.load();	
	 		MenuController menuController = loader.getController();
	 		menuController.armazenarFlashcards(list,contador);

	 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
	    
	    
	    public String criarArquivo(String pergunta,String resposta,String caixaLeitner) {
	    	Random random = new Random();
	        
	        int randomInt = random.nextInt(1000);
	        String numeroAleatorio = String.valueOf(randomInt);
	        String nomeDoArquivo = "teste1" + numeroAleatorio+ ".txt";
	        File arquivo = new File("teste1" + numeroAleatorio+ ".txt");

	        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
	        	escritor.write(pergunta);
	        	escritor.write("\n");
	        	escritor.write(resposta);
	        	escritor.write("\n");
	        	escritor.write(caixaLeitner);
	        	escritor.write("\n");
	        	
	            System.out.println("Arquivo criado e conteúdo escrito com sucesso!");
	        } catch (IOException e) {
	            System.err.println("Erro ao criar ou escrever no arquivo: " + e.getMessage());
	        }
	        return nomeDoArquivo;
	    }
	    
	    
	    private static List<String> readLinesFromFile2(String filePath) throws IOException {
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
	                i += 2;  
	            } else {
	                modifiedLines.add(lines.get(i));
	            }
	        }
	        return modifiedLines;
	    }

	    private static void writeLinesToFile(String filePath, List<String> lines) throws IOException {
	    	
	        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
	        for (String line : lines) {
	
	            writer.write(line);
	            writer.newLine();
	        }
	        writer.close();
	    }
	    
	    
	    private static ObservableList<Flashcard> readLinesFromFile(String nomeArquivo) throws IOException {
			 ObservableList<Flashcard> usuarios = FXCollections.observableArrayList();
		        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
		        	
		            String pergunta, resposta,caixaString;
		            int caixa;
		            
		            while ((pergunta = reader.readLine()) != null && (resposta = reader.readLine()) != null && (caixaString = reader.readLine()) != null) {
		            	caixa = Integer.parseInt(caixaString);
		            	Flashcard usuario = new Flashcard(pergunta,resposta,caixa);
		                usuarios.add(usuario);
		            }
		            
		        } catch (IOException e) {
		        	
		            e.printStackTrace();
		        }
		        return usuarios;
	    }
	
}
