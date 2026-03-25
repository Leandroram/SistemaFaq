package dao;
import connection.ConnectionFactory;
import model.Usuarios;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuariosDAO {
    public List<Usuarios> listarPorPosto(int id) {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbluser WHERE id = ? ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuarios f = new Usuarios();
                f.setId(rs.getInt("id"));
                f.setId(rs.getInt("idempresa"));
                f.setNome(rs.getString("nome"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setEstatus(rs.getString("status"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuarios> listarTodos() { /// função para o botão de busca
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT tbluser.id, tbluser.idEmpresa, tbluser.nome, tbluser.telefone , tbluser.email , tblempresa.empresa, tbluser.status\n" +
"FROM tbluser INNER JOIN tblempresa ON tbluser.idEmpresa = tblempresa.id ORDER BY id;";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuarios p = new Usuarios();
                p.setId(rs.getInt("id"));
                p.setIdempresa(rs.getInt("idempresa"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setEmpresa(rs.getString("empresa"));
                p.setEstatus(rs.getString("status"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }    
    
    
    public void inserir(Usuarios f) {
        String sql = "INSERT INTO tbluser (id, idEmpresa, nome, telefone, email, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getId());
            stmt.setInt(2, f.getIdempresa());
            stmt.setString(3, f.getNome());
            stmt.setString(4, f.getTelefone());
            stmt.setString(5, f.getEmail());
            stmt.setString(6, f.getEstatus());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizar(Usuarios f) {
        String sql = "UPDATE tbluser SET idEmpresa=?, nome=?, telefone=?, email=?, status=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, f.getIdempresa());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getEmail());
            stmt.setString(5, f.getEstatus());
            stmt.setInt(6, f.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deletar(int codigo) {
        String sql = "DELETE FROM tbluser WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuarios buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM tbluser WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;       
    }
    
    public List<Usuarios> buscar(String termoBusca) {
        List<Usuarios> lista = new ArrayList<>();
        // SQL que busca por código OU nome
        String sql = "SELECT * FROM tbluser WHERE nome LIKE ? OR telefone LIKE ? ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String termo = "%" + termoBusca + "%";  // % para buscar em qualquer parte
            stmt.setString(1, termo);
            stmt.setString(2, termo);
           // stmt.setString(3, termo);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuarios p = new Usuarios();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setEstatus(rs.getString("status"));
                lista.add(p);
            }
            System.out.println("✅ Busca por '" + termoBusca + "': " + lista.size() + " resultado(s)");

        } catch (SQLException e) {
            System.out.println("❌ Erro na busca: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    public int totalUsuarios() { ///usando para exibir a soma
        int total = 0;
        Connection con = ConnectionFactory.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS 'total' FROM tbluser");
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
