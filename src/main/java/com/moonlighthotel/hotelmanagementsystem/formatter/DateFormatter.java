package com.moonlighthotel.hotelmanagementsystem.formatter;

import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;
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

    public String formatPeriod(RoomReservation roomReservation) {
        String startDate = createFormat().format(roomReservation.getStartDate());
        String endDate = createFormat().format(roomReservation.getEndDate());
        return String.format("%s - %s", startDate, endDate);
    }

    private DateTimeFormatter createFormat() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"));
    }
}
