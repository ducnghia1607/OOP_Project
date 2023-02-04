package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class textAreaController implements Initializable{

	@FXML
	private TextArea textArea2;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

	public void setTextArea(String text) {
		textArea2.setText(text);
	}



}
