/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.controller;

import java.util.Scanner;
import wekafkr.FileManager;

/**
 *
 * @author fauzanrifqy
 */
public class Menu {
    private int selection = 0;
    
    public void askSelection(){
        System.out.print("1. Load File\n");
        System.out.print("2. Show Attributes\n");
        System.out.print("3. Filter\n");
        System.out.print("4. Build Classifier\n");
        System.out.print("5. Testing Model\n\n");
        
        System.out.print("6. Save Model\n");
        System.out.print("7. Load Model\n\n");
        
        System.out.print("Your input: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
    }
    
    public void goToSelection(){
        switch(selection){
            case 1:
                FileManager fileManager = new FileManager();
                fileManager.openFile("data/weather.arff");
                break;
            default:
                System.out.print("Please input your selection between number listed above.");
                break;
        }
    }
    
}