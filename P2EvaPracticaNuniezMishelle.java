
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *@author espe
 */

import java.util.Scanner;

public class P2EvaPracticaNuniezMishelle {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        
        // Nombres de archivos para persistencia
        String librosFile = "libros.csv";
        String personasFile = "personas.csv";
        String prestamosFile = "prestamos.csv";

        // Cargar todos los datos al inicio
        biblioteca.cargarTodosLosDatos(librosFile, personasFile, prestamosFile);

        Menu gestorMenu = new Menu(biblioteca, scanner);
        gestorMenu.mostrarMenuPrincipal();
        
        scanner.close();
    }
}
