package tutorial.ch01.sec01;

import java.util.Scanner;

public class UserInputExample {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = in.nextLine();
        System.out.println("Hi " + name + "!I");
        System.out.println("How old are you?");
        int age = in.nextInt();
        System.out.println(age);
    }



}
