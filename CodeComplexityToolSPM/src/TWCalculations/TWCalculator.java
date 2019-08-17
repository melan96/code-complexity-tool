/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TWCalculations;

import codecomplexitytoolspm.Analysis;
import codecomplexitytoolspm.ProgramStatement;

/**
 *
 * @author melan
 */
public class TWCalculator {
    
    
    public void calculateTWforProgramStatement(){
    
    
    //TW = CTC+CnC+Ci
    //Iteretor over programStatement object
    for (ProgramStatement progStatement : Analysis.resultSet){
        System.out.println("Ci -> "+progStatement.getCiValue() + " CnC -> "+progStatement.getCncValue()+" Ctc Value ->"+progStatement.getCtcValue());
        progStatement.setTwValue(progStatement.getCiValue()+progStatement.getCncValue()+progStatement.getCtcValue());
    }
    }
    
}
