package com.moonlighthotel.hotelmanagementsystem.formatter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {

    public Instant stringToInstant(String date) {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
    }

    public String instantToString(Instant date) {
        return DateTimeFormatter.ISO_INSTANT.format(date);
    }
}
