package com.emre.manager;

import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.emre.constans.EndPoints.*;
@FeignClient(name = "elastic-service-manager",
url = "http://localhost:9100/elastic/user",
decode404 = true)
public interface IElasticServiceManager {
    @PostMapping(SAVE)
    ResponseEntity<Void> addUser(@RequestBody UserProfile dto);

}
