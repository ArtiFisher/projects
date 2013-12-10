package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.UserDAO;
import com.exadel.borsch.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {


        private static final String QUERY_USER = "select"
                + " u.id as id,"
                + " u.login as login,"
                + " u.password as password,"
                + " u.role as role, "
                + " u.info as info, "
                + " u.email as email, "
                + "FROM user u ";

        private static final String QUERY_PRINCIPAL_BY_USERNAME = QUERY_USER + " where p.username=?";

        private static final String QUERY_SELECT_ALL = "select id, login, password,role, info, email from user";

//    private static final String QUERY_USER_BY_LOGIN = QUERY_SELECT_ALL + " where id=?";

        private static final String QUERY_USER_BY_ID = "select * from user where id=?";

        private static final String QUERY_USER_BY_NAME = "select * from user where login=?";

        private static final String QUERY_DELETE_USER = "delete from user where id=?";

        private static final String QUERY_USER_INSERT = "insert into user (login, password, role, info, email)"
                + " values(?,?,?,?,?)";

        private static final String QUERY_USER_UPDATE = "update user set login=?, password=?,role=?, info=?, email=? where id=?";

        private final RowMapper<User> rowMapper = new RowMapper<User>() {

            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("info"),
                        resultSet.getString("email")
                );
            }
        };


        public List<User> getUsers() {
            return getJdbcTemplate().query(
                    QUERY_SELECT_ALL,
                    rowMapper
            );
        }


        public User findByLogin(String login) {
            try {
                return getJdbcTemplate().queryForObject(
                        QUERY_USER_BY_NAME,
                        new Object[]{login},
                        rowMapper
                );
            } catch (EmptyResultDataAccessException e) {
                return null;

            }

        }
        public User findById(Integer id) {
            try {
                return getJdbcTemplate().queryForObject(
                        QUERY_USER_BY_ID,
                        new Object[]{id},
                        rowMapper
                );
            } catch (EmptyResultDataAccessException e) {
                return null;
            }
        }


        @Override
        public void deleteUser(Integer id) {
            getJdbcTemplate().update(QUERY_DELETE_USER, id);
        }

    //changed by me
        @Override
        public void updateUser(User user) {
            getJdbcTemplate().update(
                    QUERY_USER_UPDATE,
                    user.getLogin(),
                    user.getPassword(),
                    user.getRole(),
                    user.getInfo(),
                    user.getEmail(),
                    user.getId()
            );

        }
        @Override
        public void saveUser(User user) {
            getJdbcTemplate().update(
                    QUERY_USER_INSERT,
                    user.getLogin(),
                    user.getPassword(),
                    user.getRole(),
                    user.getInfo(),
                    user.getEmail()
            );
        }
}
