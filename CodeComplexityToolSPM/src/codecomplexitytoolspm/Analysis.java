/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexitytoolspm;

import Cr_Calculation.cr_calculation;
import TWCalculations.TWCalculator;
import cnC_calculation.CncCalculation;
import static codecomplexitytoolspm.AnalysisHistory.fileID;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;
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
    private int fileID = 0;
    private boolean fileUploadStatus = false;
    private static MainUI MU = null;

    public static ArrayList<ProgramStatement> resultSet = null;


    /**
     * Creates new form MainUI
     */
    public Analysis(String FilePath, String copyText, MainUI mu) {
        initComponents();
        
        this.MU = mu;

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
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
        name.setText("Code Analysis Of " + fileName);
        
        displayFile();
        

    }
    
    final public void readFromText(){
        System.out.println("Reading from text");
    }
    
    private final void closeApplication(){
        
        MU.enable(true);
        this.dispose();
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
        
        /*
        //Inflate Details to JTable
        for (ProgramStatement ps : resultSet) {
           Object[] row = {ps.getLineNumber(),ps.getLineContent(),ps.getCsValue(), ps.getCtcValue(),ps.getCncValue(), ps.getCiValue(), ps.getTwValue(), ps.getCpsValue(), ps.getCrValue()};
           
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                TableColumnModel columnModel = jTable1.getColumnModel();
          
                model.addRow(row);
        } */
        
        if(fileUploadStatus == false){
            uploadFile();
        }
        
        fileUploadStatus = true;
        
        
        final String sqlStatement1 = "select line_number as 'Line Number', line_content as 'Program Statement', cs, ctc, cnc, ci, tw, cps, cr from file_content where file_id=? order by line_number";
        try {
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            ps1.setInt(1, fileID);
            
            final ResultSet rs1 = ps1.executeQuery();
            
            jTable1.setModel(DbUtils.resultSetToTableModel(rs1));
           
            //change row height
            jTable1.setRowHeight(30);
            
            
            TableColumnModel columnModel = jTable1.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(500);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
            columnModel.getColumn(7).setPreferredWidth(5);
            columnModel.getColumn(8).setPreferredWidth(5);
            
        } catch (SQLException ex) {
            Logger.getLogger(AnalysisHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }
    
    final public void uploadFile(){
        
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
        name = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setResizable(false);

        JPannelMain.setBackground(new java.awt.Color(30, 31, 41));
        JPannelMain.setForeground(new java.awt.Color(30, 31, 41));

        name.setFont(new java.awt.Font("DejaVu Sans Light", 1, 24)); // NOI18N
        name.setForeground(new java.awt.Color(241, 241, 241));
        name.setText("Code Analysis");

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

        jTable1.setBackground(new java.awt.Color(30, 31, 41));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout JPannelMainLayout = new javax.swing.GroupLayout(JPannelMain);
        JPannelMain.setLayout(JPannelMainLayout);
        JPannelMainLayout.setHorizontalGroup(
            JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(JPannelMainLayout.createSequentialGroup()
                        .addComponent(name)
                        .addGap(442, 442, 442)
                        .addComponent(jToggleButton2)
                        .addGap(30, 30, 30)
                        .addComponent(jToggleButton1)
                        .addGap(50, 50, 50)
                        .addComponent(updateButton)
                        .addGap(0, 437, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JPannelMainLayout.setVerticalGroup(
            JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButton2)
                    .addComponent(updateButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
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

        displayFile();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        
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
                new Analysis(filePath, copyText, MU).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPannelMain;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JLabel name;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
