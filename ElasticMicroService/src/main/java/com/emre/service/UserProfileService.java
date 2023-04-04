package com.emre.service;

import com.emre.dto.request.AddUserRequestDto;
import com.emre.dto.request.BaseRequestDto;
import com.emre.mapper.IUserProfileMapper;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository userProfileRepository;


    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }


    public Optional<UserProfile> findByAuthId(Long authid) {
        return userProfileRepository.findOptionalByAuthid(authid);
    }

    public void saveDto(AddUserRequestDto dto) {
        /**
         * Eğer userid daha önceden kayıt edilmiş ise kayıt işlemi yapma.
         */
        if (!userProfileRepository.existsByUserprofileid(dto.getId()))
            save(IUserProfileMapper.INSTANCE.toProfile(dto));

    }

    /**
     * Sayfalama işlemlerinde belli bilgiler olmadan işlem yapmak mümkün olmayacaktır.
     * -> liste gelmeli
     * -> total kayıt miktarı
     * -> hangi sayfadayım
     * -> kaç sayfa var
     * -> sonraki sayfa var mı
     * -> son sayfada mıyım
     */
    public Page<UserProfile> findAll(BaseRequestDto dto) {
        /**
         * Sıralama ve sayfalama için bize nesneler ve ayarlamalar gerekli.
         */
        Pageable pageable = null;
        Sort sort = null;
        /**
         * Eğer kişi sıralama istediği alanı yazmamış ise sıralama yapmak istemiyordur.
         */
        if (dto.getSortParameter() != null) {
            String direction = dto.getDirection() == null ? "ASC" : dto.getDirection();
            sort = Sort.by(Sort.Direction.fromString(direction), dto.getSortParameter());
        }
        /**
         * 1. durum -> sıralama yapmak ister ve sayfalama yapmak ister.
         * 2. durum -> sıralama yapmak istemez ve sayfalama yapmak istiyorum.
         * 3. durum -> sıralama istemiyor ve sayfalama isteğinde bulunmuyor.
         */
        Integer pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize() < 1 ? 10 : dto.getPageSize();
        if (sort != null && dto.getCurrentPage() != null) {
            pageable = PageRequest.of(dto.getCurrentPage(), pageSize, sort);
        } else if (sort == null && dto.getCurrentPage() != null) {
            pageable = PageRequest.of(dto.getCurrentPage(), pageSize);
        } else {
            pageable = PageRequest.of(0, pageSize);
        }
        return userProfileRepository.findAll(pageable);
    }

}
