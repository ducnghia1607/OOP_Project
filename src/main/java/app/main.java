package app;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class main {
	
	public void init() {
		
	}
	
	 public static <T> ObservableList<T> readFromFile(String fileName, Class<T[]> clazz) {
		    FileReader reader;
				try {
					reader = new FileReader("C:\\Users\\ACER\\Desktop\\Project\\History\\src\\main\\java\\json\\" + fileName);
					GsonBuilder gsonBuilder = new GsonBuilder();
		      gsonBuilder.registerTypeAdapter(ObservableList.class,new ObservableListDeserializer<T>());
		      Gson gson = gsonBuilder.create();
		      T[] arr = gson.fromJson(reader, clazz);
		      return FXCollections.observableArrayList(arr);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
		  }
	 
	public static void main(String[] args) {
		ObservableList<Event> events = FXCollections.observableArrayList();
		events = readFromFile("event.json",Event[].class);
		System.out.println(events.get(0).getName());
	}
}

