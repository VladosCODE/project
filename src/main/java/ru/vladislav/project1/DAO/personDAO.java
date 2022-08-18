package ru.vladislav.project1.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladislav.project1.models.Book;
import ru.vladislav.project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class personDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public personDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person",new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id_user=?"
                ,new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public Person show(String fio){
        return jdbcTemplate.query("SELECT * FROM Person WHERE fio=?",new Object[]{fio},new BeanPropertyRowMapper
                <>(Person.class)).stream().findAny().orElse(null);
    }
    public void save(Person people){
        jdbcTemplate.update("INSERT INTO Person(fio,year_born) VALUES(?,?)",people.getFio(),people.getYear_born());
    }
    public void update(int id,Person people){
        jdbcTemplate.update("UPDATE person SET fio=?,year_born=? WHERE id_user=?",
                people.getFio(),people.getYear_born(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id_user=?",id);
    }
    public Person show_have_person(int id_book){
        return jdbcTemplate.query("SELECT person.id_user,person.fio,person.year_born FROM " +
                        "(SELECT list_book_user.user_id FROM (SELECT * FROM book WHERE id_book=?) " +
                        "as first JOIN list_book_user ON\n" +
                        "    first.id_book = list_book_user.book_id) " +
                        "AS SECOND JOIN person ON SECOND.user_id = person.id_user",new Object[]{id_book},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
}
