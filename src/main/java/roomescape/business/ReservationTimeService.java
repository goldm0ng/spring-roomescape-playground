package roomescape.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.persistence.JdbcReservationTimeRepository;
import roomescape.presentation.dto.ReservationTimeDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final JdbcReservationTimeRepository repository;

    public ReservationTime addTime(ReservationTimeDto reservationTimeDto) {
        ReservationTime time = convertToEntity(reservationTimeDto);
        return repository.save(time);
    }

    public List<ReservationTime> checkTimes() {
        return repository.findAll();
    }

    public void deleteTime(Long timeId) {
        repository.delete(timeId);
    }

    private ReservationTime convertToEntity(ReservationTimeDto reservationTimeDto) {
        return new ReservationTime(null, reservationTimeDto.time());
    }

}
