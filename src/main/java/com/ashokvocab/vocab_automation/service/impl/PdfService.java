package com.ashokvocab.vocab_automation.service.impl;

                import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
                import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
                import com.itextpdf.kernel.geom.PageSize;
                import com.itextpdf.kernel.pdf.PdfDocument;
                import com.itextpdf.kernel.pdf.PdfWriter;
                import com.itextpdf.layout.Document;
                import com.itextpdf.layout.element.Paragraph;
                import com.itextpdf.layout.properties.TextAlignment;
                import org.springframework.stereotype.Service;

                import java.io.ByteArrayOutputStream;
                import java.util.List;
                import com.itextpdf.kernel.pdf.PdfDocument;
                import com.itextpdf.kernel.pdf.PdfReader;
                import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
                import java.io.ByteArrayInputStream;
                import com.ashokvocab.vocab_automation.util.PdfUtil;

                @Service
                public class PdfService {

                    public byte[] generatePdf(List<VocabularyDTO> words, String generalStory, String softwareStory) {
                        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                            PdfWriter writer = new PdfWriter(baos);
                            PdfDocument pdf = new PdfDocument(writer);

                            // Set page size to A4 with margins
                            Document document = new Document(pdf, PageSize.A4);
                            document.setMargins(36, 36, 36, 36); // 36 points = 0.5 inch margins

                            // Add title
                            document.add(new Paragraph("Daily Vocabulary")
                                    .setFontSize(24)
                                    .setBold()
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setMarginBottom(20));

                            // Add vocabulary words section
                            document.add(new Paragraph("Vocabulary Words")
                                    .setFontSize(18)
                                    .setBold()
                                    .setMarginTop(20)
                                    .setMarginBottom(10));

                            // Add words with bullet points
                            for (VocabularyDTO word : words) {
                                String wordEntry = String.format("• %s: %s", word.getWord(), word.getMeaning());
                                document.add(new Paragraph(wordEntry)
                                        .setFontSize(14)
                                        .setMarginLeft(20)
                                        .setMarginBottom(5));
                            }

                            // Add stories section with proper spacing
                            document.add(new Paragraph("Stories")
                                    .setFontSize(18)
                                    .setBold()
                                    .setMarginTop(20)
                                    .setMarginBottom(10));

                            document.add(new Paragraph("General Story")
                                    .setFontSize(16)
                                    .setBold()
                                    .setMarginBottom(5));
                            document.add(new Paragraph(generalStory)
                                    .setFontSize(12)
                                    .setMarginLeft(20)
                                    .setMarginBottom(20)
                                    .setTextAlignment(TextAlignment.JUSTIFIED));

                            document.add(new Paragraph("Software Story")
                                    .setFontSize(16)
                                    .setBold()
                                    .setMarginBottom(5));
                            document.add(new Paragraph(softwareStory)
                                    .setFontSize(12)
                                    .setMarginLeft(20)
                                    .setTextAlignment(TextAlignment.JUSTIFIED));

                            document.close();
                            return baos.toByteArray();
                        } catch (Exception e) {
                            throw new VocabAutomationException("Failed to generate PDF", e);
                        }
                    }

                    // Add these methods to PdfService
                    public String extractText(byte[] pdfBytes) {
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
                            throw new VocabAutomationException("Failed to extract text from PDF", e);
                        }
                    }

                    public String extractSection(String pdfText, String sectionTitle) {
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