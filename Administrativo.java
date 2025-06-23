
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *@author Personal
 */

import java.time.LocalDate;

public class Administrativo extends Persona {
     public Administrativo(String nombre, String identificacion, String correoElectronico, LocalDate fechaRegistro) {
        super(nombre, identificacion, correoElectronico, fechaRegistro);
    }

    @Override
    public String getTipoPersona() {
        return "ADMINISTRATIVO";
    }

    // Métodos específicos para el personal administrativo
    public void registrarLibro(Biblioteca biblioteca, Libros libro) {
        biblioteca.agregarLibro(libro); // Modificado para usar agregarLibro
        System.out.println("Libro '" + libro.getTitulo() + "' registrado por el administrativo " + this.getNombre());
    }

    public void darDeBajaLibro(Biblioteca biblioteca, Libros libro) {
        // Implementación simple, podría ser más compleja (ej. mover a un historial)
        if (biblioteca.getLibros().remove(libro)) { // Modificado para usar getLibros
            System.out.println("Libro '" + libro.getTitulo() + "' dado de baja por el administrativo " + this.getNombre());
        } else {
            System.out.println("No se pudo dar de baja el libro '" + libro.getTitulo() + "'.");
        }
    }
    
    @Override
    public String toCSV() {
        // Formato: ADMINISTRATIVO;nombre;identificacion;correoElectronico;fechaRegistro
        return "ADMINISTRATIVO;" + nombre + ";" + identificacion + ";" + correoElectronico + ";" + fechaRegistro.toString();
    }
    
    public static Administrativo desdeCSV(String linea) {
        String[] datos = linea.split(";", -1);
        if (datos.length == 5 && datos[0].equals("ADMINISTRATIVO")) {
            return new Administrativo(datos[1], datos[2], datos[3], LocalDate.parse(datos[4]));
        }
        return null;
    }
}
