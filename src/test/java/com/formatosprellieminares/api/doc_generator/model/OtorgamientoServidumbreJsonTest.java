package com.formatosprellieminares.api.doc_generator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class OtorgamientoServidumbreJsonTest {

    @Test
    void testSerializationDeserialization() throws IOException {
        // Crear objeto con datos de prueba
        OtorgamientoServidumbre original = new OtorgamientoServidumbre();
        original.setDate("15/04/2023");
        original.setFromName("María Rodríguez");
        original.setFromIdentification("2345678901");
        original.setFromLocation("Medellín");
        original.setPropertyName("Hacienda La Esperanza");
        original.setPropertyRegistry("REG-456");
        original.setProjectLocation("Antioquia");
        original.setProjectAddress("Vereda El Progreso");
        original.setSafetyWidth("4 metros");
        original.setSafetyLength("150 metros");
        original.setNetworkVoltage("220kV");
        original.setNetworkType("Subterránea");
        original.setSignatureDay("20");
        original.setSignatureMonth("Abril");
        original.setSignatureYear("2023");
        original.setDepartment("Antioquia");
        original.setSignature("María Rodríguez");
        original.setPhone("3109876543");
        original.setFromAddress("Calle 45 #67-89");
        original.setEmail("maria.rodriguez@ejemplo.com");

        // Convertir a JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(original);

        // Verificar que el JSON contiene los valores esperados
        assertTrue(json.contains("María Rodríguez"));
        assertTrue(json.contains("Hacienda La Esperanza"));
        assertTrue(json.contains("220kV"));

        // Convertir de JSON a objeto
        OtorgamientoServidumbre recreated = mapper.readValue(json, OtorgamientoServidumbre.class);

        // Verificar que los objetos son iguales después de serializar/deserializar
        assertEquals(original, recreated);

        // Verificar campos específicos
        assertEquals("15/04/2023", recreated.getDate());
        assertEquals("María Rodríguez", recreated.getFromName());
        assertEquals("2345678901", recreated.getFromIdentification());
        assertEquals("Antioquia", recreated.getDepartment());
    }

    @Test
    void testEmptyObjectSerialization() throws IOException {
        // Crear objeto vacío
        OtorgamientoServidumbre empty = new OtorgamientoServidumbre();

        // Serializar y deserializar
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(empty);
        OtorgamientoServidumbre recreated = mapper.readValue(json, OtorgamientoServidumbre.class);

        // Verificar que ambos objetos son iguales (ambos vacíos)
        assertEquals(empty, recreated);
    }
}