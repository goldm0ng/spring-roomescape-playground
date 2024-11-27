package roomescape.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationDto(
        @NotBlank(message = "예약자 이름을 입력하세요.") String name,
        @NotBlank(message = "예약 날짜를 입력하세요.") String date,
        Long time
) {
}
