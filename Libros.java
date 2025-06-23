
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *@author espe
 */

//clase hija
public class Libros implements Prestable {
    private String id; // Atributo id movido directamente a Libro
    private String titulo; // Atributo titulo movido directamente a Libro
    private String autor;
    private int numPaginas;
    private String anioPublicacion;
    private String genero;
    private EstadoLibro estado;
        
    public Libros(String id, String titulo, String autor, int numPaginas, String anioPublicacion, 
            String genero, EstadoLibro estado) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.anioPublicacion = anioPublicacion;
        this.genero = genero;
        this.estado = estado;
    }
    
    // Constructor adaptado para la carga desde CSV con menos parámetros si el estado es por defecto
    public Libros(String id, String titulo, String autor, int numPaginas, String anioPublicacion, String genero) {
        this(id, titulo, autor, numPaginas, anioPublicacion, genero, EstadoLibro.DISPONIBLE); // Llama al constructor completo
    }

    // Getters para todos los atributos
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public String getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getGenero() {
        return genero;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    // Setters para los atributos (controlado según consideraciones)
    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public void setAnioPublicacion(String anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    @Override 
    public void prestar(){
        if(this.estado == EstadoLibro.DISPONIBLE){
            this.estado = EstadoLibro.PRESTADO;
            System.out.println("Libro '" + this.titulo + "' prestado exitosamente.");
        }else{
            System.out.println("El libro '" + this.titulo + "' no esta disponible para préstamo. Estado actual: " + this.estado);
        }
    }
    
    @Override
    public void devolver(){
        if(this.estado == EstadoLibro.PRESTADO){
            this.estado = EstadoLibro.DISPONIBLE;
            System.out.println("Libro '" + this.titulo + "' devuelto exitosamente.");
        }else{
            System.out.println("El libro '" + this.titulo + "' no estaba prestado.");
        }
    }
    
    public void mostrarInformacion(){
        System.out.println("ID: " + id + ", Titulo: " + titulo + ", Autor: " + autor + ", Paginas: " + numPaginas + ", Año: " + anioPublicacion + ", Genero: " + genero + ", Estado: " + estado);
    }
    
    public String toCSV(){
        // Usamos ; como delimitador para evitar conflictos si hay comas en los campos
        return id + ";" + titulo + ";" + autor + ";" + numPaginas + ";" + anioPublicacion + ";" + genero + ";" + estado.name(); 
    }
    
    public static Libros desdeCSV(String linea){
        String [] datos = linea.split(";", -1); // Split por ;
        if (datos.length == 7) { // Asegurarse de que hay todos los campos esperados
            return new Libros(datos[0], datos[1], datos[2], 
                             Integer.parseInt(datos[3]), datos[4], datos[5], 
                             EstadoLibro.valueOf(datos[6]));
        } else if (datos.length == 6){ // Si el estado no esta en el CSV, asumir DISPONIBLE
             return new Libros(datos[0], datos[1], datos[2], 
                             Integer.parseInt(datos[3]), datos[4], datos[5]);
        }
        return null; // O lanzar una excepción si el formato es incorrecto
    }
}

