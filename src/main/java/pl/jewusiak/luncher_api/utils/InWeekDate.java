package pl.jewusiak.luncher_api.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class InWeekDate {
    @JsonIgnore
    @Getter
    private int rawTime; // 0 - 00:00 Monday, 10079 - 23:59 Sunday
    @Transient
    private int dayOfWeek;

    @Transient
    private int hours;

    @Transient
    private int minutes;

    public InWeekDate(int dayOfWeek, int hours, int minutes) {
        if (dayOfWeek < 0 || dayOfWeek > 6 || hours > 23 || hours < 0 || minutes < 0 || minutes > 59)
            throw new IllegalArgumentException("DOW = [0..6], hours = [0..23], minutes = [0..59]");
        rawTime = dayOfWeek * 1440 + hours * 60 + minutes;
    }

    public InWeekDate(int time) {
        setRawTime(time);
    }

    public int getDayOfWeek() {
        return getRawTime() / 1440;
    }

    public void setDayOfWeek(int dayOfWeek) {
        if (dayOfWeek < 0 || dayOfWeek > 6)
            throw new IllegalArgumentException("Day of week has to be between 0 - Monday and 6 - Sunday.");
        rawTime = getMinutes() + getHours() * 60 + dayOfWeek * 24 * 60;
    }

    public int getHours() {
        return getRawTime() % 1440 / 60;
    }

    public void setHours(int hours) {
        if (hours < 0 || hours > 23)
            throw new IllegalArgumentException("Hour has to be between 0 and 23.");
        rawTime = getMinutes() + hours * 60 + getDayOfWeek() * 24 * 60;
    }

    public int getMinutes() {
        return getRawTime() % 1440 % 60;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes > 59)
            throw new IllegalArgumentException("Minute has to be between 0 and 59.");
        rawTime = minutes + getHours() * 60 + getDayOfWeek() * 24 * 60;
    }

    public void setRawTime(int time) {
        if (time < 0 || time > 1440 * 7 - 1)
            throw new IllegalArgumentException("Raw time between 0 (00:00 Monday) and 10079 (23:59 Sunday = 1440*6 + 23*60 + 59)");
        this.rawTime = time;
    }
}
