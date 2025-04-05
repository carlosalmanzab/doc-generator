package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class SolicitudCodigosSPARDTest {

    @Test
    void testSolicitudCodigosSPARDCreation() {
        // Crear una instancia de SolicitudCodigosSPARD
        SolicitudCodigosSPARD solicitud = new SolicitudCodigosSPARD();

        // Establecer valores
        solicitud.setFromAddress("Carrera 70 #30-40");
        solicitud.setDate("15/05/2023");
        solicitud.setFromName("Santiago Ruiz");
        solicitud.setFromIdentification("1023456789");
        solicitud.setProjectLocation("Antioquia");
        solicitud.setProjectAddress("Vereda El Retiro, Km 5");
        solicitud.setUserName("Empresa Energética S.A.");
        solicitud.setUserIdentification("900876543");
        solicitud.setUserLocation("Medellín");
        solicitud.setFromName2("Santiago Ruiz");
        solicitud.setFromIdentification2("1023456789");
        solicitud.setFromPhone("3005678901");
        solicitud.setFromEmail("santiago.ruiz@ejemplo.com");

        // Verificar que los valores se hayan establecido correctamente
        assertEquals("Carrera 70 #30-40", solicitud.getFromAddress());
        assertEquals("15/05/2023", solicitud.getDate());
        assertEquals("Santiago Ruiz", solicitud.getFromName());
        assertEquals("1023456789", solicitud.getFromIdentification());
        assertEquals("Antioquia", solicitud.getProjectLocation());
        assertEquals("Vereda El Retiro, Km 5", solicitud.getProjectAddress());
        assertEquals("Empresa Energética S.A.", solicitud.getUserName());
        assertEquals("900876543", solicitud.getUserIdentification());
        assertEquals("Medellín", solicitud.getUserLocation());
        assertEquals("Santiago Ruiz", solicitud.getFromName2());
        assertEquals("1023456789", solicitud.getFromIdentification2());
        assertEquals("3005678901", solicitud.getFromPhone());
        assertEquals("santiago.ruiz@ejemplo.com", solicitud.getFromEmail());
    }

    @Test
    void testSolicitudCodigosSPARDEquality() {
        // Crear dos instancias con los mismos valores
        SolicitudCodigosSPARD solicitud1 = new SolicitudCodigosSPARD();
        solicitud1.setFromName("Isabel Castro");
        solicitud1.setFromIdentification("51234567");
        solicitud1.setProjectLocation("Valle del Cauca");

        SolicitudCodigosSPARD solicitud2 = new SolicitudCodigosSPARD();
        solicitud2.setFromName("Isabel Castro");
        solicitud2.setFromIdentification("51234567");
        solicitud2.setProjectLocation("Valle del Cauca");

        // Verificar igualdad usando equals (generado por Lombok)
        assertEquals(solicitud1, solicitud2);

        // Modificar un valor y verificar que ya no son iguales
        solicitud2.setProjectLocation("Cauca");
        assertNotEquals(solicitud1, solicitud2);
    }

    @Test
    void testSolicitudCodigosSPARDJsonSerialization() throws Exception {
        // Crear una instancia con algunos valores
        SolicitudCodigosSPARD solicitud = new SolicitudCodigosSPARD();
        solicitud.setFromName("Daniela Pérez");
        solicitud.setFromIdentification("1045678901");
        solicitud.setProjectLocation("Santander");
        solicitud.setUserName("Constructora XYZ");

        // Crear el ObjectMapper para serializar y deserializar
        ObjectMapper objectMapper = new ObjectMapper();

        // Serializar a JSON
        String jsonString = objectMapper.writeValueAsString(solicitud);

        // Verificar que el JSON contiene los valores esperados
        assertTrue(jsonString.contains("\"fromName\":\"Daniela Pérez\""));
        assertTrue(jsonString.contains("\"fromIdentification\":\"1045678901\""));
        assertTrue(jsonString.contains("\"projectLocation\":\"Santander\""));
        assertTrue(jsonString.contains("\"userName\":\"Constructora XYZ\""));

        // Deserializar de vuelta a objeto
        SolicitudCodigosSPARD deserializado = objectMapper.readValue(jsonString, SolicitudCodigosSPARD.class);

        // Verificar que el objeto deserializado tiene los mismos valores
        assertEquals("Daniela Pérez", deserializado.getFromName());
        assertEquals("1045678901", deserializado.getFromIdentification());
        assertEquals("Santander", deserializado.getProjectLocation());
        assertEquals("Constructora XYZ", deserializado.getUserName());
    }

    @Test
    void testSolicitudCodigosSPARDToString() {
        // Crear una instancia con algunos valores
        SolicitudCodigosSPARD solicitud = new SolicitudCodigosSPARD();
        solicitud.setFromName("Eduardo Vargas");
        solicitud.setProjectAddress("Autopista Norte Km 15");
        solicitud.setUserName("Cooperativa Eléctrica Rural");

        // Verificar que toString contiene la información esperada
        String toStringResult = solicitud.toString();
        assertTrue(toStringResult.contains("Eduardo Vargas"));
        assertTrue(toStringResult.contains("Autopista Norte Km 15"));
        assertTrue(toStringResult.contains("Cooperativa Eléctrica Rural"));
    }
}