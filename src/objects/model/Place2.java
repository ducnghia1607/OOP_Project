package objects.model;

import org.json.simple.JSONObject;

import objects.ParseJSON;

public class Place2 implements ParseJSON{
	private String loaiHinhXepHang;
	private String toaDo;
	private String name;
	private String place;
	private String loaiHinhDiTich;
	private String desc;
	public Place2() {
		super();
	}
	public Place2(String loaiHinhXepHang, String toaDo, String name, String place, String loaiHinhDiTich, String desc) {
		super();
		this.loaiHinhXepHang = loaiHinhXepHang;
		this.toaDo = toaDo;
		this.name = name;
		this.place = place;
		this.loaiHinhDiTich = loaiHinhDiTich;
		this.desc = desc;
	}
	
	@Override
    public Object parseObject(JSONObject data) {
        String loaiHinhXepHang = (String) data.get("loaiHinhXepHang");
        String toaDo = (String) data.get("toaDo");
        String name = (String) data.get("name");
        String place = (String) data.get("place");
        String loaiHinhDiTich = (String) data.get("loaiHinhDiTich");
        String desc = (String) data.get("desc");
        Place2 newPlace2 = new Place2(loaiHinhXepHang, toaDo, name, place, loaiHinhDiTich, desc);
        return newPlace2;
    }
	public String getLoaiHinhXepHang() {
		return loaiHinhXepHang;
	}
	public void setLoaiHinhXepHang(String loaiHinhXepHang) {
		this.loaiHinhXepHang = loaiHinhXepHang;
	}
	public String getToaDo() {
		return toaDo;
	}
	public void setToaDo(String toaDo) {
		this.toaDo = toaDo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getLoaiHinhDiTich() {
		return loaiHinhDiTich;
	}
	public void setLoaiHinhDiTich(String loaiHinhDiTich) {
		this.loaiHinhDiTich = loaiHinhDiTich;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
