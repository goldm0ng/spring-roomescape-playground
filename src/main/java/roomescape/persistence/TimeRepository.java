package roomescape.persistence;

import roomescape.domain.Time;

import java.util.List;

public interface TimeRepository {

    Time save(Time time);

    Time findById(Long timeId);

    List<Time> findAll();

    void delete(Long timeId);
}
