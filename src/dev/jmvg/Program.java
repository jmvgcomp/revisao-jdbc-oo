package dev.jmvg;

import dev.jmvg.db.DBConnection;
import dev.jmvg.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) throws SQLException {

        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT  * FROM tb_product");

        while (rs.next()){


            System.out.println(instantiateProduct(rs));

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
}
