package base;

import java.util.List;

public class Dynasty {
	private int id;
	private String name;
	private String capital;
	private String time;
	private List<String> kingNameList;
	
	public Dynasty(int id, String name, String capital, String time, List<String> kingNameList) {
		this.id = id;
		this.name = name;
		this.capital = capital;
		this.time = time;
		this.kingNameList = kingNameList;
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
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<String> getKingNameList() {
		return kingNameList;
	}
	public void setKingNameList(List<String> kingNameList) {
		this.kingNameList = kingNameList;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Dynasty) {
			Dynasty p = (Dynasty) obj;
			return this.getName().equalsIgnoreCase(p.getName());
		}
		return false;
	}
	
}
