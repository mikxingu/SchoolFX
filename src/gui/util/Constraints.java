package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	
	public static void setTextFieldInteger(TextField text) {
		text.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d")) {
				text.setText(oldValue);
			}
		});
	}
	
	public static void setTextFieldMaxLength(TextField text, int maxLength) {
		text.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > maxLength) {
				text.setText(oldValue);
			}
		});
	}
	
	public static void setTextFieldDouble (TextField text) {
		text.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				text.setText(oldValue);
			}
		});
	}

}
