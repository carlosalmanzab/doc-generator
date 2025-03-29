package com.formatosprellieminares.api.doc_generator.service;

import java.io.ByteArrayOutputStream;

import com.formatosprellieminares.api.doc_generator.dto.DocumentoRequest;
import com.formatosprellieminares.api.doc_generator.dto.SolicitudDocumentos;

public interface DocumentoService {
    ByteArrayOutputStream generarDocumentos(SolicitudDocumentos solicitud);

    ByteArrayOutputStream generarDocumentoIndividual(DocumentoRequest request);
}