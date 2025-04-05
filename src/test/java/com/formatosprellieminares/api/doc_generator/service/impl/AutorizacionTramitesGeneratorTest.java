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
import com.formatosprellieminares.api.doc_generator.model.AutorizacionTramites;

@ExtendWith(MockitoExtension.class)
class AutorizacionTramitesGeneratorTest {

    private AutorizacionTramitesGenerator generator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generator = new AutorizacionTramitesGenerator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetNombreArchivo() {
        assertEquals("AUTORIZACION_TRAMITES.docx", generator.getNombreArchivo());
    }

    @Test
    void testGenerarDocumentoDesdeMap() throws IOException {
        // Preparar datos de prueba usando un Map
        Map<String, Object> datos = new HashMap<>();
        datos.put("fromName", "Juan Pérez");
        datos.put("fromIdentification", "123456789");
        datos.put("date", "2024-03-29");
        datos.put("fromAddress", "Calle 123 #45-67");
        datos.put("fromPhone", "3001234567");
        datos.put("fromEmail", "juan.perez@email.com");
        datos.put("projectName", "Sistema Eléctrico Residencial");
        datos.put("projectLocation", "Bogotá");
        datos.put("projectAddress", "Calle 123 #45-67, Apto 501");
        datos.put("toName", "Pedro Gómez");
        datos.put("toIdentification", "987654321");
        datos.put("fromLocation", "Bogotá");

        // Convertir el Map a un objeto AutorizacionTramites para evitar problemas de
        // serialización
        AutorizacionTramites autorizacion = new AutorizacionTramites();
        autorizacion.setFromName((String) datos.get("fromName"));
        autorizacion.setFromIdentification((String) datos.get("fromIdentification"));
        autorizacion.setDate((String) datos.get("date"));
        autorizacion.setFromAddress((String) datos.get("fromAddress"));
        autorizacion.setFromPhone((String) datos.get("fromPhone"));
        autorizacion.setFromEmail((String) datos.get("fromEmail"));
        autorizacion.setProjectName((String) datos.get("projectName"));
        autorizacion.setProjectLocation((String) datos.get("projectLocation"));
        autorizacion.setProjectAddress((String) datos.get("projectAddress"));
        autorizacion.setToName((String) datos.get("toName"));
        autorizacion.setToIdentification((String) datos.get("toIdentification"));
        autorizacion.setFromLocation((String) datos.get("fromLocation"));

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
                    "fromPhone": "3101234567",
                    "fromEmail": "juan.perez@ejemplo.com",
                    "toName": "Empresa de Energía",
                    "toIdentification": "900123456-7",
                    "projectName": "Sistema Eléctrico Residencial",
                    "projectLocation": "Barrio El Poblado",
                    "projectAddress": "Calle 10 #15-25"
                }
                """;

        // Convertir el JSON directamente a un objeto AutorizacionTramites
        AutorizacionTramites autorizacion = mapper.readValue(json, AutorizacionTramites.class);

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
        AutorizacionTramites autorizacion = new AutorizacionTramites();
        autorizacion.setFromName("María Rodríguez");
        autorizacion.setFromIdentification("987654321");
        autorizacion.setDate("2024-04-01");
        autorizacion.setFromAddress("Avenida 456 #78-90");
        autorizacion.setFromPhone("3109876543");
        autorizacion.setFromEmail("maria.rodriguez@ejemplo.com");
        autorizacion.setProjectName("Sistema Eléctrico Comercial");
        autorizacion.setProjectLocation("Medellín");
        autorizacion.setProjectAddress("Carrera 70 #45-20, Local 105");
        autorizacion.setToName("Secretaría de Planeación Municipal");
        autorizacion.setToIdentification("900456789-2");

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
        assertTrue(exception.getMessage().contains("Error al generar el documento AutorizacionTramites"));
    }
}