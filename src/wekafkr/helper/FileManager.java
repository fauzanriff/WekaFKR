/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import weka.core.Instances;

/**
 *
 * @author fauzanrifqy
 */
public class FileManager {
    private String filename;
    private Instances data;
    private int opt;
    
    public Instances openFile(String filename){
        
        this.filename = filename;
        
        System.out.println("Default file data/weather_numeric.arff");
        System.out.println("Put your data on data/ directory");
        System.out.println("Option (?)");
        System.out.println("1 Load default file");
        System.out.println("2 Load your file");
        System.out.print("\nYour input: ");
        
        Scanner scan = new Scanner(System.in);
        opt = scan.nextInt();
        
        if (opt == 2) {
            System.out.print("Filename: ");
            this.filename = scan.next();
        }
        
        System.out.println("Load file.....");
                
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/" + this.filename));
            data = new Instances(reader);
            reader.close();
            System.out.println("Load success");
        } 
        catch (FileNotFoundException ex) {
            System.out.println("Load error, file not found");
        } 
        catch (IOException ex) {
            System.out.println("Load error, bad I/O");
        }
        
        return data;
    }
}
