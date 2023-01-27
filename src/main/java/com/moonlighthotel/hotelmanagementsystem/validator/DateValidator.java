package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;

@Component
public class DateValidator {

    private static final String END_DATE = "end_date";
    private static final String START_DATE = "start_date";

    public void validateDates(Instant startDate, Instant endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidRequestException("The end date cannot be before the start date.", END_DATE);
        } else if (startDate.isBefore(Instant.now()) ||
                (endDate.isBefore(Instant.now()))) {
            throw new InvalidRequestException(
                    String.format("The start date and the end date cannot be before '%s'.", LocalDate.now()),
                    START_DATE + " " + END_DATE);
        }
        if (startDate.equals(endDate)) {
            throw new InvalidRequestException("The end date cannot match the start date.", END_DATE);
        }
    }
}
