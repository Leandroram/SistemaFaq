package dao;
import connection.ConnectionFactory;
import model.Usuarios;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empresas;

public class EmpresasDAO {
    public boolean salvar(Empresas empresas) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try { // Connection conn = ConnectionFactory.getConnection()
            conn = ConnectionFactory.getConnection();
            String sql = "INSERT INTO tblempresa (empresa, notas, data, hora, status) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, empresas.getEmpresa());
            stmt.setString(2, empresas.getNotas());
            stmt.setDate(3, empresas.getData());
            stmt.setTime(4, empresas.getHora());
            stmt.setString(5, empresas.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
    }
    
    /**
     * Atualiza um empresas existente
     */
    public boolean atualizar(Empresas empresas) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "UPDATE tblempresa SET empresa=?, notas=?, status=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, empresas.getEmpresa());
            stmt.setString(2, empresas.getNotas());
          //stmt.setDate(3, empresas.getData());
          //stmt.setTime(4, empresas.getHora());
            stmt.setString(3, empresas.getStatus());
            stmt.setInt(4, empresas.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
    }
    
    /**
     * Exclui um empresas pelo ID
     */
    public boolean excluir(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "DELETE FROM tblempresa WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
    }
    
    /**
     * Busca um empresas por ID
     */
    public Empresas buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM tblempresa WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairEmpresas(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return null;
    }
    
    /**
     * Lista todos os empresass
     */
    public List<Empresas> listarTodos() {
        List<Empresas> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM tblempresa ORDER BY id DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(extrairEmpresas(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return lista;
    }
    
    /**
     * Pesquisa empresass por nome ou email
     */
    public List<Empresas> pesquisar(String termoPesquisa) {
        List<Empresas> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM tblempresa WHERE empresas LIKE ? OR status LIKE ? ORDER BY id";
            stmt = conn.prepareStatement(sql);
            String termo = "%" + termoPesquisa + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(extrairEmpresas(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return lista;
    }
    
    /**
     * Conta total de empresass
     */
    public int contarTotal() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT COUNT(*) as total FROM tblempresa";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return 0;
    }
    
    /**
     * Conta empresass ativos
     */
    public int contarAtivos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT COUNT(*) as total FROM tblempresa WHERE status = 'Ativo'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return 0;
    }
    
    /**
     * Extrai objeto Empresas do ResultSet
     */
    private Empresas extrairEmpresas(ResultSet rs) throws SQLException {
        Empresas empresas = new Empresas();
        empresas.setId(rs.getInt("id"));
        empresas.setEmpresa(rs.getString("empresa"));
        empresas.setNotas(rs.getString("notas"));
        empresas.setData(rs.getDate("data"));
        empresas.setHora(rs.getTime("hora"));
        empresas.setStatus(rs.getString("status"));
        return empresas;
    }
    
    /**
     * Lista empresass com paginação
     */
    public List<Empresas> listarComPaginacao(int limit, int offset) {
        List<Empresas> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM tblempresa ORDER BY id LIMIT ? OFFSET ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(extrairEmpresas(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return lista;
    }
    
    /**
     * Pesquisa empresass com paginação
     */
    public List<Empresas> pesquisarComPaginacao(String termoPesquisa, int limit, int offset) {
        List<Empresas> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM tblempresa WHERE empresa LIKE ? OR notas LIKE ? ORDER BY data LIMIT ? OFFSET ?";
            stmt = conn.prepareStatement(sql);
            String termo = "%" + termoPesquisa + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(extrairEmpresas(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return lista;
    }
    
    /**
     * Conta total de resultados da pesquisa
     */
    public int contarPesquisa(String termoPesquisa) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT COUNT(*) as total FROM tblempresa WHERE empresa LIKE ? OR status LIKE ?";
            stmt = conn.prepareStatement(sql);
            String termo = "%" + termoPesquisa + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
        return 0;
    }
}
