package roomescape.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.exception.NotFoundReservationTimeException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ReservationTime save(ReservationTime time) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long generatedAutoId = keyHolder.getKey().longValue();
        return new ReservationTime(generatedAutoId, time.getTime());
    }

    @Override
    public ReservationTime findById(Long timeId) {
        String sql = "select id, time from time where id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, timeMapper(), timeId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationTimeException();
        }
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select id, time from time";

        return jdbcTemplate.query(sql, timeMapper());
    }

    @Override
    public void delete(Long timeId) {
        ReservationTime deletedTime = this.findById(timeId);

        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, deletedTime.getId());
    }

    private RowMapper<ReservationTime> timeMapper() {
        return ((rs, rowNum) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    rs.getString("time")
            );
        });
    }
}
