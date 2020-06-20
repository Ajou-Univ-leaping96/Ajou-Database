package Excute;
//외부모듈
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

//내무모듈
import Excel.Restaurant;
import OpenAPI.RegionMoney.Ajou;
import OpenAPI.RegionMoney.WorldCup;
import Excel.Menu;



public class UpdateDB {
	
	public static void main(String[] args) throws SQLException, ParserConfigurationException, SAXException
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("SQL Programming Test");
		
		System.out.println("Connecting PostgreSQL database");
		// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
		Connection conn = null;
		Statement  st   = null;
		ResultSet  rs   = null;
		
		
		// connection 속성 설정
		String     url      = "jdbc:postgresql://localhost/DooBoo";
		String     user     = "postgres";
		String     password = "123123";
		String    query    = "select version()";
		 
		System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
		
		try 
		{
			// postgresql 드라이버 클래스를 JDBC드라이버 메니저에 로드
			Class.forName("org.postgresql.Driver");
			  
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("Where is your PostgreSQL JDBC Driver?" + "Include in your library path!");
			e.printStackTrace();
			return;
			  
		}
		
		try 
		{
			// conncetion establish 하기
			conn = DriverManager.getConnection(url, user, password);
			  
		}
		catch (SQLException e) 
		{
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return; 
		}
		
		if (conn != null)
		{
			System.out.println("Connection established!");
		} 
		else 
		{
			System.out.println("Failed to make conn!");
		}
		
		System.out.println("PostgreSQL JDBC Driver Registered!");
		
		
		st = conn.createStatement();
		
		//0.초기화
		//기존에 테이블이 있으면 모두 삭제
		DatabaseMetaData metadata = conn.getMetaData(); 
		rs = metadata.getTables(null, null, "restaurant", null);
		if(rs.next()) {
			st.executeUpdate("drop table restaurant");
		}
		rs = metadata.getTables(null, null, "regionmoney", null);
		if(rs.next()) {
			st.executeUpdate("drop table regionmoney");
		}
		rs = metadata.getTables(null, null, "menu", null);
		if(rs.next()) {
			st.executeUpdate("drop table menu");
		rs = null;
		}
		
		
		
		System.out.println("=============================");
		System.out.println("1. 테이블생성 => 식당, 지역화폐, 메뉴\n");
		// 3개 테이블 생성: Create table문 이용
		// [예시] st.executeUpdate("create table Restaurant(rName varchar, tel integer, address varchar, category varchar)");
		st.executeUpdate("create table Restaurant(rName varchar, tel integer, address varchar, category varchar)"); //식당
		st.executeUpdate("create table RegionMoney(rName varchar, address varchar)"); //지역화폐
		st.executeUpdate("create table Menu(rName varchar, tel integer, meal varchar, price integer, code integer)"); //메뉴
		
		System.out.println("2. 테이블에 값 입력\n");
		// 3개 테이블에 튜플 생성: Insert문 이용
		// [예시] st.executeUpdate("insert into Restaurant values ('피치플레이', 9998888 , '양식', '수원시')");
		
		
		//[A]엑셀에서 식당시트 읽어들여서  Insert
		Restaurant r = new Restaurant();
		int rcnt = Restaurant.createArray();
		for(int i=1; i<=rcnt; i++) {
			String rName = r.rNameList[i]; int tel = r.telList[i]; String address = r.addressList[i];  String category = r.categoryList[i];
			String queryInsert = "insert into Restaurant values ( ? , ? , ? , ?)";
			PreparedStatement ps = conn.prepareStatement(queryInsert);
			ps.setString(1,rName);
			ps.setInt(2, tel);
			ps.setString(3, address);
			ps.setString(4, category);
			ps.executeUpdate();
		}
		
		
		//[B]OpenAPI에서  팔달구 월드컵로&아주로 읽어들여서 Insert
		
		//[B-1] 월드컵로 Insert
		WorldCup wc = new WorldCup();
		int wccnt = wc.getData();
		for (int i=0; i<wccnt; i++) {
			String rName = WorldCup.rNameList[i]; String address = WorldCup.addressList[i];
			String queryInsert = "insert into RegionMoney values ( ? , ? )";
			PreparedStatement ps = conn.prepareStatement(queryInsert);
			ps.setString(1,rName);
			ps.setString(2, address);
			ps.executeUpdate();
		}
		
		//[B-2] 아주로 Insert
		Ajou aj = new Ajou();
		int ajcnt = aj.getData();
		for (int i=0; i<ajcnt; i++) {
			String rName = Ajou.rNameList[i]; String address = Ajou.addressList[i];
			String queryInsert = "insert into RegionMoney values ( ? , ? )";
			PreparedStatement ps = conn.prepareStatement(queryInsert);
			ps.setString(1,rName);
			ps.setString(2, address);
			ps.executeUpdate();
		}
		
		
		
		//[C]엑셀에서 메뉴시트 읽어들여서 Insert
		Menu m = new Menu(); 
		int mcnt = Menu.createArray();
		for(int i=1; i<=mcnt; i++) {
			String rName = m.rNameList[i]; int tel = m.telList[i]; String meal = m.mealList[i];  int price = m.priceList[i]; int code = m.codeList[i];
			String queryInsert = "insert into Menu values ( ? , ? , ? , ?, ?)";
			PreparedStatement ps = conn.prepareStatement(queryInsert);
			ps.setString(1,rName);
			ps.setInt(2, tel);
			ps.setString(3, meal);
			ps.setInt(4, price);
			ps.setInt(5, code);
			ps.executeUpdate();
		}
		
		
		System.out.println("3. 쿼리문 실행");
		
		
		/*
		//Restaurant 테이블 전체 출력
		rs = st.executeQuery("select * from Restaurant");
		System.out.println("<식당명" +"/"+ "전화번호" +"/"+ "주소" + "/"+ "분류>");
		if(rs!=null) {
	         while(rs.next()) {
	        	 String rName = rs.getString("rName");
	        	 int tel = rs.getInt("tel");
	        	 String address = rs.getString("address");
	        	 String category = rs.getString("category");
	             System.out.println(rName +" / "+ tel +" / "+ address + " / " +category);
	          }
	         rs.close(); 
	     }
		*/
	     
		
		/*
		//RegionMoney 테이블 전체 출력
				rs = st.executeQuery("select * from RegionMoney");
				System.out.println("<식당명" + "주소>");
				if(rs!=null) {
			         while(rs.next()) {
			        	 String rName = rs.getString("rName");			        	 
			        	 String address = rs.getString("address");
			             System.out.println(rName +" / "+ address );
			          }
			         rs.close(); 
			     }
		*/
	     
		
		/*
		//Menu 테이블 전체 출력
		rs = st.executeQuery("select * from Menu");
		System.out.println("<식당명" +"/"+ "전화번호" +"/"+ "메뉴" + "/"+ "가격>");
		if(rs!=null) {
	         while(rs.next()) {
	        	 String rName = rs.getString("rName");
	        	 int tel = rs.getInt("tel");
	        	 String meal = rs.getString("meal");
	        	 int price = rs.getInt("price");
	             System.out.println(rName +" / "+ tel +" / "+ meal + " / " +price);
	          }
	         rs.close(); 
	     }
	     */
	     
		
		//System.out.println("4. 테이블삭제");
		//st.executeUpdate("drop table Restaurant");
		//st.executeUpdate("drop table RegionMoney");
		//st.executeUpdate("drop table Menu");
		
		
		
	}
}
