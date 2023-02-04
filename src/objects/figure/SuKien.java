package objects.figure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import objects.ParseJSON;

public class SuKien implements ParseJSON{
	private String result;
    private String imgCaption;
    private String[] relativePersonsName;
    private String imgFilename;
    private String name;
    private String time;
    private String place;
    private String desc;

    public SuKien() {
    }

    public SuKien(String result, String imgCaption, String[] relativePersonsName, String imgFilename, String name, String time, String place, String desc) {
        this.result = result;
        this.imgCaption = imgCaption;
        this.relativePersonsName = relativePersonsName;
        this.imgFilename = imgFilename;
        this.name = name;
        this.time = time;
        this.place = place;
        this.desc = desc;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object parseObject(JSONObject data) {
        String result = (String) data.get("result");
        String imgCaption = (String) data.get("imgCaption");
        List<String> list = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) data.get("relativePersonsName") ;
        Iterator<String> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        String[] relativePersonsName = list.toArray(new String[0]);
        String imgFilename = (String) data.get("imgFilename");
        String name = (String) data.get("name");
        String time = (String) data.get("time");
        String place = (String) data.get("place");
        String desc = (String) data.get("desc");
        SuKien newSuKien = new SuKien(result, imgCaption, relativePersonsName, imgFilename, name, time, place, desc);
        return newSuKien;
    }

    @Override
    public String toString() {
        return "SuKien{" +
                "result='" + result + '\'' +
                ", imgCaption='" + imgCaption + '\'' +
                ", relativePersonsName=" + Arrays.toString(relativePersonsName) +
                ", imgFilename='" + imgFilename + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
