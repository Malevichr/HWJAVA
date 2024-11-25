package org.main;

import java.util.Scanner;

public class Input {
    private final Scanner scanner = new Scanner(System.in);
    public Integer input(){
        try {
            return scanner.nextInt();
        }catch (Exception e){
            System.out.println("Not Integer number");
            return this.input();
        }
    }
}
