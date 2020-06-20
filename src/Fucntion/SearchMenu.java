package Fucntion;

import java.util.Scanner;

import Excute.DooBoo;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchMenu {
   public static void main(String[] args) throws Exception {
      Scanner scan = new Scanner(System.in);
      //System.out.println("SQL Programming Test");
      //System.out.println("Connecting PostgreSQL database");
      // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
      Connection conn = null;
      Statement st = null;
      ResultSet rs = null;

      // connection 속성 설정
      String url = "jdbc:postgresql://localhost/DooBoo";
      String user = "postgres";
      String password = "123123";
      String query = "select version()";

      //System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

      try {
         // postgresql 드라이버 클래스를 JDBC드라이버 메니저에 로드
         Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException e) {
         //System.out.println("Where is your PostgreSQL JDBC Driver?" + "Include in your library path!");
         e.printStackTrace();
         return;
      }

      try {
         // conncetion establish 하기
         conn = DriverManager.getConnection(url, user, password);

      } catch (SQLException e) {
         //System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }

      if (conn != null) {
         //System.out.println("Connection established!");
      } else {
         //System.out.println("Failed to make conn!");
      }

      //System.out.println("PostgreSQL JDBC Driver Registered!");

      st = conn.createStatement();

      System.out.println("추천받고싶은 메뉴 키워드를 입력해주세요: ");
      // 입력받은 메뉴이름
      String menu;
      menu = scan.next();

      System.out.println("메뉴 정렬 방식을 골라주세요.");
      System.out.println("1: 낮은 가격순/ 2:높은 가격순");
      // 정렬 방식
      int sort;
      sort = scan.nextInt();

      // 예외처리
      if (sort < 1 || sort > 3) {
         System.out.println("1,2번 중에 하나만 골라주세요~");
         System.out.println("1: 예 / 2: 아니요");
         sort = scan.nextInt();
      }

      // select rName,meal,price from Menu where meal like '%짜장%' order by price asc
      // => 낮은가격순
      // select rName,meal,price from Menu where meal like '%짜장%' order by price desc
      // => 높은 가격순

      /*
       * 각 선택값에 맞는 메뉴와 식당 나열하기
       */

      if (sort == 1) {
         query = "select rName,meal,price from Menu where meal like " + "'%" + menu + "%'" + " order by price asc";
         rs = st.executeQuery(query);
         System.out.println("<식당명" + " / " + "메뉴" + " / " + "가격>");
         if (rs != null) {
            while (rs.next()) {
               String rName = rs.getString("rName");
               String meal = rs.getString("meal");
               int price = rs.getInt("price");
               System.out.println(rName + " / " + meal + " / " + price);
            }
            rs.close();
         }
      } else if (sort == 2) {
         query = "select rName,meal,price from Menu where meal like " + "'%" + menu + "%'" + " order by price desc";
         rs = st.executeQuery(query);
         System.out.println("<식당명" + " / " + "메뉴" + " / " + "가격>");
         if (rs != null) {
            while (rs.next()) {
               String rName = rs.getString("rName");
               String meal = rs.getString("meal");
               int price = rs.getInt("price");
               System.out.println(rName + " / " + meal + " / " + price);
            }
            rs.close();
         }

      }
      
      DooBoo dooboo = new DooBoo();
        System.out.println("\n\n홈화면으로 돌아가시겠습니까?(1번과 2번중 하나만 골라주세요)");
        System.out.println("1: 예 / 2. 한번더 / 3: 아니요. 종료하겠습니다.");
        // 지역화페가맹점 추천 여부
        int back;
        back = scan.nextInt();
        
        switch (back) {
        	
        case 1:
        	dooboo.main(null);
        	break;
        case 2:
        	SearchMenu.main(null);
        	break;
        case 3:
           System.out.println("\n저희 서비스를 이용해주셔서 감사합니다!");
        }

   }
}