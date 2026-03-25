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
public class Ticket {
    private int id;
    private int idUser;
    private int idEmp;
    private String user;
    private String empresa;
    private String tipo;
    private String detalhe;
    private String registro;
    private Date data;
    private Time hora;
    private String detalhefech;
    private Date dataf;
    private Time horaf;
    private String status;

    public Ticket() {
    }
    
    public Ticket(int id) {
        this.id = id;
    }
       
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
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

    public String getDetalhefech() {
        return detalhefech;
    }

    public void setDetalhefech(String detalhefech) {
        this.detalhefech = detalhefech;
    }

    public Date getDataf() {
        return dataf;
    }

    public void setDataf(Date dataf) {
        this.dataf = dataf;
    }

    public Time getHoraf() {
        return horaf;
    }

    public void setHoraf(Time horaf) {
        this.horaf = horaf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", idUser=" + idUser + ", idEmp=" + idEmp + ", user=" + user + ", "
                + "empresa=" + empresa + ", tipo=" + tipo + ", detalhe=" + detalhe + ", registro=" + registro + ", "
                + "data=" + data + ", hora=" + hora + ", detalhefech=" + detalhefech + ", dataf=" + dataf + ", "
                + "horaf=" + horaf + ", status=" + status + '}';
    }
        
}
