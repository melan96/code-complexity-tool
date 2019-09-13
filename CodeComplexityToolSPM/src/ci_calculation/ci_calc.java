package ci_calculation;


import codecomplexitytoolspm.Analysis;
import static codecomplexitytoolspm.Analysis.resultSet;
import codecomplexitytoolspm.ProgramStatement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ci_calc {

    public static String getStringBetweenTwoChars(String input, String startChar, String endChar) {
        try {
            int start = input.indexOf(startChar);
            if (start != -1) {
                int end = input.indexOf(endChar, start + startChar.length());
                if (end != -1) {
                    return input.substring(start + startChar.length(), end);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input; // return null; || return "" ;
    }

    public static int calc_ci() throws IOException {
        File f1 = new File(Analysis.filePath); //Creation of File Descriptor for input file
        String rgex1="(public|private|internal|protected)\\s[c]lass\\s([^\\s]+)\\s:";
        String rgex2="(public|private|internal|protected)";
        Pattern p = Pattern.compile(rgex1);
        Pattern p1 = Pattern.compile(rgex2);
        String[] words = null;
        String[] words1 = null;//Intialize the word Array
        
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        
        String s;        
        String start_char = "implements ";
        String end_char = "{";
        String input = "implements";
        String input1 = "extends";// Input word to be searched
        String input2 = "class";
        
        int count = 0;   //Intialize the word to zero
        int ci=0;
        int cvalue=0;
        
            
        while ((s = br.readLine()) != null) //Reading Content from the file
        {
            Matcher m = p.matcher(s);
            
            
            if(m.find()){
                String output = getStringBetweenTwoChars(s, ": ", "{");
                words1 = output.split(" ");
                count++;
                    for (String word1 : words1) {
                        Matcher m1 = p1.matcher(word1);
                        if(!m1.find()){
                            count++;
                            System.out.println("WORD::::::::::"+word1);
                        }
                           //If Present increase the count by one                 
                    }
            }
            
            else{
                words = s.split(" ");  //Split the word using space
                //String output = getStringBetweenTwoChars(s,start_char,end_char);
                ///words = output.split(" ");
                for (String word : words) {
                    if (word.equals(input)) //Search for the given word
                    {
                        String output = getStringBetweenTwoChars(s, start_char, end_char);
                        words1 = output.split(" ");
                        for (String word1 : words1) {
                            count++;    //If Present increase the count by one                 
                        }
                    }
                    if (word.equals(input2)) //Search for the given word
                    {
                        count++;
                    }
                    if (word.equals(input1)) {
                        count = count + 2;
                    }

                }
            }
            int cci = count + 1;//initializing the cci value
            ci=cci;

        }
        if (count != 0) //Check for count not equal to zero
        {
            System.out.println("Ci VALUE:: " + ci );

        } else {
            System.out.println("Wrong Ci Value");
        }
          System.out.println("///////////////CI C++ VALE::"+cvalue+"::////////////");
        fr.close();
        //update ci value to global result set
        
        for(ProgramStatement ps : resultSet){
            ps.setCiValue(ci);
            //System.out.println("CI value:"+ps.getCiValue());
        }
        return ci;
    }

    public static void calc_line() throws IOException {
        File f1 = new File(Analysis.filePath); //Creation of File Descriptor for input file
        String[] words = null;
        String[] words1 = null;//Intialize the word Array
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        //String a=br.lines().collect(Collectors.joining());
        //String b=a.replaceAll("\"/\\\\*(?:.|[\\\\n\\\\r])*?\\\\*/\"","");
        //System.out.println("removed new file//  " + b);
        String s;

        int count = 0;   //Intialize the word to zero
        int count1 = 0;

        while ((s = br.readLine()) != null) //Reading Content from the file
        {
            words = s.split(" ");  //Split the word using space

            innerloop:
            for (String word : words) {
                if (word.equals("}") || word.equals("class") || word.equals("else {") || word.equals("import") || word.equals("package") || s.isEmpty())// || word.equals("} catch")) //Search for the given word
                {
                    //System.out.println("SKIPPING  " + s);
                    count1++;
                }
            }
            count++;
        }
        int total = count - count1;
        if (total != 0) //Check for count not equal to zero
        {
            //System.out.println("Line count" + total);
        } else {
            //System.out.println("No lines");
        }

        fr.close();
    }
}
