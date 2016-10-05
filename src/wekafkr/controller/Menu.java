/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.controller;

import java.util.Scanner;
import weka.core.Instances;
import wekafkr.FileManager;

/**
 *
 * @author fauzanrifqy
 */
public class Menu {
    private int selection = 0;
    private Instances instances;
    
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
                instances = fileManager.openFile("data/weather.arff");
                showAttributes(instances);
                break;
            case 2:
                showAttributes(instances);
                break;
            default:
                System.out.print("Please input your selection between number listed above.");
                break;
        }
    }
    
    public static void showAttributes(Instances instances){
        for(int i=0; i<instances.numAttributes(); i++){
            System.out.print(Integer.toString(i+1)+". "+instances.attribute(i).name()+"\n");
        }
        System.out.print("\n");
    }
    
}
