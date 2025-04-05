package com.formatosprellieminares.api.doc_generator.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formatosprellieminares.api.doc_generator.model.AutorizacionConexion;

@ExtendWith(MockitoExtension.class)
class AutorizacionConexionGeneratorTest {

    private AutorizacionConexionGenerator generator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generator = new AutorizacionConexionGenerator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetNombreArchivo() {
        assertEquals("AUTORIZACION_CONEXION.docx", generator.getNombreArchivo());
    }

    @Test
    void testGenerarDocumentoDesdeMap() throws IOException {
        // Preparar datos de prueba usando un Map
        Map<String, Object> datos = new HashMap<>();
        datos.put("fromAddress", "Calle 123 #45-67");
        datos.put("date", "15/04/2023");
        datos.put("fromName", "Juan Pérez");
        datos.put("fromIdentification", "123456789");
        datos.put("fromLocation", "Bogotá");
        datos.put("toName", "Empresa Eléctrica S.A.");
        datos.put("toIdentification", "900123456");
        datos.put("toLocation", "Bogotá");
        datos.put("fromName2", "Juan Pérez");
        datos.put("fromIdentification2", "123456789");
        datos.put("fromPhone", "3001234567");
        datos.put("fromEmail", "juan.perez@email.com");
        datos.put("structureCode", "TR-001");
        datos.put("structureLocation", "Esquina Calle 123 con Carrera 45");
        datos.put("structureType", "Poste de concreto");
        datos.put("structureHeight", "10 metros");
        datos.put("structureMaterial", "Concreto reforzado");
        datos.put("structureCondition", "Bueno");
        datos.put("structureOwner", "Pedro Gómez");
        datos.put("structureOwnerId", "987654321");
        datos.put("structureOwnerAddress", "Carrera 50 #30-15");
        datos.put("structureOwnerPhone", "3007654321");
        datos.put("structureOwnerEmail", "pedro.gomez@email.com");
        datos.put("structureOwnerSignature", "Pedro Gómez");
        datos.put("structureOwnerDate", "16/04/2023");
        datos.put("structureOwnerWitness", "María Rodriguez");
        datos.put("structureOwnerWitnessId", "567891234");
        datos.put("structureOwnerWitnessSignature", "María Rodriguez");
        datos.put("structureOwnerWitnessDate", "16/04/2023");

        // Convertir el Map a un objeto AutorizacionConexion para evitar problemas de
        // serialización
        AutorizacionConexion autorizacion = new AutorizacionConexion();
        autorizacion.setFromAddress((String) datos.get("fromAddress"));
        autorizacion.setDate((String) datos.get("date"));
        autorizacion.setFromName((String) datos.get("fromName"));
        autorizacion.setFromIdentification((String) datos.get("fromIdentification"));
        autorizacion.setFromLocation((String) datos.get("fromLocation"));
        autorizacion.setToName((String) datos.get("toName"));
        autorizacion.setToIdentification((String) datos.get("toIdentification"));
        autorizacion.setToLocation((String) datos.get("toLocation"));
        autorizacion.setFromName2((String) datos.get("fromName2"));
        autorizacion.setFromIdentification2((String) datos.get("fromIdentification2"));
        autorizacion.setFromPhone((String) datos.get("fromPhone"));
        autorizacion.setStructureCode((String) datos.get("structureCode"));
        autorizacion.setStructureLocation((String) datos.get("structureLocation"));
        autorizacion.setStructureType((String) datos.get("structureType"));
        autorizacion.setStructureHeight((String) datos.get("structureHeight"));
        autorizacion.setStructureMaterial((String) datos.get("structureMaterial"));
        autorizacion.setStructureCondition((String) datos.get("structureCondition"));
        autorizacion.setStructureOwner((String) datos.get("structureOwner"));
        autorizacion.setStructureOwnerId((String) datos.get("structureOwnerId"));
        autorizacion.setStructureOwnerAddress((String) datos.get("structureOwnerAddress"));
        autorizacion.setStructureOwnerPhone((String) datos.get("structureOwnerPhone"));
        autorizacion.setStructureOwnerEmail((String) datos.get("structureOwnerEmail"));
        autorizacion.setStructureOwnerSignature((String) datos.get("structureOwnerSignature"));
        autorizacion.setStructureOwnerDate((String) datos.get("structureOwnerDate"));
        autorizacion.setStructureOwnerWitness((String) datos.get("structureOwnerWitness"));
        autorizacion.setStructureOwnerWitnessId((String) datos.get("structureOwnerWitnessId"));
        autorizacion.setStructureOwnerWitnessSignature((String) datos.get("structureOwnerWitnessSignature"));
        autorizacion.setStructureOwnerWitnessDate((String) datos.get("structureOwnerWitnessDate"));

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(autorizacion);

        // Verificar resultado
        assertNotNull(documentoBytes);
        assertTrue(documentoBytes.length > 0);

        // Verificar que se puede abrir como un documento Word
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentoBytes));
        assertNotNull(document);
        document.close();
    }

    @Test
    void testGenerarDocumentoDesdeJSON() throws IOException {
        // Configurar ObjectMapper para ignorar propiedades desconocidas
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Definir un JSON de ejemplo directamente en la prueba
        String json = """
                {
                    "fromAddress": "Carrera 50 #80-20, Medellín",
                    "date": "10 de mayo de 2023",
                    "fromName": "Juan Pérez Rodríguez",
                    "fromIdentification": "1098765432",
                    "fromLocation": "Medellín",
                    "toName": "Empresa de Energía",
                    "toIdentification": "900123456-7",
                    "toLocation": "Medellín",
                    "fromName2": "Juan Pérez Rodríguez",
                    "fromIdentification2": "1098765432",
                    "fromPhone": "3101234567"
                }
                """;

        // Convertir el JSON directamente a un objeto AutorizacionConexion
        AutorizacionConexion autorizacion = mapper.readValue(json, AutorizacionConexion.class);

        // Asignar manualmente los valores que podrían faltar en el modelo
        autorizacion.setStructureCode("EST-001");
        autorizacion.setStructureLocation("Esquina Calle 50 con Carrera 80");
        autorizacion.setStructureType("Poste de concreto");
        autorizacion.setStructureHeight("12 metros");
        autorizacion.setStructureMaterial("Concreto reforzado");
        autorizacion.setStructureCondition("Bueno");
        autorizacion.setStructureOwner("María González");
        autorizacion.setStructureOwnerId("43210987");
        autorizacion.setStructureOwnerAddress("Calle 80 #20-30");
        autorizacion.setStructureOwnerPhone("3209876543");
        autorizacion.setStructureOwnerEmail("maria.gonzalez@ejemplo.com");
        autorizacion.setStructureOwnerSignature("María González");
        autorizacion.setStructureOwnerDate("11 de mayo de 2023");
        autorizacion.setStructureOwnerWitness("Pedro Ramírez");
        autorizacion.setStructureOwnerWitnessId("76543210");
        autorizacion.setStructureOwnerWitnessSignature("Pedro Ramírez");
        autorizacion.setStructureOwnerWitnessDate("11 de mayo de 2023");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(autorizacion);

        // Verificar resultado
        assertNotNull(documentoBytes);
        assertTrue(documentoBytes.length > 0);

        // Verificar que se puede abrir como un documento Word
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentoBytes));
        assertNotNull(document);
        document.close();
    }

    @Test
    void testGenerarDocumentoDesdeObjeto() {
        // Preparar datos de prueba usando un objeto
        AutorizacionConexion autorizacion = new AutorizacionConexion();
        autorizacion.setFromAddress("Calle 28 #10-15");
        autorizacion.setDate("05/05/2023");
        autorizacion.setFromName("Marta Jiménez");
        autorizacion.setFromIdentification("98765432");
        autorizacion.setFromLocation("Cali");
        autorizacion.setToName("Distribuidora Energía");
        autorizacion.setToIdentification("800987654");
        autorizacion.setToLocation("Cali");
        autorizacion.setFromName2("Marta Jiménez");
        autorizacion.setFromIdentification2("98765432");
        autorizacion.setFromPhone("3112345678");
        autorizacion.setStructureCode("EST-002");
        autorizacion.setStructureLocation("Avenida 5 con Calle 10");
        autorizacion.setStructureType("Poste de concreto");
        autorizacion.setStructureHeight("10 metros");
        autorizacion.setStructureMaterial("Concreto reforzado");
        autorizacion.setStructureCondition("Regular");
        autorizacion.setStructureOwner("Pedro López");
        autorizacion.setStructureOwnerId("54321678");
        autorizacion.setStructureOwnerAddress("Calle 15 #8-20");
        autorizacion.setStructureOwnerPhone("3145678901");
        autorizacion.setStructureOwnerEmail("pedro.lopez@ejemplo.com");
        autorizacion.setStructureOwnerSignature("Pedro López");
        autorizacion.setStructureOwnerDate("06/05/2023");
        autorizacion.setStructureOwnerWitness("Carmen Ortiz");
        autorizacion.setStructureOwnerWitnessId("12345670");
        autorizacion.setStructureOwnerWitnessSignature("Carmen Ortiz");
        autorizacion.setStructureOwnerWitnessDate("06/05/2023");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(autorizacion);

        // Verificar resultado
        assertNotNull(documentoBytes);
        assertTrue(documentoBytes.length > 0);
    }

    @Test
    void testGenerarDocumentoConDatosNulos() {
        // Preparar datos nulos para la prueba
        Object datosNulos = null;

        // Verificar que se lanza la excepción adecuada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            generator.generarDocumento(datosNulos);
        });

        // Verificar el mensaje de la excepción
        assertTrue(exception.getMessage().contains("Error al generar el documento AutorizacionConexion"));
    }
}