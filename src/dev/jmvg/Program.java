package dev.jmvg;

import dev.jmvg.db.DBConnection;

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
            System.out.println(rs.getLong("Id") +", "+rs.getString("Name"));
        }


    }
}
