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
                }