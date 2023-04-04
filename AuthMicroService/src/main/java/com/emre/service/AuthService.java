package com.emre.service;

import com.emre.dto.request.DoLoginRequestDto;
import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.exception.AuthServiceException;
import com.emre.exception.EErrorType;
import com.emre.manager.IUserProfileManager;
import com.emre.mapper.IAuthMapper;
import com.emre.rabbitmq.model.SaveAuthModel;
import com.emre.rabbitmq.producer.CreateUserProducer;
import com.emre.repository.IAuthRepository;
import com.emre.repository.entity.Auth;
import com.emre.utility.JwtTokenManager;
import com.emre.utility.ServiceManager;
import com.emre.utility.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository repository;
    private final JwtTokenManager tokenManager;
    private final IUserProfileManager iUserProfileManager;
    private final CreateUserProducer createUserProducer;

    public AuthService(IAuthRepository repository,
                       JwtTokenManager tokenManager,
                       IUserProfileManager iUserProfileManager,
                       CreateUserProducer createUserProducer) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.iUserProfileManager = iUserProfileManager;
        this.createUserProducer = createUserProducer;
    }

    public Optional<Auth> findOptionalByUsernameAndPassword(String username, String password) {
        return repository.findOptionalByUsernameAndPassword(username, password);
    }

    public Auth register(RegisterRequestDto dto) {
        if (repository.isUsername(dto.getUsername()))
            throw new AuthServiceException(EErrorType.REGISTER_ERROR_USERNAME);
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        /**
         * Repo -> repository.save(auth) -> bu bana kayıt ettiğim entity'i döner
         * Service -> save(auth) -> buda bana kayıt ettiği entity'i döner
         * direkt -> auth, bir şekilde kaıyt edilen entitynin bilgileri işlenir ve bunu döner.
         * bunların hepsi aynı şekilde çalışır.
         * Bir fark var o'da eğer bizim Servicemangerda özel bir kodumuz olsaydı şayet
         * özel loglama olur ,ekstra parametre eklemek gibi o zaman mecburen save(auth) kullanmamız
         * gerekecekti.
         * save(auth) -> kullanmalıyım çünkü createdate,updatedate kısmı kesinlikle kayıt ediliyor.
         */
        save(auth);
        createUserProducer.convertAndSend(SaveAuthModel.builder()
                        .authid(auth.getId())
                        .email(auth.getEmail())
                        .username(auth.getUsername())
                .build());
        //iUserProfileManager.save(IAuthMapper.INSTANCE.fromAuth(auth));
        return auth;

    }

    /**
     * Kullanıcıyı doğrulayacak ve kullanıcın sistem içinde hareket edebilmesi için ona özel bir kimlik verecek.
     * TOKEN -> JWT token
     *
     * @param dto
     * @return
     */
    public String doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty())
            throw new AuthServiceException(EErrorType.LOGIN_ERROR_USERNAME_PASSWORD);
        return tokenManager.createToken(auth.get().getId()).get();
    }

    //TOKEN CONTROL!
    public List<Auth> findAll(String token) {
        Optional<Long> id = tokenManager.getByIdFromToken(token);
        if (id.isEmpty())
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);

        if (findById(id.get()).isEmpty())
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        return findAll();
    }


}
