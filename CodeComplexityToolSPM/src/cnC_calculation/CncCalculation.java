/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnC_calculation;

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
    ArrayList<String> lineOfCode;
    ArrayList<Integer> cncPoints;
    static LinkedHashMap<Integer, Integer> cncLinePoint = new LinkedHashMap<Integer, Integer>();
    int bracketState = 0; //initially bracket state at globle value map

    //REGX block
    String openBracketsREGX = "\\{";
    String closedBracketREGX = "\\}";
    String bracketsREGX = "\\b((if|while|for|do)(\\s+|\\().*\\{)";
    String singleLineREGX = "^(\\s*\\}\\s*)|^(\\s*)$";

    public CncCalculation(ArrayList<String> lineOfCode) {
        this.lineOfCode = lineOfCode;
        cncPoints = new ArrayList<Integer>(lineOfCode.size());
    }

    public void incrementBracketState() {
        System.out.println("increment state CNC");
        this.bracketState++;
    }

    public void decrementBracketState() {

        System.out.println("Decrement state CNC");
        this.bracketState--;

    }

    public void coreBracketMapper() {
        Pattern bracketsPattern = Pattern.compile(bracketsREGX);
        Pattern bracketsSingle = Pattern.compile(singleLineREGX);
        Pattern bracketsClosed = Pattern.compile(closedBracketREGX);

        // Check each line for Cnc
        for (int i = 0; i < lineOfCode.size(); i++) {
            int count = 0;
            String line = lineOfCode.get(i);
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
