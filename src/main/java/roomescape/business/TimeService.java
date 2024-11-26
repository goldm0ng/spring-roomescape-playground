package roomescape.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.persistence.JdbcTimeRepository;
import roomescape.presentation.dto.TimeDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final JdbcTimeRepository repository;

    public Time addTime(TimeDto timeDto){
        Time time = convertToEntity(timeDto);
        return repository.save(time);
    }

    public List<Time> checkTimes(){
        return repository.findAll();
    }

    public void deleteTime(Long timeId){
        repository.delete(timeId);
    }

    private Time convertToEntity(TimeDto timeDto) {
        return new Time(null, timeDto.time());
    }

}
