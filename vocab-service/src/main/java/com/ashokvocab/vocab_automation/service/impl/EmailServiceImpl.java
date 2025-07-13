package com.ashokvocab.vocab_automation.service.impl;

                import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
                import com.ashokvocab.vocab_automation.service.EmailService;
                import org.springframework.beans.factory.annotation.Autowired;
                import org.springframework.beans.factory.annotation.Value;
                import org.springframework.core.io.UrlResource;
                import org.springframework.mail.javamail.JavaMailSender;
                import org.springframework.mail.javamail.MimeMessageHelper;
                import org.springframework.stereotype.Service;

                import jakarta.mail.MessagingException;
                import jakarta.mail.internet.MimeMessage;
                import java.net.MalformedURLException;
                import java.time.LocalDate;
                import java.util.List;
                import com.ashokvocab.vocab_automation.dto.VocabularyDTO;

                @Service
                public class EmailServiceImpl implements EmailService {

                    private final JavaMailSender mailSender;

                    @Value("${spring.mail.username}")
                    private String fromEmail;

                    @Value("${vocab.email.recipients}")
                    private String[] recipients;

                    @Autowired
                    public EmailServiceImpl(JavaMailSender mailSender) {
                        this.mailSender = mailSender;
                    }

                    @Override
                    public void sendVocabularyEmail(List<VocabularyDTO> content, String pdfUrl) {
                        try {
                            MimeMessage message = mailSender.createMimeMessage();
                            MimeMessageHelper helper = new MimeMessageHelper(message, true);

                            helper.setFrom(fromEmail);
                            helper.setTo(recipients);
                            helper.setSubject("📘 Daily Vocabulary Words - " + LocalDate.now());

                            // Convert plain text to HTML format
                            String htmlContent = buildHtmlContent(content);
                            helper.setText(htmlContent, true); // true indicates HTML content

                            // Add PDF as attachment
                            UrlResource pdfResource = new UrlResource(pdfUrl);
                            helper.addAttachment("vocabulary.pdf", pdfResource);

                            mailSender.send(message);
                        } catch (MessagingException | MalformedURLException e) {
                            throw new VocabAutomationException("Failed to send email: " + e.getMessage(), e);
                        }
                    }

                    private String buildHtmlContent(List<VocabularyDTO> words) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("<h2>📘 Today's Vocabulary</h2>");
                        sb.append("<ul>");
                        for (VocabularyDTO word : words) {
                            sb.append(String.format("<li><b>%s</b>: %s</li>",
                                word.getWord(), word.getMeaning()));
                        }
                        sb.append("</ul>");
                        sb.append("<p>Happy learning! 🌱</p>");
                        return sb.toString();
                    }
                }