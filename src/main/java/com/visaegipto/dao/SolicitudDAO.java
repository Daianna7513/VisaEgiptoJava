package com.visaegipto.dao;

import com.visaegipto.conexion.ConexionBD;
import com.visaegipto.modelo.Solicitud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {

    public boolean insertar(Solicitud solicitud) {

        String sql = "INSERT INTO solicitud "
                + "(nombre_solicitante, numero_pasaporte, tipo_visa, fecha_solicitud, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, solicitud.getNombreSolicitante());
            ps.setString(2, solicitud.getNumeroPasaporte());
            ps.setString(3, solicitud.getTipoVisa());
            ps.setDate(4, Date.valueOf(solicitud.getFechaSolicitud()));
            ps.setString(5, solicitud.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR: " + e.getMessage());
            return false;
        }
    }

    public List<Solicitud> consultarTodos() {

        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT id_solicitud, nombre_solicitante, "
                + "numero_pasaporte, tipo_visa, fecha_solicitud, estado "
                + "FROM solicitud ORDER BY id_solicitud";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Solicitud solicitud = new Solicitud(
                        rs.getInt("id_solicitud"),
                        rs.getString("nombre_solicitante"),
                        rs.getString("numero_pasaporte"),
                        rs.getString("tipo_visa"),
                        rs.getDate("fecha_solicitud").toLocalDate(),
                        rs.getString("estado")
                );

                lista.add(solicitud);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CONSULTAR: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizar(Solicitud solicitud) {

        String sql = "UPDATE solicitud SET "
                + "nombre_solicitante=?, "
                + "numero_pasaporte=?, "
                + "tipo_visa=?, "
                + "fecha_solicitud=?, "
                + "estado=? "
                + "WHERE id_solicitud=?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, solicitud.getNombreSolicitante());
            ps.setString(2, solicitud.getNumeroPasaporte());
            ps.setString(3, solicitud.getTipoVisa());
            ps.setDate(4, Date.valueOf(solicitud.getFechaSolicitud()));
            ps.setString(5, solicitud.getEstado());
            ps.setInt(6, solicitud.getIdSolicitud());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTUALIZAR: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {

        String sql = "DELETE FROM solicitud WHERE id_solicitud=?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR: " + e.getMessage());
            return false;
        }
    }
}