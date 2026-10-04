package com.visaegipto.modelo;

import java.time.LocalDate;

public class Solicitud {

    private int idSolicitud;
    private String nombreSolicitante;
    private String numeroPasaporte;
    private String tipoVisa;
    private LocalDate fechaSolicitud;
    private String estado;

    public Solicitud() {
    }

    public Solicitud(String nombreSolicitante,
                     String numeroPasaporte,
                     String tipoVisa,
                     LocalDate fechaSolicitud,
                     String estado) {

        this.nombreSolicitante = nombreSolicitante;
        this.numeroPasaporte = numeroPasaporte;
        this.tipoVisa = tipoVisa;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public Solicitud(int idSolicitud,
                     String nombreSolicitante,
                     String numeroPasaporte,
                     String tipoVisa,
                     LocalDate fechaSolicitud,
                     String estado) {

        this.idSolicitud = idSolicitud;
        this.nombreSolicitante = nombreSolicitante;
        this.numeroPasaporte = numeroPasaporte;
        this.tipoVisa = tipoVisa;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public String getNumeroPasaporte() {
        return numeroPasaporte;
    }

    public String getTipoVisa() {
        return tipoVisa;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }
}