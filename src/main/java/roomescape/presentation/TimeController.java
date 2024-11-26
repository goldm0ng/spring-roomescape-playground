package roomescape.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.business.TimeService;
import roomescape.domain.Time;
import roomescape.presentation.dto.TimeDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/times")
    public ResponseEntity<List<Time>> readTimes() {
        return ResponseEntity.ok(timeService.checkTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@Valid @RequestBody TimeDto timeDto) {
        Time savedTime = timeService.addTime(timeDto);
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long timeId) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
