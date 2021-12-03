package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ClassService;

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
	@FXML
	public void onAboutMenuAction() {
		System.out.println("ABOUT BUTTON CLICCKED");
		loadView("/gui/AboutView.fxml", x -> {});
		//TODO - IMPLEMENT A VIEW TO SHOW DEVELOPER INFORMATION.
	}
	
	@FXML
	public void onClassesMenuAction() {
		System.out.println("ABOUT BUTTON CLICCKED");
		loadView("/gui/ClassList.fxml", (ClassListController controller) -> {
			controller.setClassService(new ClassService());
			controller.updateTableView();
		});

	}
	
	
	@FXML
	public void onQuitMenuAction() {
		System.exit(0);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}
	
	private  synchronized <T> void loadView(String absoluteViewName, Consumer<T> initializingAction) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteViewName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			
			initializingAction.accept(controller);
		} catch (IOException e){
			Alerts.showAlert("IO Exception", "Error Loading View", e.getMessage(), AlertType.ERROR);
		}
	}

}
