package roomescape.domain;

import lombok.Getter;

@Getter
public class Time {

    private Long id;
    private String time;

    public Time() {
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }
}
