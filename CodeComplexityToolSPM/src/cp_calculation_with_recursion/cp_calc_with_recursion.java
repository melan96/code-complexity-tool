/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp_calculation_with_recursion;

import codecomplexitytoolspm.Analysis;
import codecomplexitytoolspm.ProgramStatement;

/**
 *
 * @author Romesh
 */
public class cp_calc_with_recursion {
    public static int cpTotal=0;
    public void calculatesCpValues(){
   
        for(ProgramStatement progs : Analysis.resultSet){
            if(progs.getCrValue()==0){
                progs.setCpValue(progs.getCpsValue());
            }
            else{
                progs.setCpValue(progs.getCpsValue() + progs.getCrValue());
            }
            cpTotal=cpTotal+progs.getCpValue();
        }
    }
}
