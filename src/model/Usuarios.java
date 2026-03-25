/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author leandro
 */
public class Usuarios {
    private int id;
    private int idempresa;
    private String nome;
    private String telefone;
    private String email;
    private String empresa;
    private String estatus;

    public Usuarios() {
    }

    public Usuarios(int id, int idempresa, String nome, String telefone, String email, String estatus) {
        this.id = id;
        this.idempresa = idempresa;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.estatus = estatus;
    }

    public Usuarios(int id, int idempresa, String nome, String empresa, String telefone, String email, String estatus) {
        this.id = id;
        this.idempresa = idempresa;
        this.nome = nome;
        this.empresa = empresa;
        this.telefone = telefone;
        this.email = email;
        this.estatus = estatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }   
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", estatus=" + estatus + '}';
    }
    
    
}
