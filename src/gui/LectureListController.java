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
import model.entities.Lecture;
import model.services.LectureService;

public class LectureListController implements Initializable {
	
	private LectureService service;
	
	@FXML
	private TableView<Lecture> lectureTableView;
	
	@FXML
	private TableColumn<Lecture, Integer> lectureIdTableColumn;
	
	@FXML
	private TableColumn<Lecture, String> lectureNameTableColumn;
	
	@FXML
	private Button lectureNewButton;
	
	private ObservableList<Lecture> obsList;
	
	@FXML
	public void onNewLectureButtonAction(ActionEvent event) {
		Stage parentStage = Utils.getCurrentStage(event);
		Lecture obj = new Lecture();
		createDialogForm(obj, "/gui/LectureForm.fxml", parentStage);
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}


	private void initializeNodes() {
		lectureIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		lectureNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		//PEGA UMA REFERENCIA DO PALCO ATUAL, E BINDA O HEIGHT DA TABLEVIEW COM O HEIGHT DO STAGE
		lectureTableView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void setClassService(LectureService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null"); 
		}
		
		List<Lecture> list = service.findAll();
		
		obsList = FXCollections.observableArrayList(list);
		
		lectureTableView.setItems(obsList);
	}
	
	private void createDialogForm(Lecture entity, String absoluteViewName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteViewName));
			Pane pane = loader.load();
			
			LectureFormController controller = loader.getController();
			controller.setLecture(entity);
			controller.updateFormData();
			
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
