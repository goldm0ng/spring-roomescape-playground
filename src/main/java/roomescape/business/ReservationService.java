package roomescape.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.persistence.JdbcReservationRepository;
import roomescape.persistence.JdbcReservationTimeRepository;
import roomescape.presentation.dto.ReservationDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final JdbcReservationRepository reservationRepository;
    private final JdbcReservationTimeRepository timeRepository;

    public Reservation addReservation(ReservationDto reservationDto) {
        Reservation reservation = convertToReservationEntity(reservationDto);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> checkReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.delete(reservationId);
    }

    private Reservation convertToReservationEntity(ReservationDto reservationDto) {
        ReservationTime time = convertToTimeEntity(reservationDto);
        return new Reservation(null, reservationDto.name(), reservationDto.date(), time);
    }

    private ReservationTime convertToTimeEntity(ReservationDto reservationDto) {
        Long timeId = reservationDto.time();
        return timeRepository.findById(timeId);
    }
}
