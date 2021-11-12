package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable{
	
	// VBOX
	private VBox defaultVbox;

	// MENU ITEMS
	@FXML
	private MenuItem studentMenuItem;
	
	@FXML
	private MenuItem teacherMenuItem;
	
	@FXML
	private MenuItem aboutMenuItem;
	
	@FXML
	private MenuItem quitMenuItem;
	
	//BUTTONS
	
	
	//EVENTS - ACTIONS
	public void onAboutMenuAction() {
		System.out.println("ABOUT BUTTON CLICCKED");
		
		//TODO - IMPLEMENT A VIEW TO SHOW DEVELOPER INFORMATION.
	}
	public void onQuitMenuAction() {
		System.exit(0);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}

}
