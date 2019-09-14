/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexitytoolspm;

import Cr_Calculation.cr_calculation;
import TWCalculations.TWCalculator;
import cnC_calculation.CncCalculation;
import cs_calculation.cs_calculation;
import ctc_calculation.CtcCalculation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import database_management.DBConnection;

import java.io.FileOutputStream;
import export_pdf.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author melan
 */
public class Analysis extends javax.swing.JFrame {

    private Dimension dimension = null;
    public static String filePath = null;
    private static String copyText = null;
    private Connection connection = null;
    private String fileName  = null;
    private boolean fileUploadStatus = false;

    public static ArrayList<ProgramStatement> resultSet = null;


    /**
     * Creates new form MainUI
     */
    public Analysis(String FilePath, String copyText) {
        initComponents();
        
        

        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
        
        
        try {
            connection = (Connection) DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        if(FilePath == null && copyText != null){
            this.copyText = copyText;
            readFromText();
        }
        else if(FilePath != null && copyText == null){
            this.filePath = FilePath;
            readFromFile();
        }
        
  

        displayFile();
        

    }
    
    final public void readFromText(){
        System.out.println("Reading from text");
    }

    final public void readFromFile() {

        resultSet = new ArrayList<ProgramStatement>();

        try {

            final File file1 = new File(filePath);
            fileName = file1.getName();
            final FileReader fileReader = new FileReader(file1);
            final BufferedReader bufferReader = new BufferedReader(fileReader); //Creation of BufferedReader object
            String line;
            int count = 1;   //Intialize the word to zero

            while ((line = bufferReader.readLine()) != null) //Reading Content from the file
            {
                ProgramStatement ps = new ProgramStatement();
                ps.setLineNumber(count);
                ps.setLineContent(line);

                resultSet.add(ps);

                count++;

            }

            fileReader.close();

        } catch (IOException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    final public void displayFile() {
        
        calculateValues();
        
        for (ProgramStatement ps : resultSet) {
            
            currentCodeTextArea.append(String.valueOf(ps.getLineNumber()));
            currentCodeTextArea.append("\t");
            currentCodeTextArea.append(ps.getLineContent());
            currentCodeTextArea.append("\t"); /*
            currentCodeTextArea.append(String.valueOf(ps.getCncValue())); */
            currentCodeTextArea.append("\t"); 
            currentCodeTextArea.append(String.valueOf(ps.getCtcValue())); 
           /* currentCodeTextArea.append("\t");
            currentCodeTextArea.append(String.valueOf(ps.getCsValue()));
            currentCodeTextArea.append("\t");
            currentCodeTextArea.append(String.valueOf(ps.getCiValue()));
            currentCodeTextArea.append("\t");
            currentCodeTextArea.append(String.valueOf(ps.getCrValue()));
            currentCodeTextArea.append("\t");
            currentCodeTextArea.append(String.valueOf(ps.getTwValue())); */
            currentCodeTextArea.append("\n");

        }
        
        if(fileUploadStatus == false){
            uploadFile();
        }
        
        fileUploadStatus = true;
        
        

    }
    
    final public void uploadFile(){
        
        int fileID = 0;
        
        try {
            
            String sqlStatement1 = "INSERT INTO files(file_name) VALUES(?)";
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            
            ps1.setString(1, fileName);
            
            ps1.executeUpdate();
            
            ps1.close();
            
            String sqlStatement2 = "SELECT * from files where file_name=?";
            PreparedStatement ps2 = connection.prepareStatement(sqlStatement2);
            
            ps2.setString(1, fileName);
            
            final ResultSet rs1 = ps2.executeQuery();
            
            while(rs1.next()){
                fileID = rs1.getInt("file_id");
            }


            ps2.close();
            rs1.close();
            
            
            
            for (ProgramStatement ps : resultSet) {
                
                String sqlStatement3 = "INSERT INTO file_content(file_id, line_number, line_content, cs, ctc, cnc, ci, tw, cps, cr) values(?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps3 = connection.prepareStatement(sqlStatement3);
                
                ps3.setInt(1, fileID);
                ps3.setInt(2, ps.getLineNumber());
                ps3.setString(3, ps.getLineContent());
                ps3.setInt(4, ps.getCsValue());
                ps3.setInt(5, ps.getCtcValue());
                ps3.setInt(6, ps.getCncValue());
                ps3.setInt(7, ps.getCiValue());
                ps3.setInt(8, ps.getTwValue());
                ps3.setInt(9, ps.getCpsValue());
                ps3.setInt(10, ps.getCrValue());
                
                ps3.executeUpdate();
                ps3.close();
                
            }
            
            


        } catch (SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error encountered in file uploading process", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }

    public void calculateCnCValues() {

        //Const init 
        CncCalculation cncCalculation = new CncCalculation();

        ArrayList<Integer> Cnc_units = cncCalculation.coreBracketMapper();

        for (int i = 0; i < Cnc_units.size(); i++) {
            System.out.println("\t" + (i + 1) + " Line has " + Cnc_units.get(i) + " Cnc");
        }

        System.out.println("Total CNC --->  " + cncCalculation.getTotalCncPoints());
    }
    
    public void calculateCsValue(){
        cs_calculation cs = new cs_calculation();
        cs.csCalculate();
    }

    public void calculateCIValue() {
        try {
            ci_calculation.ci_calc.calc_ci();
        } catch (IOException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateCtcValue() {

        CtcCalculation ctcCalc = new CtcCalculation();
        ctcCalc.calculateCtc();

    }

    public void calculateTWValue() {
        TWCalculator twCalc = new TWCalculator();
        twCalc.calculateTWforProgramStatement();

    }
    
    public void calculateCrValue(){
        cr_calculation cr = new cr_calculation();
        cr.calculateCr();
    }
    
    public void calculateValues(){
        calculateCtcValue();
        calculateCnCValues();
        calculateCIValue();
        calculateCsValue();
        calculateTWValue();
        calculateCrValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPannelMain = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        currentCodeTextArea = new javax.swing.JTextArea();
        updateButton = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setResizable(false);

        JPannelMain.setBackground(new java.awt.Color(30, 31, 41));
        JPannelMain.setForeground(new java.awt.Color(30, 31, 41));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Light", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 241, 241));
        jLabel1.setText("Code Analysis");

        currentCodeTextArea.setEditable(false);
        currentCodeTextArea.setBackground(new java.awt.Color(30, 31, 41));
        currentCodeTextArea.setColumns(20);
        currentCodeTextArea.setForeground(new java.awt.Color(255, 255, 255));
        currentCodeTextArea.setRows(5);
        currentCodeTextArea.setTabSize(4);
        currentCodeTextArea.setBorder(null);
        jScrollPane1.setViewportView(currentCodeTextArea);

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Print ");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Export PDF");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPannelMainLayout = new javax.swing.GroupLayout(JPannelMain);
        JPannelMain.setLayout(JPannelMainLayout);
        JPannelMainLayout.setHorizontalGroup(
            JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(updateButton)
                .addGap(259, 259, 259)
                .addComponent(jLabel1)
                .addGap(103, 103, 103)
                .addComponent(jToggleButton2)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton1)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        JPannelMainLayout.setVerticalGroup(
            JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jToggleButton1)
                        .addComponent(jToggleButton2))
                    .addComponent(updateButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPannelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPannelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        currentCodeTextArea.setText(null);
        displayFile();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        try {
            // TODO add your handling code here:
            currentCodeTextArea.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
         //PDf Handling Code - @melan96
        
        
             String TITLE = "TestReport"+new Date().toString();
             String PDF_EXTENSION = ".pdf";
             
             
              ArrayList<ProgramStatement> dataObjList = resultSet;
              
              Document document = null;
        try {
        //Document is not auto-closable hence need to close it separately
            document = new Document(PageSize.A4);            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
                    new File(TITLE + PDF_EXTENSION)));
            HeaderFooter event = new HeaderFooter();
            event.setHeader("Test Report");
            writer.setPageEvent(event);
            document.open();
            PDFCreator.addMetaData(document, TITLE);
            PDFCreator.addTitlePage(document, TITLE);
            PDFCreator.addContent(document, dataObjList);
            
        }catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("FileNotFoundException occurs.." + e.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(null != document){
                document.close();
            }
        }
              
        
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Analysis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Analysis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Analysis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Analysis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Analysis(filePath, copyText).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPannelMain;
    private javax.swing.JTextArea currentCodeTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
