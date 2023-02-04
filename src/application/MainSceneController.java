package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import objects.*;
import objects.figure.*;

import static application.deAccent.*;


@SuppressWarnings("unused")
public class MainSceneController implements Initializable{
	@FXML
	private ListView<String> listView;
//    @FXML
//    private MenuItem menuItem1;
//
//    @FXML
//    private MenuItem menuItem2;
//
//    @FXML
//    private MenuItem menuItem3;

	
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField searchTF;

    @FXML
    private TextField resultField;
    
    @FXML
    private ChoiceBox<String> choiceBox;
    
    @FXML
    private TextArea textArea;
    
    private ObservableList<King> kingList = ReadJson.getKingList();
    private ObservableList<Place2> place2List = ReadJson.getPlace2List();
    private ObservableList<Festival> festivalList = ReadJson.getFestivalList();
    private ObservableList<TrieuDai> trieuDaiList = ReadJson.getTrieuDaiList();
    private ObservableList<SuKien> suKienList = ReadJson.getSuKienList();
    private ObservableList<Figure> figureList = ReadJson.getFigureList();

    private ObservableList<String> nameKingList = FXCollections.observableArrayList();
    private ObservableList<String> namePlace2List = FXCollections.observableArrayList();
    private ObservableList<String> nameFestivalList = FXCollections.observableArrayList();
    private ObservableList<String> nameTrieuDaiList = FXCollections.observableArrayList();
    private ObservableList<String> nameSuKienList = FXCollections.observableArrayList();
    private ObservableList<String> nameFigureList = FXCollections.observableArrayList();
    private ObservableList<String> nameSelectedList = FXCollections.observableArrayList();

    

    Stage stage = new Stage();
    Node old_center = null;
    
    
    private String[] items= {"Vua", "Lễ Hội Văn Hóa", "Triều Đại Lịch Sử", "Di tích lịch sử", "Sự kiện lịch sử", "Nhân Vật Lịch Sử"};

//    @FXML
//    private MenuButton selectionField;
    
    ReadJson reader;
    public void setDataJson(ReadJson reader_param) {
    	reader = reader_param;
    }
    
////	private ObservableList<King> listKing = ReadJson.getKingList();
//	private List<String> nameOfKing = new ArrayList<>();

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	for(King king : kingList){
            nameKingList.add(king.getTen());
        }
        for(Place2 place2 : place2List){
            namePlace2List.add(place2.getName());
        }
        for(TrieuDai trieuDai : trieuDaiList){
            nameTrieuDaiList.add(trieuDai.getName());
        }
        for(SuKien suKien : suKienList){
            nameSuKienList.add(suKien.getName());
        }
        for(Festival festival : festivalList){
            nameFestivalList.add(festival.getName());
        }
        for(Figure figure : figureList){
            nameFigureList.add(figure.getName());
        }
        textArea.setEditable(false);
        choiceBox.getItems().addAll(items);
        choiceBox.setValue(items[0]);
        
//        choiceBox.setOnAction(this::choiceBoxAction);
        choiceBox.setOnAction(this::choiceBoxAction);
        
        listView.getItems().clear();
        listView.getItems().addAll(nameKingList);
//        
        mouseClick();
        searchTF.textProperty().addListener((obs, oldText, newText) -> {
        	search_action();
        });

