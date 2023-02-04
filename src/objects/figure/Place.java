package objects.figure;

public class Place {
	private String[] relativePersonsName;
	private String name;
	private String[] relativeEventsName;
	private String desc;
	public Place() {
		super();
	}
	public Place(String[] relativePersonsName, String name, String[] relativeEventsName, String desc) {
		super();
		this.relativePersonsName = relativePersonsName;
		this.name = name;
		this.relativeEventsName = relativeEventsName;
		this.desc = desc;
	}
	public String[] getRelativePersonsName() {
		return relativePersonsName;
	}
	public void setRelativePersonsName(String[] relativePersonsName) {
		this.relativePersonsName = relativePersonsName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getRelativeEventsName() {
		return relativeEventsName;
	}
	public void setRelativeEventsName(String[] relativeEventsName) {
		this.relativeEventsName = relativeEventsName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
