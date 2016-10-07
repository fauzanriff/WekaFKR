/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.classifier;

import java.util.Scanner;
import weka.core.Instance;
import weka.core.Instances;

public class MyClassifier {
    public static MyC45 myc45 = new MyC45();
    public static MyID3 myid3 = new MyID3();
    public static int choice;
    
    public void run (Instances instance) throws Exception {
        System.out.println("memilih algoritma : 1 = MyC45, 2 = MyID3");
        Scanner in = new Scanner(System.in);
        choice = in.nextInt();
        
        switch(choice){
            case 1 : 
                buildC45(instance);
                break;
            case 2:
                buildID3(instance);
                break;
            default:
                break;
        }
    }
    
    public static void buildC45(Instances data) throws Exception {
        myc45.buildClassifier(data);
        System.out.println(myc45.toString());
        System.out.println("memulai prune");
        myc45.prune(data);
        
    }
    
    public static void buildID3(Instances data) throws Exception {
        myid3.buildClassifier(data);
        System.out.println(myid3.toString());
        System.out.println("memulai prune");
    }
}