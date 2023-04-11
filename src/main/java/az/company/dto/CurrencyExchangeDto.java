package az.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeDto {
    private String base;
    private String date;
    private Map<String,Double> rates;
}
