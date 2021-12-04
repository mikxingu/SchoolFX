package gui;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import application.exceptions.ApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class AboutViewController implements Initializable{
	
	
	
	Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	
	@FXML
	private Hyperlink githubHyperlink;
	
	@FXML
	private Label versionLabel;
	
	
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
		
		versionLabel.setText(getApplicationVersion());
	}
	
	private static String getApplicationVersion() {
		Properties props = loadAboutProperties();
		return props.getProperty("app.version");
	}
	
	private static Properties loadAboutProperties() {
		try (FileInputStream fs = new FileInputStream("application.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new ApplicationException (e.getMessage());
		}
		
	}

}
