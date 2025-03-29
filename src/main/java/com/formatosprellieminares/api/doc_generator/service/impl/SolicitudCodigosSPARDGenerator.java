package com.formatosprellieminares.api.doc_generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formatosprellieminares.api.doc_generator.model.SolicitudCodigosSPARD;
import com.formatosprellieminares.api.doc_generator.service.DocumentoGenerator;

public class SolicitudCodigosSPARDGenerator implements DocumentoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudCodigosSPARDGenerator.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] generarDocumento(Object datos) {
        try {
            logger.info("Iniciando generación de documento SolicitudCodigosSPARD");
            logger.debug("Datos recibidos para deserializar: {}", datos);
            SolicitudCodigosSPARD solicitud = objectMapper.convertValue(datos, SolicitudCodigosSPARD.class);
            logger.debug("Datos deserializados: {}", solicitud);

            // Cargar la plantilla desde resources
            InputStream templateStream = getClass().getResourceAsStream("/docs/SOLICITUD DE CODIGOS-240.docx");
            if (templateStream == null) {
                logger.error("No se pudo encontrar el archivo de plantilla");
                throw new RuntimeException("No se pudo encontrar el archivo de plantilla");
            }
            XWPFDocument document = new XWPFDocument(templateStream);
            logger.debug("Plantilla cargada correctamente");

            // Reemplazar los campos en el documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                if (!runs.isEmpty()) {
                    StringBuilder fullText = new StringBuilder();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            fullText.append(text);
                        }
                    }

                    String text = fullText.toString();
                    logger.debug("Texto completo del párrafo: {}", text);

                    if (text.contains("${")) {
                        text = text
                                .replace("${fromAddress}", solicitud.getFromAddress())
                                .replace("${date}", solicitud.getDate())
                                .replace("${fromName}", solicitud.getFromName())
                                .replace("${fromIdentification}", solicitud.getFromIdentification())
                                .replace("${projectLocation}", solicitud.getProjectLocation())
                                .replace("${projectAddress}", solicitud.getProjectAddress())
                                .replace("${userName}", solicitud.getUserName())
                                .replace("${userIdentification}", solicitud.getUserIdentification())
                                .replace("${userLocation}", solicitud.getUserLocation())
                                .replace("${fromName2}", solicitud.getFromName2())
                                .replace("${fromIdentification2}", solicitud.getFromIdentification2())
                                .replace("${fromPhone}", solicitud.getFromPhone())
                                .replace("${fromEmail}", solicitud.getFromEmail());

                        // Limpiar todos los runs existentes
                        for (int i = runs.size() - 1; i >= 0; i--) {
                            paragraph.removeRun(i);
                        }

                        // Crear un nuevo run con el texto reemplazado
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(text);
                    }
                }
            }

            // Reemplazar en tablas
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            List<XWPFRun> runs = paragraph.getRuns();
                            if (!runs.isEmpty()) {
                                StringBuilder fullText = new StringBuilder();
                                for (XWPFRun run : runs) {
                                    String text = run.getText(0);
                                    if (text != null) {
                                        fullText.append(text);
                                    }
                                }

                                String text = fullText.toString();
                                logger.debug("Texto completo de la celda: {}", text);

                                if (text.contains("${")) {
                                    text = text
                                            .replace("${fromAddress}", solicitud.getFromAddress())
                                            .replace("${date}", solicitud.getDate())
                                            .replace("${fromName}", solicitud.getFromName())
                                            .replace("${fromIdentification}", solicitud.getFromIdentification())
                                            .replace("${projectLocation}", solicitud.getProjectLocation())
                                            .replace("${projectAddress}", solicitud.getProjectAddress())
                                            .replace("${userName}", solicitud.getUserName())
                                            .replace("${userIdentification}", solicitud.getUserIdentification())
                                            .replace("${userLocation}", solicitud.getUserLocation())
                                            .replace("${fromName2}", solicitud.getFromName2())
                                            .replace("${fromIdentification2}", solicitud.getFromIdentification2())
                                            .replace("${fromPhone}", solicitud.getFromPhone())
                                            .replace("${fromEmail}", solicitud.getFromEmail());

                                    // Limpiar todos los runs existentes
                                    for (int i = runs.size() - 1; i >= 0; i--) {
                                        paragraph.removeRun(i);
                                    }

                                    // Crear un nuevo run con el texto reemplazado
                                    XWPFRun newRun = paragraph.createRun();
                                    newRun.setText(text);
                                }
                            }
                        }
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();
            templateStream.close();
            logger.info("Documento generado exitosamente");

            return outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Error al generar el documento SolicitudCodigosSPARD", e);
            throw new RuntimeException("Error al generar el documento SolicitudCodigosSPARD", e);
        }
    }

    @Override
    public String getNombreArchivo() {
        return "SOLICITUD_CODIGOS_SPARD.docx";
    }
}