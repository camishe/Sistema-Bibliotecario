
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *
 * @author espe
 */

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    private Biblioteca biblioteca;
    private Scanner scanner;

    public Menu(Biblioteca biblioteca, Scanner scanner) {
        this.biblioteca = biblioteca;
        this.scanner = scanner;
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--- MENU PRINCIPAL DE LA BIBLIOTECA ---");
            System.out.println("1. Gestion de Libros");
            System.out.println("2. Gestion de Personas (Usuarios)");
            System.out.println("3. Gestion de Prestamos");
            System.out.println("4. Generar Reportes");
            System.out.println("5. Guardar Todos los Datos");
            System.out.println("0. Salir");
            opcion = Validaciones.leerEntero(scanner, "Opcion: ");

            switch (opcion) {
                case 1 -> menuLibros();
                case 2 -> menuPersonas();
                case 3 -> menuPrestamos();
                case 4 -> menuReportes();
                case 5 -> biblioteca.guardarTodosLosDatos("libros.csv", "personas.csv", "prestamos.csv");
                case 0 -> {
                    biblioteca.guardarTodosLosDatos("libros.csv", "personas.csv", "prestamos.csv");
                    System.out.println("Datos guardados! Hasta Pronto.");
                }
                default -> System.out.println("Opción invalida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void menuLibros() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DE GESTION DE LIBROS ---");
            System.out.println("1. Agregar libro");
            System.out.println("2. Mostrar todos los libros");
            System.out.println("3. Modificar estado de libro");
            System.out.println("0. Volver al Menu Principal");
            opcion = Validaciones.leerEntero(scanner, "Opcion: ");

            switch (opcion) {
                case 1 -> agregarLibro();
                case 2 -> biblioteca.mostrarLibros(); // Llamada al método actualizado
                case 3 -> modificarEstadoLibro();
                case 0 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void agregarLibro() {
        System.out.println("\n--- AGREGAR NUEVO LIBRO ---");
        String id = Validaciones.leerString(scanner, "ID del libro: ");
        // Verificar si el ID ya existe
        if (biblioteca.buscarLibroPorId(id) != null) {
            System.out.println("Error: Ya existe un libro con ese ID.");
            return;
        }
        String titulo = Validaciones.leerString(scanner, "Titulo: ");
        String autor = Validaciones.leerString(scanner, "Autor: ");
        int paginas = Validaciones.leerEntero(scanner, "Numero de paginas: ");
        String anioPublicacion = Validaciones.leerString(scanner, "Anio de publicacion: ");
        String genero = Validaciones.leerString(scanner, "Genero: ");
        EstadoLibro estado = Validaciones.leerEstadoLibro(scanner, "Estado inicial del libro");
        
        Libros nuevoLibro = new Libros(id, titulo, autor, paginas, anioPublicacion, genero, estado);
        biblioteca.agregarLibro(nuevoLibro); // Llamada al método actualizado
        System.out.println("Libro agregado exitosamente.");
    }
    
    private void modificarEstadoLibro() {
        biblioteca.mostrarLibros(); // Llamada al método actualizado
        if (biblioteca.getLibros().isEmpty()) return; // Usar getLibros()

        int index = Validaciones.leerEntero(scanner, "Ingrese el índice del libro a modificar: ");
        Libros libro = biblioteca.obtenerLibros(index); // Llamada al método actualizado
        
        if (libro != null) {
            System.out.println("Estado actual del libro '" + libro.getTitulo() + "': " + libro.getEstado());
            EstadoLibro nuevoEstado = Validaciones.leerEstadoLibro(scanner, "Ingrese el nuevo estado para el libro");
            libro.setEstado(nuevoEstado);
            System.out.println("Estado del libro '" + libro.getTitulo() + "' actualizado a " + nuevoEstado);
        } else {
            System.out.println("El índice seleccionado no corresponde a un libro o es invalido.");
        }
    }

    private void menuPersonas() {
        int opcion;
        do {
            System.out.println("\n--- MENU DE GESTION DE PERSONAS ---");
            System.out.println("1. Registrar nueva persona (Lector/Administrativo)");
            System.out.println("2. Mostrar todas las personas");
            System.out.println("0. Volver al Menu Principal");
            opcion = Validaciones.leerEntero(scanner, "Opcion: ");

            switch (opcion) {
                case 1 -> registrarPersona();
                case 2 -> biblioteca.mostrarPersonas();
                case 0 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opción invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarPersona() {
        System.out.println("\n--- REGISTRAR NUEVA PERSONA ---");
        String tipo = Validaciones.leerString(scanner, "Tipo de persona (LECTOR/ADMINISTRATIVO): ").toUpperCase();
        String nombre = Validaciones.leerString(scanner, "Nombre: ");
        String identificacion = Validaciones.leerString(scanner, "Identificacion: ");
        // Check if ID already exists
        if (biblioteca.getPersonas().stream().anyMatch(p -> p.getIdentificacion().equals(identificacion))) {
            System.out.println("Error: Ya existe una persona con esa identificacion.");
            return;
        }
        String email = Validaciones.leerString(scanner, "Correo Electronico: ");
        LocalDate fechaRegistro = LocalDate.now(); // Fecha actual de registro

        Persona nuevaPersona = null;
        switch (tipo) {
            case "LECTOR" -> nuevaPersona = new Lector(nombre, identificacion, email, fechaRegistro);
            case "ADMINISTRATIVO" -> nuevaPersona = new Administrativo(nombre, identificacion, email, fechaRegistro);
            default -> System.out.println("Tipo de persona invalido.");
        }

        if (nuevaPersona != null) {
            biblioteca.registrarPersona(nuevaPersona);
            System.out.println("Persona registrada exitosamente.");
        }
    }

    private void menuPrestamos() {
        int opcion;
        do {
            System.out.println("\n--- MENU DE GESTION DE PRESTAMOS ---");
            System.out.println("1. Realizar Prestamo");
            System.out.println("2. Registrar Devolucion");
            System.out.println("3. Mostrar Prestamos Activos");
            System.out.println("0. Volver al Menu Principal");
            opcion = Validaciones.leerEntero(scanner, "Opcion: ");

            switch (opcion) {
                case 1 -> realizarPrestamo();
                case 2 -> registrarDevolucion();
                case 3 -> biblioteca.mostrarPrestamosActuales();
                case 0 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void realizarPrestamo() {
        biblioteca.mostrarLibros(); // Llamada al método actualizado
        if (biblioteca.getLibros().isEmpty()) { // Usar getLibros()
            System.out.println("No hay libros disponibles para prestar.");
            return;
        }
        int indexLibro = Validaciones.leerEntero(scanner, "Indice del libro a prestar: ");
        Libros libroSeleccionado = biblioteca.obtenerLibros(indexLibro); // Llamada al método actualizado

        if (libroSeleccionado == null) {
            System.out.println("Seleccion invalida. Por favor, seleccione un libro valido.");
            return;
        }
        
        if (libroSeleccionado.getEstado() != EstadoLibro.DISPONIBLE) {
            System.out.println("El libro '" + libroSeleccionado.getTitulo() + "' no esta disponible para prestamo. Estado: " + libroSeleccionado.getEstado());
            return;
        }

        biblioteca.mostrarPersonas();
        if (biblioteca.getPersonas().isEmpty()) {
            System.out.println("No hay lectores registrados para realizar un prestamo.");
            return;
        }
        int indexPersona = Validaciones.leerEntero(scanner, "Indice del lector: ");
        Persona personaSeleccionada = biblioteca.obtenerPersona(indexPersona);

        if (!(personaSeleccionada instanceof Lector lector)) {
            System.out.println("Seleccion invalida. La persona debe ser un Lector.");
            return;
        }

        LocalDate fechaDevolucionEsperada = Validaciones.leerFecha(scanner, "Fecha de devolucion esperada");

        biblioteca.realizarPrestamo(libroSeleccionado, lector, fechaDevolucionEsperada);
    }

    private void registrarDevolucion() {
        biblioteca.mostrarPrestamosActuales();
        if (biblioteca.getPrestamos().isEmpty()) {
            System.out.println("No hay prestamos activos para devolver.");
            return;
        }
        
        // Es mejor pedir el ID del libro y el ID del lector para identificar el préstamo
        String idLibro = Validaciones.leerString(scanner, "ID del libro a devolver: ");
        String idLector = Validaciones.leerString(scanner, "ID del lector que devuelve: ");

        Object libro = biblioteca.buscarLibroPorId(idLibro);
        Lector lector = biblioteca.buscarLectorPorIdentificacion(idLector);

        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }
        if (lector == null) {
            System.out.println("Lector no encontrado.");
            return;
        }

        Prestamo prestamoEncontrado = null;
        for (Prestamo p : biblioteca.getPrestamos()) {
            // Asegurarse de que el préstamo esté activo (no devuelto)
            if (p.getLibros().equals(libro) && p.getLector().equals(lector) && p.getFechaDevolucionReal() == null) {
                prestamoEncontrado = p;
                break;
            }
        }

        if (prestamoEncontrado != null) {
            LocalDate fechaDevolucionReal = Validaciones.leerFecha(scanner, "Fecha de devolución real");
            biblioteca.registrarDevolucion(prestamoEncontrado, fechaDevolucionReal);
        } else {
            System.out.println("No se encontro un prestamo activo para ese libro y lector.");
        }
    }

    private void menuReportes() {
        int opcion;
        do {
            System.out.println("\n--- MENU DE REPORTES ---");
            System.out.println("1. Reporte de Estado de Libros");
            System.out.println("2. Reporte de Actividad de Lectores");
            System.out.println("0. Volver al Menu Principal");
            opcion = Validaciones.leerEntero(scanner, "Opcion: ");

            switch (opcion) {
                case 1 -> biblioteca.generarReporteLibros();
                case 2 -> biblioteca.generarReporteActividadLectores();
                case 0 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }
}
