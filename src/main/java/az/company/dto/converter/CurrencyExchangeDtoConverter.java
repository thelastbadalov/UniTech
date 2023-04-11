package az.company.dto.converter;

import az.company.dto.CurrencyExchangeDto;
import az.company.dto.request.Currency;
import az.company.model.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CurrencyExchangeDtoConverter {
private final ModelMapper mapper;


public CurrencyExchangeDto convert(CurrencyExchange currency){
    return mapper.map(currency, CurrencyExchangeDto.class);

}
}
