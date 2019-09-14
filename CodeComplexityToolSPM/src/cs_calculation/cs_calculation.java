/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_calculation;

import codecomplexitytoolspm.Analysis;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class cs_calculation {
    
    public static final String REGEX_TWO = "new|delete|throws|throw";
    public static final String REGEX_ARITHMETIC = "[\\s]\\+[\\s]|[\\s]\\-[\\s]|[\\s]\\*[\\s]|[\\s]\\/[\\s]|[\\s]\\%[\\s]|\\+[\\+]|\\-[\\-]";
    public static final String REGEX_RELATION = "=[\\=]|![\\=]|[\\s]>[\\s]|[\\s]<[\\s]|[\\s]>[\\=]|[\\s]<[\\=]";
    public static final String REGEX_LOGICAL = "&[\\&]|\\|[\\|]|![\\s]";
    public static final String REGEX_BITWISE = "[\\s]\\|[\\s]|<[\\<]|>[\\>]|<[\\<<]|>[\\>>]|[\\s]~[\\s]|[\\s]\\^[\\s]";
    public static final String RGEX_MISCELLANEOUS = ",[\\s]|[\\s]->|::";
    public static final String REGEX_ASSIGNMENT = "[\\s]\\+[\\=]|[\\s]\\-[\\=]|[\\s]\\*[\\=]|[\\s]\\/[\\=]|[\\s]\\&[\\=]";
    public static final String REGEX_MANIPULATORS = "endl|\\n";
    public static final String REGEX_TEXT = "\".*?\"";
    public static final String REGEX_VARIABLE = "[a-z_]\\w*";
    public static final String REGEX_NUM = "\\d+";
    public static final String REGEX_REMOVE = "public|static|else|try|return|new|delete|throws|throw|class";
    public static final String REGEX_IMPORTS = "\\bimport\\b";
    public static final String REGEX_COMMENTS = "[\\/\\/.*]";
    
    
    private final Pattern pattern = Pattern.compile (REGEX_TWO);
    private final Pattern ArithmeticPattern = Pattern.compile (REGEX_ARITHMETIC);
    private final Pattern RelationPattern = Pattern.compile (REGEX_RELATION);
    private final Pattern LogicalPattern = Pattern.compile (REGEX_LOGICAL);
    private final Pattern BitwisePattern = Pattern.compile (REGEX_BITWISE);
    private final Pattern MisPattern = Pattern.compile (RGEX_MISCELLANEOUS);
    private final Pattern AssignmentPattern = Pattern.compile (REGEX_ASSIGNMENT);
    private final Pattern ManuPattern = Pattern.compile (REGEX_MANIPULATORS);
    private final Pattern TextPattern = Pattern.compile (REGEX_TEXT);
    private final Pattern VariablePattern = Pattern.compile (REGEX_VARIABLE);
    private final Pattern NumPattern = Pattern.compile (REGEX_NUM);
    private final Pattern RemovePattern = Pattern.compile (REGEX_REMOVE);
    private final Pattern ImportPattern = Pattern.compile (REGEX_IMPORTS);
    private final Pattern CommentPattern = Pattern.compile (REGEX_COMMENTS);
    
    
   
    
    public cs_calculation(){
        
    }
       
    public void csCalculate(){
        
        System.out.println("Inside cs calculate method");
        
        for(int i=0; i < Analysis.resultSet.size(); i++){
            
            int csValue = 0;
            
            String currentLine = Analysis.resultSet.get(i).getLineContent();
            
            final Matcher matcher = pattern.matcher(currentLine);
            final Matcher arithmeticMatcher = ArithmeticPattern.matcher(currentLine);
            final Matcher relationMatcher = RelationPattern.matcher(currentLine);
            final Matcher logicalMatcher = LogicalPattern.matcher(currentLine);
            final Matcher bitwiseMatcher = BitwisePattern.matcher(currentLine);
            final Matcher misMatcher = MisPattern.matcher(currentLine);
            final Matcher assignmentMatcher = AssignmentPattern.matcher(currentLine);
            final Matcher manuMatcher = ManuPattern.matcher(currentLine);
            final Matcher textMatcher = TextPattern.matcher(currentLine);
            final Matcher variableMatcher = VariablePattern.matcher(currentLine);
            final Matcher numMatcher = NumPattern.matcher(currentLine);
            final Matcher removeMatcher = RemovePattern.matcher(currentLine);
            final Matcher importMatcher = ImportPattern.matcher(currentLine);
             final Matcher commentMatcher = CommentPattern.matcher(currentLine);
           
            
            while(matcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
                csValue++;
            }
            
            while(arithmeticMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
             
            while(relationMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
             
            while(logicalMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(bitwiseMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
             
            while(misMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(assignmentMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(manuMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(textMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(variableMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(numMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue++;
            }
            
            while(removeMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue--;
            }
             
            while(importMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue--;
            }
            
            while(commentMatcher.find()){
              //  System.out.println("svalue increasing");
                csValue--;
            }
            
            
            
            
            
            System.out.println("CS Value: " + csValue);
            
            Analysis.resultSet.get(i).setCsValue(csValue);
            
            
        }
        
        
    }
    
}
