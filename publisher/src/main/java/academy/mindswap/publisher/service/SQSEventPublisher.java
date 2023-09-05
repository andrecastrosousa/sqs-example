package academy.mindswap.publisher.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SQSEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQSEventPublisher.class);

    private AmazonSQS amazonSQS;

    private ObjectMapper objectMapper;

    public void publishEvent(JsonNode message) {
        LOGGER.info("Generating event : {}", message);
        SendMessageRequest sendMessageRequest = null;

        try {
            sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl("http://localhost:4566/000000000000/sample-queue.fifo")
                    .withMessageBody(objectMapper.writeValueAsString(message))
                    .withMessageGroupId(UUID.randomUUID().toString());
            amazonSQS.sendMessage(sendMessageRequest);
            LOGGER.info("Event has been published in SQS.");
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException e: {} and stacktrace : {}", e.getMessage(), e, e);
        } catch (Exception e) {
            LOGGER.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e ,e);
        }
    }
}
