package com.example.dbframeworkmess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Map;

@Service
public class UserService {

    private static final String INSERT_SQL = "insert into user_entity (first_name, last_name, id) values (?, ?, ?)";
    private static final String SELECT_SQL = "select first_name, last_name, id from user_entity where id = ?";
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(EntityManager entityManager, UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public UserEntity createUser1(String firstName, String lastName) {
        return userRepository.save(new UserEntity(firstName, lastName));
    }

    @Transactional
    public UserEntity createUser2(String firstName, String lastName) {
        jdbcTemplate.update(INSERT_SQL, firstName, lastName, 1001);
        // Hibernate will execute a SELECT in the same transaction and it will retrieve the UserEntity
        return userRepository.findById(1001L).get();
    }

    @Transactional
    public UserEntity createUser3(String firstName, String lastName) {
        if (!userRepository.findById(1002L).isPresent()) {
            jdbcTemplate.update(INSERT_SQL, firstName, lastName, 1002);
        }

        // Hibernate doesn't cache the result of the previous findById
        // it will execute another SELECT in the same transaction and it will retrieve the UserEntity
        return userRepository.findById(1002L).get();
    }

    @Transactional
    public UserEntity createUser4(String firstName, String lastName) {
        UserEntity userEntity = userRepository.save(new UserEntity(firstName, lastName));

        // this is necessary otherwise Hibernate will flush only after the method is completed
        entityManager.flush();

        Map<String,Object> record = jdbcTemplate.queryForMap(SELECT_SQL, userEntity.getId());
        userEntity = new UserEntity();
        userEntity.setId(((Number) record.get("id")).longValue());
        userEntity.setFirstName((String) record.get("first_name"));
        userEntity.setLastName((String) record.get("last_name"));
        return userEntity;
    }
}
