
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *author Personal
 */
import java.time.LocalDate;

//clase base (clase padre)
public abstract class Persona {
    protected String nombre;
    protected String identificacion;
    protected String correoElectronico;
    protected LocalDate fechaRegistro;

    public Persona(String nombre, String identificacion, String correoElectronico, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correoElectronico = correoElectronico;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    // Metodo abstracto para obtener el tipo de persona
    public abstract String getTipoPersona();
    
    // Metodo abstracto para convertir a CSV
    public abstract String toCSV();
}
