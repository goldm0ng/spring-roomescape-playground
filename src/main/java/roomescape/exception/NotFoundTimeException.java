package roomescape.exception;

public class NotFoundTimeException extends RuntimeException {

    private static final String NOT_FOUND_TIME_MESSAGE = "시간을 찾을 수 없습니다.";

    public NotFoundTimeException(){
        super(NOT_FOUND_TIME_MESSAGE);
    }
}
