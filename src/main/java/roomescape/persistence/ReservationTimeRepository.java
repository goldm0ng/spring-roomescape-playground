package roomescape.persistence;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime time);

    ReservationTime findById(Long timeId);

    List<ReservationTime> findAll();

    void delete(Long timeId);
}
