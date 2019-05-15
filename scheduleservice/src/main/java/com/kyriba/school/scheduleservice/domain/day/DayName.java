package com.kyriba.school.scheduleservice.domain.day;

import java.util.stream.Stream;

public enum DayName {
    MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6);

    private final int number;

    DayName(int number) {
        this.number = number;
    }

    public static DayName fromNumber(int dayNumber) {
        return Stream.of(values())
                .filter(day -> day.number == dayNumber)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("There is do day with number %s. Values from 1 to 6 are valid", dayNumber)));
    }

    public int getNumber() {
        return number;
    }
}
