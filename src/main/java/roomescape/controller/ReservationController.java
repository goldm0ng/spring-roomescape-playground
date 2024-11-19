package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservation(){
        return ResponseEntity.ok(reservationService.checkReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        Reservation savedReservation = reservationService.addReservation(reservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId())).body(savedReservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId){
        Optional<Reservation> deleteReservation = reservationService.deleteReservation(reservationId);
        if (deleteReservation.isEmpty()){
            throw new NotFoundReservationException();
        }

        return ResponseEntity.noContent().build();
    }
}
