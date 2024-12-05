package roomescape.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.business.ReservationTimeService;
import roomescape.domain.ReservationTime;
import roomescape.presentation.dto.ReservationTimeDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readTimes() {
        return ResponseEntity.ok(reservationTimeService.checkTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(@Valid @RequestBody ReservationTimeDto reservationTimeDto) {
        ReservationTime savedTime = reservationTimeService.addTime(reservationTimeDto);
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long timeId) {
        reservationTimeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
