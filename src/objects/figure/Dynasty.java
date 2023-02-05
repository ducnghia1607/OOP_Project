package objects.figure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import objects.ParseJSON;

public class Dynasty implements ParseJSON{
	private String name;
    private String[] relativePersonsName;
    private String[] relativeDynastysName;
    private String[] relativeEventsName;
    private String desc;

    public Dynasty() {
    }

    public Dynasty(String name, String[] relativePersonsName, String[] relativeDynastysName, String[] relativeEventsName, String desc) {
        this.name = name;
        this.relativePersonsName = relativePersonsName;
        this.relativeDynastysName = relativeDynastysName;
        this.relativeEventsName = relativeEventsName;
        this.desc = desc;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object parseObject(JSONObject data) {
    	String name = (String) data.get("name");
        List<String> list = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) data.get("relativePersonsName") ;
        Iterator<String> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        String[] relativePersonsName = list.toArray(new String[0]);
        list.clear();
        jsonArray = (JSONArray) data.get("relativeDynastysName") ;
        iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        String[] relativeDynastysName = list.toArray(new String[0]);
        list.clear();
        jsonArray = (JSONArray) data.get("relativeEventsName") ;
        iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        String[] relativeEventsName = list.toArray(list.toArray(new String[0]));
        String desc = (String) data.get("desc");
        Dynasty newTrieuDai = new Dynasty(name, relativePersonsName, relativeDynastysName, relativeEventsName, desc);
        return newTrieuDai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRelativePersonsName() {
        return relativePersonsName;
    }
    public String getRelativePersonsName_String() {
    	String str = "";
    	if (relativePersonsName != null) for (String i : relativePersonsName) str = i + ", " + str;
        return str;
    }
    
    public String getRelativeDynastysName_String() {
    	String str = "";
    	if (relativeDynastysName != null) for (String i : relativeDynastysName) str = i + ", " + str;
        return str;
    }
    
    public String getRelativeEventsName_String() {
    	String str = "";
    	if (relativeEventsName != null) for (String i : relativeEventsName) str = i + ", " + str;
        return str;
    }

    public void setRelativePersonsName(String[] relativePersonsName) {
        this.relativePersonsName = relativePersonsName;
    }

    public String[] getRelativeDynastysName() {
        return relativeDynastysName;
    }

    public void setRelativeDynastysName(String[] relativeDynastysName) {
        this.relativeDynastysName = relativeDynastysName;
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
