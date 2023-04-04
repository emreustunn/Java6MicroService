package com.emre.service;

import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.manager.IElasticServiceManager;
import com.emre.mapper.IUserProfileMapper;
import com.emre.rabbitmq.model.SaveAuthModel;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final IUserProfileRepository repository;
    private final IElasticServiceManager elasticServiceManager;

    public UserProfileService(IUserProfileRepository repository, IElasticServiceManager elasticServiceManager) {
        super(repository);
        this.repository = repository;
        this.elasticServiceManager = elasticServiceManager;
    }

    public Boolean saveDto(UserProfileSaveRequestDto dto) {
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
        return true;
    }

    public void saveRabbit(SaveAuthModel model) {
        UserProfile profile = IUserProfileMapper.INSTANCE.toUserProfile(model);
        save(profile);
//        elasticServiceManager.addUser(profile);
    }

    /**
     * Bu uzun süren bir işlemi simüle etmek için Thread Kullanılarak
     * bekletilmiş bir methottur.
     * - Bu method, Kağıt kelimesi için her seferinde aynı sonucu mu üretir.
     *
     * @param name
     * @return
     */
    @Cacheable(value = "getUpperName")
    public String getUpper(String name) {
        try {
            Thread.sleep(3000l);
        } catch (Exception e) {

        }
        return name.toUpperCase();
    }

    @CacheEvict(value = "getUpperName", allEntries = true)
    public void clearCache() {
        System.out.println("Tüm kayıtları temizledik");
    }
}
