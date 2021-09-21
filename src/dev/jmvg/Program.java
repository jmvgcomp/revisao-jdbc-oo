package dev.jmvg;

import dev.jmvg.db.DBConnection;
import dev.jmvg.model.Order;
import dev.jmvg.model.OrderStatus;
import dev.jmvg.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) throws SQLException {

        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT  * FROM tb_order");

        while (rs.next()){



            System.out.println(instantiateOrder(rs));

        }


    }

    private static Product instantiateProduct(ResultSet rs) throws SQLException {
        Product p = new Product();

        p.setId(rs.getLong("Id"));
        p.setDescription(rs.getString("description"));
        p.setImageUrl(rs.getString("image_uri"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));

        return p;
    }

    private static Order instantiateOrder(ResultSet rs) throws SQLException{
        Order order = new Order();

        order.setId(rs.getLong("id"));
        order.setLatitude(rs.getDouble("latitude"));
        order.setLongitude(rs.getDouble("longitude"));
        order.setMoment(rs.getTimestamp("moment").toInstant());
        order.setStatus(OrderStatus.values()[rs.getInt("status")]);

        return order;
    }
}
