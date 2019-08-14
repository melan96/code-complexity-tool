/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnC_calculation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author melan
 */
public class CncCalculation {
    
    //var base
    ArrayList<String> lineOfCode;
    ArrayList<Integer> cncPoints;
    static LinkedHashMap<Integer,Integer> cncLinePoint = new LinkedHashMap<Integer,Integer>();
    int bracketState = 0; //initially bracket state at globle value map
    
    //REGX block
    String openBracketsREGX="\\{";
    String closedBracketREGX= "\\}";
    String bracketsREGX = "\\b((if|while|for|do)(\\s+|\\().*\\{)";
    String singleLineREGX="^(\\s*\\}\\s*)|^(\\s*)$";
    
      public CncCalculation(ArrayList<String> lineOfCode){
        this.lineOfCode = lineOfCode;
        cncPoints = new ArrayList<Integer> (lineOfCode.size());
    }
    
    public void incrementBracketState(){
        System.out.println("increment state CNC");
        this.bracketState++;
    }
    
    public void decrementBracketState(){
    
        System.out.println("Decrement state CNC");
        this.bracketState--;
        
    }
    
    public void coreBracketMapper() {
        
    }


}
