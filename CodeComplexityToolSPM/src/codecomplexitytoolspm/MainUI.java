/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexitytoolspm;

import static codecomplexitytoolspm.Analysis.resultSet;
import database_management.DBConnection;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author melan
 */
public class MainUI extends javax.swing.JFrame {
    
    private Dimension dimension = null;
    public static ButtonGroup g = new ButtonGroup();
    private Connection connection = null;

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();
        
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
       
        g.add(fileradiobutton);
        g.add(textradiobutton);
        
        try {
            connection = (Connection) DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        codetextarea.setEnabled(false);      
        recentHistoryButton.setEnabled(false);
        
        recentHistoryList.setFixedCellHeight(50);
        recentHistoryList.setFixedCellWidth(100);
       
        
        //add allow listener
        fileradiobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println("FILE READING");
                codetextarea.setEnabled(false);
                filePathTextField.setEnabled(true);
                setFilePathButton.setEnabled(true);

            }
        });
        
        recentHistoryList.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                recentHistoryButton.setEnabled(true);
            }
            
            
        });
        
        //add allow listener
        textradiobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println("TEXT READING");
                filePathTextField.setEnabled(false);
                setFilePathButton.setEnabled(false);
                codetextarea.setEnabled(true);

            }
        });
        
        
        
        fileradiobutton.setSelected(true);
        
        loadRecentHistory();
        
        //codetextarea.enable(false);
 
    }
    
    final public void loadRecentHistory(){
        
        int fileID = 0;
        String fileName = null;
        Date date = null;
        
        try {
            
            String sqlStatement1 = "select * from files order by date desc limit 10;";
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
    
            final ResultSet rs1 = ps1.executeQuery();
            
            recentHistoryList.removeAll();
            DefaultListModel list = new DefaultListModel();
            list.clear();
            
            while(rs1.next()){
                
                fileID = rs1.getInt("file_id");
                fileName = rs1.getString("file_name");
                date = rs1.getDate("date");
                
                String listValue = "               " + fileID + "               " + fileName + "               " + date;
                
                list.addElement(listValue);
                
            }

            recentHistoryList.setModel(list);
            ps1.close();
            rs1.close();
            
 
        } catch (SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error - Problem encountered when retreiving recent history", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        JPannelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fileradiobutton = new javax.swing.JRadioButton();
        textradiobutton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        codetextarea = new javax.swing.JTextArea();
        filePathTextField = new javax.swing.JTextField();
        importFileButton = new javax.swing.JButton();
        setFilePathButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        recentHistoryList = new javax.swing.JList<>();
        recentHistoryButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setResizable(false);

        JPannelMain.setBackground(new java.awt.Color(30, 31, 41));
        JPannelMain.setForeground(new java.awt.Color(30, 31, 41));

        jPanel1.setBackground(new java.awt.Color(30, 31, 41));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Import Option");

        fileradiobutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fileradiobutton.setForeground(new java.awt.Color(255, 255, 255));
        fileradiobutton.setText("Import as a file");
        fileradiobutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileradiobuttonActionPerformed(evt);
            }
        });

        textradiobutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textradiobutton.setForeground(new java.awt.Color(255, 255, 255));
        textradiobutton.setText("Imort as text");
        textradiobutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textradiobuttonActionPerformed(evt);
            }
        });

        codetextarea.setColumns(20);
        codetextarea.setRows(5);
        jScrollPane1.setViewportView(codetextarea);

        filePathTextField.setEditable(false);
        filePathTextField.setBackground(new java.awt.Color(255, 255, 255));
        filePathTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filePathTextFieldActionPerformed(evt);
            }
        });

        importFileButton.setBackground(new java.awt.Color(119, 255, 94));
        importFileButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        importFileButton.setForeground(new java.awt.Color(1, 1, 1));
        importFileButton.setText("Import File");
        importFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importFileButtonActionPerformed(evt);
            }
        });

        setFilePathButton.setBackground(new java.awt.Color(119, 255, 94));
        setFilePathButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        setFilePathButton.setForeground(new java.awt.Color(1, 1, 1));
        setFilePathButton.setText("Set File Path");
        setFilePathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setFilePathButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(filePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(setFilePathButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileradiobutton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(textradiobutton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(importFileButton)
                .addGap(350, 350, 350))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fileradiobutton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textradiobutton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setFilePathButton))
                .addGap(78, 78, 78)
                .addComponent(importFileButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(30, 31, 41));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        recentHistoryList.setBackground(new java.awt.Color(30, 31, 41));
        recentHistoryList.setBorder(new javax.swing.border.MatteBorder(null));
        recentHistoryList.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        recentHistoryList.setForeground(new java.awt.Color(255, 255, 255));
        recentHistoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        recentHistoryList.setAlignmentX(1.0F);
        recentHistoryList.setSelectionBackground(new java.awt.Color(119, 255, 94));
        recentHistoryList.setVisibleRowCount(10);
        jScrollPane3.setViewportView(recentHistoryList);
        recentHistoryList.getAccessibleContext().setAccessibleDescription("");

        recentHistoryButton.setBackground(new java.awt.Color(119, 255, 94));
        recentHistoryButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        recentHistoryButton.setForeground(new java.awt.Color(1, 1, 1));
        recentHistoryButton.setText("View Report");
        recentHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recentHistoryButtonActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(30, 31, 41));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Recent History");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 322, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(325, 325, 325))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recentHistoryButton)
                .addGap(325, 325, 325))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(recentHistoryButton)
                .addGap(37, 37, 37))
        );

        jPanel3.setBackground(new java.awt.Color(30, 31, 41));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Light", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 241, 241));
        jLabel1.setText("Code Complexity Tool");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(661, 661, 661)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout JPannelMainLayout = new javax.swing.GroupLayout(JPannelMain);
        JPannelMain.setLayout(JPannelMainLayout);
        JPannelMainLayout.setHorizontalGroup(
            JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JPannelMainLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JPannelMainLayout.setVerticalGroup(
            JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPannelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JPannelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
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

    private void filePathTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filePathTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filePathTextFieldActionPerformed

    private void setFilePathButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setFilePathButtonActionPerformed
        
        
        final JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);

        try{
            final File file = fc.getSelectedFile();
            String path = file.getAbsolutePath();
            path = path.replace('\\', '/');

            filePathTextField.setText(path);

        }
        catch(Exception e){
            JOptionPane newOptionPane = new JOptionPane("Exited without selecting file", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);

        }
    }//GEN-LAST:event_setFilePathButtonActionPerformed

    private void importFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importFileButtonActionPerformed
        
        if(fileradiobutton.isSelected()){
            final String filePath = filePathTextField.getText();
        
            if(filePath.isEmpty()){
                JOptionPane newOptionPane = new JOptionPane("Please select the file path to import", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true); 
            }
            else{
                final Analysis analysis = new Analysis(filePath, null);
                this.enable(false);
                analysis.setVisible(true);
            }
        }
        else if(textradiobutton.isSelected()){
            
            if(codetextarea.getText().isEmpty()){
                
                JOptionPane newOptionPane = new JOptionPane("Please add the code to be imported", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
            }
            else{
                final Analysis analysis = new Analysis(null, codetextarea.getText());
                this.enable(false);
                analysis.setVisible(true);
            }
        }
        
    }//GEN-LAST:event_importFileButtonActionPerformed

    private void textradiobuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textradiobuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textradiobuttonActionPerformed

    private void fileradiobuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileradiobuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileradiobuttonActionPerformed

    private void recentHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recentHistoryButtonActionPerformed
        System.out.println("Selected Value: " + recentHistoryList.getSelectedValue());
        String selectedValue = recentHistoryList.getSelectedValue();
        String[] stringArray = selectedValue.split("               ");
        System.out.println("File ID: " + stringArray[1]);
        System.out.println("File Name: " + stringArray[2]);
        final AnalysisHistory ah = new AnalysisHistory(stringArray[1],stringArray[2]);
        this.enable(false);
        ah.setVisible(true);
        
    }//GEN-LAST:event_recentHistoryButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPannelMain;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea codetextarea;
    private javax.swing.JTextField filePathTextField;
    private javax.swing.JRadioButton fileradiobutton;
    private javax.swing.JButton importFileButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton recentHistoryButton;
    private javax.swing.JList<String> recentHistoryList;
    private javax.swing.JButton setFilePathButton;
    private javax.swing.JRadioButton textradiobutton;
    // End of variables declaration//GEN-END:variables
}
