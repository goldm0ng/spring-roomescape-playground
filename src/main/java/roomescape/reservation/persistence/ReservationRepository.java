package roomescape.reservation.persistence;

import roomescape.reservation.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);

    List<Reservation> findAll();

    public Optional<Reservation> delete(Long reservationId);

}
