package pl.jewusiak.luncher_api.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InWeekDateTest {
    @Test
    void shouldReturnThursday2030() {
        var time = new InWeekDate(3, 20, 30);

        assertEquals(3, time.getDayOfWeek());
        assertEquals(20, time.getHours());
        assertEquals(30, time.getMinutes());
    }
    @Test
    void rangeStart() {
        var time = new InWeekDate(0);

        assertEquals(0, time.getDayOfWeek());
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
    }
    @Test
    void rangeEnd() {
        var time = new InWeekDate(1440*7-1);

        assertEquals(6, time.getDayOfWeek());
        assertEquals(23, time.getHours());
        assertEquals(59, time.getMinutes());
    }
}