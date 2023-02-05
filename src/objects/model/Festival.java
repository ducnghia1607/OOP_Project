package objects.model;

import org.json.simple.JSONObject;

import objects.ParseJSON;

public class Festival implements ParseJSON{
	private String relativePersonsName;
    private String firstOrganized;
    private String name;
    private String celebrateDayInLunarCalendar;
    private String place;
    private String desc;

    public Festival() {
    }

    public Festival(String relativePersonsName, String firstOrganized, String name, String celebrateDayInLunarCalendar, String place, String desc) {
        this.relativePersonsName = relativePersonsName;
        this.firstOrganized = firstOrganized;
        this.name = name;
        this.celebrateDayInLunarCalendar = celebrateDayInLunarCalendar;
        this.place = place;
        this.desc = desc;
    }

    @Override
    public Object parseObject(JSONObject data) {
        String relativePersonsName = (String) data.get("relativePersonsName");
        String firstOrganized = (String) data.get("firstOrganized");
        String name = (String) data.get("name");
        String celebrateDayInLunarCalendar = (String) data.get("celebrateDayInLunarCalendar");
        String place = (String) data.get("place");
        String desc = (String) data.get("desc");
        Festival newFestival = new Festival(relativePersonsName, firstOrganized, name, celebrateDayInLunarCalendar, place, desc );
        return newFestival;
    }

    public String getRelativePersonsName() {
        return relativePersonsName;
    }

    public void setRelativePersonsName(String relativePersonsName) {
        this.relativePersonsName = relativePersonsName;
    }

    public String getFirstOrganized() {
        return firstOrganized;
    }

    public void setFirstOrganized(String firstOrganized) {
        this.firstOrganized = firstOrganized;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCelebrateDayInLunarCalendar() {
        return celebrateDayInLunarCalendar;
    }

    public void setCelebrateDayInLunarCalendar(String celebrateDayInLunarCalendar) {
        this.celebrateDayInLunarCalendar = celebrateDayInLunarCalendar;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Festival{" +
                "relativePersonsName='" + relativePersonsName + '\'' +
                ", firstOrganized='" + firstOrganized + '\'' +
                ", name='" + name + '\'' +
                ", celebrateDayInLunarCalendar='" + celebrateDayInLunarCalendar + '\'' +
                ", place='" + place + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

}
