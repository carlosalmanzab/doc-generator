package com.formatosprellieminares.api.doc_generator.dto;

import lombok.Data;

@Data
public class DocumentoRequest {
    private String tipoDocumento;
    private Object body;
}