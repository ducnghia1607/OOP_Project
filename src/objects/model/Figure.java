package objects.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// import javafx.scene.chart.Axis.TickMark;
import objects.ParseJSON;

public class Figure implements ParseJSON {
	private String imgCaption;
    private String[] relativePersonsName;
    private String[] relativePlacesName;
    private String imgFilename;
    private String name;
    private String deathDate;
    private String birthDate;
    private String desc;

    public Figure() {
    }

    public Figure(String imgCaption, String[] relativePersonsName, String[] relativePlacesName, String imgFilename, String name, String deathDate, String birthDate, String desc) {
        this.imgCaption = imgCaption;
        this.relativePersonsName = relativePersonsName;
        this.relativePlacesName = relativePlacesName;
        this.imgFilename = imgFilename;
        this.name = name;
        this.deathDate = deathDate;
        this.birthDate = birthDate;
        this.desc = desc;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object parseObject(JSONObject data) {
        String imgCaption = (String) data.get("imgCaption");
        List<String> list = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) data.get("relativePersonsName") ;
        Iterator<String> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        String[] relativePersonsName = list.toArray(new String[0]);
        list.clear();
        jsonArray = (JSONArray) data.get("relativePlacesName") ;
        iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        String[] relativePlacesName = list.toArray(new String[0]);
        String imgFilename = (String) data.get("imgFilename");
        String name = (String) data.get("name");
        String deathDate = (String) data.get("deathDate");
        String birthDate = (String) data.get("birthDate");
        String desc = (String) data.get("desc");
        Figure newFigure = new Figure(imgCaption, relativePersonsName, relativePlacesName, imgFilename, name, deathDate, birthDate, desc);
        return newFigure;
    }

    public String getImgCaption() {
        return imgCaption;
    }

    public void setImgCaption(String imgCaption) {
        this.imgCaption = imgCaption;
    }

    public String[] getRelativePersonsName() {
        return relativePersonsName;
    }

    public String getRelativePersonsName_String() {
    	String str = "";
    	if (relativePersonsName != null) for (String i : relativePersonsName) str = i + ", " + str;
        return str;
    }
    
    public void setRelativePersonsName(String[] relativePersonsName) {
        this.relativePersonsName = relativePersonsName;
    }

    public String[] getRelativePlacesName() {
        return relativePlacesName;
    }
    
    public String getRelativePlacesName_String() {
    	String str = "";
    	if (relativePersonsName != null) for (String i : relativePlacesName) str = i + ", " + str;
        return str;
    }

    public void setRelativePlacesName(String[] relativePlacesName) {
        this.relativePlacesName = relativePlacesName;
    }

    public String getImgFilename() {
        return imgFilename;
    }

    public void setImgFilename(String imgFilename) {
        this.imgFilename = imgFilename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
