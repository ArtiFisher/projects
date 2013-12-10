package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.DishDAO;
import com.exadel.borsch.entity.Dish;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;


public class DishDAOImpl extends JdbcDaoSupport implements DishDAO {
    private static final String QUERY_SELECT_ALL = "select id, name, price, info, img from dish";
    private static final String QUERY_SELECT_ALL_WITH_ACCESS =
            "SELECT d.id, d.name, d.price, d.info, d.img, "
                + "(SELECT COUNT(*) FROM dish_access a "
                + "WHERE a.dish_id=d.id AND a.date=?) AS count "
                +"FROM dish d";

    private static final String QUERY_SELECT_DATE_WITH_ACCESS = "" +
            "SELECT d.id, d.name, d.price, d.info, d.img"+
            " FROM dish d"+
            " JOIN dish_access a ON a.dish_id=d.id"+
            " WHERE a.date=?";

    private static final String QUERY_SELECT_BY_DATE =
            "select d.id, d.name, d.price, d.info, d,img from dish d "
                    + "inner join dish_access da on da.dish_id=d.id "
                    + "where d.enabled=true and da.date=?";

    private static final String QUERY_DISH_BY_ID = QUERY_SELECT_ALL + " where id=?";

    private static final String QUERY_DELETE_DISH = "delete from dish where id=?";

    private static final String QUERY_DISH_INSERT = "insert into dish (name, price, info, img) values(?,?,?,?)";

    private static final String QUERY_DISH_UPDATE = "update dish set name=?, price=?, info=? where id=?"; //


    private static final String QUERY_SELECT_DISH_ID = "SELECT DISTINCT id FROM dish";

    private static final String QUERY_INSERT_DISH_ACCESS = "INSERT INTO dish_access (date, dish_id) VALUES (?, ?)";

    private static final String QUERY_DELETE_ID = "DELETE FROM dish_access WHERE date=? AND dish_id=?";

    private static final String QUERY_COUNT_DISH_ACCESS =
            "SELECT COUNT(*) AS count FROM dish_access "
                    + "WHERE date=? AND dish_id=?";

    private static final String QUERY_DELETE_BY_DISH_ID_FROM_ACCESS = "DELETE FROM dish_access WHERE dish_id=?" ;

     private final RowMapper<Dish> dishRowMapper = new RowMapper<Dish>() {
         @Override
         public Dish mapRow(ResultSet resultSet, int rowNum) throws SQLException {
             return new Dish(
                     resultSet.getInt("id"),
                     resultSet.getString("name"),
                     resultSet.getInt("price"),
                     resultSet.getString("info"),
                     resultSet.getString("img")
             );
         }
     };


    private final RowMapper<Dish> dishRowMapperWithAccess = new RowMapper<Dish>() {
        @Override
        public Dish mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Dish(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("info"),
                    resultSet.getString("img"),
                    resultSet.getInt("count")
            );
        }
    };

    private final RowMapper<Integer> dishIDRowMapper = new RowMapper<Integer>() {

        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Integer(rs.getInt("id"));
        }
    };

    @Override
    public Dish findById(Integer id) {
        try {
            return getJdbcTemplate().queryForObject(
                    QUERY_DISH_BY_ID,
                    new Object[]{id},
                    dishRowMapper
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public void deleteDish(Integer id) {
        getJdbcTemplate().update(QUERY_DELETE_DISH, id);
    }

    @Override
    public void updeteDish(Dish dish) {
        getJdbcTemplate().update(
           QUERY_DISH_UPDATE,
                dish.getName(),
                dish.getPrice(),
                dish.getInfo(),

                dish.getId()
        );
    }

    @Override
    public List<Dish> getProducts(String selectedDate) {
        return getJdbcTemplate().query(
                QUERY_SELECT_DATE_WITH_ACCESS,
                dishRowMapper,
                selectedDate
        );
    }

    @Override
    public List<Dish> getProducts() {
        return getJdbcTemplate().query(
                QUERY_SELECT_ALL,
                dishRowMapper
        );
    }


    @Override
    public List<Dish> getProductsForAdmin(String selectedDate) {
        return getJdbcTemplate().query(
                QUERY_SELECT_ALL_WITH_ACCESS,
                dishRowMapperWithAccess,
                selectedDate
        );
    }

    @Override
    public List<Integer> getDishId() {
        return getJdbcTemplate().query(
                QUERY_SELECT_DISH_ID,
                dishIDRowMapper);
    }


    @Override
    public void insertDishMark(String date, int dish_id) {
        getJdbcTemplate().update(
                QUERY_INSERT_DISH_ACCESS,
                date,
                dish_id);
    }

    @Override
    public void deleteDishMark(String date, int dish_id) {
        getJdbcTemplate().update(
                QUERY_DELETE_ID,
                date,
                dish_id);
    }


    @Override
    public int getDishAccessCount(String date, int dish_id) {
        return getJdbcTemplate().queryForInt(
                QUERY_COUNT_DISH_ACCESS,
                date,
                dish_id);
    }

    @Override
    public void deleteDishFromDishAccess(Integer dish_id) {
        getJdbcTemplate().update(
                QUERY_DELETE_BY_DISH_ID_FROM_ACCESS,
                dish_id);
    }


    @Override
    public void saveDish(Dish dish) {
        getJdbcTemplate().update(
                QUERY_DISH_INSERT,
                dish.getName(),
                dish.getPrice(),
                dish.getInfo(),
                dish.getImg()
        );
    }


}


