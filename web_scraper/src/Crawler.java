
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Crawler {
	public static void main(String[] args) {
		String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
		crawl(1, url, new ArrayList<String>());
	}
	
	@SuppressWarnings("unchecked")
	private static void crawl(int level, String url, ArrayList<String> visited) {
		JSONArray di_tich_list = new JSONArray();
		if (level <= 5) {
			Document doc = request(url, visited);

			if (doc != null) {
				for (Element each_element : doc.select("tr")) {
					ArrayList<String> ar = new ArrayList<String>();
					if ((each_element.select("td").size() < 4)) continue;
					Elements elements = each_element.getElementsByTag("td");
					Element first_td = elements.first();
					if (first_td != null) {
						String href_link = (first_td.select("a")).attr("href");
						Element next_element = first_td.nextElementSibling();
						String dia_diem = next_element.text();
						
						
						Element tmp_element = next_element.nextElementSibling();
						next_element = tmp_element;
						String loai_di_tich = next_element.text();
						
						
						tmp_element = next_element.nextElementSibling();
						next_element = tmp_element;
						String nam_cn = next_element.text();
						
						if (href_link != "" && href_link.startsWith("/wiki")) {
							String next_link = ((Element) first_td.selectFirst("a[href]")).absUrl("href");
							Document doc_2 = request(next_link, visited);
							Elements a = doc_2.select("a");
							for (Element iter : a) {
								String title = iter.attr("title").toLowerCase();
								String text = iter.text().toLowerCase();
//								System.out.println(title);
//								System.out.println(text);
								
								if (text == "sơ khai") {
									System.out.println("So khai");
									break;
								}
								if ((iter.attr("href")).startsWith("/wiki/") && title.contains(text) && title != "" && text != "" && text.length() >= 2) {
									ar.add(text);
								}
							}
//							
							
							String title = first_td.text();
							System.out.println("=============\n");
							System.out.println("Tên: " + title);
							System.out.println("Địa điểm: " + dia_diem);
							System.out.println("Loại di tích: " + loai_di_tich);
							System.out.println("Năm CN: " + nam_cn);
							System.out.println("Reference: " + ar.subList(6, ar.size() - 3));
							
							// write to json file
							JSONObject di_tich_obj = new JSONObject();
							JSONObject info_obj = new JSONObject();
							info_obj.put("Tên", title);
							info_obj.put("Dia diem", dia_diem);
							info_obj.put("Nam CN", nam_cn);
							String json = new Gson().toJson(ar.subList(6, ar.size() - 3));
							di_tich_obj.put("Information", info_obj);
							di_tich_obj.put("Reference", json);
//							System.out.println(di_tich_obj);
							di_tich_list.add(di_tich_obj);
						}
					}
					
//					each_element = each_element.selectFirst("a[href]");
//					String next_link = each_element.absUrl("href");
//					if (visited.contains(next_link) == false) {
//						crawl(level++, next_link, visited);
//					}

				}
			}
		}
		// write to json file
		try (FileWriter file = new FileWriter("di_tich.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(di_tich_list.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
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
