package ru.vladislav.project1.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladislav.project1.models.Book;
import ru.vladislav.project1.models.Person;

import java.util.List;

@Component
public class bookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public bookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book",new BeanPropertyRowMapper<>(Book.class));
    }
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_book=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public Book show(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE name=?",new Object[]{name},new BeanPropertyRowMapper
                <>(Book.class)).stream().findAny().orElse(null);
    }
    public List<Book> show_books_user(int id){
        return jdbcTemplate.query("SELECT book.id_book,book.name,book.author,book.year_release FROM " +
                        "(SELECT first.id_user,first.fio,first.year_born,list_book_user.book_id FROM " +
                        "(SELECT * FROM person WHERE id_user=?) " +
                        "as first JOIN list_book_user ON\n" +
                        "        id_user = list_book_user.user_id) as foo JOIN book ON foo.book_id = book.id_book"
                ,new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(name,author,year_release) VALUES(?,?,?)",book.getName(),
                book.getAuthor(),book.getYear_release());
    }
    public void update(int id,Book book){
        jdbcTemplate.update("UPDATE book SET name=?,author=?,year_release=? WHERE id_book=?",
                book.getName(),book.getAuthor(),book.getYear_release(),id);
    }
    public void deleteHavePerson(int id){jdbcTemplate.update("DELETE FROM list_book_user WHERE user_id=?",id);}
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id_book=?",id);
    }
    public void addHavePerson(int id_book,Person person){
        jdbcTemplate.update("INSERT INTO list_book_user(user_id,book_id) VALUES(?,?)",person.getId_user(),id_book);
    }
}
