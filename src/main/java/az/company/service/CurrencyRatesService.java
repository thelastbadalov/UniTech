package az.company.service;

import az.company.dto.CurrencyExchangeDto;
import az.company.dto.converter.CurrencyExchangeDtoConverter;
import az.company.dto.request.Currency;
import az.company.model.CurrencyExchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyRatesService {
    private final RestTemplate restTemplate;
    private final CurrencyExchangeDtoConverter converter;
@Value("${restTemplate.header.apikey}")
private String apiKey;

    public CurrencyRatesService(RestTemplate restTemplate, CurrencyExchangeDtoConverter converter) {
        this.restTemplate = restTemplate;
        this.converter = converter;
    }

    public CurrencyExchangeDto getExchange(az.company.dto.request.Currency symbol, Currency base){
    ModelMapper mapper=new ModelMapper();
    final HttpHeaders headers=new HttpHeaders();
    headers.set("apikey",apiKey);
final HttpEntity<String> entity=new HttpEntity<>(headers);

 ResponseEntity<Map> response=restTemplate.exchange(
        "https://api.apilayer.com/exchangerates_data/latest?symbols=" +symbol.name() + "&base=" + base.name(),
        HttpMethod.GET,entity,Map.class);

CurrencyExchange currencyExchange=mapper.map(response.getBody(),CurrencyExchange.class);

return converter.convert(currencyExchange);

}


}
