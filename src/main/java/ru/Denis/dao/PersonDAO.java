package ru.Denis.dao;

import ru.Denis.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO people(fullname, yearOfBorn) VALUES(?,?)",
                person.getFullName(), person.getYearOfBorn());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM people WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findFirst().orElse(null);
    }

    public void update(Person person, int id) {
        jdbcTemplate.update("UPDATE people SET fullname = ?, yearofborn = ? WHERE person_id = ?",
                person.getFullName(), person.getYearOfBorn(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM people WHERE person_id = ?", id);
    }

}