    	old_center = borderPane.getCenter();
    }

	@SuppressWarnings("exports")
	@FXML public void handleMouseClick(MouseEvent arg0) {
        System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
    }
    
	
	public void choiceBoxAction(@SuppressWarnings("exports") ActionEvent event) {
		mouseClick();
	}
	
    public void mouseClick() {
        String item = choiceBox.getValue();

        if(item.equals(items[0])){
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/textArea.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = null;
            if (root != null) {
                node = root.lookup("#textScene");
            }
            borderPane.setCenter(node);
            TextArea textArea = (TextArea)node.lookup("#textArea2");
            System.out.println("vua");
//            listView.getItems().clear();
//            listView.getItems().addAll(nameKingList);
              listView.setItems(nameKingList);
      		nameSelectedList = listView.getItems();

            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    King selectedKing = new King();
                    String nameKing = listView.getSelectionModel().getSelectedItem();
                    if(nameKing != null) {
                        for (King king : kingList) {
                            if (nameKing.equals(king.getTen())) {
                                selectedKing = king;
                            }
                        }
                        textArea.setText("Tên: " + selectedKing.getTen() +
                                "\nNăm trị vì: " + selectedKing.getNamTriVi() +
                                "\nThe Thu: " + selectedKing.getTheThu() +
                                "\nTên Húy: " + selectedKing.getTenHuy() +
                                "\nNiên Hiệu: " + selectedKing.getNienHieu() +
                                "\nThụy Hiệu: " + selectedKing.getThuyHieu() +
                                "\nMiếu Hiệu: " + selectedKing.getMieuHieu() +
                                "\npaperURL: " + selectedKing.getPaperURL());
                    }
                }
            });
        }
        else if(item.equals(items[1])){
//            listView.getItems().clear();
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/textArea.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = null;
            if (root != null) {
                node = root.lookup("#textScene");
            }
            borderPane.setCenter(node);
            TextArea textArea = (TextArea)node.lookup("#textArea2");
            listView.setItems(nameFestivalList);
      		nameSelectedList = listView.getItems();

            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    Festival selectedFestival = new Festival();
                    String nameFestival = listView.getSelectionModel().getSelectedItem();
                    if(nameFestival != null) {
                        for (Festival festival : festivalList) {
                            if (nameFestival.equals(festival.getName())) {
                                selectedFestival = festival;
                            }
                        }
                        textArea.setText("Tên: " + selectedFestival.getName() +
                                "\nNhân vật liên quan: " + selectedFestival.getRelativePersonsName() +
                                "\nLần đầu tổ chức: " + selectedFestival.getFirstOrganized() +
                                "\nThời gian tổ chức (Âm lịch): " + selectedFestival.getCelebrateDayInLunarCalendar() +
                                "\nĐịa điểm: " + selectedFestival.getPlace() +
                                "\nThông tin: " + selectedFestival.getDesc());
                    }
                }
            });
        }
        else if(item.equals(items[2])){
//            listView.getItems().clear();
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/textArea.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = null;
            if (root != null) {
                node = root.lookup("#textScene");
            }
            borderPane.setCenter(node);
            TextArea textArea = (TextArea)node.lookup("#textArea2");
            listView.setItems(nameTrieuDaiList);
      		nameSelectedList = listView.getItems();

            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    TrieuDai selectedTrieuDai = new TrieuDai();
                    String nameTrieuDai = listView.getSelectionModel().getSelectedItem();
                    if(nameTrieuDai != null) {
                        for (TrieuDai trieuDai : trieuDaiList) {
                            if (nameTrieuDai.equals(trieuDai.getName())) {
                                selectedTrieuDai = trieuDai;
                            }
                        }
                       
                        textArea.setText("Tên: " + selectedTrieuDai.getName() +
                                "\nNhân vật liên quan: " + selectedTrieuDai.getRelativePersonsName_String() +
                                "\nTriều đại liên quan: " + selectedTrieuDai.getRelativeDynastysName_String() +
                                "\nSự kiện liên quan: " + selectedTrieuDai.getRelativeEventsName_String() +
                                "\nThông tin: " + selectedTrieuDai.getDesc());
                    }
                }
            });
        }
        else if(item.equals(items[3])){
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/textArea.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = null;
            if (root != null) {
                node = root.lookup("#textScene");
            }
            borderPane.setCenter(node);
            TextArea textArea = (TextArea)node.lookup("#textArea2");
            System.out.println("địa danh");
//            listView.getItems().clear();
            listView.setItems(namePlace2List);
      		nameSelectedList = listView.getItems();

            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    Place2 selectedPlace2 = new Place2();
                    String namePlace2 = listView.getSelectionModel().getSelectedItem();
                    if(namePlace2 != null) {
                        for (Place2 place2 : place2List) {
                            if (namePlace2.equals(place2.getName())) {
                                selectedPlace2 = place2;
                            }
                        }
                        textArea.setText("Tên: " + selectedPlace2.getName() +
                                "\nTọa độ: " + selectedPlace2.getToaDo() +
                                "\nĐịa điểm: " + selectedPlace2.getPlace() +
                                "\nLoại hình di tích: " + selectedPlace2.getLoaiHinhDiTich() +
                                "\nLoại hình xếp hạng: " + selectedPlace2.getLoaiHinhXepHang() +
                                "\nMô tả: " + selectedPlace2.getDesc());
                    }
                }
            });
        }
        else if(item.equals(items[4])){
//            listView.getItems().clear();
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ImageTextScene.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = null;
            if (root != null) {
                node = root.lookup("#imageTextScene");
            }
          
            borderPane.setCenter(node);
            ImageView image = (ImageView)node.lookup("#image");
            Label captionLabel = (Label)node.lookup("#imgCaption");
            TextArea textAre = (TextArea)node.lookup("#textArea");
            
        	listView.setItems(nameSuKienList);
      		nameSelectedList = listView.getItems();

            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    SuKien selectedSuKien = new SuKien();
                    String nameSuKien = listView.getSelectionModel().getSelectedItem();
                    if(nameSuKien != null) {
                        for (SuKien suKien : suKienList) {
                            if (nameSuKien.equals(suKien.getName())) {
                                selectedSuKien = suKien;
                            }
                        }
                        
                        // ImageShow
                        String imgPath = "file:" + System.getProperty("user.dir") + "\\src\\data\\imgs\\" + selectedSuKien.getImgFilename();
                        String imgCaption = selectedSuKien.getImgCaption();
                        System.out.println(imgPath);
                        if (selectedSuKien.getImgFilename() != "") {
                        	//imageShow(imgPath, imgCaption);
                        	image.setImage(new Image(imgPath));
                        	captionLabel.setText(imgCaption);
                        	textAre.setEditable(false);
                        	textAre.setText("Tên: " + selectedSuKien.getName() +
                                "\nNhân vật liên quan: " + selectedSuKien.getRelativePersonsName_String() +
                                "\nKết quả: " + selectedSuKien.getResult() +
//                                "\nimgFilename: " + selectedSuKien.getImgFilename() +
                                "\nThời gian: " + selectedSuKien.getTime() +
                                "\nĐịa điểm: " + selectedSuKien.getPlace() +
//                                "\nimgCaption: " + selectedSuKien.getImgCaption() +
                                "\nThông tin: " + selectedSuKien.getDesc());
                        }
                       
//                        textArea.setText("Tên: " + selectedSuKien.getName() +
//                                "\nNhân vật liên quan: " + selectedSuKien.getRelativePersonsName_String() +
//                                "\nKết quả: " + selectedSuKien.getResult() +
////                                "\nimgFilename: " + selectedSuKien.getImgFilename() +
//                                "\nThời gian: " + selectedSuKien.getTime() +
//                                "\nĐịa điểm: " + selectedSuKien.getPlace() +
////                                "\nimgCaption: " + selectedSuKien.getImgCaption() +
//                                "\nThông tin: " + selectedSuKien.getDesc());
                    }
                }
            });
        }
        else if(item.equals(items[5])){
//            listView.getItems().clear();
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ImageTextScene.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = null;
            if (root != null) {
                node = root.lookup("#imageTextScene");
            }
          
            borderPane.setCenter(node);
            ImageView image = (ImageView)node.lookup("#image");
            Label captionLabel = (Label)node.lookup("#imgCaption");
            TextArea textAre = (TextArea)node.lookup("#textArea");
            
            listView.setItems(nameFigureList);
      		nameSelectedList = listView.getItems();

            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    Figure selectedFigure = new Figure();
                    String nameFigure = listView.getSelectionModel().getSelectedItem();
                    if(nameFigure != null) {
                        for (Figure figure : figureList) {
                            if (nameFigure.equals(figure.getName())) {
                                selectedFigure = figure;
                            }
                        }
                        
                        // ImageShow
                        String imgPath = "file:" + System.getProperty("user.dir") + "\\src\\data\\imgs\\" + selectedFigure.getImgFilename();
                        String imgCaption = selectedFigure.getImgCaption();
                        System.out.println(selectedFigure.getImgFilename());
                        if (selectedFigure.getImgFilename() != "null") {
                        	//imageShow(imgPath, imgCaption);
                        	image.setImage(new Image(imgPath));
                        	captionLabel.setText(imgCaption);
                        	textAre.setEditable(false);
                        	textAre.setText("Tên: " + selectedFigure.getName() +
                                    "\nNgày sinh: " + selectedFigure.getBirthDate() +
                                    "\nNgày mất: " + selectedFigure.getDeathDate() +
//                                    "\nimgFilename: " + selectedFigure.getImgFilename() +
                                    "\nNhân vật liên quan: " + selectedFigure.getRelativePersonsName_String()+
                                    "\nĐịa điểm liên quan: " + selectedFigure.getRelativePlacesName_String() +
//                                    "\nimgCaption: " + selectedFigure.getImgCaption() +
                                    "\nThông tin: " + selectedFigure.getDesc());
                        }
                        
                        
//                        textArea.setText("Tên: " + selectedFigure.getName() +
//                                "\nNgày sinh: " + selectedFigure.getBirthDate() +
//                                "\nNgày mất: " + selectedFigure.getDeathDate() +
////                                "\nimgFilename: " + selectedFigure.getImgFilename() +
//                                "\nNhân vật liên quan: " + selectedFigure.getRelativePersonsName_String()+
//                                "\nĐịa điểm liên quan: " + selectedFigure.getRelativePlacesName_String() +
////                                "\nimgCaption: " + selectedFigure.getImgCaption() +
//                                "\nThông tin: " + selectedFigure.getDesc());
                    }
                }
            });
        }
    }
    @FXML
    private void searchBtn(ActionEvent event) {
    	search_action();
    }
    
    private void imageShow(String imgPath, String imgCaption) {
    	
//    	Image image = new Image(imgPath, 500, 700, false, false);
//      //Instantiating the Rectangle class 
//        Rectangle rectangle = new Rectangle(); 
//       
//        //Instantiating the ImageInput class 
//        ImageInput imageInput = new ImageInput(); 
//        
//        //Setting the position of the image
//        imageInput.setX(0); 
//        imageInput.setY(0);       
//        
//        //Setting source for image input  
//        imageInput.setSource(image); 
//         
//        //Applying image input effect to the rectangle node 
//        rectangle.setEffect(imageInput);    
//
//        //Creating a scene object 
//        
//    	//Creating a Group object  
//    	Group root = new Group(rectangle);   
//        Scene scene = new Scene(root, 500, 700);  
//                         
//        //Setting title to the Stage 
//        stage.setTitle(imgCaption); 
//           
//        //Adding scene to the stage 
//        stage.setScene(scene); 
//        stage.show();
    	 TextFlow textFlow = new TextFlow();
         textFlow.setPadding(new Insets(10));
         textFlow.setLineSpacing(10);

         VBox container = new VBox();
         container.getChildren().addAll(textFlow);
         VBox.setVgrow(textFlow, Priority.ALWAYS);

   
         ImageView imageView = new ImageView(imgPath);
         imageView.setFitHeight(600);
         imageView.setFitWidth(700);
         imageView.setPreserveRatio(true);

         double aspectRatio = imageView.getFitWidth() / imageView.getFitHeight();
         double realWidth = Math.min(imageView.getFitWidth(), imageView.getFitHeight() * aspectRatio);
         double realHeight = Math.min(imageView.getFitHeight(), imageView.getFitWidth() / aspectRatio);
         
         // Remove :) from text
         Text text = new Text("\n\n" + imgCaption);
         text.setStyle("-fx-font-weight: bold");

         textFlow.getChildren().add(imageView);
         textFlow.getChildren().add(text);
         
         Scene scene = new Scene(container, 600, 700);
         stage.setScene(scene);
         stage.show();
         
    }
    
    private void search_action() {
    	String item = choiceBox.getValue();
    	String searchString = searchTF.getText();
    	
    	textArea.setText(null);
    	boolean found = false;
		ObservableList<String> nameList = FXCollections.observableArrayList();
    	if(searchString.equals("")) {
    		listView.setItems(nameSelectedList);
    	}
    	
    	else {
    		for(String str : nameSelectedList) {
        		if(removeAccent(str.strip()).toLowerCase().startsWith(searchString.strip().toLowerCase()) == true && nameList.contains(str) == false) {
        			nameList.add(str);
            		found = true;
        		}
        	}
    		if (found) {
    			listView.setItems(null);
        		listView.setItems(nameList);
    		}
    		else {
    			listView.setItems(null);
    		}
    		

    	}
    }
    
    
    
    
    
    
    
    
    
    
    
}
