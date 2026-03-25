/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author leandro
 */
public class Empresas {
    private int id;
    private String empresa;
    private String notas;
    private Date data;
    private Time hora;
    private String status;

    public Empresas() {
    }

    public Empresas(int id, String empresa) {
        this.id = id;
        this.empresa = empresa;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Empresas{" + "id=" + id + ", empresa=" + empresa + ", notas=" + notas + ", data=" + data + ", hora=" + hora + ", status=" + status + '}';
    }
    
    
    
}
