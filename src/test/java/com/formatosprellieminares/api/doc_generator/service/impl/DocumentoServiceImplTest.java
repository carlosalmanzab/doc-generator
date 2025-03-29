package com.formatosprellieminares.api.doc_generator.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;

@ExtendWith(MockitoExtension.class)
public class DocumentoServiceImplTest {

    @Mock
    private SolicitudCodigosSPARDGenerator solicitudCodigosSPARDGenerator;

    @Mock
    private AutorizacionTramitesGenerator autorizacionTramitesGenerator;

    @Mock
    private AutorizacionConexionGenerator autorizacionConexionGenerator;

    @Mock
    private NoHayPermisoLineasCarreteraGenerator noHayPermisoLineasCarreteraGenerator;

    @InjectMocks
    private DocumentoServiceImpl documentoService;

    private DocumentoRequest documentoRequest;
    private SolicitudDocumentos solicitudDocumentos;
    private ByteArrayOutputStream mockOutputStream;

    @BeforeEach
    void setUp() throws Exception {
        documentoRequest = new DocumentoRequest();
        documentoRequest.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");
        documentoRequest.setDatos(new Object());

        List<DocumentoRequest> documentos = Arrays.asList(documentoRequest);
        solicitudDocumentos = new SolicitudDocumentos();
        solicitudDocumentos.setDocumentos(documentos);

        mockOutputStream = new ByteArrayOutputStream();
        mockOutputStream.write("test".getBytes());
    }

    @Test
    void generarDocumentoIndividual_DeberiaGenerarDocumento() throws Exception {
        when(solicitudCodigosSPARDGenerator.generarDocumento(any())).thenReturn("test".getBytes());
        when(solicitudCodigosSPARDGenerator.getNombreArchivo()).thenReturn("test.docx");

        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(documentoRequest);

        assertNotNull(result);
        assertEquals("test", new String(result.toByteArray()));
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
    }

    @Test
    void generarDocumentoIndividual_DeberiaLanzarExcepcionSiTipoNoExiste() {
        documentoRequest.setTipoDocumento("TIPO_NO_EXISTENTE");

        assertThrows(RuntimeException.class, () -> {
            documentoService.generarDocumentoIndividual(documentoRequest);
        });
    }

    @Test
    void generarDocumentos_DeberiaGenerarZip() throws Exception {
        when(solicitudCodigosSPARDGenerator.generarDocumento(any())).thenReturn("test".getBytes());
        when(solicitudCodigosSPARDGenerator.getNombreArchivo()).thenReturn("test.docx");

        ByteArrayOutputStream result = documentoService.generarDocumentos(solicitudDocumentos);

        assertNotNull(result);
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
    }
}