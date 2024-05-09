package camp.utility;

import java.util.Scanner;

public class ConsoleIO {
    private static final Scanner sc = new Scanner(System.in);

    // 정수 입력 받기
    public int getIntInput(String prompt) {
        System.out.print(prompt);
        int number = sc.nextInt();
        sc.nextLine();
        return number;
    }

    // 문자열 입력 받기
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    // System.out.println 메서드 간소화
    public void print(String message) {
        System.out.println(message);
    }
}
