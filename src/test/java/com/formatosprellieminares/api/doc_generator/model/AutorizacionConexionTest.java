package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AutorizacionConexionTest {

    @Test
    void testAutorizacionConexionCreation() {
        // Crear una instancia de AutorizacionConexion
        AutorizacionConexion autorizacion = new AutorizacionConexion();

        // Establecer valores
        autorizacion.setFromAddress("Carrera 45 #23-67");
        autorizacion.setDate("10/04/2023");
        autorizacion.setFromName("Roberto Sánchez");
        autorizacion.setFromIdentification("12345678");
        autorizacion.setFromLocation("Bogotá");
        autorizacion.setToName("Empresa Eléctrica S.A.");
        autorizacion.setToIdentification("900123456");
        autorizacion.setToLocation("Bogotá");
        autorizacion.setFromName2("Roberto Sánchez");
        autorizacion.setFromIdentification2("12345678");
        autorizacion.setFromPhone("3157890123");
        autorizacion.setStructureCode("EST-001");
        autorizacion.setStructureLocation("Calle 50 con Carrera 23");
        autorizacion.setStructureType("Poste metálico");
        autorizacion.setStructureHeight("12 metros");
        autorizacion.setStructureMaterial("Acero galvanizado");
        autorizacion.setStructureCondition("Buena");
        autorizacion.setStructureOwner("Juan Rodríguez");
        autorizacion.setStructureOwnerId("87654321");
        autorizacion.setStructureOwnerAddress("Avenida 30 #45-12");
        autorizacion.setStructureOwnerPhone("3209876543");
        autorizacion.setStructureOwnerEmail("juan.rodriguez@ejemplo.com");
        autorizacion.setStructureOwnerSignature("Juan Rodríguez");
        autorizacion.setStructureOwnerDate("15/04/2023");
        autorizacion.setStructureOwnerWitness("Ana María López");
        autorizacion.setStructureOwnerWitnessId("56789012");
        autorizacion.setStructureOwnerWitnessSignature("Ana María López");
        autorizacion.setStructureOwnerWitnessDate("15/04/2023");

        // Verificar que los valores se hayan establecido correctamente
        assertEquals("Carrera 45 #23-67", autorizacion.getFromAddress());
        assertEquals("10/04/2023", autorizacion.getDate());
        assertEquals("Roberto Sánchez", autorizacion.getFromName());
        assertEquals("12345678", autorizacion.getFromIdentification());
        assertEquals("Bogotá", autorizacion.getFromLocation());
        assertEquals("Empresa Eléctrica S.A.", autorizacion.getToName());
        assertEquals("900123456", autorizacion.getToIdentification());
        assertEquals("Bogotá", autorizacion.getToLocation());
        assertEquals("Roberto Sánchez", autorizacion.getFromName2());
        assertEquals("12345678", autorizacion.getFromIdentification2());
        assertEquals("3157890123", autorizacion.getFromPhone());
        assertEquals("EST-001", autorizacion.getStructureCode());
        assertEquals("Calle 50 con Carrera 23", autorizacion.getStructureLocation());
        assertEquals("Poste metálico", autorizacion.getStructureType());
        assertEquals("12 metros", autorizacion.getStructureHeight());
        assertEquals("Acero galvanizado", autorizacion.getStructureMaterial());
        assertEquals("Buena", autorizacion.getStructureCondition());
        assertEquals("Juan Rodríguez", autorizacion.getStructureOwner());
        assertEquals("87654321", autorizacion.getStructureOwnerId());
        assertEquals("Avenida 30 #45-12", autorizacion.getStructureOwnerAddress());
        assertEquals("3209876543", autorizacion.getStructureOwnerPhone());
        assertEquals("juan.rodriguez@ejemplo.com", autorizacion.getStructureOwnerEmail());
        assertEquals("Juan Rodríguez", autorizacion.getStructureOwnerSignature());
        assertEquals("15/04/2023", autorizacion.getStructureOwnerDate());
        assertEquals("Ana María López", autorizacion.getStructureOwnerWitness());
        assertEquals("56789012", autorizacion.getStructureOwnerWitnessId());
        assertEquals("Ana María López", autorizacion.getStructureOwnerWitnessSignature());
        assertEquals("15/04/2023", autorizacion.getStructureOwnerWitnessDate());
    }

    @Test
    void testAutorizacionConexionConstructorWithArgs() {
        // Crear una instancia usando el constructor con argumentos
        AutorizacionConexion autorizacion = new AutorizacionConexion(
                "Calle 28 #10-15", "05/05/2023", "Marta Jiménez", "98765432", "Cali",
                "Distribuidora Energía", "800987654", "Cali", "Marta Jiménez", "98765432",
                "3112345678", "EST-002", "Avenida 5 con Calle 10", "Poste de concreto",
                "10 metros", "Concreto reforzado", "Regular", "Pedro López", "54321678",
                "Calle 15 #8-20", "3145678901", "pedro.lopez@ejemplo.com", "Pedro López",
                "06/05/2023", "Carmen Ortiz", "12345670", "Carmen Ortiz", "06/05/2023");

        // Verificar que la instancia se crea correctamente con los valores
        // proporcionados
        assertNotNull(autorizacion);
        assertEquals("Calle 28 #10-15", autorizacion.getFromAddress());
        assertEquals("05/05/2023", autorizacion.getDate());
        assertEquals("Marta Jiménez", autorizacion.getFromName());
        assertEquals("Distribuidora Energía", autorizacion.getToName());
        assertEquals("EST-002", autorizacion.getStructureCode());
        assertEquals("Pedro López", autorizacion.getStructureOwner());
        assertEquals("Carmen Ortiz", autorizacion.getStructureOwnerWitness());
    }

    @Test
    void testAutorizacionConexionEquality() {
        // Crear dos instancias con los mismos valores
        AutorizacionConexion autorizacion1 = new AutorizacionConexion();
        autorizacion1.setFromName("Laura Torres");
        autorizacion1.setStructureType("Poste de madera");
        autorizacion1.setStructureHeight("8 metros");

        AutorizacionConexion autorizacion2 = new AutorizacionConexion();
        autorizacion2.setFromName("Laura Torres");
        autorizacion2.setStructureType("Poste de madera");
        autorizacion2.setStructureHeight("8 metros");

        // Verificar igualdad usando equals (generado por Lombok)
        assertEquals(autorizacion1, autorizacion2);

        // Modificar un valor y verificar que ya no son iguales
        autorizacion2.setStructureHeight("9 metros");
        assertNotEquals(autorizacion1, autorizacion2);
    }

    @Test
    void testAutorizacionConexionToString() {
        // Crear una instancia con algunos valores
        AutorizacionConexion autorizacion = new AutorizacionConexion();
        autorizacion.setFromName("Andrés Moreno");
        autorizacion.setStructureCode("EST-003");
        autorizacion.setStructureOwner("Gloria Vargas");

        // Verificar que toString contiene la información esperada
        String toStringResult = autorizacion.toString();
        assertTrue(toStringResult.contains("Andrés Moreno"));
        assertTrue(toStringResult.contains("EST-003"));
        assertTrue(toStringResult.contains("Gloria Vargas"));
    }
}