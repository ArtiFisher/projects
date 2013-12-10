package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.DishInOrderDAO;
import com.exadel.borsch.entity.DishInOrder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DishInOrderDAOImpl extends JdbcDaoSupport implements DishInOrderDAO {
    private static final String QUERY_SELECT_ALL = "select "
            + "dish_in_order_id, "
            + "dish_id, "
            + "onumber, "
            + "oorder from dish_in_order";

    private static final String QUERY_DISH_BY_ID_IN_DISH_IN_ORDER = "select * from dish_in_order"
            + " where dish_id=?";

    private  static final String QUERY_DELETE_BY_DISH_ID = "delete from dish_in_order"
            + " where dish_id=?";
     private final RowMapper<DishInOrder> dishInOrderRowMapper = new RowMapper<DishInOrder>() {
         @Override
         public DishInOrder mapRow(ResultSet resultSet, int rowNum) throws SQLException {
             return new DishInOrder(
                     resultSet.getInt("dish_in_order_id"),
                     resultSet.getInt("dish_id"),
                     resultSet.getInt("onumber"),
                     resultSet.getInt("oorder")
             );
         }
     };

    @Override
    public List<DishInOrder> getDishesInOrder() {
        return getJdbcTemplate().query(
                QUERY_SELECT_ALL,
                dishInOrderRowMapper
        );
    }

    @Override
    public List<DishInOrder> getDishesInOrderById(Integer id) {
        return getJdbcTemplate().query(
                QUERY_DISH_BY_ID_IN_DISH_IN_ORDER,
                new Object[]{id},
                dishInOrderRowMapper
        );
    }

    @Override
    public void deleteOrder(Integer id) {
        getJdbcTemplate().update(QUERY_DELETE_BY_DISH_ID, id);
    }
}


