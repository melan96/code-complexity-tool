package ci_calculation;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class ci_calc implements paper11, paper22 {

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

    public static void calc_ci() throws IOException {
        File f1 = new File("D:\\SLIIT\\SPM\\Project\\code-complexity-tool\\CodeComplexityToolSPM\\src\\ci_calculation\\ci_calc.java"); //Creation of File Descriptor for input file
        String[] words = null;
        String[] words1 = null;//Intialize the word Array
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;
        String start_char = "implements ";
        String end_char = "{";

        String input = "implements";
        String input1 = "extends";// Input word to be searched
        int count = 0;   //Intialize the word to zero
        int count1 = 0;
        while ((s = br.readLine()) != null) //Reading Content from the file
        {
            System.out.println("S is equal to  :" + s);
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
                        System.out.println("word/////////" + word + " ////////////////");
                        System.out.println("word/////////" + output + " ////////////////");
                    }
                }
                if (word.equals(input1)) {
                    count = 2;
                }

            }
            count1++;
            //System.out.println("/////////////" + output + " ////////////////");
        }
        if (count != 0) //Check for count not equal to zero
        {
            System.out.println("The given word is present for " + count + " Times in the file");

        } else {
            System.out.println("The given word is not present in the file");
        }

        fr.close();
    }

    public static void calc_line() throws IOException {
        File f1 = new File("D:\\SLIIT\\SPM\\Project\\code-complexity-tool\\CodeComplexityToolSPM\\src\\ci_calculation\\ci_calc.java"); //Creation of File Descriptor for input file
        String[] words = null;
        String[] words1 = null;//Intialize the word Array
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        //String a=br.lines().collect(Collectors.joining());
        //String b=a.replaceAll("\"/\\\\*(?:.|[\\\\n\\\\r])*?\\\\*/\"","");
        //System.out.println("removed new file//  " + b);
        String s;

        int count = 0;   //Intialize the word to zero
        int counta = 0;

        while ((s = br.readLine()) != null) //Reading Content from the file
        {
            words = s.split(" ");  //Split the word using space

            innerloop:
            for (String word : words) {
                if (word.equals("}") || word.equals("class") || word.equals("else {") || word.equals("import") || word.equals("package") || s.isEmpty())// || word.equals("} catch")) //Search for the given word
                {
                    System.out.println("SKIPPING  " + s);
                    //continue innerloop;
                    counta++;
                }
            }
            count++;
        }
        int total = count - counta;
        if (total != 0) //Check for count not equal to zero
        {
            //System.out.println("The given word is present for " + count + " Times in the file");
            System.out.println("Line count" + total + " Times in the file");
        } else {
            System.out.println("No lines");
        }

        fr.close();
    }

    public static void pdf() {
        try {
            Document d1=new Document();
            PdfWriter.getInstance(d1,new FileOutputStream("D:\\DEV\\udith.pdf"));
            d1.open();
            for(int i=0;i<3;i++){
            d1.add(new Paragraph("example"));}
            d1.close();
        } catch (Exception e) {
        }
        System.out.println("PDF EXPORTED");
    }

    public static void main(String[] args) throws IOException {

        calc_ci();
        calc_line();
        pdf();
    }
}
