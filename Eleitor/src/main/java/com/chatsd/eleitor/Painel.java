package com.chatsd.eleitor;

public class Painel extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Painel.class.getName());
    private int votoValue;
    private Runnable onVotar;

    
    public Painel() {
        initComponents();
        this.votoValue = 0;
    }
    
    public int getVotoValue(){
        return this.votoValue;
    }
    
    public void setOnVotar(Runnable onVotar) {
        this.onVotar = onVotar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        form = new javax.swing.ButtonGroup();
        textOrientation = new javax.swing.JLabel();
        optionCandidato1 = new javax.swing.JRadioButton();
        optionCandidato2 = new javax.swing.JRadioButton();
        optionCandidato3 = new javax.swing.JRadioButton();
        optionCandidato4 = new javax.swing.JRadioButton();
        optionBranco = new javax.swing.JRadioButton();
        optionNulo = new javax.swing.JRadioButton();
        btnVotar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Urna Eletrônica");

        textOrientation.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textOrientation.setText("Selecione o candidato");
        textOrientation.setName("textOrientation"); // NOI18N

        form.add(optionCandidato1);
        optionCandidato1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        optionCandidato1.setText("Canditato 1");
        optionCandidato1.setName("optionCandidato1"); // NOI18N
        optionCandidato1.addActionListener(this::optionCandidato1ActionPerformed);

        form.add(optionCandidato2);
        optionCandidato2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        optionCandidato2.setText("Canditato 2");
        optionCandidato2.setName("optionCandidato2"); // NOI18N
        optionCandidato2.addActionListener(this::optionCandidato2ActionPerformed);

        form.add(optionCandidato3);
        optionCandidato3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        optionCandidato3.setText("Canditato 3");
        optionCandidato3.setName("optionCandidato3"); // NOI18N
        optionCandidato3.addActionListener(this::optionCandidato3ActionPerformed);

        form.add(optionCandidato4);
        optionCandidato4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        optionCandidato4.setText("Canditato 4");
        optionCandidato4.setName("optionCandidato4"); // NOI18N
        optionCandidato4.addActionListener(this::optionCandidato4ActionPerformed);

        form.add(optionBranco);
        optionBranco.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        optionBranco.setText("Branco");
        optionBranco.setName("optionBranco"); // NOI18N
        optionBranco.addActionListener(this::optionBrancoActionPerformed);

        form.add(optionNulo);
        optionNulo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        optionNulo.setSelected(true);
        optionNulo.setText("Nulo");
        optionNulo.setName("optionNulo"); // NOI18N
        optionNulo.addActionListener(this::optionNuloActionPerformed);

        btnVotar.setText("Votar e aguardar a apuração");
        btnVotar.setName("btnVotar"); // NOI18N
        btnVotar.addActionListener(this::btnVotarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVotar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textOrientation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(optionCandidato2)
                                .addGap(18, 18, 18)
                                .addComponent(optionCandidato4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(optionCandidato1)
                                .addGap(18, 18, 18)
                                .addComponent(optionCandidato3)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(optionNulo)
                            .addComponent(optionBranco))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(textOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(optionCandidato1)
                            .addComponent(optionCandidato3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(optionCandidato2)
                            .addComponent(optionCandidato4)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(optionBranco)
                        .addGap(18, 18, 18)
                        .addComponent(optionNulo)))
                .addGap(26, 26, 26)
                .addComponent(btnVotar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void optionCandidato1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionCandidato1ActionPerformed
        this.votoValue = 1;
    }//GEN-LAST:event_optionCandidato1ActionPerformed

    private void optionCandidato2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionCandidato2ActionPerformed
        this.votoValue = 2;
    }//GEN-LAST:event_optionCandidato2ActionPerformed

    private void optionCandidato3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionCandidato3ActionPerformed
        this.votoValue = 3;
    }//GEN-LAST:event_optionCandidato3ActionPerformed

    private void optionCandidato4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionCandidato4ActionPerformed
        this.votoValue = 4;
    }//GEN-LAST:event_optionCandidato4ActionPerformed

    private void optionBrancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionBrancoActionPerformed
        this.votoValue = 5;
    }//GEN-LAST:event_optionBrancoActionPerformed

    private void optionNuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionNuloActionPerformed
        this.votoValue = 6;
    }//GEN-LAST:event_optionNuloActionPerformed

    private void btnVotarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVotarActionPerformed
        if(this.votoValue == 0) this.votoValue = 6;
        
        this.btnVotar.setText("Aguardando apuração...");
        this.btnVotar.setEnabled(false);

        this.optionCandidato1.setEnabled(false);
        this.optionCandidato2.setEnabled(false);
        this.optionCandidato3.setEnabled(false);
        this.optionCandidato4.setEnabled(false);
        this.optionBranco.setEnabled(false);
        this.optionNulo.setEnabled(false);
        
        if (this.onVotar != null) {
            this.onVotar.run();
        }
    }//GEN-LAST:event_btnVotarActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Painel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVotar;
    private javax.swing.ButtonGroup form;
    private javax.swing.JRadioButton optionBranco;
    private javax.swing.JRadioButton optionCandidato1;
    private javax.swing.JRadioButton optionCandidato2;
    private javax.swing.JRadioButton optionCandidato3;
    private javax.swing.JRadioButton optionCandidato4;
    private javax.swing.JRadioButton optionNulo;
    private javax.swing.JLabel textOrientation;
    // End of variables declaration//GEN-END:variables
}
