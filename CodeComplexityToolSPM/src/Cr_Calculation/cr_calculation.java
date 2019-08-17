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
    
    final private String methodREGX = "(public|private|static|protected) ([A-Za-z]+) ([A-Za-z0-9]+)";
    final private ArrayList<String> switchStatements = new ArrayList<String>();
    private ArrayList<ProgramStatement> copyResultSet = new ArrayList<ProgramStatement>(Analysis.resultSet.size());   
    private boolean switchFound = false;
    private int bracketCounter = 0;
    
    public cr_calculation(){
        copyArrayList();
    } 

    public void calculateCtc(){

        Pattern ifPattern = Pattern.compile(ifREGX);
        Pattern loopPattern = Pattern.compile(loopREGX);
        Pattern lgOperatorsPattern = Pattern.compile(operatorREGX);
        Pattern catchPattern = Pattern.compile(catchREGX);
        Pattern switchPattern = Pattern.compile(switchREGX);
        Pattern casePattern = Pattern.compile(caseREGX);
        Pattern openBracketPattern = Pattern.compile(openBracketREGX);
        Pattern closeBracketPattern = Pattern.compile(closeBracketREGX);
        

        for(int i=0; i < Analysis.resultSet.size(); i++){
            
            int ctcValue = 0;
            int logMatchCount = 0;

            final String currentLine = Analysis.resultSet.get(i).getLineContent();
            System.out.println(currentLine);

            Matcher ifStatementMatcher = ifPattern.matcher(currentLine);
            Matcher logOperatorsMatcher = lgOperatorsPattern.matcher(currentLine);
            Matcher loopMatcher = loopPattern.matcher(currentLine);
            Matcher catchMatcher = catchPattern.matcher(currentLine);
            Matcher switchMatcher = switchPattern.matcher(currentLine);
            

            //Check if the statement contains a if statement
            if(ifStatementMatcher.find()){

                System.out.println("If match found");

                while(logOperatorsMatcher.find()){
                    logMatchCount++;
                }

                System.out.println("Log match count: " + logMatchCount);
                ctcValue = logMatchCount + 1;
                
                Analysis.resultSet.get(i).setCtcValue(ctcValue);
                
            }
            else if(loopMatcher.find()) { //Check if the statement contains a do | while | for loop

                System.out.println("Loop match found");

                while(logOperatorsMatcher.find()){
                    logMatchCount++;
                }

                System.out.println("Log match count: " + logMatchCount);
                ctcValue = ( logMatchCount + 1 ) * 2;
                Analysis.resultSet.get(i).setCtcValue(ctcValue);
            }
            else if(catchMatcher.find()){
                
                ctcValue = 1;
                Analysis.resultSet.get(i).setCtcValue(ctcValue);
            }
            else if(switchMatcher.find()){
                
                System.out.println("Switch detected");
                
                int bracketCounter = 0;
                int caseCounter = 0;
                ArrayList<Integer> ps = new ArrayList<Integer>();
                
                ps.add(i);
                
                for(int k=i; k < copyResultSet.size(); k++){
                    
                    String currentLine1 = copyResultSet.get(k).getLineContent();
                    System.out.println("Line " + k + "- " + currentLine1);
                    
                    Matcher caseMatcher = casePattern.matcher(currentLine1);
                    Matcher openBracketMatcher = openBracketPattern.matcher(currentLine1);
                    Matcher closeBracketMatcher = closeBracketPattern.matcher(currentLine1);
                    
                    if(openBracketMatcher.find()){
                        bracketCounter++;
                    }
                    
                    if(closeBracketMatcher.find() && bracketCounter > 1){
                        bracketCounter--;
                    }
                    
                    if(caseMatcher.find()){
                        caseCounter++;
                        ps.add(k);
                    }
                    
                    if(closeBracketMatcher.find() && bracketCounter == 1){
                        break;
                    }
                    
                }
                
                for(int j=0; j < ps.size(); j++){
                    
                    Analysis.resultSet.get(ps.get(j)).setCtcValue(caseCounter);
                }
                 
            }
            
            
            System.out.println("CTC VALUE: " + ctcValue);
        }
    }
    
    public void copyArrayList(){
        
        for(int i=0; i < Analysis.resultSet.size(); i++){
            
            copyResultSet.add(Analysis.resultSet.get(i));
        }
    }
    
}
