package OpenAPI.RegionMoney;

//외부모듈
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WorldCup {
	
	public static String[] rNameList = new String[1000];
	public static String[] addressList = new String[1000];

	public static int getData() throws ParserConfigurationException, SAXException {
		// write your code here
		int cnt=0;
		BufferedReader br = null;
		try {
			String urlstr = "https://openapi.gg.go.kr/RegionMnyFacltStus?KEY=a36ae7088d4f4dfb971acd9718920414&pIndex=1&pSize=1000&SIGUN_CD=41110"
					+ "&REFINE_ROADNM_ADDR=%EA%B2%BD%EA%B8%B0%EB%8F%84%20%EC%88%98%EC%9B%90%EC%8B%9C%20%ED%8C%94%EB%8B%AC%EA%B5%AC%20%EC%9B%94%EB%93%9C%EC%BB%B5%EB%A1%9C";

			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(urlstr);
			

			// root tag
			doc.getDocumentElement().normalize();
			//System.out.println("API��: " + doc.getDocumentElement().getNodeName()); // Root element: result

			// 파싱할 태그(식당1개)
			NodeList nList = doc.getElementsByTagName("row");
			//System.out.println("식당갯수 : "+ nList.getLength());
			cnt = nList.getLength();

			// 태그내의 세부태그들 출력(식당1개에 대한 상세정보)
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					//System.out.println("######################");
					//System.out.println("식당명 : " + getTagValue("CMPNM_NM", eElement));
					//System.out.println("주소: " + getTagValue("REFINE_LOTNO_ADDR", eElement));
					rNameList[temp] = getTagValue("CMPNM_NM", eElement);
					addressList[temp] = getTagValue("REFINE_ROADNM_ADDR", eElement);
				} // for end
			} // if end
			
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		
		return nValue.getNodeValue();
	}
}
