package roomescape.domain;

import lombok.Getter;

@Getter
public class ReservationTime {

    private Long id;
    private String time;

    public ReservationTime() {
    }

    public ReservationTime(Long id, String time) {
        this.id = id;
        this.time = time;
    }
}
