package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.exceptions.DatabaseException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Lecture;
import model.services.LectureService;

public class LectureFormController implements Initializable{
	
	private Lecture entity;
	
	private LectureService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField idField;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private Button saveButton;
	
	@FXML
	private Button cancelButton;
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void setLecture(Lecture entity) {
		this.entity = entity;
	}
	
	public void setLectureService (LectureService service) {
		this.service = service;
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
		} catch (DatabaseException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Lecture getFormData() {
		Lecture obj = new Lecture();
		obj.setId(Utils.tryParseToInt(idField.getText()));
		obj.setName(nameField.getText());
		
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
//		Constraints.setTextFieldInteger(idField); TODO - CONSERTAR BUG AO CLICAR EM NEW LECTURE
		Constraints.setTextFieldMaxLength(nameField, 50);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity cannot be null!");
		}
		
		idField.setText(String.valueOf(entity.getId()));
		nameField.setText(entity.getName());
	}

}
