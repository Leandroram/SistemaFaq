package view;

import dao.UsuariosDAO;
import model.Usuarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


public class BuscarUser extends JDialog {
    
    // Variáveis
    private UsuariosDAO usuariosDAO;
    private Usuarios usuariosSelecionado;
    private JTable tableUsuarios;
    private DefaultTableModel modelTable;
    private JTextField txtBusca;
    private JButton btnSelecionar;
    private JButton btnCancelar;
    
    public BuscarUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        usuariosDAO = new UsuariosDAO();
        usuariosSelecionado = null;
        
        initComponents();
        carregarFacs();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setTitle("Busca Usuários");
        setSize(450, 400);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Layout principal
        setLayout(new BorderLayout(5, 5));
        
        // ===================================
        // PAINEL SUPERIOR - TÍTULO
        // ===================================
        JPanel panelTitulo = new JPanel();
      //  panelTitulo.setBackground(Color.WHITE);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("BUSCA USUÁRIOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.RED);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // ===================================
        // PAINEL CENTRO - BUSCA E TABELA
        // ===================================
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout(5, 5));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(5, 10, -3, 10));
     //   panelCentro.setBackground(Color.WHITE);
        
        // Campo de busca
        JPanel panelBusca = new JPanel(new BorderLayout(5, 0));
       // panelBusca.setBackground(Color.WHITE);
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        
        txtBusca = new JTextField();
        txtBusca.setFont(new Font("Arial", Font.PLAIN, 12));
        txtBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarFacs();
            }
        });
        
        panelBusca.add(lblBuscar, BorderLayout.WEST);
        panelBusca.add(txtBusca, BorderLayout.CENTER);
        
        panelCentro.add(panelBusca, BorderLayout.NORTH);
        
        // Label "Tabela"
        JLabel lblTabela = new JLabel("Tabela Fac");
        lblTabela.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTabela.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        //createEmptyBorder(top, left, bottom, right)
        panelCentro.add(lblTabela, BorderLayout.CENTER);
        
        // Tabela
        String[] colunas = {"ID", "IdEmp","Nome", "Empresa", "Telefone", "Email","Status"};
        modelTable = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableUsuarios = new JTable(modelTable);
        tableUsuarios.setFont(new Font("Arial", Font.PLAIN, 11));
        tableUsuarios.setRowHeight(20);
        tableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableUsuarios.getTableHeader().setBackground(new Color(240, 240, 240));
        
        // Ajustar largura das colunas
        tableUsuarios.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableUsuarios.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableUsuarios.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableUsuarios.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        // Duplo-clique para selecionar
        tableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    selecionarUsuarios();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableUsuarios);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scrollPane.setPreferredSize(new Dimension(300, 180));
        
        // Panel para tabela
        JPanel panelTabela = new JPanel(new BorderLayout());
      //  panelTabela.setBackground(Color.WHITE);
        panelTabela.add(lblTabela, BorderLayout.NORTH);
        panelTabela.add(scrollPane, BorderLayout.CENTER);
        
        panelCentro.add(panelTabela, BorderLayout.SOUTH);
        
        add(panelCentro, BorderLayout.CENTER);
        
        // ===================================
        // PAINEL INFERIOR - BOTÕES
        // ===================================
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
       // panelBotoes.setBackground(Color.WHITE);
        
        // Botão Selecionar
        btnSelecionar = new JButton("Selecionar");
        btnSelecionar.setPreferredSize(new Dimension(125, 30));
        btnSelecionar.setIcon(createIcon("✓", new Color(0, 150, 0)));
        btnSelecionar.setBackground(new Color(240, 255, 240));
        btnSelecionar.setFocusPainted(false);
        btnSelecionar.addActionListener(e -> selecionarUsuarios());
        
        // Botão Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(125, 30));
        btnCancelar.setIcon(createIcon("✗", new Color(200, 0, 0)));
        btnCancelar.setBackground(new Color(255, 240, 240));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> cancelar());
        
        panelBotoes.add(btnSelecionar);
        panelBotoes.add(btnCancelar);
        
        add(panelBotoes, BorderLayout.SOUTH);
        
        // Fundo branco
     //   getContentPane().setBackground(Color.WHITE);
    }
    
    // Criar ícone simples
    private Icon createIcon(String text, Color color) {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString(text, x, y + 12);
            }
            public int getIconWidth() { return 16; }
            public int getIconHeight() { return 16; }
        };
    }
    
    private void carregarFacs() {
        modelTable.setRowCount(0);
        List<Usuarios> usuarios = usuariosDAO.listarTodos();
        
        for (Usuarios p : usuarios) {
            // Preencher as 4 colunas
            modelTable.addRow(new Object[]{
                    p.getId(),
                    p.getIdempresa(),
                    p.getNome(),
                    p.getEmpresa(),
                    p.getTelefone(),
                    p.getEmail(),
                    p.getEstatus()
            });
        }
        
        System.out.println("✅ Carregados " + usuarios.size() + " usuários");
    }
    
    private void filtrarFacs() {
        String busca = txtBusca.getText().toLowerCase().trim();
        modelTable.setRowCount(0);
        
        List<Usuarios> usuarios = usuariosDAO.listarTodos();
        int count = 0;
        
        for (Usuarios p : usuarios) {
            if (busca.isEmpty() || p.getNome().toLowerCase().contains(busca)) {
                modelTable.addRow(new Object[]{
                    p.getId(),
                    p.getIdempresa(),
                    p.getNome(),
                    p.getEmpresa(),
                    p.getTelefone(),
                    p.getEmail(),
                    p.getEstatus()
                    
                });
                count++;
            }
        }
        
        System.out.println("🔍 Filtro: " + count + " resultados");
    }
    
    private void selecionarUsuarios() {
        int row = tableUsuarios.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,"Selecione um Usuario da lista!","Atenção", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int codFac = (int) modelTable.getValueAt(row, 0);
        int codEmp = (int) modelTable.getValueAt(row, 1);        
        String nome = (String) modelTable.getValueAt(row, 2);
        String empresa = (String) modelTable.getValueAt(row, 3);
        String telefone = (String) modelTable.getValueAt(row, 4);
        String email = (String) modelTable.getValueAt(row, 5);
        String status = (String) modelTable.getValueAt(row, 6);
        try {
            usuariosSelecionado = new Usuarios(
                codFac, 
                codEmp, 
                nome,
                empresa,
                telefone,
                email,
                status
            );

            System.out.println("✅ Usuario Selecionado: " + nome);
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, 
                "Erro ao processar data/hora: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    
    private void cancelar() {
        usuariosSelecionado = null;
        System.out.println("❌ Cancelado");
        dispose();
    }
    
    public Usuarios getUsuariosSelecionado() {
        return usuariosSelecionado;
    }
}