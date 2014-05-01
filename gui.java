/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Alan Wang and Ben Scharfstein and Evan Brown
 */

import java.util.Random;
import javax.swing.JTextArea;
import java.awt.*;

public class Gui extends javax.swing.JDialog {

    /**
     * Creates new form CS51JDialog
     */
    public Gui(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        textToEncrypt = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        decryptButton = new javax.swing.JButton();
        textToDecrypt = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        encryptButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        encryptedText = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        decryptedText = new javax.swing.JTextArea();
        cipherSelect = new javax.swing.JComboBox();
        textCopyButton = new javax.swing.JButton();
        cipherInteger = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textToEncrypt.setColumns(40);
        textToEncrypt.setRows(20);
        textToEncrypt.setText("textToEncrypt");
        textToEncrypt.setLineWrap(true);
        textToEncrypt.setWrapStyleWord(true);
        jScrollPane3.setViewportView(textToEncrypt);

        decryptButton.setText("Decrypt");
        decryptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptButtonActionPerformed(evt);
            }
        });

        textToDecrypt.setColumns(40);
        textToDecrypt.setRows(20);
        textToDecrypt.setText("textToDecrypt");
        textToDecrypt.setLineWrap(true);
        textToDecrypt.setWrapStyleWord(true);
        jScrollPane4.setViewportView(textToDecrypt);


        encryptButton.setText("Encrypt");
        encryptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptButtonActionPerformed(evt);
            }
        });

        textCopyButton.setText("Copy Text");
        textCopyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCopyButtonActionPerformed(evt);
            }
        });

        encryptedText.setColumns(40);
        encryptedText.setRows(20);
        encryptedText.setLineWrap(true);
        encryptedText.setWrapStyleWord(true);
        encryptedText.setEditable(false);
        jScrollPane1.setViewportView(encryptedText);

        decryptedText.setColumns(40);
        decryptedText.setRows(20);
        decryptedText.setLineWrap(true);
        decryptedText.setWrapStyleWord(true);
        decryptedText.setEditable(false);
        jScrollPane2.setViewportView(decryptedText);

        cipherSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rando", "Atbash", "Caesar"}));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cipherSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cipherInteger, 10, 30, 30)
                        .addGap(18, 18, 18)
                        .addComponent(encryptButton))
                    //.addComponent(textToEncrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textCopyButton)
                        .addGap(18, 18, 18)
                        .addComponent(decryptButton))
                    // .addComponent(textToDecrypt, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap(37, Short.MAX_VALUE)
                )
        );


        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    // .addComponent(textToEncrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    // .addComponent(textToDecrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decryptButton)
                    .addComponent(encryptButton)
                    .addComponent(textCopyButton)
                    .addComponent(cipherInteger, 10, 30, 30)
                    .addComponent(cipherSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                // .addContainerGap()
                // .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addComponent(jScrollPane1))
                .addContainerGap(37, Short.MAX_VALUE)
                )
        );
        pack();

    }// </editold>
    



    private void decryptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        decryptedText.setText("Thinking...");
        decryptedText.update(decryptedText.getGraphics());
        String[] decryptions = Decrypt.decrypt(Encrypt.clean(textToDecrypt.getText()));
        for (int i = 0; i < 50; i++) {
            String toPrint = decryptions[i];
            if (toPrint == "") break;
            if (toPrint != " ") {
                decryptedText.setText(toPrint);
                decryptedText.update(decryptedText.getGraphics());
                decryptedText.setCaretPosition(0);
                try {
                    Thread.sleep(500);
                }
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void encryptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String eText = ""; // Encrypt.shift(textToEncrypt.getText(), 1);
        int v = 0;
        try{
            String comboValue = cipherSelect.getSelectedItem().toString();

            if (new String("Rando").equals(comboValue) == true)
                eText = Encrypt.rando(textToEncrypt.getText());
            else{
                v = Integer.parseInt(cipherInteger.getText());
                if (v < 0 || v > 26)
                    throw new IllegalArgumentException();

                if (new String("Caesar").equals(comboValue) == true)
                    eText = Encrypt.shift(textToEncrypt.getText(), v);
                else 
                    eText = Encrypt.atbash(textToEncrypt.getText(), v);                
            }

         } catch(Exception e) {
            eText = "Please enter an integer between 0 and 26.";
        }



        encryptedText.setText("");
        encryptedText.append(eText);
        encryptedText.setCaretPosition(0);
    }

    private void textCopyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        textToDecrypt.setText(encryptedText.getText());
    }

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
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Gui dialog = new Gui(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });

    }

    // Variables declaration                   
    private javax.swing.JButton decryptButton;
    private javax.swing.JButton encryptButton;
    private javax.swing.JButton textCopyButton;    
    private javax.swing.JComboBox cipherSelect;
    private javax.swing.JTextField cipherInteger;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea encryptedText;
    private javax.swing.JTextArea decryptedText;
    private javax.swing.JTextArea textToEncrypt;
    private javax.swing.JTextArea textToDecrypt;
    // End of variables declaration     
}

