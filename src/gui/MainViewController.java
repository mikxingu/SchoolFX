package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import application.exceptions.DatabaseException;
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
import model.services.LectureService;
import model.services.StudentService;

public class MainViewController implements Initializable{
	
	// VBOX
	private VBox defaultVbox;

	// MENU ITEMS
	@FXML
	private MenuItem studentMenuItem;
	
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
	}
	
	@FXML
	public void onLecturesMenuAction() {
		System.out.println("LECTURES BUTTON CLICCKED");
		loadView("/gui/LectureList.fxml", (LectureListController controller) -> {
			controller.setLectureService(new LectureService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onStudentsMenuAction() {
		System.out.println("STUDENTS BUTTON CLICCKED");
		loadView("/gui/StudentList.fxml", (StudentListController controller) -> {
			controller.setStudentService(new StudentService());
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
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error Loading View", e.getMessage(), AlertType.ERROR);
		} catch (DatabaseException e) {
			Alerts.showAlert("Database Exception", "Error connecting to database", "Could not connect to the database.", AlertType.ERROR);
		}
	}

}
