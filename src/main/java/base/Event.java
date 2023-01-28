package base;

import java.util.ArrayList;
import java.util.List;

public class Event {
	private String name;
	private String time;
	private String place;
	private String description;
	private List<String> relativePersonsName = new ArrayList<String>();
	private String imgPath;
	private int id;
	
	public Event(String name, String time, String place, String description, List<String> relativePersonsName,
			String imgPath, int id) {
		this.name = name;
		this.time = time;
		this.place = place;
		this.description = description;
		this.relativePersonsName = relativePersonsName;
		this.imgPath = imgPath;
		this.id = id;
	}
	public Event() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getRelativePersonsName() {
		return relativePersonsName;
	}

	public void setRelativePersonsName(List<String> relativePersonsName) {
		this.relativePersonsName = relativePersonsName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public boolean equals(Object obj) {
		if(obj instanceof Event) {
			Event p = (Event) obj;
			return this.getName().equalsIgnoreCase(p.getName());
		}
		return false;
	}

	

	
}
