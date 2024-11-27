package roomescape.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long generatedAutoId = keyHolder.getKey().longValue();
        return new Reservation(generatedAutoId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public Reservation findById(Long reservationId) {
        String sql = "select id, name, date, time_id from reservation where id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, reservationMapperForFindById(), reservationId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "from reservation as r inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(sql, reservationMapperForFindAll());
    }

    @Override
    public void delete(Long reservationId) {
        Reservation deletedReservation = this.findById(reservationId);

        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, deletedReservation.getId());
    }

    private RowMapper<Reservation> reservationMapperForFindById() {
        return ((rs, rowNum) -> {

            Time time = new Time(rs.getLong("time_id"), null);

            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    time);
        });
    }

    private RowMapper<Reservation> reservationMapperForFindAll() {
        return ((rs, rowNum) -> {

            Time time = new Time(rs.getLong("time_id"), rs.getString("time_value"));

            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    time
            );
        });
    }
}
