package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Lecture;

public class LectureFormController implements Initializable{
	
	private Lecture entity;

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
	
	public void setClass(Lecture entity) {
		this.entity = entity;
	}
	
	@FXML
	public void onSaveButtonAction() {
		System.out.println("Clicked on Save!");
	}
	
	@FXML
	public void onCancelButtonAction() {
		System.out.println("Clicked on Cancel!");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(idField);
		Constraints.setTextFieldMaxLength(nameField, 50);
	}

}
