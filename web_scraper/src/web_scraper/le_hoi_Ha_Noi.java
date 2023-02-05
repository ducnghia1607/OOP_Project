package web_scraper;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class le_hoi_Ha_Noi {
	public static void main(String[] args) {
		String url = "https://evbn.org/danh-sach-cac-le-hoi-o-ha-noi-1650760290/";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static void crawl(int level, String url, ArrayList<String> visited) {
		Document doc = request(url, visited);
		ArrayList<String> href_visited = new ArrayList<String>();
		ArrayList<Element> all_ele = new ArrayList<Element>();
		JSONArray le_hoi_list = new JSONArray();
		List<String> s1 = List.of("celebrateDayInLunarCalendar", "place", "name", "firstOrganized", "relativePersonsName", "desc");
		Elements danh_sach = doc.select("p + p, div + p, h2 + p"); 
		System.out.println(danh_sach);

//		Elements ten = doc.select("ul ~ h3");
//		Elements thong_tin = doc.select("h3 + ul");
//		Elements desc = doc.select("h3 + ul + p");
//		
//		for (int i = 0; i < ten.size(); i++) {
//			JSONObject le_hoi_obj = new JSONObject();
//			le_hoi_obj.put("name", ten.get(i).text().replaceAll("[0-9|.]*", "").strip());
//			le_hoi_obj.put("time", thong_tin.get(i).select("li").get(0).text());
//			le_hoi_obj.put("place", thong_tin.get(i).select("li").get(1).text());
//			le_hoi_obj.put("desc", desc.get(i).text());
//			System.out.println(le_hoi_obj);
//			le_hoi_list.add(le_hoi_obj);
//		}
		System.out.println(le_hoi_list);

//		System.out.println(ten);
		for (int i = 1; i < danh_sach.size(); i++) {
			String tmp = danh_sach.get(i).toString().replaceAll("<br>", "\n").replaceAll("<br class=\"autobr\">", "").strip();
			if (tmp.split("\n").length == 5) {
				JSONObject le_hoi_obj = new JSONObject();
				le_hoi_obj.put("name", danh_sach.get(i).select("strong").text());
				le_hoi_obj.put("time", tmp.split("\n")[1].split(":")[1].strip());
				le_hoi_obj.put("place", tmp.split("\n")[2].split(":")[1].strip());
				le_hoi_obj.put("desc", tmp.split("\n")[4].strip());
				System.out.println(le_hoi_obj);
				le_hoi_list.add(le_hoi_obj);

			}
			
		}
//		
//				
//		for (String cur_link : href_visited) {
//			doc = request(cur_link, visited);
//			if (doc != null) {
//				String name = doc.selectFirst("h1").text().strip();
//				Elements all_tr = doc.select("tr");
//
//				JSONObject trieu_dai_obj = new JSONObject();
//				
//				System.out.println("=================");
//				System.out.println(name);
//				trieu_dai_obj.put("name", name);
//				
//				String desc = new String();
//				Elements all_p = doc.select("p");
//				ArrayList<Element> collect_p = new ArrayList<Element>();
//				ArrayList<Element> collect_a = new ArrayList<Element>();
//
//				int count_p = 0;
//				
//				for (Element cur_p : all_p) {
//					if (cur_p.text().length() > 1 && cur_p.parent().attr("class").equals("category-desc clearfix")) {
//						desc = desc + cur_p.text();
//						if (count_p++ > 3) break;
//						collect_a.addAll(cur_p.select("a"));
//
//					}
//					else if (cur_p.text() == "") break;
//				}
//				System.out.println(desc);
//				trieu_dai_obj.put("desc", desc.replace("\\u2013", "-"));
//				
//				
////				
//				//find reference dynasty
//				ArrayList<String> ref_dynasty = new ArrayList<String>();
//				Elements all_dynasty = doc.getElementsByClass("page-header");
//				if (all_dynasty != null) {
//					for (Element cur_dynasty : all_dynasty) {
//						if (cur_dynasty.selectFirst("a") != null) ref_dynasty.add(cur_dynasty.selectFirst("a").text());
//					}
//				}
//				
//				
//				// find reference person 
//				ArrayList<String> ref_person = new ArrayList<String>();
//				ArrayList<String> ref_event = new ArrayList<String>();
//				
//				Elements all_a = doc.select("p").select("a");
//				for (Element cur_a : all_a) {
//					if (cur_a.text().equals("Xem thêm")) continue;
//					if (cur_a.attr("href").startsWith("/nhan-vat/") && !ref_person.contains(cur_a.text())) {
//						ref_person.add(cur_a.text());
//						System.out.println(cur_a.text() + "---");
//					}
//					if (cur_a.attr("href").startsWith("/su-kien/") && !ref_event.contains(cur_a.text())) {
//						ref_event.add(cur_a.text());
//						System.out.println(cur_a.text() + "---");
//					}
//				}
//				trieu_dai_obj.put("relativePersonsName", ref_person);
//				trieu_dai_obj.put("relativeEventsName", ref_event);
//				trieu_dai_obj.put("relativeDynastysName", ref_dynasty);
//
//				System.out.println(trieu_dai_obj);

			
//			}
//			
//		}
		try (FileWriter file = new FileWriter("le_hoi_ha_noi.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(le_hoi_list.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
	  @SuppressWarnings("unused")
	private static void getImages(String src, String name) throws IOException {
		  
	        String folder = null;
	        String folderPath = new File(".").getAbsolutePath() + "\\imgs\\";
	        //Extract the name of the image from the src attribute
//	        int indexname = src.lastIndexOf("/");
//	 
//	        if (indexname == src.length()) {
//	            src = src.substring(1, indexname);
//	        }
//	 
//	        indexname = src.lastIndexOf("/");
//	        String name = src.substring(indexname, src.length());
//	 
//	        System.out.println(name);
	 
	        //Open a URL Stream
	        URL url = new URL(src);
	        InputStream in = url.openStream();
	 
	        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + name));
	 
	        for (int b; (b = in.read()) != -1;) {
	            out.write(b);
	        }
	        out.close();
	        in.close();
	 
	    }

	private static Document request(String url, ArrayList<String> v) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			if (con.response().statusCode() == 200) {
//				System.out.println("Link: " + url);
//				System.out.println(doc.title());
				v.add(url);
				
				return doc;
			}
			return null;
		}
		catch(IOException e) {
			return null;
		}
	}
}