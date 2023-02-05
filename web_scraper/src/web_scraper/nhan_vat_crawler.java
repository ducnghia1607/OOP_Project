package web_scraper;


import java.io.BufferedInputStream;
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

public class nhan_vat_crawler {
	public static void main(String[] args) {
		String url = "https://nguoikesu.com/nhan-vat/?start=";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static void crawl(int level, String url, ArrayList<String> visited) {
		Document doc = request(url, visited);
		ArrayList<String> href_visited = new ArrayList<String>();
		ArrayList<Element> all_ele = new ArrayList<Element>();
		JSONArray nhan_vat_list = new JSONArray();

		if (doc != null) {
			int number_of_pages = Integer.parseInt(doc.getElementsByClass("com-content-category-blog__counter counter float-end pt-3 pe-2").text().split("/")[1].toString().strip());
			for (int i = 0; i < Integer.min(number_of_pages, number_of_pages); i++) {
				doc = request(url + Integer.toString(i * 5), visited);
				Elements all_a = doc.select("a");
				for (Element cur_a : all_a) {
					if (!href_visited.contains(cur_a.absUrl("href")) && cur_a.attr("href").startsWith("/nhan-vat/") ) {
						href_visited.add(cur_a.absUrl("href"));
					}
				}
			}
		}
		System.out.println(href_visited.size());
		for (String cur_link : href_visited) {
			System.out.println(href_visited.indexOf(cur_link));
			doc = request(cur_link, visited);
			if (doc != null) {
				String name = doc.getElementsByClass("page-header").text().replace("Nhân Vật Lịch Sử", "");
				Elements all_tr = doc.select("tr");

				JSONObject nhan_vat_obj = new JSONObject();
				
				System.out.println("=================");
				System.out.println(name);
				nhan_vat_obj.put("name", name);
				
				String desc = new String();
				Elements all_p = doc.select("p");
				ArrayList<Element> collect_p = new ArrayList<Element>();
				ArrayList<Element> collect_a = new ArrayList<Element>();

				int count_p = 0;
				
				for (Element cur_p : all_p) {
					if (cur_p.text() != "" && cur_p.text().length() > 1 && cur_p.parent().attr("itemprop").equals("articleBody")) {
						desc = desc + cur_p.text();
						if (count_p++ > 3) break;
						collect_a.addAll(cur_p.select("a"));
					

					}
					else if (cur_p.text() == "") break;
				}
//				System.out.println(desc);
				nhan_vat_obj.put("desc", desc.replace("\\u2013", "-"));
				
				all_tr = doc.select("tr");
				List<String> s1 = List.of("Sinh", "Mất");
				List<String> s2 = List.of("birthDate", "deathDate");
				for (Element cur_tr : all_tr) {
						for (int i_index = 0; i_index < s1.size(); i_index++) {
							String tmp_str = cur_tr.text().replace(s1.get(i_index), "").strip();
							if (cur_tr.text().contains(s1.get(i_index)) && tmp_str.length() > 1) nhan_vat_obj.put(s2.get(i_index), tmp_str);
							else nhan_vat_obj.put(s2.get(i_index), "khong ro");
						}
				}
				
//				
				// find reference person 
				ArrayList<String> ref_person = new ArrayList<String>();
				ArrayList<String> ref_place = new ArrayList<String>();
				
				Elements all_a = doc.select("p").select("a");
				for (Element cur_a : all_a) {
					if (cur_a.attr("href").startsWith("/nhan-vat/") && !ref_person.contains(cur_a.text())) {
						ref_person.add(cur_a.text());
						System.out.println(cur_a.text() + "---");
					}
					if (cur_a.attr("href").startsWith("/dia-danh/") && !ref_place.contains(cur_a.text())) {
						ref_place.add(cur_a.text());
						System.out.println(cur_a.text() + "---");
					}
				}
				nhan_vat_obj.put("relativePersonsName", ref_person);
				nhan_vat_obj.put("relativePlacesName", ref_place);

				
				JSONObject su_kien_img = new JSONObject();
				// save image thumbnails
				Elements all_thumbnails = doc.select("img");
				Element cur_thumb = all_thumbnails.first();
				if (cur_thumb != null) {
					String cap = null;
					String img_filename = cur_thumb.attr("data-src").replace("/images/wiki/", "").replace("/", "-");
					String img_url = cur_thumb.parent().selectFirst("img").absUrl("data-src");
					if (!img_filename.contains("history")) {
						try {
							getImages(img_url, img_filename);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						while (!(cur_thumb.text() != "")) {
							cur_thumb = cur_thumb.parent();
						}
						cap = cur_thumb.text();
					}
					else {
						img_filename = "";
						cap = "";
					}
					nhan_vat_obj.put("imgFilename", img_filename);
					
					nhan_vat_obj.put("imgCaption", cap);

				}
			
				System.out.println(nhan_vat_obj);
				nhan_vat_list.add(nhan_vat_obj);
//				break;

			}
			
		}
		try (FileWriter file = new FileWriter("nhan_vat.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(nhan_vat_list.toJSONString()); 
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
//	        URL url = new URL(src.replace("\\", "%5C"));
//	        InputStream in = url.openStream();
//	 
//	        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath.replace("\\", "%5C") + name));
//	 
//	        for (int b; (b = in.read()) != -1;) {
//	            out.write(b);
//	        }
//	        out.close();
//	        in.close();
	        try (BufferedInputStream in = new BufferedInputStream(new URL(src).openStream());
	        		  FileOutputStream fileOutputStream = new FileOutputStream(folderPath + name)) {
	        		    byte dataBuffer[] = new byte[1024];
	        		    int bytesRead;
	        		    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
	        		        fileOutputStream.write(dataBuffer, 0, bytesRead);
	        		    }
	        		} catch (IOException e) {
	        		    // handle exception
	        		}
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