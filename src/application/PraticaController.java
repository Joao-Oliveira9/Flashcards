package application;

import javafx.scene.control.Button;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PraticaController{
    @FXML
    private Label perguntaLabel;

    @FXML
    private Label respostaLabel;
    
		private Stage stage;
    	private Scene scene;
    	private Parent root;
    	private int count = 0;
    	private int contadorAcertos = 0;
    	private int sentinelaResposta = -1;
    	private int sentinelaVirar = -1;
    	private int contadorErros = 0;
    	private int sentinelaPassar = -1;
    	
    	@FXML
    	private Button botaoSortear;
    	
    	int tamanho;
    	private ObservableList<Flashcard> list = FXCollections.observableArrayList();
    	
    	
    	private void mostrarPrimeiraPergunta(String pergunta) {
    		perguntaLabel.setText("Pergunta: " + pergunta);
    	}
    	
    	public void armazenarFlashcards(ObservableList<Flashcard> listNova,int contador) {
    		tamanho = listNova.size();
    		list.addAll(listNova);
      		mostrarPrimeiraPergunta(list.get(count).getPergunta());
      	}

        public void sortear(ActionEvent event) throws IOException {
        	if(count<tamanho && sentinelaPassar == 0) {
        		perguntaLabel.setText("Pergunta: " + list.get(count).getPergunta());
            	respostaLabel.setText("Resposta: ");
            	sentinelaVirar = -1;
            	
        	}
        	else if(count>=tamanho) {
        		botaoSortear.setText("Resultados");
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("Resultados.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                ResultadosController resultadoController = loader.getController();
                resultadoController.mostrarResultados(contadorAcertos,contadorErros,list,count);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();     		
        	}	
        }

       public void virar(ActionEvent event) throws IOException {  
    	   if(count<tamanho && sentinelaVirar == -1) {
    		   respostaLabel.setText("Resposta: " + list.get(count).getResposta());
        	   count++;
               sentinelaVirar = 0;
               sentinelaResposta = 0;
    	   }
        }
       
       public void irMenu(ActionEvent event) throws IOException {
       	   FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
           root = loader.load();
           stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           MenuController scene1Controller = loader.getController();
           saveTextFieldContent(list);
           scene1Controller.armazenarFlashcards(list,count);
           scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
       }

       public void acertar() {
    	   if(sentinelaResposta == 0 && sentinelaVirar == 0) {
    		   contadorAcertos++;
    		   sentinelaResposta = -1;
    		   if(list.get(count-1).caixaLeitner!=1) {
    			   Flashcard flashcard = list.get(count-1);
    			   flashcard.caixaLeitner--;
    		   }
    		   sentinelaPassar = 0;
    	   }
    	   if(count==tamanho) {
    		   botaoSortear.setText("Resultados");
    	   }
       }
       
       
       
       public void errar() {
    	   if(sentinelaResposta == 0 && sentinelaVirar == 0) { 
    		   contadorErros++;
    		   sentinelaResposta = -1;
    		   if(list.get(count-1).caixaLeitner!=3) {
    			   Flashcard flashcard = list.get(count-1);
    			   flashcard.caixaLeitner++;
    		   }
    		   sentinelaPassar = 0;
    	   }
    	   if(count==tamanho) {
    		   botaoSortear.setText("Resultados");
    	   }
       }

       private void saveTextFieldContent(ObservableList<Flashcard> list) {
	           try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados.txt",false))) {
	           	//writer.write("\n");
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
