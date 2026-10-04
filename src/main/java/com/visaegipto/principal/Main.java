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
                case 1 -> registrar();
                case 2 -> consultar();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 5 -> System.out.println("Aplicación finalizada.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE SOLICITUD DE VISA A EGIPTO ===");
        System.out.println("1. Registrar solicitud");
        System.out.println("2. Consultar solicitudes");
        System.out.println("3. Actualizar solicitud");
        System.out.println("4. Eliminar solicitud");
        System.out.println("5. Salir");
    }

    private static void registrar() {
        System.out.println("\n--- Registrar solicitud ---");
        String nombre = leerTexto("Nombre del solicitante: ", 100);
        String pasaporte = leerTexto("Número de pasaporte: ", 20);
        String visa = leerTexto("Tipo de visa: ", 50);
        LocalDate fecha = leerFecha("Fecha de solicitud (AAAA-MM-DD): ");
        String estado = leerEstado();

        boolean ok = DAO.insertar(new Solicitud(nombre, pasaporte, visa, fecha, estado));
        System.out.println(ok ? "Solicitud registrada correctamente." : "No se pudo registrar.");
    }

   private static void consultar() {
    List<Solicitud> lista = DAO.consultarTodos();

    System.out.println("\n=== LISTA DE SOLICITUDES ===");

    if (lista.isEmpty()) {
        System.out.println("No hay solicitudes registradas.");
    } else {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-18s %-15s %-12s %-15s%n",
                "ID", "Nombre", "Pasaporte", "Tipo visa", "Fecha", "Estado");
        System.out.println("------------------------------------------------------------");

        for (Solicitud s : lista) {
            System.out.printf("%-5d %-20s %-18s %-15s %-12s %-15s%n",
                    s.getIdSolicitud(),
                    s.getNombreSolicitante(),
                    s.getNumeroPasaporte(),
                    s.getTipoVisa(),
                    s.getFechaSolicitud(),
                    s.getEstado());
        }

        System.out.println("------------------------------------------------------------");
    }
}

    private static void actualizar() {
        System.out.println("\n--- Actualizar solicitud ---");
        int id = leerEntero("ID de la solicitud: ");
        String nombre = leerTexto("Nuevo nombre: ", 100);
        String pasaporte = leerTexto("Nuevo pasaporte: ", 20);
        String visa = leerTexto("Nuevo tipo de visa: ", 50);
        LocalDate fecha = leerFecha("Nueva fecha (AAAA-MM-DD): ");
        String estado = leerEstado();

        boolean ok = DAO.actualizar(new Solicitud(id, nombre, pasaporte, visa, fecha, estado));
        System.out.println(ok ? "Solicitud actualizada correctamente." : "No se encontró la solicitud.");
    }

    private static void eliminar() {
        System.out.println("\n--- Eliminar solicitud ---");
        int id = leerEntero("ID a eliminar: ");
        boolean ok = DAO.eliminar(id);
        System.out.println(ok ? "Solicitud eliminada correctamente." : "No se encontró la solicitud.");
    }

    private static String leerTexto(String mensaje, int max) {
        while (true) {
            System.out.print(mensaje);
            String valor = SC.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("Validación: el campo es obligatorio.");
            } else if (valor.length() > max) {
                System.out.println("Validación: máximo " + max + " caracteres.");
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
                if (numero > 0) return numero;
                System.out.println("Validación: debe ser un número positivo.");
            } catch (NumberFormatException e) {
                System.out.println("Validación: ingrese un número entero válido.");
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
                    System.out.println("Validación: la fecha no puede ser futura.");
                } else {
                    return fecha;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Validación: use el formato AAAA-MM-DD.");
            }
        }
    }

    private static String leerEstado() {
        while (true) {
            String estado = leerTexto("Estado (Pendiente/En revisión/Aprobada/Rechazada): ", 30);
            if (estado.equalsIgnoreCase("Pendiente") ||
                estado.equalsIgnoreCase("En revisión") ||
                estado.equalsIgnoreCase("Aprobada") ||
                estado.equalsIgnoreCase("Rechazada")) {
                return estado;
            }
            System.out.println("Validación: estado no permitido.");
        }
    }
}
