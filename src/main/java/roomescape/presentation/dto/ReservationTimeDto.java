package roomescape.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationTimeDto(
        @NotBlank(message = "에약 시간을 입력하세요.") String time
) {
}
