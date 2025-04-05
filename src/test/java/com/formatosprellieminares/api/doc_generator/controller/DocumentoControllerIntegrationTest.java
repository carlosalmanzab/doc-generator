package com.formatosprellieminares.api.doc_generator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;
import com.formatosprellieminares.api.doc_generator.dto.example.ExampleRequest;
import com.formatosprellieminares.api.doc_generator.service.DocumentoService;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentoService documentoService;

    @Test
    void testSolicitudCodigosSPARDIntegracion() throws Exception {
        // Preparar datos de prueba
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("documento de prueba".getBytes());

        // Configurar mock
        when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class))).thenReturn(outputStream);

        // Ejecutar solicitud
        mockMvc.perform(post("/api/documentos/solicitud-codigos-spard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ExampleRequest.SOLICITUD_CODIGOS_SPARD))
                .andExpect(status().isOk());

        // Verificar que el servicio fue llamado con el tipo de documento correcto
        verify(documentoService).generarDocumentoIndividual(any(DocumentoRequest.class));
    }

    @Test
    void testGenerarDocumentosMultiplesIntegracion() throws Exception {
        // Preparar datos de prueba
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("documentos zip de prueba".getBytes());

        // Configurar mock
        when(documentoService.generarDocumentos(any(SolicitudDocumentos.class))).thenReturn(outputStream);

        // Ejecutar solicitud
        mockMvc.perform(post("/api/documentos/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ExampleRequest.MULTIPLE_DOCUMENTOS))
                .andExpect(status().isOk());

        // Verificar que el servicio fue llamado
        verify(documentoService).generarDocumentos(any(SolicitudDocumentos.class));
    }

    @Test
    void testManejoDeErroresIntegracion() throws Exception {
        // Configurar mock para lanzar excepción
        when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class)))
                .thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar solicitud y esperar error 500 (ahora lo maneja
        // GlobalExceptionHandler)
        mockMvc.perform(post("/api/documentos/solicitud-codigos-spard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ExampleRequest.SOLICITUD_CODIGOS_SPARD))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSolicitudInvalidaIntegracion() throws Exception {
        // Ejecutar solicitud con JSON malformado (ahora lo maneja
        // GlobalExceptionHandler)
        mockMvc.perform(post("/api/documentos/solicitud-codigos-spard")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{malformed json}"))
                .andExpect(status().isBadRequest());
    }
}