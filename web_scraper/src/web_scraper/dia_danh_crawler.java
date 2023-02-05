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

public class dia_danh_crawler {
	public static void main(String[] args) {
		String url = "https://nguoikesu.com/dia-danh/?start=";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static void crawl(int level, String url, ArrayList<String> visited) {
		Document doc = request(url, visited);
		ArrayList<String> href_visited = new ArrayList<String>();
		ArrayList<Element> all_ele = new ArrayList<Element>();
		JSONArray dia_danh_list = new JSONArray();

		if (doc != null) {
			int number_of_pages = Integer.parseInt(doc.getElementsByClass("com-content-category-blog__counter counter float-end pt-3 pe-2").text().split("/")[1].toString().strip());
			for (int i = 0; i < Integer.min(number_of_pages, number_of_pages); i++) {
				doc = request(url + Integer.toString(i * 5), visited);
				if (doc != null) {
					Elements all_a = doc.select("a");
					for (Element cur_a : all_a) {
						if (!href_visited.contains(cur_a.absUrl("href")) && cur_a.attr("href").startsWith("/dia-danh/") ) {
							href_visited.add(cur_a.absUrl("href"));
						}
					}
				}

			}
		}
		for (String cur_link : href_visited) {
			doc = request(cur_link, visited);
			if (doc != null) {
				String name = doc.getElementsByClass("page-header").text().replace("Địa Danh", "").strip();
				Elements all_tr = doc.select("tr");

				JSONObject dia_danh_obj = new JSONObject();
				
				System.out.println("=================");
				System.out.println(name);
				dia_danh_obj.put("name", name);
				
				String desc = new String();
				Elements all_p = doc.select("p");
				ArrayList<Element> collect_p = new ArrayList<Element>();
				ArrayList<Element> collect_a = new ArrayList<Element>();

				int count_p = 0;
				
				for (Element cur_p : all_p) {
					if (cur_p.text().length() > 1 && cur_p.parent().attr("itemprop").equals("articleBody")) {
						desc = desc + cur_p.text();
						if (count_p++ > 3) break;
						collect_a.addAll(cur_p.select("a"));

					}
					else if (cur_p.text() == "") break;
				}
				System.out.println(desc);
				dia_danh_obj.put("desc", desc.replace("\\u2013", "-"));
				
				
//				
				// find reference person 
				ArrayList<String> ref_person = new ArrayList<String>();
				ArrayList<String> ref_event = new ArrayList<String>();
				
				Elements all_a = doc.select("p").select("a");
				for (Element cur_a : all_a) {
					if (cur_a.attr("href").startsWith("/nhan-vat/") && !ref_person.contains(cur_a.text())) {
						ref_person.add(cur_a.text());
						System.out.println(cur_a.text() + "---");
					}
					if (cur_a.attr("href").startsWith("/su-kien/") && !ref_event.contains(cur_a.text())) {
						ref_event.add(cur_a.text());
						System.out.println(cur_a.text() + "---");
					}
				}
				dia_danh_obj.put("relativePersonsName", ref_person);
				dia_danh_obj.put("relativeEventsName", ref_event);

				
//				JSONObject su_kien_img = new JSONObject();
//				// save image thumbnails
//				Elements all_thumbnails = doc.select("img");
//				Element cur_thumb = all_thumbnails.first();
//				if (cur_thumb != null) {
//					String cap = null;
//					String img_filename = cur_thumb.attr("data-src").replace("/images/wiki/", "").replace("/", "");
//					String img_url = cur_thumb.parent().selectFirst("img").absUrl("data-src");
//					dia_danh_obj.put("imgFilename", img_filename);
//					try {
//						getImages(img_url, img_filename);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					while (!(cur_thumb.text() != "")) {
//						cur_thumb = cur_thumb.parent();
//					}
//					cap = cur_thumb.text();
//					
//					dia_danh_obj.put("imgCaption", cap);
//			
				System.out.println(dia_danh_obj);
				dia_danh_list.add(dia_danh_obj);
			}
			
		}
		try (FileWriter file = new FileWriter("dia_danh.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(dia_danh_list.toJSONString()); 
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