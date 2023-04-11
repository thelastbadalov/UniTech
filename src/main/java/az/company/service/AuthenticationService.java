package az.company.service;

import az.company.dto.request.SignInRequest;
import az.company.security.UserPrincipal;
import az.company.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final AuthenticationManager manager;

    public AuthenticationService(JwtProvider jwtProvider, UserService userService, AuthenticationManager manager) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.manager = manager;
    }


    public String signInAndReturnJwt(SignInRequest request) {
        userService.chekPinIsValidOrNot(request.getPin());
        userService.checkPasswordValidOrNot(request.getPassword());
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getPin(), request.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return jwtProvider.generateToken(userPrincipal);
    }

}
