
        package com.ashokvocab.kafka.consumer;
        import com.ashokvocab.vocab_automation.dto.VocabBatchReadyDTO;
        import jakarta.activation.DataSource;
        import jakarta.mail.util.ByteArrayDataSource;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.mail.javamail.JavaMailSender;
        import org.springframework.mail.javamail.MimeMessageHelper;
        import org.springframework.kafka.annotation.KafkaListener;
        import org.springframework.stereotype.Component;
        import org.springframework.web.client.RestTemplate;

        import jakarta.mail.internet.MimeMessage;
        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

        @Component
        public class VocabBatchConsumer {

            @Autowired
            private JavaMailSender mailSender;

            @KafkaListener(topics = "vocab.batch.ready", groupId = "notification-service-group")
            public void consume(VocabBatchReadyDTO message) {
                System.out.println("Received message from Kafka topic: " + message.toString());

                if (message.getEmail() != null && message.getPdfUrl() != null) {
                    try {
                        // Download PDF from S3 URL
                        RestTemplate restTemplate = new RestTemplate();
                        byte[] pdfBytes = restTemplate.getForObject(message.getPdfUrl(), byte[].class);

                        MimeMessage mimeMessage = mailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                        helper.setTo(message.getEmail());
                        helper.setSubject("Your Vocab PDF is Ready");
                        helper.setText("Please find your PDF attached.");

                        // Attach the PDF
                        DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
                        helper.addAttachment("vocab.pdf", dataSource);

                        mailSender.send(mimeMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }