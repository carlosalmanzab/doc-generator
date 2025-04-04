package com.formatosprellieminares.api.doc_generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;
import com.formatosprellieminares.api.doc_generator.service.DocumentoGenerator;
import com.formatosprellieminares.api.doc_generator.service.DocumentoService;

/**
 * Implementación del servicio de generación de documentos.
 * Maneja la generación de documentos individuales y en lote.
 */
@Service
public class DocumentoServiceImpl implements DocumentoService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);
    private final Map<String, DocumentoGenerator> generadores;

    /**
     * Constructor que inicializa los generadores de documentos disponibles.
     */
    public DocumentoServiceImpl() {
        this.generadores = new HashMap<>();
        // Registrar los generadores de documentos
        this.generadores.put("SOLICITUD_CODIGOS_SPARD", new SolicitudCodigosSPARDGenerator());
        this.generadores.put("AUTORIZACION_TRAMITES", new AutorizacionTramitesGenerator());
        this.generadores.put("AUTORIZACION_CONEXION", new AutorizacionConexionGenerator());
        this.generadores.put("NO_HAY_PERMISO_LINEAS_CARRETERA", new NoHayPermisoLineasCarreteraGenerator());
        this.generadores.put("DECLARACION_236", new Declaracion236Generator());
        logger.info("Generadores de documentos inicializados");
    }

    /**
     * Genera múltiples documentos y los devuelve en un archivo ZIP.
     *
     * @param solicitud Objeto que contiene la lista de documentos a generar
     * @return ByteArrayOutputStream con el archivo ZIP que contiene los documentos
     *         generados
     * @throws RuntimeException si hay un error al generar los documentos
     */
    @Override
    public ByteArrayOutputStream generarDocumentos(SolicitudDocumentos solicitud) {
        logger.info("Iniciando generación de {} documentos", solicitud.getDocumentos().size());
        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
        Map<String, Integer> contadorArchivos = new HashMap<>();

        try (ZipOutputStream zos = new ZipOutputStream(zipOutputStream)) {
            for (DocumentoRequest docRequest : solicitud.getDocumentos()) {
                if (docRequest == null) {
                    logger.warn("Se encontró una solicitud de documento nula");
                    continue;
                }

                if (docRequest.getTipoDocumento() == null || docRequest.getTipoDocumento().trim().isEmpty()) {
                    logger.warn("Se encontró una solicitud de documento sin tipo");
                    continue;
                }

                if (docRequest.getBody() == null) {
                    logger.warn("Se encontró una solicitud de documento sin datos para el tipo: {}",
                            docRequest.getTipoDocumento());
                    continue;
                }

                DocumentoGenerator generator = generadores.get(docRequest.getTipoDocumento());
                if (generator != null) {
                    logger.debug("Generando documento de tipo: {}", docRequest.getTipoDocumento());
                    byte[] documentoBytes = generator.generarDocumento(docRequest.getBody());

                    // Obtener el nombre base del archivo
                    String nombreBase = generator.getNombreArchivo().replace(".docx", "");

                    // Incrementar el contador para este tipo de documento
                    int contador = contadorArchivos.getOrDefault(nombreBase, 0) + 1;
                    contadorArchivos.put(nombreBase, contador);

                    // Crear el nombre del archivo con el contador si es necesario
                    String nombreArchivo = contador > 1 ? String.format("%s-%d.docx", nombreBase, contador)
                            : String.format("%s.docx", nombreBase);

                    logger.debug("Guardando documento como: {}", nombreArchivo);
                    ZipEntry entry = new ZipEntry(nombreArchivo);
                    zos.putNextEntry(entry);
                    zos.write(documentoBytes);
                    zos.closeEntry();
                    logger.debug("Documento {} generado exitosamente", nombreArchivo);
                } else {
                    logger.warn("Tipo de documento no soportado: {}", docRequest.getTipoDocumento());
                }
            }
            logger.info("Todos los documentos generados exitosamente");
        } catch (Exception e) {
            logger.error("Error al generar los documentos", e);
            throw new RuntimeException("Error al generar los documentos", e);
        }

        return zipOutputStream;
    }

    /**
     * Genera un documento individual.
     *
     * @param request Objeto que contiene el tipo y datos del documento a generar
     * @return ByteArrayOutputStream con el documento generado
     * @throws RuntimeException si hay un error al generar el documento o si el tipo
     *                          no es soportado
     */
    @Override
    public ByteArrayOutputStream generarDocumentoIndividual(DocumentoRequest request) {
        logger.info("Iniciando generación de documento individual");
        logger.debug("Request recibido en servicio: {}", request);
        logger.debug("Tipo de documento en request: {}", request.getTipoDocumento());
        logger.debug("Generadores disponibles: {}", generadores.keySet());

        if (request.getTipoDocumento() == null || request.getTipoDocumento().trim().isEmpty()) {
            String error = "El tipo de documento no puede ser nulo o vacío";
            logger.error(error);
            throw new RuntimeException(error);
        }

        DocumentoGenerator generator = generadores.get(request.getTipoDocumento());

        if (generator == null) {
            String error = "Tipo de documento no soportado: " + request.getTipoDocumento();
            logger.error(error);
            throw new RuntimeException(error);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            logger.debug("Generando documento con el generador: {}", generator.getClass().getSimpleName());
            byte[] documentoBytes = generator.generarDocumento(request.getBody());
            outputStream.write(documentoBytes);
            logger.info("Documento generado exitosamente");
        } catch (Exception e) {
            logger.error("Error al generar el documento", e);
            throw new RuntimeException("Error al generar el documento", e);
        }

        return outputStream;
    }
}