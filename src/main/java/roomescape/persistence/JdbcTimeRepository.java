package roomescape.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.NotFoundTimeException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Time save(Time time) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long generatedAutoId = keyHolder.getKey().longValue();
        return new Time(generatedAutoId, time.getTime());
    }

    @Override
    public Time findById(Long timeId) {
        String sql = "select id, time from time where id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, timeMapper(), timeId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundTimeException();
        }
    }

    @Override
    public List<Time> findAll() {
        String sql = "select id, time from time";

        return jdbcTemplate.query(sql, timeMapper());
    }

    @Override
    public void delete(Long timeId) {
        Time deletedTime = this.findById(timeId);

        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, deletedTime.getId());
    }

    private RowMapper<Time> timeMapper() {
        return ((rs, rowNum) -> {
            return new Time(
                    rs.getLong("id"),
                    rs.getString("time")
            );
        });
    }
}
