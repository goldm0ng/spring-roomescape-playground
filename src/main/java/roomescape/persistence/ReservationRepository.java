package roomescape.persistence;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation findById(Long reservationId);

    List<Reservation> findAll();

    void delete(Long reservationId);

}
