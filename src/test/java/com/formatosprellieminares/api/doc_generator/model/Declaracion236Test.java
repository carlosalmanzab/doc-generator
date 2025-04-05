package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Declaracion236Test {

    @Test
    void testDeclaracion236Creation() {
        // Crear una instancia de Declaracion236
        Declaracion236 declaracion = new Declaracion236();

        // Establecer valores
        declaracion.setYear("2023");
        declaracion.setFromName("Carlos Gómez");
        declaracion.setFromIdentification("1098765432");
        declaracion.setFromLocation("Medellín");
        declaracion.setFromProfessionalNumber("MT-123456");
        declaracion.setInstallationDescription("Instalación eléctrica residencial");
        declaracion.setProjectName("Edificio Torres del Sur");
        declaracion.setProjectLocation("Medellín");
        declaracion.setProjectAddress("Calle 50 #20-30");
        declaracion.setUserName("María López");
        declaracion.setUserIdentification("43210987");
        declaracion.setSignatureLocation("Medellín");
        declaracion.setSignatureDate("15/04/2023");
        declaracion.setSignature("Carlos Gómez");
        declaracion.setComments("La instalación cumple con el RETIE");
        declaracion.setAnnexedDocuments("Planos, memorias de cálculo");
        declaracion.setResponsibleAddress("Carrera 70 #45-60");
        declaracion.setPhone("3101234567");

        // Verificar que los valores se hayan establecido correctamente
        assertEquals("2023", declaracion.getYear());
        assertEquals("Carlos Gómez", declaracion.getFromName());
        assertEquals("1098765432", declaracion.getFromIdentification());
        assertEquals("Medellín", declaracion.getFromLocation());
        assertEquals("MT-123456", declaracion.getFromProfessionalNumber());
        assertEquals("Instalación eléctrica residencial", declaracion.getInstallationDescription());
        assertEquals("Edificio Torres del Sur", declaracion.getProjectName());
        assertEquals("Medellín", declaracion.getProjectLocation());
        assertEquals("Calle 50 #20-30", declaracion.getProjectAddress());
        assertEquals("María López", declaracion.getUserName());
        assertEquals("43210987", declaracion.getUserIdentification());
        assertEquals("Medellín", declaracion.getSignatureLocation());
        assertEquals("15/04/2023", declaracion.getSignatureDate());
        assertEquals("Carlos Gómez", declaracion.getSignature());
        assertEquals("La instalación cumple con el RETIE", declaracion.getComments());
        assertEquals("Planos, memorias de cálculo", declaracion.getAnnexedDocuments());
        assertEquals("Carrera 70 #45-60", declaracion.getResponsibleAddress());
        assertEquals("3101234567", declaracion.getPhone());
    }

    @Test
    void testDeclaracion236Equality() {
        // Crear dos instancias con los mismos valores
        Declaracion236 declaracion1 = new Declaracion236();
        declaracion1.setFromName("Luis Ramírez");
        declaracion1.setFromIdentification("5678901234");
        declaracion1.setProjectName("Conjunto Residencial Los Pinos");

        Declaracion236 declaracion2 = new Declaracion236();
        declaracion2.setFromName("Luis Ramírez");
        declaracion2.setFromIdentification("5678901234");
        declaracion2.setProjectName("Conjunto Residencial Los Pinos");

        // Verificar igualdad usando equals (generado por Lombok)
        assertEquals(declaracion1, declaracion2);

        // Modificar un valor y verificar que ya no son iguales
        declaracion2.setFromName("Pedro Martínez");
        assertNotEquals(declaracion1, declaracion2);
    }

    @Test
    void testDeclaracion236ToString() {
        // Crear una instancia con algunos valores
        Declaracion236 declaracion = new Declaracion236();
        declaracion.setFromName("Diana Sánchez");
        declaracion.setProjectName("Casa Campestre El Retiro");
        declaracion.setUserName("José Gutiérrez");

        // Verificar que toString contiene la información esperada
        String toStringResult = declaracion.toString();
        assertTrue(toStringResult.contains("Diana Sánchez"));
        assertTrue(toStringResult.contains("Casa Campestre El Retiro"));
        assertTrue(toStringResult.contains("José Gutiérrez"));
    }
}