package pacote;

import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class FrmChat extends javax.swing.JFrame {

    public String msg = "";
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmChat.class.getName());
    private PnlEmojis pnlEmojis;
    private String ultimaMensagemRecebida = "";

    /**
     * Creates new form frmChat
     */
    public FrmChat() {
        initComponents();
        this.pnlEmojis = new PnlEmojis();
        this.btnEnviar.setEnabled(false);

       this. popUpEmoji.add(this.pnlEmojis);
        
        Thread.startVirtualThread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String msgs;

                    try (Socket client = new Socket("localhost", 6661);
                        ObjectInputStream input = new ObjectInputStream(client.getInputStream())) {
                            msgs = input.readUTF();
                        }
                    
                    if (!msgs.equals(this.ultimaMensagemRecebida)) {
                        String historicoAnterior = this.ultimaMensagemRecebida;
                        String historicoAtual = msgs;

                        javax.swing.SwingUtilities.invokeLater(() -> {
                            javax.swing.JScrollBar scrollBar =
                                    this.scrConversa.getVerticalScrollBar();

                            int posicaoAnterior = scrollBar.getValue();

                            boolean estavaNoFinal = posicaoAnterior + scrollBar.getVisibleAmount() >= scrollBar.getMaximum() - 5;

                            HTMLEditorKit kit = (HTMLEditorKit) this.edtConversa.getEditorKit();

                            HTMLDocument doc = (HTMLDocument) this.edtConversa.getDocument();

                            doc.setBase(getClass().getResource("/pacote/"));

                            try {
                                if (!historicoAnterior.isEmpty() && historicoAtual.startsWith(historicoAnterior)) {

                                    String novasMensagens =  historicoAtual.substring(historicoAnterior.length());

                                    if (!novasMensagens.isEmpty()) {
                                        kit.insertHTML(doc, doc.getLength(), novasMensagens, 0, 0, null);
                                    }

                                } else {
                                    edtConversa.setText(historicoAtual);
                                }
                                
                                javax.swing.SwingUtilities.invokeLater(() -> {
                                    if (estavaNoFinal) {
                                        edtConversa.setCaretPosition(edtConversa.getDocument().getLength() );
                                    } else {
                                        scrollBar.setValue(posicaoAnterior);
                                    }
                                });

                            } catch (Exception e) {
                                logger.log(java.util.logging.Level.WARNING, "Erro ao atualizar o histórico do chat", e);
                            }
                        });
                        this.ultimaMensagemRecebida = historicoAtual;
                    }
                    
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        this.btnEnviar.setEnabled(true);
                    });

                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;

                } catch (Exception error) {
                    logger.log(
                            java.util.logging.Level.WARNING,
                            "Servidor de mensagens indisponível",
                            error
                    );

                    javax.swing.SwingUtilities.invokeLater(() -> {
                        this.btnEnviar.setEnabled(false);

                        this.edtConversa.setText(
                                "<html><body style='font-family:Arial;"
                                + "text-align:center;margin-top:80px;'>"
                                + "<h2 style='color:red;'>"
                                + "Sistema em manutenção"
                                + "</h2>"
                                + "<p>Não foi possível conectar ao servidor.</p>"
                                + "<p>Tente novamente em alguns instantes.</p>"
                                + "</body></html>"
                        );
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });

    }

    public void gerarAndEnviarMsg() {
        this.msg = "";
        this.msg += "<img src='images/" + Util.avatar + "' width='24px' height='24px'> ";
        this.msg += "<font color='" + Util.cor + "'><b>" + Util.nickname + " </b></font>";

        if (selectModo.getSelectedItem().toString().equals("Fala")) {
            this.msg += "fala: " + this.inputTxtMensagem.getText();
        } else if (selectModo.getSelectedItem().toString().equals("Grita")) {
            this.msg += "<b>grita: </b> <font size='+2'>" + this.inputTxtMensagem.getText().toUpperCase() + "</font>";
        } else if (selectModo.getSelectedItem().toString().equals("Sussurra")) {
            this.msg += "<font size='-1'><i>sussura: </i>" + this.inputTxtMensagem.getText().toLowerCase() + "</font>";
        } else if (selectModo.getSelectedItem().toString().equals("Xinga")) {
            this.msg += "<font size='+1' color='red'><b><u>xinga: " + this.inputTxtMensagem.getText().toUpperCase() + "</u></b></font>";
        }

        if (!this.pnlEmojis.getEmojiSelecionado().equals("Nenhum")) {
            this.msg += "<img src='images/" + this.pnlEmojis.getEmojiSelecionado() + ".png' width='15' height='15'>";
        }
        
        if (!this.pnlEmojis.getGifSelecionado().equals("Nenhum")) {
            this.msg += "<img src='images/" + this.pnlEmojis.getGifSelecionado()+ ".gif' width='15' height='15'>";
        }

        this.msg += "<br>";

        ArrayList<String> codigos = new ArrayList<>();
        ArrayList<String> simbolos = new ArrayList<>();

        codigos.add(":-)");
        simbolos.add("&#128512;");
        codigos.add(";-)");
        simbolos.add("&#128521;");
        codigos.add("x-x");
        simbolos.add("&#128565;");
        codigos.add(":-(");
        simbolos.add("&#128551;");
        codigos.add(";-;");
        simbolos.add("&#128557;");

        for (int i = 0; i < codigos.size(); i++) {
            this.msg = this.msg.replace(codigos.get(i), simbolos.get(i));
        }

        inputTxtMensagem.setText("");
        selectModo.setSelectedIndex(0);
        this.pnlEmojis.setEmojiSelecionado("Nenhum");
        this.pnlEmojis.setGifSelecionado("Nenhum");
        
        try {
            Socket client = new Socket("localhost", 6662);
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeUTF(this.msg);
            output.close();
            client.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FrmChat::gerarAndEnviarMsg(): Erro " + e.getMessage());
            e.printStackTrace();
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

        popUpEmoji = new javax.swing.JPopupMenu();
        scrConversa = new javax.swing.JScrollPane();
        edtConversa = new javax.swing.JEditorPane();
        lblMensagem = new javax.swing.JLabel();
        inputTxtMensagem = new javax.swing.JTextField();
        lblModo = new javax.swing.JLabel();
        selectModo = new javax.swing.JComboBox<>();
        btnEnviar = new javax.swing.JButton();
        emojiButton = new javax.swing.JButton();

        popUpEmoji.setName("popUpEmoji"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat");
        setName("fmrChat"); // NOI18N

        scrConversa.setName("scrConversa"); // NOI18N

        edtConversa.setContentType("text/html"); // NOI18N
        scrConversa.setViewportView(edtConversa);

        lblMensagem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMensagem.setText("Mensagem:");
        lblMensagem.setName("lblMensagem"); // NOI18N

        inputTxtMensagem.setName("inputTxtMensagem"); // NOI18N
        inputTxtMensagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputTxtMensagemKeyPressed(evt);
            }
        });

        lblModo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblModo.setText("Modo:");
        lblModo.setName("lblModo"); // NOI18N

        selectModo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fala", "Grita", "Sussurra", "Xinga" }));
        selectModo.setName("selectModo"); // NOI18N

        btnEnviar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEnviar.setText("Enviar");
        btnEnviar.setName("btnEnviar"); // NOI18N
        btnEnviar.addActionListener(this::btnEnviarActionPerformed);

        emojiButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pacote/images/iconButton.png"))); // NOI18N
        emojiButton.setBorder(null);
        emojiButton.setBorderPainted(false);
        emojiButton.setContentAreaFilled(false);
        emojiButton.setName("emojiButton"); // NOI18N
        emojiButton.addActionListener(this::emojiButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputTxtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblModo)
                        .addGap(12, 12, 12)
                        .addComponent(selectModo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emojiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrConversa)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrConversa, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emojiButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputTxtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblModo)
                        .addComponent(selectModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEnviar)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        this.gerarAndEnviarMsg();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void inputTxtMensagemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputTxtMensagemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.gerarAndEnviarMsg();
        }
    }//GEN-LAST:event_inputTxtMensagemKeyPressed

    private void emojiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emojiButtonActionPerformed
        if (popUpEmoji.isVisible()) {
            popUpEmoji.setVisible(false);
        } else {
            popUpEmoji.show(
                    emojiButton,
                    0,
                    -popUpEmoji.getPreferredSize().height
            );
        }
    }//GEN-LAST:event_emojiButtonActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmChat().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JEditorPane edtConversa;
    private javax.swing.JButton emojiButton;
    private javax.swing.JTextField inputTxtMensagem;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblModo;
    private javax.swing.JPopupMenu popUpEmoji;
    private javax.swing.JScrollPane scrConversa;
    private javax.swing.JComboBox<String> selectModo;
    // End of variables declaration//GEN-END:variables
}
