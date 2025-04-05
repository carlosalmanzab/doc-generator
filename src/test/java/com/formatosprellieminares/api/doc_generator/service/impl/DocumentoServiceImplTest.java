package com.formatosprellieminares.api.doc_generator.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import com.formatosprellieminares.api.doc_generator.service.DocumentoGenerator;

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

    @Mock
    private Declaracion236Generator declaracion236Generator;

    @Mock
    private OtorgamientoServidumbreGenerator otorgamientoServidumbreGenerator;

    @InjectMocks
    private DocumentoServiceImpl documentoService;

    private Map<String, DocumentoGenerator> generadores;

    private static final String CONTENIDO_PRUEBA = "contenido de prueba";
    private static final String NOMBRE_ARCHIVO_PRUEBA = "test.docx";

    @BeforeEach
    void setUp() throws Exception {
        // Configurar el mapa de generadores para los tests
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
        lenient().when(solicitudCodigosSPARDGenerator.getNombreArchivo()).thenReturn(NOMBRE_ARCHIVO_PRUEBA);
        lenient().when(solicitudCodigosSPARDGenerator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(autorizacionTramitesGenerator.getNombreArchivo()).thenReturn(NOMBRE_ARCHIVO_PRUEBA);
        lenient().when(autorizacionTramitesGenerator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(autorizacionConexionGenerator.getNombreArchivo()).thenReturn(NOMBRE_ARCHIVO_PRUEBA);
        lenient().when(autorizacionConexionGenerator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(noHayPermisoLineasCarreteraGenerator.getNombreArchivo()).thenReturn(NOMBRE_ARCHIVO_PRUEBA);
        lenient().when(noHayPermisoLineasCarreteraGenerator.generarDocumento(any()))
                .thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(declaracion236Generator.getNombreArchivo()).thenReturn(NOMBRE_ARCHIVO_PRUEBA);
        lenient().when(declaracion236Generator.generarDocumento(any())).thenReturn(CONTENIDO_PRUEBA.getBytes());

        lenient().when(otorgamientoServidumbreGenerator.getNombreArchivo()).thenReturn(NOMBRE_ARCHIVO_PRUEBA);
        lenient().when(otorgamientoServidumbreGenerator.generarDocumento(any()))
                .thenReturn(CONTENIDO_PRUEBA.getBytes());
    }

    @Test
    void generarDocumentoSolicitudCodigosSPARD_DeberiaGenerarDocumento() throws Exception {
        // Preparar datos específicos para esta prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");

        SolicitudCodigosSPARD datosModel = new SolicitudCodigosSPARD();
        datosModel.setFromName("Nombre Prueba");
        datosModel.setFromIdentification("123456789");
        request.setBody(datosModel);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(request);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(CONTENIDO_PRUEBA, new String(result.toByteArray()));
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
    }

    @Test
    void generarDocumentoAutorizacionTramites_DeberiaGenerarDocumento() throws Exception {
        // Preparar datos específicos para esta prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("AUTORIZACION_TRAMITES");

        AutorizacionTramites datosModel = new AutorizacionTramites();
        datosModel.setFromName("María González");
        datosModel.setFromIdentification("987654321");
        request.setBody(datosModel);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(request);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(CONTENIDO_PRUEBA, new String(result.toByteArray()));
        verify(autorizacionTramitesGenerator).generarDocumento(any());
    }

    @Test
    void generarDocumentoAutorizacionConexion_DeberiaGenerarDocumento() throws Exception {
        // Preparar datos específicos para esta prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("AUTORIZACION_CONEXION");

        AutorizacionConexion datosModel = new AutorizacionConexion();
        datosModel.setFromName("Carlos Ramírez");
        datosModel.setFromIdentification("456789123");
        request.setBody(datosModel);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(request);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(CONTENIDO_PRUEBA, new String(result.toByteArray()));
        verify(autorizacionConexionGenerator).generarDocumento(any());
    }

    @Test
    void generarDocumentoNoHayPermisosLineasCarreteras_DeberiaGenerarDocumento() throws Exception {
        // Preparar datos específicos para esta prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("NO_HAY_PERMISOS_LINEAS_CARRETERAS");

        NoHayPermisisosLineasCarreteras datosModel = new NoHayPermisisosLineasCarreteras();
        datosModel.setFromName("Ana Martínez");
        datosModel.setFromIdentification("321654987");
        request.setBody(datosModel);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(request);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(CONTENIDO_PRUEBA, new String(result.toByteArray()));
        verify(noHayPermisoLineasCarreteraGenerator).generarDocumento(any());
    }

    @Test
    void generarDocumentoDeclaracion236_DeberiaGenerarDocumento() throws Exception {
        // Preparar datos específicos para esta prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("DECLARACION_236");

        Declaracion236 datosModel = new Declaracion236();
        datosModel.setFromName("Roberto Sánchez");
        datosModel.setFromIdentification("789123456");
        request.setBody(datosModel);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(request);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(CONTENIDO_PRUEBA, new String(result.toByteArray()));
        verify(declaracion236Generator).generarDocumento(any());
    }

    @Test
    void generarDocumentoOtorgamientoServidumbre_DeberiaGenerarDocumento() throws Exception {
        // Preparar datos específicos para esta prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("OTORGAMIENTO_SERVIDUMBRE");

        OtorgamientoServidumbre datosModel = new OtorgamientoServidumbre();
        datosModel.setFromName("Laura Díaz");
        datosModel.setFromIdentification("654789321");
        request.setBody(datosModel);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentoIndividual(request);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(CONTENIDO_PRUEBA, new String(result.toByteArray()));
        verify(otorgamientoServidumbreGenerator).generarDocumento(any());
    }

    @Test
    void generarDocumentoIndividual_DeberiaLanzarExcepcionSiTipoNoExiste() {
        // Preparar datos para la prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento("TIPO_NO_EXISTENTE");
        request.setBody(new Object());

        // Verificar que se lanza la excepción
        assertThrows(RuntimeException.class, () -> {
            documentoService.generarDocumentoIndividual(request);
        });
    }

    @Test
    void generarDocumentoIndividual_DeberiaLanzarExcepcionSiTipoEsNulo() {
        // Preparar datos para la prueba
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento(null);
        request.setBody(new Object());

        // Verificar que se lanza la excepción
        assertThrows(RuntimeException.class, () -> {
            documentoService.generarDocumentoIndividual(request);
        });
    }

    @Test
    void generarDocumentos_DeberiaGenerarZip() throws Exception {
        // Preparar datos para la prueba con múltiples tipos de documento
        SolicitudDocumentos solicitud = new SolicitudDocumentos();

        // Crear una lista con varios tipos de documentos
        List<DocumentoRequest> documentos = Arrays.asList(
                crearDocumentoRequest("SOLICITUD_CODIGOS_SPARD", new SolicitudCodigosSPARD()),
                crearDocumentoRequest("AUTORIZACION_TRAMITES", new AutorizacionTramites()),
                crearDocumentoRequest("DECLARACION_236", new Declaracion236()));

        solicitud.setDocumentos(documentos);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentos(solicitud);

        // Verificar el resultado (debe contener los documentos en ZIP)
        assertNotNull(result);
        assertTrue(result.size() > 0);

        // Verificar que se llamaron los generadores correspondientes
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
        verify(autorizacionTramitesGenerator).generarDocumento(any());
        verify(declaracion236Generator).generarDocumento(any());
    }

    @Test
    void generarDocumentos_DeberiaIgnorarDocumentosInvalidos() throws Exception {
        // Preparar datos para la prueba con un documento válido y uno inválido
        SolicitudDocumentos solicitud = new SolicitudDocumentos();

        // Crear una lista con un documento válido y otro inválido
        List<DocumentoRequest> documentos = Arrays.asList(
                crearDocumentoRequest("SOLICITUD_CODIGOS_SPARD", new SolicitudCodigosSPARD()),
                crearDocumentoRequest(null, new Object()) // Este debería ser ignorado
        );

        solicitud.setDocumentos(documentos);

        // Ejecutar el método bajo prueba
        ByteArrayOutputStream result = documentoService.generarDocumentos(solicitud);

        // Verificar el resultado (debe contener solo el documento válido)
        assertNotNull(result);
        assertTrue(result.size() > 0);

        // Verificar que solo se llamó al generador del documento válido
        verify(solicitudCodigosSPARDGenerator).generarDocumento(any());
    }

    // Método auxiliar para crear DocumentoRequest
    private DocumentoRequest crearDocumentoRequest(String tipo, Object datos) {
        DocumentoRequest request = new DocumentoRequest();
        request.setTipoDocumento(tipo);
        request.setBody(datos);
        return request;
    }

    // Método para verificar que un byte array no está vacío
    private boolean verificarByteArrayNoVacio(byte[] datos) {
        return datos != null && datos.length > 0;
    }
}