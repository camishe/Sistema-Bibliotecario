
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *@author Personal
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Biblioteca {
    private List<Libros> libros; // Cambiado de Material a Libro
    private List<Persona> personas;
    private List<Prestamo> prestamos;
    
    public Biblioteca() {
        libros = new ArrayList<>();
        personas = new ArrayList<>();
        prestamos = new ArrayList<>();
    }
    
    public List<Libros> getLibros() { // Getter para la lista de libros
        return libros;
    }
    

    public List<Persona> getPersonas() {
        return personas;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    // Métodos para Libros
    public void agregarLibro(Libros libro) { // Cambiado el parámetro
        libros.add(libro);
        System.out.println("Libro agregado: " + libro.getTitulo());
    }

    public void mostrarLibros() { // Cambiado el nombre del método
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la biblioteca.");
            return;
        }
        System.out.println("\n--- LISTA DE LIBROS ---");
        for (int i = 0; i < libros.size(); i++) {
            Libros libro = libros.get(i);
            System.out.print(i + ". ");
            libro.mostrarInformacion();
        }
    }

    public Libros obtenerLibros(int index) { // Cambiado el nombre del método y el tipo de retorno
        if (index >= 0 && index < libros.size()) {
            return libros.get(index);
        }
        return null;
    }
    
    public Libros buscarLibroPorId(String id) {
        for (Libros libro : libros) {
            if (libro.getId().equals(id)) {
                return libro;
            }
        }
        return null;
    }

    // Métodos para Personas
    public void registrarPersona(Persona p) {
        personas.add(p);
        System.out.println("Persona registrada: " + p.getNombre() + " (" + p.getTipoPersona() + ")");
    }

    public void mostrarPersonas() {
        if (personas.isEmpty()) {
            System.out.println("No hay personas registradas en el sistema.");
            return;
        }
        System.out.println("\n--- LISTA DE PERSONAS ---");
        for (int i = 0; i < personas.size(); i++) {
            Persona p = personas.get(i);
            System.out.println(i + ". Nombre: " + p.getNombre() + ", ID: " + p.getIdentificacion() + 
                               ", Tipo: " + p.getTipoPersona() + ", Email: " + p.getCorreoElectronico() + 
                               ", Fecha Registro: " + p.getFechaRegistro());
        }
    }
    
    public Persona obtenerPersona(int index) {
        if (index >= 0 && index < personas.size()) {
            return personas.get(index);
        }
        return null;
    }
    
    public Lector buscarLectorPorIdentificacion(String id) {
        for (Persona p : personas) {
            if (p instanceof Lector lector && lector.getIdentificacion().equals(id)) {
                return lector;
            }
        }
        return null;
    }

    // Métodos para Préstamos
    public void realizarPrestamo(Libros libro, Lector lector, LocalDate fechaDevolucionEsperada) {
        if (libro.getEstado() == EstadoLibro.DISPONIBLE) {
            Prestamo prestamo = new Prestamo(libro, lector, LocalDate.now(), fechaDevolucionEsperada);
            prestamos.add(prestamo);
            libro.prestar(); // Cambia el estado del libro
            lector.agregarPrestamo(prestamo); // Registra el préstamo en el lector
            System.out.println("Prestamo realizado: '" + libro.getTitulo() + "' a " + lector.getNombre());
        } else {
            System.out.println("No se puede prestar '" + libro.getTitulo() + "'. Estado actual: " + libro.getEstado());
        }
    }

    public void registrarDevolucion(Prestamo prestamo, LocalDate fechaDevolucionReal) {
        if (prestamo.getFechaDevolucionReal() == null) { // Solo si no ha sido devuelto ya
            prestamo.setFechaDevolucionReal(fechaDevolucionReal);
            prestamo.getLibros().devolver(); // Cambia el estado del libro a DISPONIBLE
            prestamo.getLector().removerPrestamo(prestamo); // Remueve el préstamo del lector
            System.out.println("Devolucion registrada para '" + prestamo.getLibros().getTitulo() + "'");
        } else {
            System.out.println("El prestamo de '" + prestamo.getLibros().getTitulo() + "' ya fue devuelto.");
        }
    }

    public void mostrarPrestamosActuales() {
        List<Prestamo> activos = prestamos.stream()
                                          .filter(p -> p.getFechaDevolucionReal() == null)
                                          .collect(Collectors.toList());
        
        if (activos.isEmpty()) {
            System.out.println("No hay prestamos activos en este momento.");
            return;
        }
        System.out.println("\n--- PRESTAMOS ACTIVOS ---");
        for (int i = 0; i < activos.size(); i++) {
            Prestamo p = activos.get(i);
            System.out.println(i + ". Libro: '" + p.getLibros().getTitulo() + "' (ID: " + p.getLibros().getId() + 
                               "), Lector: " + p.getLector().getNombre() + " (ID: " + p.getLector().getIdentificacion() + 
                               "), F. Préstamo: " + p.getFechaPrestamo() + 
                               ", F. Devolucion Esperada: " + p.getFechaDevolucionEsperada());
        }
    }
    
    // Generación de reportes
    public void generarReporteLibros() {
        System.out.println("\n--- REPORTE DE ESTADO DE LIBROS ---");
        if (libros.isEmpty()) {
            System.out.println("No hay libros para reportar.");
            return;
        }
        for (Libros libro : libros) { // Iterar directamente sobre libros
            System.out.println("Libro: '" + libro.getTitulo() + "', ID: " + libro.getId() + ", Estado: " + libro.getEstado());
        }
    }

    public void generarReporteActividadLectores() {
        System.out.println("\n--- REPORTE DE ACTIVIDAD DE LECTORES ---");
        if (personas.isEmpty()) {
            System.out.println("No hay lectores registrados.");
            return;
        }
        for (Persona p : personas) {
            if (p instanceof Lector lector) {
                System.out.println("Lector: " + lector.getNombre() + " (ID: " + lector.getIdentificacion() + ")");
                if (lector.getPrestamosActuales().isEmpty()) {
                    System.out.println("  - No tiene préstamos activos.");
                } else {
                    System.out.println("  - Prestamos activos:");
                    for (Prestamo prestamo : lector.getPrestamosActuales()) {
                        System.out.println("    - Libro: '" + prestamo.getLibros().getTitulo() + 
                                           "', F. Prestamo: " + prestamo.getFechaPrestamo() + 
                                           ", F. Devolucion Esperada: " + prestamo.getFechaDevolucionEsperada());
                    }
                }
                long totalPrestamos = prestamos.stream()
                                            .filter(pr -> pr.getLector().getIdentificacion().equals(lector.getIdentificacion()))
                                            .count();
                System.out.println("  - Total de prestamos (historico): " + totalPrestamos);
            }
        }
    }

    // Persistencia CSV
    public void guardarLibrosCSV(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Libros libro : libros) { // Iterar directamente sobre libros
                writer.println(libro.toCSV());
            }
            System.out.println("Libros guardados en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error guardando libros: " + e.getMessage());
        }
    }

    public void cargarLibrosCSV(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Libros libro = Libros.desdeCSV(linea);
                if (libro != null) {
                    libros.add(libro); // Agregar directamente a la lista de libros
                }
            }
            System.out.println("Libros cargados desde " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("No se pudo cargar libros: " + e.getMessage());
        }
    }
    
    public void guardarPersonasCSV(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Persona p : personas) {
                writer.println(p.toCSV());
            }
            System.out.println("Personas guardadas en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error guardando personas: " + e.getMessage());
        }
    }

    public void cargarPersonasCSV(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("LECTOR")) {
                    Lector lector = Lector.desdeCSV(linea);
                    if (lector != null) {
                        personas.add(lector);
                    }
                } else if (linea.startsWith("ADMINISTRATIVO")) {
                    Administrativo admin = Administrativo.desdeCSV(linea);
                    if (admin != null) {
                        personas.add(admin);
                    }
                }
            }
            System.out.println("Personas cargadas desde " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("No se pudo cargar personas: " + e.getMessage());
        }
    }
    
    public void guardarPrestamosCSV(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Prestamo p : prestamos) {
                writer.println(p.toCSV());
            }
            System.out.println("Prestamos guardados en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error guardando préstamos: " + e.getMessage());
        }
    }

    public void cargarPrestamosCSV(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Prestamo prestamo = Prestamo.desdeCSV(linea, this); // Pasa la instancia de Biblioteca para buscar libro/lector
                if (prestamo != null) {
                    prestamos.add(prestamo);
                    // Asegurarse de que el estado del libro y los préstamos del lector se actualicen
                    if (prestamo.getFechaDevolucionReal() == null) { // Si el préstamo está activo
                         if (prestamo.getLibros().getEstado() != EstadoLibro.PRESTADO) {
                             prestamo.getLibros().setEstado(EstadoLibro.PRESTADO);
                         }
                         if (!prestamo.getLector().getPrestamosActuales().contains(prestamo)) {
                             prestamo.getLector().agregarPrestamo(prestamo);
                         }
                    } else { // Si ya fue devuelto
                        if (prestamo.getLibros().getEstado() == EstadoLibro.PRESTADO) {
                            // Si se cargó un libro prestado que ya fue devuelto, corregir su estado
                            prestamo.getLibros().setEstado(EstadoLibro.DISPONIBLE);
                        }
                    }
                }
            }
            System.out.println("Prestamos cargados desde " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("No se pudo cargar prestamos: " + e.getMessage());
        }
    }
    
    // Método para cargar todos los datos
    public void cargarTodosLosDatos(String librosFile, String personasFile, String prestamosFile) {
        cargarLibrosCSV(librosFile);
        cargarPersonasCSV(personasFile);
        cargarPrestamosCSV(prestamosFile); // Cargar préstamos al final para que libros y personas existan
    }
    
    // Método para guardar todos los datos
    public void guardarTodosLosDatos(String librosFile, String personasFile, String prestamosFile) {
        guardarLibrosCSV(librosFile);
        guardarPersonasCSV(personasFile);
        guardarPrestamosCSV(prestamosFile);
    }
}
