package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.OrderDAO;
import com.exadel.borsch.entity.DishInOrder;
import com.exadel.borsch.entity.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderDAOImpl extends JdbcDaoSupport implements OrderDAO {
    private static final String QUERY_SELECT_ALL = "select id, user_id, odata, cancelled from oorder";

    private static final String QUERY_LIST_ORDERS = "SELECT oo.id, u.login, oo.odata, d.name, dio.onumber"+
    " FROM oorder oo"+
    " INNER JOIN dish_in_order dio ON dio.oorder=oo.id"+
    " INNER JOIN dish d ON d.id=dio.dish_id"+
    " INNER JOIN user u ON u.id=oo.user_id"+
    " ORDER BY oo.id";

    private static final String QUERY_LIST_CANCELS = "SELECT oo.id, u.login, oo.odata, d.name, dio.onumber, oo.cancelled"+
            " FROM oorder oo"+
            " INNER JOIN dish_in_order dio ON dio.oorder=oo.id"+
            " INNER JOIN dish d ON d.id=dio.dish_id"+
            " INNER JOIN user u ON u.id=oo.user_id"+
            " WHERE oo.cancelled=1"+
            " ORDER BY oo.id";

    private static final String QUERY_SELECT_BY_DATE = "SELECT oo.id, u.login, oo.odata, d.name, dio.onumber"+
            " FROM oorder oo"+
            " INNER JOIN dish_in_order dio ON dio.oorder=oo.id"+
            " INNER JOIN dish d ON d.id=dio.dish_id"+
            " INNER JOIN user u ON u.id=oo.user_id"+
            " WHERE oo.odata=?"+
            " ORDER BY oo.id";

    private static final String QUERY_SELECT_CANCELS_BY_DATE = "SELECT oo.id, u.login, oo.odata, d.name, dio.onumber, oo.cancelled"+
            " FROM oorder oo"+
            " INNER JOIN dish_in_order dio ON dio.oorder=oo.id"+
            " INNER JOIN dish d ON d.id=dio.dish_id"+
            " INNER JOIN user u ON u.id=oo.user_id"+
            " WHERE oo.odata=? AND oo.cancelled=1"+
            " ORDER BY oo.id";

   // private static final String QUERY_SELECT_BY_DATE =
     //       "select id, user_id, odata, cancelled from oorder where odata=?";

    private static final String QUERY_ORDER_BY_ID = QUERY_SELECT_ALL + " where id=?";

    private static final String QUERY_SELECT_USER_DISHES_BY_DATE =
            "select dish_id, number from dish_in_order dio "
                    + "inner join oorder o on dio.oorder=o.id "
                    + "where o.user_id=? and o.odata=?";

    private static final String QUERY_SELECT_DISHES_BY_DATE =
            "select dio.dish_id, dio.number from dish_in_order dio "
                    + "inner join oorder o on dio.oorder=o.id "
                    + "where and o.odata=?";

    private final RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            Order res=new Order();
            List<DishInOrder>list=new ArrayList<DishInOrder>();
            res.setId(resultSet.getInt("id"));
            res.setUserLogin(resultSet.getString("login"));
            res.setData(resultSet.getDate("odata"));
            while(res.getId()==resultSet.getInt("id")){

                list.add(new DishInOrder(resultSet.getInt("onumber"),resultSet.getInt("id"),resultSet.getString("name")));

                if(!resultSet.next())
                    break;
            }
            resultSet.previous();
            res.setDishes(list);

            return res;
        }
    };

    private final RowMapper<Order> cancelRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            Order res=new Order();
            List<DishInOrder>list=new ArrayList<DishInOrder>();
            res.setId(resultSet.getInt("id"));
            res.setUserLogin(resultSet.getString("login"));
            res.setData(resultSet.getDate("odata"));
            res.setCancelled(resultSet.getInt("cancelled"));
            while(res.getId()==resultSet.getInt("id")){

                list.add(new DishInOrder(resultSet.getInt("onumber"),resultSet.getInt("id"),resultSet.getString("name")));

                if(!resultSet.next())
                    break;
            }
            resultSet.previous();
            res.setDishes(list);

            return res;
        }
    };

    @Override
    public List<Order> getAllOrders() {
        return getJdbcTemplate().query(
                QUERY_LIST_ORDERS,
                orderRowMapper
        );
    }

    @Override
    public List<Order> getAllCancels() {
        return getJdbcTemplate().query(
                QUERY_LIST_CANCELS,
                cancelRowMapper
        );
    }

    @Override
    public Order findById(Integer id) {
        try {
            return getJdbcTemplate().queryForObject(
                    QUERY_ORDER_BY_ID,
                    new Object[]{id},
                    orderRowMapper
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public List<Order> getOrdersByDate(Date selectedDate) {
        return getJdbcTemplate().query(
                QUERY_SELECT_BY_DATE,
                orderRowMapper,
                selectedDate
        );
    }

    @Override
    public List<Order> getCancelsByDate(Date selectedDate) {
        return getJdbcTemplate().query(
                QUERY_SELECT_CANCELS_BY_DATE,
                cancelRowMapper,
                selectedDate
        );
    }

    @Override
    public List<Order> getUserDishesByDate(Integer id, Date selectedDate) {
        return getJdbcTemplate().query(
                QUERY_SELECT_USER_DISHES_BY_DATE,
                orderRowMapper,
                selectedDate
        );
    }

    @Override
    public List<Order> getDishesByDate(Date selectedDate) {
        return getJdbcTemplate().query(
                QUERY_SELECT_DISHES_BY_DATE,
                orderRowMapper,
                selectedDate
        );
    }

}


