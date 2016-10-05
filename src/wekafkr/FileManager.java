/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;

/**
 *
 * @author fauzanrifqy
 */
public class FileManager {
    Instances data;
    
    public Instances openFile(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            data = new Instances(reader);
            reader.close();
        } 
        catch (FileNotFoundException ex){
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
}
