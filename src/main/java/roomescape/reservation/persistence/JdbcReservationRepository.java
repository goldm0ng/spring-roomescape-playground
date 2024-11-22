package roomescape.reservation.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2,reservation.getDate());
            ps.setString(3,reservation.getTime());
            return ps;
        }, keyHolder);

        Long generatedAutoId = keyHolder.getKey().longValue();
        return new Reservation(generatedAutoId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        String sql = "select id, name, date, time from reservation where id = ?";

        try{
            Reservation reservation = jdbcTemplate.queryForObject(sql, reservationMapper(), reservationId);
            return Optional.of(reservation);
        } catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";

        return jdbcTemplate.query(sql, reservationMapper());
    }

    @Override
    public Optional<Reservation> delete(Long reservationId) {
        Optional<Reservation> reservation = this.findById(reservationId);
        if (reservation.isEmpty()){
            return Optional.empty();
        }

        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, reservationId);

        return reservation;
    }

    private RowMapper<Reservation> reservationMapper() {
        return ((rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
        });
    }
}
