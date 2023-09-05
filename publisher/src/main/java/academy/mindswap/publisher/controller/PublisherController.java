package academy.mindswap.publisher.controller;

import academy.mindswap.publisher.service.SQSEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherController {
    private SQSEventPublisher sqsEventPublisher;

    @PostMapping("/sendMessage")
    public ResponseEntity<Object> sendMessage(@RequestBody JsonNode jsonNode) {
        sqsEventPublisher.publishEvent(jsonNode);
        return ResponseEntity.ok().build();
    }
}
