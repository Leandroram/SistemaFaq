/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.ConnectionFactory;
import model.Usuarios;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Ticket;

/**
 *
 * @author leandro
 */
public class TicketDAO {   
    
    public List<Ticket> listarComPaginacao(int limit, int offset) {
        List<Ticket> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT tblticket.id AS ticketId, tblticket.idUser AS UserId, tblticket.idEmp AS EmpId, tblempresa.empresa, tbluser.nome, tblticket.tipo, tblticket.detalhe, "
                    + "tblticket.registro,tblticket.data, tblticket.hora, tblticket.detalhefech, tblticket.dataf, "
                    + "tblticket.horaf,tblticket.status FROM tblticket INNER JOIN tblempresa ON "
                    + "tblticket.idEmp = tblempresa.id INNER JOIN tbluser ON tblticket.idUser = tbluser.id "
                    + "ORDER BY tblticket.id LIMIT ? OFFSET ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticketId"));
                ticket.setIdUser(rs.getInt("UserId"));
                ticket.setIdEmp(rs.getInt("EmpId"));
                ticket.setUser(rs.getString("tbluser.nome"));
                ticket.setEmpresa(rs.getString("tblempresa.empresa")); 
                ticket.setTipo(rs.getString("tipo"));
                ticket.setDetalhe(rs.getString("detalhe"));
                ticket.setRegistro(rs.getString("registro"));
                ticket.setData(rs.getDate("data"));
                ticket.setHora(rs.getTime("hora"));
                ticket.setDetalhefech(rs.getString("detalhefech"));
                ticket.setDataf(rs.getDate("dataf"));
                ticket.setHoraf(rs.getTime("horaf"));
                ticket.setStatus(rs.getString("status"));
                lista.add(ticket);
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
    
    public int contarTotal() {
        java.sql.Connection conn = null;
        java.sql.PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT COUNT(*) as total FROM tblticket";
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
     

    public List<Ticket> pesquisarComPaginacao(int limit, int offset, String termoPesquisa) {
        List<Ticket> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT tblticket.id AS ticketId, tblticket.idUser AS UserId, tblticket.idEmp AS EmpId, tblempresa.empresa, tbluser.nome, tblticket.tipo, "
                    + "tblticket.detalhe, tblticket.registro,tblticket.data, tblticket.hora, tblticket.detalhefech, "
                    + "tblticket.dataf, tblticket.horaf,tblticket.status FROM tblticket INNER JOIN tblempresa ON "
                    + "tblticket.idEmp = tblempresa.id INNER JOIN tbluser ON tblticket.idUser = tbluser.id  "
                    + "WHERE tblempresa.empresa LIKE ? OR tblticket.registro LIKE ? ORDER BY tblticket.id LIMIT ? OFFSET ?";
            stmt = conn.prepareStatement(sql);
            String termo = "%" + termoPesquisa + "%";
            stmt.setString(1, termo);  // WHERE tblempresa.empresa LIKE ?
            stmt.setString(2, termo);  // OR tblticket.registro LIKE ?
            stmt.setInt(3, limit);     // LIMIT ?
            stmt.setInt(4, offset);    // OFFSET ?
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticketId")); 
                ticket.setIdUser(rs.getInt("UserId"));
                ticket.setIdEmp(rs.getInt("EmpId"));
                ticket.setUser(rs.getString("tbluser.nome"));
                ticket.setEmpresa(rs.getString("tblempresa.empresa")); 
                ticket.setTipo(rs.getString("tipo"));
                ticket.setDetalhe(rs.getString("detalhe"));
                ticket.setRegistro(rs.getString("registro"));
                ticket.setData(rs.getDate("data"));
                ticket.setHora(rs.getTime("hora"));
                ticket.setDetalhefech(rs.getString("detalhefech"));
                ticket.setDataf(rs.getDate("dataf"));
                ticket.setHoraf(rs.getTime("horaf"));
                ticket.setStatus(rs.getString("status"));
                lista.add(ticket);
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
    
    public int contarPesquisa(String termoPesquisa) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT COUNT(*) as total FROM tblticket WHERE registro LIKE ? OR status LIKE ?";
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
    
    public boolean excluir(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "DELETE FROM tblticket WHERE id = ?";
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
    
    public Ticket buscarPorId(int id) { ///usado para buscar e carregar a proxima tela
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT tblticket.id AS ticketId, tblticket.idUser AS userID, tblticket.idEmp AS empID, tbluser.nome, tblempresa.empresa, tbluser.nome, tblticket.tipo, "
                    + "tblticket.detalhe, tblticket.registro,tblticket.data, tblticket.hora, tblticket.detalhefech, "
                    + "tblticket.dataf, tblticket.horaf,tblticket.status FROM tblticket INNER JOIN tblempresa ON "
                    + "tblticket.idEmp = tblempresa.id INNER JOIN tbluser ON tblticket.idUser = tbluser.id "
                    + "WHERE tblticket.id = ?  ORDER BY tblticket.id";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticketId"));  
                ticket.setIdUser(rs.getInt("userID"));
                ticket.setIdEmp(rs.getInt("empID"));
                ticket.setUser(rs.getString("tbluser.nome")); 
                ticket.setEmpresa(rs.getString("tblempresa.empresa")); 
                ticket.setTipo(rs.getString("tipo"));
                ticket.setDetalhe(rs.getString("detalhe"));
                ticket.setRegistro(rs.getString("registro"));
                ticket.setData(rs.getDate("data"));
                ticket.setHora(rs.getTime("hora"));
                ticket.setDetalhefech(rs.getString("detalhefech"));
                ticket.setDataf(rs.getDate("dataf"));
                ticket.setHoraf(rs.getTime("horaf"));
                ticket.setStatus(rs.getString("status"));
                 return ticket;
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
    
    public boolean salvar(Ticket ticket) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "INSERT INTO tblticket (idUser, idEmp, tipo, detalhe, registro, data, hora, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ticket.getIdUser());
            stmt.setInt(2, ticket.getIdEmp());
            stmt.setString(3, ticket.getTipo());
            stmt.setString(4, ticket.getDetalhe());
            stmt.setString(5, ticket.getRegistro());
            stmt.setDate(6, ticket.getData());
            stmt.setTime(7, ticket.getHora());
            stmt.setString(8, ticket.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
    }
    
    public boolean atualizar(Ticket ticket) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "UPDATE tblticket SET idUser=?, idEmp=?, tipo=?, detalhe=?, detalhefech=?, dataf=?, horaf=?, status=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ticket.getIdUser());
            stmt.setInt(2, ticket.getIdEmp());
            stmt.setString(3, ticket.getTipo());
            stmt.setString(4, ticket.getDetalhe());
            stmt.setString(5, ticket.getDetalhefech());
            stmt.setDate(6, ticket.getDataf());
            stmt.setTime(7, ticket.getHoraf());
            stmt.setString(8, ticket.getStatus());
            stmt.setInt(9, ticket.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.fecharResultSet(rs);
            ConnectionFactory.fecharStatement(stmt);
            ConnectionFactory.fecharConexao(conn);
        }
    }
}
