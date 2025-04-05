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
import com.formatosprellieminares.api.doc_generator.model.OtorgamientoServidumbre;
import com.formatosprellieminares.api.doc_generator.service.DocumentoGenerator;

public class OtorgamientoServidumbreGenerator implements DocumentoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(OtorgamientoServidumbreGenerator.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] generarDocumento(Object datos) {
        try {
            logger.info("Iniciando generación de documento Otorgamiento de Servidumbre");
            OtorgamientoServidumbre servidumbre = objectMapper.convertValue(datos, OtorgamientoServidumbre.class);
            logger.debug("Datos convertidos: {}", servidumbre);

            // Cargar la plantilla desde resources
            InputStream templateStream = getClass().getResourceAsStream("/docs/FT-GRS-01-006-240.docx");
            if (templateStream == null) {
                logger.error("No se pudo encontrar el archivo de plantilla");
                throw new RuntimeException("No se pudo encontrar el archivo de plantilla");
            }
            XWPFDocument document = new XWPFDocument(templateStream);
            logger.debug("Plantilla cargada correctamente");

            // Procesar párrafos en el documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                processParagraph(paragraph, servidumbre);
            }

            // Procesar tablas
            for (XWPFTable table : document.getTables()) {
                processTable(table, servidumbre);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();
            templateStream.close();
            logger.info("Documento generado exitosamente");

            return outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Error al generar el documento Otorgamiento de Servidumbre", e);
            throw new RuntimeException("Error al generar el documento Otorgamiento de Servidumbre", e);
        }
    }

    private void processTable(XWPFTable table, OtorgamientoServidumbre servidumbre) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    processParagraph(paragraph, servidumbre);
                }
            }
        }
    }

    private void processParagraph(XWPFParagraph paragraph, OtorgamientoServidumbre servidumbre) {
        String paragraphText = paragraph.getText();
        if (paragraphText == null || paragraphText.isEmpty()) {
            return;
        }

        // Realizar los reemplazos en el texto completo
        String replacedText = replaceVariables(paragraphText, servidumbre);

        // Si el texto no cambió, no hacemos nada
        if (paragraphText.equals(replacedText)) {
            return;
        }

        // Limpiar el párrafo
        while (paragraph.getRuns().size() > 0) {
            paragraph.removeRun(0);
        }

        // Crear un nuevo run con el texto reemplazado
        XWPFRun run = paragraph.createRun();
        run.setText(replacedText);

        // Preservar el formato del primer run original si existe
        List<XWPFRun> originalRuns = paragraph.getRuns();
        if (!originalRuns.isEmpty()) {
            XWPFRun originalRun = originalRuns.get(0);
            run.setBold(originalRun.isBold());
            run.setItalic(originalRun.isItalic());
            run.setFontFamily(originalRun.getFontFamily());
            run.setFontSize(originalRun.getFontSize());
            if (originalRun.getColor() != null) {
                run.setColor(originalRun.getColor());
            }
            run.setUnderline(originalRun.getUnderline());
        }
    }

    private String replaceVariables(String text, OtorgamientoServidumbre servidumbre) {
        String replacedText = text
                .replace("${date}", nullSafe(servidumbre.getDate()))
                .replace("${fromName}", nullSafe(servidumbre.getFromName()))
                .replace("${fromIdentification}", nullSafe(servidumbre.getFromIdentification()))
                .replace("${fromLocation}", nullSafe(servidumbre.getFromLocation()))
                .replace("${propertyName}", nullSafe(servidumbre.getPropertyName()))
                .replace("${propertyRegistry}", nullSafe(servidumbre.getPropertyRegistry()))
                .replace("${projectLocation}", nullSafe(servidumbre.getProjectLocation()))
                .replace("${projectAddress}", nullSafe(servidumbre.getProjectAddress()))
                .replace("${safetyWidth}", nullSafe(servidumbre.getSafetyWidth()))
                .replace("${safetyLength}", nullSafe(servidumbre.getSafetyLength()))
                .replace("${networkVoltage}", nullSafe(servidumbre.getNetworkVoltage()))
                .replace("${networkType}", nullSafe(servidumbre.getNetworkType()))
                .replace("${signatureDay}", nullSafe(servidumbre.getSignatureDay()))
                .replace("${signatureMonth}", nullSafe(servidumbre.getSignatureMonth()))
                .replace("${signatureYear}", nullSafe(servidumbre.getSignatureYear()))
                .replace("${department}", nullSafe(servidumbre.getDepartment()))
                .replace("${signature}", nullSafe(servidumbre.getSignature()))
                .replace("${phone}", nullSafe(servidumbre.getPhone()))
                .replace("${fromAddress}", nullSafe(servidumbre.getFromAddress()))
                .replace("${email}", nullSafe(servidumbre.getEmail()));

        // Verificar si quedó alguna variable sin reemplazar y loguearla
        if (replacedText.contains("${")) {
            int start = replacedText.indexOf("${");
            int end = replacedText.indexOf("}", start);
            if (end > start) {
                String unreplacedVar = replacedText.substring(start, end + 1);
                logger.warn("Variable no reemplazada encontrada: {}", unreplacedVar);
            }
        }

        return replacedText;
    }

    private String nullSafe(String value) {
        return value != null ? value : "";
    }

    @Override
    public String getNombreArchivo() {
        return "FT-GRS-01-006-240.docx";
    }
}