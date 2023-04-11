package az.company.service;

import az.company.dto.CityDto;
import az.company.dto.UserDto;
import az.company.dto.converter.UserToUserDtoConverter;
import az.company.dto.request.CreateUserRequest;
import az.company.exception.PasswordDoesNotValidException;
import az.company.exception.PinDoesNotValidException;
import az.company.exception.UserAlreadyExistException;
import az.company.exception.UserNotFoundException;
import az.company.model.City;
import az.company.model.User;
import az.company.repository.UserRepository;
import az.company.validation.PasswordValidator;
import az.company.validation.PinValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserToUserDtoConverter converter;

    public UserService(@Lazy  PasswordEncoder passwordEncoder, @Lazy UserRepository repository, @Lazy UserToUserDtoConverter converter) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.converter = converter;
    }

    public UserDto createUser(CreateUserRequest request, CityDto cityDto) {
        chekPinIsValidOrNot(request.getPin());
        checkUserAlreadyExistOrNot(request.getPin());
        checkPasswordValidOrNot(request.getPassword());
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate(),
                City.valueOf(cityDto.name()),
                request.getAddress(),
                request.getPin(),
                passwordEncoder.encode(request.getPassword()),
                LocalDateTime.now());


        return converter.convertToDto(repository.save(user));

    }

    public List<UserDto> getAllUsers() {
        return converter.convert(repository.findAll());
    }

    public User findUserByPin(String pin) {
        return repository.findByPin(pin).orElseThrow(
                () -> new UserNotFoundException(" can not find User with given pin : " + pin));
    }

    protected User findUserById(String userId){
        return repository.findById(userId).orElseThrow(()-> new UserNotFoundException
                ("can not find User with given id : " + userId));}

    protected void checkPasswordValidOrNot(String password) {
        if (!PasswordValidator.isValid(password)) {
            throw new PasswordDoesNotValidException("Password must contain at least one digit [0-9]," +
                    "one lowercase-[a-z] and uppercase Latin character-[A-Z]," +
                    "one special character like ! @ # & ( ) " +
                    "and length of at least 8 characters and a maximum of 20 characters.");
        }
    }
    protected void checkUserAlreadyExistOrNot(String pin) {
        Optional<User> user = repository.findByPin(pin);
        user.ifPresent(user1 -> {
            throw new UserAlreadyExistException("User Already Exist");
        });
    }

    protected void chekPinIsValidOrNot(String pin) {
        if (!PinValidator.isValid(pin)) {
            throw new PinDoesNotValidException("Pin must be 7 characters length");
        }
    }
}
