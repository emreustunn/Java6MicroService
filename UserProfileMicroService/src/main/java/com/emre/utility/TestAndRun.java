package com.emre.utility;

import com.emre.manager.IElasticServiceManager;
import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestAndRun {

    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;

//    @PostConstruct
    public void init(){
        /**
         * bu kısım kullanılacak ise, zorunlu durumlar için işiniz bitince bu kodu
         * yorum satırına almak doğru olacaktır.
         * Çalışma sistemi etkilemeyen durumlarda thread içinde çalıştırmak doğru olacaktır.
         */
//        new Thread(()->{
//            run();
//        });
//        run();
    }

    public void run (){
        List<UserProfile> list = userProfileService.findAll();
        list.forEach(x->{
            elasticServiceManager.addUser(x);
        });
    }
}
