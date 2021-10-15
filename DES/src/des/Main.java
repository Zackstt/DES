package des;
//package com.company;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        // TODO read in args, should specify encode or decode
        // TODO display prompt and capture key
        // 
        String cmdOutput = "Enter message to encrypt: ";
        Scanner userInput = new Scanner(System.in);

        System.out.println(cmdOutput);
        String lineIn = userInput.nextLine();
        System.out.println(lineIn + "\n");

        // TODO

    }
}
