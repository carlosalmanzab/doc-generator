package com.formatosprellieminares.api.doc_generator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;
import com.formatosprellieminares.api.doc_generator.model.AutorizacionConexion;
import com.formatosprellieminares.api.doc_generator.model.AutorizacionTramites;
import com.formatosprellieminares.api.doc_generator.model.Declaracion236;
import com.formatosprellieminares.api.doc_generator.model.NoHayPermisisosLineasCarreteras;
import com.formatosprellieminares.api.doc_generator.model.OtorgamientoServidumbre;
import com.formatosprellieminares.api.doc_generator.model.SolicitudCodigosSPARD;
import com.formatosprellieminares.api.doc_generator.service.impl.AutorizacionConexionGenerator;
import com.formatosprellieminares.api.doc_generator.service.impl.AutorizacionTramitesGenerator;
import com.formatosprellieminares.api.doc_generator.service.impl.Declaracion236Generator;
import com.formatosprellieminares.api.doc_generator.service.impl.DocumentoServiceImpl;
import com.formatosprellieminares.api.doc_generator.service.impl.NoHayPermisoLineasCarreteraGenerator;
import com.formatosprellieminares.api.doc_generator.service.impl.OtorgamientoServidumbreGenerator;
import com.formatosprellieminares.api.doc_generator.service.impl.SolicitudCodigosSPARDGenerator;

@ExtendWith(MockitoExtension.class)
class DocumentoServiceTest {

    @Mock
    private SolicitudCodigosSPARDGenerator solicitudCodigosSPARDGenerator;

    @Mock
    private AutorizacionTramitesGenerator autorizacionTramitesGenerator;

    @Mock
    private AutorizacionConexionGenerator autorizacionConexionGenerator;

    @Mock
    private NoHayPermisoLineasCarreteraGenerator noHayPermisoLineasCarreteraGenerator;

    @Mock
    private Declaracion236Generator declaracion236Generator;

    @Mock
    private OtorgamientoServidumbreGenerator otorgamientoServidumbreGenerator;

    @Spy
    @InjectMocks
    private DocumentoServiceImpl documentoService;

    private Map<String, DocumentoGenerator> generadores;

    private static final String CONTENIDO_PRUEBA = "Documento de prueba";

