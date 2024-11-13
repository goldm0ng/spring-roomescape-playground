package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository{

    private static Map<Long, Reservation> reservationStore = new ConcurrentHashMap<>();
    private static AtomicLong index = new AtomicLong(1);

    @Override
    public Reservation save(ReservationDto reservationDto) {

        Long reservationId = index.getAndIncrement();
        Reservation savedReservation = new Reservation(
                reservationId, reservationDto.getName(), reservationDto.getDate(), reservationDto.getTime());

        reservationStore.put(reservationId,savedReservation);

        return savedReservation;
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return Optional.ofNullable(reservationStore.get(reservationId));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservationStore.values());
    }

    @Override
    public Optional<Reservation> delete(Long reservationId) {
        Optional<Reservation> reservation = findById(reservationId);

        return Optional.ofNullable(reservationStore.remove(reservationId));
    }
}
