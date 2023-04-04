package com.emre.rabbitmq.producer;

import com.emre.rabbitmq.model.SaveAuthModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {
    /**
     * Belli bir bilginin rabbitmq üzerinden iletilmesi işlemi
     */
    private final RabbitTemplate rabbitTemplate;

    public void convertAndSend(SaveAuthModel model){
        rabbitTemplate.convertAndSend("direct-exchange-auth","save-auth-key",model);
    }
}
