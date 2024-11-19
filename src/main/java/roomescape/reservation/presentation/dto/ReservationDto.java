package roomescape.reservation.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {

    @NotBlank(message = "예약자 이름을 입력하세요.")
    private String name;

    @NotBlank(message = "예약 날짜를 입력하세요.")
    private String date;

    @NotBlank(message = "예약 시간을 입력하세요.")
    private String time;

    public ReservationDto() {
    }

    public ReservationDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
