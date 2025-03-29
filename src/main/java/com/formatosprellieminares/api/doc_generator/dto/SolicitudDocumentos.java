package com.formatosprellieminares.api.doc_generator.dto;

import java.util.List;

import lombok.Data;

@Data
public class SolicitudDocumentos {
    private List<DocumentoRequest> documentos;
}