    @BeforeEach
    void setUp() {
        // Configurar el mapa de generadores para todos los tests
        generadores = new HashMap<>();
        generadores.put("SOLICITUD_CODIGOS_SPARD", solicitudCodigosSPARDGenerator);
        generadores.put("AUTORIZACION_TRAMITES", autorizacionTramitesGenerator);
        generadores.put("AUTORIZACION_CONEXION", autorizacionConexionGenerator);
        generadores.put("NO_HAY_PERMISOS_LINEAS_CARRETERAS", noHayPermisoLineasCarreteraGenerator);
        generadores.put("DECLARACION_236", declaracion236Generator);
        generadores.put("OTORGAMIENTO_SERVIDUMBRE", otorgamientoServidumbreGenerator);

        ReflectionTestUtils.setField(documentoService, "generadores", generadores);

        // Configurar comportamiento de los generadores mock con lenient para evitar
        // UnnecessaryStubbingException
        lenient().when(solicitudCodigosSPARDGenerator.getNombreArchivo()).thenReturn("SOLICITUD_CODIGOS_SPARD.docx");
        lenient().when(solicitudCodigosSPARDGenerator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(autorizacionTramitesGenerator.getNombreArchivo()).thenReturn("AUTORIZACION_TRAMITES.docx");
        lenient().when(autorizacionTramitesGenerator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(autorizacionConexionGenerator.getNombreArchivo()).thenReturn("AUTORIZACION_CONEXION.docx");
        lenient().when(autorizacionConexionGenerator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(noHayPermisoLineasCarreteraGenerator.getNombreArchivo())
                .thenReturn("NO_HAY_PERMISOS_LINEAS_CARRETERAS.docx");
        lenient().when(noHayPermisoLineasCarreteraGenerator.generarDocumento(any()))
                .thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(declaracion236Generator.getNombreArchivo()).thenReturn("DECLARACION_236.docx");
        lenient().when(declaracion236Generator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(otorgamientoServidumbreGenerator.getNombreArchivo()).thenReturn("OTORGAMIENTO_SERVIDUMBRE.docx");
        lenient().when(otorgamientoServidumbreGenerator.generarDocumento(any()))
                .thenReturn(CONTENIDO_PRUEBA.getBytes());
    }

    @Test
    void testGenerarDocumentoSolicitudCodigosSPARD() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");
        SolicitudCodigosSPARD datos = new SolicitudCodigosSPARD();
        datos.setFromName("Juan Pérez");
        datos.setFromIdentification("123456789");
        request.setBody(datos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream resultado = documentoService.generarDocumentoIndividual(request);

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentoAutorizacionTramites() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("AUTORIZACION_TRAMITES");
        AutorizacionTramites datos = new AutorizacionTramites();
        datos.setFromName("María González");
        datos.setFromIdentification("987654321");
        request.setBody(datos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream resultado = documentoService.generarDocumentoIndividual(request);

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        verify(autorizacionTramitesGenerator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentoAutorizacionConexion() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("AUTORIZACION_CONEXION");
        AutorizacionConexion datos = new AutorizacionConexion();
        datos.setFromName("Carlos Ramírez");
        datos.setFromIdentification("456789123");
        request.setBody(datos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream resultado = documentoService.generarDocumentoIndividual(request);

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        verify(autorizacionConexionGenerator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentoNoHayPermisisosLineasCarreteras() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("NO_HAY_PERMISOS_LINEAS_CARRETERAS");
        NoHayPermisisosLineasCarreteras datos = new NoHayPermisisosLineasCarreteras();
        datos.setFromName("Ana Martínez");
        datos.setFromIdentification("321654987");
        request.setBody(datos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream resultado = documentoService.generarDocumentoIndividual(request);

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        verify(noHayPermisoLineasCarreteraGenerator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentoDeclaracion236() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("DECLARACION_236");
        Declaracion236 datos = new Declaracion236();
        datos.setFromName("Roberto Sánchez");
        datos.setFromIdentification("789123456");
        request.setBody(datos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream resultado = documentoService.generarDocumentoIndividual(request);

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        verify(declaracion236Generator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentoOtorgamientoServidumbre() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("OTORGAMIENTO_SERVIDUMBRE");
        OtorgamientoServidumbre datos = new OtorgamientoServidumbre();
        datos.setFromName("Laura Díaz");
        datos.setFromIdentification("654789321");
        request.setBody(datos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream resultado = documentoService.generarDocumentoIndividual(request);

        // Verificar resultado
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        verify(otorgamientoServidumbreGenerator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentoIndividualTipoNoSoportado() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("TIPO_NO_EXISTENTE");
        request.setBody(new Object());

        // Ejecutar método bajo prueba y verificar excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            documentoService.generarDocumentoIndividual(request);
        });

        // Verificar mensaje de error
        assertTrue(exception.getMessage().contains("Tipo de documento no soportado"));
    }

    @Test
    void testGenerarDocumentoIndividualSinTipo() {
        // Preparar datos de prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setBody(new Object());

        // Ejecutar método bajo prueba y verificar excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            documentoService.generarDocumentoIndividual(request);
        });

        // Verificar mensaje de error
        assertTrue(exception.getMessage().contains("no puede ser nulo o vacío"));
    }

    @Test
    void testGenerarMultiplesDocumentos() throws IOException {
        // Preparar datos de prueba
        SolicitudDocumentos solicitud = new SolicitudDocumentos();
        List<DocumentoRequest> documentos = new ArrayList<>();

        // Agregar documentos de diferentes tipos
        DocumentoRequest request1 = new DocumentoRequest();
        request1.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");
        request1.setBody(new SolicitudCodigosSPARD());
        documentos.add(request1);

        DocumentoRequest request2 = new DocumentoRequest();
        request2.setTipoDocumento("AUTORIZACION_TRAMITES");
        request2.setBody(new AutorizacionTramites());
        documentos.add(request2);

        DocumentoRequest request3 = new DocumentoRequest();
        request3.setTipoDocumento("DECLARACION_236");
        request3.setBody(new Declaracion236());
        documentos.add(request3);

        solicitud.setDocumentos(documentos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream zipOutputStream = documentoService.generarDocumentos(solicitud);

        // Verificar resultado
        assertNotNull(zipOutputStream);
        assertTrue(zipOutputStream.size() > 0);

        // Verificar que el ZIP contiene los documentos esperados
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipOutputStream.toByteArray()));
        int contadorArchivos = 0;

        while (zipInputStream.getNextEntry() != null) {
            contadorArchivos++;
        }

        assertEquals(3, contadorArchivos);
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
        verify(autorizacionTramitesGenerator).generarDocumento(any());
        verify(declaracion236Generator).generarDocumento(any());
    }

    @Test
    void testGenerarDocumentosConTipoInvalido() {
        // Preparar datos de prueba
        SolicitudDocumentos solicitud = new SolicitudDocumentos();
        List<DocumentoRequest> documentos = new ArrayList<>();

        // Documento con tipo válido
        DocumentoRequest requestValido = new DocumentoRequest();
        requestValido.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");
        requestValido.setBody(new SolicitudCodigosSPARD());
        documentos.add(requestValido);

        // Documento con tipo inválido
        DocumentoRequest requestInvalido = new DocumentoRequest();
        requestInvalido.setTipoDocumento(null);
        requestInvalido.setBody(new Object());
        documentos.add(requestInvalido);

        solicitud.setDocumentos(documentos);

        // Ejecutar método bajo prueba
        ByteArrayOutputStream zipOutputStream = documentoService.generarDocumentos(solicitud);

        // Verificar resultado - debería contener solo el documento válido
        assertNotNull(zipOutputStream);
        assertTrue(zipOutputStream.size() > 0);

        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipOutputStream.toByteArray()));
        int contadorArchivos = 0;

        try {
            while (zipInputStream.getNextEntry() != null) {
                contadorArchivos++;
            }
        } catch (IOException e) {
            // Manejar silenciosamente
        }

        assertEquals(1, contadorArchivos);
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
    }
}