
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *@author Personal
 */
import java.time.LocalDate;

public class Prestamo {
    private Libros libro;
    private Lector lector;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal; // Null si no ha sido devuelto

    public Prestamo(Libros libro, Lector lector, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this.libro = libro;
        this.lector = lector;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = null; // Inicialmente no devuelto
    }

    public Libros getLibros() {
        return libro;
    }

    public Lector getLector() {
        return lector;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
    
    public String toCSV() {
        // Formato: idLibro;idLector;fechaPrestamo;fechaDevolucionEsperada;fechaDevolucionReal (o "null")
        return libro.getId() + ";" + 
               lector.getIdentificacion() + ";" + 
               fechaPrestamo.toString() + ";" + 
               fechaDevolucionEsperada.toString() + ";" + 
               (fechaDevolucionReal != null ? fechaDevolucionReal.toString() : "null");
    }
    
    public static Prestamo desdeCSV(String linea, Biblioteca biblioteca) {
        String[] datos = linea.split(";", -1);
        if (datos.length == 5) {
            Libros libro = biblioteca.buscarLibroPorId(datos[0]);
            Lector lector = biblioteca.buscarLectorPorIdentificacion(datos[1]);
           LocalDate fechaPrestamo = LocalDate.parse(datos[2]);
            LocalDate fechaDevolucionEsperada = LocalDate.parse(datos[3]);
            LocalDate fechaDevolucionReal = "null".equals(datos[4]) ? null : LocalDate.parse(datos[4]);
            
            if (libro != null && lector != null) {
                Prestamo prestamo = new Prestamo(libro, lector, fechaPrestamo, fechaDevolucionEsperada);
                prestamo.setFechaDevolucionReal(fechaDevolucionReal);
                return prestamo;
            }
        }
        return null;
    }
}
