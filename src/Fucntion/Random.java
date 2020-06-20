package Fucntion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Excute.DooBoo;

public class Random {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        DooBoo dooboo = new DooBoo();
        
        Connection conn = null;
      Statement st = null;
      ResultSet rs = null;

      // connection 속성 설정
      String url = "jdbc:postgresql://localhost:5432/DooBoo";
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

      System.out.println("[ 완전 랜덤 추천 페이지 ]\n");
        // 지역화페가맹점
        System.out.println("지역화폐가맹점만 추천받으시겠습니까?(1번과 2번중 하나만 골라주세요)");
        System.out.println("1: 예 / 2: 아니요(상관없음)");
        // 지역화페가맹점 추천 여부
        int regionmoneyRec;
        regionmoneyRec = scan.nextInt();
        //예외처리
        if (regionmoneyRec < 1 || regionmoneyRec > 2) {
            System.out.println("1,2번 중에 하나만 골라주세요~");
            System.out.println("1: 예 / 2: 아니요(상관없음)");
            regionmoneyRec = scan.nextInt();
        }

        //가격대 설정
        System.out.println("가격대를 설정하시겠습니까?");
        System.out.println("1: 예 / 2: 아니요");
        // 가격대 설정 여부
        int priceLimit;
        // 제한 가격
        int maxPrice = 0;

        priceLimit = scan.nextInt();
        if (priceLimit < 1 || priceLimit > 2) {
            System.out.println("1,2번 중에 하나만 골라주세요~");
            System.out.println("1: 예 / 2: 아니요");
            priceLimit = scan.nextInt();
        }
        if (priceLimit == 1) {
            System.out.println("최대 가격대를 설정하세요.");
            maxPrice = scan.nextInt();
        }

        /*
         *  지역화폐가맹여부, 가격 설정여부 , 제한 가격
         *  각 선택값에 맞는 랜덤 추천
         * */

        // 배열 행 갯수
        int i = 0;
        String rName[] = new String[9999];
        String meal[] = new String[9999];
        int price[] = new int[9999];
        String address[] = new String[9999];
        int tel[] = new int[9999];
        
        
        
        // 지역화폐 가맹점만 추천받고 , 제한 가격 없는 경우
        if(regionmoneyRec == 1 && priceLimit==2) {
           query = "select Menu.rName, Menu.meal, Menu.price, Restaurant.address, Menu.tel"
                 + " from Restaurant, Menu,RegionMoney"
                 + " where Restaurant.tel = Menu.tel and RegionMoney.rName = Restaurant.rName and RegionMoney.address = Restaurant.address";
           rs = st.executeQuery(query);
           
           i = 0;
           
           if(rs!=null) {
              while(rs.next()) { 
                 rName[i]= rs.getString("rName"); 
                 meal[i]= rs.getString("meal");
                 price[i] = rs.getInt("price"); 
                 address[i] = rs.getString("address"); 
                 tel[i] = rs.getInt("tel");
                 
                 i=i+1;
              }
           }
           if (i==0){
              System.out.println("조건에 맞는 식당이 없습니다..");
           }else {
           // 출력된 행의 수 (i) 범위안에서 랜덤 숫자 뽑기           
           int randnum = (int) (Math.random()*i);  
           System.out.println("\n 오늘은 이 식당, 이 메뉴 어떠세요? ");
           System.out.println("\n      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
            System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);           }
           rs.close();
           
        }
        
