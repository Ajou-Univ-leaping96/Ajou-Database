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

public class CategoryRandom {
   static String menuCategory=null;
   static int pl;//가격대 설정 여부
   static int limitPrice; // 가격대제한설정
   static int recommRegion; // 지역화폐가맹점 추천여부
   
    public static void main(String[] args) throws Exception {
       
      Scanner scan = new Scanner(System.in);
      DooBoo dooboo = new DooBoo();
      
      // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
      Connection conn = null;
      Statement  st   = null;
      ResultSet  rs   = null;
   
      // connection 속성 설정
         String     url      = "jdbc:postgresql://localhost:5432/DooBoo";
      String     user     = "postgres";
      String     password = "123123";
      String    query    = "select version()";
   
      try // conncetion establish 하기
      {
         conn = DriverManager.getConnection(url, user, password); 
      }
      catch (SQLException e) 
      {
         //System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return; 
      }
      
      if (conn != null)
      {
         //System.out.println("Connection established!");
      } 
      else 
      {
         //System.out.println("Failed to make conn!");
      }
      
      //카테고리 선택
        System.out.println("[ 카테고리 랜덤추천 페이지 ] \n");
        System.out.println("원하는 카테고리를 입력해주세요");
        System.out.println("[한식][중국식][일식][양식] \n");
        // 카테고리 선택값
        menuCategory = scan.nextLine();
        //예외처리
        while(!menuCategory.equals("한식")&&!menuCategory.equals("중국식")&&!menuCategory.equals("일식")&&!menuCategory.equals("양식")){
            System.out.println("[한식][중국식][일식][양식] 중에 하나만 입력해주세요~\n");
            menuCategory = scan.nextLine();
        }

        
        
        // 지역화페가맹점
        System.out.println("지역화폐가맹점만 추천받으시겠습니까?(1번과 2번중 하나만 골라주세요)");
        System.out.println("1: 예 / 2: 아니요(상관없음)");
        // 지역화페가맹점 추천 여부
        recommRegion = scan.nextInt();
       //scan.nextLine();
        //예외처리
        if(recommRegion<1||recommRegion>2){
            System.out.println("1,2번 중에 하나만 골라주세요~");
            System.out.println("1: 예 / 2: 아니요(상관없음)");
            recommRegion = scan.nextInt();
        }

        
 
        //가격대 설정
        System.out.println("가격대를 설정하시겠습니까?");
        System.out.println("1: 예 / 2: 아니요");
        pl = scan.nextInt();
        
        if (pl < 1 || pl > 2) {
            System.out.println("1,2번 중에 하나만 골라주세요~");
            System.out.println("1: 예 / 2: 아니요");
            pl = scan.nextInt();
        }
        
        if (pl == 1) {
            System.out.println("최대 가격대를 설정하세요.");
            limitPrice = scan.nextInt();
        }

        /*
        *  카테고리 선택값 , 지역화폐가맹여부, 가격 설정여부 , 제한 가격
        *  각 선택값에 맞는 랜덤 추천
        * */
        
        int i = 0;
        String rName[] = new String[9999];
        String address[] = new String[9999];
        int tel[] = new int[9999];
        String meal[] = new String[9999];
        int price[] = new int[9999];        
   
        if(recommRegion==1 && pl==1) //지역화폐추천o, 가격대설정o
       {
          
          try {
            System.out.printf("\n");
        
            query="select RegionMoney.rName, Restaurant.address, Restaurant.tel, meal, price from Restaurant join RegionMoney on Restaurant.rName = RegionMoney.rName join Menu on RegionMoney.rName = Menu.rName where category = ? and price <= ? ";
         
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, menuCategory);
            pst.setInt(2, limitPrice);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
               rName[i] = rs.getString(1);
               address[i] = rs.getString(2);
               tel[i] = rs.getInt(3);
               meal[i] = rs.getString(4);
               price[i] = rs.getInt(5);
                i++;
            
            }
            
            int randnum = (int) (Math.random()*i); 
            if(rName[randnum]!=null && address[randnum]!=null && tel[randnum]!=0 && meal[randnum]!=null && price[randnum]!=0)
            {
               System.out.println(" 오늘은 이 식당, 이 메뉴 어떠세요? ");
                 System.out.println("\n      <식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호>");
               System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);
            }
            else
            {
               System.out.println("조건에 맞는 식당이 없습니다..");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            } catch(SQLException ex) {
               ex.printStackTrace();
            }

       }
       else if(recommRegion==1 && pl==2)//지역화폐추천o, 가격대설정x
       {
          try {
            System.out.printf("\n");
            query="select RegionMoney.rName, Restaurant.address, Restaurant.tel, meal, price from Restaurant join RegionMoney on Restaurant.rName = RegionMoney.rName join Menu on RegionMoney.rName = Menu.rName where category = ? ";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, menuCategory);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
               rName[i] = rs.getString(1);
               address[i] = rs.getString(2);
               tel[i] = rs.getInt(3);
               meal[i] = rs.getString(4);
               price[i] = rs.getInt(5);
                i++;
            
            }
            int randnum = (int) (Math.random()*i); 
            if(rName[randnum]!=null && address[randnum]!=null && tel[randnum]!=0 && meal[randnum]!=null && price[randnum]!=0)
            {
               System.out.println(" 오늘은 이 식당, 이 메뉴 어떠세요? ");
               System.out.println("\n      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
               System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);
            }
            else
            {
               System.out.println("조건에 맞는 식당이 없습니다..");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            } catch(SQLException ex) {
               ex.printStackTrace();
            }
       }
       
       else if(recommRegion!=1 && pl==1)//지역화폐추천x, 가격대설정o
       {
          try {
            System.out.printf("\n");
            query="select Restaurant.rName, Restaurant.address, Restaurant.tel, meal, price from Restaurant join Menu on Restaurant.rName = Menu.rName where category = ? and price <= ?";
              
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, menuCategory);
            pst.setInt(2, limitPrice);
            rs = pst.executeQuery();
         
            while(rs.next())
            {
               rName[i] = rs.getString(1);
               address[i] = rs.getString(2);
               tel[i] = rs.getInt(3);
               meal[i] = rs.getString(4);
               price[i] = rs.getInt(5);
                i++;
            
            }
            int randnum = (int) (Math.random()*i); 
            if(rName[randnum]!=null && address[randnum]!=null && tel[randnum]!=0 && meal[randnum]!=null && price[randnum]!=0)
            {
               System.out.println(" 오늘은 이 식당, 이 메뉴 어떠세요? ");
               System.out.println("\n      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
               System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);
            }
            else
            {
               System.out.println("조건에 맞는 식당이 없습니다..");
            }
            if(rs != null) rs.close();
            if(st != null) st.close();
            } catch(SQLException ex) {
               ex.printStackTrace();
            }
       }
       
       else if(recommRegion!=1 && pl==2)//지역화폐추천x, 가격대설정x
       {
          try {
            System.out.printf("\n");
     
            query="select Restaurant.rName, Restaurant.address, Restaurant.tel, meal, price from Restaurant join Menu on Restaurant.rName = Menu.rName where category = ?";
              
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, menuCategory);
            rs = pst.executeQuery();
         
            while(rs.next())
            {
               rName[i] = rs.getString(1);
               address[i] = rs.getString(2);
               tel[i] = rs.getInt(3);
               meal[i] = rs.getString(4);
               price[i] = rs.getInt(5);
                i++;
            
            }
            int randnum = (int) (Math.random()*i); 
            if(rName[randnum]!=null && address[randnum]!=null && tel[randnum]!=0 && meal[randnum]!=null && price[randnum]!=0)
            {
               System.out.println(" 오늘은 이 식당, 이 메뉴 어떠세요? ");
               System.out.println("\n      < 식당명" + " | " + "메뉴" + " | " + "가격"+ " | " + "주소"+" | "+"전화번호 >");
               System.out.printf(" %s | %s | %d | %s | %d ",rName[randnum],meal[randnum],price[randnum],address[randnum],tel[randnum]);
            }
            else
            {
               System.out.println("조건에 맞는 식당이 없습니다..");
            }
            
            if(rs != null) rs.close();
            if(st != null) st.close();
            } catch(SQLException ex) {
               ex.printStackTrace();
            }
       }
       
       else
       {
          try {
               System.out.println("다시 입력하세요.");
            } catch(Exception ex) {
               ex.printStackTrace();
            }
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
                pl = scan.nextInt();
            }
            
            if(back==1) {
               dooboo.main(null);
            }else {
               System.out.println("\n저희 서비스를 이용해주셔서 감사합니다!");
            }
        }
        
       
      
        if(conn != null) conn.close();
      
        
       
    }
    

}