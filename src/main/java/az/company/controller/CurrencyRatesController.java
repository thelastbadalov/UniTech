package az.company.controller;

import az.company.dto.CurrencyExchangeDto;
import az.company.dto.request.Currency;
import az.company.service.CurrencyRatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rates")
@RequiredArgsConstructor
public class CurrencyRatesController {
private final CurrencyRatesService ratesService;



@GetMapping("/getExchange")
@Operation(summary = "Currency Exchange",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CurrencyExchangeDto> currencyRates(@RequestParam Currency symbol,
                                                             @RequestParam Currency base){
 return ResponseEntity.ok(ratesService.getExchange(symbol,base));
}
}
