package com.formatosprellieminares.api.doc_generator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;
import com.formatosprellieminares.api.doc_generator.dto.example.ExampleRequest;
import com.formatosprellieminares.api.doc_generator.service.DocumentoService;

@SpringBootTest
class DocumentoControllerTest {

        private MockMvc mockMvc;

        @Mock
        private DocumentoService documentoService;

        @InjectMocks
        private DocumentoController documentoController;

        @InjectMocks
        private GlobalExceptionHandler globalExceptionHandler;

        private ObjectMapper objectMapper = new ObjectMapper();

        private DocumentoRequest documentoRequest;
        private SolicitudDocumentos solicitudDocumentos;
        private ByteArrayOutputStream mockOutputStream;

        @BeforeEach
        void setUp() throws IOException {
                // Configurar MockMvc con el manejador de excepciones
                ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver();
                exceptionResolver.afterPropertiesSet();

                mockMvc = MockMvcBuilders.standaloneSetup(documentoController)
                                .setControllerAdvice(globalExceptionHandler)
                                .build();

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
        void testGenerarSolicitudCodigosSPARD() throws Exception {
                when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class)))
                                .thenReturn(mockOutputStream);

                mockMvc.perform(post("/api/documentos/solicitud-codigos-spard")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ExampleRequest.SOLICITUD_CODIGOS_SPARD))
                                .andExpect(status().isOk())
                                .andExpect(content().bytes(mockOutputStream.toByteArray()))
                                .andExpect(header().string("Content-Disposition",
                                                "form-data; name=\"attachment\"; filename=\"SOLICITUD_CODIGOS_SPARD.docx\""));
        }

        @Test
        void testGenerarAutorizacionTramites() throws Exception {
                when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class)))
                                .thenReturn(mockOutputStream);

                mockMvc.perform(post("/api/documentos/autorizacion-tramites")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ExampleRequest.AUTORIZACION_TRAMITES))
                                .andExpect(status().isOk())
                                .andExpect(content().bytes(mockOutputStream.toByteArray()))
                                .andExpect(header().string("Content-Disposition",
                                                "form-data; name=\"attachment\"; filename=\"AUTORIZACION_TRAMITES.docx\""));
        }

        @Test
        void testGenerarAutorizacionConexion() throws Exception {
                when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class)))
                                .thenReturn(mockOutputStream);

                mockMvc.perform(post("/api/documentos/autorizacion-conexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ExampleRequest.AUTORIZACION_CONEXION))
                                .andExpect(status().isOk())
                                .andExpect(content().bytes(mockOutputStream.toByteArray()))
                                .andExpect(header().string("Content-Disposition",
                                                "form-data; name=\"attachment\"; filename=\"AUTORIZACION_CONEXION.docx\""));
        }

        @Test
        void testGenerarNoHayPermisoLineasCarretera() throws Exception {
                when(documentoService.generarDocumentoIndividual(any(DocumentoRequest.class)))
                                .thenReturn(mockOutputStream);

                mockMvc.perform(post("/api/documentos/no-hay-permiso-lineas-carretera")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ExampleRequest.NO_HAY_PERMISO_LINEAS_CARRETERA))
                                .andExpect(status().isOk())
                                .andExpect(content().bytes(mockOutputStream.toByteArray()))
                                .andExpect(header().string("Content-Disposition",
                                                "form-data; name=\"attachment\"; filename=\"NO_HAY_PERMISO_LINEAS_CARRETERA.docx\""));
        }

        @Test
        void testGenerarMultiplesDocumentos() throws Exception {
                when(documentoService.generarDocumentos(any(SolicitudDocumentos.class))).thenReturn(mockOutputStream);

                mockMvc.perform(post("/api/documentos/generar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ExampleRequest.MULTIPLE_DOCUMENTOS))
                                .andExpect(status().isOk())
                                .andExpect(content().bytes(mockOutputStream.toByteArray()))
                                .andExpect(header().string("Content-Disposition",
                                                "form-data; name=\"attachment\"; filename=\"documentos.zip\""));
        }

        @Test
        void testRequestInvalido() throws Exception {
                // Esta prueba ahora espera un estado 500 debido a que RuntimeException es
                // manejada por GlobalExceptionHandler
                mockMvc.perform(post("/api/documentos/solicitud-codigos-spard")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"body\": null}"))
                                .andExpect(status().isInternalServerError());
        }
}