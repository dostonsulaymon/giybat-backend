package dasturlash.uz.controller;

import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto){
        return ResponseEntity.ok().body(authService.registration(dto));
    }
}
