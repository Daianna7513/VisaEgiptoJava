package com.visaegipto.principal;

import com.visaegipto.dao.SolicitudDAO;
import com.visaegipto.modelo.Solicitud;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);
    private static final SolicitudDAO DAO = new SolicitudDAO();

    public static void main(String[] args) {

        int opcion;

        do {

            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    registrar();
                    break;

                case 2:
                    consultar();
                    break;

                case 3:
                    actualizar();
                    break;

                case 4:
                    eliminar();
                    break;

                case 5:
                    System.out.println("Aplicación finalizada.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }

    private static void mostrarMenu() {

        System.out.println();
        System.out.println("==============================================");
        System.out.println("   SISTEMA DE SOLICITUD DE VISA A EGIPTO");
        System.out.println("==============================================");
        System.out.println("1. Registrar solicitud");
        System.out.println("2. Consultar solicitudes");
        System.out.println("3. Actualizar solicitud");
        System.out.println("4. Eliminar solicitud");
        System.out.println("5. Salir");
        System.out.println("==============================================");
    }

    private static void registrar() {

        System.out.println();
        System.out.println("--- REGISTRAR SOLICITUD ---");

        String nombre =
                leerTexto("Nombre del solicitante: ", 100);

        String pasaporte =
                leerTexto("Número de pasaporte: ", 20);

        String visa =
                leerTexto("Tipo de visa: ", 50);

        LocalDate fecha =
                leerFecha("Fecha de solicitud (AAAA-MM-DD): ");

        String estado = leerEstado();

        Solicitud solicitud =
                new Solicitud(nombre, pasaporte, visa, fecha, estado);

        if (DAO.insertar(solicitud)) {
            System.out.println();
            System.out.println("SOLICITUD REGISTRADA CORRECTAMENTE.");
        } else {
            System.out.println();
            System.out.println("NO FUE POSIBLE REGISTRAR LA SOLICITUD.");
        }
    }

    private static void consultar() {

        System.out.println();
        System.out.println("--- CONSULTAR SOLICITUDES ---");

        List<Solicitud> lista = DAO.consultarTodos();

        if (lista.isEmpty()) {

            System.out.println("No hay solicitudes registradas.");

        } else {

            System.out.println();
            System.out.println("TOTAL DE SOLICITUDES: " + lista.size());
            System.out.println();

            for (Solicitud s : lista) {

                System.out.println("---------------------------------------");
                System.out.println("ID: " + s.getIdSolicitud());
                System.out.println("Nombre: " + s.getNombreSolicitante());
                System.out.println("Pasaporte: " + s.getNumeroPasaporte());
                System.out.println("Tipo de visa: " + s.getTipoVisa());
                System.out.println("Fecha: " + s.getFechaSolicitud());
                System.out.println("Estado: " + s.getEstado());
            }

            System.out.println("---------------------------------------");
        }

        System.out.println();
        System.out.println("Consulta finalizada.");
    }

    private static void actualizar() {

        System.out.println();
        System.out.println("--- ACTUALIZAR SOLICITUD ---");

        int id =
                leerEntero("ID de la solicitud que desea actualizar: ");

        String nombre =
                leerTexto("Nuevo nombre: ", 100);

        String pasaporte =
                leerTexto("Nuevo número de pasaporte: ", 20);

        String visa =
                leerTexto("Nuevo tipo de visa: ", 50);

        LocalDate fecha =
                leerFecha("Nueva fecha (AAAA-MM-DD): ");

        String estado = leerEstado();

        Solicitud solicitud =
                new Solicitud(id, nombre, pasaporte, visa, fecha, estado);

        if (DAO.actualizar(solicitud)) {
            System.out.println("SOLICITUD ACTUALIZADA CORRECTAMENTE.");
        } else {
            System.out.println("NO SE ENCONTRÓ LA SOLICITUD.");
        }
    }

    private static void eliminar() {

        System.out.println();
        System.out.println("--- ELIMINAR SOLICITUD ---");

        int id =
                leerEntero("ID de la solicitud que desea eliminar: ");

        if (DAO.eliminar(id)) {
            System.out.println("SOLICITUD ELIMINADA CORRECTAMENTE.");
        } else {
            System.out.println("NO SE ENCONTRÓ LA SOLICITUD.");
        }
    }

    private static String leerTexto(String mensaje, int maximo) {

        while (true) {

            System.out.print(mensaje);
            String valor = SC.nextLine().trim();

            if (valor.isEmpty()) {

                System.out.println(
                        "Validación: este campo es obligatorio.");

            } else if (valor.length() > maximo) {

                System.out.println(
                        "Validación: máximo "
                        + maximo
                        + " caracteres.");

            } else {

                return valor;
            }
        }
    }

    private static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String valor = SC.nextLine().trim();

            try {

                int numero = Integer.parseInt(valor);

                if (numero > 0) {
                    return numero;
                }

                System.out.println(
                        "Validación: debe ingresar un número positivo.");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Validación: ingrese un número entero válido.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String valor = SC.nextLine().trim();

            try {

                LocalDate fecha = LocalDate.parse(valor);

                if (fecha.isAfter(LocalDate.now())) {

                    System.out.println(
                            "Validación: la fecha no puede ser futura.");

                } else {

                    return fecha;
                }

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Validación: use el formato AAAA-MM-DD.");
            }
        }
    }

    private static String leerEstado() {

        while (true) {

            String estado =
                    leerTexto(
                            "Estado (Pendiente/En revisión/Aprobada/Rechazada): ",
                            30);

            if (estado.equalsIgnoreCase("Pendiente")
                    || estado.equalsIgnoreCase("En revisión")
                    || estado.equalsIgnoreCase("Aprobada")
                    || estado.equalsIgnoreCase("Rechazada")) {

                return estado;
            }

            System.out.println(
                    "Validación: estado no permitido.");
        }
    }
}