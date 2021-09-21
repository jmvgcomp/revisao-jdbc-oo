package dev.jmvg;

import dev.jmvg.db.DBConnection;
import dev.jmvg.model.Order;
import dev.jmvg.model.OrderStatus;
import dev.jmvg.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Program {
    public static void main(String[] args) throws SQLException {

        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM tb_order " +
                "INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id " +
                "INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");


        Map<Long, Order> map = new HashMap<>();
        Map<Long, Product> prods = new HashMap<>();

        while (rs.next()){
            Long orderId = rs.getLong("order_id");
            if(map.get(orderId) == null){
                map.put(orderId, instantiateOrder(rs));
            }

            Long productId = rs.getLong("product_id");
            if(prods.get(productId) == null){
                prods.put(productId, instantiateProduct(rs));
            }

            map.get(orderId).getProducts().add(prods.get(productId));


        }

        for (Long orderId : map.keySet()){
            System.out.println(map.get(orderId));
            for (Product p: map.get(orderId).getProducts()) {
                System.out.println(p);
            }
            System.out.println();
        }

    }

    private static Product instantiateProduct(ResultSet rs) throws SQLException {
        Product p = new Product();

        p.setId(rs.getLong("product_id"));
        p.setDescription(rs.getString("description"));
        p.setImageUrl(rs.getString("image_uri"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));

        return p;
    }

    private static Order instantiateOrder(ResultSet rs) throws SQLException{
        Order order = new Order();

        order.setId(rs.getLong("order_id"));
        order.setLatitude(rs.getDouble("latitude"));
        order.setLongitude(rs.getDouble("longitude"));
        order.setMoment(rs.getTimestamp("moment").toInstant());
        order.setStatus(OrderStatus.values()[rs.getInt("status")]);

        return order;
    }
}
