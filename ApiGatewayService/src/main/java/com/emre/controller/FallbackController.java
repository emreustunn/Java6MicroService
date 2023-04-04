package com.emre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuth(){
        return ResponseEntity.ok("Auth servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }
    @GetMapping("/product")
    public ResponseEntity<String> fallbackProduct(){
        return ResponseEntity.ok("Auth servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }
    @GetMapping("/sale")
    public ResponseEntity<String> fallbackSale(){
        return ResponseEntity.ok("Auth servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }
    @GetMapping("/user")
    public ResponseEntity<String> fallbackUser(){
        return ResponseEntity.ok("Auth servisi şuan hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz.");
    }
}
