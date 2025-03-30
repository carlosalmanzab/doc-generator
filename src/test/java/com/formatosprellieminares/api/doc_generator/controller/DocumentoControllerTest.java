package com.formatosprellieminares.api.doc_generator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;
import com.formatosprellieminares.api.doc_generator.service.DocumentoService;

@ExtendWith(MockitoExtension.class)
public class DocumentoControllerTest {

    @Mock
    private DocumentoService documentoService;

    @InjectMocks
    private DocumentoController documentoController;

    private DocumentoRequest documentoRequest;
    private SolicitudDocumentos solicitudDocumentos;
    private ByteArrayOutputStream mockOutputStream;

    @BeforeEach
    void setUp() throws IOException {
        documentoRequest = new DocumentoRequest();
        documentoRequest.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");
        documentoRequest.setBody(new Object());

        List<DocumentoRequest> documentos = Arrays.asList(documentoRequest);
        solicitudDocumentos = new SolicitudDocumentos();
        solicitudDocumentos.setDocumentos(documentos);

        mockOutputStream = new ByteArrayOutputStream();
        mockOutputStream.write("test".getBytes());
    }

    @Test
    void generarDocumentos_DeberiaRetornarZip() {
        when(documentoService.generarDocumentos(any(SolicitudDocumentos.class))).thenReturn(mockOutputStream);

        ResponseEntity<byte[]> response = documentoController.generarDocumentos(solicitudDocumentos);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test", new String(response.getBody()));
    }

    @Test
    void generarSolicitudCodigosSPARD_DeberiaRetornarDocumento() {
        when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class))).thenReturn(mockOutputStream);

        ResponseEntity<byte[]> response = documentoController.generarSolicitudCodigosSPARD(documentoRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test", new String(response.getBody()));
    }

    @Test
    void generarAutorizacionTramites_DeberiaRetornarDocumento() {
        when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class))).thenReturn(mockOutputStream);

        ResponseEntity<byte[]> response = documentoController.generarAutorizacionTramites(documentoRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test", new String(response.getBody()));
    }

    @Test
    void generarAutorizacionConexion_DeberiaRetornarDocumento() {
        when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class))).thenReturn(mockOutputStream);

        ResponseEntity<byte[]> response = documentoController.generarAutorizacionConexion(documentoRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test", new String(response.getBody()));
    }

    @Test
    void generarNoHayPermisoLineasCarretera_DeberiaRetornarDocumento() {
        when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class))).thenReturn(mockOutputStream);

        ResponseEntity<byte[]> response = documentoController.generarNoHayPermisoLineasCarretera(documentoRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test", new String(response.getBody()));
    }
}