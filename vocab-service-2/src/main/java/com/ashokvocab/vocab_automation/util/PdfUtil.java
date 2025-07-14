package com.ashokvocab.vocab_automation.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import java.io.ByteArrayInputStream;

public class PdfUtil {
    public static String extractText(byte[] pdfBytes) {
        try (PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes))) {
            PdfDocument pdfDoc = new PdfDocument(reader);
            StringBuilder text = new StringBuilder();
            int numPages = pdfDoc.getNumberOfPages();
            for (int i = 1; i <= numPages; i++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i)));
                text.append("\n");
            }
            pdfDoc.close();
            return text.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract text from PDF", e);
        }
    }

    public static String extractSection(String pdfText, String sectionTitle) {
        String[] lines = pdfText.split("\\r?\\n");
        StringBuilder section = new StringBuilder();
        boolean inSection = false;
        for (String line : lines) {
            if (line.trim().equalsIgnoreCase(sectionTitle)) {
                inSection = true;
                continue;
            }
            if (inSection && line.matches("^[A-Za-z ]+Story$") && !line.trim().equalsIgnoreCase(sectionTitle)) {
                break;
            }
            if (inSection) {
                section.append(line).append("\n");
            }
        }
        return section.toString().trim();
    }
}