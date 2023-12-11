package cl.ucn.codecrafters.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static LocalDateTime convertToLocalDate(String dateString) {
        // Intenta analizar la fecha utilizando diferentes patrones
        String[] patterns = {
                "yyyy/MM/dd HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss",
                "dd/MM/yyyy HH:mm:ss",
                "MM/dd/yyyy HH:mm:ss",
                "yyyy/MM/dd",
                "yyyy-MM-dd",
                "dd/MM/yyyy",
                "MM/dd/yyyy"
        };

        for (String pattern : patterns) {
            try {
                LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));

                // Transforma LocalDate a LocalDateTime con la hora establecida a medianoche

                return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
            } catch (DateTimeParseException e) {
                // Continúa con el próximo patrón
            }
        }

        // Si llega aquí, ninguno de los patrones funcionó
        throw new IllegalArgumentException("Formato de fecha y hora no reconocido: " + dateString);
    }



}
