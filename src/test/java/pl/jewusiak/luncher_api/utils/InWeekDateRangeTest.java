package pl.jewusiak.luncher_api.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InWeekDateRangeTest {
    @Test
    void normalRange() {
        var range = new InWeekDateRange(new InWeekDate(4, 10, 30), new InWeekDate(4,18,0));

        assert range.isDateWithinRange(new InWeekDate(4,12,0));
        assert range.isDateWithinRange(new InWeekDate(4,10,30));
        assert !range.isDateWithinRange(new InWeekDate(4,18,0));
        assert !range.isDateWithinRange(new InWeekDate(0));
        assert !range.isDateWithinRange(new InWeekDate(5,10,20));
    }

    @Test
    void invertedRange() {
        var range = new InWeekDateRange(new InWeekDate(6, 19, 0), new InWeekDate(0,4,0));

        assert range.isDateWithinRange(new InWeekDate(0,1,0));
        assert range.isDateWithinRange(new InWeekDate(6,19,30));
        assert !range.isDateWithinRange(new InWeekDate(0,4,0));
        assert range.isDateWithinRange(new InWeekDate(0));
        assert !range.isDateWithinRange(new InWeekDate(0,10,0));
        assert !range.isDateWithinRange(new InWeekDate(5,10,0));
    }
}