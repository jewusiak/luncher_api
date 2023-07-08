package pl.jewusiak.luncher_api.utils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class InWeekDateRange {
    @Embedded
    private InWeekDate start;
    @Embedded
    private InWeekDate end;

    public InWeekDateRange(InWeekDate start, InWeekDate end) {
        if (start == end) throw new IllegalArgumentException("Start can't be equal to end date.");
        this.start = start;
        this.end = end;
    }

    public boolean isDateWithinRange(InWeekDate date) {
        if (start.getRawTime() < end.getRawTime()) {
            //normal
            return date.getRawTime() >= start.getRawTime() && date.getRawTime() < end.getRawTime();
        } else {
            //inverted
            return date.getRawTime() >= start.getRawTime() || date.getRawTime() < end.getRawTime();
        }
    }
}
