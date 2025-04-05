package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OtorgamientoServidumbreTest {

    @Test
    void testOtorgamientoServidumbreCreation() {
        // Crear una instancia de OtorgamientoServidumbre
        OtorgamientoServidumbre servidumbre = new OtorgamientoServidumbre();

        // Establecer valores
        servidumbre.setDate("01/05/2023");
        servidumbre.setFromName("Juan Pérez");
        servidumbre.setFromIdentification("1234567890");
        servidumbre.setFromLocation("Bogotá");
        servidumbre.setPropertyName("Finca El Descanso");
        servidumbre.setPropertyRegistry("Registro-123");
        servidumbre.setProjectLocation("Cundinamarca");
        servidumbre.setProjectAddress("Calle 123 #45-67");
        servidumbre.setSafetyWidth("5 metros");
        servidumbre.setSafetyLength("100 metros");
        servidumbre.setNetworkVoltage("110kV");
        servidumbre.setNetworkType("Aérea");
        servidumbre.setSignatureDay("15");
        servidumbre.setSignatureMonth("Mayo");
        servidumbre.setSignatureYear("2023");
        servidumbre.setDepartment("Cundinamarca");
        servidumbre.setSignature("Juan Pérez");
        servidumbre.setPhone("3001234567");
        servidumbre.setFromAddress("Calle Principal #10-20");
        servidumbre.setEmail("juan.perez@ejemplo.com");

        // Verificar que los valores se hayan establecido correctamente
        assertEquals("01/05/2023", servidumbre.getDate());
        assertEquals("Juan Pérez", servidumbre.getFromName());
        assertEquals("1234567890", servidumbre.getFromIdentification());
        assertEquals("Bogotá", servidumbre.getFromLocation());
        assertEquals("Finca El Descanso", servidumbre.getPropertyName());
        assertEquals("Registro-123", servidumbre.getPropertyRegistry());
        assertEquals("Cundinamarca", servidumbre.getProjectLocation());
        assertEquals("Calle 123 #45-67", servidumbre.getProjectAddress());
        assertEquals("5 metros", servidumbre.getSafetyWidth());
        assertEquals("100 metros", servidumbre.getSafetyLength());
        assertEquals("110kV", servidumbre.getNetworkVoltage());
        assertEquals("Aérea", servidumbre.getNetworkType());
        assertEquals("15", servidumbre.getSignatureDay());
        assertEquals("Mayo", servidumbre.getSignatureMonth());
        assertEquals("2023", servidumbre.getSignatureYear());
        assertEquals("Cundinamarca", servidumbre.getDepartment());
        assertEquals("Juan Pérez", servidumbre.getSignature());
        assertEquals("3001234567", servidumbre.getPhone());
        assertEquals("Calle Principal #10-20", servidumbre.getFromAddress());
        assertEquals("juan.perez@ejemplo.com", servidumbre.getEmail());
    }

    @Test
    void testOtorgamientoServidumbreEquality() {
        // Crear dos instancias con los mismos valores
        OtorgamientoServidumbre servidumbre1 = new OtorgamientoServidumbre();
        servidumbre1.setFromName("Ana López");
        servidumbre1.setFromIdentification("9876543210");

        OtorgamientoServidumbre servidumbre2 = new OtorgamientoServidumbre();
        servidumbre2.setFromName("Ana López");
        servidumbre2.setFromIdentification("9876543210");

        // Verificar igualdad usando equals (generado por Lombok)
        assertEquals(servidumbre1, servidumbre2);

        // Modificar un valor y verificar que ya no son iguales
        servidumbre2.setFromName("Carlos Rodríguez");
        assertNotEquals(servidumbre1, servidumbre2);
    }

    @Test
    void testOtorgamientoServidumbreToString() {
        // Crear una instancia con algunos valores
        OtorgamientoServidumbre servidumbre = new OtorgamientoServidumbre();
        servidumbre.setFromName("Pedro Gómez");
        servidumbre.setPropertyName("Lote Los Pinos");

        // Verificar que toString contiene la información esperada
        String toStringResult = servidumbre.toString();
        assertTrue(toStringResult.contains("Pedro Gómez"));
        assertTrue(toStringResult.contains("Lote Los Pinos"));
    }
}