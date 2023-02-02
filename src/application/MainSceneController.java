package application;

import java.util.function.Predicate;

import application.popup.PopUpWinDow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


@SuppressWarnings("unused")
public class MainSceneController{

    @FXML
    private MenuItem menuItem1;

    @FXML
    private MenuItem menuItem2;

    @FXML
    private MenuItem menuItem3;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField searchField;

    @FXML
    private TextField resultField;
    
    @FXML
    private MenuButton selectionField;
    
    @FXML
    void clickMenuItem(ActionEvent event) {
    	MenuItem menuItem = (MenuItem) event.getSource();
        String lableSelectItem = menuItem.getText();
        PopUpWinDow newPopUp = new PopUpWinDow();
        selectionField.setText(lableSelectItem);
        switch (lableSelectItem) {
        	case "Nhân Vật Lịch Sử":
        		searchField.setPromptText("Tên nhân vật");
                break;

        }
    }
    @FXML
    void pressEnter(ActionEvent event) {
    	
    }
    @FXML
    void search(ActionEvent event) {
    	Stage MainWindow = (Stage) searchField.getScene().getWindow();
    	String search_information = searchField.getText();
    	resultField.setText(search_information);
    }
}
