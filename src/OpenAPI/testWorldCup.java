package OpenAPI;


import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import OpenAPI.RegionMoney.WorldCup;

public class testWorldCup {

	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		WorldCup rm = new WorldCup();
		int cnt = WorldCup.getData();
		
		for (int i=0; i<cnt; i++) {
			System.out.println(WorldCup.rNameList[i] +"/"+ WorldCup.addressList[i]);
		}
	}

}
