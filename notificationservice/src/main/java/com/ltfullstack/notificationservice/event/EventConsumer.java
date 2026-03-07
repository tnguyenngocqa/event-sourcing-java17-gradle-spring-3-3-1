package com.ltfullstack.notificationservice.event;

import com.ltfullstack.commonservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
@Service
public class EventConsumer {

//    @Autowired
//    private EmailService emailService;

    @RetryableTopic(
            attempts = "4", // 3 topic retry + 1 topic DLQ
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetriableException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Received Message: {}", message);

        // simulate error
        // throw new RuntimeException("Test Exception in the KafkaListener @RetryableTopic");
    }

    @DltHandler
    private void processDLQ(String message) {
        log.info("Dlt Handler - Received Message: {}", message);
    }

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    public void listenForTestEmail(String message) {
        log.info("Received Message[Email]: {}", message);

        String template = """
                <div>
                    <h1>Welcome, %s!</h1>
                    <p>Thank you for joining us. We're excited to have you on board.</p>
                    <p>Your username is: <strong>%s</strong></p>
                </div>
                """;
        String filledTemplate = String.format(template, "Sean Nguyen", message);

       // emailService.sendEmail(message, "Thanks for buy my course", filledTemplate, true, null);
    }
}
