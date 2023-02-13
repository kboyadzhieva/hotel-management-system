package com.moonlighthotel.hotelmanagementsystem.formatter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {

    public Instant stringToInstant(String date) {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
    }

    public String instantToString(Instant date) {
        return DateTimeFormatter.ISO_INSTANT.format(date);
    }

    public String formatDate(Instant date) {
        return String.format("%s", createFormat().format(date));
    }

    public String formatPeriod(Instant startDate, Instant endDate) {
        String formattedStartDate = createFormat().format(startDate);
        String formattedEndDate = createFormat().format(endDate);
        return String.format("%s - %s", formattedStartDate, formattedEndDate);
    }

    private DateTimeFormatter createFormat() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"));
    }
}
