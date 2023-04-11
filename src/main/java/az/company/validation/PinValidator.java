package az.company.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinValidator {
    private static  final String PIN_PATTERN="^.{7}$";



    private static final Pattern pattern=Pattern.compile(PIN_PATTERN);


    public static boolean isValid(final String pin){
        Matcher matcher=pattern.matcher(pin);
        return matcher.matches();
    }
}
