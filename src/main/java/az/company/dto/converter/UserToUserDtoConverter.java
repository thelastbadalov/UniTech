package az.company.dto.converter;
import az.company.dto.UserDto;
import az.company.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToUserDtoConverter {
private final ModelMapper modelMapper;
public UserDto convertToDto(User user){
return modelMapper.map(user, UserDto.class);
}

public List<UserDto> convert(List<User> users){
    return users.stream().map(this::convertToDto).collect(Collectors.toList());
}
}
