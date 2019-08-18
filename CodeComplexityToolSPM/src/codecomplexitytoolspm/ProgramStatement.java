/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexitytoolspm;

/**
 *
 * @author pasindu
 */
public class ProgramStatement {
    
    private int lineNumber;
    private String lineContent;
    private int csValue;
    private int ctcValue;
    private int cncValue;
    private int ciValue;
    private int twValue;
    private int cpsValue;
    private int crValue;
    private int cpValue;

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLineContent() {
        return lineContent;
    }

    public int getCsValue() {
        return csValue;
    }

    public int getCtcValue() {
        return ctcValue;
    }

    public int getCncValue() {
        return cncValue;
    }

    public int getCiValue() {
        return ciValue;
    }

    public int getTwValue() {
        return twValue;
    }

    public int getCpsValue() {
        return cpsValue;
    }

    public int getCrValue() {
        return crValue;
    }
    
    public int getCpValue(){
        return cpValue;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setLineContent(String lineContent) {
        this.lineContent = lineContent;
    }

    public void setCsValue(int csValue) {
        this.csValue = csValue;
    }

    public void setCtcValue(int ctcValue) {
        this.ctcValue = ctcValue;
    }

    public void setCncValue(int cncValue) {
        this.cncValue = cncValue;
    }

    public void setCiValue(int ciValue) {
        this.ciValue = ciValue;
    }

    public void setTwValue(int twValue) {
        this.twValue = twValue;
    }

    public void setCpsValue(int cpsValue) {
        this.cpsValue = cpsValue;
    }
    
    public void setCpValue(int cpValue){
        this.cpValue=cpValue;
    }

    public void setCrValue(int crValue) {
        this.crValue = crValue;
    }
    
    
    
}
