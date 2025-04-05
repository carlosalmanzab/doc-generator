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
import com.formatosprellieminares.api.doc_generator.model.NoHayPermisisosLineasCarreteras;

@ExtendWith(MockitoExtension.class)
class NoHayPermisoLineasCarreteraGeneratorTest {

    private NoHayPermisoLineasCarreteraGenerator generator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generator = new NoHayPermisoLineasCarreteraGenerator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetNombreArchivo() {
        assertEquals("NO_HAY_PERMISO_LINEAS_CARRETERA.docx", generator.getNombreArchivo());
    }

    @Test
    void testGenerarDocumentoDesdeMap() throws IOException {
        // Preparar datos de prueba usando un Map
        Map<String, Object> datos = new HashMap<>();
        datos.put("fromAddress", "Carrera 50 #80-20, Medellín");
        datos.put("date", "10 de mayo de 2023");
        datos.put("fromName", "Juan Pérez Rodríguez");
        datos.put("fromIdentification", "1098765432");
        datos.put("fromLocation", "Medellín");
        datos.put("fromPhone", "3101234567");
        datos.put("fromEmail", "juan.perez@ejemplo.com");

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
        String jsonCompleto = ExampleRequest.NO_HAY_PERMISO_LINEAS_CARRETERA;
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
        NoHayPermisisosLineasCarreteras noHayPermisos = new NoHayPermisisosLineasCarreteras();
        noHayPermisos.setFromAddress("Avenida 70 #45-30, Bogotá");
        noHayPermisos.setDate("15 de mayo de 2023");
        noHayPermisos.setFromName("Pedro Ramírez González");
        noHayPermisos.setFromIdentification("80123456");
        noHayPermisos.setFromLocation("Bogotá");
        noHayPermisos.setFromPhone("3209876543");
        noHayPermisos.setFromEmail("pedro.ramirez@ejemplo.com");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(noHayPermisos);

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
        assertTrue(exception.getMessage().contains("Error al generar el documento NoHayPermisoLineasCarretera"));
    }
}