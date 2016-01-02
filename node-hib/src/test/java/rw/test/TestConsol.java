package rw.test;

import java.util.Scanner;

/**
 * Created by miha on 30.07.2015.
 */
public class TestConsol {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
//        int a = in.nextInt();//считываем целое число
//        byte b = in.nextByte();//считываем байтовое число
//        String c = in.nextLine();//считываем одну строку целиком
//        double d = in.nextDouble();//считываем вещественное число
//        long  e = in.nextLong();//считываем длинное целое число
//        short f = in.nextShort();//считываем короткое целое число
//        String s = in.next();//считываем строку до первого пробела
        String s = in.nextLine();
        System.out.println(s);
    }
}
