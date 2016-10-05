/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.helper;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 *
 * @author Rahman Adianto
 */
public class FilterManager {
    
    public Instances filterResample(Instances input, Double percentage) {
        Instances result = null;
        Resample filter = new Resample();
        
        System.out.println("Resampling...");
        filter.setBiasToUniformClass(1.0);
        try {
            filter.setInputFormat(input);
            filter.setNoReplacement(false);
            filter.setSampleSizePercent(percentage);
            result = Filter.useFilter(input, filter);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
}
