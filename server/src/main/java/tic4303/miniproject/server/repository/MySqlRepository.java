package tic4303.miniproject.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import tic4303.miniproject.server.model.Interest;

import static tic4303.miniproject.server.repository.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class MySqlRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void submitForm(Interest interest) {
        jdbcTemplate.update(SQL_SUBMIT_FORM, interest.getName(), interest.getEmail(), interest.getPhone(), interest.getCountry(), interest.getGender(), interest.getQualification());   
    }

    public List<Interest> getInterests() {
        List<Interest> interests = new LinkedList<>();
        SqlRowSet rs = null;
        rs = jdbcTemplate.queryForRowSet(SQL_SELECT_INTERESTS);
        while (rs.next()) {
            interests.add(Interest.create(rs));
        }
        return interests;
    }
    
}
