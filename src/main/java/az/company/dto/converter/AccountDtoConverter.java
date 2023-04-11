package az.company.dto.converter;

import az.company.dto.AccountDto;
import az.company.model.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountDtoConverter {
private final ModelMapper mapper;

public AccountDto convert(Account account){

    return mapper.map(account, AccountDto.class);
}
    public List<AccountDto> convert(List<Account> account){
        return account.stream().map(this::convert).collect(Collectors.toList());
    }

}
