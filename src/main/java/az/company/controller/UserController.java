package az.company.controller;

import az.company.dto.CityDto;
import az.company.dto.UserDto;
import az.company.dto.request.CreateUserRequest;
import az.company.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request,
                                          @RequestParam CityDto city){
    return new ResponseEntity<>(userService.createUser(request,city), HttpStatus.CREATED);
}

@GetMapping("/findAllUsers")
@Operation(summary = "Find All Users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserDto>> getAllUsers(){
    return ResponseEntity.ok(userService.getAllUsers());
}
}
