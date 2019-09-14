/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cr_Calculation;

import codecomplexitytoolspm.Analysis;
import codecomplexitytoolspm.ProgramStatement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pasindu
 */ 
public class cr_calculation {
    
    final private String methodREGX = "\\b(public|private)\\s*\\w+\\s*\\w+\\s*\\w+\\(.*\\)";
    final private String openBracketREGX = "\\{";
    final private String closeBracketREGX = "\\}";
    final private String methodName = "\\b\\w+\\(";
    private boolean recursionDetected = false;
    private int start = 0;
    private int end = 0;
    private ArrayList<ProgramStatement> copyResultSet = new ArrayList<ProgramStatement>(Analysis.resultSet.size());   
    
    public cr_calculation(){
        copyArrayList();
    } 

    public void calculateCr(){

        Pattern methodPattern = Pattern.compile(methodREGX);
        
        for(int i=0; i < Analysis.resultSet.size(); i++){
            
            String currentLine = Analysis.resultSet.get(i).getLineContent();

            Matcher methodMatcher = methodPattern.matcher(currentLine);
            
            if(methodMatcher.find()){
                
                //Get the method name
                
                start = i;
                Pattern name = Pattern.compile(methodName);
                Matcher methodNameMatcher = name.matcher(currentLine);
                String methodName = null;
                String newMethodName = null;

                if(methodNameMatcher.find()){

                    methodName = methodNameMatcher.group();

                    int index = methodName.indexOf("(");
                    newMethodName = methodName.substring(0, index);

                }
                
                final String recursionREGX = "\\b" + newMethodName + "+\\(.+\\)";
                Pattern recursion = Pattern.compile(recursionREGX);
                
                int bracketCounter = 0;                
                
                for(int k=i; k < copyResultSet.size(); k++){
                   
                    String newCurrentLine = copyResultSet.get(k).getLineContent();
                    Matcher recursionMatcher = recursion.matcher(newCurrentLine);
                    
                    Pattern openBracket = Pattern.compile(openBracketREGX);
                    Pattern closeBracket = Pattern.compile(closeBracketREGX);
                    
                    Matcher openBracketMatcher = openBracket.matcher(newCurrentLine);
                    Matcher closeBracketMatcher = closeBracket.matcher(newCurrentLine);
                    
                    
                    if(openBracketMatcher.find()){
                        System.out.println("Increasing bc");
                        bracketCounter++;
                    }
                    
                    if(closeBracketMatcher.find()){
                        
                        System.out.println("Decreasing bc");
                        bracketCounter--;
                    }
                    
                  
                    if(bracketCounter == 0){
                        end = k;
                        break;
                    }
                    
                    
                    if(recursionMatcher.find() && k!=i){
                        System.out.println("Recursion Detected");
                        recursionDetected = true;
                    }
                }
                
                
                updateCrValue();
                recursionDetected=false;
                
                
            }
      
        }
    }
    
    public void copyArrayList(){
        
        for(int i=0; i < Analysis.resultSet.size(); i++){
            
            copyResultSet.add(Analysis.resultSet.get(i));
        }
    } 
    
    
    public void updateCrValue(){

        
        if(recursionDetected==true){
            for(int i=start; i <= end; i++){
                
                int cpc = Analysis.resultSet.get(i).getCpsValue();
                Analysis.resultSet.get(i).setCrValue(cpc);
            
            }
        }
    } 
} 
