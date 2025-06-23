
package com.mycompany.p2evapracticanuniezmishelle;

/**
 *
 * @author espe
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validaciones {
    
     public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero entero.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    public static String leerString(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
    
    public static LocalDate leerFecha(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (YYYY-MM-DD): ");
            String fechaStr = scanner.nextLine();
            try {
                return LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha invalido. Por favor, use YYYY-MM-DD.");
            }
        }
    }
    
    public static EstadoLibro leerEstadoLibro(Scanner scanner, String mensaje) {
        while (true) {
            System.out.println(mensaje + " (DISPONIBLE, PRESTADO, EN_REPARACION, EXTRAVIADO): ");
            String estadoStr = scanner.nextLine().toUpperCase();
            try {
                return EstadoLibro.valueOf(estadoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Estado invalido. Por favor, elija uno de los estados permitidos.");
            }
        }
    }
}
