package org.alejandroflores.bean;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServicioHasEmpleado {
    
    private int Servicios_codigoServicio;
    private int codigoServicio;
    private int codigoEmpleado;
    private Date fechaEvento;
    private Time horaEvento;
    private String lugarEvento;

    public ServicioHasEmpleado() {
    }

    public ServicioHasEmpleado(int Servicios_codigoServicio, int codigoServicio, int codigoEmpleado, Date fechaEvento, Time horaEvento, String lugarEvento) {
        this.Servicios_codigoServicio = Servicios_codigoServicio;
        this.codigoServicio = codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
    }

    public int getServicios_codigoServicio() {
        return Servicios_codigoServicio;
    }

    public void setServicios_codigoServicio(int Servicios_codigoServicio) {
        this.Servicios_codigoServicio = Servicios_codigoServicio;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Time getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(Time horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }
    
    public String getHora(){
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String hora = formato.format(horaEvento);
        return hora.substring(0,2);
    }
    
    public String getMinuto(){
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String minuto = formato.format(horaEvento);
        return minuto.substring(3,5);
    }
    
}
