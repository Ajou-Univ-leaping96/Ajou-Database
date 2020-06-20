package Excute;
import java.sql.*;
import java.util.Scanner;

//// 테이블 생성 코드
public class SqlTest {
    public static void main(String[] args) throws SQLException {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");

            System.out.println("Connecting PostgreSQL database");
            // JDBC를 이용해 PostgreSQL 서버 및 데이터 베이스 연결
            String username, pwd, url;
            // postgre username
            username = "postgres";
            // postgre port number
            url = "jdbc:postgresql://localhost:5432/";
            // postgre password
            pwd = "123123";

            Connection conn = DriverManager.getConnection(url, username, pwd);

            //////////////////////////////////////////////////////////////////////////////
            // 테이블 생성 코드

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
