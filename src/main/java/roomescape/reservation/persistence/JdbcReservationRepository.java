package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Reservation save(ReservationDto reservationDto) {
        String sql = "insert into reservation (name, date, time) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationDto.getName());
            ps.setString(2,reservationDto.getDate());
            ps.setString(3,reservationDto.getTime());
            return ps;
        }, keyHolder);

        Long generatedAutoId = keyHolder.getKey().longValue();
        return new Reservation(generatedAutoId, reservationDto.getName(), reservationDto.getDate(), reservationDto.getTime());
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
