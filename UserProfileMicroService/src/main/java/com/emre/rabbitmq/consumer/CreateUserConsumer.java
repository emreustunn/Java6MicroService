package com.emre.rabbitmq.consumer;

import com.emre.rabbitmq.model.SaveAuthModel;
import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import lombok.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Table;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {

    private final UserProfileService userProfileService;
    //kuyruk servisinde bir şey var mı diye sürekli dinleyen method.
    @RabbitListener(queues = "queue-auth")
    public void createUserFromHandleQueue(SaveAuthModel model) {
        System.out.println("Gelen Data..: "+model.getUsername());
        userProfileService.saveRabbit(model);
    }

}
