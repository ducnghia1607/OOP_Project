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

public class dia_danh_2_crawler {
	public static void main(String[] args) {
		String url = "http://ditich.vn/FrontEnd/DiTich?rpage=&cpage=1&tpage=1";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static void crawl(int level, String url, ArrayList<String> visited) {
		Document doc = request(url, visited);
		ArrayList<String> href_visited = new ArrayList<String>();
		ArrayList<Element> all_ele = new ArrayList<Element>();
		JSONArray dia_danh_list = new JSONArray();

		if (doc != null) {
			int number_of_pages = Integer.parseInt(doc.getElementsByClass("pages").text().split(" ")[3].toString().strip());
			System.out.println(number_of_pages);
			for (int i = 1; i < Integer.min(number_of_pages, number_of_pages); i++) {
				doc = request(String.format("http://ditich.vn/FrontEnd/DiTich?rpage=%d&cpage=%d&tpage=%d", 128, i, i), visited);
				if (doc != null) {
					Elements all_a = doc.select("a");
					for (Element cur_a : all_a) {
						if (!href_visited.contains(cur_a.absUrl("href")) && cur_a.attr("href").contains("/FrontEnd/DiTich/Form?do=&ItemId=") ) {
							href_visited.add(cur_a.absUrl("href"));
						}
					}
				}

			}
		}
		System.out.println("Numbers of links: " + href_visited.size());
		for (String cur_link : href_visited) {
			System.out.println("================\n" + href_visited.indexOf(cur_link));

			doc = request(cur_link, visited);
			if (doc != null) {
				JSONObject dia_danh_obj = new JSONObject();
				
				Element info = doc.getElementsByClass("hl__library-info__features").first();
				Elements another_info = doc.getElementsByClass("hl__contact-info").first().getElementsByClass("hl__contact-info__section");
				
				String name = info.select("h2").text();
				String place = another_info.text();
				System.out.println("=================");
				System.out.println(name);
				dia_danh_obj.put("name", name);
				dia_danh_obj.put("place", place);
				
				List<String> s1 = List.of("Loại hình di tích", "Loại hình xếp hạng", "Tọa độ");
				List<String> s2 = List.of("loaiHinhDiTich", "loaiHinhXepHang", "toaDo");
				String desc = new String();

				Elements info_items = info.select("span");
				for (Element i : info_items) {
					String tmp_str = i.text();
					if (!tmp_str.contains(":")) continue;

					String tmp_str1 = tmp_str.split(":")[0];
					String tmp_str2 = tmp_str.split(":")[1];
					for (String str_i : s1) {
						if (str_i.contains(tmp_str1)) {
							dia_danh_obj.put(s2.get(s1.indexOf(str_i)), tmp_str2);
						}
					}
					if (!s1.contains(tmp_str1)) desc = desc + tmp_str + "\n";
				}
				dia_danh_obj.put("desc", desc);

//				System.out.println(desc);

				System.out.println(dia_danh_obj);
				
//				

				
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
				dia_danh_list.add(dia_danh_obj);
				System.out.println(dia_danh_list);
//				break;
			}
			
		}
		try (FileWriter file = new FileWriter("dia_danh_2.json")) {
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