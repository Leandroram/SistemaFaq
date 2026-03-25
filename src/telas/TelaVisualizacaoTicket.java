package telas;

import model.Ticket;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import model.Ticket;

/**
 * Tela de visualização de detalhes do contato
 */
public class TelaVisualizacaoTicket extends javax.swing.JDialog {

    public TelaVisualizacaoTicket(java.awt.Frame parent, Ticket ticket) {
        super(parent, true);
        initComponents();
        preencherDados(ticket);
    }
    
    public TelaVisualizacaoTicket(Ticket ticket) {
        super((Frame) null, true);
        initComponents();
        preencherDados(ticket);
    }

    private void preencherDados(Ticket ticket) {
        painelInfo.removeAll();
        painelInfo.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int linha = 0;
        
        // ID
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblIdLabel = new JLabel("ID:");
        lblIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblIdLabel, gbc);
        
        gbc.gridx = 1;
        JLabel lblId = new JLabel(String.valueOf(ticket.getId()));
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblId, gbc);
        
        linha++;
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblNomeLabel = new JLabel("Nome:");
        lblNomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblNomeLabel, gbc);
        
        gbc.gridx = 1;
        JLabel lblNome = new JLabel(ticket.getUser());
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblNome, gbc);
    
        linha++;
        
        // Empresa
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblEmpresaLabel = new JLabel("Empresa:");
        lblEmpresaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblEmpresaLabel, gbc);
        
        gbc.gridx = 1;
        JLabel lblEmpresa = new JLabel(ticket.getEmpresa());
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblEmpresa, gbc);  
        
        linha++;
        
        // Tipo
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblTipoLabel = new JLabel("Tipo:");
        lblTipoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblTipoLabel, gbc);
        
        gbc.gridx = 1;
        JLabel lblTipo = new JLabel(ticket.getTipo());
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblTipo, gbc);
        
        linha++;
        
        // Registro
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblRegistroLabel = new JLabel("Registro:");
        lblRegistroLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblRegistroLabel, gbc);
        
        gbc.gridx = 1;
        JLabel lblRegistro = new JLabel(ticket.getRegistro());
        lblRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblRegistro, gbc);
        
        linha++;
        
        // Data
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblDataLabel = new JLabel("Data:");
        lblDataLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblDataLabel, gbc);
        
        gbc.gridx = 1;
        SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");
        JLabel lblData = new JLabel(sdfData.format(ticket.getData()));
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblData, gbc);
        
        linha++;
        
        // hora
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblHoraLabel = new JLabel("Horas:");
        lblHoraLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblHoraLabel, gbc);        
              
        gbc.gridx = 1;
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        JLabel lblHora = new JLabel(sdfHora.format(ticket.getHora()));
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelInfo.add(lblHora, gbc);
       
        linha++;
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lblStatusLabel = new JLabel("status:");
        lblStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelInfo.add(lblStatusLabel, gbc);
        
        gbc.gridx = 1;
        JPanel painelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel circulo = new JLabel("●");
        circulo.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        
        if ("Aberto".equals(ticket.getStatus())) {
            circulo.setForeground(new Color(76, 175, 80)); // Verde
        } else {
            circulo.setForeground(new Color(244, 67, 54)); // Vermelho
        }
        
        JLabel lblStatus = new JLabel(ticket.getStatus());
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        painelStatus.add(circulo);
        painelStatus.add(lblStatus);
        painelInfo.add(painelStatus, gbc);
        
        painelInfo.revalidate();
        painelInfo.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        painelInfo = new javax.swing.JPanel();
        painelBotao = new javax.swing.JPanel();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visualizar Contato");
        setModal(true);
        setResizable(false);

        painelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Detalhes do Ticket");

        painelInfo.setLayout(new java.awt.GridBagLayout());

        btnFechar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        painelBotao.add(btnFechar);

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
            .addComponent(painelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelBotao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(painelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 385, Short.MAX_VALUE)
                .addComponent(painelBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelBotao;
    private javax.swing.JPanel painelInfo;
    private javax.swing.JPanel painelPrincipal;
    // End of variables declaration//GEN-END:variables
}
