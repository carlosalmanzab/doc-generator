package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AutorizacionTramitesTest {

    @Test
    void testAutorizacionTramitesCreation() {
        // Crear una instancia de AutorizacionTramites
        AutorizacionTramites autorizacion = new AutorizacionTramites();

        // Establecer valores
        autorizacion.setFromAddress("Calle 80 #20-30");
        autorizacion.setDate("22/05/2023");
        autorizacion.setFromName("Miguel Ángel Rodríguez");
        autorizacion.setFromIdentification("71234567");
        autorizacion.setFromLocation("Bogotá");
        autorizacion.setToName("Ministerio de Minas y Energía");
        autorizacion.setToIdentification("899999022");
        autorizacion.setToLocation("Bogotá D.C.");
        autorizacion.setProjectName("Electrificación Rural Vereda La Esperanza");
        autorizacion.setProjectLocation("Cundinamarca");
        autorizacion.setProjectAddress("Vereda La Esperanza, Municipio de Cota");
        autorizacion.setFromName2("Miguel Ángel Rodríguez");
        autorizacion.setFromIdentification2("71234567");
        autorizacion.setFromPhone("3502345678");
        autorizacion.setFromEmail("miguel.rodriguez@ejemplo.com");

        // Verificar que los valores se hayan establecido correctamente
        assertEquals("Calle 80 #20-30", autorizacion.getFromAddress());
        assertEquals("22/05/2023", autorizacion.getDate());
        assertEquals("Miguel Ángel Rodríguez", autorizacion.getFromName());
        assertEquals("71234567", autorizacion.getFromIdentification());
        assertEquals("Bogotá", autorizacion.getFromLocation());
        assertEquals("Ministerio de Minas y Energía", autorizacion.getToName());
        assertEquals("899999022", autorizacion.getToIdentification());
        assertEquals("Bogotá D.C.", autorizacion.getToLocation());
        assertEquals("Electrificación Rural Vereda La Esperanza", autorizacion.getProjectName());
        assertEquals("Cundinamarca", autorizacion.getProjectLocation());
        assertEquals("Vereda La Esperanza, Municipio de Cota", autorizacion.getProjectAddress());
        assertEquals("Miguel Ángel Rodríguez", autorizacion.getFromName2());
        assertEquals("71234567", autorizacion.getFromIdentification2());
        assertEquals("3502345678", autorizacion.getFromPhone());
        assertEquals("miguel.rodriguez@ejemplo.com", autorizacion.getFromEmail());
    }

    @Test
    void testAutorizacionTramitesConstructorWithArgs() {
        // Crear una instancia usando el constructor con argumentos
        AutorizacionTramites autorizacion = new AutorizacionTramites(
                "Avenida El Dorado #66-63",
                "28/05/2023",
                "Patricia Gómez",
                "43765432",
                "Medellín",
                "Secretaría de Infraestructura",
                "800123456",
                "Medellín, Antioquia",
                "Ampliación Red Eléctrica Sector Norte",
                "Antioquia",
                "Carrera 80 #45-90, Municipio de Bello",
                "Patricia Gómez",
                "43765432",
                "3158907654",
                "patricia.gomez@ejemplo.com");

        // Verificar que la instancia se crea correctamente con los valores
        // proporcionados
        assertNotNull(autorizacion);
        assertEquals("Avenida El Dorado #66-63", autorizacion.getFromAddress());
        assertEquals("28/05/2023", autorizacion.getDate());
        assertEquals("Patricia Gómez", autorizacion.getFromName());
        assertEquals("43765432", autorizacion.getFromIdentification());
        assertEquals("Medellín", autorizacion.getFromLocation());
        assertEquals("Secretaría de Infraestructura", autorizacion.getToName());
        assertEquals("800123456", autorizacion.getToIdentification());
        assertEquals("Medellín, Antioquia", autorizacion.getToLocation());
        assertEquals("Ampliación Red Eléctrica Sector Norte", autorizacion.getProjectName());
        assertEquals("Antioquia", autorizacion.getProjectLocation());
        assertEquals("Carrera 80 #45-90, Municipio de Bello", autorizacion.getProjectAddress());
        assertEquals("Patricia Gómez", autorizacion.getFromName2());
        assertEquals("43765432", autorizacion.getFromIdentification2());
        assertEquals("3158907654", autorizacion.getFromPhone());
        assertEquals("patricia.gomez@ejemplo.com", autorizacion.getFromEmail());
    }

    @Test
    void testAutorizacionTramitesEquality() {
        // Crear dos instancias con los mismos valores
        AutorizacionTramites autorizacion1 = new AutorizacionTramites();
        autorizacion1.setFromName("José Luis Martínez");
        autorizacion1.setProjectName("Interconexión Eléctrica Municipal");
        autorizacion1.setToName("Entidad Reguladora");

        AutorizacionTramites autorizacion2 = new AutorizacionTramites();
        autorizacion2.setFromName("José Luis Martínez");
        autorizacion2.setProjectName("Interconexión Eléctrica Municipal");
        autorizacion2.setToName("Entidad Reguladora");

        // Verificar igualdad usando equals (generado por Lombok)
        assertEquals(autorizacion1, autorizacion2);

        // Modificar un valor y verificar que ya no son iguales
        autorizacion2.setToName("Otra Entidad");
        assertNotEquals(autorizacion1, autorizacion2);
    }

    @Test
    void testAutorizacionTramitesToString() {
        // Crear una instancia con algunos valores
        AutorizacionTramites autorizacion = new AutorizacionTramites();
        autorizacion.setFromName("Javier González");
        autorizacion.setProjectName("Subestación Eléctrica El Progreso");
        autorizacion.setFromEmail("javier.gonzalez@ejemplo.com");

        // Verificar que toString contiene la información esperada
        String toStringResult = autorizacion.toString();
        assertTrue(toStringResult.contains("Javier González"));
        assertTrue(toStringResult.contains("Subestación Eléctrica El Progreso"));
        assertTrue(toStringResult.contains("javier.gonzalez@ejemplo.com"));
    }
}