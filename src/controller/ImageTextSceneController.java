package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTextSceneController implements Initializable{

	@FXML
	private Label imgCaption;
	@FXML
	private ImageView image;
	@FXML
	private TextArea textArea;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	public void setImage(String path) {
		image.setImage(new Image(path));
	}
	public void setImageCaption(String caption) {
		imgCaption.setText(caption);
	}
	public void setTextArea(String text) {
		textArea.setText(text);
	}
	@SuppressWarnings("exports")
	public ImageView getImageView() {
		return this.image;
	}


}
