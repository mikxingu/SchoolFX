package gui;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

public class AboutViewController implements Initializable{
	
	
	
	Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	
	@FXML
	private Hyperlink githubHyperlink;
	
	
	
	
	public void onGithubHyperlinkAction() {
		try {
			desktop.browse(URI.create("localhost:8080"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}




	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
		loader.setController(new MainViewController());
		
	}

}
