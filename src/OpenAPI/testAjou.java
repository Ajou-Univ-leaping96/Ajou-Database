package OpenAPI;


import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import OpenAPI.RegionMoney.Ajou;

public class testAjou {

	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		Ajou aj = new Ajou();
		int cnt = aj.getData();
		
		for (int i=0; i<cnt; i++) {
			System.out.println(Ajou.rNameList[i] +"/"+ Ajou.addressList[i]);
		}
	}

}
