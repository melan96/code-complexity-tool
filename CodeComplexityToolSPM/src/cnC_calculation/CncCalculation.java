/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnC_calculation;

import codecomplexitytoolspm.ProgramStatement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author melan
 */
public class CncCalculation {

    //var base
    ArrayList<ProgramStatement> lineOfCodeList;
    ArrayList<Integer> cncPoints;
    //static LinkedHashMap<Integer, Integer> cncLinePoint = new LinkedHashMap<Integer, Integer>();
    Integer bracketState = 0; //initially bracket state at globle value map

    //REGX block
    
    String bracketsREGX = "\\b((if|while|for|do)(\\s+|\\().*\\{)";
    String singleLineREGX = "^(\\s*\\}\\s*)|^(\\s*)$";
    String openBracketsREGX = "\\{";
    String closedBracketREGX = "\\}";

    //Initialize LINE_OF_CODE 
    public CncCalculation(ArrayList<ProgramStatement> lineOfCodeList) {
        this.lineOfCodeList = lineOfCodeList;
        cncPoints = new ArrayList<Integer>(lineOfCodeList.size());
    }

    public void incrementBracketState() {
        System.out.println("increment state CNC");
        bracketState++;
    }

    public void decrementBracketState() {

        System.out.println("Decrement state CNC");
        
        if(this.bracketState > 0){
            bracketState--;
        }

    }

    public void coreBracketMapper() {
        
        //REGX PatternCompiler Heads
        Pattern bracketsPattern = Pattern.compile(bracketsREGX);
        Pattern bracketsSingle = Pattern.compile(singleLineREGX);
        Pattern bracketsClosed = Pattern.compile(closedBracketREGX);

        // Check each line for Cnc
        for (int i = 0; i < lineOfCodeList.size(); i++) {
            int count = 0;
            
            //Fetch String Content
            String line = lineOfCodeList.get(i).getLineContent();
            
            // check for conditions and loops
            Matcher matcher = bracketsPattern.matcher(line);
            while (matcher.find()) {
                incrementBracketState();
                
            }
            
            count = bracketState;

            Matcher close_m = bracketsClosed.matcher(line);
            while (close_m.find()) {
                decrementBracketState();
            }
            
            count = bracketState;
            
            // check for lines with brackets or empty line
            Matcher singleline = bracketsSingle.matcher(line);
            if (singleline.find()) {
                count = 0;
            }
            cncPoints.add(count);
        }
    }

    public ArrayList<Integer> getCnc() {
        coreBracketMapper();
        return cncPoints;
    }

    public int getTotalCncPoints() {
        int total = 0;

        for (int i = 0; i < cncPoints.size(); i++) {
            total += cncPoints.get(i);
        }

        return total;
    }

}
