package com.formatosprellieminares.api.doc_generator.controller;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;
import com.formatosprellieminares.api.doc_generator.dto.example.ExampleRequest;
import com.formatosprellieminares.api.doc_generator.service.DocumentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la generación de documentos.
 * Proporciona endpoints para generar diferentes tipos de documentos de forma
 * individual o en lote.
 */
@RestController
@RequestMapping("/api/documentos")
@Tag(name = "Generador de Documentos", description = "API para generar diferentes tipos de documentos")
public class DocumentoController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentoController.class);

    @Autowired
    private DocumentoService documentoService;

    /**
     * Genera múltiples documentos y los devuelve en un archivo ZIP.
     *
     * @param solicitud Objeto que contiene la lista de documentos a generar
     * @return ResponseEntity con el archivo ZIP que contiene los documentos
     *         generados
     */
    @Operation(summary = "Generar múltiples documentos", description = "Genera varios documentos y los devuelve en un archivo ZIP. Permite generar múltiples documentos de diferentes tipos en una sola solicitud.", operationId = "generarDocumentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos generados exitosamente", content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Verifique el formato de los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor - Contacte al administrador")
    })
    @PostMapping("/generar")
    public ResponseEntity<byte[]> generarDocumentos(
            @Parameter(description = "Solicitud con la lista de documentos a generar", required = true, schema = @Schema(implementation = SolicitudDocumentos.class)) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Solicitud con la lista de documentos a generar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolicitudDocumentos.class), examples = @ExampleObject(value = ExampleRequest.MULTIPLE_DOCUMENTOS))) @RequestBody SolicitudDocumentos solicitud) {
        logger.info("Iniciando generación de múltiples documentos");
        try {
            ByteArrayOutputStream zipOutputStream = documentoService.generarDocumentos(solicitud);
            logger.debug("Documentos generados exitosamente");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "documentos.zip");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(zipOutputStream.toByteArray());
        } catch (Exception e) {
            logger.error("Error al generar los documentos", e);
            throw e;
        }
    }

    /**
     * Genera un documento de solicitud de códigos SPARD.
     *
     * @param request Objeto que contiene los datos para generar el documento
     * @return ResponseEntity con el documento generado
     */
    @Operation(summary = "Generar Solicitud de Códigos SPARD", description = "Genera un documento de solicitud de códigos SPARD. Este documento es utilizado para solicitar códigos específicos del sistema SPARD.", operationId = "generarSolicitudCodigosSPARD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento generado exitosamente", content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Verifique el formato de los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor - Contacte al administrador")
    })
    @PostMapping("/solicitud-codigos-spard")
    public ResponseEntity<byte[]> generarSolicitudCodigosSPARD(
            @Parameter(description = "Datos para generar la solicitud de códigos SPARD", required = true, schema = @Schema(implementation = DocumentoRequest.class)) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para generar la solicitud de códigos SPARD", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoRequest.class), examples = @ExampleObject(value = ExampleRequest.SOLICITUD_CODIGOS_SPARD))) @RequestBody DocumentoRequest request) {
        logger.info("Iniciando generación de solicitud de códigos SPARD");
        try {
            logger.debug("Request recibido: {}", request);
            if (request == null) {
                request = new DocumentoRequest();
            }
            if (request.getBody() == null) {
                throw new RuntimeException("Los datos del documento no pueden ser nulos");
            }
            request.setTipoDocumento("SOLICITUD_CODIGOS_SPARD");
            logger.debug("Tipo de documento establecido: {}", request.getTipoDocumento());
            ByteArrayOutputStream outputStream = documentoService.generarDocumentoIndividual(request);
            logger.debug("Solicitud de códigos SPARD generada exitosamente");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "SOLICITUD_CODIGOS_SPARD.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("Error al generar la solicitud de códigos SPARD", e);
            throw e;
        }
    }

    /**
     * Genera un documento de autorización de trámites.
     *
     * @param request Objeto que contiene los datos para generar el documento
     * @return ResponseEntity con el documento generado
     */
    @Operation(summary = "Generar Autorización de Trámites", description = "Genera un documento de autorización de trámites. Este documento es utilizado para autorizar diferentes tipos de trámites en el sistema.", operationId = "generarAutorizacionTramites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento generado exitosamente", content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Verifique el formato de los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor - Contacte al administrador")
    })
    @PostMapping("/autorizacion-tramites")
    public ResponseEntity<byte[]> generarAutorizacionTramites(
            @Parameter(description = "Datos para generar la autorización de trámites", required = true, schema = @Schema(implementation = DocumentoRequest.class)) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para generar la autorización de trámites", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoRequest.class), examples = @ExampleObject(value = ExampleRequest.AUTORIZACION_TRAMITES))) @RequestBody DocumentoRequest request) {
        logger.info("Iniciando generación de autorización de trámites");
        try {
            logger.debug("Request recibido: {}", request);
            if (request == null) {
                request = new DocumentoRequest();
            }
            if (request.getBody() == null) {
                throw new RuntimeException("Los datos del documento no pueden ser nulos");
            }
            request.setTipoDocumento("AUTORIZACION_TRAMITES");
            logger.debug("Tipo de documento establecido: {}", request.getTipoDocumento());
            ByteArrayOutputStream outputStream = documentoService.generarDocumentoIndividual(request);
            logger.debug("Autorización de trámites generada exitosamente");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "AUTORIZACION_TRAMITES.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("Error al generar la autorización de trámites", e);
            throw e;
        }
    }

    /**
     * Genera un documento de autorización de conexión.
     *
     * @param request Objeto que contiene los datos para generar el documento
     * @return ResponseEntity con el documento generado
     */
    @Operation(summary = "Generar Autorización de Conexión", description = "Genera un documento de autorización de conexión. Este documento es utilizado para autorizar conexiones en el sistema.", operationId = "generarAutorizacionConexion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento generado exitosamente", content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Verifique el formato de los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor - Contacte al administrador")
    })
    @PostMapping("/autorizacion-conexion")
    public ResponseEntity<byte[]> generarAutorizacionConexion(
            @Parameter(description = "Datos para generar la autorización de conexión", required = true, schema = @Schema(implementation = DocumentoRequest.class)) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para generar la autorización de conexión", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoRequest.class), examples = @ExampleObject(value = ExampleRequest.AUTORIZACION_CONEXION))) @RequestBody DocumentoRequest request) {
        logger.info("Iniciando generación de autorización de conexión");
        try {
            logger.debug("Request recibido: {}", request);
            if (request == null) {
                request = new DocumentoRequest();
            }
            if (request.getBody() == null) {
                throw new RuntimeException("Los datos del documento no pueden ser nulos");
            }
            request.setTipoDocumento("AUTORIZACION_CONEXION");
            logger.debug("Tipo de documento establecido: {}", request.getTipoDocumento());
            ByteArrayOutputStream outputStream = documentoService.generarDocumentoIndividual(request);
            logger.debug("Autorización de conexión generada exitosamente");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "AUTORIZACION_CONEXION.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("Error al generar la autorización de conexión", e);
            throw e;
        }
    }

    /**
     * Genera un documento de no hay permiso para líneas de carretera.
     *
     * @param request Objeto que contiene los datos para generar el documento
     * @return ResponseEntity con el documento generado
     */
    @Operation(summary = "Generar No Hay Permiso Líneas Carretera", description = "Genera un documento de no hay permiso para líneas de carretera. Este documento es utilizado para indicar que no hay permisos para líneas en carreteras.", operationId = "generarNoHayPermisoLineasCarretera")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento generado exitosamente", content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Verifique el formato de los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor - Contacte al administrador")
    })
    @PostMapping("/no-hay-permiso-lineas-carretera")
    public ResponseEntity<byte[]> generarNoHayPermisoLineasCarretera(
            @Parameter(description = "Datos para generar el documento de no hay permiso", required = true, schema = @Schema(implementation = DocumentoRequest.class)) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para generar el documento de no hay permiso", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoRequest.class), examples = @ExampleObject(value = ExampleRequest.NO_HAY_PERMISO_LINEAS_CARRETERA))) @RequestBody DocumentoRequest request) {
        logger.info("Iniciando generación de documento no hay permiso líneas carretera");
        try {
            logger.debug("Request recibido: {}", request);
            if (request == null) {
                request = new DocumentoRequest();
            }
            if (request.getBody() == null) {
                throw new RuntimeException("Los datos del documento no pueden ser nulos");
            }
            request.setTipoDocumento("NO_HAY_PERMISO_LINEAS_CARRETERA");
            logger.debug("Tipo de documento establecido: {}", request.getTipoDocumento());
            ByteArrayOutputStream outputStream = documentoService.generarDocumentoIndividual(request);
            logger.debug("Documento no hay permiso líneas carretera generado exitosamente");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "NO_HAY_PERMISO_LINEAS_CARRETERA.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            logger.error("Error al generar el documento no hay permiso líneas carretera", e);
            throw e;
        }
    }
}