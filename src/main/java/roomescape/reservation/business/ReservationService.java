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

    public List<Reservation> checkReservations(){
        return repository.findAll();
    }

    public Reservation addReservation(ReservationDto reservationDto){
        return repository.save(reservationDto);
    }

    public Optional<Reservation> deleteReservation(Long reservationId){
        return repository.delete(reservationId);
    }

}
