package application;

import java.io.IOException;

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
	public void start(Stage primaryStage) throws IOException{
//		try {
////			Parent root = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
////			MainController controller = FXMLLoader(getClass().getResource("/MainScene.fxml")).<MainController>getController();
//			// Group root = new Group();
//			
//			ReadJson reader = new ReadJson();
//			
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));     
//			
//			Parent root = (Parent)fxmlLoader.load();          
//			MainSceneController controller = fxmlLoader.<MainSceneController>getController();
//			
//			Scene scene = new Scene(root, 800, 600);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			controller.setDataJson(reader);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Lịch sử Việt Nam");

        primaryStage.setScene(scene);

        primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

//	@SuppressWarnings("exports")
//	@Override
//	public void start(Stage arg0) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
}
