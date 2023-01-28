package base;

import java.util.ArrayList;
import java.util.List;

public class Relic {
	private int id;
	private String name;
	private String content;
	private String address;
	private List<String> nameList = new ArrayList<String>();
	private String imgSource;
	
	public Relic(int id, String name, String content, String address, List<String> nameList, String imgSource) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.address = address;
		this.nameList = nameList;
		this.imgSource = imgSource;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
	public String getImgSource() {
		return imgSource;
	}
	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Relic) {
			Relic t = (Relic) obj;
			return this.getName().equals(t.getName());
		}
		return false;
	}
	
}
