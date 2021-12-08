package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.exceptions.DatabaseIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Student;
import model.services.StudentService;

public class StudentListController implements Initializable , DataChangeListener{
	
	private StudentService service;
	
	@FXML
	private TableView<Student> StudentTableView;
	
	@FXML
	private TableColumn<Student, Integer> StudentIdTableColumn;
	
	@FXML
	private TableColumn<Student, String> StudentNameTableColumn;
	
	@FXML
	private TableColumn<Student, Student> StudentEditTableColumn;
	
	@FXML
	private TableColumn<Student, Student> StudentDeleteTableColumn;
	
	@FXML
	private Button StudentNewButton;
	
	private ObservableList<Student> obsList;
	
	@FXML
	public void onNewStudentButtonAction(ActionEvent event) {
		Stage parentStage = Utils.getCurrentStage(event);
		Student obj = new Student();
		createDialogForm(obj, "/gui/StudentForm.fxml", parentStage);
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}


	private void initializeNodes() {
		StudentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		StudentNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		//PEGA UMA REFERENCIA DO PALCO ATUAL, E BINDA O HEIGHT DA TABLEVIEW COM O HEIGHT DO STAGE
		StudentTableView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void setStudentService(StudentService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null"); 
		}
		
		List<Student> list = service.findAll();
		
		obsList = FXCollections.observableArrayList(list);
		
		StudentTableView.setItems(obsList);
		
		initEditButtons();
		initDeleteButtons();
	}
	
	private void createDialogForm(Student entity, String absoluteViewName, Stage parentStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteViewName));
//			Pane pane = loader.load();
//			
//			StudentFormController controller = loader.getController();
//			controller.setStudent(entity);
//			controller.setStudentService(new StudentService());
//			//SE INSCREVE PARA RECEBER OS EVENTOS DO FORM CONTROLLER
//			controller.subscribeDataChangeListener(this);
//			
//			controller.updateFormData();
//			
//			
//			Stage dialogStage = new Stage();
//			
//			dialogStage.setTitle("Enter Class data");
//			dialogStage.setScene(new Scene(pane));
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//			
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading view.", e.getMessage(), AlertType.ERROR);
//		}
	}


	@Override //RECEBE ESSE EVENTO SEMPRE QUE O FORM DISPARAR UM EVENTO
	public void onDataChanged() {
		updateTableView();	
	}
	
	private void initEditButtons() {
		StudentEditTableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		StudentEditTableColumn.setCellFactory(param -> new TableCell<Student, Student>(){
			private final Button button = new Button("Edit");
			
			@Override
			protected void updateItem(Student obj, boolean empty) {
				super.updateItem(obj, empty);
				
				if (obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(
								obj, "/gui/StudentForm.fxml", Utils.getCurrentStage(event)));
			}
		});
	}
	
	private void initDeleteButtons() {
		StudentDeleteTableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		StudentDeleteTableColumn.setCellFactory(param -> new TableCell<Student, Student>(){
			private final Button button = new Button("Delete");
			
			@Override
			protected void updateItem(Student obj, boolean empty) {
				super.updateItem(obj, empty);
				
				if (obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void removeEntity(Student obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Deletion Confirmation", "Are you sure you want delete this entity?");
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service cannot be null!");
			}
			
			try {
				service.remove(obj);
				updateTableView();
			} catch (DatabaseIntegrityException e) {
				Alerts.showAlert("Error removing entity", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
		
	}
}
