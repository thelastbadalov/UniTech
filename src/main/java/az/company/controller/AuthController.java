package az.company.controller;

import az.company.dto.request.SignInRequest;
import az.company.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
private final AuthenticationService service;



@PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest request){
    return ResponseEntity.ok(service.signInAndReturnJwt(request));
}

}
