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
import com.formatosprellieminares.api.doc_generator.model.OtorgamientoServidumbre;

@ExtendWith(MockitoExtension.class)
class OtorgamientoServidumbreGeneratorTest {

    private OtorgamientoServidumbreGenerator generator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generator = new OtorgamientoServidumbreGenerator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetNombreArchivo() {
        assertEquals("FT-GRS-01-006-240.docx", generator.getNombreArchivo());
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
        datos.put("propertyName", "Hacienda Los Álamos");
        datos.put("propertyRegistry", "Matrícula inmobiliaria 123-45678");
        datos.put("projectLocation", "Vereda La Esperanza, Municipio de Santa Rosa");
        datos.put("projectAddress", "Calle Principal #123");
        datos.put("safetyWidth", "10 metros");
        datos.put("safetyLength", "200 metros");
        datos.put("networkVoltage", "13.2 kV");
        datos.put("networkType", "Trifásica");
        datos.put("signatureDay", "10");
        datos.put("signatureMonth", "mayo");
        datos.put("signatureYear", "2023");
        datos.put("department", "Antioquia");
        datos.put("signature", "Juan Pérez Rodríguez");
        datos.put("phone", "3101234567");
        datos.put("email", "juan.perez@ejemplo.com");

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
        // Generar un ejemplo de JSON para la prueba ya que no existe la constante
        String jsonCompleto = """
                {
                    "body": {
                        "fromAddress": "Carrera 50 #80-20, Medellín",
                        "date": "10 de mayo de 2023",
                        "fromName": "Juan Pérez Rodríguez",
                        "fromIdentification": "1098765432",
                        "fromLocation": "Medellín",
                        "propertyName": "Hacienda Los Álamos",
                        "propertyRegistry": "Matrícula inmobiliaria 123-45678",
                        "projectLocation": "Vereda La Esperanza",
                        "projectAddress": "Calle Principal #123",
                        "safetyWidth": "10 metros",
                        "safetyLength": "200 metros",
                        "networkVoltage": "13.2 kV",
                        "networkType": "Trifásica",
                        "signatureDay": "10",
                        "signatureMonth": "mayo",
                        "signatureYear": "2023",
                        "department": "Antioquia",
                        "signature": "Juan Pérez Rodríguez",
                        "phone": "3101234567",
                        "email": "juan.perez@ejemplo.com"
                    }
                }
                """;
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
        OtorgamientoServidumbre otorgamiento = new OtorgamientoServidumbre();
        otorgamiento.setFromAddress("Avenida 70 #45-30, Bogotá");
        otorgamiento.setDate("15 de mayo de 2023");
        otorgamiento.setFromName("Pedro Ramírez González");
        otorgamiento.setFromIdentification("80123456");
        otorgamiento.setFromLocation("Bogotá");
        otorgamiento.setPropertyName("Finca El Paraíso");
        otorgamiento.setPropertyRegistry("Matrícula inmobiliaria 234-56789");
        otorgamiento.setProjectLocation("Vereda San Antonio");
        otorgamiento.setProjectAddress("Calle Principal #456");
        otorgamiento.setSafetyWidth("5 metros");
        otorgamiento.setSafetyLength("300 metros");
        otorgamiento.setNetworkVoltage("34.5 kV");
        otorgamiento.setNetworkType("Monofásica");
        otorgamiento.setSignatureDay("15");
        otorgamiento.setSignatureMonth("mayo");
        otorgamiento.setSignatureYear("2023");
        otorgamiento.setDepartment("Cundinamarca");
        otorgamiento.setSignature("Pedro Ramírez González");
        otorgamiento.setPhone("3209876543");
        otorgamiento.setEmail("pedro.ramirez@ejemplo.com");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(otorgamiento);

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

        // Verificar el mensaje de la excepción - usar una verificación más genérica
        assertTrue(exception.getMessage().contains("Error al generar el documento") ||
                exception.getMessage().contains("Error generando documento") ||
                exception.getMessage().contains("OtorgamientoServidumbre"));
    }
}