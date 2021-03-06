package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import application.exceptions.DatabaseException;
import application.exceptions.ValidationException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Lecture;
import model.entities.Student;
import model.services.LectureService;
import model.services.StudentService;

public class StudentFormController implements Initializable{
	
	private Student entity;
	
	private StudentService service;
	
	private LectureService lectureService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField idField;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private ComboBox<Lecture> lecturesCombobox;
	
	@FXML
	private Label errorLabelName;
	
	@FXML
	private Label errorLabelEmail;
	
	@FXML
	private Button saveButton;
	
	@FXML
	private Button cancelButton;
	
	private ObservableList<Lecture> lectureObsList;
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void setStudent(Student entity) {
		this.entity = entity;
	}
	
	public void setServices (StudentService studentService, LectureService lectureService) {
		this.service = studentService;
		this.lectureService = lectureService;
	}
	
	@FXML
	public void onSaveButtonAction(ActionEvent event) {
		
		if (entity == null) {
			throw new IllegalStateException("Entity cannot be null!");
		}
		if (service == null) {
			throw new IllegalStateException("Service cannot be null!");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.getCurrentStage(event).close();
			notifyDataChangeListeners();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DatabaseException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Student getFormData() {
		Student obj = new Student();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParseToInt(idField.getText()));
		
		if(nameField.getText() == null || nameField.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty!");
		}
		
		obj.setName(nameField.getText());
		
		if (emailField.getText() == null || emailField.getText().trim().equals("")) {
			exception.addError("email", "Field can't be empty!");
		}
		
		obj.setEmail(emailField.getText());
		
		if (exception.getErrors().size() > 0) { 
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onCancelButtonAction(ActionEvent event) {
		Utils.getCurrentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(idField);
		Constraints.setTextFieldMaxLength(nameField, 50);
		Constraints.setTextFieldMaxLength(emailField, 100);
		
		initializeLectureComboBox();
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity cannot be null!");
		}
		
		idField.setText(String.valueOf(entity.getId()));
		nameField.setText(entity.getName());
		emailField.setText(entity.getEmail());
		
		//TODO - IMPLEMENTAR MANEIRA DE SALVAR DEPARTAMENTOS VINCULADOS AO ALUNO,
		// e trazer essa info pra comboBox
//		lecturesCombobox.setValue(entity.getName());
	}
	
	public void loadAssociatedObjects() {
		if (lectureService == null) {
			throw new IllegalStateException("Lecture Service cannot be null!");
		}
		List<Lecture> list = lectureService.findAll();
		lectureObsList = FXCollections.observableArrayList(list);
		lecturesCombobox.setItems(lectureObsList);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		errorLabelName.setText(fields.contains("name") ? errors.get("name") : "");

		errorLabelEmail.setText(fields.contains("email") ? errors.get("email") : "");
	}
	
	private void initializeLectureComboBox() {
		Callback<ListView<Lecture>, ListCell<Lecture>> factory = lv -> new ListCell<Lecture>() {
			@Override
			protected void updateItem(Lecture item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		lecturesCombobox.setCellFactory(factory);
		lecturesCombobox.setButtonCell(factory.call(null));
	}
}
