package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Class;
import model.services.ClassService;

public class ClassListController implements Initializable {
	
	private ClassService service;
	
	@FXML
	private TableView<Class> classTableView;
	
	@FXML
	private TableColumn<Class, Integer> classIdTableColumn;
	
	@FXML
	private TableColumn<Class, String> classNameTableColumn;
	
	@FXML
	private Button classNewButton;
	
	private ObservableList<Class> obsList;
	
	@FXML
	public void onClassNewButtonAction(ActionEvent event) {
		Stage parentStage = Utils.getCurrentStage(event);
		createDialogForm("/gui/ClassForm.fxml", parentStage);
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}


	private void initializeNodes() {
		classIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		classNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		//PEGA UMA REFERENCIA DO PALCO ATUAL, E BINDA O HEIGHT DA TABLEVIEW COM O HEIGHT DO STAGE
		classTableView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void setClassService(ClassService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null"); 
		}
		
		List<Class> list = service.findAll();
		
		obsList = FXCollections.observableArrayList(list);
		
		classTableView.setItems(obsList);
	}
	
	private void createDialogForm(String absoluteViewName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteViewName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			
			dialogStage.setTitle("Enter Class data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view.", e.getMessage(), AlertType.ERROR);
		}
	}

}
