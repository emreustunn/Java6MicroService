package com.emre.controller;

import com.emre.dto.request.DoLoginRequestDto;
import com.emre.dto.request.RegisterRequestDto;
import com.emre.exception.AuthServiceException;
import com.emre.exception.EErrorType;
import com.emre.repository.entity.Auth;
import com.emre.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.emre.constans.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<Auth> register(@RequestBody @Valid RegisterRequestDto dto) {
        if(!dto.getPassword().equals(dto.getRepassword()))
            throw new AuthServiceException(EErrorType.REGISTER_ERROR_PASSWORD_UNMATCH);
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(DOLOGIN)
    public ResponseEntity<String> doLogin(@RequestBody DoLoginRequestDto dto) {
        return ResponseEntity.ok(authService.doLogin(dto));
    }
    @GetMapping(GETALL)
    public ResponseEntity<List<Auth>> findAll(String token){
        return ResponseEntity.ok(authService.findAll(token));
    }

    @GetMapping("/message")
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok("Genel bir mesaj !!!");
    }
}
