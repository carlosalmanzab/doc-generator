package com.formatosprellieminares.api.doc_generator.service;

public interface DocumentoGenerator {
    byte[] generarDocumento(Object datos);

    String getNombreArchivo();
}