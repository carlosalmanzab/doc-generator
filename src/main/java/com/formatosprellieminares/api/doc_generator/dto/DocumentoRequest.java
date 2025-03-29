package com.formatosprellieminares.api.doc_generator.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.Data;

@Data
public class DocumentoRequest {
    private String tipoDocumento;

    @JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
    private Object datos;
}