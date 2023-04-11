package az.company.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchange {
private Boolean success;
private String timeStamp;
private String base;
@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private String date;

private Map<String,Double> rates;
}
