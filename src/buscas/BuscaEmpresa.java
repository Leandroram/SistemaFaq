package buscas;

import dao.EmpresasDAO;
import model.Empresas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BuscaEmpresa extends JDialog {
    
    // Variáveis
    private EmpresasDAO postoDAO;
    private Empresas postoSelecionado;
    private JTable tableEmpresas;
    private DefaultTableModel modelTable;
    private JTextField txtBusca;
    private JButton btnSelecionar;
    private JButton btnCancelar;
    
    public BuscaEmpresa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        postoDAO = new EmpresasDAO();
        postoSelecionado = null;
        
        initComponents();
        carregarEmpresass();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setTitle("Busca Empresas");
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
        
        JLabel lblTitulo = new JLabel("BUSCA EMPRESA");
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
                filtrarEmpresass();
            }
        });
        
        panelBusca.add(lblBuscar, BorderLayout.WEST);
        panelBusca.add(txtBusca, BorderLayout.CENTER);
        
        panelCentro.add(panelBusca, BorderLayout.NORTH);
        
        // Label "Tabela"
        JLabel lblTabela = new JLabel("Tabela Empresas");
        lblTabela.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTabela.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        //createEmptyBorder(top, left, bottom, right)
        panelCentro.add(lblTabela, BorderLayout.CENTER);
        
        // Tabela
        String[] colunas = {"ID", "Empresa"};
        modelTable = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableEmpresas = new JTable(modelTable);
        tableEmpresas.setFont(new Font("Arial", Font.PLAIN, 11));
        tableEmpresas.setRowHeight(20);
        tableEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmpresas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableEmpresas.getTableHeader().setBackground(new Color(240, 240, 240));
        
        // Ajustar largura das colunas
        tableEmpresas.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableEmpresas.getColumnModel().getColumn(1).setPreferredWidth(100);
    //    tableEmpresas.getColumnModel().getColumn(2).setPreferredWidth(100);
    //    tableEmpresas.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        // Duplo-clique para selecionar
        tableEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    selecionarEmpresas();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableEmpresas);
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
        btnSelecionar.addActionListener(e -> selecionarEmpresas());
        
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
    
    private void carregarEmpresass() {
        modelTable.setRowCount(0);
        List<Empresas> postos = postoDAO.listarTodos();
        
        for (Empresas p : postos) {
            // Preencher as 4 colunas
            modelTable.addRow(new Object[]{
                p.getId(),      // Title 1
                p.getEmpresa()       // Title 4 (vazio por enquanto)
            });
        }
        
        System.out.println("✅ Carregados " + postos.size() + " postos");
    }
    
    private void filtrarEmpresass() {
        String busca = txtBusca.getText().toLowerCase().trim();
        modelTable.setRowCount(0);
        
        List<Empresas> postos = postoDAO.listarTodos();
        int count = 0;
        
        for (Empresas p : postos) {
            if (busca.isEmpty() || p.getEmpresa().toLowerCase().contains(busca)) {
                modelTable.addRow(new Object[]{
                    p.getId(),
                    p.getEmpresa()
                });
                count++;
            }
        }
        
        System.out.println("🔍 Filtro: " + count + " resultados");
    }
    
    private void selecionarEmpresas() {
        int row = tableEmpresas.getSelectedRow();
        
        if (row < 0) {
            JOptionPane.showMessageDialog(
                this, 
                "Selecione um posto da lista!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int id = (int) modelTable.getValueAt(row, 0);
        String empresa = (String) modelTable.getValueAt(row, 1);
        
        postoSelecionado = new Empresas(id, empresa);
        
        System.out.println("✅ Selecionado: " + empresa);
        
        dispose();
    }
    
    private void cancelar() {
        postoSelecionado = null;
        System.out.println("❌ Cancelado");
        dispose();
    }
    
    public Empresas getEmpresasSelecionado() {
        return postoSelecionado;
    }
}