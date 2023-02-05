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

public class le_hoi_crawler {
	public static void main(String[] args) {
		String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static void crawl(int level, String url, ArrayList<String> visited) {
		Document doc = request(url, visited);
		ArrayList<String> href_visited = new ArrayList<String>();
		ArrayList<Element> all_ele = new ArrayList<Element>();
		JSONArray le_hoi_list = new JSONArray();
		List<String> s1 = List.of("celebrateDayInLunarCalendar", "place", "name", "firstOrganized", "relativePersonsName", "desc");
		Elements danh_sach = doc.getElementsByClass("prettytable wikitable").select("tr");
		for (int i = 1; i < danh_sach.size(); i++) {
			JSONObject le_hoi_obj = new JSONObject();

			Elements all_td = danh_sach.get(i).select("td");
			for (int j = 0; j < Integer.min(s1.size(), all_td.size()); j++) {
				le_hoi_obj.put(s1.get(j), all_td.get(j).text());
			}
			System.out.println(le_hoi_obj);
			le_hoi_list.add(le_hoi_obj);
		}
		
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
		try (FileWriter file = new FileWriter("le_hoi.json")) {
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
