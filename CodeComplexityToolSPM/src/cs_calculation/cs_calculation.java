/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_calculation;

import codecomplexitytoolspm.Analysis;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pasindu
 */
public class cs_calculation {
    
    private final String REGEX_TWO = "new|delete|throw|throws|&[^&]|\\*";
    private final String REGEX_ARITHMETIC = "\\+[^\\+]|-[^-]| \\*|/|%|\\+\\+|--";
    private final String REGEX_RELATION = "==|!=|>|<|>=|<=";
    private final String REGEX_LOGICAL = "&&|\\|\\||!";
    private final String REGEX_BITWISE = "\\|[^\\|]|\\^|~|<<|>>|<<<|>>>";
    private final String RGEX_MISCELLANEOUS = ",|->|\\.|::";
    private final String REGEX_ASSIGNMENT = "\\+=|-=|\\*=|[^<>]=|>>>=|\\|=|&=|%=|<<=|>>=|\\^=";
    private final String REGEX_KEY = "void|double|int|float|String|printf|println|cout|cin|if|for|while|do-while|switch|case";
    private final String REGEX_MANIPULATORS = "endl|\\n";
    private final String REGEX_TEXT = "\".*?\"";
    private final String REGEX_VARIABLE = "[a-z_]\\w*";
    private final String REGEX_NUM = "\\d+";
    private final String REGEX_REMOVE = "public|static|else|try|return|new|delete|throw|throws";
    
    private final Pattern pattern = Pattern.compile("(" + REGEX_ARITHMETIC + "|" + REGEX_RELATION + "|" + REGEX_LOGICAL + "|" + REGEX_BITWISE + "|" + RGEX_MISCELLANEOUS + "|" + REGEX_MANIPULATORS + "|" + REGEX_TEXT + "|" + REGEX_VARIABLE + "|" + REGEX_NUM + ")");
    private final Pattern removePattern = Pattern.compile("(" + REGEX_REMOVE + ")");
    private final Pattern doublePattern = Pattern.compile("(" + REGEX_TWO + ")");
    
    public cs_calculation(){
        
    }
       
    public void csCalculate(){
        
        for(int i=0; i < Analysis.resultSet.size(); i++){
            
            int csValue = 0;
            
            String currentLine = Analysis.resultSet.get(i).getLineContent();
            
            final Matcher matcher = pattern.matcher(currentLine);
            final Matcher removeMatcher = removePattern.matcher(currentLine);
            final Matcher doubleMatcher = doublePattern.matcher(currentLine);
            
            while(matcher.find()){
                csValue++;
            }
            
            while(removeMatcher.find()){
                csValue--;
            }
            
            while(doubleMatcher.find()){
                
                csValue =+ 2;
            }
            
            
            System.out.println("CS Value: " + csValue);
            
            Analysis.resultSet.get(i).setCsValue(csValue);
            
            
        }
        
        
    }
    
}
