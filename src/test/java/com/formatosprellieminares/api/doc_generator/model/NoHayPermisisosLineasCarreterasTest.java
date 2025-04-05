package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NoHayPermisisosLineasCarreterasTest {

    @Test
    void testNoHayPermisisosLineasCarreterasCreation() {
        // Crear una instancia de NoHayPermisisosLineasCarreteras
        NoHayPermisisosLineasCarreteras documento = new NoHayPermisisosLineasCarreteras();

        // Establecer valores
        documento.setFromAddress("Calle 24 #15-30");
        documento.setDate("20/04/2023");
        documento.setFromName("Alberto Martínez");
        documento.setFromIdentification("79123456");
        documento.setFromLocation("Bogotá");
        documento.setFromPhone("3134567890");
        documento.setFromEmail("alberto.martinez@ejemplo.com");

        // Verificar que los valores se hayan establecido correctamente
        assertEquals("Calle 24 #15-30", documento.getFromAddress());
        assertEquals("20/04/2023", documento.getDate());
        assertEquals("Alberto Martínez", documento.getFromName());
        assertEquals("79123456", documento.getFromIdentification());
        assertEquals("Bogotá", documento.getFromLocation());
        assertEquals("3134567890", documento.getFromPhone());
        assertEquals("alberto.martinez@ejemplo.com", documento.getFromEmail());
    }

    @Test
    void testNoHayPermisisosLineasCarreterasConstructorWithArgs() {
        // Crear una instancia usando el constructor con argumentos
        NoHayPermisisosLineasCarreteras documento = new NoHayPermisisosLineasCarreteras(
                "Avenida 19 #100-50",
                "25/04/2023",
                "Carolina Herrera",
                "52345678",
                "Medellín",
                "3187654321",
                "carolina.herrera@ejemplo.com");

        // Verificar que la instancia se crea correctamente con los valores
        // proporcionados
        assertNotNull(documento);
        assertEquals("Avenida 19 #100-50", documento.getFromAddress());
        assertEquals("25/04/2023", documento.getDate());
        assertEquals("Carolina Herrera", documento.getFromName());
        assertEquals("52345678", documento.getFromIdentification());
        assertEquals("Medellín", documento.getFromLocation());
        assertEquals("3187654321", documento.getFromPhone());
        assertEquals("carolina.herrera@ejemplo.com", documento.getFromEmail());
    }

    @Test
    void testNoHayPermisisosLineasCarreterasEquality() {
        // Crear dos instancias con los mismos valores
        NoHayPermisisosLineasCarreteras documento1 = new NoHayPermisisosLineasCarreteras();
        documento1.setFromName("Jorge Fernández");
        documento1.setFromIdentification("80987654");
        documento1.setFromLocation("Cali");

        NoHayPermisisosLineasCarreteras documento2 = new NoHayPermisisosLineasCarreteras();
        documento2.setFromName("Jorge Fernández");
        documento2.setFromIdentification("80987654");
        documento2.setFromLocation("Cali");

        // Verificar igualdad usando equals (generado por Lombok)
        assertEquals(documento1, documento2);

        // Modificar un valor y verificar que ya no son iguales
        documento2.setFromLocation("Barranquilla");
        assertNotEquals(documento1, documento2);
    }

    @Test
    void testNoHayPermisisosLineasCarreterasToString() {
        // Crear una instancia con algunos valores
        NoHayPermisisosLineasCarreteras documento = new NoHayPermisisosLineasCarreteras();
        documento.setFromName("Lucía Gómez");
        documento.setFromIdentification("51234567");
        documento.setFromEmail("lucia.gomez@ejemplo.com");

        // Verificar que toString contiene la información esperada
        String toStringResult = documento.toString();
        assertTrue(toStringResult.contains("Lucía Gómez"));
        assertTrue(toStringResult.contains("51234567"));
        assertTrue(toStringResult.contains("lucia.gomez@ejemplo.com"));
    }
}