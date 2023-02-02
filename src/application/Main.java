package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class Main extends Application {
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		initializeScene(primaryStage);
		
	}
	
	void initializeScene(Stage primaryStage) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
			// Group root = new Group();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
