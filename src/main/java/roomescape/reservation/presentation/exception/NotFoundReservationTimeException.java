package roomescape.exception;

public class NotFoundReservationTimeException extends RuntimeException {

    private static final String NOT_FOUND_TIME_MESSAGE = "시간을 찾을 수 없습니다.";

    public NotFoundReservationTimeException(){
        super(NOT_FOUND_TIME_MESSAGE);
    }
}
