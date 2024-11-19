package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    public Reservation addReservation(ReservationDto reservationDto){
        Reservation reservation = convertToEntity(reservationDto);
        return repository.save(reservation);
    }

    public List<Reservation> checkReservations(){
        return repository.findAll();
    }

    public Optional<Reservation> deleteReservation(Long reservationId){
        return repository.delete(reservationId);
    }

    private Reservation convertToEntity(ReservationDto dto) {
        return new Reservation(null, dto.getName(), dto.getDate(), dto.getTime());
    }
}
