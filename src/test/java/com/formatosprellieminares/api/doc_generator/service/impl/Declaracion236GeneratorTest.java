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
import com.formatosprellieminares.api.doc_generator.model.Declaracion236;

@ExtendWith(MockitoExtension.class)
class Declaracion236GeneratorTest {

    private Declaracion236Generator generator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generator = new Declaracion236Generator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetNombreArchivo() {
        assertEquals("DECLARACION-236.docx", generator.getNombreArchivo());
    }

    @Test
    void testGenerarDocumentoDesdeMap() throws IOException {
        // Preparar datos de prueba usando un Map
        Map<String, Object> datos = new HashMap<>();
        datos.put("year", "2023");
        datos.put("fromName", "Carlos Gómez");
        datos.put("fromIdentification", "1098765432");
        datos.put("fromLocation", "Medellín");
        datos.put("fromProfessionalNumber", "MT-123456");
        datos.put("installationDescription", "Instalación eléctrica residencial");
        datos.put("projectName", "Edificio Torres del Sur");
        datos.put("projectLocation", "Medellín");
        datos.put("projectAddress", "Calle 50 #20-30");
        datos.put("userName", "María López");
        datos.put("userIdentification", "43210987");
        datos.put("signatureLocation", "Medellín");
        datos.put("signatureDate", "15/04/2023");
        datos.put("signature", "Carlos Gómez");
        datos.put("comments", "La instalación cumple con el RETIE");
        datos.put("annexedDocuments", "Planos, memorias de cálculo");
        datos.put("responsibleAddress", "Carrera 70 #45-60");
        datos.put("phone", "3101234567");

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
                        "year": "2023",
                        "fromName": "Carlos Gómez",
                        "fromIdentification": "1098765432",
                        "fromLocation": "Medellín",
                        "fromProfessionalNumber": "MT-123456",
                        "installationDescription": "Instalación eléctrica residencial",
                        "projectName": "Edificio Torres del Sur",
                        "projectLocation": "Medellín",
                        "projectAddress": "Calle 50 #20-30",
                        "userName": "María López",
                        "userIdentification": "43210987",
                        "signatureLocation": "Medellín",
                        "signatureDate": "15/04/2023",
                        "signature": "Carlos Gómez",
                        "comments": "La instalación cumple con el RETIE",
                        "annexedDocuments": "Planos, memorias de cálculo",
                        "responsibleAddress": "Carrera 70 #45-60",
                        "phone": "3101234567"
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
        Declaracion236 declaracion = new Declaracion236();
        declaracion.setYear("2023");
        declaracion.setFromName("Luis Ramírez");
        declaracion.setFromIdentification("5678901234");
        declaracion.setFromLocation("Bogotá");
        declaracion.setFromProfessionalNumber("MT-987654");
        declaracion.setInstallationDescription("Instalación eléctrica comercial");
        declaracion.setProjectName("Centro Comercial Plaza Mayor");
        declaracion.setProjectLocation("Bogotá");
        declaracion.setProjectAddress("Avenida Calle 80 #20-50");
        declaracion.setUserName("Constructora ABC");
        declaracion.setUserIdentification("900123456");
        declaracion.setSignatureLocation("Bogotá");
        declaracion.setSignatureDate("20/04/2023");
        declaracion.setSignature("Luis Ramírez");
        declaracion.setComments("Cumple con todas las normas técnicas vigentes");
        declaracion.setAnnexedDocuments("Planos eléctricos, memorias de cálculo, certificados de materiales");
        declaracion.setResponsibleAddress("Carrera 15 #80-20");
        declaracion.setPhone("3209876543");

        // Generar documento
        byte[] documentoBytes = generator.generarDocumento(declaracion);

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
        assertTrue(exception.getMessage().contains("Error al generar el documento Declaracion236"));
    }
}