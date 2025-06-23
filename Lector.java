
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *@author Personal
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lector extends Persona{
    private List<Prestamo> prestamosActuales; // Para llevar un registro de lo que tiene prestado

    public Lector(String nombre, String identificacion, String correoElectronico, LocalDate fechaRegistro) {
        super(nombre, identificacion, correoElectronico, fechaRegistro);
        this.prestamosActuales = new ArrayList<>();
    }

    @Override
    public String getTipoPersona() {
        return "LECTOR";
    }

    public List<Prestamo> getPrestamosActuales() {
        return prestamosActuales;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        this.prestamosActuales.add(prestamo);
    }

    public void removerPrestamo(Prestamo prestamo) {
        this.prestamosActuales.remove(prestamo);
    }
    
    @Override
    public String toCSV() {
        // Formato: LECTOR;nombre;identificacion;correoElectronico;fechaRegistro
        return "LECTOR;" + nombre + ";" + identificacion + ";" + correoElectronico + ";" + fechaRegistro.toString();
    }
    
    public static Lector desdeCSV(String linea) {
        String[] datos = linea.split(";", -1);
        if (datos.length == 5 && datos[0].equals("LECTOR")) {
            return new Lector(datos[1], datos[2], datos[3], LocalDate.parse(datos[4]));
        }
        return null;
    }
}
