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
import com.formatosprellieminares.api.doc_generator.dto.example.ExampleRequest;
import com.formatosprellieminares.api.doc_generator.model.SolicitudCodigosSPARD;

@ExtendWith(MockitoExtension.class)
class SolicitudCodigosSPARDGeneratorTest {

    private SolicitudCodigosSPARDGenerator generator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generator = new SolicitudCodigosSPARDGenerator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetNombreArchivo() {
        assertEquals("SOLICITUD_CODIGOS_SPARD.docx", generator.getNombreArchivo());
    }

    @Test
    void testGenerarDocumentoDesdeMap() throws IOException {
        // Preparar datos de prueba usando un Map
        Map<String, Object> datos = new HashMap<>();
        datos.put("fromName", "Juan Pérez");
        datos.put("fromIdentification", "123456789");
        datos.put("date", "2024-04-01");
        datos.put("fromAddress", "Calle 123 #45-67");
        datos.put("fromPhone", "3001234567");
        datos.put("fromEmail", "juan.perez@ejemplo.com");
        datos.put("projectLocation", "Barrio El Centro");
        datos.put("projectAddress", "Neiva");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(datos);

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
        // Obtener el JSON de ejemplo y extraer solo el body
        String jsonCompleto = ExampleRequest.SOLICITUD_CODIGOS_SPARD;
        Map<String, Object> datosCompletos = objectMapper.readValue(jsonCompleto, Map.class);
        Object body = datosCompletos.get("body");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(body);

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
        SolicitudCodigosSPARD solicitud = new SolicitudCodigosSPARD();
        solicitud.setFromName("María Rodríguez");
        solicitud.setFromIdentification("987654321");
        solicitud.setDate("2024-04-01");
        solicitud.setFromAddress("Avenida 456 #78-90");
        solicitud.setFromPhone("3109876543");
        solicitud.setFromEmail("maria.rodriguez@ejemplo.com");
        solicitud.setProjectLocation("Urbanización Las Palmas");
        solicitud.setProjectAddress("Medellín");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(solicitud);

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
        assertTrue(exception.getMessage().contains("Error al generar el documento SolicitudCodigosSPARD"));
    }
}