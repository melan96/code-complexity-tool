/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cps_Calculations;

import codecomplexitytoolspm.Analysis;
import codecomplexitytoolspm.ProgramStatement;

/**
 *
 * @author melan
 */
public class CpsCalculation {
    
    
    //Calculate CPs values 
    //Cps = Tw * Cs
    
    public void calculateCpsValues(){
   
        for(ProgramStatement progs : Analysis.resultSet){
            progs.setCpsValue(progs.getTwValue()*progs.getCsValue());
        }
    }
    
}
