/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import buscas.BuscaEmpresa;
import dao.UsuariosDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Empresas;
import model.Usuarios;

/**
 *
 * @author leandro
 */
public class UsuariosCad extends javax.swing.JDialog {
    private UsuariosDAO usuariosDAO;   
    // Models
    private DefaultTableModel modelTableUsuarios;
    private int codEmpresasSelecionado = 0;
    private Usuarios selectedUsuarios = null;       
    private DefaultTableModel modelTable;
    private Usuarios usuariosSelecionado;  // ✅ Declarar variável de instância
    private List<Usuarios> visibleFields;
    
    private DefaultTableModel modeloTabela;
    private int paginaAtual = 1;
    private int registrosPorPagina = 10;
    private int totalRegistros = 0;
    private int totalPaginas = 0;
    private String termoPesquisa = "";
    /**
     * Creates new form UsuariosCad
     */
    public UsuariosCad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        usuariosDAO = new UsuariosDAO();
        
        initComponents();
        carregarDados();
        carregarPostos();
      //  configurarTabela();
        setupListeners();  
        carregarUsuarios();        
        
        setLocationRelativeTo(parent);
    }
    
    private void carregarDados() {
        String[] colunas = {"ID", "IDE", "Nome", "Telefone", "E-mail", "Empresa", "Status"};
        modelTable = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
        tblUsuarios.setModel(modelTable);        
        // ✅ APLICAR RENDERER NA COLUNA STATUS
        tblUsuarios.getColumnModel().getColumn(6).setCellRenderer(new StatusCellRenderer());        
        // Configurações da tabela
        tblUsuarios.setRowHeight(25);
        tblUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    }
    
    private void carregarPostos() {
        modelTable.setRowCount(0);        
        List<Usuarios> usuarios = usuariosDAO.listarTodos();        
        for (Usuarios p : usuarios) {
            modelTable.addRow(new Object[]{
                p.getId(),
                p.getIdempresa(),
                p.getNome(),
                p.getTelefone(),
                p.getEmail(),
                p.getEstatus()  // ✅ Será renderizado com círculo colorido
            });
        }        
        System.out.println("✅ " + usuarios.size() + " Usuarios carregados");
    }    
    // ===================================
    // CLASSE INTERNA - RENDERER
    // ===================================
    class StatusCellRenderer extends DefaultTableCellRenderer {        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) { 
            
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String status = value.toString().toUpperCase();
                    Color corCirculo;
                    switch (status) {
                        case "ATIVO":
                            corCirculo = new Color(34, 139, 34);  // Verde
                            break;
                        case "DESATIVADO":
                            corCirculo = new Color(220, 20, 60);  // Vermelho
                            break;
                        case "SUSPENSO":
                            corCirculo = new Color(255, 193, 7);  // Amarelo
                            break;
                        default:
                            corCirculo = Color.GRAY;
                    }
                    Icon circulo = new Icon() {
                        @Override
                        public void paintIcon(Component c, Graphics g, int x, int y) {
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                            g2.setColor(corCirculo);
                            g2.fillOval(x, y, 12, 12);

                            g2.setColor(corCirculo.darker());
                            g2.drawOval(x, y, 12, 12);
                        }

                        @Override
                        public int getIconWidth() { return 12; }
                        @Override
                        public int getIconHeight() { return 12; }
                    };

                    setIcon(circulo);
                    setText(" " + status);
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                return this;
            }
    }        

    
    private void carregarUsuarioSelecionado() {
        int row = tblUsuarios.getSelectedRow();
        if (row < 0) return;
        int cod = (int) modelTableUsuarios.getValueAt(row, 0);
        Usuarios f = usuariosDAO.buscarPorCodigo(cod);
        if (f != null) {
            txtId.setText(String.valueOf(f.getId()));
            txtCodEMp.setText(String.valueOf(f.getIdempresa()));
            txtNome.setText(String.valueOf(f.getNome()));
            txtTelefone.setText(String.valueOf(f.getTelefone()));
            txtEmail.setText(String.valueOf(f.getEmail()));
            txtEmpresa.setText(String.valueOf(f.getEmail()));
            cbxStatus.setSelectedItem(f.getEstatus());                    
        }
    }    

    
    private void setupListeners() {
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblUsuarios.getSelectedRow();
                
                if (selectedRow != -1) {
                    // Pegar código da linha selecionada (coluna 0)
                    int codUsuario = (int) modelTable.getValueAt(selectedRow, 0);                    
                    // Buscar posto completo no DAO
                    usuariosSelecionado = usuariosDAO.buscarPorCodigo(codUsuario);
                    
                    if (usuariosSelecionado != null) {
                        // Preencher campos do formulário
                        preencherCampos(usuariosSelecionado);                        
                        System.out.println("✅ Selecionado: " + usuariosSelecionado.getNome());
                    }
                }
            }
        });        

        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tblUsuarios.getSelectedRow();
                    if (row >= 0) {
                        // Ação no duplo-clique (ex: editar)
                        System.out.println("Duplo-clique na linha: " + row);
                    }
                }
            }
        });
    }
    
    private void carregarUsuarios() {
        modelTable.setRowCount(0);        
        List<Usuarios> postos = usuariosDAO.listarTodos();        
        for (Usuarios p : postos) {
            modelTable.addRow(new Object[]{
                p.getId(),
                p.getIdempresa(),
                p.getNome(),
                p.getTelefone(),
                p.getEmail(),
                p.getEmpresa(),
                p.getEstatus()

            });
        }        
        System.out.println("✅ " + postos.size() + " postos carregados");
    }
    
    private void preencherCampos(Usuarios usuarios) {
        txtId.setText(String.valueOf(usuarios.getId()));        
        txtNome.setText(usuarios.getNome());
        txtTelefone.setText(usuarios.getTelefone());
        txtEmail.setText(usuarios.getEmail());
        txtEmpresa.setText(usuarios.getEmpresa());
    }
    
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");    
        txtTelefone.setText(""); 
        txtEmail.setText(""); 
        txtEmpresa.setText(""); 
        txtCodEMp.setText(""); 
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do usuario!");
            return;
        }        
        Usuarios usuarios = new Usuarios();
        
        usuarios.setIdempresa(Integer.parseInt(txtCodEMp.getText()));
        usuarios.setNome(txtNome.getText().trim());
        usuarios.setTelefone(txtTelefone.getText().trim());
        usuarios.setEmail(txtEmail.getText().trim());
        if (cbxStatus.getSelectedItem() != null) {
            usuarios.setEstatus((String) cbxStatus.getSelectedItem());
        }
        
        try {
            if (txtId.getText().trim().isEmpty()) {
                // INSERIR
                usuariosDAO.inserir(usuarios);
                JOptionPane.showMessageDialog(this, "✅ Usuario cadastrado!");
            } else {
                // ATUALIZAR
                usuarios.setId(Integer.parseInt(txtId.getText()));
                usuariosDAO.atualizar(usuarios);
                JOptionPane.showMessageDialog(this, "✅ Usuario atualizado!");
            }            
            carregarUsuarios();
            limparCampos();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuario!");
            return;
        }        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja excluir o usuario " + txtId.getText() + "?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int codigo = Integer.parseInt(txtId.getText());
                usuariosDAO.deletar(codigo);                
                JOptionPane.showMessageDialog(this, "✅ Usuario excluído!");
                carregarUsuarios();
                limparCampos();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Erro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void buscarUsuarios() {
        String termoBusca = txtSearch.getText().trim();
        // Se campo vazio, carregar todos
        if (termoBusca.isEmpty()) {
            carregarPostos();
            return;
        }
        // Buscar no banco
        modelTable.setRowCount(0);

        List<Usuarios> resultados = usuariosDAO.buscar(termoBusca);
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,"Nenhum posto encontrado com: " + termoBusca,
                "Busca",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            for (Usuarios p : resultados) {
                modelTable.addRow(new Object[]{
                    p.getId(),
                    p.getIdempresa(),
                    p.getNome(),
                    p.getTelefone(),
                    p.getEmail(),
                    p.getEmpresa(),
                    p.getEstatus()
                });
            }
            System.out.println("✅ " + resultados.size() + " posto(s) encontrado(s)");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        cbxStatus = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnBusca = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtCodEMp = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de Usuários", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtId.setEditable(false);

        jLabel1.setText("ID:");

        jLabel2.setText("Nome:");

        jLabel3.setText("Telefone:");

        jLabel4.setText("E-mail:");

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Desativado", "Suspenso" }));

        jLabel5.setText("Status:");

        txtEmpresa.setEditable(false);
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });

        jLabel6.setText("Empresa:");

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(157, 157, 157)
                                .addComponent(jLabel6)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefone)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jLabel5)
                                .addGap(0, 86, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        btnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_search_16px.png"))); // NOI18N
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_save_as_16px.png"))); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/encerrar.png"))); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_clear_formatting_16px.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtCodEMp.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBusca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodEMp, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBusca)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalvar)
                        .addComponent(txtCodEMp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        // TODO add your handling code here:
        buscarUsuarios();
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
         salvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
         excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limparCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        // TODO add your handling code here:
        if(tblUsuarios.getSelectedRow() != -1){
            txtId.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString());
            txtCodEMp.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 1).toString());
            txtNome.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 2).toString());                     
            txtTelefone.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 3).toString()); 
            txtEmail.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 4).toString());  
            txtEmpresa.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 5).toString()); 
            cbxStatus.setSelectedItem(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 6).toString());
        }
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        BuscaEmpresa tela = new BuscaEmpresa(
            (java.awt.Frame) SwingUtilities.getWindowAncestor(this), 
            true  // ← modal
        );
        tela.setVisible(true);         
        // Pegar o posto selecionado após fechar
        Empresas empresasSelecionado = tela.getEmpresasSelecionado();
    
        if (empresasSelecionado != null) {
            txtCodEMp.setText(String.valueOf(empresasSelecionado.getId()));
            txtEmpresa.setText(empresasSelecionado.getEmpresa());
            codEmpresasSelecionado = empresasSelecionado.getId();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(UsuariosCad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuariosCad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuariosCad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuariosCad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuariosCad dialog = new UsuariosCad(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCodEMp;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
