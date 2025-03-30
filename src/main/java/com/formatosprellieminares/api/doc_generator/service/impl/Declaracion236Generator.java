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
import com.formatosprellieminares.api.doc_generator.model.Declaracion236;
import com.formatosprellieminares.api.doc_generator.service.DocumentoGenerator;

public class Declaracion236Generator implements DocumentoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(Declaracion236Generator.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] generarDocumento(Object datos) {
        try {
            logger.info("Iniciando generación de documento Declaracion236");
            Declaracion236 declaracion = objectMapper.convertValue(datos, Declaracion236.class);
            logger.debug("Datos convertidos: {}", declaracion);

            // Cargar la plantilla desde resources
            InputStream templateStream = getClass().getResourceAsStream("/docs/DECLARACION-236.docx");
            if (templateStream == null) {
                logger.error("No se pudo encontrar el archivo de plantilla");
                throw new RuntimeException("No se pudo encontrar el archivo de plantilla");
            }
            XWPFDocument document = new XWPFDocument(templateStream);
            logger.debug("Plantilla cargada correctamente");

            // Procesar párrafos en el documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                processParagraph(paragraph, declaracion);
            }

            // Procesar tablas
            for (XWPFTable table : document.getTables()) {
                processTable(table, declaracion);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();
            templateStream.close();
            logger.info("Documento generado exitosamente");

            return outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Error al generar el documento Declaracion236", e);
            throw new RuntimeException("Error al generar el documento Declaracion236", e);
        }
    }

    private void processTable(XWPFTable table, Declaracion236 declaracion) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    processParagraph(paragraph, declaracion);
                }
            }
        }
    }

    private void processParagraph(XWPFParagraph paragraph, Declaracion236 declaracion) {
        String paragraphText = paragraph.getText();
        if (paragraphText == null || paragraphText.isEmpty()) {
            return;
        }

        // Realizar los reemplazos en el texto completo
        String replacedText = replaceVariables(paragraphText, declaracion);

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

    private String replaceVariables(String text, Declaracion236 declaracion) {
        // Primero reemplazamos las variables que podrían ser parte de otras variables
        String replacedText = text
                .replace("${year}", nullSafe(declaracion.getYear()))
                .replace("${fromName}", nullSafe(declaracion.getFromName()))
                .replace("${fromIdentification}", nullSafe(declaracion.getFromIdentification()))
                .replace("${fromLocation}", nullSafe(declaracion.getFromLocation()))
                .replace("${fromProfessionalNumber}", nullSafe(declaracion.getFromProfessionalNumber()))
                .replace("${installationDescription}", nullSafe(declaracion.getInstallationDescription()))
                .replace("${projectName}", nullSafe(declaracion.getProjectName()))
                .replace("${projectLocation}", nullSafe(declaracion.getProjectLocation()))
                .replace("${projectAddress}", nullSafe(declaracion.getProjectAddress()))
                .replace("${userName}", nullSafe(declaracion.getUserName()))
                .replace("${userIdentification}", nullSafe(declaracion.getUserIdentification()))
                .replace("${signatureLocation}", nullSafe(declaracion.getSignatureLocation()))
                .replace("${signatureDate}", nullSafe(declaracion.getSignatureDate()))
                .replace("${signature}", nullSafe(declaracion.getSignature()))
                .replace("${comments}", nullSafe(declaracion.getComments()))
                .replace("${annexedDocuments}", nullSafe(declaracion.getAnnexedDocuments()))
                .replace("${responsibleAddress}", nullSafe(declaracion.getResponsibleAddress()))
                .replace("${phone}", nullSafe(declaracion.getPhone()));

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
        return "DECLARACION-236.docx";
    }
}