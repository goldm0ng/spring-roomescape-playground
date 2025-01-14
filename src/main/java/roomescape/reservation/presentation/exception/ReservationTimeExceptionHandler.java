package roomescape.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.presentation.ReservationTimeController;

@Slf4j
@ControllerAdvice(assignableTypes = ReservationTimeController.class)
public class ReservationTimeExceptionHandler {

    @ExceptionHandler(NotFoundReservationTimeException.class)
    public ResponseEntity<String> handleNotFoundTimeException(NotFoundReservationTimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
