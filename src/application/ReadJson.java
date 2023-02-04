package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.figure.*;

public class ReadJson {
    private static ObservableList<King> kingList = FXCollections.observableArrayList();
//    private ObservableList<Figure> figureList = FXCollections.observableArrayList();
//    private ObservableList<Dynasty> dynastyList = FXCollections.observableArrayList();

    @SuppressWarnings("exports")
	public static ObservableList<King> getKingList() {
    	kingList.clear();
        JSONArray dataList = readData("src/data/vua.json");
        for (int i = 0; i < dataList.size(); i++) {
        	King tmp_king = (King) new King().parseObject((JSONObject) dataList.get(i));
            if (kingList.contains(tmp_king) == false && tmp_king.getTen() != "") kingList.add(tmp_king);
        }
        System.out.println(kingList);
        return kingList;
    }

    @SuppressWarnings("exports")
	public static ObservableList<Place2> getPlace2List() {
        ObservableList<Place2> place2List = FXCollections.observableArrayList();
        JSONArray dataList = readData("src/data/dia_danh_2.json");
        for (int i = 0; i < dataList.size(); i++) {
            place2List.add((Place2) new Place2().parseObject((JSONObject) dataList.get(i)));
        }
        System.out.println(place2List);
        return place2List;
    }
    
    @SuppressWarnings("exports")
	public static ObservableList<TrieuDai> getTrieuDaiList() {
        ObservableList<TrieuDai> trieuDaiList = FXCollections.observableArrayList();
        JSONArray dataList = readData("src/data/trieu_dai.json");
        for (int i = 0; i < dataList.size(); i++) {
            trieuDaiList.add((TrieuDai) new TrieuDai().parseObject((JSONObject) dataList.get(i)));
        }
        System.out.println(trieuDaiList);
        return trieuDaiList;
    }

    @SuppressWarnings("exports")
	public static ObservableList<SuKien> getSuKienList() {
        ObservableList<SuKien> suKienList = FXCollections.observableArrayList();
        JSONArray dataList = readData("src/data/su_kien.json");
        for (int i = 0; i < dataList.size(); i++) {
            suKienList.add((SuKien) new SuKien().parseObject((JSONObject) dataList.get(i)));
        }
        System.out.println(suKienList);
        return suKienList;
    }
    
    @SuppressWarnings("exports")
	public static ObservableList<Figure> getFigureList() {
        ObservableList<Figure> figureList = FXCollections.observableArrayList();
        JSONArray dataList = readData("src/data/nhan_vat.json");
        for (int i = 0; i < dataList.size(); i++) {
            figureList.add((Figure) new Figure().parseObject((JSONObject) dataList.get(i)));
        }
        System.out.println(figureList);
        return figureList;
    }
    @SuppressWarnings("exports")
	public static ObservableList<Festival> getFestivalList() {
        ObservableList<Festival> festivalList = FXCollections.observableArrayList();
        JSONArray dataList = readData("src/data/le_hoi.json");
        for (int i = 0; i < dataList.size(); i++) {
            festivalList.add((Festival) new Festival().parseObject((JSONObject) dataList.get(i)));
        }
        System.out.println(festivalList);
        return festivalList;
    }


    @SuppressWarnings({ "exports" })
    public static JSONArray readData(String path) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONArray dataList = null;
        try (FileReader reader = new FileReader(path)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            dataList = (JSONArray) obj;

            // System.out.println(employeeList);

            // Iterate over employee array
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataList;
        // System.out.println(kingList.get(0).getMieuHieu());
    }

}
