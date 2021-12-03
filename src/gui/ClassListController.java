package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Class;

public class ClassListController implements Initializable {
	
	@FXML
	private TableView<Class> classTableView;
	
	@FXML
	private TableColumn<Class, Integer> classIdTableColumn;
	
	@FXML
	private TableColumn<Class, String> classNameTableColumn;
	
	@FXML
	private Button classNewButton;
	
	@FXML
	public void onClassNewButtonAction() {
		System.out.println("New Class Clicked");
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		initializeNodes();
	}


	private void initializeNodes() {
		classIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		classNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		//PEGA UMA REFERENCIA DO PALCO ATUAL, E BINDA O HEIGHT DA TABLEVIEW COM O HEIGHT DO STAGE
		classTableView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	

}
