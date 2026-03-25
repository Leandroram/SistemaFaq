package telas;

import dao.EmpresasDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Empresas;
import javax.swing.JOptionPane;
import java.sql.Time;
import javax.swing.Timer;
import java.time.LocalDateTime;
import java.util.Date;
import view.EmpresasCad;

/**
 * Tela de cadastro/edição de contatos
 */
public class TelaCadastroEmp extends javax.swing.JDialog {
    
    private Empresas empresaEdicao;
    private EmpresasCad telaListagem;
    private EmpresasDAO dao;

    public TelaCadastroEmp(java.awt.Frame parent, Empresas empresa) {
        super(parent, true);
        this.telaListagem = (EmpresasCad) parent;
        this.empresaEdicao = empresa;
        this.dao = new EmpresasDAO();
        
        initComponents();
        
        if (empresa != null) {
            lblTitulo.setText("Editar Contato");
            btnSalvar.setText("Atualizar");
            preencherCampos();
        }
        
        showDate();
        showTime();
    }
    
    void showTime(){
        new Timer(0, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        Date date = new Date();
        SimpleDateFormat s= new SimpleDateFormat("hh:mm:ss");
        txtHoras.setText(s.format(date));
        }
           }).start();
    }
    
    void showDate(){
      Date d= new Date();
      SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
      txtData.setText(dateFormat.format(d));
    }

    private void preencherCampos() {
        txtEmpresa.setText(empresaEdicao.getEmpresa());
        //-----------------data------------------------
        SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");
        txtData.setText(sdfData.format(empresaEdicao.getData()));

        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        txtHoras.setText(sdfHora.format(empresaEdicao.getHora()));
        //--------------------------------------------
        txtNotas.setText(empresaEdicao.getNotas());
        cbxStatus.setSelectedItem(empresaEdicao.getStatus());
    }
    
    private void salvar() {
        if (txtEmpresa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome!", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtEmpresa.requestFocus();
            return;
        }

        Empresas empresa;
        if (empresaEdicao == null) {
            empresa = new Empresas();
        } else {
            empresa = empresaEdicao;
        }

        try {
            // DATA
            SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdfData.parse(txtData.getText().trim());
            java.sql.Date dataSql = new java.sql.Date(data.getTime());
            empresa.setData(dataSql);

            // HORA
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
            Date horaUtil = sdfHora.parse(txtHoras.getText().trim());
            Time hora = new Time(horaUtil.getTime());
            empresa.setHora(hora);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        empresa.setEmpresa(txtEmpresa.getText().trim());
        empresa.setNotas(txtNotas.getText().trim());
        empresa.setStatus(cbxStatus.getSelectedItem().toString());

        boolean sucesso;
        if (empresaEdicao == null) {
            sucesso = dao.salvar(empresa);
        } else {
            sucesso = dao.atualizar(empresa);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                empresaEdicao == null ? "Empresa cadastrada com sucesso!" : "Contato atualizado com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            telaListagem.carregarDados();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar contato!","Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        painelFormulario = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtHoras = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotas = new javax.swing.JTextArea();
        painelBotoes = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Contato");
        setModal(true);
        setResizable(false);

        painelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastrar Empresas");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelFormulario.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblNome.setText("Empresa: *");

        txtEmpresa.setColumns(25);

        lblTelefone.setText("Data:*");

        txtData.setEditable(false);
        txtData.setColumns(25);

        lblEmail.setText("Notas: *");

        lblStatus.setText("Status: *");

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Desativado" }));

        jLabel1.setText("Hora:*");

        txtHoras.setEditable(false);

        txtNotas.setColumns(20);
        txtNotas.setRows(5);
        jScrollPane1.setViewportView(txtNotas);

        javax.swing.GroupLayout painelFormularioLayout = new javax.swing.GroupLayout(painelFormulario);
        painelFormulario.setLayout(painelFormularioLayout);
        painelFormularioLayout.setHorizontalGroup(
            painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFormularioLayout.createSequentialGroup()
                .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelFormularioLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(painelFormularioLayout.createSequentialGroup()
                                    .addComponent(lblEmail)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painelFormularioLayout.createSequentialGroup()
                                    .addComponent(lblTelefone)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(painelFormularioLayout.createSequentialGroup()
                                .addComponent(lblStatus)
                                .addGap(12, 12, 12)
                                .addComponent(cbxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(painelFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        painelFormularioLayout.setVerticalGroup(
            painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFormularioLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone)
                    .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFormularioLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblEmail))
                    .addGroup(painelFormularioLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFormularioLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblStatus))
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSalvar.setBackground(new java.awt.Color(76, 175, 80));
        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Cadastrar");
        btnSalvar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelBotoesLayout = new javax.swing.GroupLayout(painelBotoes);
        painelBotoes.setLayout(painelBotoesLayout);
        painelBotoesLayout.setHorizontalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotoesLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelBotoesLayout.setVerticalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelBotoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelFormulario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelFormulario;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtHoras;
    private javax.swing.JTextArea txtNotas;
    // End of variables declaration//GEN-END:variables
}
