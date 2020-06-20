package Excute;
import sun.rmi.transport.StreamRemoteCall;

import java.io.InputStream;
import java.util.Scanner;
///////////////////////// OpenAPI 값 가져오는 코드 ////////////////////////

import Fucntion.CategoryRandom;
import Fucntion.Random;
import Fucntion.SearchMenu;

public class DooBoo {
    public static void main(String[] args) throws  Exception{

        Scanner scan = new Scanner(System.in);
        /////// 메인 홈 //////
        System.out.println("[ ♬ MAIN HOME ♬] \n");
        System.out.println("원하는 추천방식을 번호로 입력해주세요");
        System.out.println("1: 카테고리 랜덤 / 2: 완전랜덤 / 3: 메뉴검색 \n");
        // 추천 방식 선택값
        int choice_home;
        choice_home = scan.nextInt();

        if (choice_home==1){
            // 카테고리 랜덤 CLASS로 가기
            CategoryRandom categoryRandom = new CategoryRandom();
            categoryRandom.main(null);

        }else if (choice_home==2){
           // 완전 랜덤 CLASS로 가기
            Random random = new Random();
            random.main(null);

        }else if (choice_home==3){
            // 메뉴검색 CLASS로 가기
            SearchMenu searchMenu = new SearchMenu();
            searchMenu.main(null);

        } else {
            System.out.println("1,2,3번 중에 하나만 골라주세요~");
            System.out.println("1: 카테고리 랜덤 / 2: 완전랜덤 / 3: 메뉴검색 \n");
            choice_home = scan.nextInt();
        }


    }
}