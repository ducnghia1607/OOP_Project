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

public class su_kien_crawler {
	public static void main(String[] args) {
		String url = "https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=&start=";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static void crawl(int level, String url, ArrayList<String> visited) {
		Document doc = request(url, visited);
		ArrayList<String> href_visited = new ArrayList<String>();
		ArrayList<Element> all_ele = new ArrayList<Element>();
		JSONArray su_kien_list = new JSONArray();

		if (doc != null) {
			int number_of_pages = Integer.parseInt(doc.getElementsByClass("com-content-category-blog__counter counter float-end pt-3 pe-2").text().split("/")[1].toString().strip());
			for (int i = 0; i < number_of_pages; i++) {
				doc = request(url + Integer.toString(i * 5), visited);
				Elements all_a = doc.select("a");
				for (Element cur_a : all_a) {
					if (!href_visited.contains(cur_a.absUrl("href")) && cur_a.attr("href").startsWith("/tu-lieu/quan-su/") ) {
						href_visited.add(cur_a.absUrl("href"));
					}
				}
			}
		}
		for (String cur_link : href_visited) {
			doc = request(cur_link, visited);
			if (doc != null) {
				String name = doc.getElementsByClass("page-header").text();
				Elements all_tr = doc.select("tr");

				JSONObject su_kien_obj = new JSONObject();
				
				System.out.println("=================");
				System.out.println(name);
				su_kien_obj.put("name", name);
				
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
				su_kien_obj.put("desc", desc.replace("\\u2013", "-"));
				
				List<String> s1 = List.of("Thời gian", "Địa điểm", "Kết quả");
				List<String> s2 = List.of("time", "place", "result");
				for (Element cur_tr : all_tr) {
					if (cur_tr.select("td").size() == 2) {
						String td_0 = cur_tr.select("td").get(0).text();
						String td_1 = cur_tr.select("td").get(1).text();
						for (int i_index = 0; i_index < s1.size(); i_index++) {
							if (td_0.equals(s1.get(i_index))) su_kien_obj.put(s2.get(i_index), td_1.replace("\\u2013", "-"));
						}
					}
				}
				
				// find reference person 
				ArrayList<String> ref_person = new ArrayList<String>();
//				for (Element cur_a : collect_a) {
//					if (!ref_person.contains(cur_a.text()) && cur_a.attr("href").startsWith("/nhan-vat/") ) {
//						ref_person.add(cur_a.text());
//					}
//				}
				
				Elements all_a = doc.select("table").select("tr").select("td").select("a");
				for (Element cur_a : all_a) {
					if (cur_a.attr("href").startsWith("/nhan-vat/") && !ref_person.contains(cur_a.text())) {
						ref_person.add(cur_a.text());
						System.out.println(cur_a.text() + "---");
					}
				}
				su_kien_obj.put("relativePersonsName", ref_person);

				
				JSONObject su_kien_img = new JSONObject();
				// save image thumbnails
				Elements all_thumbnails = doc.select("img");
				Element cur_thumb = all_thumbnails.first();
				if (cur_thumb != null) {
					String cap = "không rõ";
					String img_filename = cur_thumb.attr("data-src").replace("/images/wiki/", "").replace("/", "");
					String img_url = cur_thumb.parent().selectFirst("img").absUrl("data-src");
					if (!img_filename.contains("history")) {
						su_kien_obj.put("imgFilename", img_filename);
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
				
					
					su_kien_obj.put("imgCaption", cap);
//					JSONObject tmp_img = new JSONObject();
//					su_kien_obj.put("imgCaption", cur_thumb.select("img[class=jch-lazyload]").attr("alt"));
//					String img_filename = cur_thumb.select("img[class=jch-lazyload]").attr("data-src").replace("/images/wiki/", "").replace("/", "");
//					String img_url = cur_thumb.selectFirst("img[class=jch-lazyload]").absUrl("data-src");
//					su_kien_obj.put("imgFilename", img_filename);
//					try {
//						getImages(img_url, img_filename);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(cur_thumb.parent().parent().attr("class"));
					
				}
				
//				System.out.println(all_thumbnails);
//				break;
				System.out.println(su_kien_obj);
				su_kien_list.add(su_kien_obj);
			}
			
		}
		try (FileWriter file = new FileWriter("su_kien.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(su_kien_list.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
	@SuppressWarnings("unused")
	private static void getImages(String src, String name) throws IOException {
		  
        String folder = null;
        String folderPath = new File(".").getAbsolutePath() + "\\imgs\\";
       
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