        // 지역화폐 가맹점만 추천받고, 제한 가격 있는 경우
        if(regionmoneyRec == 1 && priceLimit==1) {
            query = "select Menu.rName, Menu.meal, price, Restaurant.address, Menu.tel"
                     + " from Restaurant, Menu,RegionMoney"
                     + " where Restaurant.tel = Menu.tel and RegionMoney.rName = Restaurant.rName and RegionMoney.address = Restaurant.address and price<?;";

           PreparedStatement ps = conn.prepareStatement(query);
           ps.setInt(1, maxPrice);
         rs = ps.executeQuery();
         
         i = 0;
         
           if(rs!=null) {
              while(rs.next()) { 
                 rName[i]= rs.getString("rName"); 
                 meal[i]= rs.getString("meal");
                 price[i] = rs.getInt("price"); 
                 address[i] = rs.getString("address"); 
                 tel[i] = rs.getInt("tel");
                 
                 i=i+1;
              }
           }
           if (i==0){
              System.out.println("조건에 맞는 식당이 없습니다..");
           }else {
           // 출력된 행의 수 (i) 범위안에서 랜덤 숫자 뽑기           
           int randnum = (int) (Math.random()*i);      
           System.out.println("\n 오늘은 이 식당, 이 메뉴 어떠세요? ");
           System.out.println("      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
            System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);           }
           rs.close();           
        }
        
        
        // 지역화폐 가맹점 상관없고, 제한 가격 있는 경우
        if(regionmoneyRec == 2 && priceLimit==1) {
           
           query = "select Restaurant.rName, meal, price, Restaurant.address, Restaurant.tel "
           + "from Restaurant, Menu "
           + "where Restaurant.tel = Menu.tel and Restaurant.rName = Menu.rName and price <= ?;";
           PreparedStatement ps = conn.prepareStatement(query);
           ps.setInt(1, maxPrice);
         rs = ps.executeQuery();
         
         i = 0;
                       
           if(rs!=null) {
              while(rs.next()) { 
                 rName[i]= rs.getString("rName"); 
                 meal[i]= rs.getString("meal");
                 price[i] = rs.getInt("price"); 
                 address[i] = rs.getString("address"); 
                 tel[i] = rs.getInt("tel");
                 
                 i=i+1;
              }
           }
           if (i==0){
              System.out.println("조건에 맞는 식당이 없습니다..");
           }else {
           // 출력된 행의 수 (i) 범위안에서 랜덤 숫자 뽑기           
           int randnum = (int) (Math.random()*i);      
           System.out.println("\n 오늘은 이 식당, 이 메뉴 어떠세요? ");
           System.out.println("\n      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
            System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);           }
           rs.close();
        }
        
        
        // 지역화폐 가맹점 상관 없고, 제한 가격 없는 경우
        if(regionmoneyRec == 2 && priceLimit==2) {
           query = "select Menu.rName, Menu.meal, price, Restaurant.address, Menu.tel"
                 + " from Restaurant, Menu"
                 + " where Restaurant.tel = Menu.tel and Restaurant.rName = Menu.rName;";
           rs = st.executeQuery(query);

           i = 0;
           
           if(rs!=null) {
              while(rs.next()) { 
                 rName[i]= rs.getString(1); 
                 meal[i]= rs.getString(2);
                 price[i] = rs.getInt(3); 
                 address[i] = rs.getString(4); 
                 tel[i] = rs.getInt(5);
                 
                 i=i+1;
              }
           }
           if (i==0){
              System.out.println("조건에 맞는 식당이 없습니다..");
           }else {
           // 출력된 행의 수 (i) 범위안에서 랜덤 숫자 뽑기           
           int randnum = (int) (Math.random()*i);      
           System.out.println(" 오늘은 이 식당, 이 메뉴 어떠세요? ");
           System.out.println("      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
            System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]); 
            }
           
           rs.close();

        }
        
        // 조건에 맞는 식당이 없을 땐,  홈화면 질문 
        // 조건에 맞는 식당이 있을 땐, 다시 추천받을지 질문
        if(rName[0]!=null) {
        System.out.println("\n\n다시 추천 받으시겠습니까?(1번과 2번중 하나만 골라주세요)");
        System.out.println("1: 예 / 2: 아니요");
        int oneMore = scan.nextInt();
        
        if(oneMore==1) {
           int randnum = (int) (Math.random()*i);
           System.out.println(" 오늘은 이 식당, 이 메뉴 어떠세요? ");
            System.out.println("\n      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
            System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);
            
            System.out.println("\n\n홈화면으로 돌아가시겠습니까?(1번과 2번중 하나만 골라주세요)");
            System.out.println("1: 예 / 2: 아니요. 종료하겠습니다.");
            
            int back;
            back = scan.nextInt();
            
            
            if(back==1) {
               dooboo.main(null);
            }else {
               System.out.println("\n저희 서비스를 이용해주셔서 감사합니다!");
            }
        }else {
       
        System.out.println("\n\n홈화면으로 돌아가시겠습니까?(1번과 2번중 하나만 골라주세요)");
        System.out.println("1: 예 / 2: 아니요. 종료하겠습니다.");
        
        int back;
        back = scan.nextInt();
        
        
        if(back==1) {
           dooboo.main(null);
        }else {
           System.out.println("\n저희 서비스를 이용해주셔서 감사합니다!");
        }
       
        }
        }else {
           System.out.println("\n\n홈화면으로 돌아가시겠습니까?(1번과 2번중 하나만 골라주세요)");
            System.out.println("1: 예 / 2: 아니요. 종료하겠습니다.");
            
            int back;
            back = scan.nextInt();
            
          //예외처리
            if (back < 1 || back > 2) {
                System.out.println("1,2번 중에 하나만 골라주세요~");
                System.out.println("1: 예 / 2: 아니요");
                priceLimit = scan.nextInt();
            }
            
            if(back==1) {
               dooboo.main(null);
            }else {
               System.out.println("\n저희 서비스를 이용해주셔서 감사합니다!");
            }
        }
        
    }
}